package com.nullhawk.imagit;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FilterController extends Application {

    public static BufferedImage BF_Image;
    private File selectedFile;
    private  File tempFile = new File("temp.jpg");
    public static Image fxImage;
    Filters filters = new Filters();

    @FXML
    public HBox photo;
    @FXML
    void setBlur(MouseEvent event) throws  IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applyBlur(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setClassic(MouseEvent event) throws  IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applyClassicFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setGrey(MouseEvent event) throws IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applyGrayscaleFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setHudson(MouseEvent event) throws IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applyHudsonFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setKelvin(MouseEvent event) throws  IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applyKelvinFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setNashville(MouseEvent event) throws  IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applyNashvilleFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setSharpen(MouseEvent event) throws  IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applySharpenFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }
    @FXML
    void setSapia(MouseEvent event) throws  IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applySepiaFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setToaster(MouseEvent event)throws IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applyToasterFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setVintage(MouseEvent event)throws  IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applyVintageFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    @FXML
    void setXpro(MouseEvent event)throws IOException {
        if(ImagitControler.firstCommand){
            BF_Image = ImageIO.read(ImagitControler.selectedFile);
        }else{
            BF_Image = ImageIO.read(ImagitControler.tempFile);
        }
        BF_Image = filters.applyXproIIFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        setImage();
    }

    void setImage(){
        ImageView imageView = new ImageView();
        imageView.setImage(fxImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(600);
        imageView.setFitHeight(600);

        // Set the ImageView as the background content of the ScrollPane\ // Clear default background
        if (this.photo != null) {
            this.photo.getChildren().clear();
            this.photo.getChildren().add(imageView);



        } else {
            System.out.println("ScrollPane is null, cannot set style.");
        }

    }
    @FXML
    void apply() throws IOException{
        BF_Image = ImageIO.read(ImagitControler.tempFile);
        BF_Image = filters.applyXproIIFilter(BF_Image);
        ImageIO.write(BF_Image, "jpg", tempFile);
        fxImage = new Image(this.tempFile.getAbsolutePath());
        ImageView imageView = new ImageView();
        imageView.setImage(fxImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(600);
        imageView.setFitHeight(600);

        // Set the ImageView as the background content of the ScrollPane\ // Clear default background
        ImagitControler obj = new ImagitControler();

        ImagitControler.setPhoto(BF_Image, fxImage);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

}







