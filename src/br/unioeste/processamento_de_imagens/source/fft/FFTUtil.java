/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.processamento_de_imagens.source.fft;

import br.unioeste.processamento_de_imagens.controller.Controller;
import br.unioeste.processamento_de_imagens.source.Imagem;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Henrique
 */
public class FFTUtil {

    private static int width;
    private static int height;
    private static int length;

    public static Complex[] prepareImage(BufferedImage image, boolean center) {
        width = image.getWidth();
        height = image.getHeight();
        length = image.getWidth() * image.getHeight();
        int[] pxsOrig;

        //Verifica se imagem esta em tons de cinza com paleta de cores
        if (Imagem.getInstance().getBufferedImage().getType() != BufferedImage.TYPE_BYTE_INDEXED) {
            Controller.converterToGrayScale();
            System.out.println("Convertendo para tons de cinza");
        }

        if (!FFTUtil.isPotenciaDeDois(length)) {
            width = nextPowToTwo(width);
            height = nextPowToTwo(height);
            length = width * height;
            pxsOrig = new int[length];
            int[] rgb = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

            int[] newRGB = new int[length];
            System.arraycopy(rgb, 0, pxsOrig, 0, image.getWidth() * image.getHeight());

            System.out.println("Alocando o vetor como potência de 2: " + length);
            for (int i = length; i < length; i++) {
                newRGB[i] = 0;
            }

        } else {
            //atribui os pixels a um vetor de pixels
            pxsOrig = Imagem.getInstance().getBufferedImage().getRGB(0, 0, image.getWidth(), image.getHeight(),
                    null, 0, image.getWidth());
        }

        Complex[] dadosOrigCentralizados = new Complex[pxsOrig.length];
        Complex[] dadosOriginais = intToComplex(pxsOrig);

        //inicializa o vetor principal
        for (int i = 0; i < pxsOrig.length; i++) {
            dadosOriginais[i] = new Complex(pxsOrig[i], 0);
        }

        //faz uma copia do vetor original, para centralizar
        System.arraycopy(dadosOriginais, 0, dadosOrigCentralizados, 0, pxsOrig.length);

        if (center) {
            //centraliza o espectro
            int k = 0;

            System.out.println("Width: " + image.getWidth() + " Height " + image.getHeight());
            System.out.println("lenght esperado: " + width * height + " lenght real: " + pxsOrig.length);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (((i + j) % 2) == 1) {
                        dadosOrigCentralizados[k] = Complex.nega(dadosOriginais[k]);
                    }
                    k++;
                }
            }
        }

        return dadosOrigCentralizados;
    }

    public static BufferedImage generateSpectrum(Complex[] dadosFFT) {
        BufferedImage bi = Imagem.getInstance().getBufferedImage();

//        int width = Imagem.getInstance().getWidth();
//        int height = Imagem.getInstance().getHeight();
//        int length = width * height;

        //gera o espectro com o vetor dadosFiltros (os dados depois de passar pela FFT)
        double[] spectrum = new double[length];
        int x = 0;
        int y = 0;

        for (int i = 0; i < length; i++) {
            if (x == width) {
                y++;
                x = 0;
            }

            double aux1 = Math.pow(dadosFFT[i].re(), 2) + Math.pow(dadosFFT[i].im(), 2);
            double squirtle; //usado caso tenha que se fazer log de 0
            squirtle = Math.sqrt(aux1);
            if (squirtle == 0) {
                squirtle = 0.000000000001;
            }
            double aux2 = Math.log(squirtle);
            double aux3 = 1;
            spectrum[i] = (aux2 * aux3);
            x++;
        }
        int max = -1;
        int min = 999;
        for (int i = 0; i < length; i++) {
            spectrum[i] = (int) spectrum[i];
            max = (int) (spectrum[i] > max ? spectrum[i] : max);
            min = (int) (spectrum[i] < min ? spectrum[i] : min);
        }

        int[] autoEscala = new int[length];
        for (int i = 0; i < length; i++) {
            autoEscala[i] = (int) ((255.0 / (max - min)) * (spectrum[i] - min));
        }

        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                double p = autoEscala[(i * width) + j];
                if (p < 0) {
                    p = 0;
                } else if (p > 255) {
                    p = 255;
                }
                Color cor = new Color((int) p, (int) p, (int) p);
                bi.setRGB(i, j, cor.getRGB());
            }
        }
        return bi;
    }

    public static Complex[] intToComplex(int[] pixel) {

        Complex[] dadosOriginais = new Complex[pixel.length];

        //inicializa o vetor principal
        for (int i = 0; i < pixel.length; i++) {
            dadosOriginais[i] = new Complex(pixel[i], 0);
        }
        return dadosOriginais;
    }

    public static BufferedImage generateImage(Complex[] c, BufferedImage image) {
//        int length = image.getWidth() * image.getHeight();
        int[] pixels = new int[length];
        Complex[] dadosFinais = c;

//        System.arraycopy(c, 0, dadosFinais, 0, length);

        // Descentraliza o espectro
        int k = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (((i + j) % 2) == 1) {
                    dadosFinais[k] = Complex.nega(c[k]);
                }
                k++;
            }
        }

        for (int i = 0; i < length; i++) {
            pixels[i] = (int) (dadosFinais[i].re() + (dadosFinais[i].im()));
        }

        int max = -999;
        int min = 999;

        for (int i = 0; i < length; i++) {
            max = (pixels[i] & 0xFF) > max ? (pixels[i] & 0xFF) : max;
            min = (pixels[i] & 0xFF) < min ? (pixels[i] & 0xFF) : min;
        }

        for (int i = 0; i < length; i++) {
            int red = (int) ((255.0 / (max - min)) * ((pixels[i] & 0xFF) - min));

            pixels[i] = ((red) & 0xFF000000)
                    | ((red << 16) & 0x00FF0000)
                    | ((red << 8) & 0x0000FF00)
                    | (red & 0x000000FF);

        }

        image.setRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        return image;
    }

    public static boolean isPotenciaDeDois(int numero) {
        while (numero % 2 == 0) {
            numero = (numero / 2);
        }
        return numero == 1 ? true : false;
    }

    public static int nextPowToTwo(int value) {
        int newLength = 1;
        while (newLength < value) {
            newLength <<= 1;
        }
        return newLength;
    }
}
