/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import java.io.File;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class VistaController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private FlowPane flowPane;
    @FXML
    private FlowPane flowPane1;
    @FXML
    private FlowPane flowPane2;
    @FXML
    private ImageView image;
    @FXML
    private TreeView<String> tree;
    @FXML
    private Text text11;
    @FXML
    private Text text1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File file = new File(System.getProperty("user.dir"));
        TreeItem tree = new TreeItem(file.getName());
        listarDirectorio(file, tree);
    }
    
    public void listarDirectorio(File archivo, TreeItem nombreDirectorio){
        TreeItem<String> rootItem = new TreeItem<>("Directorio");
        
        // LISTADO DE TODOS LOS ARCHIVOS
        File[] listado = archivo.listFiles();
        
        // SI EL TIPO File ES UN DIRECTORIO, Y LA LISTA DE ARCHIVOS ES 0: 
        if (archivo.isDirectory() && archivo.list().length == 0) {
            // CREAMOS UN NUEVO TREE ITEM DE TIPO STRING
            TreeItem<String> treeItem = new TreeItem<>(archivo.getName());
        }             
        
        // RECORREMOS BUCLE PARA EL LISTADO DE TODOS LOS ARCHIVOS 
        for (int i = 0; i < listado.length; i++) {
            if (listado[i].isDirectory()) {
                // SE IMPRIME NOMBRE DIRECTORIO            
                System.out.println("Directorio: " + listado[i].getName());
                // CREAMOS UN NUEVO TREE ITEM DE TIPO STRING PARA QUE MUESTRE CON EL getName EL NOMBRE DEL DIRECTORIO, RECORRIENDO LOS DIRECTORIOS
                TreeItem<String> directorio = new TreeItem<>(listado[i].getName());
                // AÑADIMOS EN EL nombreDirectorio, EL  DIRECTORIO CREADO POR EL BUCLE 
                nombreDirectorio.getChildren().add(directorio);
                
                // LLAMAMOS A LA FUNCIÓN RECURSIVAMENTE PARA QUE RECORRA EL BUCLE PASAR LOS ARCHIVOS
                listarDirectorio(listado[i], directorio);
            } else if (listado[i].isFile()) {
                
            }
        }
        
        // DEFINIMOS EN EL tree, COMO root EL TREEITEM DE nombreDirectorio
        tree.setRoot(nombreDirectorio);
    }

}