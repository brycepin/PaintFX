package paintfx;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * This is a singleton class that holds the layers on the canvas.
 *
 *
 *
 * @author Thomas Chapman
 * @author Bryce Pinder
 *
 */
public class CanvasLayers {

   //layers holds all the different Layer objects,
   //currentLayer is used by the tools to know what to draw on
   //controlLayer is always on top, invisible, and is what has the 
   //action events on it
   private ArrayList<Layer> layers;
   private Layer currentLayer;
   private Layer controlLayer;

   //The main pane that contains all the canvas's
   private Pane parentPane;

   private double canvasWidth, canvasHeight;
   private boolean initialized = false;

   //Holds the singleton object
   private static CanvasLayers instance = null;

   private CanvasLayers() {
   }

   /**
    *
    * @return Singleton object
    */
   public static CanvasLayers getInstance() {
      if (instance == null) {
         instance = new CanvasLayers();
      }
      return instance;
   }

   /**
    * Used ot load the image into the current layer
    *
    * @param img Image to be drawn
    */
   public void loadImage(Image img) {
      instance.getCurrentLayer().getContext().drawImage(img, 0, 0);
   }

   /**
    * Check for initialization
    *
    * @return isInitialized
    */
   public boolean isInitialized() {
      return instance.initialized;
   }

   /**
    * @return parent Pane
    */
   public Pane getParentPane() {
      return instance.parentPane;
   }

   /**
    * Used to clear the entire controlLayer. Used by the Shape classes that
    * redraw on the controlLayer every frame.
    */
   public void clearControl() {
      instance.controlLayer.getContext().clearRect(0, 0, canvasWidth, canvasHeight);
   }

   /**
    * Clears the current layout. Called from the button in the menu
    */
   public void clear() {
      instance.currentLayer.getContext().clearRect(0, 0, canvasWidth, canvasHeight);
   }

   /**
    * Resets the entire instance. Used when making a new file
    *
    * @param canvasWidth double width
    * @param canvasHeight double height
    */
   public static void startNewCanvas(double canvasWidth, double canvasHeight) {

      Pane prntPn = instance.parentPane;

      //Destroy instance
      instance = null;
      //Destroy all children of the parent pane
      prntPn.getChildren().clear();

      //Start again
      initialize(prntPn, canvasWidth, canvasHeight);
   }

   /**
    * Used to initialize the entire instance
    *
    * @param parent Pane that holds all the canvas's
    * @param canvasWidth double width
    * @param canvasHeight double height
    */
   public static void initialize(Pane parent, double canvasWidth, double canvasHeight) {

      //Creates the new instance
      getInstance();

      //Sets variables for the instance
      instance.parentPane = parent;
      instance.layers = new ArrayList();

      instance.canvasWidth = canvasWidth;
      instance.canvasHeight = canvasHeight;

      instance.controlLayer = instance.createNewLayer();
      instance.currentLayer = instance.createNewLayer();

      instance.initialized = true;

      //Lock the parent, and in turn the canvas, into a specific size.
      parent.setMaxHeight(canvasHeight);
      parent.setMinHeight(canvasHeight);
      parent.setMaxWidth(canvasWidth);
      parent.setMinWidth(canvasWidth);

   }

   /**
    * Creates a new layer and adds it to the CanvasLayers singleton
    * 
    * @return returns the layer created
    */
   public Layer createNewLayer() {
      //New Canvas with specific sizes
      Canvas newCanvas = new Canvas();
      newCanvas.setWidth(canvasWidth);
      newCanvas.setHeight(canvasHeight);
      
      //New layer added to the instance
      Layer toBeAdded = new Layer(newCanvas);
      instance.layers = new ArrayList();
      instance.layers.add(toBeAdded);
      
      //Add the layer to the parent pane
      instance.parentPane.getChildren().add(newCanvas);
      
      //If there are no layers, then it creates the controlLayer.
      if (instance.controlLayer != null) {
         instance.controlLayer.getCanvas().toFront();
      }

      return toBeAdded;
   }

   /**
    * Gets the control layer
    *
    * @return Layer control layer
    */
   public Layer getControlLayer() {
      return instance.controlLayer;
   }

   /**
    * Get the currently selected layer
    *
    * @return the working Layer
    */
   public Layer getCurrentLayer() {
      return instance.currentLayer;
   }

   /**
    * Could be used to move layers behind/infront of one another.
    *
    * @param layerPos
    * @param targetPos
    */
   @Deprecated
   public void moveLayer(int layerPos, int targetPos) {
      Layer temp = layers.get(layerPos);
      instance.layers.set(layerPos, layers.get(targetPos));
      instance.layers.set(targetPos, temp);
   }

   /**
    * Used to select the current layer
    *
    * @param toBeSet
    */
   @Deprecated
   public void setCurrentLayer(Layer toBeSet) {
      if (layers.contains(toBeSet)) {
         instance.currentLayer = toBeSet;
      }
   }
}
