package paintfx.tools.shapes;

import paintfx.CanvasLayers;
import paintfx.Layer;
import paintfx.tools.Tool;
import paintfx.tools.ToolHelper;

/**
 * Abstract class to define a Shape
 *
 * @author Thomas Chapman
 * @author Bryce Pinder
 */
public abstract class Shape extends Tool {

   //isFilled is static so that it can be accessed from the main class and tool window
   //too specific for the tool helper
   static boolean isFilled = true;

   double xStart, yStart;
   boolean started = false;

   /**
    * Constructor
    *
    * @param th
    */
   public Shape(ToolHelper th) {
      super(th);

   }

   /**
    * Sets if filled
    *
    * @param val True is filled. False is hollow
    */
   public static void setIsFilled(boolean val) {
      isFilled = val;
   }

   /**
    * Defines how to draw the shape. Overwritten by child classes
    *
    * @param mx1 Mouse position 1 X
    * @param my1 Mouse position 1 Y
    * @param mx2 Either Mouse position 2 X OR width depending on shapes needs
    * @param my2 Either Mouse position 2 X OR width depending on shapes needs
    * @param layer Layer to draw on
    */
   public abstract void drawShape(
         double mx1, double my1,
         double mx2, double my2,
         Layer layer);

   /**
    * When the mouse is clicked for the first time
    *
    * @param mouseX mouse position
    * @param mouseY mouse position
    */
   @Override
   public void mouseDown(double mouseX, double mouseY) {

      /**
       * This method is called every frame, and just sends the mouse position to
       * the mouseStillDown method if the mouse wasn't clicked
       */
      if (started) {
         mouseStillDown(mouseX, mouseY);
      } else {
         xStart = mouseX;
         yStart = mouseY;
         started = true;
         //The javafx Line is different to the other shapes so 
         //it needs some special help
         if (th.getCurrentTool() instanceof PaintfxLine) {
            CanvasLayers.getInstance().getControlLayer().getContext().
                  setLineWidth(th.getToolSize());
            CanvasLayers.getInstance().getCurrentLayer().getContext().
                  setLineWidth(th.getToolSize());
         }
      }

   }

   /**
    * Executes when the mouse is held down
    * @param mouseX mouse position
    * @param mouseY mouse position
    */
   @Override
   public void mouseStillDown(double mouseX, double mouseY) {

      //Wipe out controllayer
      CanvasLayers.getInstance().clearControl();

      //The javafx Line is different to the other shapes so 
      //it needs some special help
      if (th.getCurrentTool() instanceof PaintfxLine) {
         this.drawShape(xStart, yStart, mouseX, mouseY, CanvasLayers.getInstance().getControlLayer());
      } else {
         double[] coords = this.getShapeCoordAndSize(xStart, yStart, mouseX, mouseY);
         this.drawShape(coords[0], coords[1], coords[2], coords[3], CanvasLayers.getInstance().getControlLayer());
      }
   }

   /**
    * Executes when the mouse is release. Draws the shape to the working canvas,
    * and resets the mouse state.
    * 
    * @param mouseX mouse position
    * @param mouseY mouse position
    */
   @Override
   public void mouseUp(double mouseX, double mouseY) {
      started = false;
      if (th.getCurrentTool() instanceof PaintfxLine) {
         this.drawShape(xStart, yStart, mouseX, mouseY, CanvasLayers.getInstance().getCurrentLayer());
      } else {
         double[] coords = this.getShapeCoordAndSize(xStart, yStart, mouseX, mouseY);
         this.drawShape(coords[0], coords[1], coords[2], coords[3], CanvasLayers.getInstance().getCurrentLayer());
      }
      CanvasLayers.getInstance().clearControl();
   }

   /**
    * Helper method for getting the appropriate coords and sizes from the mouse
    * positions. Shapes need to be drawn from top left to bottom right. This 
    * makes sure that happens
    * 
    * @param startX mouse position
    * @param startY mouse position
    * @param endX mouse position
    * @param endY mouse position
    * @return double[xStart, yStart, width, height]
    */
   private double[] getShapeCoordAndSize(double startX, double startY,
         double endX, double endY) {

      //Check which is the smaller of the values per axis
      boolean x = startX < endX;
      boolean y = startY < endY;

      double[] ret = new double[4];

      //Depending on which is lower, set values to correct value. 
      //The startin x and y (ret[0 & 1]) must be the lower because 
      //the width and height (ret[2 & 3]) have to be positive going 
      //Down and Right due to the way javafx draws to a canvas
      ret[0] = x ? startX : endX;
      ret[1] = y ? startY : endY;
      ret[2] = x ? endX - startX : startX - endX;
      ret[3] = y ? endY - startY : startY - endY;

      return ret;

   }
}
