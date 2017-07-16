/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package br.unioeste.processamento_de_imagens.controller;

import br.unioeste.processamento_de_imagens.source.Filtros;
import br.unioeste.processamento_de_imagens.source.Imagem;
import br.unioeste.processamento_de_imagens.source.ImageOPStatistic;
import br.unioeste.processamento_de_imagens.source.ImageOP;
import br.unioeste.processamento_de_imagens.source.Quantizador;
import br.unioeste.processamento_de_imagens.source.fft.Complex;
import br.unioeste.processamento_de_imagens.source.fft.TransformadaFourier;
import br.unioeste.processamento_de_imagens.source.fft.FFTUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Henrique
 */
public final class Controller {

    private static Complex[] c;

    public static boolean createImage(File file) {
        try {
            Imagem.getInstance().setBufferedImage(ImageIO.read(file));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, "Exceção de leitura de aquivo", ex);
            return false;
        }
        return true;
    }

    public static boolean saveImage(File file) {
        try {
            ImageIO.write(Imagem.getInstance().getBufferedImage(), "bmp", file);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, "Exceção de escrita de aquivo", ex);
            return false;
        }

        return true;
    }

    public static String infoImage() {
        Imagem gi = Imagem.getInstance();
        int[] rgb = new int[3];
        int[] gray = new int[1];
        String s = "";
        s += "Altura da imagem: " + gi.getHeight() + "\n";
        s += "Largura da imagem: " + gi.getWidth() + "\n";
        s += "Bits por píxel: " + gi.getPixelSize() + "\n";
        if (ImageOPStatistic.calcMedia(gi.getBufferedImage()).length == 1) {
            gray = ImageOPStatistic.calcMedia(gi.getBufferedImage());
            s += "Media: " + gray[0] + "\n";
            gray = ImageOPStatistic.calcVariancia(gi.getBufferedImage());
            s += "Variância: " + gray[0] + "\n";
        } else {
            rgb = ImageOPStatistic.calcMedia(gi.getBufferedImage());
            s += "Media:\n   R: " + rgb[0] + "\n   G: " + rgb[1] + "\n   B: " + rgb[2] + "\n";
            rgb = ImageOPStatistic.calcVariancia(gi.getBufferedImage());
            s += "Variância:\n   R: " + rgb[0] + "\n   G: " + rgb[1] + "\n   B: " + rgb[2] + "\n";
        }
        s += Imagem.getInstance().getTypeImage();
        return s;
    }

    public static List<JFreeChart> gerarHistograma() {
        return ImageOPStatistic.gerarHistograma(Imagem.getInstance().getBufferedImage());
    }

    public static boolean converterToGrayScale() {
        try {
            Imagem.getInstance().setBufferedImage(Quantizador.converteToGrayScale(
                    Imagem.getInstance().getBufferedImage()));
        } catch (Exception e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, "Erro durante conversão para tons de cinza", e);
            return false;
        }
        return true;
    }

    public static void autoEscala() {
        Imagem.getInstance().setBufferedImage(ImageOP.autoEscala(Imagem.getInstance().getBufferedImage()));
    }

    public static void ajustarBrilho(int b, int c) {
        Imagem.getInstance().setBufferedImage(ImageOP.ajusteBrilhoContraste(b, c,
                Imagem.getInstance().getBufferedImage()));
    }

    public static void limiarizar(int limiar) {
        Imagem.getInstance().setBufferedImage(Quantizador.limiarizar(limiar,
                Imagem.getInstance().getBufferedImage()));
    }

    public static void resize(int width, int height) {
        Imagem.getInstance().setBufferedImage(ImageOP.resizeImage(Imagem.getInstance().getBufferedImage(), width, height));
    }

    public static void negativo() {
        Imagem.getInstance().setBufferedImage(ImageOP.negativo(
                Imagem.getInstance().getBufferedImage()));
    }

    public static void filtroMedia() {
        Imagem.getInstance().setBufferedImage(Filtros.filtroMedia(Imagem.getInstance().getBufferedImage()));
    }

    public static void filtroMediana() {
        Imagem.getInstance().setBufferedImage(Filtros.filtroMediana(Imagem.getInstance().getBufferedImage()));
    }

    public static void filtroRoberts() {
        Imagem.getInstance().setBufferedImage(Filtros.filtroRoberts(Imagem.getInstance().getBufferedImage()));
    }

    public static void FFT() {
        c = FFTUtil.prepareImage(Imagem.getInstance().getBufferedImage(), true);
        System.out.println("transformando...");
        c = TransformadaFourier.fft(c);
        System.out.println("Transformado");
        Imagem.getInstance().setBufferedImage(FFTUtil.generateSpectrum(c));
        c = null;

    }

    public static void filtroHighBoost() {
        int i = 1;
        i = Integer.parseInt(JOptionPane.showInputDialog("entre com o index", i));
        Imagem.getInstance().setBufferedImage(Filtros.filtroHighBoost(i, Imagem.getInstance().getBufferedImage()));
    }

    public static void IFFT() {
        if (c != null) {
            c = TransformadaFourier.ifft(c);
            Imagem.getInstance().setBufferedImage(FFTUtil.generateImage(c, Imagem.getInstance().getBufferedImage()));
            autoEscala();
        }
    }
}
