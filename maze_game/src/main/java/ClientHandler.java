import java.io.*;
import java.net.*;

import lombok.Getter;
import lombok.Setter;
import maze_files.*;
import org.json.*;
import service.JsonObjectCreator;
import service.MyFileWriter;

@Getter@Setter
public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private String clientName;
    private Maze maze;
    private PrintWriter out;
    private BufferedReader in;
    private String filePath = "maze_game/src/main/resources/clients.txt";
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    //Обработка сообщений клиента
    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject commandJSON = new JSONObject(inputLine);
                String command = commandJSON.getString("command");
                String minSteps = String.valueOf(MazePathFinder.shortestPath(MazeGameServer.getMaze(), new Point(0,0),
                        new Point(MazeGameServer.getMaze().length - 1,MazeGameServer.getMaze()[0].length / 2)));
                MyFileWriter writer = new MyFileWriter(filePath);
                if (command.equals("start")) {
                    clientName = commandJSON.getString("clientName");
                    int[][] mazeData = MazeGameServer.getMaze();
                    maze = new Maze(mazeData, 0, 0, mazeData.length - 1, mazeData[0].length / 2);
                    sendMessage(JsonObjectCreator.statusStart(clientName).toString());
                } else if (command.equals("direction")) {
                    String direction = commandJSON.getString("direction");
                    boolean validMove = maze.move(direction);
                    if (maze.isGameFinished() && validMove) {
                        writer.writeToFile(clientName + ";" + maze.getSteps() + ";" + minSteps);
                        sendMessage(JsonObjectCreator.statusStop(String.valueOf(maze.getSteps()),minSteps,filePath).toString());
                    } else {
                        sendMessage(JsonObjectCreator.statusGo(validMove).toString());
                    }
                } else if (command.equals("stop")) {
                    writer.writeToFile(clientName + ";" + maze.getSteps() + ";" + minSteps);
                    sendMessage(JsonObjectCreator.statusStop(String.valueOf(maze.getSteps()),minSteps,filePath).toString());
                    break;
                }
                maze.printMaze();
            }
            MazeGameServer.removeClient(this);
            clientSocket.close();
        } catch (IOException | JSONException e) {
            System.out.println("Клиент отключился !");
        }
    }
    public void sendMessage(String message) {
        out.println(message);
    }
}
