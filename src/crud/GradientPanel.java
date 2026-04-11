/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package crud;

import java.awt.*;
import javax.swing.*;

public class GradientPanel extends javax.swing.JPanel {

    public GradientPanel() {
        setLayout(new BorderLayout());
        setOpaque(false); // important for gradient visibility
    }
@Override
protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create(); // safer than direct cast

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);

    int w = getWidth();
    int h = getHeight();

    Color top = Color.WHITE;
    Color middle = new Color(210, 210, 210);
    Color bottom = new Color(70, 70, 70);

    // top → middle
    GradientPaint gp1 = new GradientPaint(0, 0, top, 0, h / 2f, middle);
    g2d.setPaint(gp1);
    g2d.fillRect(0, 0, w, h / 2);

    // middle → bottom
    GradientPaint gp2 = new GradientPaint(0, h / 2f, middle, 0, h, bottom);
    g2d.setPaint(gp2);
    g2d.fillRect(0, h / 2, w, h / 2);

    g2d.dispose(); // IMPORTANT: prevents weird artifacts
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
