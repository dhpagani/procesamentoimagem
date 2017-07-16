/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unioeste.processamento_de_imagens.view.componentes;

import java.awt.Graphics;
import javax.swing.JScrollPane;

/**
 *
 * @author Henrique
 */
public class ScrollPane extends JScrollPane{

    public ScrollPane() {
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaint();

    }


}
