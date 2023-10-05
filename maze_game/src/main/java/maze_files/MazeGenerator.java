package maze_files;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;
//Класс отвечающий за генерацию данных в лабиринте (матрице)
@Getter@Setter
public class MazeGenerator {
    private int numRows;
    private int numCols;
    private int[][] maze;

    public MazeGenerator(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.maze = generateMaze();
    }

    private int[][] generateMaze() {
        int[][] generatedMaze = new int[numRows][numCols];
        Random random = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                generatedMaze[i][j] = (random.nextInt(3) == 0) ? 1 : 0;
            }
        }
        if (MazePathFinder.isPathExist(generatedMaze, new Point(0,0), new Point(generatedMaze.length - 1,generatedMaze[0].length / 2))) {
            return generatedMaze;
        } else {
            generatedMaze = generateMaze();
        }
        return generatedMaze;
    }

}
