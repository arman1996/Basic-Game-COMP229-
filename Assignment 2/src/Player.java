import bos.GameBoard;
import java.awt.*;

public class Player implements KeyObserver {

    public Cell location;
    private Boolean inMove;

    public Player(Cell location){
        this.location = location;
        inMove = false;
    }

    public void paint(Graphics g){
        // Paints the player on the grid.
        g.setColor(Color.ORANGE);
        g.fillOval(location.x + location.width / 4, location.y + location.height / 4, location.width / 2, location.height / 2);
        g.setColor(Color.BLACK);
        g.drawOval(location.x + location.width / 4, location.y + location.height / 4, location.width / 2, location.height / 2);
    }

    public void startMove(){
        inMove = true;
    }

    public Boolean inMove(){
        return inMove;
    }

    public void notify(char c, GameBoard<Cell> gb) {
        if (inMove){
            // Use a filter lambda to ensure that the player only moves onto a Grass cell.
            if (c == 'a') {
                location = gb.leftOf(location).filter(loc -> loc.getTerrain().compareTo("Grass") == 0).orElse(location);
                inMove = false;
            } else if (c == 'd') {
                location = gb.rightOf(location).filter(loc -> loc.getTerrain().compareTo("Grass") == 0).orElse(location);
                inMove = false;
            } else if (c == 'w') {
                location = gb.above(location).filter(loc -> loc.getTerrain().compareTo("Grass") == 0).orElse(location);
                inMove = false;
            } else if (c == 's') {
                location = gb.below(location).filter(loc -> loc.getTerrain().compareTo("Grass") == 0).orElse(location);
                inMove = false;
            }

        }
    }
}
