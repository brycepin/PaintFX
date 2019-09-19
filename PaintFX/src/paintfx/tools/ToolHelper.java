package paintfx.tools;

import javafx.scene.paint.Color;
import paintfx.CanvasLayers;

/**
 * This class is used as the link between the two windows. This class deals with
 * the logic for mouse presses
 *
 * @author Thomas Chapman
 * @author Bryce Pinder
 * 
 */
public class ToolHelper {

   //Variables set throughout the program depending on the users input
   //Used by the tools for drawing
   private Tool currentTool;
   private boolean isMouseDown = false;
   private Color currentColor = Color.BLACK;
   private int toolSize = 5;

   /**
    * @return Tool currentTool
    */
   public Tool getCurrentTool() {
      return this.currentTool;
   }

   /**
    *
    * @param currentTool
    */
   public void setCurrentTool(Tool currentTool) {
      this.currentTool = currentTool;
   }

   /**
    * @return int tool size
    */
   public int getToolSize() {
      return this.toolSize;
   }

   /**
    * Sets the tool size
    *
    * @param toolSize int tool size
    */
   public void setToolSize(int toolSize) {
      if (toolSize > 0) {
         this.toolSize = toolSize;
      }
   }

   /**
    * @return Color selected Color
    */
   public Color getColor() {
      return this.currentColor;
   }

   /**
    * Sets the stroke and fill for control and current layers
    *
    * @param color Color color to be set
    */
   public void setColor(Color color) {
      currentColor = color;
      CanvasLayers.getInstance().getControlLayer().getContext().setStroke(color);
      CanvasLayers.getInstance().getCurrentLayer().getContext().setStroke(color);
      CanvasLayers.getInstance().getControlLayer().getContext().setFill(color);
      CanvasLayers.getInstance().getCurrentLayer().getContext().setFill(color);
   }

   /**
    * This is the method called whenever the mouse is down. Then it
    * differentiates between the click and the dragging.
    *
    * @param mouseX Mouse position on the canvas
    * @param mouseY Mouse position on the canvas
    */
   public void mouseDown(double mouseX, double mouseY) {
      if (isMouseDown) {
         currentTool.mouseStillDown(mouseX, mouseY);
      } else {
         isMouseDown = true;
         currentTool.mouseDown(mouseX, mouseY);
//         System.out.println("toolhelper mousedown");
      }
   }

   /**
    * This is the method called whenever the mouse is released.
    *
    * @param mouseX Mouse position on the canvas
    * @param mouseY Mouse position on the canvas
    */
   public void mouseUp(double mouseX, double mouseY) {
      currentTool.mouseUp(mouseX, mouseY);
      isMouseDown = false;
   }

}
