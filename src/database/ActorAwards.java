package database;

public class ActorAwards {
    private String name;
    private Integer number_awards;

    public ActorAwards(String name, Integer number_awards) {
        this.name = name;
        this.number_awards = number_awards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber_awards() {
        return number_awards;
    }

    public void setNumber_awards(Integer number_awards) {
        this.number_awards = number_awards;
    }

    @Override
    public String toString() {
        return name ;
    }
}
