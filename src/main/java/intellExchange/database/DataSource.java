package intellExchange.database;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import intellExchange.Constants;
import intellExchange.model.Category;
import intellExchange.model.ServiceItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("service_items");
        ScanResult result = client.scan(scanRequest);

        List<ServiceItem> res = new ArrayList<>();
        for (Map<String, AttributeValue> item : result.getItems()){
            System.out.println(item);
            String id = item.get("id").getS();
            String name = item.get("name").getS();
            String description = item.get("description").getS();
            ServiceItem newItem = new ServiceItem(name, id, description);
            res.add(newItem);
        }
        return res;
    }
}
