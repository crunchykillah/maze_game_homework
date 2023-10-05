package maze_files;

import lombok.Getter;
import lombok.Setter;
//Класс описывающий лабиринт
@Setter@Getter
public class Maze {
    private int[][] maze;
    private int numRows;
    private int numCols;
    private int startRow;
    private int startCol;
    private int exitRow;
    private int exitCol;
    private int currentRow;
    private int currentCol;
    private int steps;

    public Maze(int[][] maze, int startRow, int startCol, int exitRow, int exitCol) {
        this.maze = maze;
        this.numRows = maze.length;
        this.numCols = maze[0].length;
        this.startRow = startRow;
        this.startCol = startCol;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        this.currentRow = startRow;
        this.currentCol = startCol;
        this.steps = 0;
        maze[exitRow][exitCol] = 0;
    }

    public boolean move(String direction) {
        int newRow = currentRow;
        int newCol = currentCol;

        if (direction.equals("u")) {
            newRow--;
        } else if (direction.equals("d")) {
            newRow++;
        } else if (direction.equals("l")) {
            newCol--;
        } else if (direction.equals("r")) {
            newCol++;
        }
        if (isValidMove(newRow, newCol)) {
            currentRow = newRow;
            currentCol = newCol;
            System.out.println(currentRow + " " + currentCol);
            System.out.println(exitRow + " " + exitCol);
            steps++;
            return true;
        }
        return false;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols && maze[row][col] == 0;
    }

    public boolean isGameFinished() {
        return currentRow == exitRow && currentCol == exitCol;
    }
    public void printMaze() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (i == currentRow && j == currentCol) {
                    System.out.print("P "); // Позиция игрока
                } else if (i == exitRow && j == exitCol) {
                    System.out.print("E "); // Выход
                } else if (maze[i][j] == 1) {
                    System.out.print("# "); // Стена
                } else {
                    System.out.print(". "); // Проход
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
