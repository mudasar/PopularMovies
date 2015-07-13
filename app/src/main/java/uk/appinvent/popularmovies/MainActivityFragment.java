package uk.appinvent.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getName();
    public static final String DETAIL_ID = "DETAIL_ID";
    private final static String API_BASE_URL = "api.themoviedb.org";
    private static final String API_URL = "3/movie/";
    //popular
    private final static String API_KEY ="d1ef9dc0336bed3f42aa90354fdc4abf";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

     ImageAdapter imageAdapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movies_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String api_method =    preferences.getString(getString(R.string.sort_order_key),"popular");


        LoadMoviesTask loadMoviesTask = new LoadMoviesTask();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority(API_BASE_URL);
        builder.appendEncodedPath(API_URL + api_method);
        builder.appendQueryParameter("api_key", API_KEY);
        Uri url = builder.build();

        AsyncTask<String, String, List<Movie>> task = loadMoviesTask.execute(url.toString());
        List<Movie> movies = new ArrayList<Movie>();
        try {
            movies =  task.get();
            if (movies != null) {
                for (Movie movie : movies) {
                    //set image path
                    movie.setPosterPath(IMAGE_BASE_URL + "w500" + movie.getPosterPath());
                }
                imageAdapter.moviesList.clear();
                imageAdapter.moviesList.addAll(movies);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_movies, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.movies_grid_view);

        final Context appContext = this.getActivity().getApplicationContext();

        List<Movie> movies = new ArrayList<Movie>();





        imageAdapter = new ImageAdapter(appContext, movies);

        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie =(Movie) imageAdapter.getItem(position);

//                Toast toast = Toast.makeText(appContext,movie.getTitle() + " was clicked",Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0,10);
//                toast.show();

                Intent detailIntent = new Intent(appContext,DetailsActivity.class);
                detailIntent.putExtra("title",movie.title);
                detailIntent.putExtra("plot",movie.plot);
                detailIntent.putExtra("poster",movie.posterPath);
                detailIntent.putExtra("id",movie.id);
                detailIntent.putExtra("releasedate",movie.releaseDate);

                startActivity(detailIntent);

            }
        });

        return rootView;
    }



}
