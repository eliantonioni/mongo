package com.luxoft.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by aeliseev on 11.04.2014
 */
@Document(collection = Category.COLLECTION_NAME)
public class Category {

    public static final String COLLECTION_NAME = "category";

    @Id
    private String id;

    private String name;

    private List<ObjectId> ancestors;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ObjectId> getAncestors() {
        return ancestors;
    }

    public void setAncestors(List<ObjectId> ancestors) {
        this.ancestors = ancestors;
    }
}
