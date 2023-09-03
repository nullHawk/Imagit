package com.nullhawk.imagit;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.util.Objects;

public class Filters {

    private  void applyColorAdjustments(BufferedImage source, BufferedImage destination, float redFactor, float greenFactor, float blueFactor) {
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                Color pixelColor = new Color(source.getRGB(x, y));
                int r = Math.min(255, (int) (pixelColor.getRed() * redFactor));
                int g = Math.min(255, (int) (pixelColor.getGreen() * greenFactor));
                int b = Math.min(255, (int) (pixelColor.getBlue() * blueFactor));
                Color newColor = new Color(r, g, b);
                destination.setRGB(x, y, newColor.getRGB());
            }
        }
    }

    // Helper function to apply contrast adjustment
    private  void applyContrastAdjustment(BufferedImage source, BufferedImage destination, float contrastFactor) {
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                Color pixelColor = new Color(source.getRGB(x, y));
                int r = Math.min(255, Math.max(0, (int) (pixelColor.getRed() * contrastFactor)));
                int g = Math.min(255, Math.max(0, (int) (pixelColor.getGreen() * contrastFactor)));
                int b = Math.min(255, Math.max(0, (int) (pixelColor.getBlue() * contrastFactor)));
                Color newColor = new Color(r, g, b);
                destination.setRGB(x, y, newColor.getRGB());
            }
        }
    }

    public  BufferedImage applyClassicFilter(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        float redFactor = 1.2f;
        float greenFactor = 1.1f;
        float blueFactor = 0.8f;
        float contrastFactor = 0.9f;

        applyColorAdjustments(image, result, redFactor, greenFactor, blueFactor);
        applyContrastAdjustment(result, result, contrastFactor);

        return result;
    }

    // Apply the Nashville filter
    public  BufferedImage applyNashvilleFilter(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        float redFactor = 1.2f;
        float blueFactor = 0.8f;
        float contrastFactor = 1.1f;

        applyColorAdjustments(image, result, redFactor, 1.0f, blueFactor);
        applyContrastAdjustment(result, result, contrastFactor);

        return result;
    }

    // Apply the Toaster filter
    public  BufferedImage applyToasterFilter(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        float redFactor = 1.5f;
        float greenFactor = 0.9f;
        float contrastFactor = 1.1f;

        applyColorAdjustments(image, result, redFactor, greenFactor, 1.0f);
        applyContrastAdjustment(result, result, contrastFactor);

        return result;
    }

    // Apply the Hudson filter
    public  BufferedImage applyHudsonFilter(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        float redFactor = 0.9f;
        float greenFactor = 1.1f;
        float blueFactor = 1.3f;

        applyColorAdjustments(image, result, redFactor, greenFactor, blueFactor);

        return result;
    }

    // Apply the Kelvin filter
    public  BufferedImage applyKelvinFilter(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        float redFactor = 1.3f;
        float greenFactor = 0.9f;
        float contrastFactor = 1.1f;

        applyColorAdjustments(image, result, redFactor, greenFactor, 1.0f);
        applyContrastAdjustment(result, result, contrastFactor);

        return result;
    }

    // Apply the XproII filter
    public  BufferedImage applyXproIIFilter(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        float redFactor = 1.3f;
        float blueFactor = 0.8f;
        float contrastFactor = 1.3f;

        applyColorAdjustments(image, result, redFactor, 1.0f, blueFactor);
        applyContrastAdjustment(result, result, contrastFactor);

        return result;
    }

    public BufferedImage applyBlurFilter(BufferedImage image) {
        float[] blurMatrix = {
                0.111f, 0.111f, 0.111f,
                0.111f, 0.111f, 0.111f,
                0.111f, 0.111f, 0.111f
        };

        BufferedImageOp blurOp = new ConvolveOp(new Kernel(3, 3, blurMatrix));
        return blurOp.filter(image, null);
    }

    // Apply a sharpen filter to the BufferedImage
    public BufferedImage applySharpenFilter(BufferedImage image) {
        float[] sharpenMatrix = {
                -1, -1, -1,
                -1, 9, -1,
                -1, -1, -1
        };

        BufferedImageOp sharpenOp = new ConvolveOp(new Kernel(3, 3, sharpenMatrix));
        return sharpenOp.filter(image, null);
    }
    public BufferedImage applyGrayscaleFilter(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                int grayValue = (int) (0.299 * pixelColor.getRed() + 0.587 * pixelColor.getGreen() + 0.114 * pixelColor.getBlue());
                Color newColor = new Color(grayValue, grayValue, grayValue);
                result.setRGB(x, y, newColor.getRGB());
            }
        }

        return result;
    }
    public BufferedImage applySepiaFilter(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                int grayValue = (int) (0.299 * pixelColor.getRed() + 0.587 * pixelColor.getGreen() + 0.114 * pixelColor.getBlue());
                int r = Math.min(255, (int) (grayValue * 1.07));
                int g = Math.min(255, (int) (grayValue * 0.74));
                int b = Math.min(255, (int) (grayValue * 0.43));
                Color newColor = new Color(r, g, b);
                result.setRGB(x, y, newColor.getRGB());
            }
        }

        return result;
    }
    public  BufferedImage applyVintageFilter(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                int r = Math.min(255, (int) (pixelColor.getRed() * 1.5));
                int g = Math.min(255, (int) (pixelColor.getGreen() * 0.9));
                int b = Math.min(255, (int) (pixelColor.getBlue() * 0.7));
                Color newColor = new Color(r, g, b);
                result.setRGB(x, y, newColor.getRGB());
            }
        }

        return result;
    }
    public BufferedImage applyBlur(BufferedImage inputImage){
        int radius = 5;

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] pixel = new int[]{0, 0, 0};
                int count = 0;

                // Sum the color values of neighboring pixels within the specified radius
                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dx = -radius; dx <= radius; dx++) {
                        int newX = x + dx;
                        int newY = y + dy;

                        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                            int rgb = inputImage.getRGB(newX, newY);
                            pixel[0] += (rgb >> 16) & 0xFF;
                            pixel[1] += (rgb >> 8) & 0xFF;
                            pixel[2] += rgb & 0xFF;
                            count++;
                        }
                    }
                }

                // Calculate the average color value
                pixel[0] /= count;
                pixel[1] /= count;
                pixel[2] /= count;

                // Set the pixel in the output image
                int blurredPixel = (0xFF << 24) | (pixel[0] << 16) | (pixel[1] << 8) | pixel[2];
                outputImage.setRGB(x, y, blurredPixel);
            }
        }

        return outputImage;
    }

    public static void main(String[] args){
        Filters obj = new Filters();
        File inputFile = new File("image.jpg");
        File outputfile = new File("output_image.jpg");
        try{
            BufferedImage inputImage = ImageIO.read(Objects.requireNonNull(Editor.class.getResource("image.jpg")));
            //BufferedImage outputImage = obj.rotate(inputImage, 45);
            BufferedImage outputImage = obj.applyBlur(inputImage);

            System.out.println(outputImage);
            //BufferedImage outputImage = obj.adjustShadows(inputImage, -0.9);
            ImageIO.write(outputImage, "jpg", outputfile);
            System.out.print("Reaching");
        }catch(Exception e){
            System.out.println(e);
        }

    }
}
