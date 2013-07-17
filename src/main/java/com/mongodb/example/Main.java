package com.mongodb.example;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        try {
            // connect to mongoDB, ip and port number
            Mongo mongo = new Mongo("127.0.0.1", 27017);

            // get database from MongoDB,
            // if database doesn't exists, mongoDB will create it automatically
            DB db = mongo.getDB("yourdb");

            //Getting A List Of Collections
            Set<String> collections = db.getCollectionNames();

            for (String s : collections) {
                System.out.println(s);
            }

            // Get collection from MongoDB, database named "yourDB"
            // if collection doesn't exists, mongoDB will create it automatically
            DBCollection collection = db.getCollection("yourCollection");

            // create a document to store key and value
            DBObject document = new BasicDBObject();
            document.put("id", 1001);
            document.put("msg", "hello world mongoDB in Java");

            // save it into collection named "yourCollection"
            collection.insert(document);

            // search query
            DBObject searchQuery = new BasicDBObject();
            searchQuery.put("id", 1001);

            // query it
            DBCursor cursor = collection.find(searchQuery);

            // loop over the cursor and display the retrieved result
            while (cursor.hasNext()) {
                System.out.println("Our collection after putting document here: " + cursor.next());
            }

            //Counting Documents in Collection
            System.out.println("Elements in collection " + collection.getCount());

            //update document (just replase exist - it is normal practise)
            DBObject updatedDocument = new BasicDBObject();
            updatedDocument.put("id", 1001);
            updatedDocument.put("msg", "hello world mongoDB in Java updated");
            collection.update(new BasicDBObject().append("id", 1001), updatedDocument);

            // query it
            DBCursor cursorAfterUpdate = collection.find(searchQuery);

            // loop over the cursor and display the retrieved result
            while (cursorAfterUpdate.hasNext()) {
                System.out.println("Our collection after update: " + cursorAfterUpdate.next());
            }

            //Counting Documents in Collection
            System.out.println("Elements in collection " + collection.getCount());

            //Map to object
            Message message = new Message();
            message.setId((Integer) document.get("id"));
            message.setMessage((String) document.get("msg"));

            System.out.println("Id putted in object: " + message.getId());
            System.out.println("Message putted in object: " + message.getMessage());

            //Remove document from collection
            DBObject doc = collection.findOne(); //get first document
            collection.remove(doc);

            // query it
            DBCursor cursorAfterDelete = collection.find(searchQuery);

            // loop over the cursor and display the retrieved result
            while (cursorAfterDelete.hasNext()) {
                System.out.println("Our collection after delete: " + cursorAfterDelete.next());
            }

            //Counting Documents in Collection
            System.out.println("Elements in collection " + collection.getCount());

            //Close connection to db
            mongo.close();

            System.out.println("Done");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }

    }

}
