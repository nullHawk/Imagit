package com.nullhawk.imagit;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
interface Action {
    void apply(); // Apply the action to the image
    void undo();  // Undo the action on the image
    void redo();  // Redo the action on the image
}

public class ImagitControler extends Application {

    @FXML
    private HBox photoViewer;
    @FXML
    private Slider exposureSlide;
    @FXML
    private Slider rotateSlide;
    @FXML
    private Slider contrastSlide;
    @FXML
    private Slider highlightSlide;
    @FXML
    private Slider shadowSlide;
    @FXML
    private VBox controlPanel;

    public static BufferedImage BF_Image;
    private File selectedFile;
    private  File tempFile = new File("temp.jpg");
    public static Image fxImage;
    Editor edit = new Editor();

    private Stack<BufferedImage> undoStack = new Stack<BufferedImage>();
    private Stack<BufferedImage> redoStack = new Stack<BufferedImage>();
    private int lastExposure = 0, lastShadow = 0, lastHighlight = 0, lastRotate = 0;
    float lastContrast = 0;
    private boolean firstCommand = true ,isImageLoading =true;


    @FXML
    void crop(MouseEvent event) {

    }

    @FXML
    void filter(MouseEvent event) throws IOException {
        Filter filter = new Filter();
        Stage newStage = new Stage();
        filter.start(newStage);
    }

    @FXML
    void open(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JPG File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg","*jpeg")
        );
        this.selectedFile = fileChooser.showOpenDialog(Imagit.publicStage);
        try{
            fxImage =  new Image(this.selectedFile.getAbsolutePath());
            BF_Image = ImageIO.read(this.selectedFile.getAbsoluteFile());

            // Create an ImageView and set it as the background content of the ScrollPane
            setImage();
            this.controlPanel.setVisible(true);
            tempFile.delete();


        }catch (Exception e){
            System.out.println(e);
        }


    }




    @FXML
    void redo(MouseEvent event)throws IOException {
        if(!redoStack.isEmpty()){
            saveStack();
            BufferedImage copiedImage = redoStack.pop();
            int width = copiedImage.getWidth();
            int height = copiedImage.getHeight();
            // Copy pixel data from the source image to the new image
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixelValue = copiedImage.getRGB(x, y);
                    BF_Image.setRGB(x, y, pixelValue);
                }
            }
            ImageIO.write(BF_Image, "jpg", tempFile);
            fxImage = new Image(this.tempFile.getAbsolutePath());
            ImageView imageView = new ImageView();
            imageView.setImage(this.fxImage);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(600);
            imageView.setFitHeight(600);
            if (this.photoViewer != null) {
                this.photoViewer.getChildren().clear();
                this.photoViewer.getChildren().add(imageView);
            }
            System.out.println("Working");
        }
    }


    @FXML
    void save(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");

        // Set an extension filter (optional)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(Imagit.publicStage);

        if (file != null) {
            saveImageToFile(this.BF_Image, file);
        }
    }
    private void saveImageToFile(BufferedImage image, File file) {
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void setBlacks(MouseEvent event) {

    }

    @FXML
    void setContrast(MouseEvent event) throws IOException {
        saveStack();
        if(firstCommand){
            BF_Image = ImageIO.read(this.selectedFile);
            firstCommand = false;
        }else{
            BF_Image = ImageIO.read(this.tempFile);
        }

        float value = (float) contrastSlide.getValue() / (float) 25;
        BF_Image = edit.increaseContrast(BF_Image,value);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setExposure(MouseEvent event) throws IOException {
        saveStack();
        if(firstCommand){
            BF_Image = ImageIO.read(this.selectedFile);
            firstCommand = false;
        }else{
            BF_Image = ImageIO.read(this.tempFile);
        }
        BF_Image = edit.adjustExposure(BF_Image,(int)exposureSlide.getValue());

        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setHighlights(MouseEvent event)throws IOException {
        saveStack();
        if(firstCommand){
            BF_Image = ImageIO.read(this.selectedFile);
            firstCommand = false;
        }else{
            BF_Image = ImageIO.read(this.tempFile);
        }
        BF_Image = edit.increaseHighlights(BF_Image,(int)highlightSlide.getValue());
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setShadows(MouseEvent event) throws IOException {
        saveStack();
        if(firstCommand){
            BF_Image = ImageIO.read(this.selectedFile);
            firstCommand = false;
        }else{
            BF_Image = ImageIO.read(this.tempFile);
        }
        BF_Image = edit.adjustShadows(BF_Image,(int)shadowSlide.getValue()/100);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setWhites(MouseEvent event) {

    }

    @FXML
    void undo(MouseEvent event) throws IOException {
        if(!undoStack.isEmpty()){
            saveRedoStack();
            BufferedImage copiedImage = undoStack.pop();
            int width = copiedImage.getWidth();
            int height = copiedImage.getHeight();
            // Copy pixel data from the source image to the new image
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixelValue = copiedImage.getRGB(x, y);
                    BF_Image.setRGB(x, y, pixelValue);
                }
            }
            ImageIO.write(BF_Image, "jpg", tempFile);
            fxImage = new Image(this.tempFile.getAbsolutePath());
            ImageView imageView = new ImageView();
            imageView.setImage(this.fxImage);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(600);
            imageView.setFitHeight(600);
            if (this.photoViewer != null) {
                this.photoViewer.getChildren().clear();
                this.photoViewer.getChildren().add(imageView);
            }
        }

    }
    @FXML
    void setRotate(MouseEvent event) throws IOException {
        saveStack();
        if(firstCommand){
            BF_Image = ImageIO.read(this.selectedFile);
            firstCommand = false;
        }else{
            BF_Image = ImageIO.read(this.selectedFile);
        }
        BF_Image = edit.rotate(BF_Image,(int)rotateSlide.getValue()-lastRotate);
        lastRotate = (int)rotateSlide.getValue() - lastRotate;
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }


    @Override
    public void start(Stage stage) throws Exception {

    }

    void saveRedoStack(){
        int width = BF_Image.getWidth();
        int height = BF_Image.getHeight();
        BufferedImage copiedImage = new BufferedImage(width, height, BF_Image.getType());

        // Copy pixel data from the source image to the new image
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixelValue = BF_Image.getRGB(x, y);
                copiedImage.setRGB(x, y, pixelValue);
            }
        }
        redoStack.add(copiedImage);
    }
    void saveStack(){
        int width = BF_Image.getWidth();
        int height = BF_Image.getHeight();
        BufferedImage copiedImage = new BufferedImage(width, height, BF_Image.getType());

        // Copy pixel data from the source image to the new image
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixelValue = BF_Image.getRGB(x, y);
                copiedImage.setRGB(x, y, pixelValue);
            }
        }
        undoStack.add(copiedImage);
    }
    void setImage(){
        ImageView imageView = new ImageView();
        imageView.setImage(this.fxImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(600);
        imageView.setFitHeight(600);

        // Set the ImageView as the background content of the ScrollPane\ // Clear default background
        if (this.photoViewer != null) {
            this.photoViewer.getChildren().clear();
            this.photoViewer.getChildren().add(imageView);



        } else {
            System.out.println("ScrollPane is null, cannot set style.");
        }
//                int width = (int) fxImage.getWidth();
//                int height = (int) fxImage.getHeight();
//                WritableImage copiedImage = new WritableImage(width, height);
//
//                // Get PixelReader and PixelWriter
//                PixelReader reader = fxImage.getPixelReader();
//                PixelWriter writer = copiedImage.getPixelWriter();
//
//                // Copy pixel data from the source image to the new image
//                for (int x = 0; x < width; x++) {
//                    for (int y = 0; y < height; y++) {
//                        writer.setArgb(x, y, reader.getArgb(x, y));
//                    }
//                }



    }


}
