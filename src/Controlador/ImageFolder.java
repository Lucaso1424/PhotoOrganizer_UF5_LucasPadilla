/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author lucas
 */
public class ImageFolder {
    // CREAMOS LA IMAGEPREVIEW
    private ImageView previewImage;
    // CREAMOS OBJETO IMAGE
    private Image image;

    
    // GENERAMOS LOS GETTERS Y SETTERS
    public ImageView getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(ImageView previewImage) {
        this.previewImage = previewImage;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}