package uk.appinvent.popularmovies;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailsActivity extends ActionBarActivity {

    private static final String LOG_TAG = DetailsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_details, container, false);

            Intent pIntent = getActivity().getIntent();

                TextView titleView = (TextView) rootView.findViewById(R.id.movie_title);
                String title = pIntent.getStringExtra("title");

                titleView.setText(title);

            String plot = pIntent.getStringExtra("plot");

            TextView plotView = (TextView) rootView.findViewById(R.id.movie_plot);
            plotView.setText(plot);

            TextView releaseDateView = (TextView) rootView.findViewById(R.id.movie_release_date);
            String releaseDate = pIntent.getStringExtra("releasedate");
            releaseDateView.setText(releaseDate);

            ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
            String poster = pIntent.getStringExtra("poster");

           Picasso.with(rootView.getContext()).load(poster).into(imageView);


            //TODO:  parse into int  create a new a sync task to load movie

            //TODO: display details in the proper UI
            // TODO: handle orientation change event

            //TODO: implement search and settings

            return rootView;
        }
    }
}
