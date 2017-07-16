/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.processamento_de_imagens.source;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Henrique
 */
public class ImageOP {

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, image.getType());
        Graphics2D gd = (Graphics2D) bi.getGraphics();
        gd.drawImage(image, 0, 0, width, height, null);
        return bi;
    }

    public static BufferedImage autoEscala(BufferedImage image) {
        BufferedImage bi = null;
        int[] temp = new int[image.getWidth() * image.getHeight()];
        int autoEscala[] = new int[temp.length];
        int max = -999;
        int min = 999;
        boolean flag = false;
        switch (image.getType()) {
            case BufferedImage.TYPE_BYTE_INDEXED:
                image.getRGB(0, 0, image.getWidth(), image.getHeight(), temp, 0, image.getWidth());

                for (int i = 0; i < temp.length; i++) {
                    max = (temp[i] & 0xFF) > max ? (temp[i] & 0xFF) : max;
                    min = (temp[i] & 0xFF) < min ? (temp[i] & 0xFF) : min;
                }

                for (int i = 0; i < temp.length; i++) {
                    int red = (int)((255.0 / (max - min)) * ((temp[i] & 0xFF) - min));

                    autoEscala[i] = ((red) & 0xFF000000)
                            | ((red << 16) & 0x00FF0000)
                            | ((red << 8) & 0x0000FF00)
                            | (red & 0x000000FF);

                }
                flag = true;
                break;

            case BufferedImage.TYPE_BYTE_GRAY:
                temp = image.getData().getPixels(0, 0, image.getWidth(), image.getHeight(), temp);

                for (int i = 0; i < temp.length; i++) {
                    max = temp[i] > max ? temp[i] : max;
                    min = temp[i] < min ? temp[i] : min;
                }

                for (int i = 0; i < temp.length; i++) {
                    int gray = (int)((255 / (max - min)) * (temp[i] - min));

                    autoEscala[i] = ((gray) & 0xFF000000)
                            | ((gray << 16) & 0x00FF0000)
                            | ((gray << 8) & 0x0000FF00)
                            | (gray & 0x000000FF);
                }
                flag = true;
                break;

            default:
                System.out.println("não implementado para esse espaço de cor...");
        }



        bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
        bi.setRGB(0, 0, image.getWidth(), image.getHeight(), autoEscala, 0, image.getWidth());

        return flag == true ? bi : image;
    }

    public static BufferedImage ajusteBrilhoContraste(int b, int c, BufferedImage image) {

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                int rgb = image.getRGB(i, j);

                int red = (c * ((rgb >> 16) & 0x00FF)) + b;
                int green = (c * ((rgb >> 8) & 0x0000FF)) + b;
                int blue = (c * (rgb & 0x000000FF)) + b;

                red = red > 255 ? 255 : red;
                green = green > 255 ? 255 : green;
                blue = blue > 255 ? 255 : blue;

                red = red < 0 ? 0 : red;
                green = green < 0 ? 0 : green;
                blue = blue < 0 ? 0 : blue;

                Color color = new Color(red, green, blue);
                image.setRGB(i, j, color.getRGB());
            }
        }
        
        return image;
    }

    public static BufferedImage negativo(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int r = 255 - (int) ((rgb & 0x00FF0000) >> 16);
                int g = 255 - (int) ((rgb & 0x0000FF00) >> 8);
                int b = 255 - (int) (rgb & 0x000000FF);
                Color color = new Color(r, g, b);
                image.setRGB(i, j, color.getRGB());
            }
        }
        return image;
    }
}
