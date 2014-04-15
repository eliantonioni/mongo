package com.luxoft.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeliseev on 08.04.2014
 */
@Document(collection = Contact.COLLECTION_NAME)
public class Contact implements Serializable {

    public static final String COLLECTION_NAME = "contacts";

    @Id
    private String id;

    private String name;
    private Long number;
    private String email;

    private List<ObjectId> categoryIds;

    public Contact() {
    }

    public Contact(String name, Long number, String email, List<ObjectId> categoryIds) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.categoryIds = categoryIds;
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

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ObjectId> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<ObjectId> categoryIds) {
        this.categoryIds = categoryIds;
    }
}

