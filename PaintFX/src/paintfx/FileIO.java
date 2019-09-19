/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintfx;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * Static class for file io
 *
 * @author Bryce Pinder
 * @author Thomas Chapman
 */
public class FileIO {
      /**
    * Save file method, allows user to save their file to a .png .jpg. or .jpeg
    *
    * @see JPG/JPEG does not work due to a bug within java its self. 
    *    Tested on Ubuntu: Saved file is 0 bytes/
    *    Teston on windows: Colours are messed up. White turns out salmon coloured
    * @param primaryStage
    */
   public static void saveFile(Stage primaryStage) {

      File file = new File(".");
      FileChooser chooser = new FileChooser();

      chooser.setTitle("Save File");
      chooser.setInitialDirectory(file);
      chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("png", "*.png"),
            new FileChooser.ExtensionFilter("jpg", "*.jpg"),
            new FileChooser.ExtensionFilter("jpeg", "*.jpeg"));

      File selectedFile = chooser.showSaveDialog(primaryStage);

      if (selectedFile != null) {

         //This gets the image from the canvas's. Javafx has a snapshot feature
         //Which makes this super easy.
         WritableImage wi = CanvasLayers.getInstance().getParentPane().snapshot(new SnapshotParameters(), null);

         RenderedImage rimg = SwingFXUtils.fromFXImage(wi, null);

         //Getting file extension
         String[] fileSplit = selectedFile.getName().split("\\.");

         String extension = "";

         String[] acceptableExtensions = {"jpg", "jpeg", "png"};

         //Figuring out if the user entered a file extension with their name 
         //and if they didnt then adding the appropriate one.
         boolean getFromChooser = true;
         if (fileSplit.length >= 2) {
            extension = fileSplit[fileSplit.length - 1];
            for (String i : acceptableExtensions) {
               if (extension.equalsIgnoreCase(i)) {
                  getFromChooser = false;
                  break;
               }
            }
         }

         if (getFromChooser) {
            extension = chooser.getSelectedExtensionFilter().getDescription();
            selectedFile = new File(selectedFile.getAbsolutePath() + "." + extension.toLowerCase());
         }

         try {
            ImageIO.write(rimg, extension, selectedFile);
         } catch (IOException ex) {
            System.out.println(ex.toString());
         }
      }

   }

   /**
    * Open File which allows users to open and edit any files in .png, .jpg or
    * .jpeg file formats
    *
    *
    *
    * @param primaryStage
    */
   public static void openFile(Stage primaryStage) {
      checkToSave(primaryStage);

      File file = new File(System.getProperty("user.home"));

      FileChooser chooser = new FileChooser();

      chooser.setTitle("Open Image");
      chooser.setInitialDirectory(file);
      chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("png", "*.png"),
            new FileChooser.ExtensionFilter("jpg", "*.jpg"),
            new FileChooser.ExtensionFilter("jpeg", "*.jpeg"));

      File selectedFile = chooser.showOpenDialog(primaryStage);

      if (selectedFile != null) {
         Image img = new Image(selectedFile.toURI().toString());
         CanvasLayers.startNewCanvas(img.getWidth(), img.getHeight());
         CanvasLayers.getInstance().loadImage(img);
      }

   }
   
      /**
    * Checking with the user if they would like to save their file
    *
    * @param primaryStage
    * @return
    */
   public static boolean checkToSave(Stage primaryStage) {
      if (CanvasLayers.getInstance().isInitialized()) {

         Alert confirmSave = new Alert(Alert.AlertType.CONFIRMATION);

         confirmSave.setTitle("Would you like to save?");

         confirmSave.setContentText("Would you like to save?");

         Optional<ButtonType> btn = confirmSave.showAndWait();

         if (btn.isPresent() && btn.get() == ButtonType.OK) {
            saveFile(primaryStage);
            return true;
         } else {
            return false;
         }
      }
      return true;
   }
}
