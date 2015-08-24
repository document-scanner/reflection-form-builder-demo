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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import richtercloud.reflection.form.builder.ReflectionFormBuilder;
import richtercloud.reflection.form.builder.components.OCRResultPanelRetriever;
import richtercloud.reflection.form.builder.components.ScanResultPanelRetriever;

/**
 *
 * @author richter
 */
public class ReflectionFormBuilderDemo extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;
    private final static EntityManagerFactory ENTITY_MANAGER_FACTORY;
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
                    if(ReflectionFormBuilderDemo.CONNECTION != null) {
                        try {
                            ReflectionFormBuilderDemo.CONNECTION.close();
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
    private EntityManager entityManager;

    /**
     * Creates new form ReflectionFormBuilderDemo
     */
    public ReflectionFormBuilderDemo() {
        this.initComponents();
        try {
            this.entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        OCRResultPanelRetriever oCRResultPanelRetriever = new OCRResultPanelRetriever() {
            @Override
            public String retrieve() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        ScanResultPanelRetriever scanResultPanelRetriever = new ScanResultPanelRetriever() {
            @Override
            public byte[] retrieve() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
            ReflectionFormBuilder<EntityA> reflectionFormBuilder = new ReflectionFormBuilder<>(oCRResultPanelRetriever, scanResultPanelRetriever);
            JPanel reflectionPanel = reflectionFormBuilder.transform(EntityA.class);
            BoxLayout mainPanelLayout = new BoxLayout(this.mainPanel, BoxLayout.X_AXIS);
            this.mainPanel.setLayout(mainPanelLayout);
            this.mainPanel.add(reflectionPanel);
            this.mainPanel.validate();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                new ReflectionFormBuilderDemo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
