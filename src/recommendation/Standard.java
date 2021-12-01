package recommendation;

import command.Command;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.HashMap;

public class Standard {
  private ArrayList<Movie> movieRecom;
  private ArrayList<TvShow> tvShowsRecom;
  private final HashMap<String, User> listUser;

  public Standard(final HashMap<String, User> listUser) {
    this.listUser = listUser;
  }

  /**
   * cauta in lista de video primul video nevazut de user
   * @param command
   * @param tvShowArrayList
   * @param movieArrayList
   */
  public void standardRun(
      final Command command,
      final ArrayList<TvShow> tvShowArrayList,
      final ArrayList<Movie> movieArrayList) {
    User user = listUser.get(command.getUsername());
    for (int i = 0; i < movieArrayList.size(); ++i) {
      if (!user.getMovieSeen().containsKey(movieArrayList.get(i).getTitle())) {
        command.setMessage("StandardRecommendation result: " + movieArrayList.get(i).getTitle());
        return;
      }
    }
    for (int i = 0; i < tvShowArrayList.size(); ++i) {
      if (!user.getMovieSeen().containsKey(tvShowArrayList.get(i).getTitle())) {
        command.setMessage("StandardRecommendation result: " + tvShowArrayList.get(i).getTitle());
        return;
      }
    }
    command.setMessage("StandardRecommendation cannot be applied!");
  }
}
