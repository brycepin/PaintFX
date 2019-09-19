package paintfx.tools.shapes;

import paintfx.Layer;
import paintfx.tools.ToolHelper;

/**
 * This class specifies how to draw a rectangle on the canvas
 *
 * @author Thomas Chapman
 * @author Bryce Pinder
 */
public class PaintfxRectangle extends Shape {

   /**
    * Constructor. Sets the tool helper.
    *
    * @param th
    */
   public PaintfxRectangle(ToolHelper th) {
      super(th);
   }

   /**
    * Draws a rectangle on the canvas
    *
    * @param startX Where the rectangle should start (x coord)
    * @param startY Where the rectangle should start (y coord)
    * @param width Width of the rectangle
    * @param height Height of the rectangle
    * @param layer Layer to draw on
    */
   @Override
   public void drawShape(
         double startX, double startY,
         double width, double height,
         Layer layer) {
      
      //Filled or not filled
      if (isFilled) {
         layer.getContext().fillRect(startX, startY, width, height);
      } else {
         layer.getContext().strokeRect(startX, startY, width, height);
      }
   }

}
