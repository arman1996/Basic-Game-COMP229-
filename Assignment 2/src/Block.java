import java.awt.*;

// Using the Strategy Pattern for the type of terrain the cell should be.
// Thus Block (a type of terrain) implements Terrain (interface).
public class Block implements Terrain {

    private Color c;

    public Block(){
        // The RGB combination required for producing Brown.
        c = new Color(150, 75, 0);
    }

    @Override
    public void paint(Graphics g, int x, int y) {
        // Paints the Cell Brown.
        g.setColor(c);
        g.fillRect(x, y, 35, 35);
        g.setColor(Color.BLACK);
        g.drawRect(x,y, 35, 35);
        // Draws a pattern on the Block.
        for(int i = 5; i <= 35; i = i + 5){
            g.drawLine(x + i, y, x, y + i);
            g.drawLine(x + 35, y + i, x + i, y + 35);
        }
    }

    // Returns the colour of the Cell.
    @Override
    public Color getColour(){
        return c;
    }

    // Returns the type of terrain as a String.
    @Override
    public String toString() {
        return "Block";
    }
}
