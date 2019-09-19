package paintfx.tools;

/**
 * Abstract class representing a tool
 *
 * @author Thomas Chapman
 * @author Bryce Pinder
 */
public abstract class Tool {

   //The ToolHelper is used by the tools to make the link between the tool
   //window and the main window, and uses polymorphism to draw with any tool
   public ToolHelper th;

   /**
    * Constructor. Sets the ToolHelper
    * 
    * @param th toolHelper
    */
   public Tool(ToolHelper th) {
      this.th = th;
   }

   /**
    * Abstract class that is executed when the mouse is first pressed down
    *
    * @param mouseX mouse pointer position when clicked
    * @param mouseY mouse pointer position when clicked
    */
   public abstract void mouseDown(double mouseX, double mouseY);

   /**
    * Abstract class that is executed when the mouse is held down
    *
    * @param mouseX mouse pointer position when clicked
    * @param mouseY mouse pointer position when clicked
    */
   public abstract void mouseStillDown(double mouseX, double mouseY);

   /**
    * Abstract class that is executed when the mouse is released
    *
    * @param mouseX mouse pointer position when clicked
    * @param mouseY mouse pointer position when clicked
    */
   public abstract void mouseUp(double mouseX, double mouseY);

}
