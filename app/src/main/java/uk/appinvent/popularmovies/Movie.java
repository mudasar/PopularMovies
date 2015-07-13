package uk.appinvent.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mudasar on 10/07/2015.
 */
public class Movie implements Parcelable {

    // original title
     String title;
    //tmdb movie id
     long id;
    // movie genre
     String genre;
    // post path
     String posterPath;

    //Plot synopsis
     String plot;

     double userRating;

     String releaseDate;

    String runtime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return hashCode();
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(genre);
        dest.writeString(posterPath);
        dest.writeString(plot);
        dest.writeString(releaseDate);
        dest.writeDouble(userRating);
        dest.writeLong(id);
    }


    public Movie(String title, long id, String posterPath, String genre, String plot, double userRating, String releaseDate) {
        this.title = title;
        this.id = id;
        this.posterPath = posterPath;
        this.genre = genre;
        this.plot = plot;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public Movie(Parcel in){
        this.id = in.readLong();
        this.title = in.readString();
        this.genre = in.readString();
        this.releaseDate = in.readString();
        this.userRating = in.readDouble();
        this.plot = in.readString();
        this.posterPath = in.readString();

    }

    public class MovieCreator implements Parcelable.Creator<Movie>{

        /**
         * Create a new instance of the Parcelable class, instantiating it
         * from the given Parcel whose data had previously been written by
         * {@link android.os.Parcelable#writeToParcel Parcelable.writeToParcel()}.
         *
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        /**
         * Create a new array of the Parcelable class.
         *
         * @param size Size of the array.
         * @return Returns an array of the Parcelable class, with every entry
         * initialized to null.
         */
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
