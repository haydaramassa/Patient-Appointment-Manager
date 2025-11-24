
package patient.appointment.manager;
import javax.swing.DefaultListModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DashboardFrame extends javax.swing.JFrame {

    
   private DefaultListModel<String> patientListModel = new DefaultListModel<>();//هذا هو المودل (مخزن البيانات تبع الـ JList).
   private javax.swing.table.DefaultTableModel tableModel;   // موديل جدول المواعيد

   public DashboardFrame() {
        initComponents();
        
         
    // ربط قائمة المرضى
    jList1.setModel(patientListModel);
    loadPatientsFromDB();

    // 🔴 احذفي أو علّقي هذا السطر التجريبي:
    // tableModel.setValueAt("Test Termin", 0, 0);

    // ✅ هنا نغيّر موديل الجدول للمواعيد الحقيقية
    javax.swing.table.DefaultTableModel model =
        new javax.swing.table.DefaultTableModel(
            new Object[][]{},            
            new String[] { "Patient", "Datum/Uhrzeit", "Arzt", "Status", "Bemerkungen" }

        );
    jTable1.setModel(model);

    // خزّنيه في المتغيّر اللي فوق
    tableModel = model;
    // التاريخ الافتراضي: اليوم
    jDateChooser1.setDate(new java.util.Date());

        // تحميل مواعيد اليوم مباشرة
        loadTodayAppointments();

    // الـ ListSelectionListener تبع jList1 (خليه مثل ما هو بس ضيفي السطر اللي قلناه سابقاً)
    jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
        @Override
        public void valueChanged(javax.swing.event.ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                String selected = jList1.getSelectedValue();
                if (selected != null && selected.contains(" - ")) {
                    String idStr = selected.substring(0, selected.indexOf(" - ")).trim();
                    try {
                        int id = Integer.parseInt(idStr);
                        showPatientDetails(id);
                        loadAppointmentsForPatient(id); // 👈 هذا السطر مهم
                    } catch (NumberFormatException ex) {}
                }
            }
        }
    });
    
        // دبل كليك على المريض لفتح نافذة المواعيد الكاملة
    jList1.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (e.getClickCount() == 2) {  // double click

                String selected = jList1.getSelectedValue();

                if (selected != null && selected.contains(" - ")) {

                    // استخراج الـ ID
                    String idStr = selected.substring(0, selected.indexOf(" - ")).trim();
                    int patientId = Integer.parseInt(idStr);

                    // استخراج اسم المريض (اللي بعد " - ")
                    String name = selected.substring(selected.indexOf(" - ") + 3);

                    // فتح الفريم الجديد
                    PatientAppointmentsFrame frame = new PatientAppointmentsFrame(patientId, name);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }
        }
    });

    }

    public  void loadPatientsFromDB() {

    patientListModel.clear();   // لتفريغ أي بيانات قديمة

    try (Connection conn = DBConnection.getConnection()) {

        String sql = "SELECT id, first_name, last_name FROM patients ORDER BY last_name";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String first = rs.getString("first_name");
            String last = rs.getString("last_name");

            // الشكل الذي يظهر في الـ JList
            String item = id + " - " + last + ", " + first;

            patientListModel.addElement(item);
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "خطأ أثناء تحميل المرضى: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelbasic = new javax.swing.JPanel();
        jPaneltitle = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelleft = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanelright = new javax.swing.JPanel();
        jPanelRightTitle = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtPatientDetails = new javax.swing.JTextArea();
        add = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jPanelsearchthings = new javax.swing.JPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        searchbutton = new javax.swing.JButton();
        nachname = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        add1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelbasic.setPreferredSize(new java.awt.Dimension(800, 500));

        jPaneltitle.setBackground(new java.awt.Color(46, 139, 192));

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("                                Terminmanager für Patienten");

        jPanelleft.setBackground(new java.awt.Color(46, 139, 192));

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel2.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Patientenliste");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Patienten- oder Terminsuche");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanelleftLayout = new javax.swing.GroupLayout(jPanelleft);
        jPanelleft.setLayout(jPanelleftLayout);
        jPanelleftLayout.setHorizontalGroup(
            jPanelleftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelleftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelleftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelleftLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelleftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))))
                .addGap(46, 46, 46))
        );
        jPanelleftLayout.setVerticalGroup(
            jPanelleftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelleftLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
        );

        jPanelright.setBackground(new java.awt.Color(248, 251, 255));

        jPanelRightTitle.setBackground(new java.awt.Color(46, 139, 192));

        jLabel3.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText(" Patientendaten");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanelRightTitleLayout = new javax.swing.GroupLayout(jPanelRightTitle);
        jPanelRightTitle.setLayout(jPanelRightTitleLayout);
        jPanelRightTitleLayout.setHorizontalGroup(
            jPanelRightTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
        );
        jPanelRightTitleLayout.setVerticalGroup(
            jPanelRightTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRightTitleLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtPatientDetails.setEditable(false);
        txtPatientDetails.setColumns(20);
        txtPatientDetails.setRows(5);
        jScrollPane3.setViewportView(txtPatientDetails);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelrightLayout = new javax.swing.GroupLayout(jPanelright);
        jPanelright.setLayout(jPanelrightLayout);
        jPanelrightLayout.setHorizontalGroup(
            jPanelrightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelRightTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelrightLayout.setVerticalGroup(
            jPanelrightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelrightLayout.createSequentialGroup()
                .addComponent(jPanelRightTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add.setText("Patient hinzufügen");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        delete.setText("Löschen");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        jPanelsearchthings.setBackground(new java.awt.Color(248, 251, 255));
        jPanelsearchthings.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel5.setText("• Datum:");

        jLabel6.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel6.setText("• Nachname:");

        searchbutton.setBackground(new java.awt.Color(46, 139, 192));
        searchbutton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchbutton.setForeground(new java.awt.Color(255, 255, 255));
        searchbutton.setText("Suchen");
        searchbutton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        searchbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbuttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelsearchthingsLayout = new javax.swing.GroupLayout(jPanelsearchthings);
        jPanelsearchthings.setLayout(jPanelsearchthingsLayout);
        jPanelsearchthingsLayout.setHorizontalGroup(
            jPanelsearchthingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelsearchthingsLayout.createSequentialGroup()
                .addGroup(jPanelsearchthingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelsearchthingsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nachname))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelsearchthingsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelsearchthingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelsearchthingsLayout.createSequentialGroup()
                                .addGroup(jPanelsearchthingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelsearchthingsLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(searchbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 54, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelsearchthingsLayout.setVerticalGroup(
            jPanelsearchthingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelsearchthingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nachname, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(searchbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mo.", "Di.", "Mi.", "Do.", "Fr.", "Sa.", "So."
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        add1.setText("Neuen Termin hinzufügen");
        add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPaneltitleLayout = new javax.swing.GroupLayout(jPaneltitle);
        jPaneltitle.setLayout(jPaneltitleLayout);
        jPaneltitleLayout.setHorizontalGroup(
            jPaneltitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPaneltitleLayout.createSequentialGroup()
                .addGroup(jPaneltitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPaneltitleLayout.createSequentialGroup()
                        .addComponent(jPanelleft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPaneltitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPaneltitleLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jPanelright, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPaneltitleLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPaneltitleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelsearchthings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPaneltitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPaneltitleLayout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPaneltitleLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(add1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(134, 134, 134)))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPaneltitleLayout.setVerticalGroup(
            jPaneltitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneltitleLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneltitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPaneltitleLayout.createSequentialGroup()
                        .addComponent(jPanelleft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPaneltitleLayout.createSequentialGroup()
                        .addComponent(jPanelright, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPaneltitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(add)
                            .addComponent(delete))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPaneltitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelsearchthings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPaneltitleLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(add1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(547, 547, 547))
        );

        javax.swing.GroupLayout jPanelbasicLayout = new javax.swing.GroupLayout(jPanelbasic);
        jPanelbasic.setLayout(jPanelbasicLayout);
        jPanelbasicLayout.setHorizontalGroup(
            jPanelbasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneltitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelbasicLayout.setVerticalGroup(
            jPanelbasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneltitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelbasic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelbasic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        // TODO add your handling code here:
        Addpatient addpatient = new Addpatient(this);
        addpatient.setLocationRelativeTo(this); // يخليها بالنص
        addpatient.setVisible(true);
    }//GEN-LAST:event_addActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
                                           
    // 1) نجيب المريض المحدد من القائمة
    String selected = jList1.getSelectedValue();

    if (selected == null || !selected.contains(" - ")) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Bitte wählen Sie einen Patienten aus!");
        return;
    }

    // الشكل: "1 - Nachname, Vorname" → نأخذ الـ ID قبل " - "
    String idStr = selected.substring(0, selected.indexOf(" - ")).trim();
    int patientId;

    try {
        patientId = Integer.parseInt(idStr);
    } catch (NumberFormatException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Ungültige Patienten-ID!");
        return;
    }

    // 2) سؤال تأكيد قبل الحذف
    int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
            "Möchten Sie diesen Patienten wirklich löschen?",
            "Bestätigung",
            javax.swing.JOptionPane.YES_NO_OPTION);

    if (confirm != javax.swing.JOptionPane.YES_OPTION) {
        return; // المستخدم اختار لا
    }

    // 3) حذف من قاعدة البيانات
    try (java.sql.Connection conn = DBConnection.getConnection()) {

        String sql = "DELETE FROM patients WHERE id = ?";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, patientId);
        ps.executeUpdate();

        javax.swing.JOptionPane.showMessageDialog(this, 
                "Patient wurde gelöscht.");

        // 4) تحديث القائمة و تفريغ التفاصيل
        loadPatientsFromDB();            // تعيد تحميل الـ JList من الداتابيس
        txtPatientDetails.setText("");   // امسحي بيانات المريض من اليمين

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Fehler beim Löschen: " + e.getMessage());
    }
               
        
    }//GEN-LAST:event_deleteActionPerformed

    private void add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add1ActionPerformed
      // 1) نجيب المريض المحدد من القائمة
    String selected = jList1.getSelectedValue();

    if (selected == null || !selected.contains(" - ")) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Bitte wählen Sie einen Patienten aus!");
        return;
    }

    // الشكل: "1 - Nachname, Vorname"
    String idStr = selected.substring(0, selected.indexOf(" - ")).trim();
    int patientId;
    try {
        patientId = Integer.parseInt(idStr);
    } catch (NumberFormatException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Ungültige Patienten-ID!");
        return;
    }
    // الاسم لعرضه في شاشة الموعد
    String patientName = selected.substring(selected.indexOf(" - ") + 3); 
    // 2) نفتح الفورم الجديد للموعد
    AddAppointmentFrame appt = new AddAppointmentFrame(this,patientId, patientName);
    appt.setLocationRelativeTo(this);
    appt.setVisible(true);
    
    }//GEN-LAST:event_add1ActionPerformed

    private void searchbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbuttonActionPerformed
        // TODO add your handling code here:
        searchAppointments(); 
    }//GEN-LAST:event_searchbuttonActionPerformed

public void loadAppointmentsForPatient(int patientId) {
       try (Connection conn = DBConnection.getConnection()) {

        String sql = "SELECT appointment_date, doctor_name, status, notes "
                   + "FROM appointments "
                   + "WHERE patient_id = ? "
                   + "ORDER BY appointment_date";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, patientId);
        ResultSet rs = ps.executeQuery();

        // نفرّغ الجدول من المواعيد القديمة
        tableModel.setRowCount(0);

        java.time.format.DateTimeFormatter fmt =
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (rs.next()) {
            java.sql.Timestamp ts = rs.getTimestamp("appointment_date");
            String dt = (ts == null)
                    ? ""
                    : ts.toLocalDateTime().format(fmt);

            String doc    = rs.getString("doctor_name");
            String status = rs.getString("status");
            String notes  = rs.getString("notes");

            // نضيف صف جديد للجدول
            tableModel.addRow(new Object[] { dt, doc, status, notes });
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
                "Fehler beim Laden der Termine: " + e.getMessage());
    }
}

// بحث عن المواعيد حسب التاريخ و/أو الكنية
private void searchAppointments() {

    // 1) نقرأ القيم من الواجهة
    java.util.Date date = jDateChooser1.getDate();
    String lastNameFilter = nachname.getText().trim();

    if (date == null && lastNameFilter.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this,
                "Bitte geben Sie ein Datum oder einen Nachnamen ein!");
        return;
    }

    try (Connection conn = DBConnection.getConnection()) {

        StringBuilder sql = new StringBuilder(
            "SELECT a.appointment_date, a.doctor_name, a.status, a.notes " +
            "FROM appointments a " +
            "JOIN patients p ON a.patient_id = p.id " +
            "WHERE 1=1 "
        );

        // نجهز قائمة البراميترز
        java.util.List<Object> params = new java.util.ArrayList<>();

        // لو في تاريخ → نضيف شرط
        if (date != null) {
            // ناخذ التاريخ فقط بدون وقت
            java.time.LocalDate localDate = date.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();

            sql.append(" AND DATE(a.appointment_date) = ? ");
            params.add(java.sql.Date.valueOf(localDate));
        }

        // لو في كنية
        if (!lastNameFilter.isEmpty()) {
            sql.append(" AND p.last_name LIKE ? ");
            params.add("%" + lastNameFilter + "%");
        }

        sql.append(" ORDER BY a.appointment_date ");

        PreparedStatement ps = conn.prepareStatement(sql.toString());

        // نربط البراميترز
        for (int i = 0; i < params.size(); i++) {
            Object value = params.get(i);
            if (value instanceof java.sql.Date) {
                ps.setDate(i + 1, (java.sql.Date) value);
            } else {
                ps.setString(i + 1, value.toString());
            }
        }

        ResultSet rs = ps.executeQuery();

        // نفرغ الجدول القديم
        tableModel.setRowCount(0);

        java.time.format.DateTimeFormatter fmt =
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // نعبّي النتائج في الجدول
        while (rs.next()) {
            java.sql.Timestamp ts = rs.getTimestamp("appointment_date");
            String dt = (ts == null)
                    ? ""
                    : ts.toLocalDateTime().format(fmt);

            String doc    = rs.getString("doctor_name");
            String status = rs.getString("status");
            String notes  = rs.getString("notes");

            tableModel.addRow(new Object[]{ dt, doc, status, notes });
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
                "Fehler bei der Terminsuche: " + e.getMessage());
    }
}


// عرض تفاصيل مريض واحد في منطقة Patientendaten
private void showPatientDetails(int patientId) {
    try (Connection conn = DBConnection.getConnection()) {

        String sql = "SELECT * FROM patients WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, patientId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            StringBuilder details = new StringBuilder();

            details.append("ID: ").append(rs.getInt("id")).append("\n");
            details.append("Vorname: ").append(rs.getString("first_name")).append("\n");
            details.append("Nachname: ").append(rs.getString("last_name")).append("\n");
            details.append("Geburtsdatum: ").append(rs.getString("birth_date")).append("\n");
            details.append("Geschlecht: ").append(rs.getString("gender")).append("\n");
            details.append("Telefon: ").append(rs.getString("phone")).append("\n");
            details.append("E-Mail: ").append(rs.getString("email")).append("\n");
            details.append("Adresse: ").append(rs.getString("address")).append("\n");
            details.append("Versicherungsnr.: ").append(rs.getString("insurance_number")).append("\n");
            details.append("Bemerkungen: ").append(rs.getString("notes")).append("\n");

            txtPatientDetails.setText(details.toString());
        } else {
            txtPatientDetails.setText("Kein Patient gefunden.");
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Fehler beim Laden des Patienten: " + e.getMessage());
    }
}
    
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardFrame().setVisible(true);
            }
        });
        
        
        
    }
    private void loadTodayAppointments() {

    java.time.LocalDate today = java.time.LocalDate.now();

    try (Connection conn = DBConnection.getConnection()) {

        String sql =
            "SELECT a.appointment_date, a.doctor_name, a.status, a.notes, " +
            "       p.first_name, p.last_name " +
            "FROM appointments a " +
            "JOIN patients p ON a.patient_id = p.id " +
            "WHERE DATE(a.appointment_date) = ? " +
            "ORDER BY a.appointment_date";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDate(1, java.sql.Date.valueOf(today));

        ResultSet rs = ps.executeQuery();

        tableModel.setRowCount(0); // مسح محتوى الجدول القديم

        java.time.format.DateTimeFormatter fmt =
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (rs.next()) {

            String patientName = rs.getString("last_name") + ", " + rs.getString("first_name");

            java.sql.Timestamp ts = rs.getTimestamp("appointment_date");
            String dt = (ts == null)
                    ? ""
                    : ts.toLocalDateTime().format(fmt);

            String doc    = rs.getString("doctor_name");
            String status = rs.getString("status");
            String notes  = rs.getString("notes");

            tableModel.addRow(new Object[] { patientName, dt, doc, status, notes });
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
                "Fehler beim Laden der heutigen Termine: " + e.getMessage());
    }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton add1;
    private javax.swing.JButton delete;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelRightTitle;
    private javax.swing.JPanel jPanelbasic;
    private javax.swing.JPanel jPanelleft;
    private javax.swing.JPanel jPanelright;
    private javax.swing.JPanel jPanelsearchthings;
    private javax.swing.JPanel jPaneltitle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField nachname;
    private javax.swing.JButton searchbutton;
    private javax.swing.JTextArea txtPatientDetails;
    // End of variables declaration//GEN-END:variables
}
