package maze_files;

import java.util.LinkedList;
import java.util.Queue;

//BFS SEARCH
public class MazePathFinder {
    public static int shortestPath(int[][] matrix, Point start, Point finish) {
        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };
        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean[][] visited = new boolean[rows][cols];

        Queue<Point> queue = new LinkedList<>();
        queue.offer(start);
        visited[start.x][start.y] = true;

        int distance = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Point current = queue.poll();
                if (current.x == finish.x && current.y == finish.y) {
                    return distance;
                }
                for (int j = 0; j < 4; j++) {
                    int newX = current.x + dx[j];
                    int newY = current.y + dy[j];

                    if (newX >= 0 && newX < rows && newY >= 0 && newY < cols
                            && matrix[newX][newY] == 0 && !visited[newX][newY]) {
                        queue.offer(new Point(newX, newY));
                        visited[newX][newY] = true;
                    }
                }
            }
            distance++;
        }

        return -1;
    }
    //Проверка на существование пути
    public static boolean isPathExist(int[][] matrix, Point start, Point finish) {
        int shortestDistance = shortestPath(matrix, start, finish);
        if (shortestDistance != -1) {
            return true;
        } else {
            return false;
        }
    }
}
