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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView imageViewFotos;
    @FXML
    private Button imgGrande;
    @FXML
    private Button imgPeque;
    @FXML
    private Button imgDefault;
    @FXML
    private Text text12;
    @FXML
    private Text text13;
    @FXML
    private Text text131;
    @FXML
    private Button siguiente;
    @FXML
    private Button anterior;
    @FXML
    private Label namePreview;
    @FXML
    private Text datePreview;
    @FXML
    private Button guardarFavs;
    @FXML
    private Button imgFavButton1;
    @FXML
    private Button imgFavButton;
    @FXML
    private Button quitar;
    @FXML
    private Button moveImg;
    @FXML
    private Text favText;
    /**
     * Initializes the controller class.
     */
    // ARRAYLIST DE FILE PARA GUARDAR LAS IMÁGENES
    ArrayList<File> fotosJpg = new ArrayList<File>();
    // ARRAYLIST DE LA CLASE ImageFolder PARA LA PREVIEW
    ArrayList<ImageFolder> imageFolder = new ArrayList<>();
    // ARRAYLIST DE TIPO STRING PARA LAS IMAGENES FAV
    ArrayList<String> imgFav = new ArrayList<>();

    // CONTADOR
    int contador = 0;

    // NUMERO DE FILAS PARA TILE PANE
    private int numeroFilas;
    // NUMERO DE COLUMNAS PARA TILE PANE (3 COLUMNAS)
    private int numeroColumnas = 3;

    // CREAMOS LA MEDIDA DEL ELEMENTO DE CADA FOTO
    private static double ELEMENT_SIZE = 200;
    // CREAMOS LA SEPARACIÓN DE CADA ELEMENTO, QUE SERÁ LA MEDIDA ENTRE 10
    private static final double GAP = ELEMENT_SIZE / 10;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        outputFavInitialize();
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

        // SETEAMOS LAS COLUMNAS Y FILAS
        tilePane1.setPrefColumns(numeroColumnas);
        tilePane1.setPrefRows(numeroFilas);
        // SETEAMOS LAS SEPARACIONES LATERALES Y SUPERIORES PARA EL Tile Pane
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
                    alerta.setContentText("Elige otra carpeta.");
                    alerta.showAndWait();
                } else {
                    // HACEMOS EL SET ROOT DE LA CARPETA PADRE, LLAMANDO A LA FUNCIÓN DE ABRIR CARPETAS,
                    // PASANDOLE LA opcion, QUE ES LA CARPETA COMO PARÁMETRO

                    // FILTRADO DE LAS IMAGENES JPG, CREANDO OBJETO FILENAMEFILTER
                    FilenameFilter filterJpg = new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            boolean pepe = false;
                            if (name.toLowerCase().endsWith(".jpg")) {
                                pepe = name.toLowerCase().endsWith(".jpg");
                            }
                            return pepe;
                        }
                    };

                    // GESTIÓN DE FILAS Y COLUMNAS
                    // DECLARAMOS EL NUEVO ARRAYLIST PARA EL fotoJpg PARA EL FILTRO
                    fotosJpg = new ArrayList<File>();
                    int x = 0;
                    int y = 0;
                    // APLICAMOS EN EL ARRAY DE FOTOS, EL FILTRO DE ARCHIVOS .jpg, CON UN FOR EACH DE FILES
                    // PARA PASAR DE UN [] A UN ARRAYLIST
                    for (File f : opcion.listFiles(filterJpg)) {
                        // LO AÑADIMOS CON EL f
                        fotosJpg.add(f);
                        if (x == 2) {
                            x = 0;
                            y++;
                        } else {
                            x++;
                        }
                    }

                    System.out.println(y + " - " + x);
                    // SI LA y ES 0, y SE SUMARÁ EN 1 (QUE ES LA FILA), POR SI HAY SOLO 1 COLUMNA
                    if (y == 0) {
                        y += 1;
                    }
                    // SETEAMOS LA y DEL TILEPANE
                    tilePane1.setPrefRows(y);
                    // IGUALAMOS LA y PARA QUE SEA EL NUMERO DE FILAS
                    numeroFilas = y;
                    // SI y ES MEJOR QUE 0, DECLARAMOS IGUALMENTE LAS COLUMNAS A 3
                    if (y > 0) {
                        tilePane1.setPrefColumns(3);
                        numeroColumnas = 3;
                    } else {
                        tilePane1.setPrefColumns(x + 1);
                        numeroColumnas = x + 1;
                    }
                    // LLAMAMOS AL METODO CREAR FOTOS, LE PASAMOS LA MEDIDA DEL ELEMENT_SIZE
                    // AL ABRIR LA CARPETA SELECCIONADA
                    crearFotos();
                    System.out.println(opcion);

                    // CREAMOS UN NUEVO EVENTO DE RATON, PARA CAMBAIR LA MEDIDA DEL ELEMENT_SIZE
                    // Y LLAMAMOS DE NUEVO A LA FUNCIÓN PARA CREAR LAS FOTOS, PARA QUE LAS BORRE Y LAS
                    // CREE DE NUEVO LAS VBOX CON SUS MEDIDAS Y SUS IMAGENES
                    imgPeque.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            ELEMENT_SIZE = 100;
                            crearFotos();
                        }
                    });
                    // PARA LAS IMÁGENES GRANDES
                    imgGrande.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            ELEMENT_SIZE = 500;
                            crearFotos();
                        }
                    });
                    // LO MISMO PARA LAS IMÁGENES NORMALES
                    imgDefault.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            ELEMENT_SIZE = 200;
                            crearFotos();
                        }
                    });
                    tree.setRoot(abrirCarpetas(opcion));
                }
            }
        });
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

    private void crearFotos() {
        // HACEMOS UN CLEAR, CADA VEZ QUE VAYAMOS A RECARGAR UNA CARPETA
        tilePane1.getChildren().clear();
        imageFolder.clear();
        contador = 0;
        // RECORREMOS EN UN BUCLE LAS FILAS COLUMNAS 
        for (int i = 0; i < numeroColumnas; i++) {
            for (int j = 0; j < numeroFilas; j++) {
                // DESPUÉS, HACEMOS UN getChildren DEL PANEL 
                // Y LLAMAMOS A LA FUNCION crearVbox Y APLICAMOS EL CONTADOR
                tilePane1.getChildren().add(crearVbox(contador));
                contador++;
            }
        }
    }

    public VBox crearVbox(int index) {
        // CREAMOS UN OBJETO ImageView PARA VISUALIZAR LA FOTO        
        ImageView imageView = new ImageView();
        // CREAREMOS EL OBJETO ImageFolder PARA ACCEDER A LA CLASE ImageFolder
        ImageFolder a = new ImageFolder();

        if (fotosJpg.size() <= index) {
            System.out.println("Index out of bounds");
        }

        // DECLARAMOS EN UN NUEVO FILE EL ARRAY DE FOTOS
        // PASANDOLE UN ENTERO, QUE ES EL CONTADOR DE LA FUNCION CREAR FOTOS
        File file = fotosJpg.get(index);

        // CREAMOS UN NUEVO OBJETO DE DATE FORMAR, Y LE PASAMOS POR PARAMETROS LA FECHA Y HORA
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Label labelName = new Label(file.getName());
        Label labelDate = new Label(dateFormat.format(file.lastModified()));
        labelName.setWrapText(true);
        labelName.setMaxWidth(ELEMENT_SIZE);
        labelDate.setWrapText(true);
        labelDate.setMaxWidth(ELEMENT_SIZE);

        a.setFavImg(false);
        favText.setText("NO ES UNA IMÁGEN FAVORITA");

        try {
            // LEEMOS LA FOTO CON EL BUFFERED IMAGE            
            BufferedImage bufferedImage = ImageIO.read(file);
            // PASAMOS LA URL A toString
            Image imageVbox = new Image(file.toURI().toString());

            // SETEAMOS LA IMAGEN DEL imageView
            imageView.setImage(imageVbox);
            // AÑADIMOS LA MEDIDA DEL ELEMENT_SIZE, CON LOS setFitWidth Y setFitHeight
            imageView.setFitWidth(ELEMENT_SIZE);
            imageView.setFitHeight(ELEMENT_SIZE);

            imageView.setSmooth(true);
            imageView.setCache(true);

            // SETEAMOS LA PREVIEW DE LA IMAGEN DE LA CLASE ImageFolder
            a.setPreviewImage(imageView);

            // HACEMOS EL SETTER DE LA RUTA DE lA IMAGEN
            a.setPathImage(file);
            // SETEAMOS LA FECHA Y LO PASAMOS A toString
            a.setNombreFecha(labelDate.toString());
            // SETEAMOS LA IMAGEN image QUE GESTIONAMOS EN EL try
            a.setImage(imageVbox);
            a.setNombrePath(file.toURI().toString());
            // AÑADIMOS EN EL NUEVO ARRAY LIST DE LA CLASE IMAGE FOLDER
            // EL a, QUE ES EL OBJETO DE LA CLASE ImageFolder, Y AÑADIMOS TODAS LAS FOTOS
            // EN ESE ARRAY LIST
            imageFolder.add(a);

            // HACEMOS EL getPreviewImage, Y ACCEDEMOS AL EVENT HANDLER DEL MOUSE CLICKED
            a.getPreviewImage().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    // SETEAMOS EN TRUE SI SELECCIONAMOS Y EN FALSE SI NO
                    if (a.isSelectedFoto()) {
                        a.setSelectedFoto(false);
                    } else {
                        a.setSelectedFoto(true);
                    }
                    // SETEAMOS LA IMAGEN EN imageViewFotos (QUE ES EL PANEL DE ImageView DE LA VISTA)
                    // Y DENTRO HACEMOS UN getImage(); DE LA IMAGEN SETEADA DE LA LINEA DE ARRIBA
                    namePreview.setText(labelName.getText());
                    datePreview.setText(labelDate.getText());
                    imageViewFotos.setImage(a.getImage());

                    // RECORREMOS EL ARRAY DE imageFolder.size(), Y LUEGO
                    // LO GESTIONAMOS CON UN if VALIDANDO EL NOMBRE Y LA PREVIEW
                    for (int i = 0; i < imageFolder.size(); i++) {
                        if (imageFolder.get(i).getPath().equals(namePreview.getText())) {
                            // DECLARAREMOS QUE EL contador ES i PARA UTILIZARLO
                            contador = i;
                        }
                    }
                }
            });

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        // CREAMOS UNA NUEVA CAJA VBox
        VBox vbox = new VBox();
        // AÑADIMOS CON EL getChildren() EL .add() DE LA PREVIEW DE LA IMAGEN 
        // CON EL imageView DE ARRIBA
        vbox.getChildren().add(imageView);
        vbox.getChildren().add(labelName);
        vbox.getChildren().add(labelDate);

        // LO DECLARAMOS COMO NULO Y LA DEVOLVEMOS PARA  
        // VOLVER A EJECUTAR LA FUNCIÓN Y QUE SE IMPRIMAN LAS FOTOS EN BUCLE
        imageView = null;
        return vbox;
    }

    public VBox crearVboxFav(int index, ArrayList bobobo) {
        // CREAMOS UN OBJETO ImageView PARA VISUALIZAR LA FOTO        
        ImageView imageView = new ImageView();
        // CREAREMOS EL OBJETO ImageFolder PARA ACCEDER A LA CLASE ImageFolder
        ImageFolder a = new ImageFolder();

        if (imgFav.size() <= index) {
            System.out.println("Index out of bounds");
        }
        // DECLARAMOS EN UN NUEVO FILE EL ARRAY DE FOTOS
        // PASANDOLE UN ENTERO, QUE ES EL CONTADOR DE LA FUNCION CREAR FOTOS

        String replace;
        replace = imgFav.get(index).substring(imgFav.get(index).lastIndexOf("file:\\") + 1);
        File file = new File(replace);
        
        // CREAMOS UN NUEVO OBJETO DE DATE FORMAR, Y LE PASAMOS POR PARAMETROS LA FECHA Y HORA
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Label labelNameFav = new Label(file.getName());
        Label labelDateFav = new Label(dateFormat.format(file.lastModified()));
        // FORMATEAMOS EL LABEL DE LA FECHA Y EL NOMBRE
        // CREAMOS UN NUEVO OBJETO DE DATE FORMAR, Y LE PASAMOS POR PARAMETROS LA FECHA Y HORA
        labelNameFav.setWrapText(true);
        labelNameFav.setMaxWidth(ELEMENT_SIZE);
        labelDateFav.setWrapText(true);
        labelDateFav.setMaxWidth(ELEMENT_SIZE);
        a.setFavImg(true);
        favText.setText("ES UNA IMÁGEN FAVORITA");

        // PASAMOS LA URL A toString, HACIENDO EL REPLACE DE LAS RUTAS
        Image imageVbox = new Image(file.toString().replace("\\", "/"));
        // SETEAMOS LA IMAGEN DEL imageView
        imageView.setImage(imageVbox);
        // AÑADIMOS LA MEDIDA DEL ELEMENT_SIZE, CON LOS setFitWidth Y setFitHeight
        imageView.setFitWidth(ELEMENT_SIZE);
        imageView.setFitHeight(ELEMENT_SIZE);

        imageView.setSmooth(true);
        imageView.setCache(true);
       

        // SETEAMOS LA PREVIEW DE LA IMAGEN DE LA CLASE ImageFolder
        a.setPreviewImage(imageView);

        // HACEMOS EL SETTER DE LA RUTA DE lA IMAGEN
        a.setPathImage(file);
        // SETEAMOS LA FECHA Y LO PASAMOS A toString
        a.setNombreFecha(labelDateFav.toString());
        // SETEAMOS LA IMAGEN image QUE GESTIONAMOS EN EL try
        a.setImage(imageVbox);
        a.setNombrePath(file.toString());
                
        // HACEMOS EL getPreviewImage, Y ACCEDEMOS AL EVENT HANDLER DEL MOUSE CLICKED
        a.getPreviewImage().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                // SETEAMOS LA IMAGEN EN imageViewFotos (QUE ES EL PANEL DE ImageView DE LA VISTA)
                // Y DENTRO HACEMOS UN getImage(); DE LA IMAGEN SETEADA DE LA LINEA DE ARRIBA
                namePreview.setText(labelNameFav.getText());
                datePreview.setText(labelDateFav.getText());
                imageViewFotos.setImage(a.getImage());

                // RECORREMOS EL ARRAY DE imgFav(), Y LUEGO
                // LO GESTIONAMOS CON UN if VALIDANDO EL NOMBRE Y LA PREVIEW
                for (int i = 0; i < imgFav.size(); i++) {
                    if (imgFav.get(i).substring(imgFav.get(i).lastIndexOf("/") + 1).equals(namePreview.getText())) {
                        contador = i;
                    }
                }
            }
        });

        // CREAMOS UNA NUEVA CAJA VBox
        VBox vbox = new VBox();
        // AÑADIMOS CON EL getChildren() EL .add() DE LA PREVIEW DE LA IMAGEN 
        // CON EL imageView DE ARRIBA
        vbox.getChildren().add(imageView);
        vbox.getChildren().add(labelNameFav);
        vbox.getChildren().add(labelDateFav);

        // LO DECLARAMOS COMO NULO Y LA DEVOLVEMOS PARA  
        // VOLVER A EJECUTAR LA FUNCIÓN Y QUE SE IMPRIMAN LAS FOTOS EN BUCLE
        imageView = null;
        return vbox;
    }

    // REALIZAMOS EL SIGUIENTE CÓDIGO PARA EL ANTERIOR
    @FXML
    private void wakala(ActionEvent event) {
        // DECLARAMOS, SI IMAGEFOLDER DEL ARRAY, SI SU RUTA ES EL equals()
        // DE EL LABEL DEL NOMBRE DE LA IMAGEN CON EL getText(), PASANDO EL CONTADOR
        if (imageFolder.get(contador).getPath().equals(namePreview.getText())) {
            // RESTAREMOS UN -- CADA VEZ QUE SE EJECUTE EL int DEL CONTADOR
            contador--;
            if (contador == (-1)) {
                contador = imageFolder.size() - 1;
            }
            // SETEAMOS LA RUTA DEL imageView DE LA IMAGEN, Y COGEMOS LA RUTA
            // DE LA IMAGEN PASANDOLE LA i DEL BUCLE FOR, Y HACEMOS EL getPath
            namePreview.setText(imageFolder.get(contador).getPath());
            // HACEMOS LO MISMO PASANDOLE EL getNombreFecha Y HACEMOS EL setText
            // PASANDOLE LA i DEL BUCLE PARA QUE LO RECORRA
            datePreview.setText(imageFolder.get(contador).getNombreFecha());
            // SETEAMOS LA IMAGEN EN LA PREVIEW
            imageViewFotos.setImage(imageFolder.get(contador).getImage());
        }
    }

    // REALIZAMOS LO MISMO PARA EL OTRO BOTÓN DE SIGUIENTE, PERO SUMANDO EL
    // CONTADOR EN ++ EN VEZ DE RESTARLO
    @FXML
    private void makelele(ActionEvent event) {
        if (imageFolder.get(contador).getPath().equals(namePreview.getText())) {
            // SUMAREMOS UN ++ CADA VEZ QUE SE EJECUTE EL int DEL CONTADOR
            contador++;
            if (contador == imageFolder.size()) {
                contador = 0;
            }
            namePreview.setText(imageFolder.get(contador).getPath());
            datePreview.setText(imageFolder.get(contador).getNombreFecha());
            imageViewFotos.setImage(imageFolder.get(contador).getImage());
        }
    }

    private void saveFavs() {
        ArrayList<String> bobobo = imgFav;
        try {
            FileOutputStream fileOut = new FileOutputStream("imgFav.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(bobobo);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void outputFav() {
        ArrayList<String> bobobo = new ArrayList<>();
        try {
            FileInputStream input = new FileInputStream("imgFav.ser");
            ObjectInputStream output = new ObjectInputStream(input);
            bobobo = (ArrayList) output.readObject();
            output.close();
            input.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }
        for (int i = 0; i < bobobo.size(); i++) {
            for (int j = 0; j < imageFolder.size(); j++) {
                imageFolder.get(j).setFavImg(true);
                favText.setText("ES UNA IMÁGEN FAVORITA");
            }
        }
    }

    private void outputFavInitialize() {
        ArrayList<String> bobobo = new ArrayList<>();
        try {
            FileInputStream input = new FileInputStream("imgFav.ser");
            ObjectInputStream output = new ObjectInputStream(input);
            bobobo = (ArrayList) output.readObject();
            output.close();
            input.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("No se ha encontrado la clase");
            c.printStackTrace();
            return;
        }
        for (int i = 0; i < bobobo.size(); i++) {
            imgFav.add(bobobo.get(i));
        }
    }

    private void mostrarOutput() {
        tilePane1.getChildren().clear();
        contador = 0;
        ArrayList<String> bobobo = new ArrayList<>();
        try {
            FileInputStream input = new FileInputStream("imgFav.ser");
            ObjectInputStream output = new ObjectInputStream(input);
            bobobo = (ArrayList) output.readObject();
            output.close();
            input.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("No se ha encontrado la clase");
            c.printStackTrace();
            return;
        }
        for (int i = 0; i < bobobo.size(); i++) {
            crearVboxFav(i, bobobo);
            tilePane1.getChildren().add(crearVboxFav(i, bobobo));
        }
    }

    @FXML
    private void onclickFavs(ActionEvent event) {
        for (int i = 0; i < imageFolder.size(); i++) {
            if (imageFolder.get(i).getPath().equals(namePreview.getText())) {
                if (!imageFolder.get(i).isFavImg()) {
                    imgFav.add(imageFolder.get(i).getNombrePath());
                    imageFolder.get(i).setFavImg(true);
                    favText.setText("ES UNA IMÁGEN FAVORITA");
                }
            }
        }
        saveFavs();
        outputFav();
        mostrarOutput();
    }

    @FXML
    private void quitarFavs(ActionEvent event) {
        for (int i = 0; i < imageFolder.size(); i++) {
            if (imageFolder.get(i).getPath().equals(namePreview.getText())) {
                if (imageFolder.get(i).isFavImg()) {
                    imgFav.remove(imageFolder.get(i).getNombrePath());
                    imageFolder.get(i).setFavImg(false);
                    favText.setText("NO ES UNA IMÁGEN FAVORITA");
                }
            }
        }
        saveFavs();
        outputFav();
        mostrarOutput();
    }

    @FXML
    private void filtroImgFav(ActionEvent event) {
        mostrarOutput();
    }

    @FXML
    private void filtroDirectorioOpened(ActionEvent event) {
        crearFotos();
    }

    @FXML
    private void moveImgAction(ActionEvent event) {
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
            alerta.setContentText("Elige otra carpeta.");
            alerta.showAndWait();
        } else {
            // RECORREMOS EL ARRAY ENTERO DE imageFolder.size()
            for (int i = 0; i < imageFolder.size(); i++) {
                // DECLARAMOS QUE SI SELECCIONAMOS LA FOTO Y ESTÁ EN TRUE:
                if (imageFolder.get(i).isSelectedFoto()) {
                    // DECLARAMOS EN UN NUEVO File QUE NOS COJA LA RUTA DE LA FOTO 
                    File file = new File(imageFolder.get(i).getNombrePath());

                    // DECLARAMOS EN UN OBJETO Path LA RUTA DE LA IMAGEN, QUITANDO EL file:\\ CON EL .replace()
                    Path pathToFile = FileSystems.getDefault().getPath(file.getPath().replace("file:\\", ""));
                    // DECLARAMOS EN UN OBJETO Path, EL DIRECTORIO QUE QUEREMOS CAMBIAR, CON EL getAbsolutePath, 
                    // Y AÑADIENDOLE LA FOTO RECORRIDA MÁS EL getPath
                    Path ruta = FileSystems.getDefault().getPath(opcion.getAbsolutePath() + "\\" + imageFolder.get(i).getPath());
                    // EN EL TRY, UTILIZAREMOS EL MÉTODO move, PASANDOLE POR PARÁMETRO LA RUTA DEL ARCHIVO, LA RUTA A MOVER Y EL 
                    // PARÁMETRO DE LA CLASE StandardCopyOption.REPLACE_EXISTING
                    try {
                        Files.move(pathToFile, ruta, StandardCopyOption.REPLACE_EXISTING);
                        abrirCarpetas(opcion);
                    } catch (IOException e) {
                        System.out.println(pathToFile);
                        System.out.println(ruta);
                        System.out.println(opcion.getAbsolutePath());
                        System.out.println(e);
                    }
                }
            }
        }
        tree.setRoot(abrirCarpetas(opcion));
        crearVbox(contador);
    }
}
