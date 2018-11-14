import java.awt.*;
import java.util.Optional;
import java.util.List;
import bos.GamePiece;
import bos.RelativeMove;

// Makes use of the Strategy Pattern to give the characters a movement behaviour.
public abstract class Character implements GamePiece<Cell> {

    // Attributes for a Character.
    Optional<Color> display;
    Cell location;
    Behaviour behaviour;

    // Character constructor.
    public Character(Cell location, Behaviour behaviour){
        this.location = location;
        this.display = Optional.empty();
        this.behaviour = behaviour;
    }

    // Paints each character.
    public  void paint(Graphics g){
        if(display.isPresent()) {
            g.setColor(display.get());
            g.fillOval(location.x + location.width / 4, location.y + location.height / 4, location.width / 2, location.height / 2);
            g.setColor(Color.BLACK);
            g.drawOval(location.x + location.width / 4, location.y + location.height / 4, location.width / 2, location.height / 2);
        }
    }

    // Gives each character a Cell to place themselves on.
    public void setLocationOf(Cell loc){
        this.location = loc;
    }

    // Returns the current Cell the character is on.
    public Cell getLocationOf(){
        return this.location;
    }

    // Sets the behaviour of the character.
    public void setBehaviour(Behaviour behaviour){
        this.behaviour = behaviour;
    }

    // Returns the current behaviour of the character.
    public Behaviour getBehaviour(){
        return this.behaviour;
    }

    // Returns the full list of moves required to reach target.
    // chooseMoves() already returns a list of moves.
    // aimove() is just acting as a bridge.
    // It relays this list of moves to stage.
    public List<RelativeMove> aiMove(Stage stage){
        return behaviour.chooseMoves(stage, this);
    }

    // Takes The Mouse Position And Checks Whether It Is Within The Grid Bounds.
    // Returns A Boolean Based On This Check.
    public boolean contains(Point target){
        if (target == null)
            return false;
        return target.x > this.location.x && target.x < this.location.x + this.location.getWidth()
                && target.y > this.location.y && target.y < this.location.y + this.location.getHeight();
    }
}
