package opinion;

public class Film extends Item {

    private String director;

    private String scenarist;

    private int duration;

    public Film (String title, String kind, String director, String scenarist, int duration) {

        super (title, kind);
        this.scenarist = scenarist;
        this.director = director;
        this.duration = duration;

    }


    public String getDirector () {

        return this.director;

    }


    public String getScenarist() {

        return this.scenarist;

    }


    public int getDuration () {

        return this.duration;
    }


    public String toString () {
        return super.getTitle() + ", " + super.getKind() + ", " + this.getDirector() + ", " + this.getScenarist() + ", " + this.getDuration();
    }

}
