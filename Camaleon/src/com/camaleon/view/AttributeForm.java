/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.view;

import com.camaleon.entities.Attribute;
import com.camaleon.entities.AttributeDataType;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class AttributeForm extends javax.swing.JFrame {
    
    App padre = null;
    Attribute attribute = null;
    Set<String> attributeKeys=null;

    /**
     * Creates new form AttributeForm
     * @param parent
     * @param attribute
     * @param attributeKeys
     */
    public AttributeForm(javax.swing.JFrame parent, Attribute attribute, Set<String> attributeKeys) {
        padre = (App) parent;
        this.attribute = attribute;
        this.attributeKeys = attributeKeys;
        initComponents();      
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        txtLength.setVisible(false);
        lblLength.setVisible(false);
        txtScale.setVisible(false);
        lblScale.setVisible(false);
        selTipo.setModel(new DefaultComboBoxModel(AttributeDataType.values()));
        if(attribute!=null){
            txtKey.setText(this.attribute.getKey());
            txtKey.setEnabled(false);
            txtName.setText(this.attribute.getName());
            selTipo.setSelectedIndex(1);
            selTipo.setSelectedItem(this.attribute.getType());
            if(this.attribute.getLength()!=null){
                txtLength.setText(""+this.attribute.getLength());
            }
        }else{
            selTipo.setSelectedIndex(1);
            selTipo.setSelectedIndex(0);
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

        lblKey = new javax.swing.JPanel();
        selTipo = new javax.swing.JComboBox<>();
        lblType = new javax.swing.JLabel();
        btnGuardarAtributo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtKey = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        lblLength = new javax.swing.JLabel();
        txtLength = new javax.swing.JTextField();
        lblScale = new javax.swing.JLabel();
        txtScale = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Atributo");

        selTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selTipoItemStateChanged(evt);
            }
        });

        lblType.setText("Tipo:");

        btnGuardarAtributo.setText("Guardar Atributo");
        btnGuardarAtributo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarAtributoActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Llave:");

        lblName.setText("Nombre:");

        lblLength.setText("Longitud:");

        lblScale.setText("Escala:");

        javax.swing.GroupLayout lblKeyLayout = new javax.swing.GroupLayout(lblKey);
        lblKey.setLayout(lblKeyLayout);
        lblKeyLayout.setHorizontalGroup(
            lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblKeyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblKeyLayout.createSequentialGroup()
                        .addGroup(lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblKeyLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(14, 14, 14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblType)
                                .addComponent(lblName)
                                .addComponent(lblLength)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtKey, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtLength, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblKeyLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardarAtributo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addGroup(lblKeyLayout.createSequentialGroup()
                        .addComponent(lblScale)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(txtScale, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        lblKeyLayout.setVerticalGroup(
            lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblKeyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblType)
                    .addComponent(selTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLength)
                    .addComponent(txtLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblScale)
                    .addComponent(txtScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(lblKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardarAtributo))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblKey, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarAtributoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarAtributoActionPerformed
        String key = txtKey.getText();
        String name = txtName.getText();
        if(key.length()>0 && name.length()>0){
            boolean edit = this.attribute != null;
            boolean error=false;
            if(!edit && this.attributeKeys.contains(key)){
                JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent((Component) evt.getSource()), "La llave "+key+" ya esta siendo usada en la lista de atributos", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                Attribute newAttribute = new Attribute(key, name);
                AttributeDataType type =  (AttributeDataType) selTipo.getSelectedItem();
                newAttribute.setType(type);
                if(type.requiresLength()){
                    try {
                        long length = Long.parseLong(txtLength.getText());
                        newAttribute.setLength(length);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent((Component) evt.getSource()), "Debe ingresar un número válido en la longitud del campo", "Error", JOptionPane.ERROR_MESSAGE);
                        error=true;
                    }
                }
                if(type.requiresScale()){
                    try {
                        long scale = Long.parseLong(txtScale.getText());
                        newAttribute.setScale(scale);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent((Component) evt.getSource()), "Debe ingresar un número válido en la escala del campo", "Error", JOptionPane.ERROR_MESSAGE);
                        error=true;
                    }
                }
                if(!error){
                    this.dispose();
                    if(edit){
                        padre.editAttribute(this.attribute, newAttribute);
                    }else{
                        padre.addAttribute(newAttribute);
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent((Component) evt.getSource()), "Debe ingresar una llave y un nombre para el atributo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnGuardarAtributoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void selTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selTipoItemStateChanged
        if(selTipo.getSelectedIndex()>-1){
            AttributeDataType tipo = (AttributeDataType) selTipo.getSelectedItem();
            boolean requiresLength = tipo.requiresLength();
            boolean requiresScale = tipo.requiresScale();
            lblLength.setVisible(requiresLength);
            txtLength.setVisible(requiresLength);
            lblScale.setVisible(requiresScale);
            txtScale.setVisible(requiresScale);
        }
    }//GEN-LAST:event_selTipoItemStateChanged

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
            java.util.logging.Logger.getLogger(AttributeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AttributeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AttributeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AttributeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AttributeForm(null,null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarAtributo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel lblKey;
    private javax.swing.JLabel lblLength;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblScale;
    private javax.swing.JLabel lblType;
    private javax.swing.JComboBox<AttributeDataType> selTipo;
    private javax.swing.JTextField txtKey;
    private javax.swing.JTextField txtLength;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtScale;
    // End of variables declaration//GEN-END:variables
}