package src

public class Film extends Item {

    private String director;

    private int duration;

    public Item (String title, String director, int duration) {

        super(title);
        this.director = director;
        this.duration = duration;
    
    }

    public String getDirector() {

        return this.director;

    }


    public int getDuration() {

        return this.duration;

    }



}