package paintfx.tools.writing;


import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import paintfx.tools.ToolHelper;

/**
 * Implements the drawing shape for a brush
 * 
 * @author Thomas Chapman
 * @author Bryce Pinder
 */
public class PaintfxBrush extends DrawingInstrument {

   /**
    * Constructor. Sets the tool helper
    * @param th 
    */
   public PaintfxBrush(ToolHelper th) {
      super(th);
   }
  
   /**
    * Implements the shape to draw
    * 
    * @param x Mouse position on canvas
    * @param y Mouse position on canvas
    * @param size Radius
    * @param pw PixelWriter to write to
    * @param c  Color to draw
    */
   @Override public void drawShape(double x, double y, 
         int size, PixelWriter pw, Color c){
      
      //Circle equation = (x-i)^2 + (y-j)^2 = r^2
      //Where i and j are coord of center and r is radius
      for(double xc = x-size; xc <= x+size; xc++){
         for(double yc = y-size; yc <= y+size; yc++){
            //If the current pixel is within the radius of the circle, draw
            if(Math.pow(xc-x,2) + Math.pow(yc-y,2) <= size*size){
               pw.setColor((int)xc,(int)yc,c);
            }
         }  
      }
   }
}

