/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unioeste.processamento_de_imagens.source;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;

/**
 *
 * @author Henrique
 */
public class Quantizador {

    public static BufferedImage convertGrayScaleJava(BufferedImage image) {
        BufferedImageOp op = new ColorConvertOp(
                ColorSpace.getInstance(ColorSpace.CS_GRAY), null);

        return op.filter(image, null);
    }

    public static BufferedImage converteToGrayScale(BufferedImage image) {
        BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);

                int red = (rgb >> 16) & 0x00ff;
                int green = (rgb >> 8) & 0x0000ff;
                int blue = (rgb) & 0x000000ff;

                int gray = (int) (red + green + blue) / 3;

                Color color = new Color(gray, gray, gray);

                bi.setRGB(i, j, color.getRGB());
            }
        }
        return bi;

    }

    public static BufferedImage limiarizar(int limiar, BufferedImage image) {

        BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                int rgb = image.getRGB(i, j);

                int r = ((rgb >> 16) & 0x00ff);
                int g = ((rgb >> 8) & 0x0000ff);
                int b = (rgb & 0x000000ff);

                //converte para tons de cinza
                int media = (int) (r + g + b) / 3;
//                System.out.println("Log: R "+r+"\tG "+g+"\tB "+b+"\t media "+media);

                Color white = new Color(255, 255, 255);
                Color black = new Color(0, 0, 0);

                if (media < limiar) {
                    bi.setRGB(i, j, black.getRGB());
                } else {
                    bi.setRGB(i, j, white.getRGB());
                }
            }
        }
        return bi;
    }
}
