package uk.appinvent.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mudasar on 10/07/2015.
 */
public class LoadMoviesTask extends AsyncTask<String,String, ArrayList<Movie>> {

    private static final String LOG_TAG = LoadMoviesTask.class.getName();
    private OnTaskCompleted listener;

    public LoadMoviesTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected ArrayList<Movie> doInBackground(String... params) {



        String apiUrl = params[0];

Log.e(LOG_TAG,apiUrl);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String jsonData = "";

        try{

            URL nUrl = new URL(apiUrl);
            urlConnection = (HttpURLConnection) nUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null){
                return null;
            }

            String line = "";
            reader = new BufferedReader(new InputStreamReader(inputStream));
            if (reader == null){
                return null;
            }

            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine())!= null){
                buffer.append(line);
            }
            jsonData = buffer.toString();

        }catch (IOException e){
            e.printStackTrace();
            Log.e("network_error", e.getMessage());
        }

        finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("reader_error",e.getMessage());
                }
            }
        }

        Log.e(LOG_TAG,jsonData);

        if (jsonData != null){
            try {
                ArrayList<Movie> movieList = new ArrayList<Movie>();

                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray resultsArray = jsonObject.getJSONArray("results");
                for(int i=0; i < resultsArray.length(); i++){
                    JSONObject jsonMovie = resultsArray.getJSONObject(i);

                    Movie movie = new Movie(
                            jsonMovie.getString("original_title"),
                            jsonMovie.getLong("id"),
                            jsonMovie.getString("poster_path"),
                            "genre",
                            jsonMovie.getString("overview"),
                            jsonMovie.getDouble("vote_average"),

                             jsonMovie.getString("release_date")
                    );
                    movieList.add(movie);
                }
                return movieList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param movies The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        listener.onTaskCompleted(movies);
    }
}
