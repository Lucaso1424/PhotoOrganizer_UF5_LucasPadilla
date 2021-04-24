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
    // DECLARAMOS UN STRING PARA LA FECHA DE LA IMAGEN
    private String NombreFecha;

    // SETEAMOS LA FECHA, HACIENDO UN SUBSTRING DE LA FECHA ENTERA, Y COGIENDO EL ULTIMO VALOR
    // CON EL lastIndexOf, RESTANDOLE 5 POSICIONES PARA QUE COJA EL DIA, MES Y AÑO DE TODA LA FECHA
    public String getNombreFecha() {
        String valor = NombreFecha.substring(NombreFecha.lastIndexOf("/") - 5);
        return valor;
    }

    public void setNombreFecha(String NombreFecha) {
        this.NombreFecha = NombreFecha;
    }

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

    // SETEAMOS LA PATH, HACIENDO UN SUBSTRING DE LA PATH ENTERA, Y COGIENDO EL ULTIMO VALOR
    // CON EL lastIndexOf, SUMANDOLE 1 PARA QUE COJA EL ULTIMO STRING, QUE ES EL NOMBRE DE LA IMAGEN
    public String getPath() {
        String valor = NombrePath.substring(NombrePath.lastIndexOf("/") + 1);
        return valor;
    }
}
