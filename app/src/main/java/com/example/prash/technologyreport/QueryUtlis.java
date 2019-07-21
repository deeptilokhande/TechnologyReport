package com.example.prash.technologyreport;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by prash on 7/15/2018.
 */

public final class QueryUtlis {
    // private static final String SAMPLE_JSON_RESPONSE = "https://content.guardianapis.com/search?q=technology&api-key=d40b9fde-a745-49f1-bc1e-60ec99092dec";
    private static final String LOG_TAG = MainActivity.class.getName();

    //making constructor private , so object of it is not created accidently.
    //with all methods and variables declared as static, they can be accessed directly from class name.QueryUtils
    private QueryUtlis() {
    }

    public static List<News> fetchNewsData(String requestUrl) {

        //Create URL object
        URL url = createUrl(requestUrl);

        //Perform HTTP request with URL and receive JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem in Making HTTP Request.", e);
        }
        ;

        //Extract relevant fields from JSON response and create a List of News
        List<News> newsList = extractFeatureFromJson(jsonResponse);

        //Return  the List of latest news
        return newsList;
    }


    //Create , form and return URL object
    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;

    }

    //Create HTTP request for URL , connect and get a response in form of string.
    private static String makeHTTPUrl(URL url) throws IOException {
        String response = "";

        if (url == null) {
            return response;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return response;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    public static ArrayList<News> extractFeatureFromJson(String jsonResponse) {


        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding newslist to.
        ArrayList<News> news = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            // Convert  JSON_RESPONSE String into a JSONObject
            JSONObject rootJSON = new JSONObject(jsonResponse);

            //Extract Results JSON Array

            JSONObject response = rootJSON.getJSONObject("response");
            if (response == null || response.length() == 0) {
                return null;
            }

            JSONArray resultsArray = response.getJSONArray("results");

            //Looping through News JSON object at position i

            String webTitle;
            String author;
            String section;
            String date;
            String url;


            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject currentnews = resultsArray.getJSONObject(i);
                //get attributes of JSON object

                section = currentnews.getString("sectionName");

                String rawdate = currentnews.getString("webPublicationDate");
                date = formatDate(rawdate);
                webTitle = currentnews.getString("webTitle");
                url = currentnews.getString("webUrl");
                JSONArray authorArray = currentnews.getJSONArray("tags");
                author = "";
                if (authorArray.length() == 0) {
                    author = "";
                } else {
                    for (int j = 0; j < authorArray.length(); j++) {
                        JSONObject firstObject = authorArray.getJSONObject(j);
                        author += firstObject.getString("webTitle") + ". ";
                    }

                }
                // Create Earthquake java object from magnitude, location, and time
                // and Add earthquake to list of earthquakes
                news.add(new News(webTitle, author, date, section, url));
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("Query Utils", "Problem parsing the News JSON results", e);
        }

        // Return the list of earthquakes
        return news;
    }


    private static String formatDate(String rawDate) {
        String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);
        try {
            Date parsedJsonDate = jsonFormatter.parse(rawDate);
            String finalDatePattern = "MMM d, yyy";
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
            return finalDateFormatter.format(parsedJsonDate);
        } catch (ParseException e) {
            Log.e("QueryUtils", "Error parsing JSON date: ", e);
            return "";
        }
    }


}

