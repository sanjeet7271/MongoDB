package MongoConnect;

import java.util.Date;

import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoConnection {
	DBCollection table;
	//Create database if not available
	@SuppressWarnings("resource")
	public void ConnectToDataBase() {
		MongoClientURI uri = new MongoClientURI(
				"mongodb://<username>:<Password>@shop-api-shard-00-00-ndpvc.mongodb.net:27017,shop-api-shard-00-01-ndpvc.mongodb.net:27017,shop-api-shard-00-02-ndpvc.mongodb.net:27017/test?ssl=true&replicaSet=Shop-API-shard-0&authSource=admin&retryWrites=true&w=majority");

		MongoClient mongoClient = new MongoClient(uri);
		// MongoDatabase database = mongoClient.getDatabase("test");

		@SuppressWarnings("deprecation")
		DB db = mongoClient.getDB("test");
		// if collection doesn't exists, MongoDB will create it for you
		table = db.getCollection("Employee");
	}
	@Test(priority=0)
	public void createDatabse() {
		// create a document to store key and value
				ConnectToDataBase();
				BasicDBObject document = new BasicDBObject();
				document.put("name", "Nagarro");
				document.put("age", 15);
				document.put("company", "Nagarro India");
				document.put("createdDate", new Date());
				table.insert(document);
	}
	@Test(priority=1)
	public void updateTable() {
		ConnectToDataBase();
		BasicDBObject query = new BasicDBObject();
		query.put("name", "Nagarro");

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("name", "Nagarro-updated");

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		table.update(query, updateObj);
	}
	@Test(priority=2)
	public void writeQueryToSearch() {
		ConnectToDataBase();

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "Nagarro-updated");
		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}
}
