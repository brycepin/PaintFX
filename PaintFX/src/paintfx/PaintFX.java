
package paintfx;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import paintfx.tools.ToolHelper;

import paintfx.tools.writing.PaintfxBrush;

/**
 * Main class with start() and main()
 *
 * @author Bryce Pinder
 * @author Thomas Chapman
 */
public class PaintFX extends Application {

   // Data fields
   protected ComboBox brushSize;
   protected Circle b1;
   protected Circle b2;
   protected Circle b3;
   protected Circle b4;
   protected Circle b5;
   protected Circle e1;
   protected Circle e2;
   protected Circle e3;
   protected Circle e4;
   protected Circle e5;
   protected Rectangle p1;
   protected Rectangle p2;
   protected Rectangle p3;
   protected Rectangle p4;
   protected Rectangle p5;
   protected ColorPicker colorPicker;
   protected Rectangle rect;
   protected Circle circ;
   protected Line line;
   protected Stage toolsWindowStage;
   protected boolean toolsWindowOpen = false;
   protected Pane canvasHolder;
   protected ToolHelper toolHelper;
   protected javafx.scene.shape.Shape lastSelected;

   /**
    * Entry point for JavaFX. Sets up all the nodes, panes, and menu items
    * 
    * @param primaryStage 
    */
   @Override
   public void start(Stage primaryStage) {

      
      VBox root = new VBox(10);
      toolHelper = new ToolHelper();

      ToolWindow toolWindow = new ToolWindow(toolHelper);

      // Menu Options and interactions
      MenuBar menu = new MenuBar();
      final Menu menuFile = new Menu("File");
      final Menu menuClearButton = new Menu("Clear");
      final Menu menuTools = new Menu("Tools");

      MenuItem newFile = new MenuItem("New");
      MenuItem save = new MenuItem("Save");
      MenuItem open = new MenuItem("Open");
      MenuItem tools = new MenuItem("Tools");
      MenuItem clear = new MenuItem("Clear");

      newFile.setOnAction(event -> {
         newCanvas(primaryStage);
      });

      save.setOnAction(event -> {
         FileIO.saveFile(primaryStage);
      });

      open.setOnAction((ActionEvent event) -> {
         FileIO.openFile(primaryStage);
         setPaintControls();
      });

      clear.setOnAction(event -> {
         if (!FileIO.checkToSave(primaryStage)) {
            CanvasLayers.getInstance().clear();
         } else {
            CanvasLayers.getInstance().clear();
         }
      });

      menuTools.setOnAction(event -> {
         toolWindow.openTools();
      });

      menuFile.getItems().addAll(newFile, save, open);

      menuClearButton.getItems().add(clear);

      menuTools.getItems().add(tools);

      menu.getMenus().addAll(menuFile, menuClearButton, menuTools);

      root.setStyle("-fx-background-color: lightgrey;");

      canvasHolder = new Pane();

      root.getChildren().addAll(menu, new StackPane(canvasHolder));

      CanvasLayers.initialize(canvasHolder, 500, 500);
      canvasHolder.backgroundProperty().set(new Background(new BackgroundFill(Paint.valueOf("WHITE"), CornerRadii.EMPTY, Insets.EMPTY)));
      setPaintControls();

      Scene scene = new Scene(root, 650, 650);

      primaryStage.setTitle("PaintFX");
      primaryStage.setScene(scene);
      primaryStage.show();

      toolWindow.initTools(primaryStage);

   }

   /**
    * Allowing the user to create a new file with custom width and height
    *
    * @param primaryStage
    */
   public void newCanvas(Stage primaryStage) {

      FileIO.checkToSave(primaryStage);

      try {
         double[] canvasSize = getCanvasSize();
         CanvasLayers.startNewCanvas(canvasSize[0], canvasSize[1]);
         setPaintControls();
      } catch (IOException e) {
      }
   }

   /**
    * Setting event handlers on different layers of canvas
    */
   public void setPaintControls() {

      toolHelper.setCurrentTool(new PaintfxBrush(toolHelper));

      CanvasLayers.getInstance().getControlLayer().getCanvas().setOnMousePressed(ev -> {
         if (ev.isPrimaryButtonDown()) {
            toolHelper.mouseDown(ev.getX(), ev.getY());
         }

      });

      CanvasLayers.getInstance().getControlLayer().getCanvas().setOnMouseDragged(ev -> {
         if (ev.isPrimaryButtonDown()) {
            toolHelper.mouseDown(ev.getX(), ev.getY());
         }
      });
      CanvasLayers.getInstance().getControlLayer().getCanvas().setOnMouseReleased(ev -> {
         if (ev.getButton() == MouseButton.PRIMARY) {
            toolHelper.mouseUp(ev.getX(), ev.getY());
         }
      });
   }

   /**
    * Sets custom canvas width
    *
    * @return @throws IOException
    */
   public double getUserSpecifiedCanvasWidth() throws IOException {
      TextInputDialog dialog = new TextInputDialog("500");

      dialog.setTitle("Width");
      dialog.setHeaderText("Enter a Width for your project.");
      boolean thr = false;
      Optional<String> result = dialog.showAndWait();

      try {
         if (result.isPresent()) {
            return Double.parseDouble(result.get());
         } else {
            //Cancel
            thr = true;
         }
      } catch (NumberFormatException e) {
         System.err.println("User entered value could not be parsed as double.");
      }

      if (thr) {
         throw new IOException();
      }
      //Failed attempt.
      return -1;
   }

   /**
    * Sets custom canvas height
    *
    * @return @throws IOException
    */
   public double getUserSpecifiedCanvasHeight() throws IOException {
      TextInputDialog dialog = new TextInputDialog("500");

      dialog.setTitle("Height");
      dialog.setHeaderText("Enter a Height for your project.");
      boolean thr = false;
      Optional<String> result = dialog.showAndWait();

      try {
         if (result.isPresent()) {
            return Double.parseDouble(result.get());
         } else {
            //Cancel
            thr = true;
         }
      } catch (NumberFormatException e) {
         System.err.println("User entered value could not be parsed as double.");
      }

      if (thr) {
         throw new IOException();
      }
      //Failed attempt.
      return -1;
   }

   /**
    * Finds size of canvas
    *
    * @return @throws IOException
    */
   public double[] getCanvasSize() throws IOException {
      double width = -1;
      while (width == -1) {
         width = getUserSpecifiedCanvasWidth();
      }
      double height = -1;
      while (height <= 0) {
         height = getUserSpecifiedCanvasHeight();
      }

      return new double[]{width, height};
   }

   /**
    * Draws border around selected tool and removes border off the last
    * highlighted tool
    *
    * @param highlightTarget Shape to highlight
    */
   public void highlight(javafx.scene.shape.Shape highlightTarget) {
      if (lastSelected != null) {
         lastSelected.setStrokeWidth(0);
      }
      highlightTarget.setStroke(Color.GREEN);
      highlightTarget.setStrokeWidth(4);
      lastSelected = highlightTarget;

   }

   /**
    *
    * @param args
    */
   public static void main(String[] args) {
      launch(args);
   }
}
