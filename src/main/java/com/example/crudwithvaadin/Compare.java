package com.example.crudwithvaadin;

public class Compare {
    private String id;
    private String owner;
    private String account_number;
    private String x_vas_transaction_id;
    private String value;
    private String display_name;
    private String product_recharge_type;
    private String client_reference;
    private String statusExcel;
    private String statusQprism;
    private String created_at;
    private String result;

    public Compare(){}

    public Compare(String id, String owner, String account_number, String x_vas_transaction_id, String value,
                   String display_name, String product_recharge_type, String client_reference, String statusQprism, String created_at){
        this.id = id;
        this.owner = owner;
        this.account_number = account_number;
        this.x_vas_transaction_id = x_vas_transaction_id;
        this.value = value;
        this.display_name = display_name;
        this.product_recharge_type = product_recharge_type;
        this.client_reference = client_reference;
        this.statusQprism = statusQprism;
        this.created_at = created_at;
    }

    public Compare(String id, String owner, String account_number, String x_vas_transaction_id, String value,
                   String display_name, String product_recharge_type, String client_reference, String statusExcel,
                   String statusQprism, String created_at, String result){
        this.id = id;
        this.owner = owner;
        this.account_number = account_number;
        this.x_vas_transaction_id = x_vas_transaction_id;
        this.value = value;
        this.display_name = display_name;
        this.product_recharge_type = product_recharge_type;
        this.client_reference = client_reference;
        this.statusExcel = statusExcel;
        this.statusQprism = statusQprism;
        this.created_at = created_at;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getX_vas_transaction_id() {
        return x_vas_transaction_id;
    }

    public void setX_vas_transaction_id(String x_vas_transaction_id) {
        this.x_vas_transaction_id = x_vas_transaction_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getProduct_recharge_type() {
        return product_recharge_type;
    }

    public void setProduct_recharge_type(String product_recharge_type) {
        this.product_recharge_type = product_recharge_type;
    }

    public String getClient_reference() {
        return client_reference;
    }

    public void setClient_reference(String client_reference) {
        this.client_reference = client_reference;
    }

    public String getStatusExcel() {
        return statusExcel;
    }

    public void setStatusExcel(String statusExcel) {
        this.statusExcel = statusExcel;
    }

    public String getStatusQprism() {
        return statusQprism;
    }

    public void setStatusQprism(String statusQprism) {
        this.statusQprism = statusQprism;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
