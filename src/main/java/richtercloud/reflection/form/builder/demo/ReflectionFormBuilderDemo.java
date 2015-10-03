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
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import org.apache.commons.lang3.tuple.Pair;
import richtercloud.reflection.form.builder.ClassAnnotationHandler;
import richtercloud.reflection.form.builder.FieldAnnotationHandler;
import richtercloud.reflection.form.builder.FieldHandler;
import richtercloud.reflection.form.builder.SimpleEntityListFieldHandler;
import richtercloud.reflection.form.builder.IntegerListFieldHandler;
import richtercloud.reflection.form.builder.ReflectionFormBuilder;
import richtercloud.reflection.form.builder.ReflectionFormPanel;
import richtercloud.reflection.form.builder.FieldUpdateEvent;
import richtercloud.reflection.form.builder.FieldUpdateListener;
import richtercloud.reflection.form.builder.jpa.JPAReflectionFormBuilder;
import richtercloud.reflection.form.builder.retriever.ValueRetriever;

/**
 *
 * @author richter
 */
public class ReflectionFormBuilderDemo extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private ReflectionFormPanel reflectionPanel;

    /**
     * Creates new form ReflectionFormBuilderDemo
     */
    public ReflectionFormBuilderDemo() {
        this.initComponents();
        try {
            List<Pair<Class<? extends Annotation>, FieldAnnotationHandler>> fieldAnnotationMapping = new LinkedList<>(ReflectionFormBuilder.FIELD_ANNOTATION_MAPPING_DEFAULT);
            List<Pair<Class<? extends Annotation>, ClassAnnotationHandler<Object,FieldUpdateEvent<Object>>>> classAnnotationMapping = new LinkedList<>(ReflectionFormBuilder.CLASS_ANNOTATION_MAPPING_DEFAULT);
            Map<java.lang.reflect.Type, FieldHandler<?,?>> classMapping = new HashMap<>(JPAReflectionFormBuilder.CLASS_MAPPING_DEFAULT);
            classMapping.put(EntityA.class.getDeclaredField("cs").getGenericType(), IntegerListFieldHandler.getInstance());
            classMapping.put(EntityA.class.getDeclaredField("entityBs").getGenericType(), SimpleEntityListFieldHandler.getInstance());
            classMapping.put(EntityB.class, new FieldHandler<Boolean,FieldUpdateEvent<Boolean>>() {
                @Override
                public JComponent handle(java.lang.reflect.Type type,
                        Boolean fieldValue,
                        final FieldUpdateListener<FieldUpdateEvent<Boolean>> updateListener,
                        ReflectionFormBuilder reflectionFormBuilder) {
                    final JCheckBox retValue = new JCheckBox("This checkbox represents an EntityB!");
                    retValue.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            updateListener.onUpdate(new FieldUpdateEvent<Boolean>() {
                                @Override
                                public Boolean getNewValue() {
                                    return retValue.isSelected();
                                }
                            });
                        }
                    });
                    return retValue;
                }
            });
            Map<Class<?>, FieldHandler<?,?>> primitiveMapping = new HashMap<>(ReflectionFormBuilder.PRIMITIVE_MAPPING_DEFAULT);
            Map<Class<? extends JComponent>, ValueRetriever<?, ?>> valueRetrieverMapping = new HashMap<>(ReflectionFormBuilder.VALUE_RETRIEVER_MAPPING_DEFAULT);
            ReflectionFormBuilder reflectionFormBuilder = new ReflectionFormBuilder(classMapping,
                    primitiveMapping,
                    valueRetrieverMapping,
                    fieldAnnotationMapping,
                    classAnnotationMapping,
                    new HashSet<java.lang.reflect.Type>(),
                    new HashSet<java.lang.reflect.Type>(),
                    new HashSet<java.lang.reflect.Type>());
            reflectionPanel = reflectionFormBuilder.transform(EntityA.class,
                    null //entityToUpdate
            );
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
        try {
            Object instance = this.reflectionPanel.retrieveInstance();
            displayInstanceInfoDialog(this, instance);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
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
                new ReflectionFormBuilderDemo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton displayButton;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
