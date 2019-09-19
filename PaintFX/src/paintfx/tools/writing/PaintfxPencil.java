package paintfx.tools.writing;


import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import paintfx.tools.ToolHelper;

/**
 * Implements the drawing shape for a pencil
 * 
 * @author Thomas Chapman
 * @author Bryce Pinder
 */
public class PaintfxPencil extends DrawingInstrument {

   /**
    * Constructor. Sets the tool helper
    * @param th 
    */
   public PaintfxPencil(ToolHelper th) {
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
      
      //Simpler than brush or eraser as the pencil needs to just fill the square
      for(double xc = -(size/2); xc <= size/2; xc++){
         for(double yc = -(size/2); yc <= size/2; yc++){
            pw.setColor((int)(x+xc),(int)(y+yc),c);
            
         }  
      }
   }


}

