import java.util.ArrayList;
import java.util.List;

public class BoardEl {

    private final String word;
    private final int width;
    private final int height;
    private final Cell cell;
    private final boolean[][] visited;
    private final Dictionary.Prefix prefix;

    public BoardEl(Dictionary.Prefix prefix, BoardEl boardEl, Cell cell, String word) {
        this(prefix, boardEl.height, boardEl.width, cell, word);
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                visited[i][j] = visited[i][j] || boardEl.visited[i][j];
    }

    public BoardEl(Dictionary.Prefix prefix, int height, int width, Cell cell, String word) {
        this.width = width;
        this.height = height;
        this.word = word;
        visited = new boolean[height][width];
        visited[cell.getRow()][cell.getCol()] = true;
        this.cell = cell;
        this.prefix = prefix;
    }

    public String getWord() {
        return word;
    }

    public Dictionary.Prefix getPrefix() {
        return prefix;
    }

    public Iterable<Cell> getUnvisitedNeighbours() {
        List<Cell> result = new ArrayList<>();
        if (cell.getRow() - 1 >= 0) {
            result.addAll(getUnvisitedNeighbours(cell.getRow() - 1));
        }
        result.addAll(getUnvisitedNeighbours(cell.getRow()));
        if (cell.getRow() + 1 < height) {
            result.addAll(getUnvisitedNeighbours(cell.getRow() + 1));
        }
        return result;
    }

    private List<Cell> getUnvisitedNeighbours(int row) {
        List<Cell> result = new ArrayList<>(3);
        if (cell.getCol() - 1 >= 0 && !visited[row][cell.getCol() - 1]) {
            result.add(new Cell(row, cell.getCol() - 1));
        }
        if (cell.getRow() != row && !visited[row][cell.getCol()]) {
            result.add(new Cell(row, cell.getCol()));
        }
        if (cell.getCol() + 1 < width && !visited[row][cell.getCol() + 1]) {
            result.add(new Cell(row, cell.getCol() + 1));
        }
        return result;
    }

}