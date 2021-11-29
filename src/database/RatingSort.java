package database;

import command.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class RatingSort {
    private ArrayList<VideoRating> videoRatings;

    public RatingSort() {
        this.videoRatings = new ArrayList<>();
    }

    public void sort_rating(Command command) {
        Comparator<VideoRating> videoRatingComparator = new Comparator<VideoRating>() {
            @Override
            public int compare(VideoRating o1, VideoRating o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        this.videoRatings.sort(videoRatingComparator);
        Comparator<VideoRating> videoRatingComparator1 = new Comparator<VideoRating>() {
            @Override
            public int compare(VideoRating o1, VideoRating o2) {
                return o1.getRating() < o2.getRating() ? -1
                        : o1.getRating() > o2.getRating()? 1
                        : 0;
            }
        };
        this.videoRatings.sort(videoRatingComparator1);
        if(command.getSortType().equals("desc")) {
            Collections.reverse(this.videoRatings);
        }
        if(videoRatings.size() < command.getNumber()) {
            command.setNumber(videoRatings.size());
        }
    }
    @Override
    public String toString() {
        return videoRatings + "]";
    }

    public void message(Command command) {
        command.setMessage("Query result: "  + videoRatings.toString());
    }

    public ArrayList<VideoRating> getVideoRatings() {
        return videoRatings;
    }

    public void setVideoRatings(ArrayList<VideoRating> videoRatings) {
        this.videoRatings = videoRatings;
    }
}
