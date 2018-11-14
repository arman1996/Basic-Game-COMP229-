import java.awt.*;
import java.util.Optional;

public class Wolf extends Character {

    // Wolf appearance.
    public Wolf(Cell location, Behaviour behaviour) {
        super(location, behaviour);
        display = Optional.of(Color.RED);
    }
}