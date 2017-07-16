package br.unioeste.processamento_de_imagens.source;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class TransformadaFourier2 {

    private static double[] data;
    private static int[][] matriz;
    private static int[][] matriz2;
    private static int altura;
    private static int largura;
    private static int z;

    public static double[] dimensao(BufferedImage image) {
        int i = image.getWidth();
        int j = image.getHeight();
        if ((j % 2 != 0) && (i % 2 != 0)) {
            if (i % 2 != 0) {
                i++;
            }
            if (j % 2 != 0) {
                j++;
            }
            BufferedImage bi = image;
            Color[][] cores = separaRGB(image);
            for (int x = 1; x < image.getWidth() - 1; x++) {
                int r = 0;
                int g = 0;
                int b = 0;
                bi.setRGB(x, j, new Color(r, g, b).getRGB());
            }
            dimensao(image);
        }
        altura = i;
        largura = 2 * j;
        matriz2 = new int[i][j];
        matriz = new int[altura][largura];
        Color[][] cores = separaRGB(image);
        for (int x = 0; x < i; x++) {
            for (int y = 0; y < j; y++) {
                int r = cores[x][y].getRed();
                int g = cores[x][y].getGreen();
                int b = cores[x][y].getBlue();
                matriz2[x][y] = (int) (r + g + b) / 3;
            }
        }
        int h = 0;
        for (int x = 0; x < i; x++) {
            for (int y = 0; y < j; y++) {
                if (y % 2 == 0) {
                    matriz[x][y] = matriz2[x][y / 2];
                    h++;
                } else {
                    matriz[x][y] = 0;
                }
//                    System.out.println(matriz[x][y]);
            }
        }
        data = new double[2 * i * j + 1];
        z = 0;
        data[z] = 0.0;
        z++;
        for (int x = 0; x < i; x++) {
            for (int y = 0; y < largura; y++) {
                data[z] = (double) matriz[x][y];
//                System.out.println(data[z]);
                z++;
            }
        }
//        for (int x = 0; x < z; x++) {
//            System.out.println(data[x] + " ");
//        }

        return data;
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

    public static int getAltura() {
        return altura;
    }

    public static int getLargura() {
        return largura;
    }

    public static long[] getNN() {
        long[] nn = new long[3];
        nn[0] = 0;
        nn[1] = getAltura();
        nn[2] = getLargura();
        return nn;
    }

    //nn (0,altura.largura) sinal 1 ou -1  ndin =2
    public static void transformada(long[] nn, int sinal, int ndim) {
        int idim;
        long i1, i2, i3, i2rev, i3rev, ip1, ip2, ip3, ifp1, ifp2;
        long ibit, n, nprev, nrem, ntot;
        int k1, k2;
        double tempi, tempr;
        double theta, wi, wpi, wpr, wr, wtemp;

        for (ntot = 1, idim = 1; idim <= ndim; idim++) {
            ntot *= nn[idim];
        }

        nprev = 1;
        int h = 0;
        for (idim = ndim; idim >= 1; idim--) {

            n = nn[idim];
            nrem = ntot / (n * nprev);
            ip1 = nprev << 1;
            ip2 = ip1 * n;
            ip3 = ip2 * nrem;
            i2rev = 1;
            h++;
            //System.out.println("entrou "+h);
            for (i2 = 1; i2 <= ip2; i2 += ip1) {

                if (i2 < i2rev) {
                    for (i1 = i2; i1 <= i2 + ip1 - 2; i1 += 2) {
                        for (i3 = i1; i3 <= ip3; i3 += ip2) {
                            i3rev = i2rev + i3 - i2;
                            SWAP((int) i3, (int) i3rev);
                            SWAP((int) i3 + 1, (int) i3rev + 1);
                        }
                    }
                }

                ibit = ip2 >> 1;

                while (ibit >= ip1 && i2rev > ibit) {
                    i2rev -= ibit;
                    ibit >>= 1;
                }

                i2rev += ibit;
            }
            //System.out.println("saiu swap");
            ifp1 = ip1;

            while (ifp1 < ip2) {

                ifp2 = ifp1 << 1;
                theta = sinal * 6.28318530717959 / (ifp2 / ip1);
                wtemp = Math.sin(0.5 * theta);
                wpr = -2.0 * wtemp * wtemp;
                wpi = Math.sin(theta);
                wr = 1.0;
                wi = 0.0;

                for (i3 = 1; i3 <= ifp1; i3 += ip1) {
                    for (i1 = i3; i1 <= i3 + ip1 - 2; i1 += 2) {
                        for (i2 = i1; i2 <= ip3; i2 += ifp2) {
                            k1 = (int) i2;
                            k2 = (int) (k1 + ifp1);
                            if ((k1 >= z) || (k2 >= z)) {
                            } else {
                                tempr = ((float) wr * data[k2] - (float) wi * data[k2 + 1]);
                                tempi = (float) wr * data[k2 + 1] + (float) wi * data[k2];
                                data[k2] = data[k1] - tempr;
                                data[k2 + 1] = data[k1 + 1] - tempi;
                                data[k1] += tempr;
                                data[k1 + 1] += tempi;
                            }
                        }
                    }

                    wr = (wtemp = wr) * wpr - wi * wpi + wr;
                    wi = wi * wpr + wtemp * wpi + wi;
                }
                //System.out.println("saiu");
                ifp1 = ifp2;
            }
            nprev *= n;
        }
        //System.out.println("ACABOU");
    }

    public static void SWAP(int a, int b) {
//        System.out.println("a: " + a);
//        System.out.println("b: " + b);
        if ((a >= z) || (b >= z)) {
        } else {
            double temp = data[a];
            data[a] = data[b];
            data[b] = temp;
        }
    }

    public static void Centraliza() {
        int k = 1; //salta a posição 0 do vetor M (M é a imagem em notação complexa equivalente ao vetor Data)
        //if (Center == 1){ //Center = 1 --> centralizar o espectro
        for (int i = 0; i < altura; i++) { //altura da imagem envelopada
            for (int j = 0; j < largura/2; j++) { //largura da imagem envelopada
                if ((i + j) % 2 == 0) {
                    data[k-1] = -data[k-1]; //M é a imagem já em notação complexa e linearizada
                    data[k] = -data[k];
                }
                k += 2;
            }
        }

    }
//  

    public static BufferedImage Espectro() {
        double[] teste = new double[z / 2 - 1];
        Centraliza();
        System.out.println(z);
        for (int i = 1; i < z / 2; i++) {
//            System.out.println("data "+i*2+": "+data[i*2]);
//            System.out.println("data "+i*2+1+": "+data[i*2+1]);
            double f = Math.pow(data[i * 2], 2) + Math.pow(data[(i * 2) + 1], 2);
            f = Math.sqrt(f);
            f = Math.sqrt(f);
//            teste[j] = Math.log10(Math.sqrt(Math.sqrt(Math.pow(data[i], 2) + Math.pow(data[i + 1], 2))));
            teste[i - 1] = Math.log10(f);
//            System.out.println("teste "+i+": "+teste[i]);
        }
        teste = autoEscala(teste);
        BufferedImage image = new BufferedImage(largura / 2, altura, BufferedImage.TYPE_INT_RGB);
        int x = 0;
        for (int i = 0; i < largura / 2 - 1; i++) {
            for (int k = 0; k < altura; k++) {
                Color cor;
                if (teste[x] > 255) {
                    cor = new Color(255, 255, 255);
                } else {
                    if (teste[x] < 0) {
                        cor = new Color(0, 0, 0);
                    } else {
                        cor = new Color((int) teste[x], (int) teste[x], (int) teste[x]);
                    }
                }

                image.setRGB(i, k, cor.getRGB());
                x++;
            }
        }
        return image;
    }

    public static double[] autoEscala(double[] d) {
        double max, min;
        max = -9999999;
        min = 9999999;
        for (int i = 1; i < z / 2 - 1; i = i + 2) {
            if (d[i] > max) {
                max = d[i];
            }
            if (d[i] < min) {
                min = d[i];
            }
        }
        for (int i = 0; i < z / 2 - 1; i = i + 2) {
            d[i] = (255 / (max - min)) * (d[i] - min);
        }
        return d;
    }

    public void escrever() {
        System.out.println(data.toString());
    }
}
