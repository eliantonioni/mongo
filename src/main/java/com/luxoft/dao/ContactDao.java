package com.luxoft.dao;

import com.luxoft.model.Category;
import com.luxoft.model.Contact;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class ContactDao {

    @Autowired
    private MongoTemplate mongoOperations;
//    private MongoOperations mongoOperations;

    public void save(Contact contact) {
        mongoOperations.insert(contact);
    }

    public void bulkInsert(Collection<Contact> contacts) {
        mongoOperations.insertAll(contacts);
    }

    public long getCount() {
        return mongoOperations.count(null, Contact.class);
    }

    public void ensureIndex(final String name) {
        mongoOperations.indexOps(Contact.class).ensureIndex(
                new Index()
                        .on(name, Sort.Direction.DESC)
        );
    }
    

    public Contact get(Long id) {
        return mongoOperations.findOne(Query.query(Criteria.where("id").is(id)), Contact.class);
    }

    public Contact getByNumber(Long number) {
        return mongoOperations.findOne(Query.query(Criteria.where("number").is(number)), Contact.class);
    }

    public Collection<Contact> getByNumberComparison(boolean gt, Long number, int pageNumber, int pageSize) {

        Pageable pageable = new PageRequest(pageNumber, pageSize);

        DBCollection coll = mongoOperations.getCollection(Contact.COLLECTION_NAME);
        BasicDBObject query = new BasicDBObject(
                "number",
                new BasicDBObject("$gt", number)
        );
        coll.find(query);

        return mongoOperations.find(
                Query.query(Criteria.where("number").gt(number)).with(pageable), Contact.class
        );
    }

    public List<Contact> getContactsByCat(Category cat) {
        return mongoOperations.find(
                Query.query(Criteria.where("categoryIds").in(new ObjectId(cat.getId()))), Contact.class
        );
    }

    public List<Contact> getAll() {
        return mongoOperations.findAll(Contact.class);
    }

    public void remove(Long id) {
        mongoOperations.remove(Query.query(Criteria.where("id").is(id)), Contact.class);
    }
}
