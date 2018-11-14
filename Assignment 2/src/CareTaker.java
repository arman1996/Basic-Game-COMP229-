import bos.GameBoard;

public class CareTaker implements KeyObserver{

    // We need an instance of stage, to be able to call the createMemento() and setStateFromMemento() methods.
    private Stage stage;
    // Reference of Memento created.
    private Memento save = new Memento();

    public CareTaker(Stage _stage){
        // Allows the notify() method to use the instance of stage.
        this.stage = _stage;
    }

    @Override
    public void notify(char c, GameBoard<Cell> gb) {
        if(c == ' '){
            // Starts the creation of a Memento.
            // Makes the returned value point to the reference of Memento.
            save = stage.createMemento();
        }

        if(c == 'r'){
            // Starts the restoration of the previously saved Memento.
            // Passes the reference of the Memento object to stage.
            stage.setStateFromMemento(save);
        }
    }


}
