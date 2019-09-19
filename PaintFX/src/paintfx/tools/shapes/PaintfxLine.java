package paintfx.tools.shapes;

import paintfx.Layer;
import paintfx.tools.ToolHelper;

/**
 * This class specifies how to draw a circle on the canvas
 *
 * @author Thomas Chapman
 * @author Bryce Pinder
 */
public class PaintfxLine extends Shape {

   /**
    * Constructor. Sets the tool helper.
    *
    * @param th
    */
   public PaintfxLine(ToolHelper th) {
      super(th);
   }

   /**
    * Draws the line on the canvas
    *
    * @param startX Where the line should start (x coord)
    * @param startY Where the line should start (y coord)
    * @param endX Where the line should end (y coord)
    * @param endY Where the line should end (y coord)
    * @param layer Layer to draw on
    */
   @Override
   public void drawShape(
         double startX, double startY,
         double endX, double endY,
         Layer layer) {

      layer.getContext().strokeLine(startX, startY, endX, endY);
   }

}
