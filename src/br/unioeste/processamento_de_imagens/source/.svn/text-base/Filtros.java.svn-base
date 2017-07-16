/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.processamento_de_imagens.source;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Henrique
 */
public class Filtros {

    public static BufferedImage filtroMedia(BufferedImage image) {

        BufferedImage bi = image;

        Color[][] cores = separaRGB(image);

        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {


                int r = (cores[i - 1][j - 1].getRed() + cores[i - 1][j].getRed() + cores[i - 1][j + 1].getRed()
                        + cores[i][j - 1].getRed() + cores[i][j].getRed() + cores[i][j + 1].getRed()
                        + cores[i + 1][j - 1].getRed() + cores[i + 1][j].getRed() + cores[i + 1][j + 1].getRed()) / 9;

                int g = (cores[i - 1][j - 1].getGreen() + cores[i - 1][j].getGreen() + cores[i - 1][j + 1].getGreen()
                        + cores[i][j - 1].getGreen() + cores[i][j].getGreen() + cores[i][j + 1].getGreen()
                        + cores[i + 1][j - 1].getGreen() + cores[i + 1][j].getGreen() + cores[i + 1][j + 1].getGreen()) / 9;

                int b = (cores[i - 1][j - 1].getBlue() + cores[i - 1][j].getBlue() + cores[i - 1][j + 1].getBlue()
                        + cores[i][j - 1].getBlue() + cores[i][j].getBlue() + cores[i][j + 1].getBlue()
                        + cores[i + 1][j - 1].getBlue() + cores[i + 1][j].getBlue() + cores[i + 1][j + 1].getBlue()) / 9;


                bi.setRGB(i, j, new Color(r, g, b).getRGB());
            }
        }

        return bi;
    }

    public static BufferedImage filtroMediana(BufferedImage image) {
        BufferedImage bi = image;

        Color[][] cores = separaRGB(image);

        int[] vet = new int[9];

        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {

                vet[0] = cores[i - 1][j - 1].getRed();
                vet[1] = cores[i - 1][j].getRed();
                vet[2] = cores[i - 1][j + 1].getRed();
                vet[3] = cores[i][j - 1].getRed();
                vet[4] = cores[i][j].getRed();
                vet[5] = cores[i][j + 1].getRed();
                vet[6] = cores[i + 1][j - 1].getRed();
                vet[7] = cores[i + 1][j].getRed();
                vet[8] = cores[i + 1][j + 1].getRed();
                int r = ordena(vet);

                vet[0] = cores[i - 1][j - 1].getGreen();
                vet[1] = cores[i - 1][j].getGreen();
                vet[2] = cores[i - 1][j + 1].getGreen();
                vet[3] = cores[i][j - 1].getGreen();
                vet[4] = cores[i][j].getGreen();
                vet[5] = cores[i][j + 1].getGreen();
                vet[6] = cores[i + 1][j - 1].getGreen();
                vet[7] = cores[i + 1][j].getGreen();
                vet[8] = cores[i + 1][j + 1].getGreen();
                int g = ordena(vet);

                vet[0] = cores[i - 1][j - 1].getBlue();
                vet[1] = cores[i - 1][j].getBlue();
                vet[2] = cores[i - 1][j + 1].getBlue();
                vet[3] = cores[i][j - 1].getBlue();
                vet[4] = cores[i][j].getBlue();
                vet[5] = cores[i][j + 1].getBlue();
                vet[6] = cores[i + 1][j - 1].getBlue();
                vet[7] = cores[i + 1][j].getBlue();
                vet[8] = cores[i + 1][j + 1].getBlue();
                int b = ordena(vet);
                bi.setRGB(i, j, new Color(r, g, b).getRGB());
            }
        }
        return image;
    }

    public static BufferedImage filtroHighBoost(int ordem, BufferedImage image) {
         BufferedImage bi = image;

        Color[][] cores = separaRGB(image);

        float[][] mascara = new float[3][3];
        mascara[0][0] = -1;
	mascara[0][1] = -1;
	mascara[0][2] = -1;
	mascara[1][0] = -1;
	mascara[1][1] = 9*ordem - 1;
	mascara[1][2] = -1;
	mascara[2][0] = -1;
	mascara[2][1] = -1;
	mascara[2][2] = -1;

        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {

                int r =(int) ((cores[i - 1][j - 1].getRed()*mascara[0][0] + cores[i - 1][j].getRed()*mascara[0][1] + cores[i - 1][j + 1].getRed()*mascara[0][2]
                + cores[i][j - 1].getRed()*mascara[1][0]  + cores[i][j].getRed()*mascara[1][1]  + cores[i][j + 1].getRed()*mascara[1][2]
                + cores[i + 1][j - 1].getRed()*mascara[2][0]  + cores[i + 1][j].getRed()*mascara[2][1]  + cores[i + 1][j + 1].getRed()*mascara[2][2] ))/9;

                int g =(int)  ((cores[i - 1][j - 1].getGreen()*mascara[0][0] + cores[i - 1][j].getGreen()*mascara[0][1] + cores[i - 1][j + 1].getGreen()*mascara[0][2]
                + cores[i][j - 1].getGreen()*mascara[1][0]  + cores[i][j].getGreen()*mascara[1][1]  + cores[i][j + 1].getGreen()*mascara[1][2]
                + cores[i + 1][j - 1].getGreen()*mascara[2][0]  + cores[i + 1][j].getGreen()*mascara[2][1]  + cores[i + 1][j + 1].getGreen()*mascara[2][2] )/9);

                int b =(int) ((cores[i - 1][j - 1].getBlue()*mascara[0][0] + cores[i - 1][j].getBlue()*mascara[0][1] + cores[i - 1][j + 1].getBlue()*mascara[0][2]
                + cores[i][j - 1].getBlue()*mascara[1][0]  + cores[i][j].getBlue()*mascara[1][1]  + cores[i][j + 1].getBlue()*mascara[1][2]
                + cores[i + 1][j - 1].getBlue()*mascara[2][0]  + cores[i + 1][j].getBlue()*mascara[2][1]  + cores[i + 1][j + 1].getBlue()*mascara[2][2] )/9);

                r = r > 255 ? 255 : r;
                g = g > 255 ? 255 : g;
                b = b > 255 ? 255 : b;
                
                r = r < 0 ? 0 : r;
                g = g < 0 ? 0 : g;
                b = b < 0 ? 0 : b;
                
                bi.setRGB(i, j, new Color(r, g, b).getRGB());
            }
        }

        return bi;
    }

    public static BufferedImage filtroRoberts(BufferedImage image) {
        BufferedImage bi = image;
        BufferedImage bi_ve = image;
        BufferedImage bi_ho = image;

        Color[][] cores = separaRGB(image);

        int[][] ve = new int[3][3];
        ve[0][0] = 0;
        ve[0][1] = 0;
        ve[0][2] = -1;
        ve[1][0] = 0;
        ve[1][1] = 1;
        ve[1][2] = 0;
        ve[2][0] = 0;
        ve[2][1] = 0;
        ve[2][2] = 0;

        int[][] ho = new int[3][3];
        ho[0][0] = -1;
        ho[0][1] = 0;
        ho[0][2] = 0;
        ho[1][0] = 0;
        ho[1][1] = 1;
        ho[1][2] = 0;
        ho[2][0] = 0;
        ho[2][1] = 0;
        ho[2][2] = 0;

        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {

                 int r =(int) (cores[i - 1][j - 1].getRed()*ve[0][0] + cores[i - 1][j].getRed()*ve[0][1] + cores[i - 1][j + 1].getRed()*ve[0][2]
                + cores[i][j - 1].getRed()*ve[1][0]  + cores[i][j].getRed()*ve[1][1]  + cores[i][j + 1].getRed()*ve[1][2]
                + cores[i + 1][j - 1].getRed()*ve[2][0]  + cores[i + 1][j].getRed()*ve[2][1]  + cores[i + 1][j + 1].getRed()*ve[2][2] );

                int g =(int)  (cores[i - 1][j - 1].getGreen()*ho[0][0] + cores[i - 1][j].getGreen()*ho[0][1] + cores[i - 1][j + 1].getGreen()*ho[0][2]
                + cores[i][j - 1].getGreen()*ho[1][0]  + cores[i][j].getGreen()*ho[1][1]  + cores[i][j + 1].getGreen()*ho[1][2]
                + cores[i + 1][j - 1].getGreen()*ho[2][0]  + cores[i + 1][j].getGreen()*ho[2][1]  + cores[i + 1][j + 1].getGreen()*ho[2][2] );

                int b =(int) (cores[i - 1][j - 1].getBlue()*ve[0][0] + cores[i - 1][j].getBlue()*ve[0][1] + cores[i - 1][j + 1].getBlue()*ve[0][2]
                + cores[i][j - 1].getBlue()*ve[1][0]  + cores[i][j].getBlue()*ve[1][1]  + cores[i][j + 1].getBlue()*ve[1][2]
                + cores[i + 1][j - 1].getBlue()*ve[2][0]  + cores[i + 1][j].getBlue()*ve[2][1]  + cores[i + 1][j + 1].getBlue()*ve[2][2] );

                r = r > 255 ? 255 : r;
                g = g > 255 ? 255 : g;
                b = b > 255 ? 255 : b;
                
                r = r < 0 ? 0 : r;
                g = g < 0 ? 0 : g;
                b = b < 0 ? 0 : b;
//                System.out.println("R "+r+"\tG "+g+"\tB "+b);
                
                bi_ve.setRGB(i, j, new Color(r, g, b).getRGB());
            }
        }
        
        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {

                 int r =(int) (cores[i - 1][j - 1].getRed()*ho[0][0] + cores[i - 1][j].getRed()*ho[0][1] + cores[i - 1][j + 1].getRed()*ho[0][2]
                + cores[i][j - 1].getRed()*ho[1][0]  + cores[i][j].getRed()*ho[1][1]  + cores[i][j + 1].getRed()*ho[1][2]
                + cores[i + 1][j - 1].getRed()*ho[2][0]  + cores[i + 1][j].getRed()*ho[2][1]  + cores[i + 1][j + 1].getRed()*ho[2][2] );

                int g =(int)  (cores[i - 1][j - 1].getGreen()*ho[0][0] + cores[i - 1][j].getGreen()*ho[0][1] + cores[i - 1][j + 1].getGreen()*ho[0][2]
                + cores[i][j - 1].getGreen()*ho[1][0]  + cores[i][j].getGreen()*ho[1][1]  + cores[i][j + 1].getGreen()*ho[1][2]
                + cores[i + 1][j - 1].getGreen()*ho[2][0]  + cores[i + 1][j].getGreen()*ho[2][1]  + cores[i + 1][j + 1].getGreen()*ho[2][2] );

                int b =(int) (cores[i - 1][j - 1].getBlue()*ho[0][0] + cores[i - 1][j].getBlue()*ho[0][1] + cores[i - 1][j + 1].getBlue()*ho[0][2]
                + cores[i][j - 1].getBlue()*ho[1][0]  + cores[i][j].getBlue()*ho[1][1]  + cores[i][j + 1].getBlue()*ho[1][2]
                + cores[i + 1][j - 1].getBlue()*ho[2][0]  + cores[i + 1][j].getBlue()*ho[2][1]  + cores[i + 1][j + 1].getBlue()*ho[2][2] );

                r = r > 255 ? 255 : r;
                g = g > 255 ? 255 : g;
                b = b > 255 ? 255 : b;
                
                r = r < 0 ? 0 : r;
                g = g < 0 ? 0 : g;
                b = b < 0 ? 0 : b;
                
                bi_ho.setRGB(i, j, new Color(r, g, b).getRGB());
            }
        }
        Color[][] cores_ve = separaRGB(bi_ve);
        Color[][] cores_ho = separaRGB(bi_ho);
        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {
               int r = (int) (Math.sqrt(Math.pow(cores_ve[i][j].getRed(), 2) + Math.pow(cores_ho[i][j].getRed(), 2)));
               int g = (int) (Math.sqrt(Math.pow(cores_ve[i][j].getGreen(), 2) + Math.pow(cores_ho[i][j].getGreen(), 2)));
               int b = (int) (Math.sqrt(Math.pow(cores_ve[i][j].getBlue(), 2) + Math.pow(cores_ho[i][j].getBlue(), 2)));
                
               r = r > 255 ? 255 : r;
               g = g > 255 ? 255 : g;
               b = b > 255 ? 255 : b;
                
               r = r < 0 ? 0 : r;
               g = g < 0 ? 0 : g;
               b = b < 0 ? 0 : b;
               
               bi.setRGB(i, j, new Color(r, g, b).getRGB());
            }
        }

        return bi;
    }

    private static Color[][] separaRGB(BufferedImage image) {

        Color[][] cores = new Color[image.getWidth()][image.getHeight()];

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                int rgb = image.getRGB(i, j);

                int r = ((rgb >> 16) & 0x00ff);
                int g = ((rgb >> 8) & 0x0000ff);
                int b = (rgb & 0x000000ff);

                cores[i][j] = new Color(r, g, b);
            }
        }
        return cores;
    }

    private static int ordena(int[] vet) {
        int chave = 0;
        int i = 0;
        for (int j = 1; j < 9; j++) {
            chave = vet[j];
            i = j - 1;
            while (i >= 0 && vet[i] > chave) {
                vet[i + 1] = vet[i];
                i--;
            }
            vet[i + 1] = chave;
        }
        return vet[4];
    }
}
