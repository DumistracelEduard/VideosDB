package recommendation;

import command.Command;
import entertainment.Genre;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Popular {
    private HashMap<String, Integer> genres;
    private ArrayList<RecommendationObject> listVideo;
    private List<String> listgenres;
    private ArrayList<PopularObject> listGenre;

    public Popular(ArrayList<RecommendationObject> listVideo) {
        this.listVideo = listVideo;
        this.listgenres = new ArrayList<>();
        this.genres = new HashMap<>();
        this.listGenre = new ArrayList<>();

        for(RecommendationObject object : listVideo) {
            for(int i = 0; i < object.getGenres().size(); ++i) {
                if(!genres.containsKey(object.getGenres().get(i))) {
                    genres.put(object.getGenres().get(i), 1);
                } else {
                    genres.put(object.getGenres().get(i), genres.get(object.getGenres().get(i)) + 1);
                }
            }
        }
    }

    public void sortGenres(Command command, HashMap<String, User> ListUser, ArrayList<Movie> ListMovie, ArrayList<TvShow> ListShows) {
        for(String key: this.genres.keySet()) {
            PopularObject object = new PopularObject(key, this.genres.get(key));
            this.listGenre.add(object);
        }

        Comparator<PopularObject> comparator = new Comparator<PopularObject>() {
            @Override
            public int compare(PopularObject o1, PopularObject o2) {
                return o1.getNumber() < o2.getNumber() ? 1
                        : o1.getNumber() > o2.getNumber() ? -1
                        : 0;
            }
        };

        this.listGenre.sort(comparator);
        User user = ListUser.get(command.getUsername());
        for(PopularObject popularObject : this.listGenre) {
            for(Movie movie: ListMovie) {
                List<String> genres = movie.getGenres();
                for(int i = 0; i < genres.size(); ++i) {
                    if(genres.get(i).equals(popularObject.getGenre())) {
                        if(!user.getMovie_seen().containsKey(movie.getTitle())) {
                            command.setMessage("PopularRecommendation result: " + movie.getTitle());
                            return;
                        }
                    }
                }
            }
            for(TvShow tvShow: ListShows) {
                List<String> genres = tvShow.getGenres();
                for(int i = 0; i < genres.size(); ++i) {
                    if(genres.get(i).equals(popularObject.getGenre())); {
                        if(!user.getMovie_seen().containsKey(tvShow.getTitle())) {
                            command.setMessage("PopularRecommendation result: " + tvShow.getTitle());
                            return;
                        }
                    }
                }
            }
        }
        command.setMessage("PopularRecommendation cannot be applied!");
    }
}
