package service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//Класс отвечающий за создание JSON-объектов
public class JsonObjectCreator {
    public static JSONObject statusStart(String clientName) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", "start");
        responseJSON.put("message", "Привет, " + clientName);
        responseJSON.put("startPoint", new int[]{0,0});
        return responseJSON;
    }
    public static JSONObject statusGo(boolean validMove) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", "go");
        responseJSON.put("result", validMove ? "0" : "1");
        return responseJSON;
    }

    public static JSONObject commandStart(String clientName) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("command", "start");
        responseJSON.put("clientName", clientName);
        return responseJSON;
    }
    public static JSONObject commandDirection(String direction) {
        JSONObject directionJSON = new JSONObject();
        directionJSON.put("command", "direction");
        directionJSON.put("direction", direction);
        return directionJSON;
    }
    public static JSONObject commandStop() {
        JSONObject stopJson = new JSONObject();
        stopJson.put("command", "stop");
        return stopJson;
    }
    public static JSONArray createJsonWithRating(String filename) throws IOException {
        JSONArray ratingArray = new JSONArray();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            JSONObject ratingItem = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    ratingItem = new JSONObject();
                    ratingItem.put("name", parts[0]);
                    ratingItem.put("steps", parts[1]);
                    ratingItem.put("min", parts[2]);
                }
                ratingArray.put(ratingItem);
            }

        }
        return ratingArray;
    }
    public static JSONObject statusStop(String steps, String minSteps,String filename) throws IOException {
        JSONArray ratingArray = createJsonWithRating(filename);
        JSONObject result = new JSONObject();
        result.put("status", "stop");
        result.put("rating", ratingArray);
        result.put("result",steps);
        result.put("min",minSteps);

        return result;
    }
    public static String JSONArrayToString(JSONArray jsonArray) {
        String[] stringArray = new String[jsonArray.length()];
        StringBuilder rating = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String name = item.getString("name");
            String steps = item.getString("steps");
            String min = item.getString("min");
            rating.append("Имя: ").append(name).append(", Кол-во шагов: ").append(steps).append(", Минимальное кол-во шагов: ").append(min).append("\n");
        }
        return rating.toString();
    }
}
