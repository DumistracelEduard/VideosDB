package database;

import command.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RatingSort {
  private ArrayList<VideoRating> videoRatings;

  public RatingSort() {
    this.videoRatings = new ArrayList<>();
  }

    /**
     * sorteaza lista de ratings si se scot primele N pers
     * @param command
     */
  public void sortRating(final Command command) {
    Comparator<VideoRating> videoRatingComparator =
        new Comparator<VideoRating>() {
          @Override
          public int compare(final VideoRating o1, final VideoRating o2) {
            return o1.getName().compareTo(o2.getName());
          }
        };
    this.videoRatings.sort(videoRatingComparator);
    Comparator<VideoRating> videoRatingComparator1 =
        new Comparator<VideoRating>() {
          @Override
          public int compare(final VideoRating o1, final VideoRating o2) {
            return o1.getRating() < o2.getRating() ? -1 : o1.getRating() > o2.getRating() ? 1 : 0;
          }
        };
    this.videoRatings.sort(videoRatingComparator1);
    if (command.getSortType().equals("desc")) {
      Collections.reverse(this.videoRatings);
    }
    if (videoRatings.size() < command.getNumber()) {
      command.setNumber(videoRatings.size());
    }
  }

  @Override
  public final String toString() {
    return videoRatings + "]";
  }

    /**
     * salveaza mesajul
     * @param command
     */
  public void message(final Command command) {
    command.setMessage("Query result: " + videoRatings.toString());
  }

  public final ArrayList<VideoRating> getVideoRatings() {
    return videoRatings;
  }
}
