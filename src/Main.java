public class Main {
    public static void main(String[] args) {
        /*
        Problem Statement:
        
        My program is a simple freestyle drawing app created with Swing 
        and 2D graphics in Java with multiple functions. Users can 
        draw freely using the mouse, select different brush 
        thicknesses (tiny, small, medium, and large), and choose from
        four colours using a pop-up ColourPanel window (black, light blue, 
        light green, and beige). Beige acts as an eraser, matching the canvas 
        background. A live cursor indicator shows the current brush 
        size while hovering over the canvas at all times. The user can also 
        undo any amount of strokes via right clicking anywhere on the canvas.

        Technical Requirements Met:

        Swing Components:
        - JFrame is used for the main application window.
        - Multiple JButtons are used for changing brush thickness and opening the colour selector.
        - JPanel is used both as the drawing canvas and for toolbar layout (toolsPanel, lighterSection, and ColourPanel).
        - JSlider is used for changing brush thickness freely and precisely. 

        2D Graphics:
        - The program overrides paintComponent() to draw strokes made of lines on the canvas JPanel.
        - Each stroke is stored as an Arraylist of points, Color colour, and int thickness using the Stroke class.
        - A filled oval is drawn in real time as an indicator of brush size under the mouse.

        Event Listeners:
        - ActionListeners are attached to all buttons to handle colour and thickness changes.
        - MouseListener and MouseMotionListener are used to draw strokes, update the brush preview,
          and allow undoing the last stroke with a right click.

        Instructions:
        - Left click and drag to draw
        - Right click to undo the last stroke or as many strokes as you wish
        - Drag slider on the right side up or down to change thickness (Same can be achieved through scroll wheel on mouse)
        - Click "Colour Choices" to open a popup and select a colour
        - Use toolbar buttons to adjust preset brush thicknesses
        */
        DrawingPanel d1 = new DrawingPanel();
    }
}
