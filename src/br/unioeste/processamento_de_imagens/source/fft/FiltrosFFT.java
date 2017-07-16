/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.processamento_de_imagens.source.fft;

import br.unioeste.processamento_de_imagens.source.Imagem;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author Henrique
 */
public class FiltrosFFT {
 
 
    public void passaBaixaOuAlta(Complex[] vetor, int raio, boolean alta, int centroX, int centroY) {
        if (raio > Imagem.getInstance().getWidth() / 2) {
            System.out.println("Atencao, diametro maior que imagem");
        }
        for (int i = 0; i < Imagem.getInstance().getWidth(); i++) {
            for (int j = 0; j < Imagem.getInstance().getHeight(); j++) {
                double distancia = Math.sqrt(Math.pow((i - centroX), 2) + Math.pow(j - centroY, 2));
                if (distancia > raio) {
                    if (!alta) {
                        vetor[(i * Imagem.getInstance().getWidth()) + j] = new Complex(0, 0);
                    }
                } else {
                    if (alta) {
                        vetor[(i * Imagem.getInstance().getWidth()) + j] = new Complex(0, 0);
                    }
                }
            }
        }
    }

    public void passaBaixa(Complex[] vetor, int raio, int centroX, int centroY) {
        passaBaixaOuAlta(vetor, raio, false, centroX, centroY);
    }

    public void passaAlta(Complex[] vetor, int raio, int centroX, int centroY) {
        passaBaixaOuAlta(vetor, raio, true, centroX, centroY);
    }

    public void passaBanda(Complex[] vetor, int raioAlta, int raioBaixa, int centroX, int centroY) {
        if (raioAlta > raioBaixa) {
            System.out.println("Atencao, raio do passa alta eh maior que o raio do filtro passa baixa");
        }
        passaAlta(vetor, raioAlta, centroX, centroY);
        passaBaixa(vetor, raioBaixa, centroX, centroY);
    }

    public void setorAngular(Complex[] vetor, int r1x, int r1y, int r2x, int r2y) {
        int r1b, r2b, c = Imagem.getInstance().getWidth() / 2;
        double r1a, r2a;
        r1a = (r1y - c);
        r1a /= (r1x - c);
        r1b = (int) (c - r1a * c);
        r2a = (r2y - c);
        r2a /= (r2x - c);
        r2b = (int) (c - r2a * c);
        for (int i = 0; i < Imagem.getInstance().getWidth(); i++) {
            for (int j = 0; j < Imagem.getInstance().getHeight(); j++) {
                int YnaR1 = (int) (r1a * i + r1b);
                int YnaR2 = (int) (r2a * i + r2b);
                if (j > c) {
                    if ((j < YnaR1) && (j > YnaR2)) {
                        vetor[(i * Imagem.getInstance().getWidth()) + j] = new Complex(0, 0);
                    } else {
                    }
                } else if (j < c) {
                    if ((j > YnaR1) && (j < YnaR2)) {
                        vetor[(i * Imagem.getInstance().getWidth()) + j] = new Complex(0, 0);
                    } else {
                    }
                } else {
                    //nao faz nada por enquanto
                }
            }
        }
    }
}
