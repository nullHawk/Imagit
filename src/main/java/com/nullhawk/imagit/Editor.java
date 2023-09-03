package com.nullhawk.imagit;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.util.Objects;

public class Editor {

    public  BufferedImage increaseContrast(BufferedImage inputImage, float contrastFactor) {
        RescaleOp rescaleOp = new RescaleOp(contrastFactor, 15, null);
        rescaleOp.filter(inputImage, inputImage);

        return inputImage;
    }

    public BufferedImage rotate(BufferedImage img, double degree) {
        // Getting Dimensions of image
        int width = img.getWidth();
        int height = img.getHeight();

        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), img.getType());

        // creating Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();

        // Rotating image by degrees using toradians()
        // method
        // and setting new dimension t it
        g2.rotate(Math.toRadians(degree), width / 2,
                height / 2);
        g2.drawImage(img, null, 0, 0);

        // Return rotated buffer image
        return newImage;
    }
    public BufferedImage adjustExposure(BufferedImage inputImage, int exposureValue) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage adjustedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                r = clamp(r + exposureValue, 0, 255);
                g = clamp(g + exposureValue, 0, 255);
                b = clamp(b + exposureValue, 0, 255);

                int adjustedRGB = (r << 16) | (g << 8) | b;
                adjustedImage.setRGB(x, y, adjustedRGB);
            }
        }

        return adjustedImage;
    }

    public BufferedImage adjustShadows(BufferedImage originalImage, int darkeningFactor) {
        // Create a new BufferedImage with the same dimensions
        BufferedImage modifiedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        // Brightness threshold for darkening (adjust as needed)
        int brightnessThreshold = 100;

        // Iterate through each pixel in the original image
        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                Color pixelColor = new Color(originalImage.getRGB(x, y));

                // Calculate brightness (average of R, G, B values)
                int brightness = (pixelColor.getRed() + pixelColor.getGreen() + pixelColor.getBlue()) / 3;

                // Check if the pixel is dark
                if (brightness < brightnessThreshold) {
                    // Decrease the brightness by the factor
                    int darkenedBrightness = Math.max(0, brightness - darkeningFactor);

                    // Create a new color with the darkened brightness
                    Color darkenedColor = new Color(
                            darkenedBrightness,
                            darkenedBrightness,
                            darkenedBrightness
                    );

                    // Set the pixel in the modified image
                    modifiedImage.setRGB(x, y, darkenedColor.getRGB());
                } else {
                    // If the pixel is not dark, keep the original color
                    modifiedImage.setRGB(x, y, pixelColor.getRGB());
                }
            }
        }

        return modifiedImage;
    }

    public BufferedImage increaseHighlights(BufferedImage originalImage, int highlightIncreasingFactor) {
        // Create a new BufferedImage with the same dimensions
        BufferedImage modifiedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        // Brightness threshold for enhancement (adjust as needed)
        int brightnessThreshold = 150;

        // Iterate through each pixel in the original image
        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                Color pixelColor = new Color(originalImage.getRGB(x, y));

                // Calculate brightness (average of R, G, B values)
                int brightness = (pixelColor.getRed() + pixelColor.getGreen() + pixelColor.getBlue()) / 3;

                // Check if the pixel is bright
                if (brightness > brightnessThreshold) {
                    // Increase the brightness by the factor
                    int enhancedBrightness = Math.min(255, brightness + highlightIncreasingFactor);

                    // Create a new color with the enhanced brightness
                    Color enhancedColor = new Color(
                            enhancedBrightness,
                            enhancedBrightness,
                            enhancedBrightness
                    );

                    // Set the pixel in the modified image
                    modifiedImage.setRGB(x, y, enhancedColor.getRGB());
                } else {
                    // If the pixel is not bright, keep the original color
                    modifiedImage.setRGB(x, y, pixelColor.getRGB());
                }
            }
        }

        return modifiedImage;
    }





    private  int adjustChannelContrast(int channel, double contrast) {
        double factor = (contrast + 100) / 100.0; // Convert percentage to factor
        return clampValue((int) (128 + factor * (channel - 128)),0, 225);
    }

    private  int clampValue(int value, int min, int max) {
        return Math.min(255, Math.max(0, value));
    }

    private static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }


    public static void main(String[] args){
        Editor obj = new Editor();
        File inputFile = new File("image.jpg");
        File outputfile = new File("output_image.jpg");
        try{
            BufferedImage inputImage = ImageIO.read(Objects.requireNonNull(Editor.class.getResource("image.jpg")));
            //BufferedImage outputImage = obj.rotate(inputImage, 45);
            BufferedImage outputImage = obj.increaseHighlights(inputImage,4);
            System.out.println(outputImage);
            //BufferedImage outputImage = obj.adjustShadows(inputImage, -0.9);
            ImageIO.write(outputImage, "jpg", outputfile);
            System.out.print("Reaching");
        }catch(Exception e){
            System.out.println(e);
        }

    }
}
