/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import calculos.CalcularProcesos;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import objetos.Proceso;

/**
 *
 * @author krawz
 */
public class VistaProcesos extends javax.swing.JFrame {

    List<Proceso> procesos;
    int ultimoValor = 1;

    /**
     * Creates new form ViewProcesos
     */
    public VistaProcesos() {
        initComponents();
        agregarProceso(1);
        agregarProceso(2);
        procesos = new ArrayList<>();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSpinnerNumProceso = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerQuantum = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setAutoscrolls(true);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));
        scrollPane.setViewportView(jPanel1);

        jLabel1.setText("Número de procesos:");

        jSpinnerNumProceso.setModel(new javax.swing.SpinnerNumberModel(2, 2, 100, 1));
        jSpinnerNumProceso.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerNumProcesoStateChanged(evt);
            }
        });

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Resolver procesos con los algoritmos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Quantums de tiempo:");

        jSpinnerQuantum.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSpinnerNumProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinnerQuantum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jSpinnerNumProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jSpinnerQuantum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 239, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(scrollPane))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!camposCorrectosJPanel(jPanel1)) {
            JOptionPane.showMessageDialog(null, "Llene todos los campos requeridos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            procesos.clear();
            obtenerDatosJPanel(jPanel1);
            if (numeroRepetido()) {
                JOptionPane.showMessageDialog(null, "ERROR: Dos procesos no pueden llegar en el mismo tiempo. Verifique sus datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                procesos.forEach((ps) -> {
                    System.out.println(ps.toString());
                });

                new CalcularProcesos(procesos, (int) jSpinnerQuantum.getValue());
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jSpinnerNumProcesoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerNumProcesoStateChanged
        int cantidadProcesos = (int) jSpinnerNumProceso.getValue();
        //scrollPane.getViewport().removeAll();
        if (cantidadProcesos > 0) {
            if (cantidadProcesos > ultimoValor) {
                agregarProceso(cantidadProcesos);
            } else {
                quitarProceso(cantidadProcesos);
            }
        }
        ultimoValor = cantidadProcesos;
    }//GEN-LAST:event_jSpinnerNumProcesoStateChanged

    private void agregarProceso(int proceso) {
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(0, 2);
        panel.setLayout(layout);
        JLabel titulo = new JLabel("Proceso: ");
        JLabel numero = new JLabel(String.valueOf(proceso));
        Font font = new JLabel().getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        titulo.setFont(boldFont);
        numero.setFont(boldFont);
        panel.add(titulo);
        panel.add(numero);
        panel.add(new JLabel("Nombre: "));
        panel.add(cuadroTexto("Nombre", false));
        panel.add(new JLabel("Prioridad: "));
        panel.add(cuadroTexto("Prioridad", true));
        panel.add(new JLabel("Tiempo de llegada: "));
        panel.add(cuadroTexto("TiempoLlegada", true));
        panel.add(new JLabel("Rafaga de CPU: "));
        panel.add(cuadroTexto("RafagaCPU", true));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        jPanel1.add(panel);
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private void quitarProceso(int proceso) {
        jPanel1.remove(proceso);
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private JTextField cuadroTexto(String nombre, boolean numerico) {
        JTextField textField = new JTextField();
        if (numerico) {
            textField = cuadroTextoNumerico();
        }
        textField.setName(nombre);
        return textField;
    }

    private JTextField cuadroTextoNumerico() {
        JTextField textField = new JTextField();
        textField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent evt) {
                char numero = evt.getKeyChar();
                if (!Character.isDigit(numero) && numero != KeyEvent.VK_BACK_SPACE && numero != KeyEvent.VK_DELETE) {
                    evt.consume();
                    getToolkit().beep();
                }
            }
        });

        return textField;
    }

    boolean camposCorrectosJPanel(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JPanel) {
                if (!camposCorrectosJTextField((Container) component)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean camposCorrectosJTextField(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                switch (component.getName()) {
                    case "Nombre":
                        if (((JTextComponent) component).getText().isEmpty()) {
                            return false;
                        }
                        break;
                    case "TiempoLlegada":
                        if (((JTextComponent) component).getText().isEmpty()) {
                            return false;
                        }
                        break;
                    case "RafagaCPU":
                        if (((JTextComponent) component).getText().isEmpty()) {
                            return false;
                        }
                        break;
                }
            }
        }
        return true;
    }

    private void obtenerDatosJPanel(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JPanel) {
                obtenerDatosJTextField((Container) component);
            }
        }
    }

    private void obtenerDatosJTextField(Container container) {
        String nombre = null;
        int prioridad = 0;
        int tiempoLlegada = 0;
        int rafagaCPU = 0;
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                switch (component.getName()) {
                    case "Nombre":
                        nombre = ((JTextComponent) component).getText();
                        break;
                    case "Prioridad":
                        if (((JTextComponent) component).getText().isEmpty()) {
                            ((JTextComponent) component).setText("0");
                        }
                        prioridad = Integer.valueOf(((JTextComponent) component).getText());
                        break;
                    case "TiempoLlegada":
                        tiempoLlegada = Integer.valueOf(((JTextComponent) component).getText());
                        break;
                    case "RafagaCPU":
                        rafagaCPU = Integer.valueOf(((JTextComponent) component).getText());
                        break;
                }
                System.out.println(component.getName());
            }
        }
        procesos.add(new Proceso(procesos.size(), nombre, prioridad, tiempoLlegada, rafagaCPU));
    }

    boolean numeroRepetido() {
        List<Integer> numeros = new ArrayList<>();
        for (Proceso ps : procesos) {
            if (numeros.isEmpty()) {
                numeros.add(ps.getTiempoLlegada());
            } else {
                if (numeros.stream().anyMatch((i) -> (i == ps.getTiempoLlegada()))) {
                    return true;
                }
                numeros.add(ps.getTiempoLlegada());
            }
        }
        return false;
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaProcesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaProcesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaProcesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaProcesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaProcesos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSpinnerNumProceso;
    private javax.swing.JSpinner jSpinnerQuantum;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}