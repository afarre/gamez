package Model.igdb.search;

public class IGDBFieldSearch {

    private long id;
    private String slug;

    public IGDBFieldSearch(long id, String name) {
        this.id = id;
        this.slug = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return slug;
    }

}
