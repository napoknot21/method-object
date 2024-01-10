package opinion;

public class Film extends Item {

    private String director;

    private int duration;

    public Film (String title, String director, int duration) {

        super(title);
        this.director = director;
        this.duration = duration;

    }

    public String getDirector() {
        return this.director;

    }
}
