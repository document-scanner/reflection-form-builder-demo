/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.richtercloud.reflection.form.builder.demo;

import de.richtercloud.message.handler.DialogMessageHandler;
import de.richtercloud.message.handler.ExceptionMessage;
import de.richtercloud.message.handler.IssueHandler;
import de.richtercloud.message.handler.LoggerIssueHandler;
import de.richtercloud.message.handler.MessageHandler;
import de.richtercloud.reflection.form.builder.ResetException;
import de.richtercloud.reflection.form.builder.fieldhandler.FieldHandlingException;
import de.richtercloud.reflection.form.builder.jpa.panels.BidirectionalControlPanel;
import de.richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntry;
import de.richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorage;
import de.richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorageCreationException;
import de.richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorageFactory;
import de.richtercloud.reflection.form.builder.jpa.panels.QueryPanel;
import de.richtercloud.reflection.form.builder.jpa.panels.XMLFileQueryHistoryEntryStorageFactory;
import de.richtercloud.reflection.form.builder.jpa.retriever.JPAOrderedCachedFieldRetriever;
import de.richtercloud.reflection.form.builder.jpa.storage.FieldInitializer;
import de.richtercloud.reflection.form.builder.jpa.storage.ReflectionFieldInitializer;
import de.richtercloud.reflection.form.builder.retriever.FieldOrderValidationException;
import de.richtercloud.reflection.form.builder.storage.StorageConfValidationException;
import de.richtercloud.reflection.form.builder.storage.StorageCreationException;
import de.richtercloud.reflection.form.builder.storage.StorageException;
import de.richtercloud.validation.tools.FieldRetriever;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richter
 */
@SuppressWarnings({"PMD.SingularField",
    "PMD.AccessorMethodGeneration",
    "PMD.FieldDeclarationsShouldBeAtStartOfClass"
})
public class QueryPanelDemo extends AbstractDemo {
    private static final long serialVersionUID = 1L;
    private final static String APP_NAME = "reflection-form-builder-demo";
    private final static Logger LOGGER = LoggerFactory.getLogger(QueryPanelDemo.class);
    private static Long nextId = 1L;
    private final static Random RANDOM = new Random();
    private final static List<QueryHistoryEntry> QUERY_PANEL_INITIAL_HISTORY = new LinkedList<>();
    static {
        QUERY_PANEL_INITIAL_HISTORY.add(new QueryHistoryEntry("select a from EntityA a", 1, new Date()));
        QUERY_PANEL_INITIAL_HISTORY.add(new QueryHistoryEntry("select b from EntityB b", 5, new Date()));
        QUERY_PANEL_INITIAL_HISTORY.add(new QueryHistoryEntry("select c from EntityC c", 3, new Date()));
    }
    private final IssueHandler issueHandler = new LoggerIssueHandler(LOGGER);
    private final Class<?> entityClass = EntityA.class;
    private final Set<Class<?>> entityClasses = new HashSet<>(Arrays.asList(EntityA.class,
            EntityB.class,
            EntityC.class));
    private final FieldRetriever fieldRetriever = new JPAOrderedCachedFieldRetriever(entityClasses);
    private final JButton createAButton = new JButton();
    private final JButton createBButton = new JButton();
    private final JButton createCButton = new JButton();
    private final QueryPanel<EntityA> queryPanel;

    private static Long getNextId() {
        nextId += 1;
        return nextId;
    }

    public QueryPanelDemo() throws IOException,
            IllegalArgumentException,
            FieldHandlingException,
            FieldOrderValidationException,
            QueryHistoryEntryStorageCreationException,
            ResetException,
            SQLException,
            StorageConfValidationException,
            StorageCreationException {
        super();
        this.queryPanel = createQueryPanel();
        this.initComponents();
    }

    private QueryPanel createQueryPanel() throws FieldHandlingException,
            IOException,
            QueryHistoryEntryStorageCreationException,
            ResetException {
        String bidirectionalHelpDialogTitle = String.format("%s - Info", QueryPanelDemo.class.getSimpleName());

        List<Field> entityClassFields;
        entityClassFields = fieldRetriever.retrieveRelevantFields(entityClass);
        Set<Field> mappedFieldCandidates = QueryPanel.retrieveMappedFieldCandidates(entityClass,
                        entityClassFields);
        BidirectionalControlPanel bidirectionalControlPanel = new BidirectionalControlPanel(entityClass,
                bidirectionalHelpDialogTitle,
                QueryPanel.retrieveMappedByFieldPanel(entityClassFields),
                mappedFieldCandidates);
        FieldInitializer fieldInitializer = new ReflectionFieldInitializer(fieldRetriever);
        File entryStorageFile = File.createTempFile(QueryPanelDemo.class.getSimpleName(),
                null);
        QueryHistoryEntryStorageFactory entryStorageFactory = new XMLFileQueryHistoryEntryStorageFactory(entryStorageFile,
                getEntityClasses(),
                false,
                getIssueHandler());
        QueryHistoryEntryStorage entryStorage = entryStorageFactory.create();
        return new QueryPanel<>(getStorage(),
                entityClass,
                issueHandler,
                fieldRetriever,
                null, //initialValue
                bidirectionalControlPanel,
                ListSelectionModel.SINGLE_SELECTION,
                fieldInitializer,
                entryStorage);
    }

    @Override
    protected String getAppName() {
        return APP_NAME;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        createAButton.setText("Create new A (references B)");
        createAButton.addActionListener((ActionEvent evt) -> {
            createAButtonActionPerformed(evt);
        });

        createBButton.setText("Create new B");
        createBButton.addActionListener((ActionEvent evt) -> {
            createBButtonActionPerformed(evt);
        });

        createCButton.setText("Create new C (extends A)");
        createCButton.addActionListener((ActionEvent evt) -> {
            createCButtonActionPerformed(evt);
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(236, Short.MAX_VALUE)
                .addComponent(createCButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(createBButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(createAButton)
                .addContainerGap())
            .addComponent(queryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(queryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createAButton)
                    .addComponent(createBButton)
                    .addComponent(createCButton))
                .addContainerGap())
        );

        pack();
    }

    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void createAButtonActionPerformed(ActionEvent evt) {
        Long nextId0 = getNextId();
        EntityA newA = new EntityA(nextId0, RANDOM.nextInt(), String.valueOf(RANDOM.nextInt()));
        try {
            getStorage().store(newA);
        } catch (StorageException ex) {
            issueHandler.handle(new ExceptionMessage(ex));
        }
        LOGGER.info("Create and persisted new instance of {}", EntityA.class.getName());
    }

    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void createBButtonActionPerformed(ActionEvent evt) {
        Long nextId0 = getNextId();
        List<EntityA> as = getStorage().runQueryAll(EntityA.class);
        EntityA randomA = null;
        if(!as.isEmpty()) {
            randomA = as.get(RANDOM.nextInt(as.size()));
        }
        EntityB newB = new EntityB(nextId0, RANDOM.nextInt(), randomA);
        try {
            getStorage().store(newB);
        } catch (StorageException ex) {
            issueHandler.handle(new ExceptionMessage(ex));
        }
        LOGGER.info("Create and persisted new instance of {}", EntityB.class.getName());
    }

    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void createCButtonActionPerformed(ActionEvent evt) {
        Long nextId0 = getNextId();
        EntityC newC = new EntityC(nextId0, RANDOM.nextInt(), String.valueOf(RANDOM.nextInt()), String.valueOf(RANDOM.nextInt()));
        try {
            getStorage().store(newC);
        } catch (StorageException ex) {
            issueHandler.handle(new ExceptionMessage(ex));
        }
        LOGGER.info("Create and persisted new instance of {}", EntityC.class.getName());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        MessageHandler messageHandler = new DialogMessageHandler(null //parent
        );
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            messageHandler.handle(new ExceptionMessage(ex));
            return;
        }

        /* Create and display the form */
        EventQueue.invokeLater(() -> {
            try {
                new QueryPanelDemo().setVisible(true);
            } catch (SQLException
                    | IOException
                    | IllegalArgumentException
                    | QueryHistoryEntryStorageCreationException
                    | ResetException
                    | FieldOrderValidationException
                    | FieldHandlingException
                    | StorageConfValidationException
                    | StorageCreationException ex) {
                messageHandler.handle(new ExceptionMessage(ex));
            }
        });
    }
}
