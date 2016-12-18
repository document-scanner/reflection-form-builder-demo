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

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import richtercloud.message.handler.LoggerMessageHandler;
import richtercloud.message.handler.MessageHandler;
import richtercloud.reflection.form.builder.ReflectionFormBuilder;
import richtercloud.reflection.form.builder.ReflectionFormPanel;
import richtercloud.reflection.form.builder.fieldhandler.FieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.MappingFieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.factory.MappingFieldHandlerFactory;
import richtercloud.reflection.form.builder.jpa.JPACachedFieldRetriever;
import richtercloud.reflection.form.builder.jpa.panels.QueryListPanel;
import richtercloud.reflection.form.builder.jpa.storage.DerbyEmbeddedPersistenceStorage;
import richtercloud.reflection.form.builder.jpa.storage.DerbyEmbeddedPersistenceStorageConf;
import richtercloud.reflection.form.builder.jpa.storage.FieldInitializer;
import richtercloud.reflection.form.builder.jpa.storage.PersistenceStorage;
import richtercloud.reflection.form.builder.jpa.storage.ReflectionFieldInitializer;
import richtercloud.reflection.form.builder.storage.StorageConfInitializationException;
import richtercloud.reflection.form.builder.storage.StorageCreationException;
import richtercloud.reflection.form.builder.storage.StorageException;

/**
 *
 * @author richter
 */
public class QueryListPanelDemo extends AbstractDemo {
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = LoggerFactory.getLogger(QueryListPanelDemo.class);
    private final static Random RANDOM = new Random();
    private final static String APP_NAME = "Query list panel demo";
    private static Long nextId = 1L;
    private final static String BIDIRECTIONAL_HELP_DIALOG_TITLE = String.format("%s - Info", JPAReflectionFormBuilderDemo.class.getSimpleName());
    private static synchronized Long getNextId() {
        nextId += 1;
        return nextId;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QueryListPanelDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QueryListPanelDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QueryListPanelDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QueryListPanelDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new QueryListPanelDemo().setVisible(true);
                } catch (IOException | SQLException | StorageException | StorageCreationException | StorageConfInitializationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private ReflectionFormBuilder reflectionFormBuilder;
    private Class<?> entityClass = EntityA.class;
    private List<Object> initialValues = new LinkedList<>();
    private final MessageHandler messageHandler = new LoggerMessageHandler(LOGGER);
    private final QueryListPanel queryListPanel;
    private javax.swing.JButton createAButton;
    private javax.swing.JButton createBButton;
    private javax.swing.JButton createCButton;

    /**
     * Creates new form ListQueryPanelDemo
     */
    public QueryListPanelDemo() throws IOException, SQLException, StorageException, StorageCreationException, StorageConfInitializationException {
        PersistenceStorage storage = new DerbyEmbeddedPersistenceStorage(new DerbyEmbeddedPersistenceStorageConf(getEntityClasses(),
                getDatabaseName(),
                getSchemeChecksumFile()),
                "richtercloud_reflection-form-builder-demo_jar_1.0-SNAPSHOTPU",
                1 //parallelQueryCount
        );
        MappingFieldHandlerFactory mappingFieldHandlerFactory = new MappingFieldHandlerFactory(messageHandler);
        FieldHandler fieldHandler = new MappingFieldHandler<>(mappingFieldHandlerFactory.generateClassMapping(),
                mappingFieldHandlerFactory.generatePrimitiveMapping());
        this.reflectionFormBuilder = new ReflectionFormBuilder("Field description",
                messageHandler,
                new JPACachedFieldRetriever());
        FieldInitializer fieldInitializer = new ReflectionFieldInitializer(this.reflectionFormBuilder.getFieldRetriever());
        try {
            this.queryListPanel = new QueryListPanel(getStorage(),
                    reflectionFormBuilder,
                    entityClass,
                    messageHandler,
                    this.initialValues,
                    BIDIRECTIONAL_HELP_DIALOG_TITLE,
                    fieldInitializer);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            JOptionPane.showMessageDialog(this, //parent
                    String.format("The following unexpected exception occured during intialization of the query panel: %s", ReflectionFormPanel.generateExceptionMessage(ex)),
                    null, WIDTH);
            throw new RuntimeException(ex);
        }
        this.initComponents();
    }


    private void initComponents() {

        createAButton = new javax.swing.JButton();
        createCButton = new javax.swing.JButton();
        createBButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 500, 300));

        createAButton.setText("Create new A (references B)");
        createAButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    createAButtonActionPerformed(evt);
                } catch (StorageException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        createCButton.setText("Create new C (extends A)");
        createCButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    createCButtonActionPerformed(evt);
                } catch (StorageException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        createBButton.setText("Create new B");
        createBButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    createBButtonActionPerformed(evt);
                } catch (StorageException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(queryListPanel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(createAButton)
                        .addComponent(createBButton)
                        .addComponent(createCButton)));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(queryListPanel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(createAButton)
                        .addComponent(createBButton)
                        .addComponent(createCButton)));

        pack();
    }

    private void createAButtonActionPerformed(java.awt.event.ActionEvent evt) throws StorageException {
        Long nextId0 = getNextId();
        EntityA newA = new EntityA(nextId0, RANDOM.nextInt(), String.valueOf(RANDOM.nextInt()));
        getStorage().store(newA);
        LOGGER.info("Create and persisted new instance of {}", EntityA.class.getName());
    }

    private void createCButtonActionPerformed(java.awt.event.ActionEvent evt) throws StorageException {
        Long nextId0 = getNextId();
        EntityC newC = new EntityC(nextId0, RANDOM.nextInt(), String.valueOf(RANDOM.nextInt()), String.valueOf(RANDOM.nextInt()));
        getStorage().store(newC);
        LOGGER.info("Create and persisted new instance of {}", EntityC.class.getName());
    }

    private void createBButtonActionPerformed(java.awt.event.ActionEvent evt) throws StorageException {
        Long nextId0 = getNextId();
        List<EntityA> as = getStorage().runQueryAll(EntityA.class);
        EntityA randomA = null;
        if(!as.isEmpty()) {
            randomA = as.get(RANDOM.nextInt(as.size()));
        }
        EntityB newB = new EntityB(nextId0, RANDOM.nextInt(), randomA);
        getStorage().store(newB);
        LOGGER.info("Create and persisted new instance of {}", EntityB.class.getName());
    }

    @Override
    protected String getAppName() {
        return APP_NAME;
    }
}
