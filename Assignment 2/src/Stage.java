import java.awt.*;
import java.util.*;
import java.time.*;
import java.util.List;
import bos.RelativeMove;

public class Stage extends KeyObservable {
    protected Grid grid;
    protected Character sheep;
    protected Character shepherd;
    protected Character wolf;
    private List<Character> allCharacters;
    protected Player player;

    // Making An Instance Of CareTaker.
    // Passing It The Instance Of Stage.
    private CareTaker careTaker = new CareTaker(this);

    private Instant timeOfLastMove = Instant.now();

    public Stage() {
        SAWReader sr = new SAWReader("data/stage1.saw");
        // Passing block locations List to grid.
        grid     = new Grid(10, 10, sr.getBlockLoc());
        shepherd = new Shepherd(grid.cellAtRowCol(sr.getShepherdLoc().first, sr.getShepherdLoc().second), new StandStill());
        sheep    = new Sheep(grid.cellAtRowCol(sr.getSheepLoc().first, sr.getSheepLoc().second), new MoveTowards(shepherd));
        wolf     = new Wolf(grid.cellAtRowCol(sr.getWolfLoc().first, sr.getWolfLoc().second), new MoveTowards(sheep));

        player = new Player(grid.getRandomCell());
        this.register(player);
        player.startMove();

        // Registering careTaker For KeyObserver.
        this.register(careTaker);

        allCharacters = new ArrayList<Character>();
        allCharacters.add(sheep); allCharacters.add(shepherd); allCharacters.add(wolf);

    }

    public void update(){
        if (!player.inMove()) {
            if (sheep.location == shepherd.location) {
                System.out.println("The sheep is safe :)");
                System.exit(0);
            } else if (sheep.location == wolf.location) {
                System.out.println("The sheep is dead :(");
                System.exit(1);
            } else {
                if (sheep.location.x == sheep.location.y) {
                    sheep.setBehaviour(new StandStill());
                    shepherd.setBehaviour(new MoveTowards(sheep));
                }
                // Takes the full moves list that aimove() returns and only performs the first one.
                allCharacters.forEach((c) -> c.aiMove(this).get(0).perform());
                player.startMove();
                timeOfLastMove = Instant.now();
            }
        }
    }

    public void paint(Graphics g, Point mouseLocation) {
        grid.paint(g, mouseLocation);
        sheep.paint(g);
        shepherd.paint(g);
        wolf.paint(g);
        player.paint(g);

        List<RelativeMove> currentPath;
        for(int i = 0; i < allCharacters.size(); i++){
            if(allCharacters.get(i).contains(mouseLocation)){
                // Calculates current moves using aimove().
                currentPath = allCharacters.get(i).aiMove(this);

                int _x = allCharacters.get(i).location.x;
                int _y = allCharacters.get(i).location.y;
                int _w = allCharacters.get(i).location.width;
                int _h = allCharacters.get(i).location.height;

                g.setColor(Color.YELLOW);
                g.drawOval(_x + ((_w - 8)/4), _y + ((_h - 8)/4), (_w + 8)/2, (_h + 8)/2);

                int currentX = _x;
                int currentY = _y;

                // Reverse engineers the moves list into x and y coordinates.
                for(int j = 0; j < currentPath.size(); j++){
                    if(currentPath.get(j).toString().split("@")[0].compareTo("bos.MoveLeft") == 0){
                        // Update Placeholders.
                        currentX = currentX - 35;
                    }
                    if(currentPath.get(j).toString().split("@")[0].compareTo("bos.MoveRight") == 0){
                        // Update Placeholders.
                        currentX = currentX + 35;
                    }
                    if(currentPath.get(j).toString().split("@")[0].compareTo("bos.MoveDown") == 0){
                        // Update Placeholders.
                        currentY = currentY + 35;
                    }
                    if(currentPath.get(j).toString().split("@")[0].compareTo("bos.MoveUp") == 0){
                        // Update Placeholders.
                        currentY = currentY - 35;
                    }
                    // Shade The Cell.
                    g.setColor(Color.YELLOW);
                    g.drawRect(currentX,currentY, _w, _h);
                    // Add A Pattern To The Shaded Cell.
                    for(int k = 5; k <= 35; k = k + 5){
                        g.drawLine(currentX + k, currentY, currentX, currentY + k);
                        g.drawLine(currentX + 35, currentY + k, currentX + k, currentY + 35);
                    }
                }
            }
        }
    }

    // String to store character information as a comma separated variable.
    private String [] charInfo = new String[4];

    public Memento createMemento(){
        // Make a memento object so the String Array can be passed to the memento class.
        Memento save = new Memento();

        // Iterates through the allCharacters list.
        for(int i = 0; i <= allCharacters.size() - 1; i++){
            // Place holder used to make the comma separated string for each character.
            // THis gets overwritten for each character.
            String character;
            character = (allCharacters.get(i).toString().split("@")[0]);
            character = character + "," + (allCharacters.get(i).location.x) + "," + (allCharacters.get(i).location.y);
            character = character + "," + (allCharacters.get(i).getBehaviour().toString().split("@")[0]);

            // Storing Character Information.
            charInfo[i] = character;
        }
        // Storing Player Information.
        charInfo[3] = player.toString().split("@")[0] + "," + (player.location.x) + "," + (player.location.y);

        // Pass the String Array to the memento class.
        save.setState(charInfo);
        // Returns the instance of the memento to the caretaker.
        return save;
    }

    //Restoration
    public void setStateFromMemento(Memento memento){
        String [] savedInfo = memento.getState();

        for(int i = 0; i <= savedInfo.length - 1; i++) {
            // Split on the comma.
            // Each element in savedInfo becomes 4 elements.
            // These four elements are stored in characterInfo and overwritten for each iteration.
            String[] characterInfo = savedInfo[i].split(",");
            // First three elements are actual character details.
            if (i < 3) {
                allCharacters.get(i).location = grid.cellAtRowCol(((Integer.parseInt(characterInfo[2]) - 10) / 35), (Integer.parseInt(characterInfo[1]) - 10) / 35);
                if (characterInfo[0].equals("Wolf")) {
                    // Because the wolf always moves towards the sheep.
                    allCharacters.get(i).setBehaviour(new MoveTowards(sheep));
                }
                else if(characterInfo[3].equals("StandStill")){
                    allCharacters.get(i).setBehaviour(new StandStill());
                }else{
                    if (characterInfo[0].equals("Sheep")) {
                        allCharacters.get(i).setBehaviour(new MoveTowards(shepherd));
                    }
                    if (characterInfo[0].equals("Shepherd")) {
                        allCharacters.get(i).setBehaviour(new MoveTowards(sheep));
                    }
                }
            }
            // Last element is for the player details.
            if(i == 3){
                player.location = grid.cellAtRowCol( ((Integer.parseInt(characterInfo[2]) - 10)/35), (Integer.parseInt(characterInfo[1]) - 10)/35);
            }
        }
    }
}