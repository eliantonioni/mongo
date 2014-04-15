package com.luxoft.dao;

import com.luxoft.model.Category;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aeliseev on 15.04.2014
 */
@Repository
public class CategoryDao {

    @Autowired
    private MongoTemplate mongoOperations;

    public List<Category> getAllCats() {
        return mongoOperations.findAll(Category.class);
    }

    public void save(Category category) {
        mongoOperations.insert(category);
    }

    public Category getCategoryByName(String name) {
        return mongoOperations.findOne(Query.query(Criteria.where("name").is(name)), Category.class);
    }

    public void setAncestor(Category parent, Category child) {
        mongoOperations.updateFirst(
                Query.query(Criteria.where("_id").is(new ObjectId(child.getId()))),
                new Update().push("ancestors", new ObjectId(parent.getId())),
                Category.class
        );
    }

    public void updateCategoryName(Category updated, String newName) {
        mongoOperations.updateFirst(
                Query.query(Criteria.where("_id").is(new ObjectId(updated.getId()))),
                new Update().set("name", newName),
                Category.class
        );
    }

    public void changeHierarchy(Category newCat, Category child) {

        // TODO: atomicity broken!!!

        // set newCat ancestor as a child ancestor
        mongoOperations.updateFirst(
                Query.query(
                        Criteria.where("_id").is(new ObjectId(newCat.getId()))
                ),
                new Update().set("ancestors.0", child.getAncestors().get(0)),
                Category.class
        );

        // update child to point to a newCat as ancestor
        mongoOperations.updateFirst(
                Query.query(
                        Criteria.where("_id").is(new ObjectId(child.getId()))
                ),
                new Update().set("ancestors.0", new ObjectId(newCat.getId())),
                Category.class
        );
    }
}
