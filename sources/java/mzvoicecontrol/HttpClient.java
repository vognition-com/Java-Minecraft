package mzvoicecontrol;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import scala.util.parsing.json.JSONObject;

public class HttpClient {

    public static JsonObject main(URL url, byte[] postDataBytes, String requestType) throws Exception {
    	HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod(requestType);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        
        int intValueOfChar;
        String targetString = "";
        while ((intValueOfChar = in.read()) != -1) {
            targetString += (char) intValueOfChar;
            //System.out.println((char) intValueOfChar);
        }
        	
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(targetString).getAsJsonObject();
        //String returnResponse = obj.get("response").toString();
        System.out.println(obj.toString());
        return obj;
    }
}
