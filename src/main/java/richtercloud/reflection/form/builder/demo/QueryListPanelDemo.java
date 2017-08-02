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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.GroupLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import richtercloud.message.handler.DialogMessageHandler;
import richtercloud.message.handler.ExceptionMessage;
import richtercloud.message.handler.IssueHandler;
import richtercloud.message.handler.LoggerIssueHandler;
import richtercloud.message.handler.MessageHandler;
import richtercloud.reflection.form.builder.ResetException;
import richtercloud.reflection.form.builder.jpa.JPACachedFieldRetriever;
import richtercloud.reflection.form.builder.jpa.JPAFieldRetriever;
import richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorage;
import richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorageCreationException;
import richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorageFactory;
import richtercloud.reflection.form.builder.jpa.panels.QueryListPanel;
import richtercloud.reflection.form.builder.jpa.panels.XMLFileQueryHistoryEntryStorageFactory;
import richtercloud.reflection.form.builder.jpa.storage.FieldInitializer;
import richtercloud.reflection.form.builder.jpa.storage.ReflectionFieldInitializer;
import richtercloud.reflection.form.builder.storage.StorageConfValidationException;
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
        MessageHandler messageHandler = new DialogMessageHandler(null //parent
        );
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            messageHandler.handle(new ExceptionMessage(ex));
            return;
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new QueryListPanelDemo().setVisible(true);
                } catch (IOException
                        | SQLException
                        | StorageException
                        | StorageCreationException
                        | StorageConfValidationException
                        | QueryHistoryEntryStorageCreationException
                        | NoSuchFieldException
                        | ResetException
                        | IllegalAccessException ex) {
                    messageHandler.handle(new ExceptionMessage(ex));
                }
            }
        });
    }
    private Class<?> entityClass = EntityA.class;
    private List<Object> initialValues = new LinkedList<>();
    private final IssueHandler issueHandler = new LoggerIssueHandler(LOGGER);
    private final QueryListPanel queryListPanel;
    private javax.swing.JButton createAButton;
    private javax.swing.JButton createBButton;
    private javax.swing.JButton createCButton;

    /**
     * Creates new form ListQueryPanelDemo
     */
    public QueryListPanelDemo() throws IOException,
            SQLException,
            StorageException,
            StorageCreationException,
            StorageConfValidationException,
            QueryHistoryEntryStorageCreationException,
            NoSuchFieldException,
            ResetException,
            IllegalArgumentException,
            IllegalAccessException {
        JPAFieldRetriever fieldRetriever = new JPACachedFieldRetriever();
        FieldInitializer fieldInitializer = new ReflectionFieldInitializer(fieldRetriever);
        File entryStorageFile = File.createTempFile(QueryListPanelDemo.class.getSimpleName(),
                null);
        QueryHistoryEntryStorageFactory entryStorageFactory = new XMLFileQueryHistoryEntryStorageFactory(entryStorageFile,
                getEntityClasses(),
                false,
                getIssueHandler());
        QueryHistoryEntryStorage entryStorage = entryStorageFactory.create();
        this.queryListPanel = new QueryListPanel(getStorage(),
                fieldRetriever,
                entityClass,
                issueHandler,
                this.initialValues,
                BIDIRECTIONAL_HELP_DIALOG_TITLE,
                fieldInitializer,
                entryStorage);
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
                    issueHandler.handle(new ExceptionMessage(ex));
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
                    issueHandler.handle(new ExceptionMessage(ex));
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
                    issueHandler.handle(new ExceptionMessage(ex));
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

    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void createAButtonActionPerformed(java.awt.event.ActionEvent evt) throws StorageException {
        Long nextId0 = getNextId();
        EntityA newA = new EntityA(nextId0, RANDOM.nextInt(), String.valueOf(RANDOM.nextInt()));
        getStorage().store(newA);
        LOGGER.info("Create and persisted new instance of {}", EntityA.class.getName());
    }

    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void createCButtonActionPerformed(java.awt.event.ActionEvent evt) throws StorageException {
        Long nextId0 = getNextId();
        EntityC newC = new EntityC(nextId0, RANDOM.nextInt(), String.valueOf(RANDOM.nextInt()), String.valueOf(RANDOM.nextInt()));
        getStorage().store(newC);
        LOGGER.info("Create and persisted new instance of {}", EntityC.class.getName());
    }

    @SuppressWarnings("PMD.UnusedFormalParameter")
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
