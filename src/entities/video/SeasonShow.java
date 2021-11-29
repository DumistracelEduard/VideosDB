package entities.video;

import java.util.ArrayList;
import java.util.List;

public class SeasonShow {
    private int currSeason;
    private int duration;
    private List<Double> ratings;

    public SeasonShow(int currSeason, int duration, List<Double> ratings) {
        this.currSeason = currSeason;
        this.duration = duration;
        this.ratings = ratings;
    }

    public double rating_season(){
        double sum = 0;
        for(int i = 0; i < ratings.size(); ++i) {
            sum += ratings.get(i);
        }
        if(sum == 0) {
            return 0;
        } else {
            return sum / ratings.size();
        }
    }

    public int getCurrSeason() {
        return currSeason;
    }

    public void setCurrSeason(int currSeason) {
        this.currSeason = currSeason;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }
}
