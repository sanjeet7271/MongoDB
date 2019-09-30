package com.MongoBD;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;

public class mongoConnect {

	public static void main(String[] args) throws UnknownHostException {
		try {
			/*
			 * MongoClientURI uri = new MongoClientURI(
			 * "mongodb://admin:admin@shop-api-ndpvc.mongodb.net/test?retryWrites=true&w=majority"
			 * );
			 * 
			 * MongoClient mongoClient = new MongoClient(uri); DB db =
			 * mongoClient.getDB("test"); //MongoDatabase database =
			 * mongoClient.getDatabase("test");
			 * 
			 */ /**** Get collection / table from 'testdb' ****/

			MongoClientURI uri = new MongoClientURI(
					"mongodb://<username>:<Password>@shop-api-shard-00-00-ndpvc.mongodb.net:27017,shop-api-shard-00-01-ndpvc.mongodb.net:27017,shop-api-shard-00-02-ndpvc.mongodb.net:27017/test?ssl=true&replicaSet=Shop-API-shard-0&authSource=admin&retryWrites=true&w=majority");

			MongoClient mongoClient = new MongoClient(uri);
			// MongoDatabase database = mongoClient.getDatabase("test");

			DB db = mongoClient.getDB("test");
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("user");
			/**** Insert ****/
			// create a document to store key and value
			BasicDBObject document = new BasicDBObject();
			document.put("name", "Sachin");
			document.put("age", 35);
			document.put("createdDate", new Date());
			table.insert(document);

			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "Sachin");
			DBCursor cursor = table.find(searchQuery);

			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}

			/**** Update ****/
			// search document where name="mkyong" and update it with new values
			BasicDBObject query = new BasicDBObject();
			query.put("name", "Sachin");

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("name", "Sachin-updated");

			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);

			table.update(query, updateObj);

			/**** Find and display ****/
			BasicDBObject searchQuery2 = new BasicDBObject().append("name", "Sachin-updated");

			DBCursor cursor2 = table.find(searchQuery2);

			while (cursor2.hasNext()) {
				System.out.println(cursor2.next());
			}
			System.out.println("Done");

		} catch (MongoException e) {
			e.printStackTrace();
		}

	}

}
