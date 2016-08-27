package me.jack.LD36.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Jack on 27/08/2016.
 */
public class LevelGenerator {


    public static float[][] generateWhiteNoise(int width, int height, Random r) {


        float[][] noise = new float[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                noise[i][j] = (float) r.nextDouble();
            }
        }

        return noise;
    }


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


    public static float[][] generatePerlinNoise(float[][] noise, int oC,float persistence) {
        int w = noise.length;
        int h = noise[0].length;

        float[][][] smoothNoise = new float[oC][][];


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


    public static float interpolate(float x0, float x1, float alpha) {
        return x0 * (1 - alpha) + alpha * x1;
    }


    public static float[][] group(float[][] noise) {
        for (int x = 0; x != noise.length; x++) {
            for (int y = 0; y != noise[0].length; y++) {
                float p = noise[x][y];
                if (p > 0.5 && p < 0.52) {
                    noise[x][y] = 3;
                } else if (p > 0.52) {
                    noise[x][y] = 1;
                } else {
                    noise[x][y] = 2;
                }
            }
        }
        return noise;
    }


    public static void main(String[] args) {

        while (true) {
            int w = 250;

          //  float[][] whiteNoise = generateWhiteNoise(w, w, r);

//            float[][] perlin = generatePerlinNoise(whiteNoise, 6);
  //          perlin = group(perlin);

            Level level = generateLevel(w,w);

            BufferedImage image = new BufferedImage(w, w, BufferedImage.TYPE_INT_RGB);

            int[] pixels = new int[w * w];

            for (int x = 0; x != level.getW(); x++) {
                for (int y = 0; y != level.getH(); y++) {
                    int i = level.getTileAt(x,y);
                    int ii = level.getTileAtTop(x,y);
                    Color c = null;
                    if(i == 1){
                        c = Color.green;
                    }else if(i == 2){
                        c = Color.blue;
                    }else{
                        c = Color.yellow;
                    }

                    if(ii == 4){
                        c = Color.darkGray;
                    }
                    pixels[x + y * w] = c.hashCode();
                }
            }

            image.setRGB(0, 0, w, w, pixels, 0, w);
            JOptionPane.showMessageDialog(null, null, "Test", JOptionPane.YES_NO_OPTION, new ImageIcon(image.getScaledInstance(w * 2, w * 2, 0)));
        }

    }

    public static float[][] generateTrees(int w,int h){
        float[][] whiteNoise = generateWhiteNoise(w,h,r);
        float[][] perlin = generatePerlinNoise(whiteNoise,5,0.2f);
        for (int x = 0; x != perlin.length; x++) {
            for (int y = 0; y != perlin[0].length; y++) {
                float p = perlin[x][y];
                if (p > 0.65) perlin[x][y] = 1;
                else perlin[x][y] = 0;
            }
        }
        return perlin;
    }
    static Random r = new Random();
    public static Level generateLevel(int w, int h) {
        Level level = new Level(w, h);
        float[][] whiteNoise = generateWhiteNoise(w, w, r);

        float[][] perlin = generatePerlinNoise(whiteNoise, 6,0.5f);
        perlin = group(perlin);

        float[][] trees = generateTrees(w,h);

        for (int x = 0; x != perlin.length; x++) {
            for (int y = 0; y != perlin[0].length; y++) {
                float p = perlin[x][y];
                level.setTile(x,y,(int)p);
                if(p == 1){
                    if(trees[x][y] == 1){
                       level.setTileTop(x,y,4);
                    }
                }
            }
        }
        level.postCreate();
        return level;
    }

    public static Color getColor(Color s, Color e, float t) {
        float u = 1 - t;
        int r = (int) (s.getRed() * u + e.getRed() * t);
        int g = (int) (s.getGreen() * u + e.getGreen() * t);
        int b = (int) (s.getBlue() * u + e.getBlue() * t);
        Color c = new Color(r, g, b);
        return c;
    }

}


