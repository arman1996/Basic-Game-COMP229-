import bos.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Grid implements GameBoard<Cell> {

    // Grid will have 400 Cells.
    private Cell[][] cells = new Cell[20][20];

    private int x;
    private int y;
    // List containing the locations of all the Blocks.
    // Stored as a Pair <X, Y>.
    private List<bos.Pair<Integer, Integer>> blockLocations = new ArrayList<>();

    // Used for mouse listener.
    private Point lastSeenMousePos;
    private long stillMouseTime;

    // Grid constructor.
    public Grid(int x, int y, List<Pair<Integer, Integer>> _blockLocations) {
        this.x = x;
        this.y = y;
        this.blockLocations = _blockLocations;

        // Makes all the cells first.
        // They are all Grass by default.
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                cells[i][j] = new Cell(x + j * 35, y + i * 35);
            }
        }

        // The blocks are then drawn on top of the existing cells.
        // The terrain type fo the cells are changed to Block.
        // Thus they appear Brown.
        for (int i = 0; i < blockLocations.size(); i++) {
            this.cellAtRowCol(blockLocations.get(i).first, blockLocations.get(i).second).setTerrain(new Block());
        }

    }

    // Paint method for the grid is constantly run.
    public void paint(Graphics g, Point mousePosition) {
        if (lastSeenMousePos != null && lastSeenMousePos.equals(mousePosition)) {
            stillMouseTime++;
        } else {
            stillMouseTime = 0;
        }
        // the mouse pointer is passed to each paint method of Cell.
        doToEachCell((c) -> {
            c.paint(g, c.contains(mousePosition));
        });
        doToEachCell((c) -> {
            if (c.contains(mousePosition)) {
                if (stillMouseTime > 20) {
                    g.setColor(Color.YELLOW);
                    g.fillRoundRect(mousePosition.x + 20, mousePosition.y + 20, 65, 15, 3, 3);
                    g.setColor(Color.BLACK);
                    g.drawString(c.getCellType(), mousePosition.x + 20, mousePosition.y + 32);
                }
            }
        });
        lastSeenMousePos = mousePosition;


    }

    // Used to give Player a random cell every time the game loads a stage.
    public Cell getRandomCell() {
        java.util.Random rand = new java.util.Random();
        return cells[rand.nextInt(20)][rand.nextInt(20)];
    }

    public Cell cellAtRowCol(Integer row, Integer col) {
        return cells[row][col];
    }

    // Returns an Optional<Cell> when it is passed a Cell.
    public Optional<Pair<Integer, Integer>> safeFindAmongstCells(Predicate<Cell> predicate) {
        for (int y = 0; y < 20; ++y) {
            for (int x = 0; x < 20; ++x) {
                // The test() function returns True or False based on whether it has located the Cell or not.
                if (predicate.test(cells[y][x]))
                    // If True the Optional<Cell> is returned.
                    return Optional.of(new Pair(y, x));

            }
        }
        // If False an Optional<Empty> is returned.
        return Optional.empty();

    }

    // Uses the accept() function within Consumer to apply something to every single cell.
    // Also used to loop through the entire array of cells to search for things.
    private void doToEachCell(Consumer<Cell> func) {
        for (int y = 0; y < 20; ++y) {
            for (int x = 0; x < 20; ++x) {
                func.accept(cells[y][x]);
            }
        }
    }

    // Used to check if there is a cell below the current one.
    @Override
    public Optional<Cell> below(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.first < 19)
                .map((pair) -> cells[pair.first + 1][pair.second]);
    }

    // Used to check if there is a cell above the current one.
    @Override
    public Optional<Cell> above(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.first > 0)
                .map((pair) -> cells[pair.first - 1][pair.second]);
    }

    // Used to check if there is a cell to the right of the current one.
    @Override
    public Optional<Cell> rightOf(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.second < 19)
                .map((pair) -> cells[pair.first][pair.second + 1]);
    }

    // Used to check if there is a cell to the left of the current one.
    @Override
    public Optional<Cell> leftOf(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.second > 0)
                .map((pair) -> cells[pair.first][pair.second - 1]);

    }

    // Returns The Movable Cells as a List.
    public List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighboursList = new ArrayList<>();
        List<Optional<Cell>> possible = Arrays.asList(leftOf(cell), rightOf(cell), below(cell), above(cell));
        for (Optional<Cell> o : possible) {
            if (o.isPresent() && o.get().getTerrain().equals("Grass")) {
                neighboursList.add(o.get());
            }
        }
        return neighboursList;
    }

    // Breadth First Search.
    public HashMap<Cell, Integer> breadthFirstSearch(Cell startCell, Cell targetCell) {
        HashMap<Cell, Integer> weights = new HashMap<>();
        LinkedList<Cell> queue = new LinkedList<>();

        // Current Origin Location.
        weights.put(startCell, 0);

        // Initialize Queue and HashMap.
        for (Cell c : getNeighbours(startCell)) {
            queue.add(c);
            weights.put(c, 1);
        }

        // Keeps going until the queue finishes.
        while (!queue.isEmpty()) {
            // Takes the first cell off the queue.
            Cell temp = queue.remove();
            // Gets its neighbours.
            List<Cell> neighbours = getNeighbours(temp);

            // Covering a corner case.
            // If a cell hasn't been assigned a weight yet, then the "minimum weight" is set to -1.
            int min = -1;
            for (Cell c : neighbours) {
                // The first time a cell is found with a weight, it's weight is assigned to min.
                // And then other cells are checked and the minimum weight found.
                if (min == -1 || weights.getOrDefault(c, min) < min) {
                    min = weights.getOrDefault(c, -1);
                }
            }

            // If the target cell is found then the while loop breaks.
            // The equals() method from Cell is used in this case.
            if (temp.equals(targetCell)) {
                break;
            }

            // Puts cells into the HashMap.
            weights.put(temp, min + 1);
            for (Cell c : neighbours) {
                // If the cell isn't already in the queue and if the cell isn't in the HashMap, then we add it.
                if (!weights.containsKey(c) && !queue.contains(c)) {
                    queue.add(c);
                }
            }
        }

        // Returns a List of cells.
        return weights;
    }

    // Map Out The Shortest Path.
    public List<Cell> optimalPath(HashMap<Cell, Integer> weights, Cell from, Cell to) {
        List<Cell> path = new ArrayList<>();

        // Adds the target to the beginning of the list.
        Cell current = to;
        path.add(current);
        // Keeps iterating until the target cell and the origin cell is the same.
        while (!current.equals(from)) {
            // Gets neighbours of the current cell.
            List<Cell> neighbours = getNeighbours(current);

            // Covering a corner case.
            // If a cell appears that doesn't exist, then the "minimum cell" is set to null.
            Cell min = null;
            for (Cell c : neighbours) {
                if (min == null) {
                    // The first time a cell is found with a weight, it's weight is assigned to min.
                    if (weights.containsKey(c)) {
                        min = c;
                    }
                    // And then other cells are checked and the minimum weight found.
                } else if (weights.getOrDefault(c, weights.get(min)) < weights.get(min)) {
                    min = c;
                }
            }
            // The current cell is updated to the cell with the lowest weight.
            current = min;

            // The minimum cell is added to the front of the List.
            path.add(0, current);
        }
        // The List of cells from the origin to the target containing the lowest weights is returned.
        return path;
    }

    // Convert Cells to Moves.
    public List<RelativeMove> convertCellsToMoves(List<Cell> path, GamePiece<Cell> mover) {
        List<RelativeMove> moves = new ArrayList<>();

        // Iterates through all the cells in the List provided.
        for (int i = 0; i < path.size() - 1; i++) {
            Cell currentCell = above(path.get(i)).orElse(null);
            Cell nextcell = path.get(i + 1);

            // If the nextCell is above the currentCell then Move Up.
            if (currentCell == nextcell) {
                // Add Move Up.
                moves.add(new MoveUp(this, mover));
            }

            // If the nextCell is below the currentCell then Move Down.
            currentCell = below(path.get(i)).orElse(null);
            if (currentCell == path.get(i + 1)) {
                // Add Move Down.
                moves.add(new MoveDown(this, mover));
            }

            // If the nextCell is to the left of the currentCell then Move Left.
            currentCell = leftOf(path.get(i)).orElse(null);
            if (currentCell == path.get(i + 1)) {
                // Add Move Left.
                moves.add(new MoveLeft(this, mover));
            }

            // If the nextCell is to the right of the currentCell then Move Right.
            currentCell = rightOf(path.get(i)).orElse(null);
            if (currentCell == path.get(i + 1)) {
                // Add Move Right.
                moves.add(new MoveRight(this, mover));
            }
        }

        // The List of moves is returned.
        return moves;
    }

    //New MovesBetween Function.
    @Override
    public java.util.List<RelativeMove> movesBetween(Cell from, Cell to, GamePiece<Cell> mover) {
        System.out.println(breadthFirstSearch(from, to));
        // Uses breadthFirstSearch(), optimalPath() and convertCellsToMoves() methods in one hit.
        // Returns a list of moves.
        return convertCellsToMoves(optimalPath((breadthFirstSearch(from, to)), from, to), mover);
    }
}