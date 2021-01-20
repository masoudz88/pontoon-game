import java.awt.Color;
import java.util.Random;
import javax.swing.*;
import java.util.*;

/*
 *  A GUI component
 *
 *  A simple extension of JButton which records its
 *  coordinates in xcoord and ycoord, NOT in 'x' and 'y'.
 *  Why not? Because 'x' and 'y' are already attributes of
 *  the panel (super) class which say where to draw it in the window.
 *
 *  The game grid and allows the background colour to be set with ease.
 *
 *  @author mZare
 */
public class GridSquare extends JButton
{
    private int xcoord, ycoord, result;  // location in the grid

    // constructor takes the x and y coordinates of this square
    public GridSquare(int xcoord, int ycoord)
    {
        super();
        this.setSize(50,50);
        this.setText("--");
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        result=0;
    }


    // generates a random number between 1-5
    public void generateRandom()    {

        Random rnd=new Random();
        result=rnd.nextInt(5)+1;
        this.setText(Integer.toString(result));
    }

    // simple setters and getters
    public int getResult()    { return result; }
    public int getXcoord()              { return xcoord; }
    public int getYcoord()              { return ycoord; }
}

