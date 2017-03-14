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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import richtercloud.message.handler.IssueHandler;
import richtercloud.message.handler.LoggerIssueHandler;
import richtercloud.reflection.form.builder.ReflectionFormBuilder;
import richtercloud.reflection.form.builder.ReflectionFormPanel;
import richtercloud.reflection.form.builder.TransformationException;
import richtercloud.reflection.form.builder.fieldhandler.FieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.FieldUpdateEvent;
import richtercloud.reflection.form.builder.fieldhandler.FieldUpdateListener;
import richtercloud.reflection.form.builder.fieldhandler.IntegerListFieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.MappingFieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.SimpleEntityListFieldHandler;
import richtercloud.reflection.form.builder.fieldhandler.factory.MappingFieldHandlerFactory;
import richtercloud.reflection.form.builder.jpa.JPACachedFieldRetriever;

/**
 *
 * @author richter
 */
public class ReflectionFormBuilderDemo extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = LoggerFactory.getLogger(ReflectionFormBuilderDemo.class);
    private ReflectionFormPanel reflectionPanel;
    private final IssueHandler issueHandler = new LoggerIssueHandler(LOGGER);

    /**
     * Creates new form ReflectionFormBuilderDemo
     */
    public ReflectionFormBuilderDemo() throws TransformationException, NoSuchFieldException {
        this.initComponents();
        MappingFieldHandlerFactory fieldHandlerFactory = new MappingFieldHandlerFactory(issueHandler);
        Map<java.lang.reflect.Type, FieldHandler<?,?,?, ?>> classMapping = fieldHandlerFactory.generateClassMapping();
        classMapping.put(EntityA.class.getDeclaredField("elementCollectionBasics").getGenericType(), new IntegerListFieldHandler(issueHandler));
        classMapping.put(EntityA.class.getDeclaredField("oneToManyEntityBs").getGenericType(), new SimpleEntityListFieldHandler(issueHandler));
        classMapping.put(EntityB.class, new FieldHandler<Boolean,FieldUpdateEvent<Boolean>, ReflectionFormBuilder, Component>() {
            @Override
            public JComponent handle(Field field,
                    Object instance,
                    final FieldUpdateListener<FieldUpdateEvent<Boolean>> updateListener,
                    ReflectionFormBuilder reflectionFormBuilder) {
                final JCheckBox retValue = new JCheckBox("This checkbox represents an EntityB!");
                retValue.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        updateListener.onUpdate(new FieldUpdateEvent<>(retValue.isSelected()));
                    }
                });
                return retValue;
            }

            @Override
            public void reset(Component component) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        FieldHandler fieldHandler = new MappingFieldHandler(classMapping,
                fieldHandlerFactory.generatePrimitiveMapping());
        ReflectionFormBuilder reflectionFormBuilder = new ReflectionFormBuilder(
                "Field description",
                issueHandler,
                new JPACachedFieldRetriever());
        reflectionPanel = reflectionFormBuilder.transformEntityClass(EntityA.class,
                null, //entityToUpdate
                fieldHandler
        );
        BoxLayout mainPanelLayout = new BoxLayout(this.mainPanel, BoxLayout.X_AXIS);
        this.mainPanel.setLayout(mainPanelLayout);
        this.mainPanel.add(reflectionPanel);
        this.mainPanel.validate();
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
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
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
        displayInstanceInfoDialog(this, instance);
    }//GEN-LAST:event_displayButtonActionPerformed

    public static void displayInstanceInfoDialog(Frame owner, Object instance) {
        JDialog displayDialog = new JDialog(owner,
                String.format("Info - %s", ReflectionFormBuilderDemo.class.getSimpleName()), //title
                true //modal
        );
        displayDialog.getContentPane().setLayout(new BorderLayout());
        displayDialog.getContentPane().add(new JLabel(String.format("<html>%s</html>", instance.toString()) //message
        ));
        displayDialog.setPreferredSize(new Dimension(500, 300));
        displayDialog.pack();
        displayDialog.setVisible(true);
    }

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
                    new ReflectionFormBuilderDemo().setVisible(true);
                } catch (TransformationException | NoSuchFieldException ex) {
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
