import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.util.*;

/*
 *  The main window of the gui.
 *  Notice that it extends JFrame - so we can add our own components.
 *  Notice that it implements ActionListener - so we can handle user input.
 *  This version also implements MouseListener to show equivalent functionality (compare with the other demo).
 *  @author mhatcher
 */
public class WindowDemo extends JFrame implements ActionListener, MouseListener
{
    // gui components that are contained in this frame:
    private JPanel topPanel, bottomPanel;	// top and bottom panels in the main window
    private JLabel instructionLabel;		// a text label to tell the user what to do
    private JLabel infoLabel;
    private JLabel currentStatus;// a text label to show the coordinate of the selected square
    private JButton topButton;				// a 'reset' button to appear in the top panel
    private GridSquare [][] gridSquares;	// squares to appear in grid formation in the bottom panel
    private int rows,columns;
    private Random rnd;// the size of the grid
    //public ArrayList<Integer> currentNumbers;
    private int counter;
    private int current;

    /*
     *  constructor method takes as input how many rows and columns of gridsquares to create
     *  it then creates the panels, their subcomponents and puts them all together in the main frame
     *  it makes sure that action listeners are added to selectable items
     *  it makes sure that the gui will be visible
     */
    public WindowDemo(int rows, int columns)
    {
        rnd=new Random();
        counter=rnd.nextInt(1);
        this.rows = rows;
        this.columns = columns;
        this.setSize(600,600);

        // first create the panels
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(rows, columns));
        bottomPanel.setSize(500,500);

        // then create the components for each panel and add them to it


        // for the top panel:
        instructionLabel = new JLabel("Click the Squares!");
        infoLabel = new JLabel("player --");
        topButton = new JButton("New Game");
        currentStatus=new JLabel("current: "+ current);
        topButton.addActionListener(this);			// IMPORTANT! Without this, clicking the square does nothing.
        topPanel.add (topButton);
        topPanel.add(instructionLabel);

        topPanel.add(infoLabel);
        topPanel.add(currentStatus);

        // for the bottom panel:
        // create the squares and add them to the grid
        gridSquares = new GridSquare[rows][columns];
        for ( int x = 0; x < columns; x ++)
        {
            for ( int y = 0; y < rows; y ++)
            {
                gridSquares[x][y] = new GridSquare(x, y);
                gridSquares[x][y].setSize(20, 20);
                gridSquares [x][y].setText("--");
                gridSquares[x][y].setBackground(Color.white);
                gridSquares[x][y].addMouseListener(this);// AGAIN, don't forget this line!
                gridSquares[x][y].generateRandom();
                bottomPanel.add(gridSquares[x][y]);
            }
        }

        // now add the top and bottom panels to the main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);		// needs to be center or will draw too small

        // housekeeping : behaviour
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }


    /*
     *  handles actions performed in the gui
     *  this method must be present to correctly implement the ActionListener interface
     */
    public void actionPerformed(ActionEvent aevt)
    {
        // get the object that was selected in the gui

        Object selected = aevt.getSource();
        current=0;

        // if resetting the squares' colours is requested then do so
        if ( selected.equals(topButton) )
        {
            for ( int x = 0; x < columns; x ++)
            {
                for ( int y = 0; y < rows; y ++)
                {

                    gridSquares [x][y].setBackground(Color.white);
                    gridSquares [x][y].generateRandom();
                    gridSquares [x][y].addMouseListener(this);
                    instructionLabel.setText("Click the Squares!");
                    infoLabel.setText("player --");
                    currentStatus.setText("current: "+ current);


                }
            }
        }
    }


    // Mouse Listener events
    public void mouseClicked(MouseEvent mevt) {
        // get the object that was selected in the gui
        Object selected = mevt.getSource();


        /*
         * I'm using instanceof here so that I can easily cover the selection of any of the gridsquares
         * with just one piece of code.
         * In a real system you'll probably have one piece of action code per selectable item.
         * Later in the course we'll see that the Command Holder pattern is a much smarter way to handle actions.
         */

        // if a gridsquare is selected then switch its color when a player clicks and the random numbers in each square are added


        if (selected instanceof GridSquare) {

            GridSquare square = (GridSquare) selected;

            infoLabel.setText("player " + (counter % 2 + 1));

            if (counter % 2 == 0) {
                square.setBackground(Color.BLUE);
                counter++;
            } else if (counter % 2 == 1) {
                square.setBackground(Color.yellow);
                counter++;
            }
            int x = square.getXcoord();
            int y = square.getYcoord();

            current += square.getResult();
            currentStatus.setText("current: " + current);
            if (current > 21) {
                instructionLabel.setText("Winner is player" + (counter % 2 + 1));
                infoLabel.setText("");
                square.setBackground(Color.white);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        gridSquares[x][y].removeMouseListener(this);
                    }
                }
                square.removeMouseListener(this);
            }

        }
    }





    // not used but must be present to fulfil MouseListener contract
    public void mouseEntered(MouseEvent arg0){}
    public void mouseExited(MouseEvent arg0) {}
    public void mousePressed(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}
}

