package paintfx.tools.writing;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import paintfx.CanvasLayers;
import paintfx.tools.Tool;
import paintfx.tools.ToolHelper;

/**
 * Abstract class to be extended by writing tools (ie. pencil, pen, eraser)
 * 
 * @author Thomas Chapman
 * @author Bryce Pinder
 */
public abstract class DrawingInstrument extends Tool {

   //Too keep track of where the mouse is moving between frames
   double oldMouseX, oldMouseY, newMouseX, newMouseY;
   
   /**
    * Constructor
    * @param th sets the tool helper
    */
   public DrawingInstrument(ToolHelper th) {
      super(th);
   }

   /**
    * Executes when the mouse is clicked. Sets the variables up and draws
    *
    * @param mouseX Mouse position
    * @param mouseY Mouse position
    */
   @Override
   public void mouseDown(double mouseX, double mouseY) {
      oldMouseX = mouseX;
      oldMouseY = mouseY;
      newMouseX = mouseX;
      newMouseY = mouseY;
      this.draw(mouseX, mouseY, oldMouseX, oldMouseY);
   }

   /**
    * Executes when the mouse is being dragged, sets old and new values, and 
    * draws.
    *
    * @param mouseX Mouse position
    * @param mouseY Mouse position
    */
   @Override
   public void mouseStillDown(double mouseX, double mouseY) {
      oldMouseX = newMouseX;
      oldMouseY = newMouseY;
      newMouseX = mouseX;
      newMouseY = mouseY;
      this.draw(mouseX, mouseY, oldMouseX, oldMouseY);
   }

   /**
    * Finishes up the line wherever the mouse leaves the canvas
    *
    * @param mouseX
    * @param mouseY
    */
   @Override
   public void mouseUp(double mouseX, double mouseY) {
      this.draw(mouseX, mouseY, oldMouseX, oldMouseY);
   }

   /**
    * Draws pixels on the canvas of the selected layer
    *
    * @param mouseX
    * @param mouseY
    * @param oldMouseX
    * @param oldMouseY
    */
   private void draw(
         double mouseX, double mouseY,
         double oldMouseX, double oldMouseY) {

      //The pixel writer for the canvas
//      System.out.println("Canvas: "+CanvasLayers.getInstance().getCurrentLayer());
      PixelWriter pw = CanvasLayers.getInstance().getCurrentLayer().getContext().getPixelWriter();
      //Length of step used to get from the point of OldMouse to NewMouse positions
      //Payoff is performance vs drawing quality.
      //A higher number is easier on the computer but leaves gaps in the line
      //Lower number removes gaps but takes longer to process.
      double step = 0.05;

      //Editable coords for the path
      double currX = oldMouseX;
      double currY = oldMouseY;

      //Distance between the current working position (currX,Y) and the final destination
      double distance = 2;

      //Ratio for the distance and the step
      double ratio = step / distance;
      
      //While the distance between the current position and the final destination is greater than 1 pixels
      while (distance > 1) {
          
         //Update the ratio
         distance = getDistance(currX, currY, mouseX, mouseY);

         //Each individual tool will implement their own drawShape logic
         
         
         this.drawShape(
               currX,
               currY,
               th.getToolSize(), 
               pw, 
               th.getColor());
         currX = (1 - ratio) * currX + (ratio * mouseX);
         currY = (1 - ratio) * currY + (ratio * mouseY);
      }
   }

   /**
    * Helper function for finding the distance between two points
    * 
    * @param x1 Position 1
    * @param y1 Position 1
    * @param x2 Position 2
    * @param y2 Position 2
    * @return 
    */
   private double getDistance(double x1, double y1, double x2, double y2) {
      return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(x2 - x1, 2));
   }
   
   /**
    * To be implemented by the specific tool to get the right shape
    * 
    * @param x X Position
    * @param y Y Position
    * @param size Radius
    * @param pw PixelWriter to write to
    * @param c Colour to use
    */
   public abstract void drawShape(double x, double y, int size, PixelWriter pw, Color c);

}
