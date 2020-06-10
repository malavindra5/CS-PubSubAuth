package CruxHelper;

import Interfaces.GenericIDComponents;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class CruxUserInfo {
    private final String DEFAULT_BLOCKSTACK_NAMESPACE = "id";
    private final String CRUX_DOMAIN_SUFFIX = "_crux";
    private final String CRUX_PAY_FILE_NAME_SUFFIX = "_cruxpay.json";

    public static void main(String[] args) throws Exception {
        CruxUserInfo cruxUserInfo = new CruxUserInfo();
        cruxUserInfo.getCruxClaimAndPubKeyObject("mascot6699@cruxdev.crux");
    }

    public Object getCruxClaimAndPubKeyObject(String cruxIdString) throws Exception {
        CruxId cruxId = new CruxId();
        GenericIDComponents components = cruxId.fromString(cruxIdString);
        String cruxDomain = components.getDomain();
        components.setDomain(cruxDomain + CRUX_DOMAIN_SUFFIX);
        components.setNamespace(DEFAULT_BLOCKSTACK_NAMESPACE);
        String cruxToBlockStackName = cruxId.toBlockStackString(components);
        String nameDetails = getNameDetails(cruxToBlockStackName);
        String cruxPayFileName = nameDetails + "/" + cruxDomain + CRUX_PAY_FILE_NAME_SUFFIX;
        Object cruxUserContentByFileName = getContentByFileName(cruxPayFileName);
        JsonArray convertedJsonCruxUserContentByFileName = JsonConverter.JavaObjectToJSon(cruxUserContentByFileName);
        JsonObject contentByFileNameObject = (JsonObject) ((JsonObject) convertedJsonCruxUserContentByFileName.get(0)).get("decodedToken").getAsJsonObject().get("payload");
        JsonElement publicKey = contentByFileNameObject.get("issuer").getAsJsonObject().get("publicKey");
        JsonElement claim = contentByFileNameObject.get("claim");
        JsonObject cruxUserInfo = new JsonObject();
        cruxUserInfo.add("publicKey", publicKey);
        cruxUserInfo.add("claim", claim);
        return cruxUserInfo;
    }

    private String getNameDetails(String blockStackId) {
        try {
            String BNSUrl = "https://core.blockstack.org/v1/names/" + blockStackId;
            URL url = new URL(BNSUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            JsonObject response = getObjectResponseData(connection);
            return response.get("address").getAsString();
        } catch (IOException err) {
            System.out.println("Error occurred while fetching address");
        }
        return null;
    }

    private Object getContentByFileName(String fileName) {
        try {
            String bnsURL = "https://gaia.cruxpay.com/" + fileName;
            URL url = new URL(bnsURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return getArrayResponseData(connection);
        } catch (IOException err) {
            System.out.println("Error occurred while fetching address");
        }
        return null;

    }

    private JsonObject getObjectResponseData(HttpURLConnection connection) throws IOException {
        String response = getResponseData(connection);
        return new Gson().fromJson(response, JsonObject.class);
    }

    private JsonArray getArrayResponseData(HttpURLConnection connection) throws IOException {
        String response = getResponseData(connection);
        return new Gson().fromJson(response, JsonArray.class);
    }

    private String getResponseData(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        return response.toString();
    }
}
