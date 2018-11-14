import bos.NoMove;
import bos.RelativeMove;
import java.util.List;

public class MoveTowards implements Behaviour {
    Character target;

    public MoveTowards(Character character){
        this.target = character;
    }

    // Overwrites the method within the Behaviour Interface.
    // Returns a List of Relative Moves.
    @Override
    public List<RelativeMove> chooseMoves(Stage stage, Character mover) {
        // Calls movesBetween function within grid.
        List<RelativeMove> movesToTarget = stage.grid.movesBetween(mover.location,target.location, mover);
        if (movesToTarget.size() == 0) {
            movesToTarget.add(new NoMove(stage.grid, mover));
        }
        return movesToTarget;
    }
}
