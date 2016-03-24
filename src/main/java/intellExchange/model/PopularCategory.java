package intellExchange.model;

/**
 * Created by Yao on 3/21/16.
 */
public class PopularCategory {
    private final String name;
    private final Integer popularity;

    public String getName() {
        return name;
    }

    public Integer getPopularity() {

        return popularity;
    }

    public PopularCategory(String name, Integer popularity) {
        this.name = name;
        this.popularity = popularity;
    }
}
