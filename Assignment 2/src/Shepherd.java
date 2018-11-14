import java.awt.*;
import java.util.Optional;

public class Shepherd extends Character {

    // Shepherd appearance.
    public Shepherd(Cell location, Behaviour behaviour) {
        super(location, behaviour);
        display = Optional.of(Color.GREEN);
    }

}