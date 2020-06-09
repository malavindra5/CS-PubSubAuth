package CruxHelper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class JsonConverter {

    public static JsonArray JavaObjectToJSon(Object cruxUserContentByFileName) {
        Gson gson = new Gson();
        String json = gson.toJson(cruxUserContentByFileName);
        System.out.println("Using Gson.toJson() on a raw collection: " + json);
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(json).getAsJsonArray();
        System.out.println("Using parser to JsonArray: " + array);
        return array;
    }

}
