package school;

import com.google.gson.Gson;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import school.HistoryContent;
import school.School;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class DataProvider {
    abstract public School getById(long id);

    abstract public List<School> selectSchools();

    abstract public void insert(School school);

    abstract public void delete(long id);

    abstract public void update(School school);

    public void saveHistory(String className, String methodName, HistoryContent.Status status, School json){
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("history");
            try {
                database.createCollection("historyContent");
            } catch (MongoCommandException ignored) {
            }
            String date = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
            HistoryContent historyContent = new HistoryContent(className, date, methodName, status, new Gson().toJson(json));
            MongoCollection<Document> collection = database.getCollection("historyContent");
            collection.insertOne(Document.parse(new Gson().toJson(historyContent)));
        }
    }
}
