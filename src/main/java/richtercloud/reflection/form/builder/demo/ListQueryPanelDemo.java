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

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import richtercloud.reflection.form.builder.ClassAnnotationHandler;
import richtercloud.reflection.form.builder.FieldAnnotationHandler;
import richtercloud.reflection.form.builder.FieldUpdateEvent;
import richtercloud.reflection.form.builder.ReflectionFormBuilder;
import richtercloud.reflection.form.builder.ReflectionFormPanel;
import richtercloud.reflection.form.builder.jpa.panels.QueryListPanel;

/**
 *
 * @author richter
 */
public class ListQueryPanelDemo extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;
    private EntityManager entityManager;
    private final static Logger LOGGER = LoggerFactory.getLogger(QueryPanelDemo.class);
    private final static Random RANDOM = new Random();
    private ReflectionFormBuilder reflectionFormBuilder;
    private static Long nextId = 1L;
    private Class<?> entityClass = EntityA.class;
    private List<Object> initialValues = new LinkedList<>();

    /**
     * Creates new form ListQueryPanelDemo
     */
    public ListQueryPanelDemo() {
        this.entityManager = QueryPanelDemo.ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Pair<Class<? extends Annotation>, FieldAnnotationHandler>> fieldAnnotationMapping = new LinkedList<>();
        List<Pair<Class<? extends Annotation>, ClassAnnotationHandler<Object,FieldUpdateEvent<Object>>>> classAnnotationMapping = new LinkedList<>();
        this.reflectionFormBuilder = new ReflectionFormBuilder(fieldAnnotationMapping, classAnnotationMapping);
        try {
            this.initComponents();
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            JOptionPane.showMessageDialog(this, //parent
                    String.format("The following unexpected exception occured during intialization of the query panel: %s", ReflectionFormPanel.generateExceptionMessage(ex)),
                    null, WIDTH);
        }
    }

    private static synchronized Long getNextId() {
        nextId += 1;
        return nextId;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() throws IllegalArgumentException, IllegalAccessException {

        listQueryPanel = new QueryListPanel(entityManager, reflectionFormBuilder, entityClass, this.initialValues)
        ;
        createAButton = new javax.swing.JButton();
        createCButton = new javax.swing.JButton();
        createBButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 500, 300));

        createAButton.setText("Create new A (references B)");
        createAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAButtonActionPerformed(evt);
            }
        });

        createCButton.setText("Create new C (extends A)");
        createCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCButtonActionPerformed(evt);
            }
        });

        createBButton.setText("Create new B");
        createBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listQueryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(createCButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(createBButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(createAButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(listQueryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
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

    private void createCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCButtonActionPerformed
        Long nextId0 = getNextId();
        EntityC newC = new EntityC(nextId0, RANDOM.nextInt(), String.valueOf(RANDOM.nextInt()), String.valueOf(RANDOM.nextInt()));
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(newC);
        this.entityManager.getTransaction().commit();
        LOGGER.info("Create and persisted new instance of {}", EntityC.class.getName());
    }//GEN-LAST:event_createCButtonActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListQueryPanelDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListQueryPanelDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListQueryPanelDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListQueryPanelDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListQueryPanelDemo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createAButton;
    private javax.swing.JButton createBButton;
    private javax.swing.JButton createCButton;
    private richtercloud.reflection.form.builder.jpa.panels.QueryListPanel listQueryPanel;
    // End of variables declaration//GEN-END:variables
}
