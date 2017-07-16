/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.processamento_de_imagens.view.componentes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Henrique
 */
public class PreviewPanel extends JPanel {

    private BufferedImage bi;

    public PreviewPanel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bi != null) {
            this.setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
        }

        g.drawImage(bi, 0, 0, null);
        this.repaint();
    }

    public PreviewPanel(BufferedImage bi) {
        this.bi = bi;
    }

}
