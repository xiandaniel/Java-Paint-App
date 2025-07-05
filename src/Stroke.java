import java.awt.*;
import java.util.*;

// stroke class activates when mousebutton1 is pressed and does stuff when released later
public class Stroke {
    private ArrayList<Point> points = new ArrayList<>();
    private Color colour;
    private int thickness;

    // constructor for setting the colour and thickness for a stroke
    public Stroke(Color colour, int thickness) {
        this.colour = colour;
        this.thickness = thickness;
    }

    // setter for adding a single point into my points list
    public void addPoint(Point p) {
        points.add(p);
    }

    // getter for when i need to access the colour of each stroke while repainting paintComponent stuff
    public Color getColor() {
        return colour;
    }

    // same with getter for thickness
    public int getThickness() {
        return thickness;
    }

    // and this too
    public int pointsSize() {
        return points.size();
    }

    // and this...
    public ArrayList<Point> getPoints() {
        return points;
    }
}
