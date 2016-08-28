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


    public static float[][] generatePerlinNoise(float[][] noise, int oC, float persistence) {
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
                } else if (p > 0.52 && p < 0.75) {
                    noise[x][y] = 1;
                } else if (p > 0.75) {
                    noise[x][y] = 5;
                } else {
                    noise[x][y] = 2;
                }
            }
        }
        return noise;
    }

    public static float[][] groupUnderworld(float[][] noise) {
        for (int x = 0; x != noise.length; x++) {
            for (int y = 0; y != noise[0].length; y++) {
                float p = noise[x][y];
                System.out.println(p);
                if (p > 0.45) {
                    noise[x][y] = 5;
                }
                else{
                    noise[x][y] = 8;
                }
            }
        }

        for (int x = 0; x != noise.length; x++) {
            for (int y = 0; y != noise[0].length; y++) {
                float p = noise[x][y];
                if(p == 5 && touchingCorner(x,y,8,noise)){
                    noise[x][y] = 9;
                }
            }
        }

        for (int x = 0; x != noise.length; x++) {
            for (int y = 0; y != noise[0].length; y++) {
                float p = noise[x][y];
                if(p == 5 && touching(x,y,9,noise) && r.nextInt(2) == 0){
                    noise[x][y] = 9;
                }
            }
        }
        return noise;
    }

    public static boolean touching(int x, int y, int i, float[][] tiles){
        for(int xx = -1;xx!=2;xx++){
            for(int yy = -1;yy!= 2;yy++){
                if(yy == xx)continue;
                try {
                    float p = tiles[x + xx][y + yy];
                    if(p == i){
                        return true;
                    }
                }catch (Exception e){}
            }
        }
        return false;
    }


    public static boolean touchingCorner(int x, int y, int i, float[][] tiles){
        for(int xx = -1;xx!=2;xx++){
            for(int yy = -1;yy!= 2;yy++){
                try {
                    float p = tiles[x + xx][y + yy];
                    if(p == i){
                        return true;
                    }
                }catch (Exception e){}
            }
        }
        return false;
    }

    public static void main(String[] args) {

        while (true) {
            int w = 250;

            //  float[][] whiteNoise = generateWhiteNoise(w, w, r);

//            float[][] perlin = generatePerlinNoise(whiteNoise, 6);
            //          perlin = group(perlin);

            Level level = generateUnderworld(w, w);

            BufferedImage image = new BufferedImage(w, w, BufferedImage.TYPE_INT_RGB);

            int[] pixels = new int[w * w];

            for (int x = 0; x != level.getW(); x++) {
                for (int y = 0; y != level.getH(); y++) {
                    int i = level.getTileAt(x, y);
                    int ii = level.getTileAtTop(x, y);
                    Color c = null;
                    if (i == 1) {
                        c = Color.green;
                    } else if (i == 2) {
                        c = Color.blue;
                    } else if (i == 5) {
                        c = Color.lightGray;
                    } else if (i == 3) {
                        c = Color.yellow;
                    } else if (i == 8) {
                        c = new Color(255, 0, 0);
                    } else if (i == 9) {
                        c = Color.black;
                    } else {
                        c = Color.pink;
                    }

                    if (ii == 4) {
                        c = Color.darkGray;
                    }
                    if (ii == 6) {
                        c = Color.CYAN;
                    }
                    if (ii == 7) {
                        c = new Color(255, 0, 0);
                    }

                    if(ii == 11){
                        c = new Color(100,5,100);
                    }
                    pixels[x + y * w] = c.hashCode();
                }
            }

            image.setRGB(0, 0, w, w, pixels, 0, w);
            JOptionPane.showMessageDialog(null, null, "Test", JOptionPane.YES_NO_OPTION, new ImageIcon(image.getScaledInstance(w * 2, w * 2, 0)));
        }

    }

    public static float[][] generateTrees(int w, int h) {
        float[][] whiteNoise = generateWhiteNoise(w, h, r);
        float[][] perlin = generatePerlinNoise(whiteNoise, 5, 0.2f);
        for (int x = 0; x != perlin.length; x++) {
            for (int y = 0; y != perlin[0].length; y++) {
                float p = perlin[x][y];
                if (p > 0.65) perlin[x][y] = 1;
                else perlin[x][y] = 0;
            }
        }
        return perlin;
    }

    public static float[][] generateRocks(int w, int h) {
        float[][] whiteNoise = generateWhiteNoise(w, h, r);
        float[][] perlin = generatePerlinNoise(whiteNoise, 5, 0.2f);
        for (int x = 0; x != perlin.length; x++) {
            for (int y = 0; y != perlin[0].length; y++) {
                float p = perlin[x][y];
                if (p > 0.65) perlin[x][y] = 1;
                else perlin[x][y] = 0;
            }
        }
        return perlin;
    }

    public static float[][] generateBushes(int w, int h) {
        float[][] whiteNoise = generateWhiteNoise(w, h, r);
        float[][] perlin = generatePerlinNoise(whiteNoise, 3, 0.5f);
        for (int x = 0; x != perlin.length; x++) {
            for (int y = 0; y != perlin[0].length; y++) {
                float p = perlin[x][y];
                if (p > 0.7) perlin[x][y] = 1;
                else perlin[x][y] = 0;
            }
        }
        return perlin;
    }

    static Random r = new Random();

    public static Level generateLevel(int w, int h) {
        Level level = new LevelOverworld(w, h);
        float[][] whiteNoise = generateWhiteNoise(w, w, r);

        float[][] perlin = generatePerlinNoise(whiteNoise, 6, 0.5f);
        perlin = group(perlin);

        float[][] trees = generateTrees(w, h);
        float[][] rocks = generateRocks(w, h);
        float[][] bushes = generateBushes(w, h);

        for (int x = 0; x != perlin.length; x++) {
            for (int y = 0; y != perlin[0].length; y++) {
                float p = perlin[x][y];

                if (p == 1 || p == 5) {
                    if (trees[x][y] == 1) {
                        level.setTileTop(x, y, 4);
                        p = 1;
                    }
                    if (rocks[x][y] == 1 && p == 5) {
                        level.setTileTop(x, y, 6);
                    }
                }
                if (p == 1) {
                    if (bushes[x][y] == 1) {
                        if (level.getTileAtTop(x, y) == 0) {
                            level.setTileTop(x, y, 7);
                        }
                    }
                }
                level.setTile(x, y, (int) p);
            }
        }
        level.postCreate(null);
        return level;
    }


    public static float[][] generateIron(int w, int h) {
        float[][] whiteNoise = generateWhiteNoise(w, h, r);
        float[][] perlin = generatePerlinNoise(whiteNoise, 6, 0.9f);
        for (int x = 0; x != perlin.length; x++) {
            for (int y = 0; y != perlin[0].length; y++) {
                float p = perlin[x][y];
                if (p > 0.65) perlin[x][y] = 1;
                else perlin[x][y] = 0;
                if(r.nextInt(2) == 0)
                    perlin[x][y] = 0;
                if(r.nextInt(2) == 0)
                    perlin[x][y] = 6;
            }
        }
        return perlin;
    }

    public static Level generateUnderworld(int w, int h) {
        Level level = new LevelUnderworld(w, h);
        float[][] whiteNoise = generateWhiteNoise(w, w, r);

        float[][] perlin = generatePerlinNoise(whiteNoise, 9, 0.9f);
        perlin = groupUnderworld(perlin);
        float[][] iron = generateIron(w,h);

        for (int x = 0; x != perlin.length; x++) {
            for (int y = 0; y != perlin[0].length; y++) {
                float p = perlin[x][y];
                level.setTile(x, y, (int) p);
                if(p == 5 && iron[x][y] == 1){
                    level.setTileTop(x,y,11);
                }
                if(p == 5 && iron[x][y] == 6){
                    level.setTileTop(x,y,6);
                }
            }
        }
          level.postCreate(null);
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


