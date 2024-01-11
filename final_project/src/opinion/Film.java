package opinion;

/**
 * Represents a film in the social network.
 * This class extends the Item class and includes additional attributes specific to films, such as director, scenarist, and duration.
 */
public class Film extends Item {

    /**
     * The director of the film.
     */
    private String director;

    /**
     * The scenarist (screenwriter) of the film.
     */
    private String scenarist;

    /**
     * The duration of the film in minutes.
     */
    private int duration;


    /**
     * Constructs a new Film with specified details.
     * 
     * @param title      The title of the film.
     * @param kind       The kind or category of the film.
     * @param director   The director of the film.
     * @param scenarist  The scenarist (screenwriter) of the film.
     * @param duration   The duration of the film in minutes.
     */
    public Film(String title, String kind, String director, String scenarist, int duration) {

        super(title, kind);
        this.director = director;
        this.scenarist = scenarist;
        this.duration = duration;

    }

    /**
     * Retrieves the director of the film.
     * 
     * @return The director of the film.
     */
    public String getDirector() {

        return this.director;

    }

    /**
     * Retrieves the scenarist (screenwriter) of the film.
     * 
     * @return The scenarist of the film.
     */
    public String getScenarist() {

        return this.scenarist;

    }

    /**
     * Retrieves the duration of the film in minutes.
     * 
     * @return The duration of the film.
     */
    public int getDuration() {

        return this.duration;

    }

    /**
     * Returns a string representation of the Film.
     * Includes the title, kind, director, scenarist, and duration of the film.
     * 
     * @return A string representation of the Film.
     */
    @Override
    public String toString() {

        return super.getTitle() + ", " + super.getKind() + ", " + this.getDirector() + ", " + this.getScenarist() + ", " + this.getDuration();
    
    }

}
