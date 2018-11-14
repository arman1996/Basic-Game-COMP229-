import bos.NoMove;
import bos.RelativeMove;
import java.util.ArrayList;
import java.util.List;

public class StandStill implements Behaviour {
    // Since choosemoves has to return a list, this function just returns a list containing NoMove.
    @Override
    public List<RelativeMove> chooseMoves(Stage stage, Character mover) {
        List<RelativeMove> movesToTarget = new ArrayList<>();
        if(movesToTarget.size() == 0){
            movesToTarget.add(new NoMove(stage.grid, mover));
        }
        return movesToTarget;
    }
}
