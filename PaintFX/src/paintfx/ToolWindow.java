package paintfx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import paintfx.tools.ToolHelper;
import paintfx.tools.shapes.PaintfxCircle;
import paintfx.tools.shapes.PaintfxLine;
import paintfx.tools.shapes.PaintfxRectangle;
import paintfx.tools.shapes.Shape;
import paintfx.tools.writing.PaintfxBrush;
import paintfx.tools.writing.PaintfxEraser;
import paintfx.tools.writing.PaintfxPencil;

/**
 * Object that initializes and shows the tool window
 *
 * @author Bryce Pinder
 * @author Thomas Chapman
 */
public class ToolWindow extends PaintFX {

   ToolHelper toolWindowsToolHelper;
   
   public ToolWindow(ToolHelper toolHelper){
      this.toolWindowsToolHelper = toolHelper;
   }
   
    /**
     * 
     * Initializes and opens the secondary tools window
     *
     * @param primaryStage
     */
    public void initTools(Stage primaryStage) {
        if (toolsWindowStage == null) {
            toolsWindowStage = new Stage();
            toolsWindowOpen = false;
            VBox tool = new VBox(10);

            //Shape pane
            Label lblShapes = new Label("Shapes");
            HBox shapesBox = new HBox(10);
            shapesBox.setAlignment(Pos.CENTER);
            rect = new Rectangle(30, 25);
            circ = new Circle();
            circ.setRadius(14);
            line = new Line(0, 10, 10, 30);
            CheckBox filled = new CheckBox("Filled?");
            filled.setSelected(true);
            rect.setStroke(toolWindowsToolHelper.getColor());
            circ.setStroke(toolWindowsToolHelper.getColor());

            ComboBox<Integer> lineWidth = new ComboBox();
            lineWidth.getItems().addAll(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30);
            lineWidth.setPromptText("Line Width");
            lineWidth.setOnAction(event -> {
                toolWindowsToolHelper.setCurrentTool(new PaintfxLine(toolWindowsToolHelper));
                toolWindowsToolHelper.setToolSize(lineWidth.getValue());
            });

            // Determine if the shape drawn is to be filled solid, or draw only an outline
            filled.setOnAction(event -> {
                if (filled.isSelected()) {
                    rect.setFill(toolWindowsToolHelper.getColor());
                    circ.setFill(toolWindowsToolHelper.getColor());
                    CanvasLayers.getInstance().getControlLayer().getContext().setFill(toolWindowsToolHelper.getColor());
                    CanvasLayers.getInstance().getCurrentLayer().getContext().setFill(toolWindowsToolHelper.getColor());
                } else {
                    CanvasLayers.getInstance().getControlLayer().getContext().setFill(new Color(0, 0, 0, 0));
                    CanvasLayers.getInstance().getCurrentLayer().getContext().setFill(new Color(0, 0, 0, 0));

                    rect.setFill(new Color(211, 211, 211, 0.5));
                    circ.setFill(new Color(211, 211, 211, 0.5));
                    rect.setStroke(Color.BLACK);
                    circ.setStroke(Color.BLACK);
                }
                Shape.setIsFilled(filled.isSelected());

            });

            shapesBox.getChildren().addAll(rect, circ, line, filled, lineWidth);

            //Brush pane
            HBox brushBox = new HBox(10);
            Label lblBrush = new Label("Brush");
            brushBox.setAlignment(Pos.CENTER);
            b1 = new Circle(4);
            b2 = new Circle(6);
            b3 = new Circle(8);
            b4 = new Circle(14);
            b5 = new Circle(18);
            Circle[] brushes = {b1, b2, b3, b4, b5};

            // Set the width of the Brush tool by clicking on the circles
            for (Circle c : brushes) {
                c.addEventHandler(MouseEvent.MOUSE_CLICKED, (t) -> {
                    toolWindowsToolHelper.setCurrentTool(new PaintfxBrush(toolWindowsToolHelper));
                    toolWindowsToolHelper.setToolSize((int) c.getRadius());
                    highlight(c);
                });

            }

            // Combo box with different sizes for the Brush tool
            ComboBox<Integer> brushSizeComboBox = new ComboBox();
            brushSizeComboBox.getItems().addAll(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30);
            brushSizeComboBox.setPromptText("Size");
            brushBox.getChildren().addAll(b1, b2, b3, b4, b5, brushSizeComboBox);
            brushSizeComboBox.setOnAction(event -> {
                toolWindowsToolHelper.setCurrentTool(new PaintfxBrush(toolWindowsToolHelper));
                toolWindowsToolHelper.setToolSize(brushSizeComboBox.getValue());
            });

            //Color pane
            Label lblColor = new Label("Colors");
            colorPicker = new ColorPicker(Color.BLACK);

            //Eraser pane
            HBox eraserBox = new HBox(10);
            Label lblEraser = new Label("Eraser");
            eraserBox.setAlignment(Pos.CENTER);
            e1 = new Circle(4);
            e2 = new Circle(6);
            e3 = new Circle(8);
            e4 = new Circle(14);
            e5 = new Circle(18);
            Circle[] circles = {e1, e2, e3, e4, e5};

            // Set the width of the Eraser tool by clicking on the circles
            for (Circle c : circles) {
                c.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    toolWindowsToolHelper.setCurrentTool(new PaintfxEraser(toolWindowsToolHelper));
                    toolWindowsToolHelper.setToolSize((int) c.getRadius());
                    highlight(c);
                });
            }

            ComboBox eraserSize = new ComboBox();
            eraserSize.getItems().addAll(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30);
            eraserSize.setPromptText("Size");
            eraserBox.getChildren().addAll(e1, e2, e3, e4, e5, eraserSize);

            eraserSize.setOnAction(event -> {
                toolWindowsToolHelper.setCurrentTool(new PaintfxEraser(toolWindowsToolHelper));
                toolWindowsToolHelper.setToolSize((int) eraserSize.getValue());
            });

            HBox pencilBox = new HBox(10);
            Label lblPencil = new Label("Pencil");
            pencilBox.setAlignment(Pos.CENTER);
            p1 = new Rectangle(4, 4);
            p2 = new Rectangle(6, 6);
            p3 = new Rectangle(8, 8);
            p4 = new Rectangle(14, 14);
            p5 = new Rectangle(18, 18);

            Rectangle[] pencils = {p1, p2, p3, p4, p5};

            for (Rectangle p : pencils) {
                p.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    toolWindowsToolHelper.setCurrentTool(new PaintfxPencil(toolWindowsToolHelper));
                    toolWindowsToolHelper.setToolSize((int) p.getWidth());
                    highlight(p);
                });

            }

            ComboBox<Integer> pencilSize = new ComboBox();
            pencilSize.getItems().addAll(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30);
            pencilSize.setPromptText("Size");
            pencilBox.getChildren().addAll(p1, p2, p3, p4, p5, pencilSize);
            pencilSize.setOnAction(event -> {
                toolWindowsToolHelper.setCurrentTool(new PaintfxPencil(toolWindowsToolHelper));
                toolWindowsToolHelper.setToolSize(pencilSize.getValue());
            });

            tool.setAlignment(Pos.TOP_CENTER);

            lblBrush.setStyle("-fx-font-size: 18px;");
            lblColor.setStyle("-fx-font-size: 18px;");
            lblEraser.setStyle("-fx-font-size: 18px;");

            tool.getChildren().addAll(lblBrush, brushBox, lblPencil, pencilBox, lblColor, colorPicker, lblShapes, shapesBox, lblEraser, eraserBox);

            Scene toolsWindow = new Scene(tool, 400, 500);

            toolsWindowStage.setTitle("Tools");
            toolsWindowStage.setScene(toolsWindow);
            toolsWindowStage.setX(primaryStage.getX() + primaryStage.getWidth() + 30);
            toolsWindowStage.setY(primaryStage.getY());
            toolsWindowStage.isAlwaysOnTop();

            colorPicker.setOnAction(event -> {
                selectColor(primaryStage, filled.isSelected());
                toolWindowsToolHelper.setColor(colorPicker.getValue());

            });

            rect.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                toolWindowsToolHelper.setCurrentTool(new PaintfxRectangle(toolWindowsToolHelper));
                highlight(rect);
            });

            circ.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                toolWindowsToolHelper.setCurrentTool(new PaintfxCircle(toolWindowsToolHelper));
                highlight(circ);
            });

            line.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                toolWindowsToolHelper.setCurrentTool(new PaintfxLine(toolWindowsToolHelper));
            });

            line.setStrokeWidth(6);

            toolsWindowStage.setOnCloseRequest(event -> {
                toolsWindowOpen = false;
                toolsWindowStage.close();
            });
        }
    }

    /**
     * If window is open it will not open another instance of Tools window
     */
    public void openTools() {

        if (!toolsWindowOpen) {
            toolsWindowStage.show();
        }

    }

    /**
     * Finds the color from the color chooser and sets tools
     *
     * @param primaryStage
     * @param filled
     */
    public void selectColor(Stage primaryStage, boolean filled) {
        Color valueGot = colorPicker.getValue();
        for (javafx.scene.shape.Shape sh : new javafx.scene.shape.Shape[]{b1, b2, b3, b4, b5, p1, p2, p3, p4, p5}) {
            sh.setFill(valueGot);
            sh.setStroke(valueGot);
            sh.setStrokeWidth(2);
        }
        if (filled) {
            rect.setFill(valueGot);
            circ.setFill(valueGot);
        }

        rect.setStroke(valueGot);
        circ.setStroke(valueGot);
        line.setStroke(valueGot);

    }

}
