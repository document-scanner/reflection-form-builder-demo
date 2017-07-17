/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package richtercloud.reflection.form.builder.demo;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import richtercloud.reflection.form.builder.ReflectionFormPanel;
import richtercloud.reflection.form.builder.TransformationException;
import richtercloud.reflection.form.builder.components.money.AmountMoneyCurrencyStorage;
import richtercloud.reflection.form.builder.components.money.AmountMoneyExchangeRateRetriever;
import richtercloud.reflection.form.builder.components.money.AmountMoneyUsageStatisticsStorage;
import richtercloud.reflection.form.builder.components.money.FailsafeAmountMoneyExchangeRateRetriever;
import richtercloud.reflection.form.builder.components.money.MemoryAmountMoneyCurrencyStorage;
import richtercloud.reflection.form.builder.components.money.MemoryAmountMoneyUsageStatisticsStorage;
import richtercloud.reflection.form.builder.fieldhandler.FieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.IntegerListFieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.MappingFieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.factory.AmountMoneyMappingFieldHandlerFactory;
import richtercloud.reflection.form.builder.jpa.JPACachedFieldRetriever;
import richtercloud.reflection.form.builder.jpa.JPAEntityListFieldHandler;
import richtercloud.reflection.form.builder.jpa.JPAReflectionFormBuilder;
import richtercloud.reflection.form.builder.jpa.WarningHandler;
import richtercloud.reflection.form.builder.jpa.fieldhandler.JPAMappingFieldHandler;
import richtercloud.reflection.form.builder.jpa.fieldhandler.factory.JPAAmountMoneyMappingFieldHandlerFactory;
import richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorage;
import richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorageCreationException;
import richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorageFactory;
import richtercloud.reflection.form.builder.jpa.panels.XMLFileQueryHistoryEntryStorageFactory;
import richtercloud.reflection.form.builder.jpa.storage.FieldInitializer;
import richtercloud.reflection.form.builder.jpa.storage.ReflectionFieldInitializer;
import richtercloud.reflection.form.builder.jpa.typehandler.ElementCollectionTypeHandler;
import richtercloud.reflection.form.builder.jpa.typehandler.ToManyTypeHandler;
import richtercloud.reflection.form.builder.jpa.typehandler.ToOneTypeHandler;
import richtercloud.reflection.form.builder.jpa.typehandler.factory.JPAAmountMoneyMappingTypeHandlerFactory;
import richtercloud.reflection.form.builder.storage.StorageConfValidationException;
import richtercloud.reflection.form.builder.storage.StorageCreationException;
import richtercloud.reflection.form.builder.storage.StorageException;
import richtercloud.validation.tools.FieldRetrievalException;
import richtercloud.validation.tools.FieldRetriever;

/**
 *
 * @author richter
 */
public class JPAReflectionFormBuilderDemo extends AbstractDemo {
    private final static String APP_NAME = "reflection-form-builder-demo";
    private final static Logger LOGGER = LoggerFactory.getLogger(JPAReflectionFormBuilderDemo.class);
    private static final long serialVersionUID = 1L;
    private final AmountMoneyUsageStatisticsStorage amountMoneyUsageStatisticsStorage = new MemoryAmountMoneyUsageStatisticsStorage();
    private final AmountMoneyCurrencyStorage amountMoneyCurrencyStorage = new MemoryAmountMoneyCurrencyStorage();
    private final File fileCacheDir;
    private final AmountMoneyExchangeRateRetriever amountMoneyExchangeRateRetriever;
    private final JPAAmountMoneyMappingFieldHandlerFactory jPAAmountMoneyClassMappingFactory;
    private final AmountMoneyMappingFieldHandlerFactory amountMoneyMappingFieldHandlerFactory;
    private ReflectionFormPanel reflectionPanel;
    private final FieldRetriever fieldRetriever = new JPACachedFieldRetriever();

    /**
     * Creates new form JPAReflectionFormBuilderDemo
     */
    public JPAReflectionFormBuilderDemo() throws StorageException, IOException, TransformationException, QueryHistoryEntryStorageCreationException, NoSuchFieldException, SQLException, StorageConfValidationException, StorageCreationException, FieldRetrievalException {
        super();
        initComponents();
        fileCacheDir = File.createTempFile(JPAReflectionFormBuilderDemo.class.getSimpleName(), null);
        amountMoneyExchangeRateRetriever = new FailsafeAmountMoneyExchangeRateRetriever(fileCacheDir);

        EntityA entityA = new EntityA(8484L, 24, "klfds");
        EntityB entityB = new EntityB(38923L, 23, entityA);
        EntityC entityC1 = new EntityC(324L, 33, "b", "d");
        EntityC entityC2 = new EntityC(325L, 33, "b", "d");
        getStorage().store(entityA);
        getStorage().store(entityB);
        getStorage().store(entityC1);
        getStorage().store(entityC2);
        String bidirectionalHelpDialogTitle = String.format("%s - Info", JPAReflectionFormBuilderDemo.class.getSimpleName());
        jPAAmountMoneyClassMappingFactory = JPAAmountMoneyMappingFieldHandlerFactory.create(getStorage(),
                20,
                getIssueHandler(),
                amountMoneyUsageStatisticsStorage,
                amountMoneyCurrencyStorage,
                amountMoneyExchangeRateRetriever,
                bidirectionalHelpDialogTitle,
                fieldRetriever);
        this.amountMoneyMappingFieldHandlerFactory = new AmountMoneyMappingFieldHandlerFactory(amountMoneyUsageStatisticsStorage,
                amountMoneyCurrencyStorage,
                amountMoneyExchangeRateRetriever,
                getIssueHandler());
        JPAAmountMoneyMappingTypeHandlerFactory jPAAmountMoneyTypeHandlerMappingFactory = new JPAAmountMoneyMappingTypeHandlerFactory(getStorage(),
                20,
                getIssueHandler(),
                bidirectionalHelpDialogTitle,
                fieldRetriever);
        FieldHandler embeddableFieldHandler = new MappingFieldHandler(this.amountMoneyMappingFieldHandlerFactory.generateClassMapping(), //don't use JPA... field handler factory because it's for embeddables
                this.amountMoneyMappingFieldHandlerFactory.generatePrimitiveMapping());
        ElementCollectionTypeHandler elementCollectionTypeHandler = new ElementCollectionTypeHandler(jPAAmountMoneyTypeHandlerMappingFactory.generateTypeHandlerMapping(),
                jPAAmountMoneyTypeHandlerMappingFactory.generateTypeHandlerMapping(),
                getIssueHandler(),
                embeddableFieldHandler,
                fieldRetriever);
        JPACachedFieldRetriever fieldRetriever = new JPACachedFieldRetriever();
        FieldInitializer fieldInitializer = new ReflectionFieldInitializer(fieldRetriever);
        File entryStorageFile = File.createTempFile(JPAReflectionFormBuilderDemo.class.getSimpleName(),
                null);
        QueryHistoryEntryStorageFactory entryStorageFactory = new XMLFileQueryHistoryEntryStorageFactory(entryStorageFile,
                getEntityClasses(),
                false, //forbidSubtypes
                getIssueHandler());
        QueryHistoryEntryStorage entryStorage = entryStorageFactory.create();
        Map<java.lang.reflect.Type, FieldHandler<?,?,?, ?>> classMapping = jPAAmountMoneyClassMappingFactory.generateClassMapping();
        classMapping.put(EntityA.class.getDeclaredField("elementCollectionBasics").getGenericType(),
                new IntegerListFieldHandler(getIssueHandler()));
        classMapping.put(EntityA.class.getDeclaredField("oneToManyEntityBs").getGenericType(),
                new JPAEntityListFieldHandler(getStorage(),
                        getIssueHandler(),
                        bidirectionalHelpDialogTitle,
                        fieldInitializer,
                        entryStorage,
                        fieldRetriever));
        classMapping.put(EntityD.class.getDeclaredField("oneToManyEntityCs").getGenericType(),
                new JPAEntityListFieldHandler(getStorage(),
                        getIssueHandler(),
                        bidirectionalHelpDialogTitle,
                        fieldInitializer,
                        entryStorage,
                        fieldRetriever));
        Map<Class<?>, FieldHandler<?,?,?, ?>> primitiveMapping = jPAAmountMoneyClassMappingFactory.generatePrimitiveMapping();
        ToManyTypeHandler toManyTypeHandler = new ToManyTypeHandler(getStorage(),
                getIssueHandler(),
                jPAAmountMoneyTypeHandlerMappingFactory.generateTypeHandlerMapping(),
                jPAAmountMoneyTypeHandlerMappingFactory.generateTypeHandlerMapping(),
                bidirectionalHelpDialogTitle,
                fieldInitializer,
                entryStorage,
                fieldRetriever);
        ToOneTypeHandler toOneTypeHandler = new ToOneTypeHandler(getStorage(),
                getIssueHandler(),
                bidirectionalHelpDialogTitle,
                fieldInitializer,
                entryStorage,
                fieldRetriever);
        FieldHandler fieldHandler = new JPAMappingFieldHandler(jPAAmountMoneyClassMappingFactory.generateClassMapping(),
                amountMoneyMappingFieldHandlerFactory.generateClassMapping(),
                primitiveMapping,
                elementCollectionTypeHandler,
                toManyTypeHandler,
                toOneTypeHandler,
                getIssueHandler(),
                fieldRetriever,
                getIdApplier());
        JPAReflectionFormBuilder reflectionFormBuilder = new JPAReflectionFormBuilder(getStorage(),
                APP_NAME,
                getIssueHandler(),
                getConfirmMessageHandler(),
                fieldRetriever,
                getIdApplier(),
                getIdGenerator(),
                new HashMap<Class<?>, WarningHandler<?>>() //warningHandlers
        );
        reflectionPanel = reflectionFormBuilder.transformEntityClass(EntityD.class,
                null, //entityToUpdate
                fieldHandler
        );
        BoxLayout mainPanelLayout = new BoxLayout(this.mainPanel, BoxLayout.X_AXIS);
        this.mainPanel.setLayout(mainPanelLayout);
        this.mainPanel.add(reflectionPanel);
        this.mainPanel.validate();
    }

    @Override
    protected String getAppName() {
        return APP_NAME;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        displayButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 500, 300));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );

        displayButton.setText("Display instance info");
        displayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(displayButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(displayButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void displayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayButtonActionPerformed
        Object instance = this.reflectionPanel.retrieveInstance();
        ReflectionFormBuilderDemo.displayInstanceInfoDialog(this, instance);
    }//GEN-LAST:event_displayButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new JPAReflectionFormBuilderDemo().setVisible(true);
                } catch (StorageException | IOException | TransformationException | QueryHistoryEntryStorageCreationException | NoSuchFieldException | SQLException | StorageConfValidationException | StorageCreationException | FieldRetrievalException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton displayButton;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
