/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hotel_booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rieje
 */
public class booking_details_page extends javax.swing.JFrame {
    Connection con= javaconnect.connectdb();
    PreparedStatement ps = null;
    ResultSet rs=null;
    DefaultTableModel tbmodel = new DefaultTableModel();
    /**
     * Creates new form booking_details_page
     */
    public booking_details_page() {
        initComponents();
        javaconnect.connectdb();
        Select();
    }
    private void Select() {
        
    String sql = "SELECT name, contact, email, gender, checkin, checkout, pax, roomnum, roomtype, price,username, status FROM BOOKINGS ";   
    String[] columnNames = {"NAME", "CONTACT", "EMAIL", "GENDER", "CHECKIN", "CHECKOUT", "PAX", "ROOMNUM", "ROOMTYPE", "PRICE", "USERNAME", "STATUS"};
    tbmodel.setColumnIdentifiers(columnNames);
    tbmodel.setRowCount(0);

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            int x = 0;
            while (rs.next()) {
                String name = rs.getString("NAME");
                String contact = rs.getString("CONTACT");
                String email = rs.getString("EMAIL");
                String gender = rs.getString("GENDER");
                String checkin = rs.getString("CHECKIN");
                String checkout = rs.getString("CHECKOUT");
                String pax = rs.getString("PAX");
                String roomNum = rs.getString("ROOMNUM");
                String roomType = rs.getString("ROOMTYPE");
                String price = rs.getString("PRICE");
                String username = rs.getString("USERNAME");
                String status = rs.getString("STATUS");

                tbmodel.addRow(new Object[] {name, contact, email, gender, checkin, checkout, pax, roomNum, roomType, price,username, status});
                x++;
            }

            bookdetailstb.setModel(tbmodel);
            bookdetailstb.setVisible(x > 0);

            if (x == 0) {
                JOptionPane.showMessageDialog(this, "No records found.");
            }

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(this, "Error retrieving data: " + err.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
        }
    }
    private void searchBookingStatus() {
    String roomNumber = roomnum.getText().trim();
    String userName = username.getText().trim();
    String selectedStatus = (String) cb_stat.getSelectedItem();

    if (roomNumber.isEmpty() || userName.isEmpty() || selectedStatus == null || selectedStatus.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Room number, username, and status are required.");
        return;
    }

    String sql = "SELECT name, contact, email, gender, checkin, checkout, pax, roomnum, roomtype, price, username, status " +
                 "FROM BOOKINGS WHERE username = ? AND roomnum = ? AND status = ?";

    String[] columnNames = {"NAME", "CONTACT", "EMAIL", "GENDER", "CHECKIN", "CHECKOUT", "PAX", "ROOMNUM", "ROOMTYPE", "PRICE", "USERNAME", "STATUS"};
    tbmodel.setColumnIdentifiers(columnNames);
    tbmodel.setRowCount(0);  // Clear previous data

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, userName);
        ps.setString(2, roomNumber);
        ps.setString(3, selectedStatus);

        ResultSet rs = ps.executeQuery();

        int count = 0;
        while (rs.next()) {
            tbmodel.addRow(new Object[] {
                rs.getString("NAME"),
                rs.getString("CONTACT"),
                rs.getString("EMAIL"),
                rs.getString("GENDER"),
                rs.getString("CHECKIN"),
                rs.getString("CHECKOUT"),
                rs.getString("PAX"),
                rs.getString("ROOMNUM"),
                rs.getString("ROOMTYPE"),
                rs.getString("PRICE"),
                rs.getString("USERNAME"),
                rs.getString("STATUS")
            });
            count++;
        }

        bookdetailstb.setModel(tbmodel);
        bookdetailstb.setVisible(count > 0);

        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No matching records found for the provided filters.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        roomnum = new javax.swing.JTextField();
        username = new javax.swing.JTextField();
        cb_stat = new javax.swing.JComboBox<>();
        btn_accept = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookdetailstb = new javax.swing.JTable();
        btn_accept1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1060, 597));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(165, 157, 173));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(32, 33, 67));
        jButton1.setText("Bookig Details");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(32, 33, 67));
        jButton2.setText("Room Management");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(32, 33, 67));
        jButton4.setText("Sales Report");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255, 80));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel_booking/logo 120.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 110));

        jButton5.setText("Log out");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jButton5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(101, 101, 101))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 593));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Booking Details");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 17, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Username:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Room Number:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, -1, -1));

        roomnum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomnumActionPerformed(evt);
            }
        });
        jPanel1.add(roomnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 140, 31));
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, 140, 31));

        cb_stat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Status", "Pending", "Accepted", "Completed", "Declined" }));
        jPanel1.add(cb_stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 150, -1, -1));

        btn_accept.setBackground(new java.awt.Color(204, 255, 204));
        btn_accept.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_accept.setText("Search");
        btn_accept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_acceptActionPerformed(evt);
            }
        });
        jPanel1.add(btn_accept, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 110, -1, -1));

        bookdetailstb.setModel(tbmodel);
        bookdetailstb.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(bookdetailstb);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 780, 390));

        btn_accept1.setBackground(new java.awt.Color(204, 255, 204));
        btn_accept1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_accept1.setText("Update");
        btn_accept1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_accept1ActionPerformed(evt);
            }
        });
        jPanel1.add(btn_accept1, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 150, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        sales_report n=new sales_report();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        admin_home n=new admin_home();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        booking_details_page n=new booking_details_page();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        room_management_page n=new room_management_page();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void roomnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roomnumActionPerformed

    private void btn_acceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_acceptActionPerformed
        searchBookingStatus();


    }//GEN-LAST:event_btn_acceptActionPerformed

    private void btn_accept1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_accept1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_accept1ActionPerformed

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
            java.util.logging.Logger.getLogger(booking_details_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(booking_details_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(booking_details_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(booking_details_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new booking_details_page().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bookdetailstb;
    private javax.swing.JButton btn_accept;
    private javax.swing.JButton btn_accept1;
    private javax.swing.JComboBox<String> cb_stat;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField roomnum;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
