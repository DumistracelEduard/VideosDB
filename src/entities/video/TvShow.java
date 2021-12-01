package entities.video;

import command.Command;
import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public class TvShow extends Video{
    private int numberOfSeasons;
    private ArrayList<SeasonShow> seasons;
    private double rating;
    private int number_favorite;
    private int time;

    public TvShow(String title, ArrayList<String> cast,
                  ArrayList<String> genres, int numberOfSeasons,
                  ArrayList<SeasonShow> seasons, int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.number_favorite = 0;
    }

    public TvShow(SerialInputData serial) {
        super(serial.getTitle(), serial.getYear(), serial.getCast(), serial.getGenres());
        this.numberOfSeasons = serial.getNumberSeason();
        this.seasons = new ArrayList<>(numberOfSeasons);
        this.number_favorite = 0;
        for(int i = 0; i < serial.getSeasons().size(); ++i) {
            Season season = serial.getSeasons().get(i);
            SeasonShow seasonShow = new SeasonShow(i, season.getDuration(), season.getRatings());
            this.seasons.add(seasonShow);
        }
    }
    public void time_total(){
        for(int i = 0; i < numberOfSeasons; ++i) {
            this.time += this.seasons.get(i).getDuration();
        }
    }

    public void add_favorite() {
        this.number_favorite += 1;
    }

    public void add_rating(Command command) {
        this.seasons.get(command.getSeasonNumber() - 1).getRatings().add(command.getGrade());
        this.seasons.get(command.getSeasonNumber() - 1).rating_season();
    }

    public void rating_total() {
        double sum = 0;
        for(int i = 0; i < seasons.size(); ++i) {
            sum += seasons.get(i).rating_season();
        }
        if(sum == 0) {
            this.rating = 0;
            return;
        }
        this.rating = sum / seasons.size();
    }

    public int getNumber_favorite() {
        return number_favorite;
    }

    public void setNumber_favorite(int number_favorite) {
        this.number_favorite = number_favorite;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<SeasonShow> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<SeasonShow> seasons) {
        this.seasons = seasons;
    }
}
