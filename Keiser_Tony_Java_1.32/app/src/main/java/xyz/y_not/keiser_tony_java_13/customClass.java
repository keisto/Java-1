package xyz.y_not.keiser_tony_java_13;

public class customClass {
    //Variables
    private String movieTitle;
    private String moviePlot;
    private String movieRelease;

    public customClass(String _movieTitle, String _moviePlot, String _movieRelease){
        movieTitle = _movieTitle;
        moviePlot = _moviePlot;
        movieRelease = _movieRelease;

    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMoviePlot() {
        return moviePlot;
    }

    public void setMoviePlot(String moviePlot) {
        this.moviePlot = moviePlot;
    }

    public String getMovieRelease() {
        return movieRelease;
    }

    public void setMovieRelease(String movieRelease) {
        this.movieRelease = movieRelease;
    }
}
