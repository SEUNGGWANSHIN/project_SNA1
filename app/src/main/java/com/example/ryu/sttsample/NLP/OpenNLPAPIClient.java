package com.example.ryu.sttsample.NLP;

/**
 * Created by Ryu on 2017-07-29.
 */
import android.util.Log;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Iterator;


public class OpenNLPAPIClient {
    public String getName(String content) {
        Log.d("jun", content);
        String PersonName = "";
        try {
            URL url = new URL("https://language.googleapis.com/v1beta2/documents:analyzeEntities?" +
                    "key=AIzaSyDRJqdGZoPpbN8LBuDrWMVCaFulRU8L2mc");
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            Log.d("jun", "Make URL Connection");

            urlConn.setRequestMethod("POST");
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-Type", "application/json");
            Log.d("jun", "Setting Request");

            urlConn.connect();

            JSONObject obj = new JSONObject();
            JSONObject document = new JSONObject();
            document.put("type", "PLAIN_TEXT");
            document.put("language", "en");
            document.put("content", content);
            obj.put("document", document);
            obj.put("encodingType", "UTF8");
            OutputStreamWriter output= new OutputStreamWriter(urlConn.getOutputStream());
            output.write(obj.toString());
            output.flush();
            output.close();
            Log.d("jun", "write Body");

            StringBuilder sb = new StringBuilder();
            String in = null;
            int HttpResult = urlConn.getResponseCode();
            Log.d("jun", "d");
            //if(HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            Log.d("jun", "h");
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Log.d("jun", "i");
            br.close();
            in = sb.toString();
            Log.d("jun", in);
            //}
            //else{
            // Log.d("jun", valueOf(HttpResult));
            //}

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(in);
            JSONArray entities = (JSONArray)jsonObject.get("entities");
            Iterator<JSONObject> iterator = entities.iterator();

            while (iterator.hasNext()) {
                JSONObject tmp = iterator.next();
                Log.d("jun", tmp.get("type").toString());
                if(tmp.get("type").toString().equals("PERSON"))
                {
                    PersonName = tmp.get("name").toString();
                    Log.d("jun", PersonName);
                }
                Log.d("jun", tmp.toString());
            }

            urlConn.disconnect();

        }catch (MalformedURLException mex) {
            PersonName = "MalformedURLException";
            Log.d("jun","MalformedURLException");
        } catch (ProtocolException pex) {
            PersonName = "ProtocolException";
            Log.d("jun","ProtocolException");
        } catch (IOException iex) {
            PersonName = "IOException";
            Log.d("jun","IOException");
        } catch (ParseException paex) {
            PersonName = "ParseException";
            Log.d("jun","ParseException");
        }
        Log.d("jun",PersonName);
        return PersonName;
    }
}