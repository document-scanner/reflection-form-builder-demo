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
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.ListSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import richtercloud.message.handler.IssueHandler;
import richtercloud.message.handler.LoggerIssueHandler;
import richtercloud.validation.tools.FieldRetriever;
import richtercloud.reflection.form.builder.ReflectionFormBuilder;
import richtercloud.reflection.form.builder.jpa.JPACachedFieldRetriever;
import richtercloud.reflection.form.builder.jpa.panels.BidirectionalControlPanel;
import richtercloud.reflection.form.builder.jpa.panels.XMLFileQueryHistoryEntryStorageFactory;
import richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntry;
import richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorage;
import richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorageCreationException;
import richtercloud.reflection.form.builder.jpa.panels.QueryHistoryEntryStorageFactory;
import richtercloud.reflection.form.builder.jpa.panels.QueryPanel;
import richtercloud.reflection.form.builder.jpa.storage.FieldInitializer;
import richtercloud.reflection.form.builder.jpa.storage.ReflectionFieldInitializer;
import richtercloud.reflection.form.builder.storage.StorageConfValidationException;
import richtercloud.reflection.form.builder.storage.StorageCreationException;
import richtercloud.reflection.form.builder.storage.StorageException;

/**
 *
 * @author richter
 */
public class QueryPanelDemo extends AbstractDemo {
    private static final long serialVersionUID = 1L;
    private final static String APP_NAME = "reflection-form-builder-demo";
    private final static Logger LOGGER = LoggerFactory.getLogger(QueryPanelDemo.class);
    private static Long nextId = 1L;
    private final static Random RANDOM = new Random();
    private ReflectionFormBuilder reflectionFormBuilder;
    private final static List<QueryHistoryEntry> QUERY_PANEL_INITIAL_HISTORY = new LinkedList<>();
    static {
        QUERY_PANEL_INITIAL_HISTORY.add(new QueryHistoryEntry("select a from EntityA a", 1, new Date()));
        QUERY_PANEL_INITIAL_HISTORY.add(new QueryHistoryEntry("select b from EntityB b", 5, new Date()));
        QUERY_PANEL_INITIAL_HISTORY.add(new QueryHistoryEntry("select c from EntityC c", 3, new Date()));
    }
    private final IssueHandler issueHandler = new LoggerIssueHandler(LOGGER);
    private final Class<?> entityClass = EntityA.class;
    private final FieldRetriever fieldRetriever = new JPACachedFieldRetriever();

    /**
     * Creates new form Demo
     */
    public QueryPanelDemo() throws SQLException, IOException, StorageException, StorageCreationException, StorageConfValidationException {
        this.reflectionFormBuilder = new ReflectionFormBuilder("Field description",
                issueHandler,
                new JPACachedFieldRetriever());
        this.initComponents();
    }

    private static synchronized Long getNextId() {
        nextId += 1;
        return nextId;
    }

    private QueryPanel createQueryPanel() {
        String bidirectionalHelpDialogTitle = String.format("%s - Info", QueryPanelDemo.class.getSimpleName());

        List<Field> entityClassFields = fieldRetriever.retrieveRelevantFields(entityClass);
        Set<Field> mappedFieldCandidates = QueryPanel.retrieveMappedFieldCandidates(entityClass,
                        entityClassFields);
        BidirectionalControlPanel bidirectionalControlPanel = new BidirectionalControlPanel(entityClass,
                bidirectionalHelpDialogTitle,
                QueryPanel.retrieveMappedByFieldPanel(entityClassFields),
                mappedFieldCandidates);
        FieldInitializer fieldInitializer = new ReflectionFieldInitializer(fieldRetriever);
        File entryStorageFile;
        try {
            entryStorageFile = File.createTempFile(QueryPanelDemo.class.getSimpleName(),
                    null);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        QueryHistoryEntryStorageFactory entryStorageFactory = new XMLFileQueryHistoryEntryStorageFactory(entryStorageFile,
                getEntityClasses(),
                false,
                getIssueHandler());
        QueryHistoryEntryStorage entryStorage;
        try {
            entryStorage = entryStorageFactory.create();
        } catch (QueryHistoryEntryStorageCreationException ex) {
            throw new RuntimeException(ex);
        }
        try {
            return new QueryPanel<>(getStorage(),
                    entityClass,
                    issueHandler,
                    fieldRetriever,
                    null, //initialValue
                    bidirectionalControlPanel,
                    ListSelectionModel.SINGLE_SELECTION,
                    fieldInitializer,
                    entryStorage);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
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

        createAButton = new javax.swing.JButton();
        createBButton = new javax.swing.JButton();
        createCButton = new javax.swing.JButton();
        queryPanel = createQueryPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        createAButton.setText("Create new A (references B)");
        createAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAButtonActionPerformed(evt);
            }
        });

        createBButton.setText("Create new B");
        createBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBButtonActionPerformed(evt);
            }
        });

        createCButton.setText("Create new C (extends A)");
        createCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCButtonActionPerformed(evt);
            }
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
    }// </editor-fold>//GEN-END:initComponents

    private void createAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAButtonActionPerformed
        Long nextId0 = getNextId();
        EntityA newA = new EntityA(nextId0, RANDOM.nextInt(), String.valueOf(RANDOM.nextInt()));
        try {
            getStorage().store(newA);
        } catch (StorageException ex) {
            throw new RuntimeException(ex);
        }
        LOGGER.info("Create and persisted new instance of {}", EntityA.class.getName());
    }//GEN-LAST:event_createAButtonActionPerformed

    private void createBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBButtonActionPerformed
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
            throw new RuntimeException(ex);
        }
        LOGGER.info("Create and persisted new instance of {}", EntityB.class.getName());
    }//GEN-LAST:event_createBButtonActionPerformed

    private void createCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCButtonActionPerformed
        Long nextId0 = getNextId();
        EntityC newC = new EntityC(nextId0, RANDOM.nextInt(), String.valueOf(RANDOM.nextInt()), String.valueOf(RANDOM.nextInt()));
        try {
            getStorage().store(newC);
        } catch (StorageException ex) {
            throw new RuntimeException(ex);
        }
        LOGGER.info("Create and persisted new instance of {}", EntityC.class.getName());
    }//GEN-LAST:event_createCButtonActionPerformed

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

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new QueryPanelDemo().setVisible(true);
                } catch (SQLException | IOException | StorageException | StorageCreationException | StorageConfValidationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createAButton;
    private javax.swing.JButton createBButton;
    private javax.swing.JButton createCButton;
    private richtercloud.reflection.form.builder.jpa.panels.QueryPanel<EntityA> queryPanel;
    // End of variables declaration//GEN-END:variables
}
