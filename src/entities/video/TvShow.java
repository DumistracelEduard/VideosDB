package entities.video;

import command.Command;
import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public class TvShow extends Video {
    private final int numberOfSeasons;
    private final ArrayList<SeasonShow> seasons;
    private double rating;
    private int numberFavorite;
    private int time;

    public TvShow(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres, final int numberOfSeasons,
                  final ArrayList<SeasonShow> seasons, final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.numberFavorite = 0;
    }

    public TvShow(final SerialInputData serial) {
        super(serial.getTitle(), serial.getYear(), serial.getCast(), serial.getGenres());
        this.numberOfSeasons = serial.getNumberSeason();
        this.seasons = new ArrayList<>(numberOfSeasons);
        this.numberFavorite = 0;
        for (int i = 0; i < serial.getSeasons().size(); ++i) {
            Season season = serial.getSeasons().get(i);
            SeasonShow seasonShow = new SeasonShow(i, season.getDuration(), season.getRatings());
            this.seasons.add(seasonShow);
        }
    }

    /**
     * Calculeaza timpul total al serialului
     */
    public void timeTotal() {
        for (int i = 0; i < numberOfSeasons; ++i) {
            this.time += this.seasons.get(i).getDuration();
        }
    }

    /**
     * Adauga la numarul de favorite
     */
    public void addFavorite() {
        this.numberFavorite += 1;
    }

    /**
     * Adauga unui sezon un rating
     * @param command
     */
    public void addRating(final Command command) {
        this.seasons.get(command.getSeasonNumber() - 1).getRatings().add(command.getGrade());
        this.seasons.get(command.getSeasonNumber() - 1).ratingSeason();
    }

    /**
     * Calculeaza ratingul total
     */
    public void ratingTotal() {
        double sum = 0;
        for (int i = 0; i < seasons.size(); ++i) {
            sum += seasons.get(i).ratingSeason();
        }
        if (sum == 0) {
            this.rating = 0;
            return;
        }
        this.rating = sum / seasons.size();
    }

    public final int getNumberFavorite() {
        return numberFavorite;
    }

    public final int getTime() {
        return time;
    }

    public final double getRating() {
        return rating;
    }

    public final ArrayList<SeasonShow> getSeasons() {
        return seasons;
    }
}
