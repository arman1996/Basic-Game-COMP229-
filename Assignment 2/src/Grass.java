import java.awt.*;
import java.util.Random;

// Using the Strategy Pattern for the type of terrain the cell should be.
// Thus Grass (a type of terrain) implements Terrain (interface).
public class Grass implements Terrain {

    // Random number generator used to assign a cell a shade of green.
    private static Random rand = new Random();
    private Color c;

    public Grass(){
        // The RGB combination required for producing a shade of green.
        c = new Color(rand.nextInt(30), rand.nextInt(155)+100, rand.nextInt(30));
    }

    @Override
    public void paint(Graphics g, int x, int y) {
        // Paint the cell.
        g.setColor(c);
        g.fillRect(x, y, 35, 35);
        g.setColor(Color.BLACK);
        g.drawRect(x,y, 35, 35);
    }

    // Returns the colour of the Cell.
    @Override
    public Color getColour(){
        return c;
    }

    // Returns the type of terrain as a String.
    @Override
    public String toString() {
        return "Grass";
    }
}
