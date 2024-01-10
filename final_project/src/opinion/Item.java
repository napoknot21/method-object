package opinion;

public class Item {


    private String title;

    private String kind;

    
    public Item (String title, String kind) {

        this.title = title;
        this.kind = kind;

    }

    public String getTitle () {

        return this.title;
    
    }

    public String getKind () {

        return this.kind;

    }

}