/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.processamento_de_imagens.view.componentes;

import java.awt.Graphics;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Henrique
 */
public class HistogramaView extends ChartFrame {

    private JFreeChart objectPaint;
    private String title;

    public HistogramaView(String title, JFreeChart chart) {
//        super();u
        super(title, chart);
        this.objectPaint = chart;
        this.title = title;
        this.setSize(620, 420);
        this.setLocationRelativeTo(rootPane);
    }

    @Override
    public void printComponents(Graphics g) {
        super.printComponents(g);
        g.drawImage(objectPaint.createBufferedImage(600, 400), 0, 0, null);
    }
}
