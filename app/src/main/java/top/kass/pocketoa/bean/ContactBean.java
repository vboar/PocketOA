package top.kass.pocketoa.bean;

import java.io.Serializable;

public class ContactBean implements Serializable {

    private int id;

    private String name;

    private String customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
