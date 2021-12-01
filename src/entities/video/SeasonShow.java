package entities.video;

import java.util.List;

public class SeasonShow {
    private final int currSeason;
    private final int duration;
    private final List<Double> ratings;

    public SeasonShow(final int currSeason, final int duration, final List<Double> ratings) {
        this.currSeason = currSeason;
        this.duration = duration;
        this.ratings = ratings;
    }

    /**
     * Returneaza media de rating a sezonului
     * @return
     */
    public double ratingSeason() {
        double sum = 0;
        for (int i = 0; i < ratings.size(); ++i) {
            sum += ratings.get(i);
        }
        if (sum == 0) {
            return 0;
        } else {
            return sum / ratings.size();
        }
    }

    public final int getDuration() {
        return duration;
    }

    public final List<Double> getRatings() {
        return ratings;
    }
}
