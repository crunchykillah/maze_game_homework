import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.*;
import service.JsonObjectCreator;

public class MazeGameClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    //Клиент
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Подключение к серверу " + SERVER_IP + ":" + SERVER_PORT);
            System.out.print("Введите ваше имя: ");
            String clientName = scanner.nextLine();
            out.println(JsonObjectCreator.commandStart(clientName).toString());

            String response = in.readLine();
            JSONObject responseJSON = new JSONObject(response);
            //Обработка запросов
            if (responseJSON.getString("status").equals("start")) {
                System.out.println(responseJSON.getString("message"));
                int[] startPoint = toIntArray(responseJSON.getJSONArray("startPoint"));
                int currentRow = startPoint[0];
                int currentCol = startPoint[1];
                System.out.println("Стартовая точка: " + currentRow + ", " + currentCol);

                while (true) {
                    System.out.print("Введите направление (u - вверх, d - вниз, l - влево, r - вправо, stop - остановить игру): ");
                    String direction = scanner.nextLine();
                    if (direction.equals("stop")) {
                        out.println(JsonObjectCreator.commandStop().toString());
                    }
                    out.println(JsonObjectCreator.commandDirection(direction).toString());

                    response = in.readLine();
                    responseJSON = new JSONObject(response);

                    if (responseJSON.getString("status").equals("go")) {
                        int result = Integer.parseInt(responseJSON.getString("result"));
                        if (result == 0) {
                            System.out.println("Вы успешно сделали ход.");
                        } else {
                            System.out.println("Вы не можете сделать этот ход, там стена.");
                        }
                    } else if (responseJSON.getString("status").equals("stop")) {
                        int steps = Integer.parseInt(responseJSON.getString("result"));
                        int minSteps = Integer.parseInt(responseJSON.getString("min"));
                        String rating = JsonObjectCreator.JSONArrayToString(responseJSON.getJSONArray("rating"));
                        System.out.println("Вы завершили игру!");
                        System.out.println("Сделано шагов: " + steps);
                        System.out.println("Минимальное число шагов: " + minSteps);
                        System.out.println("Рейтинг: " + "\n" + rating);
                        break;
                    }
                }
            } else {
                System.out.println("Ошибка подключения.");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    //JSONArray --> int[ ]
    public static int[] toIntArray(JSONArray jsonArray) {
        int[] result = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            result[i] = jsonArray.getInt(i);
        }
        return result;
    }
}
