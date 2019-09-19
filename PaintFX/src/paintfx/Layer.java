package paintfx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents one layer in the program
 *
 * @author Thomas Chapman
 * @author Bryce Pinder
 */
public class Layer {

   //The canvas that is held by this layer
   private final Canvas canvas;

   /**
    * Constructor
    * @param canvas to be held by this layer
    */
   public Layer(Canvas canvas) {
      this.canvas = canvas;
   }

   /**
    * @return Get the canvas
    */
   public Canvas getCanvas() {
      return this.canvas;
   }

   /**
    * @return Get the GraphicsContext2D from the canvas
    */
   public GraphicsContext getContext() {
      return this.canvas.getGraphicsContext2D();
   }

   /**
    * String representation of the layer. Just the width and height
    * @return 
    */
   @Override
   public String toString() {
      return String.format("Width: %s, Height: %s", this.canvas.getWidth(), this.canvas.getHeight());
   }

}
