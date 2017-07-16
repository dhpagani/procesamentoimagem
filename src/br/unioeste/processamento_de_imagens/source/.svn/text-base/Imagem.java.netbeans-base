/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unioeste.processamento_de_imagens.source;

import java.awt.image.BufferedImage;

/**
 *
 * @author Henrique
 */
public class Imagem {

    private BufferedImage image;
    private static Imagem gi = null;

    public static Imagem getInstance(){
        if(gi == null) {
            gi = new Imagem();
        }
        return gi;
    }

    private Imagem(){
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public void setBufferedImage(BufferedImage image) {
        this.image = image;
    }

    public String getTypeImage(){
        switch(Imagem.getInstance().getBufferedImage().getType()){
            case BufferedImage.TYPE_3BYTE_BGR:
                return "Tipo de imagem: 3BYTE_BGR";
            case BufferedImage.TYPE_4BYTE_ABGR:
                return "Tipo de imagem: 4BYTE_ABGR";
            case BufferedImage.TYPE_BYTE_GRAY:
                return "Tipo de imagem: BYTE_GRAY";
            case BufferedImage.TYPE_BYTE_INDEXED:
                return "Tipo de imagem: BYTE_INDEXED";
            case BufferedImage.TYPE_INT_RGB:
                return "Tipo de imagem: INT_RGB";
        }
        return "";
    }

    public int getWidth(){
        return image.getWidth();
    }

    public int getHeight(){
        return image.getHeight();
    }

    public int getNumComponents(){
        return image.getColorModel().getNumComponents();
    }

    public int getPixelSize(){
        return image.getColorModel().getPixelSize();
    }
}
