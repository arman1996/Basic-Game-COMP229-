import java.awt.*;
import java.util.Optional;

public class Sheep extends Character {

    // Sheep appearance.
    public Sheep(Cell location, Behaviour behaviour) {
        super(location, behaviour);
        display = Optional.of(Color.WHITE);
    }
}