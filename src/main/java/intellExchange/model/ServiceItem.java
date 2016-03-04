package intellExchange.model;

/**
 * Created by Yao on 3/3/16.
 */
public class ServiceItem {
    private final String name;
    private final String id;
    private final String description;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public ServiceItem(String name, String id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }
}
