package paintfx.tools.shapes;

import paintfx.Layer;
import paintfx.tools.ToolHelper;

/**
 * This class specifies how to draw a circle on the canvas
 *
 * @author Thomas Chapman
 * @author Bryce Pinder
 */
public class PaintfxCircle extends Shape {

   /**
    * Constructor. Sets the tool helper.
    *
    * @param th
    */
   public PaintfxCircle(ToolHelper th) {
      super(th);
   }

   /**
    * Draws the circle on the canvas
    *
    * @param startX Where the circle should start (x coord)
    * @param startY Where the circle should start (y coord)
    * @param width Width of the circle
    * @param height Height of the circle
    * @param layer Layer to draw on
    */
   @Override
   public void drawShape(
         double startX, double startY,
         double width, double height,
         Layer layer) {

      //Fill or not filled circle
      if (isFilled) {
         layer.getContext().fillOval(startX, startY, width, height);
      } else {
         layer.getContext().strokeOval(startX, startY, width, height);
      }
   }
}
