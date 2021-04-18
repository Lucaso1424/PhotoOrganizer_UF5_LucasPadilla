/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javax.imageio.ImageIO;

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
    private FlowPane flowPane2;
    @FXML
    private ImageView image;
    @FXML
    private TreeView<String> tree;
    @FXML
    private Text text11;
    @FXML
    private Text text1;
    @FXML
    private Button buttonBtn;
    @FXML
    private AnchorPane anchorPanelFinal;
    @FXML
    private TilePane tilePane1;

    /**
     * Initializes the controller class.
     */
    
    // ARRAY DE FILE PARA GUARDAR LAS IMÁGENES
    File fotosJpg[]; 

    // CONTADOR
    int contador = 0;

    // NUMERO DE FILAS PARA TILE PANE
    private int numeroFilas = 3;  
    // NUMERO DE COLUMNAS PARA TILE PANE
    private int numeroColumnas = 3;  

    private static final double ELEMENT_SIZE = 100;
    private static final double GAP = ELEMENT_SIZE / 10;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // DEFINIMOS LA CARPETA DEL PROYECTO
        File file = new File(System.getProperty("user.dir"));
        // DEFINIMOS EL NOMBRE DE LA CARPETA
        TreeItem treeProyecto = new TreeItem(file.getName());
        // LLAMAMOS A LA FUNCIÓN DE LISTAR EL DIRECTORIO
        listarDirectorio(file, treeProyecto);

        // LLAMAMOS A LA FUNCIÓN DEL EVENTO DEL BOTÓN
        // CREANDO EVENTO DEL BOTÓN
        ActionEvent e = new ActionEvent();
        eventoBoton(e, tree);

        tilePane1.setPrefColumns(numeroColumnas);
        tilePane1.setPrefRows(numeroFilas);
        tilePane1.setHgap(GAP);
        tilePane1.setVgap(GAP);
    }

    public void listarDirectorio(File archivo, TreeItem nombreDirectorio) {
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

    public void eventoBoton(ActionEvent e, TreeView<String> tree) {
        // LE PASAMOS COMO PARÁMETRO EL treeView DE LA VISTA
        // PONEMOS EL BOTÓN CON UN setOnAction CON UN EVENTHANDLDER (COMO UN addEventListener DE JS)
        buttonBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser fileC = new DirectoryChooser();
                // DECLARAMOS COMO DIRECTORIO INICIAL, user.home                
                fileC.setInitialDirectory(new File(System.getProperty("user.home")));

                // LE PASAMOS UN STAGE PARA ABRIR LA CARPETA DE IMAGENES
                Stage primaryStage = new Stage();

                // LE PASAMOS LA OPCION DEL primaryStage                
                File opcion = fileC.showDialog(primaryStage);
                // GESTIONAMOS UN CASO DE PRUEBA, SI LA OPCIÓN ES NULA O NO ES UN DIRECTORIO                
                if (opcion == null || !opcion.isDirectory()) {
                    // CREAMOS UNA ALERTA
                    Alert alerta = new Alert(AlertType.ERROR);
                    // MOSTRAMOS MENSAJE DE ALERTA
                    alerta.setHeaderText("No se puede abrir el directorio.");
                    alerta.setContentText("El archivo no es válido.");
                    alerta.showAndWait();
                } else {
                    // HACEMOS EL SET ROOT DE LA CARPETA PADRE, LLAMANDO A LA FUNCIÓN DE ABRIR CARPETAS,
                    // PASANDOLE LA opcion, QUE ES LA CARPETA COMO PARÁMETRO
                    
                    // FILTRADO DE LAS IMAGENES JPG, CREANDO OBJETO FILENAMEFILTER
                    FilenameFilter filterJpg = new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".jpg");
                        }
                    };

                    tree.setRoot(abrirCarpetas(opcion));
                    fotosJpg = opcion.listFiles(filterJpg);
                    
                    // LLAMAMOS AL METODO CREAR FOTOS AL ABRIR LA CARPETA SELECCIONADA
                    crearFotos();
                    

                    System.out.println(opcion);
                }
            }
        });
    }

    private void crearFotos() {
        tilePane1.getChildren().clear();

        for (int i = 0; i < numeroColumnas; i++) {
            for (int j = 0; j < numeroFilas; j++) {
                tilePane1.getChildren().add(crearPagina(contador));
                contador++;
            }
        }
    }

    public VBox crearPagina(int index) {
        ImageView imageView = new ImageView();

        File file = fotosJpg[index];
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(ELEMENT_SIZE);
            imageView.setFitHeight(ELEMENT_SIZE);

            imageView.setSmooth(true);
            imageView.setCache(true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        VBox vbox = new VBox();
        vbox.getChildren().add(imageView);

        imageView = null;
        return vbox;
    }

    public TreeItem<String> abrirCarpetas(File directorio) {
        TreeItem<String> rootTree = new TreeItem<String>(directorio.getName());
        // HACEMOS UN FOR EACH, QUE POR CADA FILE DE CADA DIRECTORIO DE LA LISTA        
        for (File files : directorio.listFiles()) {
            if (files.isDirectory()) {
                // SI ES UN DIRECTORIO, AÑADE LA CARPETA EN LA PADRE EN EL rootTree CON EL .add()
                rootTree.getChildren().add(abrirCarpetas(files));
            } else {
                // SI NO ES UN DIRECTORIO, AÑADE EL ITEM DEL ARCHIVO CON EL .add() Y EL .getName()
                rootTree.getChildren().add(new TreeItem<String>(files.getName()));
            }
        }
        // HACEMOS EL RETURN DEL DIRECTORIO
        return rootTree;
    }

}
