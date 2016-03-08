package intellExchange.controller;

import intellExchange.database.DataSource;
import intellExchange.model.Category;
import intellExchange.model.ServiceItem;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Yao on 2/27/16.
 */

@RestController
public class TestController {

    @CrossOrigin
    @RequestMapping("/item")
    public List<ServiceItem> getAllItems() {
        return DataSource.getAllServiceItem();
    }

    /* Controller that get all categories */
    @CrossOrigin
    @RequestMapping("/category")
    public List<Category> getAllCategories() {
        return DataSource.getAllCategories();
    }

    /* Get all items in one category */

    @CrossOrigin
    @RequestMapping(value="/category/{name}")
    public List<ServiceItem> getItemsInOneCategory(@PathVariable("name") String name) {
        return DataSource.getServiceItem(name);
    }

    @CrossOrigin
    @RequestMapping(value="/item/{itemID}")
    public ServiceItem getItemByID(@PathVariable("itemID") String id) {
        return DataSource.getItemByID(id);
    }
}