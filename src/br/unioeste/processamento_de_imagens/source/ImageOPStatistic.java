/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.processamento_de_imagens.source;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

/**
 *
 * @author Henrique
 */
public class ImageOPStatistic {

    public static List<JFreeChart> gerarHistograma(BufferedImage i) {
        switch (i.getType()) {
            case BufferedImage.TYPE_3BYTE_BGR:
                return histograma3Bytergb(i);

            case BufferedImage.TYPE_4BYTE_ABGR:
                System.out.println("4byte_ABGR");
                break;
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                System.out.println("4byte_ABGR_pre");
                break;
            case BufferedImage.TYPE_BYTE_BINARY:
                System.out.println("Byte_Binary");
                break;
            case BufferedImage.TYPE_BYTE_GRAY:
                return histogramaByteGray(i);

            case BufferedImage.TYPE_BYTE_INDEXED:
                return histogramaIndexedByte(i);
                
            case BufferedImage.TYPE_CUSTOM:
                System.out.println("type_custom");
                break;
            case BufferedImage.TYPE_INT_ARGB:
                System.out.println("Int_ARGB");
                break;
            case BufferedImage.TYPE_INT_BGR:
                System.out.println("Int_BGR");
                break;
            case BufferedImage.TYPE_INT_RGB:
                histogramaIntrgb(i);
        }
        return null;

    }

    private static List<JFreeChart> histogramaByteGray(BufferedImage image) {

        int[] pixels = new int[image.getWidth() * image.getHeight()];
        double[] aux = new double[image.getWidth()*image.getHeight()];

        pixels = image.getData().getPixels(0, 0, image.getWidth(), image.getHeight(), pixels);

        for (int j = 0; j < pixels.length; j++) {
            aux[j] = pixels[j];
        }

        HistogramDataset hds = new HistogramDataset();
        hds.addSeries("Distribuição de píxel", aux, 1000);
        JFreeChart chart = ChartFactory.createHistogram("Intensidade de Cinza", "Intensidade", "Acumulado",
                hds, PlotOrientation.VERTICAL, true, true, true);
        chart.setAntiAlias(true);
        List<JFreeChart> lbi = new ArrayList<JFreeChart>();
        lbi.add(chart);
        return lbi;
    }

    private static List<JFreeChart> histogramaIndexedByte(BufferedImage i) {
        int[] pixels = new int[i.getWidth() * i.getHeight()];
        double[] aux = new double[pixels.length];
        System.out.println("pixels.length "+pixels.length);
        i.getRGB(0, 0, i.getWidth(), i.getHeight(), pixels, 0, i.getWidth());

        for (int j = 0; j < pixels.length; j++) {
            aux[j] = ((pixels[j] >> 8) & 0xFF);

        }
        
        HistogramDataset hds = new HistogramDataset();
        hds.addSeries("Distribuição de píxel", aux, 256);
        JFreeChart chart = ChartFactory.createHistogram("Intensidade de Cinza", "Intensidade", "Acumulado",
                hds, PlotOrientation.VERTICAL, true, true, true);
        chart.setAntiAlias(true);
        List<JFreeChart> lbi = new ArrayList<JFreeChart>();
        lbi.add(chart);
        return lbi;
    }

    private static List<JFreeChart> histograma3Bytergb(BufferedImage image) {

        int[] pixel = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixel, 0, image.getWidth());

        double[] red = new double[pixel.length];
        double[] green = new double[pixel.length];
        double[] blue = new double[pixel.length];


        for (int h = 0; h < pixel.length; h++) {
            red[h] = ((pixel[h] >> 16) & 0xff);
            green[h] = (pixel[h] >> 8) & 0xff;
            blue[h] = (pixel[h]) & 0xff;
        }

        HistogramDataset hds = new HistogramDataset();
        List<JFreeChart> lbi = new ArrayList<JFreeChart>();
        hds.addSeries("Canal vermelho", red, 1000);
        JFreeChart chart = ChartFactory.createHistogram("Distribuição de Vermelho", "Intensidade", "Acumulado",
                hds, PlotOrientation.VERTICAL, true, true, true);
        chart.setAntiAlias(true);
        lbi.add(chart);

        hds = new HistogramDataset();
        hds.addSeries("Canal verde", green, 1000);
        chart = ChartFactory.createHistogram("Distribuição de Verde", "Intensidade", "Acumulado",
                hds, PlotOrientation.VERTICAL, true, true, true);
        chart.setAntiAlias(true);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.GREEN);
        lbi.add(chart);

        hds = new HistogramDataset();
        hds.addSeries("Canal Azul", blue, 1000);
        chart = ChartFactory.createHistogram("Distribuição de Azul", "Intensidade", "Acumulado",
                hds, PlotOrientation.VERTICAL, true, true, true);
        chart.setAntiAlias(true);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.BLUE);
        lbi.add(chart);

        return lbi;
    }

    private static List<JFreeChart> histogramaIntrgb(BufferedImage i) {
        int[] aux = null;

        int[] pixel = i.getData().getPixels(0, 0, i.getWidth(), i.getHeight(), aux);
        double[] red = new double[(int) (pixel.length / 3 + 0.5)];
        double[] green = new double[(int) (pixel.length / 3 + 0.5)];
        double[] blue = new double[(int) (pixel.length / 3 + 0.5)];

        int h = 0;
        for (int index = 0; h < pixel.length & index < blue.length; index++) {
            red[index] = pixel[++h];
            green[index] = pixel[++h];
            blue[index] = pixel[h];

            h += 2;
        }

        HistogramDataset hds = new HistogramDataset();
        List<JFreeChart> lbi = new ArrayList<JFreeChart>();
        hds.addSeries("Canal vermelho", red, 1000);
        JFreeChart chart = ChartFactory.createHistogram("Distribuição de Vermelho", "Intensidade", "Acumulado",
                hds, PlotOrientation.VERTICAL, true, true, true);
        chart.setAntiAlias(true);
        lbi.add(chart);

        hds = new HistogramDataset();
        hds.addSeries("Canal verde", green, 1000);
        chart = ChartFactory.createHistogram("Distribuição de Verde", "Intensidade", "Acumulado",
                hds, PlotOrientation.VERTICAL, true, true, true);
        chart.setAntiAlias(true);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.GREEN);
        lbi.add(chart);

        hds = new HistogramDataset();
        hds.addSeries("Canal Azul", blue, 1000);
        chart = ChartFactory.createHistogram("Distribuição de Azul", "Intensidade", "Acumulado",
                hds, PlotOrientation.VERTICAL, true, true, true);
        chart.setAntiAlias(true);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.BLUE);
        lbi.add(chart);

        return lbi;
    }

    private static int[] mediaByteGray(BufferedImage image) {
        int[] aux = null;
        int[] pixelValue = image.getData().getPixels(0, 0, image.getWidth(), image.getHeight(), aux);
        int[] sum = new int[1];

        for (int i = 0; i < pixelValue.length; i++) {
            sum[0] += pixelValue[i];
        }

        sum[0] = sum[0] / pixelValue.length;
        return sum;
    }

    private static int[] media3ByteBGR(BufferedImage image) {

        int[] aux = null;
        int[] pixelValue = image.getData().getPixels(0, 0, image.getWidth(), image.getHeight(), aux);
        int[] sum = new int[3];
        int red = 0;
        int green = 0;
        int blue = 0;

        int h = 0;
        for (int index = 0; h < pixelValue.length & index < (int) (pixelValue.length / 3 + 0.5); index++) {
            blue += pixelValue[h];
            green += pixelValue[++h];
            red += pixelValue[++h];
            h++;
        }

        red = red / (pixelValue.length / 3);
        blue = blue / (pixelValue.length / 3);
        green = green / (pixelValue.length / 3);


        switch (image.getType()) {
            case BufferedImage.TYPE_3BYTE_BGR:
                sum[0] = red;
                sum[1] = blue;
                sum[2] = green;
                break;
            case BufferedImage.TYPE_INT_RGB:
                sum[2] = red;
                sum[1] = blue;
                sum[0] = green;
                break;
        }
        return sum;
    }

    public static int[] calcMedia(BufferedImage i) {
        switch (i.getType()) {
            case BufferedImage.TYPE_3BYTE_BGR:
                return media3ByteBGR(i);
            case BufferedImage.TYPE_4BYTE_ABGR:
                System.out.println("4byte_ABGR");
                break;
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                System.out.println("4byte_ABGR_pre");
                break;
            case BufferedImage.TYPE_BYTE_BINARY:
                System.out.println("Byte_Binary");
                break;
            case BufferedImage.TYPE_BYTE_GRAY:
                return mediaByteGray(i);

            case BufferedImage.TYPE_BYTE_INDEXED:
                return mediaByteGray(i);

            case BufferedImage.TYPE_CUSTOM:
                System.out.println("type_custom");
                break;
            case BufferedImage.TYPE_INT_ARGB:
                System.out.println("Int_ARGB");
                break;
            case BufferedImage.TYPE_INT_BGR:
                System.out.println("Int_BGR");
                break;
            case BufferedImage.TYPE_INT_RGB:
                return media3ByteBGR(i);
        }
        return null;
    }

    private static int[] varianciaByteGray(BufferedImage image) {
        int[] aux = null;
        int[] pixelValue = image.getData().getPixels(0, 0, image.getWidth(), image.getHeight(), aux);
        int[] sum = new int[1];
        int media = mediaByteGray(image)[0];

        sum[0] = 0;
        for (int i = 0; i < pixelValue.length; i++) {
            sum[0] += (Math.pow(pixelValue[i] - media, 2));
//            //System.out.println(""+sum);
        }
//
        sum[0] = sum[0] / pixelValue.length;
//        return sum;

        return sum;
    }

    private static int[] variancia3ByteBGR(BufferedImage image) {
        int[] aux = null;
        int[] pixelValue = image.getData().getPixels(0, 0, image.getWidth(), image.getHeight(), aux);
        int[] sum = new int[3];
        int red = 0;
        int green = 0;
        int blue = 0;

        int[] media = media3ByteBGR(image);
        int h = 0;
        switch (image.getType()) {
            case BufferedImage.TYPE_3BYTE_BGR:
                for (int index = 0; h < pixelValue.length & index < (int) (pixelValue.length / 3 + 0.5); index++) {
                    blue += (Math.pow(pixelValue[h] - media[2], 2));
                    green += (Math.pow(pixelValue[++h] - media[1], 2));
                    red += (Math.pow(pixelValue[++h] - media[0], 2));
                    h++;
                }
                red = red / (pixelValue.length / 3);
                blue = blue / (pixelValue.length / 3);
                green = green / (pixelValue.length / 3);
                sum[0] = red;
                sum[1] = blue;
                sum[2] = green;
                return sum;
            case BufferedImage.TYPE_INT_RGB:
                for (int index = 0; h < pixelValue.length & index < (int) (pixelValue.length / 3 + 0.5); index++) {
                    blue += (Math.pow(pixelValue[h] - media[0], 2));
                    green += (Math.pow(pixelValue[++h] - media[1], 2));
                    red += (Math.pow(pixelValue[++h] - media[2], 2));
                    h++;
                }
                red = red / (pixelValue.length / 3);
                blue = blue / (pixelValue.length / 3);
                green = green / (pixelValue.length / 3);
                sum[2] = red;
                sum[1] = blue;
                sum[0] = green;
                return sum;
        }
        return null;

    }

    public static int[] calcVariancia(BufferedImage i) {
        switch (i.getType()) {
            case BufferedImage.TYPE_3BYTE_BGR:
                return variancia3ByteBGR(i);
            case BufferedImage.TYPE_4BYTE_ABGR:
                System.out.println("4byte_ABGR");
                break;
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                System.out.println("4byte_ABGR_pre");
                break;
            case BufferedImage.TYPE_BYTE_BINARY:
                System.out.println("Byte_Binary");
                break;
            case BufferedImage.TYPE_BYTE_GRAY:
                return varianciaByteGray(i);

            case BufferedImage.TYPE_BYTE_INDEXED:
                return varianciaByteGray(i);

            case BufferedImage.TYPE_CUSTOM:
                System.out.println("type_custom");
                break;
            case BufferedImage.TYPE_INT_ARGB:
                System.out.println("Int_ARGB");
                break;
            case BufferedImage.TYPE_INT_BGR:
                System.out.println("Int_BGR");
                break;
            case BufferedImage.TYPE_INT_RGB:
                return variancia3ByteBGR(i);
        }
        return null;
    }

}
