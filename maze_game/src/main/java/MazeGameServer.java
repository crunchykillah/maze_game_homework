import java.io.*;
import java.net.*;
import java.util.*;

import maze_files.MazeGenerator;

public class MazeGameServer {
    private static final int PORT = 12345;
    private static final List<ClientHandler> clients = new ArrayList<>();
    private static MazeGenerator mazeGenerator;

    public static void main(String[] args) {
        mazeGenerator = new MazeGenerator(5, 5);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Подключился новый клиент.");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public static int[][] getMaze() {
        return mazeGenerator.getMaze();
    }

}
