package uk.appinvent.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
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
    private final static String API_BASE_URL = "api.themoviedb.org";
    private static final String API_URL = "3/movie/popular";
    private final static String API_KEY ="d1ef9dc0336bed3f42aa90354fdc4abf";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_movies, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.movies_grid_view);

        final Context appContext = this.getActivity().getApplicationContext();

        List<Movie> movies = new ArrayList<Movie>();

        for (int i = 0; i < 15; i++){
            Movie movie = new Movie();
            movie.setTitle("Movie " + i);
            movie.setGenre("comedy");
            movie.setId(i);
            movie.setPlot("this is sample text");
            movie.setReleaseDate("12/07/2015");
            movie.setUserRating(i);
            movie.setPosterPath(IMAGE_BASE_URL + "w500/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg");

            movies.add(movie);
        }

        LoadMoviesTask loadMoviesTask = new LoadMoviesTask();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority(API_BASE_URL);
        builder.appendEncodedPath(API_URL);
        builder.appendQueryParameter("api_key",API_KEY);
        Uri url = builder.build();

        AsyncTask<String, String, List<Movie>> task = loadMoviesTask.execute(url.toString());
        try {
          movies =  task.get();
            if (movies != null) {
                for (Movie movie : movies) {
                    //set image path
                    movie.setPosterPath(IMAGE_BASE_URL + "w500" + movie.getPosterPath());
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        final ImageAdapter imageAdapter = new ImageAdapter(appContext, movies);

        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie =(Movie) imageAdapter.getItem(position);

                Toast toast = Toast.makeText(appContext,movie.getTitle() + " was clicked",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0,10);
                toast.show();
            }
        });

        return rootView;
    }
}
