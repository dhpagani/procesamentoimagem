/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.processamento_de_imagens.view.componentes;

import br.unioeste.processamento_de_imagens.source.Imagem;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Henrique
 */
public class ImageView extends JPanel {


    public ImageView() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(Imagem.getInstance().getBufferedImage() != null) {
            this.setPreferredSize(new Dimension(Imagem.getInstance().getWidth(),
                    Imagem.getInstance().getHeight()));
        }

        g.drawImage(Imagem.getInstance().getBufferedImage(), 0, 0, null);
        this.repaint();
    }
}
