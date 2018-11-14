import java.awt.*;

public class Cell extends Rectangle {

    // Making an instance of the interface Terrain so we can call the paint method.
    private Terrain terrain;

    public Cell(int x, int y) {
        // Cells are drawn as rectangles.
        super(x, y, 35, 35);
        // By default, all cells are of type Grass.
        this.setTerrain(new Grass());
    }

    public void paint(Graphics g, Boolean highlighted) {
        // Using the Strategy Pattern to paint the cells.
        terrain.paint(g, x, y);
        // Outlines a cell when highlighted.
        if (highlighted) {
            g.setColor(Color.LIGHT_GRAY);
            for(int i = 0; i < 10; i++){
                g.drawRoundRect(x+1, y+1, 33, 33, i, i);
            }
        }
    }

    // Wrapping the super class's contains() method in our own contains() method.
    @Override
    public boolean contains(Point target){
        if (target == null)
            return false;
        return super.contains(target);
    }

    // Returns a meaningful String based on what the type of terrain is.
    public String getCellType(){
        if(this.getTerrain().equals("Block")){
            return "I'm a Block!";
        } else {
            return "Grass: " + this.terrain.getColour().getGreen() / 50 + "m";
        }
    }

    // A setter method for terrain.
    public void setTerrain(Terrain _terrain){
        this.terrain = _terrain;
    }

    // Used to transfer the String from Block or Grass, to other methods.
    public String getTerrain(){
        return this.terrain.toString();
    }

    // Equals method used in Path Finding.
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cell) {
            Cell temp = (Cell) obj;
            return this.getTerrain().equals(temp.getTerrain()) &&
                    this.getX() == temp.getX() &&
                    this.getY() == temp.getY();
        } else {
            return false;
        }
    }
}
