package database;

import command.Command;
import entities.Actor;

import java.util.*;

public class AverageSort {
    public ArrayList<ActorSort> actorSorts;

    public AverageSort(HashMap<String, Actor> ListActor, Command command){
        this.actorSorts = new ArrayList<>(command.getNumber());
        ArrayList<ActorSort> list= new ArrayList<>();

        for(String key : ListActor.keySet()) {
            Actor actor = ListActor.get(key);
            if(actor.getAverage() != 0) {
                ActorSort actorSort = new ActorSort(actor.getName(), actor.getAverage());
               list.add(actorSort);
            }
        }
        Comparator<ActorSort> comparator = new Comparator<ActorSort>() {
            @Override
            public int compare(ActorSort o1, ActorSort o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        list.sort(comparator);
        Collections.sort(list);
        if(command.getSortType().equals("desc")){
            Collections.reverse(list);
        }
        if(command.getNumber() > list.size()){
            this.actorSorts = list;
        } else {
            for(int i = 0; i < command.getNumber(); ++i) {
                this.actorSorts.add(list.get(i));
            }
        }
    }

    @Override
    public String toString() {
        return actorSorts + "]";
    }

    public void message(Command command) {
        command.setMessage("Query result: "  + actorSorts.toString());
    }
}
