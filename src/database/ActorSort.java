package database;

public class ActorSort implements Comparable<ActorSort>{
    private String name;
    private Double ratings;

    public ActorSort(String name, Double ratings) {
        this.name = name;
        this.ratings = ratings;
    }

    @Override
    public int compareTo(ActorSort other) {
        return this.ratings < other.ratings ? -1 : this.ratings > other.ratings ? 1 : 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRatings() {
        return ratings;
    }

    public void setRatings(Double ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return name;
    }
}
