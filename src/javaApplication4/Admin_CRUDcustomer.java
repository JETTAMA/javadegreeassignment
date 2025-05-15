package javaApplication4;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;
import classes.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.JOptionPane;

public class Admin_CRUDcustomer extends javax.swing.JFrame {

    private FileManager fm;
    private String userFile = "src/resources/users.txt";
    private Transaction latestTransaction;
    public Admin_CRUDcustomer() {
        initComponents();
        this.fm = new FileManager();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        customerCRUDbutton = new javax.swing.JButton();
        vendorCRUDbutton = new javax.swing.JButton();
        runnerCRUDbutton = new javax.swing.JButton();
        notificationsbutton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        updateButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        topupButton = new javax.swing.JButton();
        walletField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        topupamountField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        idField = new javax.swing.JTextField();
        deleteButton = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        jMenu1.setText("jMenu1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "UserID", "Role", "Full Name", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        customerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(customerTable);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 48, 73));
        jLabel1.setText("Customers");

        jPanel3.setBackground(new java.awt.Color(252, 191, 73));
        jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel3.setForeground(new java.awt.Color(0, 48, 73));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/burger.png"))); // NOI18N

        logoutButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(0, 48, 73));
        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        customerCRUDbutton.setBackground(new java.awt.Color(247, 127, 0));
        customerCRUDbutton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        customerCRUDbutton.setForeground(new java.awt.Color(0, 48, 73));
        customerCRUDbutton.setText("Customer Management");
        customerCRUDbutton.setBorderPainted(false);
        customerCRUDbutton.setMinimumSize(new java.awt.Dimension(148, 23));
        customerCRUDbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerCRUDbuttonActionPerformed(evt);
            }
        });

        vendorCRUDbutton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        vendorCRUDbutton.setForeground(new java.awt.Color(0, 48, 73));
        vendorCRUDbutton.setText("Vendor Management");
        vendorCRUDbutton.setBorderPainted(false);
        vendorCRUDbutton.setMinimumSize(null);
        vendorCRUDbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendorCRUDbuttonActionPerformed(evt);
            }
        });

        runnerCRUDbutton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        runnerCRUDbutton.setForeground(new java.awt.Color(0, 48, 73));
        runnerCRUDbutton.setText("Runner Management");
        runnerCRUDbutton.setBorderPainted(false);
        runnerCRUDbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runnerCRUDbuttonActionPerformed(evt);
            }
        });

        notificationsbutton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        notificationsbutton.setForeground(new java.awt.Color(0, 48, 73));
        notificationsbutton.setText("Notifications");
        notificationsbutton.setBorderPainted(false);
        notificationsbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificationsbuttonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("ADMIN");

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 48, 73));
        jButton6.setText("User Registration");
        jButton6.setBorderPainted(false);
        jButton6.setMinimumSize(new java.awt.Dimension(148, 23));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vendorCRUDbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(runnerCRUDbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customerCRUDbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(notificationsbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(customerCRUDbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(vendorCRUDbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(runnerCRUDbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(notificationsbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        jPanel4.setBackground(new java.awt.Color(252, 191, 73));

        label1.setBackground(new java.awt.Color(252, 191, 73));
        label1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        label1.setForeground(new java.awt.Color(0, 48, 73));
        label1.setText("Customer Registration Approval");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(387, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 153, 0));
        jLabel4.setText("UserID");
        jLabel4.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 153, 0));
        jLabel2.setText("Name");
        jLabel2.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 0));
        jLabel6.setText("Email");
        jLabel6.setToolTipText("");

        emailField.setEditable(false);
        emailField.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        emailField.setEnabled(false);
        emailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailFieldActionPerformed(evt);
            }
        });

        updateButton.setBackground(new java.awt.Color(255, 153, 0));
        updateButton.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 153, 0));
        jLabel7.setText("Wallet");
        jLabel7.setToolTipText("");

        topupButton.setBackground(new java.awt.Color(255, 153, 0));
        topupButton.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        topupButton.setText("Top-up");
        topupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topupButtonActionPerformed(evt);
            }
        });

        walletField.setEditable(false);
        walletField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        walletField.setBorder(null);
        walletField.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        walletField.setEnabled(false);
        walletField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                walletFieldActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 153, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Top-up Amount");
        jLabel8.setToolTipText("");

        topupamountField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        topupamountField.setBorder(null);
        topupamountField.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        topupamountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topupamountFieldActionPerformed(evt);
            }
        });

        nameField.setEditable(false);
        nameField.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        nameField.setEnabled(false);
        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        idField.setEditable(false);
        idField.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        idField.setEnabled(false);

        deleteButton.setBackground(new java.awt.Color(255, 0, 0));
        deleteButton.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(topupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(emailField)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(walletField)
                                .addComponent(topupamountField)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                .addComponent(nameField)
                                .addComponent(idField))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(3, 3, 3)
                .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(3, 3, 3)
                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(deleteButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(walletField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(topupamountField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(topupButton)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(205, 205, 205)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void customerCRUDbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerCRUDbuttonActionPerformed
        this.dispose();
        Admin_CRUDcustomer newDashboard = new Admin_CRUDcustomer();
        newDashboard.setVisible(true);

    }//GEN-LAST:event_customerCRUDbuttonActionPerformed

    private void vendorCRUDbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendorCRUDbuttonActionPerformed
        // Close the current Admin_Dashboard
        this.dispose();

        Admin_CRUDvendor newDashboard = new Admin_CRUDvendor();
        newDashboard.setVisible(true);
    }//GEN-LAST:event_vendorCRUDbuttonActionPerformed

    private void runnerCRUDbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runnerCRUDbuttonActionPerformed
        // Close the current Admin_Dashboard
        this.dispose();
        Admin_CRUDrunner newDashboard = new Admin_CRUDrunner();
        newDashboard.setVisible(true);
    }//GEN-LAST:event_runnerCRUDbuttonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        OpeningPage opnpg = new OpeningPage();
        opnpg.setVisible(true);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void notificationsbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificationsbuttonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        Admin_Notifications admn = new Admin_Notifications();
        admn.setVisible(true);

    }//GEN-LAST:event_notificationsbuttonActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.dispose(); // Close the Register form

        // Re-open the AdminDashboard
        Admin_Dashboard adminDashboard = new Admin_Dashboard();
        adminDashboard.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed
    
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

//        loadUsersIntoTable();
        // Fetch the populated table model
        Controller controller = new Controller();
        DefaultTableModel populatedModel = controller.populateUsersTableModel(User.Role.Customer);
        customerTable.setModel(populatedModel);
        refreshTable();
    }//GEN-LAST:event_formWindowOpened
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
            // Clear existing rows
            model.setRowCount(0);

            File f = new File(userFile);
            if (!f.exists()) {
                System.out.println("File not found: " + userFile);
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 5 && data[4].equals("Customer")){
                        model.addRow(new Object[]{data[0], data[4], data[2], data[3]});
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    private void clearFields(){
        refreshTable();
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        walletField.setText("");
        topupamountField.setText("");
        walletField.setText("");
    }
    private void emailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailFieldActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        
        try{
            int row;
            row = customerTable.getSelectedRow();
            String id = customerTable.getValueAt(row, 0).toString();
            if ("Update".equals(updateButton.getText())){
            idField.setEnabled(true);
            idField.setEditable(true);
            nameField.setEnabled(true);
            nameField.setEditable(true);
            emailField.setEnabled(true);
            emailField.setEditable(true);
            updateButton.setText("Save");

        }else{
            idField.setEnabled(false);
            idField.setEditable(false);
            nameField.setEnabled(false);
            nameField.setEditable(false);
            emailField.setEnabled(false);
            emailField.setEditable(false);
            updateButton.setText("Update");
//            
            String newid = idField.getText();
            String name = nameField.getText();
            String email=emailField.getText();

            try{
                Admin admin = new Admin();
                admin.updateUser(id, newid, name, email);
                JOptionPane.showMessageDialog(this, "Profile is updated.");
                clearFields();
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Failed to update profile: " + e.getMessage());
            }

        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Please select a row.");
        }
        
        

    }//GEN-LAST:event_updateButtonActionPerformed

   
    
    
    
    private void topupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topupButtonActionPerformed
        // TODO add your handling code here:
        try{
            double topupAmount = Double.parseDouble(topupamountField.getText());

            // Fetch the customer by ID
            Customer customer = (Customer) fm.getUserById(idField.getText());  // Cast to Customer
            // Perform wallet top-up (will update wallet and transaction file)
            Admin admin = new Admin();
            boolean success = admin.topUpCustomerWallet(customer, topupAmount);
            
//          writing the notification
            this.latestTransaction = customer.getWallet().getTransactions().get(customer.getWallet().getTransactions().size() - 1);
            
            if (success) {
                JOptionPane.showMessageDialog(this, customer.getUserId() + "'s wallet has been topped up.");
                
                LocalDateTime datetime= latestTransaction.getDateTime();
                LocalDate date = datetime.toLocalDate();
                LocalTime time = datetime.toLocalTime();
                String textBlock = """

        Do you want to send transactional receipt?

        Transaction Receipt

        Transaction ID: """ + latestTransaction.getTransactionId() + """
        
        User ID: """ + latestTransaction.getUserId() + """
        
        Amount: RM """ + latestTransaction.getAmount() + """
        
        Type: """ + latestTransaction.getType() + """
        
        Date: """ + date + """
        
        Time: """ + time + """
        
        Status: """ + latestTransaction.getStatus() + """
        """;
                int confirm = JOptionPane.showConfirmDialog(this,textBlock,                                                                
                            "Send Receipt", 
                            JOptionPane.YES_NO_OPTION);
                walletField.setText(String.valueOf(fm.getWalletAmountByUserId(idField.getText())));
                if (confirm == JOptionPane.YES_OPTION) {
                    admin.sendReceipt(customer, latestTransaction, latestTransaction.toString());
                    JOptionPane.showMessageDialog(this,"Transaction Receipt sent to "+ customer.getUserId());
                }else{
                    JOptionPane.showMessageDialog(this,"No receipt sent");
                }
                
                topupamountField.setText("");
            } else {
                JOptionPane.showMessageDialog(this,"Failed to top up wallet for customer ID:" + idField.getText()+"\n Please input a valid number.");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Please input a valid amount");
            topupamountField.setText("");
        }
        
    }//GEN-LAST:event_topupButtonActionPerformed

    private void walletFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_walletFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_walletFieldActionPerformed

    private void topupamountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topupamountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_topupamountFieldActionPerformed

    private void customerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
        int index = customerTable.getSelectedRow();
        int selectedRow = customerTable.getSelectedRow();
        String userId = (String) model.getValueAt(index, 0);
        String role = (String) model.getValueAt(index, 1);
        String name = (String) model.getValueAt(index, 2);
        String email = (String) model.getValueAt(index, 3);

        idField.setText(userId);
        nameField.setText(name);
        emailField.setText(email);
        walletField.setText(String.valueOf(fm.getWalletAmountByUserId(userId)));
    }//GEN-LAST:event_customerTableMouseClicked

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
            // Get the selected row from the table
        int selectedRow = customerTable.getSelectedRow();

        // Check if a row is actually selected
        if (selectedRow >= 0) {
            // Confirm the deletion with the user
            int confirm = JOptionPane.showConfirmDialog(this, 
                            "Are you sure you want to delete this user?", 
                            "Delete User", 
                            JOptionPane.YES_NO_OPTION);

            // If the user confirms the deletion
            if (confirm == JOptionPane.YES_OPTION) {
                // Get the User ID from the selected row
                String userId = (String) customerTable.getValueAt(selectedRow, 0);

                Admin admin = new Admin();

                // Remove the user with the matching ID
                boolean userRemoved = admin.deleteUser(userId);

                // If a user was successfully removed
                if (userRemoved) {

                    // Remove the row from the table model
                    ((DefaultTableModel) customerTable.getModel()).removeRow(selectedRow);

                    // Notify the user of the successful deletion
                    JOptionPane.showMessageDialog(this, "User deleted successfully.");
                    clearFields();
                } else {
                    // Notify the user if the user ID was not found
                    JOptionPane.showMessageDialog(this, "Error: User ID not found.");
                }
            }
        } else {
            // Notify the user if no row is selected
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_CRUDcustomer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton customerCRUDbutton;
    private javax.swing.JTable customerTable;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField emailField;
    private javax.swing.JTextField idField;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label label1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton notificationsbutton;
    private javax.swing.JButton runnerCRUDbutton;
    private javax.swing.JButton topupButton;
    private javax.swing.JTextField topupamountField;
    private javax.swing.JButton updateButton;
    private javax.swing.JButton vendorCRUDbutton;
    private javax.swing.JTextField walletField;
    // End of variables declaration//GEN-END:variables
}
