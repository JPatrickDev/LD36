package uk.co.jdpatrick.JEngine.Perlin;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * Created by Jack on 15/10/2014.
 */
public class PerlinNoise {


    /**
     *
     * Generates a float array of white noise
     *
     * @param width The width of the noise array
     * @param height The height of the noise array
     * @param r The random to use for generation
     * @return A float array of noise
     */
    public static float[][] generateWhiteNoise(int width, int height, Random r) {
        float[][] noise = new float[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                noise[i][j] = (float) r.nextDouble();
            }
        }

        return noise;
    }


    /**
     * Take an array of white noise and create a smooth version of the noise
     *
     * @param noise The starting white noise
     * @param octave The octave value to use for the smoothing
     * @return The float array of smooth noise
     */
    public static float[][] generateSmoothNoise(float[][] noise, int octave) {

        int w = noise.length;
        int h = noise[0].length;

        float[][] smoothNoise = new float[w][h];

        int sP = 1 << octave;
        float sF = 1.0f / sP;

        for (int i = 0; i < w; i++) {

            int s_i0 = (i / sP) * sP;
            int s_i1 = (s_i0 + sP) % w;
            float hB = (i - s_i0) * sF;

            for (int j = 0; j < h; j++) {

                int s_j0 = (j / sP) * sP;
                int s_j1 = (s_j0 + sP) % h;
                float vB = (j - s_j0) * sF;

                float top = interpolate(noise[s_i0][s_j0], noise[s_i1][s_j0], hB);

                float bottom = interpolate(noise[s_i0][s_j1], noise[s_i1][s_j1], hB);

                smoothNoise[i][j] = interpolate(top, bottom, vB);
            }
        }
        return smoothNoise;
    }


    /**
     * Generate a perlin noise float array
     *
     * @param noise The base white noise
     * @param oC The octave to use
     * @return A float array of perlin noise
     */
    public static float[][] generatePerlinNoise(float[][] noise, int oC) {
        int w = noise.length;
        int h = noise[0].length;

        float[][][] smoothNoise = new float[oC][][];

        float persistence = 0.5f;

        for (int i = 0; i < oC; i++) {
            smoothNoise[i] = generateSmoothNoise(noise, i);
        }

        float[][] perlinNoise = new float[w][h];

        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        for (int octave = oC - 1; octave > 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }


        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }
        return perlinNoise;
    }


    /**
     * Interpolate two values
     *
     * @param x0 The first value
     * @param x1 The second value
     * @param alpha The alpha value
     * @return An interpolated float
     */
    public static float interpolate(float x0, float x1, float alpha) {
        return x0 * (1 - alpha) + alpha * x1;
    }


    /**
     *
     * Display the output of the perlin nosie generation
     *
     * @param args Program arguments
     */
    public static void main(String[] args) {
        Random r = new Random();
        while (true) {

            float[][] whiteNoise = generateWhiteNoise(500, 500, r);

            float[][] perlin = generatePerlinNoise(whiteNoise, 6);

            int[] noise = null;

            BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);

            int[] pixels = new int[500 * 500];

            for (int x = 0; x != perlin.length; x++) {
                for (int y = 0; y != perlin[0].length; y++) {
                    float p = perlin[x][y];

                    Color c = getColor(Color.DARK_GRAY, Color.black, p);
                    pixels[x + y * 500] = c.hashCode();
                }
            }

            image.setRGB(0, 0, 500, 500, pixels, 0, 500);

            JOptionPane.showMessageDialog(null, null, "Test", JOptionPane.YES_NO_OPTION, new ImageIcon(image.getScaledInstance(500, 500, 0)));
        }

    }


    /**
     * Get the colour of a float value
     *
     * @param s The darkest colour
     * @param e The lightest colour
     * @param t The value
     * @return A colour representation of the float input
     */
    public static Color getColor(Color s, Color e, float t) {
        float u = 1 - t;
        int r = (int) (s.getRed() * u + e.getRed() * t);
        int g = (int) (s.getGreen() * u + e.getGreen() * t);
        int b = (int) (s.getBlue() * u + e.getBlue() * t);
        Color c = new Color(r, g, b).brighter().brighter();
        return c;
    }

}
