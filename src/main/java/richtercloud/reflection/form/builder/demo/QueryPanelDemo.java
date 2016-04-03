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
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import richtercloud.reflection.form.builder.ReflectionFormBuilder;
import richtercloud.reflection.form.builder.jpa.HistoryEntry;
import richtercloud.reflection.form.builder.jpa.JPACachedFieldRetriever;
import richtercloud.reflection.form.builder.jpa.panels.BidirectionalControlPanel;
import richtercloud.reflection.form.builder.jpa.panels.QueryPanel;
import richtercloud.reflection.form.builder.message.LoggerMessageHandler;
import richtercloud.reflection.form.builder.message.MessageHandler;

/**
 *
 * @author richter
 */
public class QueryPanelDemo extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;
    public final static EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private final static Connection CONNECTION;
    private final static String APP_NAME = "reflection-form-builder-demo";
    private final static Logger LOGGER = LoggerFactory.getLogger(QueryPanelDemo.class);
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
                    if(QueryPanelDemo.CONNECTION != null) {
                        try {
                            QueryPanelDemo.CONNECTION.close();
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
    private final EntityManager entityManager;
    private static Long nextId = 1L;
    private final static Random RANDOM = new Random();
    private ReflectionFormBuilder reflectionFormBuilder;
    private final static List<HistoryEntry> QUERY_PANEL_INITIAL_HISTORY = new LinkedList<>();
    static {
        QUERY_PANEL_INITIAL_HISTORY.add(new HistoryEntry("select a from EntityA a", 1, new Date()));
        QUERY_PANEL_INITIAL_HISTORY.add(new HistoryEntry("select b from EntityB b", 5, new Date()));
        QUERY_PANEL_INITIAL_HISTORY.add(new HistoryEntry("select c from EntityC c", 3, new Date()));
    }
    private final MessageHandler messageHandler = new LoggerMessageHandler(LOGGER);
    private final Class<?> entityClass = EntityA.class;

    /**
     * Creates new form Demo
     */
    public QueryPanelDemo() {
        this.entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        this.reflectionFormBuilder = new ReflectionFormBuilder("Field description",
                messageHandler,
                new JPACachedFieldRetriever());
        this.initComponents();
    }

    private static synchronized Long getNextId() {
        nextId += 1;
        return nextId;
    }

    private QueryPanel createQueryPanel() {
        String bidirectionalHelpDialogTitle = String.format("%s - Info", QueryPanelDemo.class.getSimpleName());

        List<Field> entityClassFields = reflectionFormBuilder.getFieldRetriever().retrieveRelevantFields(entityClass);
        Set<Field> mappedFieldCandidates = QueryPanel.retrieveMappedFieldCandidates(entityClass,
                        entityClassFields,
                        reflectionFormBuilder.getFieldRetriever());
        BidirectionalControlPanel bidirectionalControlPanel = new BidirectionalControlPanel(entityClass,
                bidirectionalHelpDialogTitle,
                QueryPanel.retrieveMappedByField(entityClassFields),
                mappedFieldCandidates);
        try {
            return new QueryPanel<>(this.entityManager,
                    entityClass,
                    messageHandler,
                    reflectionFormBuilder,
                    null, //initialValue
                    bidirectionalControlPanel);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
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
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(newA);
        this.entityManager.getTransaction().commit();
        LOGGER.info("Create and persisted new instance of {}", EntityA.class.getName());
    }//GEN-LAST:event_createAButtonActionPerformed

    private void createBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBButtonActionPerformed
        Long nextId0 = getNextId();
        CriteriaQuery<EntityA> criteriaQuery = this.entityManager.getCriteriaBuilder().createQuery(EntityA.class);
        Root<EntityA> queryRoot = criteriaQuery.from(EntityA.class);
        criteriaQuery.select(queryRoot);
        List<EntityA> as = this.entityManager.createQuery(criteriaQuery).getResultList();
        EntityA randomA = null;
        if(!as.isEmpty()) {
            randomA = as.get(RANDOM.nextInt(as.size()));
        }
        EntityB newB = new EntityB(nextId0, RANDOM.nextInt(), randomA);
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(newB);
        this.entityManager.getTransaction().commit();
        LOGGER.info("Create and persisted new instance of {}", EntityB.class.getName());
    }//GEN-LAST:event_createBButtonActionPerformed

    private void createCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCButtonActionPerformed
        Long nextId0 = getNextId();
        EntityC newC = new EntityC(nextId0, RANDOM.nextInt(), String.valueOf(RANDOM.nextInt()), String.valueOf(RANDOM.nextInt()));
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(newC);
        this.entityManager.getTransaction().commit();
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
                new QueryPanelDemo().setVisible(true);
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
