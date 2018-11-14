import bos.RelativeMove;
import java.util.List;

public interface Behaviour {
    // Returns a list of moves according to the mover's behaviour.
    public List<RelativeMove> chooseMoves(Stage stage, Character mover);
}
