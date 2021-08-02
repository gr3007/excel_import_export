package com.example.crudwithvaadin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ClientReference {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "client_reference")
    private String client_reference;

    @Column(name = "status")
    private String status;

    public ClientReference(){}

    public ClientReference(int id, String client_reference, String status){
        this.id = id;
        this.client_reference = client_reference;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_reference() {
        return client_reference;
    }

    public void setClient_reference(String client_reference) {
        this.client_reference = client_reference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
