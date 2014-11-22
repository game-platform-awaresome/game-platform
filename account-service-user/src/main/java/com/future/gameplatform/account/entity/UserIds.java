package com.future.gameplatform.account.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "ids", noClassnameStored = true)
public class UserIds {
    @Id
    String id;

    String name;

    int value;

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
