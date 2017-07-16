package br.unioeste.processamento_de_imagens.source.fft;

/**
 *
 * @author Hugo
 */
public class TransformadaFourier {

    public static Complex[] fft(Complex[] x) {
        int length = x.length;

        if (length == 1) {
            return new Complex[]{x[0]};
        }


        Complex[] pares = new Complex[length / 2];
        for (int k = 0; k < length / 2; k++) {
            pares[k] = x[2 * k];
        }
        Complex[] q = fft(pares);

        Complex[] impares = pares;
        for (int k = 0; k < length / 2; k++) {
            impares[k] = x[2 * k + 1];
        }
        Complex[] r = fft(impares);

        Complex[] y = new Complex[length];
        for (int k = 0; k < length / 2; k++) {
            double kth = -2 * k * Math.PI / length;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].plus(wk.times(r[k]));
            y[k + length / 2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }

    public static Complex[] ifft(Complex[] x) {
        int N = x.length;
        Complex[] y = new Complex[N];

        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugate();
        }

        y = fft(y);

        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(1.0 / N);
        }
        return y;

    }
}
