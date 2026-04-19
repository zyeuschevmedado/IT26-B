package crud;

import java.awt.geom.RoundRectangle2D;

public class LoadingScreen extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoadingScreen.class.getName());

    public LoadingScreen() {
        setUndecorated(true);
        initComponents();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(
                        0, 0,
                        getWidth(),
                        getHeight(),
                        30, 30
                ));
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ld = new javax.swing.JLabel();
        loadbar = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        ld.setFont(new java.awt.Font("Showcard Gothic", 1, 48)); // NOI18N
        ld.setForeground(new java.awt.Color(102, 102, 102));
        ld.setText("%");
        getContentPane().add(ld, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, -1, -1));
        getContentPane().add(loadbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 240, 60));

        jLabel4.setFont(new java.awt.Font("Showcard Gothic", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("LOADING");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/crud/0c667f05d3f74d182d2d3577652381c8.gif"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 290));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

        public static void main(String args[]) {

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

    
            java.awt.EventQueue.invokeLater(() -> {
                LoadingScreen ls = new LoadingScreen();
                ls.setVisible(true);

                
                ls.setShape(new RoundRectangle2D.Double(
                        0, 0,
                        ls.getWidth(),
                        ls.getHeight(),
                        30, 30
                ));

                javax.swing.Timer timer = new javax.swing.Timer(50, null);

                timer.addActionListener(e -> {
                    int value = ls.loadbar.getValue();

                    if (value < 100) {
                        value++;
                        ls.loadbar.setValue(value);
                        ls.ld.setText(value + "%");
                    } else {
                        timer.stop();
                        ls.dispose();

                        LOGINFORM lr = new LOGINFORM();
                        lr.setVisible(true);
                    }
                });

                timer.start();

            }
            );
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel ld;
    private javax.swing.JProgressBar loadbar;
    // End of variables declaration//GEN-END:variables
}
