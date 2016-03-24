package intellExchange.database;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import intellExchange.Constants;
import intellExchange.model.Category;
import intellExchange.model.PopularCategory;
import intellExchange.model.ServiceItem;

import java.util.*;

/**
 * Created by Yao on 3/4/16.
 */
public class DataSource {
    private static DynamoDB dynamoDB;
    private static AmazonDynamoDBClient client;

    public static void init() {
        client = new AmazonDynamoDBClient
                (new DefaultAWSCredentialsProviderChain()).
                withEndpoint(Constants.AMZ_WEST_URL);
        dynamoDB = new DynamoDB(client);
    }


    public static List<Category> getAllCategories() {
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("category");
        ScanResult result = client.scan(scanRequest);
        List<Category> res = new ArrayList<>();
        for (Map<String, AttributeValue> item : result.getItems()){
            System.out.println(item);
            String name = item.get("name").getS();
            Category newCategory = new Category(name);
            res.add(newCategory);
        }
        return res;
    }

    public static List<ServiceItem> getAllServiceItem() {
        Table table = dynamoDB.getTable("service_items");
        ItemCollection<ScanOutcome> result = table.scan();
        List<ServiceItem> res = new ArrayList<>();
        for (Item item : result) {
            ServiceItem newItem = generateServiceItem(item);
            res.add(newItem);
        }
        return res;
    }

    public static List<ServiceItem> getServiceItem(String targetCatName) {
        Table serviceItemTable = dynamoDB.getTable("service_items");
        Map<String, Object> attribute = new HashMap<>();
        attribute.put(":target", targetCatName);
        ItemCollection<ScanOutcome> result =
                serviceItemTable.scan("category = :target", null, attribute);

        List<ServiceItem> res = new ArrayList<>();
        for (Item item : result){
            ServiceItem newItem = generateServiceItem(item);
            res.add(newItem);
        }
        return res;
//        Table table = dynamoDB.getTable("service_items");
//        QuerySpec spec = new QuerySpec().
//                withKeyConditionExpression("").
//                withFilterExpression("category = :vcategory").
//                withValueMap(new ValueMap()
//                    .withString(":vcategory", targetCatName));
//
//        ItemCollection<QueryOutcome> items = table.query(spec);
//
//        Iterator<Item> iterator = items.iterator();
//        List<ServiceItem> res = new ArrayList<>();
//        while (iterator.hasNext()) {
//            Item item = iterator.next();
//            String id = (String)item.get("id");
//            String name = (String)item.get("name");
//            String description = (String)item.get("description");
//            ServiceItem newItem = new ServiceItem(name, id, description);
//            res.add(newItem);
//        }
//        return res;
    }

    private static ServiceItem generateServiceItem(Item item) {
        String id = (String)item.get("id");
        String name = (String)item.get("name");
        String description = (String)item.get("description");
        String category = (String)item.get("category");
        ServiceItem newItem = new ServiceItem(name, id, description, category);
        return newItem;
    }

    public static ServiceItem getItemByID(String id) {
        Table table = dynamoDB.getTable("service_items");
        QuerySpec spec = new QuerySpec().
                withKeyConditionExpression("id = :v_id").
                withValueMap(new ValueMap()
                    .withString(":v_id", "guitar-teaching"));
        ItemCollection<QueryOutcome> result = table.query(spec);
        List<ServiceItem> result_svi = new ArrayList<>();

        for(Item item : result) {
            System.out.println(item);
            result_svi.add(generateServiceItem(item));
            break;
        }

        return result_svi.size() > 0 ? result_svi.get(0) : null;

    }

    public static List<PopularCategory> getPopularCategory() {
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("popular_category");
        ScanResult result = client.scan(scanRequest);
        List<PopularCategory> res = new ArrayList<>();
        for (Map<String, AttributeValue> item : result.getItems()){
            System.out.println(item);
            String name = item.get("name").getS();
            Integer popularity = Integer.valueOf(item.get("popularity").getN());
            PopularCategory popCat = new PopularCategory(name, popularity);
            res.add(popCat);
        }
        return res;
    }
}
