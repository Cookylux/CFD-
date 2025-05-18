/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hotel_booking;
import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author rieje
 */
public class Registration_page extends javax.swing.JFrame {
    private String username;
    Connection con= javaconnect.connectdb();
    PreparedStatement ps=null;
    ResultSet rs=null;
    /**
     * Creates new form user_booking_details_page
     */
    public Registration_page() {
        initComponents();
        
        con = javaconnect.connectdb(); 

        loadcustomerdata(username);

        
        cb_roomnum.setSelectedItem(BookingData.roomType);
        price.setText(BookingData.price);

        SimpleDateFormat reg = new SimpleDateFormat("MM-dd-yyyy");
        try {
            check_in.setText(reg.format(TransferBookSpinner.InDate));
            check_out.setText(reg.format(TransferBookSpinner.OutDate));
            spin_pax.setValue(TransferBookSpinner.adults + TransferBookSpinner.children);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadcustomerdata(String username) {
        try {
            String sql = "SELECT firstname, lastname, email, contactnum FROM SIGNUP WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(sql); 
            ps.setString(1, Current.loggedInUsername); 
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String emailValue = rs.getString("email");

                if (firstName == null) firstName = "";
                if (lastName == null) lastName = "";

                name.setText((firstName + " " + lastName).trim());

                email.setText(emailValue != null ? emailValue : "No email");
                contact.setText(rs.getString("contactnum"));
            } else {
                System.out.println("No user found with username: " + Current.loggedInUsername);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updatePrice() {
    try {
        int pricePerRoom = switch ((String) cb_roomtype.getSelectedItem()) {
            case "Single Room" -> 2999;
            case "Double Room" -> 9599;
            case "Triple Room" -> 15600;
            case "Quad Room" -> 20000;
            default -> 0;
        };

            NumberFormat php = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
            String formatted = php.format(pricePerRoom).replaceAll("\\.00$", "");
            price.setText(formatted);
        } catch (Exception e) {
            price.setText("₱0");
        }  
    }
    private void loadAvailableRoomsByType(String roomType) {
        try {
            String checkInText = check_in.getText().trim();
            String checkOutText = check_out.getText().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            long nights = 1;
            LocalDate checkInDate = null;
            LocalDate checkOutDate = null;

            if (!checkInText.isEmpty() && !checkOutText.isEmpty()) {
                try {
                    checkInDate = LocalDate.parse(checkInText, formatter);
                    checkOutDate = LocalDate.parse(checkOutText, formatter);

                    if (!checkOutDate.isBefore(checkInDate)) {
                        nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                    }
                } catch (DateTimeParseException e) {
                    nights = 1;
                }
            }

            cb_roomnum.removeAllItems();
            price.setText(null);

            String query = """
                SELECT ROOMNUM, PRICE FROM room_management
                WHERE ROOMTYPE = ? AND STATUS = 'Available'
                  AND ROOMNUM NOT IN (
                      SELECT ROOMNUM FROM UNAVAILROOMS
                      WHERE UNAVAILDATES BETWEEN ? AND ?
                  )
            """;

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, roomType);
            stmt.setDate(2, checkInDate != null ? java.sql.Date.valueOf(checkInDate) : java.sql.Date.valueOf(LocalDate.now()));
            stmt.setDate(3, checkOutDate != null ? java.sql.Date.valueOf(checkOutDate.minusDays(1)) : java.sql.Date.valueOf(LocalDate.now()));

            ResultSet rs = stmt.executeQuery();

            NumberFormat php = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
            boolean first = true;

            while (rs.next()) {
                cb_roomnum.addItem(rs.getString("ROOMNUM"));

                if (first) {
                    try {
                        double pricePerNight = rs.getDouble("PRICE");
                        double totalPrice = pricePerNight * nights;

                        String priceInfo = "₱" + (int) pricePerNight + " x " + nights + " night(s) = " +
                                php.format(totalPrice).replaceAll("\\.00$", "");
                        price.setText(priceInfo);
                    } catch (NumberFormatException e) {
                        price.setText("₱0");
                    }
                    first = false;
                }
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
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

        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        price = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        contact = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        check_in = new javax.swing.JTextField();
        check_out = new javax.swing.JTextField();
        cb_roomnum = new javax.swing.JComboBox<>();
        proceed = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        cb_gender = new javax.swing.JComboBox<>();
        cb_roomtype = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        spin_pax = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Price", "₱2,999", "₱9,599", "₱15,600", "₱20,000" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel6.setText("Birthday:");

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel17.setText("Last Name:");

        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel10.setText("Children:");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel9.setText("Adult:");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1060, 597));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel3.setBackground(new java.awt.Color(75, 59, 91));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel2.setText("Booking");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel3.setText("Name:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel4.setText("Contact Number:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel5.setText("Email:");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel7.setText("Check-in:");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel8.setText("Check-out:");

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel11.setText("Room Type:");

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel12.setText("Available Room:");

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel13.setText("Price:");

        price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceActionPerformed(evt);
            }
        });

        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });

        proceed.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        proceed.setText("Proceed");
        proceed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proceedActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel15.setText("Gender:");

        cb_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Gender", "Male", "Female" }));

        cb_roomtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Room Type", "Single Room", "Double Room", "Triple Room", "Quad Room" }));
        cb_roomtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_roomtypeActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel16.setText("Pax:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(180, 180, 180)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(check_in, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(140, 140, 140)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(check_out, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel16)
                            .addComponent(spin_pax, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_roomtype, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(cb_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(220, 220, 220)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(cb_roomnum, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))))
                .addGap(110, 110, 110))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(proceed, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(check_in, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(check_out, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(spin_pax, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(cb_roomtype, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(cb_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(cb_roomnum, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(proceed, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel_booking/logo 120.png"))); // NOI18N
        jLabel14.setText("jLabel14");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, -1));

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 1060, 590);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseClicked

    private void proceedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proceedActionPerformed
        java.sql.Date checkin = null;
    java.sql.Date checkout = null;
    String status = "Pending";

    try {
        // Parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate checkInDate = LocalDate.parse(check_in.getText().trim(), formatter);
        LocalDate checkOutDate = LocalDate.parse(check_out.getText().trim(), formatter);

        checkin = java.sql.Date.valueOf(checkInDate);
        checkout = java.sql.Date.valueOf(checkOutDate);

        if (checkOutDate.isBefore(checkInDate)) {
            JOptionPane.showMessageDialog(this, "Check-out date cannot be before Check-in date.", "Date Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name.getText().trim().isEmpty() || contact.getText().trim().isEmpty() || email.getText().trim().isEmpty() ||
            cb_gender.getSelectedItem() == null || cb_roomtype.getSelectedItem() == null || cb_roomnum.getSelectedItem() == null ||
            spin_pax.getValue() == null) {

            JOptionPane.showMessageDialog(this, "Please fill out all fields before proceeding.", "Missing Info", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int pax = Integer.parseInt(spin_pax.getValue().toString());
        String roomType = cb_roomtype.getSelectedItem().toString();

        int peoplePerRoom = switch (roomType) {
            case "Single Room" -> 2;
            case "Double Room" -> 4;
            case "Triple Room" -> 4;
            case "Quad Room" -> 6;
            default -> 1;
        };

        if (pax > peoplePerRoom) {
            JOptionPane.showMessageDialog(this,
                "Total people exceed the capacity for the selected room(s).\n" +
                "Room type: " + roomType + "\n" +
                "Total capacity: " + peoplePerRoom + "\n" +
                "People: " + pax,
                "Room Capacity Exceeded", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String priceText = price.getText();
        double totalPrice = 0.0;

        try {
            String[] parts = priceText.split("=");
            if (parts.length == 2) {
                String totalPart = parts[1].replaceAll("[^\\d.]", "");
                totalPrice = Double.parseDouble(totalPart);
            }
        } catch (Exception e) {
            totalPrice = 0.0;
        }

        
        String insertSQL = "INSERT INTO BOOKINGS (name, contact, email, gender, checkin, checkout, pax, roomnum, roomtype, price, username, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement insertPs = con.prepareStatement(insertSQL);
        insertPs.setString(1, name.getText().trim());
        insertPs.setString(2, contact.getText().trim());
        insertPs.setString(3, email.getText().trim());
        insertPs.setString(4, cb_gender.getSelectedItem().toString().trim());
        insertPs.setDate(5, checkin);
        insertPs.setDate(6, checkout);
        insertPs.setString(7, spin_pax.getValue().toString().trim());
        insertPs.setString(8, cb_roomnum.getSelectedItem().toString().trim());
        insertPs.setString(9, cb_roomtype.getSelectedItem().toString().trim());
        insertPs.setString(10, String.valueOf(totalPrice));
        insertPs.setString(11, Current.loggedInUsername);
        insertPs.setString(12,status);
        insertPs.executeUpdate();
        insertPs.close();

        String roomNum = cb_roomnum.getSelectedItem().toString();

        String unavailableSQL = "INSERT INTO UNAVAILROOMS (ROOMNUM, UNAVAILDATES) VALUES (?, ?)";
        PreparedStatement unavailablePs = con.prepareStatement(unavailableSQL);

        for (LocalDate date = checkInDate; date.isBefore(checkOutDate); date = date.plusDays(1)) {
            unavailablePs.setString(1, roomNum);
            unavailablePs.setDate(2, java.sql.Date.valueOf(date));
            unavailablePs.addBatch();  // More efficient
        }

        unavailablePs.executeBatch();
        unavailablePs.close();
        con.close();

        JOptionPane.showMessageDialog(this, "Booking Successful");
        new loggedin_home_page().setVisible(true);
        this.setVisible(false);

    } catch (DateTimeParseException e) {
        JOptionPane.showMessageDialog(this, "Invalid date format. Use MM-DD-YYYY", "Date Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Something went wrong while booking.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_proceedActionPerformed

    private void priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void cb_roomtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_roomtypeActionPerformed
        String selectedType = (String) cb_roomtype.getSelectedItem();

        if (selectedType != null && !selectedType.equals("Select")) {
            loadAvailableRoomsByType(selectedType);
        }        
    }//GEN-LAST:event_cb_roomtypeActionPerformed

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
            java.util.logging.Logger.getLogger(Registration_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registration_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registration_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registration_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registration_page().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cb_gender;
    private javax.swing.JComboBox<String> cb_roomnum;
    private javax.swing.JComboBox<String> cb_roomtype;
    private javax.swing.JTextField check_in;
    private javax.swing.JTextField check_out;
    private javax.swing.JTextField contact;
    private javax.swing.JTextField email;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField name;
    private javax.swing.JTextField price;
    private javax.swing.JButton proceed;
    private javax.swing.JSpinner spin_pax;
    // End of variables declaration//GEN-END:variables
}
