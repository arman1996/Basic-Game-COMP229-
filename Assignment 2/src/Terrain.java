import java.awt.*;

// Terrain (interface) used in Strategy Pattern.
public interface Terrain {
    void paint(Graphics g, int x, int y);
    Color getColour ();
    String toString();
}
