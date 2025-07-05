import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class DrawingPanel extends JPanel {
    // initializing all the variables in one place to make my code look neater (instead of doing like JButton wtv = new JButton for each button)
    private ArrayList<Stroke> strokes = new ArrayList<>();
    private Color colour = Color.BLACK;
    private int thickness = 10;
    private int mouseX;
    private int mouseY;
    private int scrollWheelValue = thickness; 
    private boolean showIndicator = false;
    private Stroke currentStroke;
    private JFrame frame;
    private JPanel toolsPanel;
    private JPanel lighterSection;
    private JButton colourButton;
    private JButton button;
    private JButton thickness1;
    private JButton thickness2;
    private JButton thickness3;
    private JButton thickness4;
    private JSlider thicknessSlider;
    
    // my constructor that creates the jframe and also jpanel buttons that corresponds to the colour choices
    // also have buttons associated with thickness and a simple colour spectrum to choose from
    public DrawingPanel() {
        // making the main frame where ill put everything in
        frame = new JFrame("DrawDesk");
        frame.setSize(1070, 1045);
        frame.setLayout(null);

        // Jpanel for the tools section at the very top for user manipulation with different features of the program
        toolsPanel = new JPanel();
        toolsPanel.setLayout(null);
        toolsPanel.setBounds(0, 0, 1000, 100);
        toolsPanel.setBackground(new Color(28, 26, 26));
        toolsPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));
        frame.add(toolsPanel);

        // JPanel for a lighter shade of grey in the middle of the toolbar cuz i want to make the ui look a bit better
        lighterSection = new JPanel();
        lighterSection.setBackground(new Color(232, 224, 208));
        lighterSection.setLayout(null);
        lighterSection.setBounds(0, 10, 1000, 80);
        toolsPanel.add(lighterSection);


        // colourpanel button that opens up a new window with colour choices within
        colourButton = new JButton("Colour Choices");
        colourButton.setBackground(new Color(240, 235, 225));
        colourButton.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 3));
        colourButton.setForeground(new Color(20, 20, 20));
        colourButton.setBounds(700, 18, 200, 50);
        lighterSection.add(colourButton);
        // add action listener for detecting when the button is pressed and have it in anonymous inner clsas formatting since
        // its a one time thing meaning no need to create a whole new class that implements actionlistener
        // I also do this in my ColourPanel class for creating the colour buttons
        colourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ColourPanel c1 = new ColourPanel(DrawingPanel.this);
            }
        });

        // JButtons for thicknesses
        // tiny size
        thickness1 = createThicknesses("Tiny Brush", 1);
        thickness1.setBounds(50, 20, 100, 50);
        // small size
        thickness2 = createThicknesses("Small Brush", 5);
        thickness2.setBounds(200, 20, 100, 50);
        // medium size
        thickness3 = createThicknesses("Medium Brush", 10);
        thickness3.setBounds(350, 20, 100, 50);
        // large size
        thickness4 = createThicknesses("Large Brush", 15);
        thickness4.setBounds(500, 20, 100, 50);
        // add all of the thickness JButtons to my lightshaded JPanel 
        lighterSection.add(thickness1);
        lighterSection.add(thickness2);
        lighterSection.add(thickness3);
        lighterSection.add(thickness4);

        // create new vertical JSlider with initial size = 1, maximum size = 20, and by default 10 
        thicknessSlider = new JSlider(JSlider.VERTICAL, 0, 20, scrollWheelValue); 
        // every 5 pixels big theres an indicator for the brush thickness
        thicknessSlider.setMajorTickSpacing(5);
        // every 1 pixel theres a tick so users can get precise thicknesses
        thicknessSlider.setMinorTickSpacing(1);
        // this shows the actual ticks and the numbers below each tick
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.setPaintLabels(true);
        thicknessSlider.setLayout(null);
        thicknessSlider.setBounds(1000, 10, 45, 980);
        // changelistener is the interface for jslider that allows for detecting when changes are made
        // incorporated as an anonymous inner class the same way as actionlisteners everywhere else
        thicknessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                thickness = thicknessSlider.getValue();
            }
        });
        frame.add(thicknessSlider);


        // add the DrawingPanel JPanel to the frame itself so drawings actually show up
        this.setBounds(0, 100, 1000, 900);
        this.setBackground(new Color(248, 248, 240));
        this.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 20));
        frame.add(this);

        // create instance of a mousetracker and add it to the drawingpanel jpanel
        // this is essemtially the brush
        MouseTracker m1 = new MouseTracker();
        this.addMouseListener(m1);
        this.addMouseMotionListener(m1);
        this.addMouseWheelListener(m1);
        frame.setVisible(true);

    }

    // paintcomponent that does the drawing for each stroke
    @Override
    public void paintComponent(Graphics g) {
        // first call super.paintcomponent clears the panel and ensures everything is in a clean state
        super.paintComponent(g);
        // did a for loop that loops over all my strokes individually and in each stroke i set the colour 
        // based on the colour within the stroke object as well as the thickness 
        for (Stroke s : strokes) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(s.getColor());
            g2d.setStroke(new BasicStroke(s.getThickness()));
            // inner nested loop that through each point in pairs of twos from start to end in a single stroke (for each stroke in the strokes arraylist)
            for (int i = 0; i < s.pointsSize() - 1; i++) {
                Point p1 = s.getPoints().get(i);
                Point p2 = s.getPoints().get(i+1);
                // just drawing a simple line between each point (when repaint() is called in mouseDragged the lines will flow well as if its animating freestyle drawing)
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        if (showIndicator == true) {
            Graphics2D g2d = (Graphics2D) g;
            // minus the thickness/2 since x,y correspond to the top left corner so i have to subtract the x,y values 
            // by the circles radius to get the cursor centralized
            g2d.setColor(this.colour);
            g2d.fillOval(mouseX - thickness/2, mouseY - thickness/2, thickness, thickness);
        }
    }


    // for now this is used for colourpanel class that updates the colour based on user input via button clicks
    public void setColour(Color c) {
        this.colour = c;
    }

    // method for creating the buttons i want for reduced repetition
    public final JButton createThicknesses(String size, int t) {
        // general stuff in here asides from variable changes of size and thickness t
        button = new JButton(size);
        button.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 3));
        colourButton.setForeground(new Color(20, 20, 20));
        button.setBackground(new Color(240, 235, 225));
        button.addActionListener(new ActionListener() {
            // when the button is pressed it changes the drawingpanel's thickness to t
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingPanel.this.thickness = t;
            }
        });
        return button;

    }
    
    // nested class MouseTracker that implements MouseMotionListener, MouseWheelListener, and MouseListener interfaces for drawing functionality 
    private class MouseTracker implements MouseMotionListener, MouseListener, MouseWheelListener {

        // the methods that I use here are mouseDragged, mousePressed, mouseReleased, mouseMoved, and mouseEntered
        // essentially everything starts with mousePressed. I initialize an instance of stroke called "currentStroke" and
        // add the starting point to the stroke. I then add that stroke to my strokes arraylist so that paintcomponent can 
        // start functioning right away because it requires a loop through the strokes. While the mouse is dragged, I just constantly add
        // the points of where the mouse goes over (mousedragged, when pressed, is like a while loop). The repaint part is the most 
        // important in terms of real time changes being noticable since with each point being added, paintcomponent is being called meaning with
        // the addition of a new point, a tiny new line is drawn to accommodate for that. This allows for a smooth drawing. 
        // as for mouseMoved i just use it to track indicator with a circle in paintcomponent.
        // Regarding the scroll wheel I set it so that if scroll wheel value is capped between ranges 0-20 for consistency with slider.
        @Override
        public void mouseDragged(MouseEvent e) {
           currentStroke.addPoint(e.getPoint());
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            showIndicator = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            showIndicator = false;
        }

        // additional feature within mousePressed is the ability to undo a current stroke if right click is detected
        @Override
        public void mousePressed(MouseEvent e) {
            // if right click is pressed and the strokes arraylist isnt empty then no exceptions will be thrown 
            // this acts as undo function (got inspiration from ms paint they do the same thing)
            if (e.getButton() == MouseEvent.BUTTON3 && !strokes.isEmpty()) {
                // removes last element from the strokes list and repaint so it doesnt include it in paintcomponent
                strokes.remove(strokes.size() - 1);
                repaint();
            } else {
            // just creates a new stroke by default if no right click is detected and add it to strokes arraylist
            currentStroke = new Stroke(colour, thickness);
            currentStroke.addPoint(e.getPoint());
            strokes.add(currentStroke);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        // for changing brush size thickness via scrollwheel
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            // if wheel rotation is up then -1 if down then +1 so subtract the scrollWheelValue by the amount to invert it
            scrollWheelValue -= e.getWheelRotation();

            // math conditions that sets the bound between 0 and 20 
            // if amount is less than 20 then amount = amount
            // if amount is greater than 20 then amount = 20 forcefully and Math.max takes the capped 20
            // if amount is less than 0 then it will take 0 between 0 and amount (less than 0)
            scrollWheelValue = Math.max(0, Math.min(scrollWheelValue, 20));
            // set thickness to this new scrolled value and repaint to update the cursor indicator
            thickness = scrollWheelValue;
            repaint();
            // for syncing up the thickness slider with the changes made to thickness by scroll wheel
            thicknessSlider.setValue(scrollWheelValue);
        }
    }


}
