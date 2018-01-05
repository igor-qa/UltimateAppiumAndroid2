package Networking;


import Utils.BaseTest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Iterator;

/**
 * Created by borisgurtovyy on 10/30/17.
 */
public class ServerManager extends BaseTest {

    private String base_url = "https://moviemates-prod.herokuapp.com/api/v1/";

    public String getMovies() throws IOException {
        String url = base_url + "movies?days=1";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        System.out.println(prop.getProperty("TOKEN"));
        request.addHeader("Authorization", "Token " + prop.getProperty("TOKEN"));
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }


    public int getMovieId(String name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(getMovies());
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray movies = (JSONArray) jsonObject.get("movies");
        Iterator<JSONObject> iterator = movies.iterator();
        while(iterator.hasNext()){
            JSONObject movieObject = iterator.next();
            String title = (String) movieObject.get("title");
            if (title.toUpperCase().equals(name)) {
                return Integer.valueOf((String) movieObject.get("rootId"));
            }
        }
        return -1;
    }

    public String updateUserProfile(String oldName, String oldGender, String oldBirthday, String oldLocation) throws IOException {
        String url = base_url + "users/"+ prop.getProperty("ID");
        HttpClient client = new DefaultHttpClient();
        HttpPut request = new HttpPut(url);
        System.out.println(prop.getProperty("TOKEN"));
        request.addHeader("Authorization", "Token " + prop.getProperty("TOKEN"));
        request.addHeader("Content-Type", "application/json");
        StringEntity jsonData = new StringEntity("{\"name\":\"" + oldName + "\", \"gender\":\"" + oldGender + "\", \"birthday\":\"" + oldBirthday + "\", \"location\":\"" + oldLocation + "\"}", "UTF-8");
        request.setEntity(jsonData);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();

    }

    public void cleanMovies() throws IOException, InterruptedException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(getMovies());
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray dates = (JSONArray) jsonObject.get("dates");
        Iterator i = dates.iterator();
        JSONObject innerObj = (JSONObject) i.next();
        JSONArray innerArray = (JSONArray) innerObj.get("movies");
        for(int x=0; x<innerArray.size(); x++) {
            JSONObject movieInt = (JSONObject) innerArray.get(x);
            if (movieInt.get("interested").equals(true)) {
                String rootId = (String) movieInt.get("rootId");
                System.out.println("The rootId: " + rootId);
                String base_url = "https://moviemates-prod.herokuapp.com/api/v1/movies/";
                String url = base_url + rootId + "?date=" + LocalDate.now();
                HttpClient client = new DefaultHttpClient();
                HttpDelete request = new HttpDelete(url);
                request.addHeader("Authorization", "Token " + prop.getProperty("TOKEN"));
                HttpResponse response = client.execute(request);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer result = new StringBuffer();
                String line = "";
                while((line = rd.readLine()) != null) {
                    result.append(line);
                }
                System.out.println(result.toString());
            }
        }
    }
}
