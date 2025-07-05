import java.awt.event.*;
import java.util.*;
import java.awt.*; 
import javax.swing.*;


// colourpanel is a simple class that just makes a window for choosing 6 colours
public class ColourPanel {
    private DrawingPanel dp;
    private JFrame cframe;
    private JPanel cpanel;
    private JButton colour;

    // constructor for colourpanel
    public ColourPanel(DrawingPanel dp) {
        // since i want to modify drawingpanel's colour variable directly, i have to take that instance of drawingpanel through calling 
        // ColourPanel c1 = new ColourPanel(this); with this being the current instance of drawingpanel which allows for real time changes to appear
        this.dp = dp;

        // bunch of ui design: jframe for popup feature, set up a base jpanel to pad colour buttons onto etc
        cframe = new JFrame("Colour Selector");
        cframe.setBackground(Color.LIGHT_GRAY);
        cframe.setSize(515, 377);
        cframe.setLayout(null);
        cpanel = new JPanel();
        cpanel.setLayout(null);
        cpanel.setSize(500, 500);
        cframe.add(cpanel);


        // just calls colourmaker() function 6 times for 6 colour options black (default), light blue, light green, light red,  light yellow, beige (functions as eraser since I made background beige)
        colourmaker(Color.BLACK, 0, 0);
        colourmaker(new Color(120, 160, 200), 166, 0);
        colourmaker(new Color(150, 170, 140), 332, 0);
        colourmaker(new Color(200, 140, 140), 0, 166);
        colourmaker(new Color(230, 215, 140), 166, 166);
        JButton white = colourmaker(new Color(248, 248, 240), 332, 166);
        white.setText("Eraser");

        cframe.setVisible(true);
    }


    // a method that creates customizable JButtons to reduce code repetition 
    private JButton colourmaker(Color c, int x, int y) {
        // colour is just the general variable name of the coloured button i actually want 
        // rest is justs adding its dimensions, adding the button to the main panel, and actionlistener for detecting clicks
        colour = new JButton();
        colour.setBackground(c);
        colour.setBounds(x, y, 166, 166);
        cpanel.add(colour);

        // with each click the method goes to the current instance of DrawingPanel's setColour method to change the global colour variable
        // then i close the cframe so that users dont get annoyed spam opening it a bunch of times. Downsides are that you have to 
        // reopen it again every time if you want to change colours but I think thats more slick than having to close a bunch of the same tabs.
        colour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dp.setColour(c);
                cframe.dispose();
            }
        });
        return colour;
    }
}