/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.File;
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
    // CREAMOS OBJETO FILE PARA LA RUTA DE LA IMAGEN
    private File pathImage;
    // DECLARAMOS UN STRING PARA LA RUTA DE LA IMAGEN EN EL EVENTO DE BOTÓN
    private String NombrePath;

    public String getNombrePath() {
        return NombrePath;
    }

    public void setNombrePath(String NombrePath) {
        this.NombrePath = NombrePath;
    }

    public File getPathImage() {
        return pathImage;
    }

    public void setPathImage(File pathImage) {
        this.pathImage = pathImage;
    }

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
    
    public String getPath() {
        String valor = NombrePath.substring(NombrePath.lastIndexOf("/")+1);
        return valor;
    }
}
