/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class DashB extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashB.class.getName());

    /**
     * Creates new form DashB
     */
    public DashB(String role, String username) {
        initComponents();
        jTextField1.setEditable(false);
        jTextField1.setEnabled(false);
        jTextField1.setBackground(javax.swing.UIManager.getColor("Panel.background"));

        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (evt.getClickCount() == 2) {

                    int row = jTable2.rowAtPoint(evt.getPoint());

                    if (row == -1) {
                        return;
                    }

                    int id = (int) jTable2.getValueAt(row, 0);

                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Do you want to delete this entire row?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteRow(id, row);
                    }
                }
            }
        });
        Studentinfo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = Studentinfo.rowAtPoint(evt.getPoint());
                    if (row == -1) {
                        return;
                    }
                    int id = (int) Studentinfo.getValueAt(row, 0);
                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Do you want to delete this student?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteStudentRow(id, row);
                    }
                }
            }
        });

        if (!role.equalsIgnoreCase("admin")) {
            JOptionPane.showMessageDialog(this, "Access denied!");
            this.dispose();
            return;
        }

        jTextField1.setText(username);
        loadStudents();
        loadData();
        addEditListener();

        // Add the Add Student button to the panel
        setupAddStudentButton();
    }

    private void deleteStudentRow(int id, int row) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

            // remove from JTable
            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) Studentinfo.getModel();

            model.removeRow(row);

            JOptionPane.showMessageDialog(this, "Student deleted successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage());
        }
    }

    private void loadStudents() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM students";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) Studentinfo.getModel();

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("fullname"),
                    rs.getString("year_level"),
                    rs.getString("student_id"),
                    rs.getString("address"),
                    rs.getString("course"),
                    rs.getInt("age")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }

    private void setupAddStudentButton() {
        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.addActionListener(this::addStudentActionPerformed);
        jPanel2.add(addStudentBtn);
        jPanel2.revalidate();
        jPanel2.repaint();
    }

    private void deleteRow(int id, int row) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

            // remove from JTable
            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) jTable2.getModel();

            model.removeRow(row);

            JOptionPane.showMessageDialog(this, "Row deleted successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadData() {
        try {
            Connection con = DBConnection.getConnection();
            System.out.println("DB: " + con);
            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM users");
            ResultSet rs1 = ps1.executeQuery();
            var model1 = (javax.swing.table.DefaultTableModel) jTable2.getModel();
            model1.setRowCount(0);
            int count = 0;
            while (rs1.next()) {
                count++;
                model1.addRow(new Object[]{rs1.getInt("id"), rs1.getString("firstname"), rs1.getString("lastname"), rs1.getString("username"), rs1.getString("email"), rs1.getString("password"), rs1.getString("gender"), rs1.getString("role")});
            }
            System.out.println("Rows loaded: " + count);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.toString());
        }

    }

    private void addStudentActionPerformed(java.awt.event.ActionEvent evt) {
        // Create a dialog to add student
        String fullname = JOptionPane.showInputDialog(this, "Enter Full Name:");
        if (fullname == null) {
            return;
        }

        String yearLevel = JOptionPane.showInputDialog(this, "Enter Year Level:");
        if (yearLevel == null) {
            return;
        }

        String studentId = JOptionPane.showInputDialog(this, "Enter Student ID:");
        if (studentId == null) {
            return;
        }

        String address = JOptionPane.showInputDialog(this, "Enter Address:");
        if (address == null) {
            return;
        }

        String course = JOptionPane.showInputDialog(this, "Enter Course:");
        if (course == null) {
            return;
        }

        String age = JOptionPane.showInputDialog(this, "Enter Age:");
        if (age == null) {
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO students (fullname, year_level, student_id, address, course, age) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fullname);
            ps.setString(2, yearLevel);
            ps.setString(3, studentId);
            ps.setString(4, address);
            ps.setString(5, course);
            ps.setInt(6, Integer.parseInt(age));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student Added!");
            loadStudents(); // Refresh table
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void addEditListener() {

        jTable2.getModel().addTableModelListener(e -> {

            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {

                int row = e.getFirstRow();
                int col = e.getColumn();

                if (col == 0) {
                    return; // prevent editing ID
                }
                String columnName = jTable2.getColumnName(col);
                Object newValue = jTable2.getValueAt(row, col);

                int id = Integer.parseInt(jTable2.getValueAt(row, 0).toString());

                String dbColumn;

                switch (columnName) {
                    case "First Name":
                        dbColumn = "firstname";
                        break;
                    case "Last Name":
                        dbColumn = "lastname";
                        break;
                    case "User Name":
                        dbColumn = "username";
                        break;
                    case "E-mail":
                        dbColumn = "email";
                        break;
                    case "Password":
                        dbColumn = "password";
                        break;
                    case "Gender":
                        dbColumn = "gender";
                        break;
                    case "Role":
                        dbColumn = "role";
                        break;
                    default:
                        return;
                }

                try {
                    Connection con = DBConnection.getConnection();

                    String sql = "UPDATE users SET " + dbColumn + "=? WHERE id=?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setObject(1, newValue);
                    ps.setInt(2, id);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Updated successfully!");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });

    }

    public void setAdminName(String username) {
        jTextField1.setText(username);
    }

    // DELETE THIS ENTIRE METHOD FROM DashB
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        adminname = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Studentinfo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        adminname.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "User Management System", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP));

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable2.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "First Name", "Last Name", "User Name", "E-mail", "Password", "Gender", "Role"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 974, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
                    .addGap(8, 8, 8)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(189, 189, 189)))
        );

        jTabbedPane2.addTab("User Accounts", jPanel11);

        Studentinfo.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        Studentinfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User Id", "First Name", "Year Level", "Student Id", "Address", "Course", "Age"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Studentinfo);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Student Informations", jPanel2);

        jLabel1.setText("Welcome Amin!");

        jTextField1.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        javax.swing.GroupLayout adminnameLayout = new javax.swing.GroupLayout(adminname);
        adminname.setLayout(adminnameLayout);
        adminnameLayout.setHorizontalGroup(
            adminnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adminnameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
            .addGroup(adminnameLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        adminnameLayout.setVerticalGroup(
            adminnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(adminnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(()
                -> new DashB("admin", "AdminName").setVisible(true)
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Studentinfo;
    private javax.swing.JPanel adminname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
