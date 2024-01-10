package opinion;

import java.util.LinkedList;

public abstract class Item {


    private String title;

    private String kind;

    private LinkedList<Review> reviews;

    
    public Item (String title, String kind) {

        this.title = title;
        this.kind = kind;
        this.reviews = new LinkedList<Review>();

    }

    public String getTitle () {

        return this.title;
    
    }

    public String getKind () {

        return this.kind;

    }

    public LinkedList<Review> getReviews () {

        return this.reviews;

    }


    public float averageNote () {

        if (this.reviews.size() == 0) return Float.NaN;

        float ret = 0;

        for (Review r : this.reviews) {

            ret = (float) (ret + r.getMark());

        }
        return (ret/this.reviews.size());
    }
}