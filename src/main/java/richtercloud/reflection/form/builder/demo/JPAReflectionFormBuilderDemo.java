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
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import richtercloud.reflection.form.builder.ReflectionFormPanel;
import richtercloud.reflection.form.builder.components.AmountMoneyCurrencyStorage;
import richtercloud.reflection.form.builder.components.AmountMoneyUsageStatisticsStorage;
import richtercloud.reflection.form.builder.components.FixerAmountMoneyExchangeRateRetriever;
import richtercloud.reflection.form.builder.components.MemoryAmountMoneyCurrencyStorage;
import richtercloud.reflection.form.builder.components.MemoryAmountMoneyUsageStatisticsStorage;
import richtercloud.reflection.form.builder.fieldhandler.FieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.FieldHandlingException;
import richtercloud.reflection.form.builder.fieldhandler.IntegerListFieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.MappingFieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.factory.AmountMoneyMappingFieldHandlerFactory;
import richtercloud.reflection.form.builder.jpa.IdGenerator;
import richtercloud.reflection.form.builder.jpa.JPACachedFieldRetriever;
import richtercloud.reflection.form.builder.jpa.JPAEntityListFieldHandler;
import richtercloud.reflection.form.builder.jpa.JPAReflectionFormBuilder;
import richtercloud.reflection.form.builder.jpa.SequentialIdGenerator;
import richtercloud.reflection.form.builder.jpa.fieldhandler.JPAMappingFieldHandler;
import richtercloud.reflection.form.builder.jpa.fieldhandler.factory.JPAAmountMoneyMappingFieldHandlerFactory;
import richtercloud.reflection.form.builder.jpa.typehandler.ElementCollectionTypeHandler;
import richtercloud.reflection.form.builder.jpa.typehandler.ToManyTypeHandler;
import richtercloud.reflection.form.builder.jpa.typehandler.ToOneTypeHandler;
import richtercloud.reflection.form.builder.jpa.typehandler.factory.JPAAmountMoneyMappingTypeHandlerFactory;
import richtercloud.reflection.form.builder.message.LoggerMessageHandler;
import richtercloud.reflection.form.builder.message.MessageHandler;
import richtercloud.reflection.form.builder.components.AmountMoneyExchangeRateRetriever;

/**
 *
 * @author richter
 */
public class JPAReflectionFormBuilderDemo extends javax.swing.JFrame {
    private final static EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private final static Connection CONNECTION;
    private final static String APP_NAME = "reflection-form-builder-demo";
    private final static Logger LOGGER = LoggerFactory.getLogger(QueryPanelDemo.class);
    private static final long serialVersionUID = 1L;
    static {
        try {
            File parentDir = new File("/tmp/reflection-form-builder-demo");
            if(!parentDir.exists()) {
                parentDir.mkdir();
            }
            String databaseDirName = "databases";
            File databaseDir = new File(parentDir, databaseDirName);
            Class<?> driver = EmbeddedDriver.class;
            driver.newInstance();
            CONNECTION = DriverManager.getConnection(String.format("jdbc:derby:%s;create=%s", databaseDir.getAbsolutePath(), !databaseDir.exists()));
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    LOGGER.info("running {} shutdown hooks", QueryPanelDemo.class);
                    if(CONNECTION != null) {
                        try {
                            CONNECTION.close();
                        } catch (SQLException ex) {
                            LOGGER.error("an exception during shutdown of the database connection occured", ex);
                        }
                    }
                }
            });
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("richtercloud_reflection-form-builder-demo_jar_1.0-SNAPSHOTPU");
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, //parentComponent
                    String.format("<html>An unexpected exception occured during the initialization of resources (%s)</html>", ex.getMessage()), //message
                    String.format("Error in initialization of resources - %s", APP_NAME), //title
                    JOptionPane.ERROR_MESSAGE //type
            );
            LOGGER.error("An unexpected exception occured during the initialization of resources (see nested exception for details)", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    private final  MessageHandler messageHandler = new LoggerMessageHandler(LOGGER);
    private final IdGenerator idGenerator = SequentialIdGenerator.getInstance();
    private final AmountMoneyUsageStatisticsStorage amountMoneyUsageStatisticsStorage = new MemoryAmountMoneyUsageStatisticsStorage();
    private final AmountMoneyCurrencyStorage amountMoneyCurrencyStorage = new MemoryAmountMoneyCurrencyStorage();
    private final AmountMoneyExchangeRateRetriever amountMoneyConversionRateRetriever = new FixerAmountMoneyExchangeRateRetriever();
    private final JPAAmountMoneyMappingFieldHandlerFactory jPAAmountMoneyClassMappingFactory;
    private final AmountMoneyMappingFieldHandlerFactory amountMoneyMappingFieldHandlerFactory;
    private final EntityManager entityManager;
    private ReflectionFormPanel reflectionPanel;

    /**
     * Creates new form JPAReflectionFormBuilderDemo
     */
    public JPAReflectionFormBuilderDemo() {
        initComponents();
        this.entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityA entityA = new EntityA(8484L, 24, "klfds");
        EntityB entityB = new EntityB(38923L, 23, entityA);
        EntityC entityC1 = new EntityC(324L, 33, "b", "d");
        EntityC entityC2 = new EntityC(325L, 33, "b", "d");
        entityManager.getTransaction().begin();
        entityManager.persist(entityA);
        entityManager.persist(entityB);
        entityManager.persist(entityC1);
        entityManager.persist(entityC2);
        entityManager.getTransaction().commit();
        String bidirectionalHelpDialogTitle = String.format("%s - Info", JPAReflectionFormBuilderDemo.class.getSimpleName());
        jPAAmountMoneyClassMappingFactory = JPAAmountMoneyMappingFieldHandlerFactory.create(entityManager,
                20,
                messageHandler,
                amountMoneyUsageStatisticsStorage,
                amountMoneyCurrencyStorage,
                amountMoneyConversionRateRetriever,
                bidirectionalHelpDialogTitle);
        this.amountMoneyMappingFieldHandlerFactory = new AmountMoneyMappingFieldHandlerFactory(amountMoneyUsageStatisticsStorage,
                amountMoneyCurrencyStorage,
                amountMoneyConversionRateRetriever,
                messageHandler);
        JPAAmountMoneyMappingTypeHandlerFactory jPAAmountMoneyTypeHandlerMappingFactory = new JPAAmountMoneyMappingTypeHandlerFactory(entityManager,
                20,
                messageHandler,
                bidirectionalHelpDialogTitle);
        FieldHandler embeddableFieldHandler = new MappingFieldHandler(this.amountMoneyMappingFieldHandlerFactory.generateClassMapping(), //don't use JPA... field handler factory because it's for embeddables
                this.amountMoneyMappingFieldHandlerFactory.generatePrimitiveMapping());
        ElementCollectionTypeHandler elementCollectionTypeHandler = new ElementCollectionTypeHandler(jPAAmountMoneyTypeHandlerMappingFactory.generateTypeHandlerMapping(),
                jPAAmountMoneyTypeHandlerMappingFactory.generateTypeHandlerMapping(),
                messageHandler,
                embeddableFieldHandler);
        try {
            Map<java.lang.reflect.Type, FieldHandler<?,?,?, ?>> classMapping = jPAAmountMoneyClassMappingFactory.generateClassMapping();
            classMapping.put(EntityA.class.getDeclaredField("elementCollectionBasics").getGenericType(),
                    new IntegerListFieldHandler(messageHandler));
            classMapping.put(EntityA.class.getDeclaredField("oneToManyEntityBs").getGenericType(),
                    new JPAEntityListFieldHandler(entityManager, messageHandler, bidirectionalHelpDialogTitle));
            classMapping.put(EntityD.class.getDeclaredField("oneToManyEntityCs").getGenericType(),
                    new JPAEntityListFieldHandler(entityManager, messageHandler, bidirectionalHelpDialogTitle));
            Map<Class<?>, FieldHandler<?,?,?, ?>> primitiveMapping = jPAAmountMoneyClassMappingFactory.generatePrimitiveMapping();
            ToManyTypeHandler toManyTypeHandler = new ToManyTypeHandler(entityManager,
                    messageHandler,
                    jPAAmountMoneyTypeHandlerMappingFactory.generateTypeHandlerMapping(),
                    jPAAmountMoneyTypeHandlerMappingFactory.generateTypeHandlerMapping(),
                    bidirectionalHelpDialogTitle);
            ToOneTypeHandler toOneTypeHandler = new ToOneTypeHandler(entityManager,
                    messageHandler,
                    bidirectionalHelpDialogTitle);
            JPACachedFieldRetriever fieldRetriever = new JPACachedFieldRetriever();
            FieldHandler fieldHandler = new JPAMappingFieldHandler(jPAAmountMoneyClassMappingFactory.generateClassMapping(),
                    amountMoneyMappingFieldHandlerFactory.generateClassMapping(),
                    primitiveMapping,
                    elementCollectionTypeHandler,
                    toManyTypeHandler,
                    toOneTypeHandler,
                    idGenerator,
                    messageHandler,
                    fieldRetriever);
            JPAReflectionFormBuilder reflectionFormBuilder = new JPAReflectionFormBuilder(entityManager,
                    APP_NAME,
                    messageHandler,
                    fieldRetriever);
            try {
                reflectionPanel = reflectionFormBuilder.transformEntityClass(EntityD.class,
                        null, //entityToUpdate
                        fieldHandler
                );
            } catch (FieldHandlingException ex) {
                throw new RuntimeException(ex);
            }
            BoxLayout mainPanelLayout = new BoxLayout(this.mainPanel, BoxLayout.X_AXIS);
            this.mainPanel.setLayout(mainPanelLayout);
            this.mainPanel.add(reflectionPanel);
            this.mainPanel.validate();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
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
        try {
            Object instance = this.reflectionPanel.retrieveInstance();
            ReflectionFormBuilderDemo.displayInstanceInfoDialog(this, instance);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
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
                new JPAReflectionFormBuilderDemo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton displayButton;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
