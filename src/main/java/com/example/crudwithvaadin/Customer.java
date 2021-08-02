package com.example.crudwithvaadin;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {

	@Id
	private String id;
	private String msisdn_id;
	private String recipient_id;
	private String owner;
	private String account_number;
	private String x_product_id;
	private String x_product_list;
	private String value;
	private String display_name;
	private String product_recharge_type;
	private String client_reference;
	private String x_vas_transaction_id;
	private String status;
	private String reversed;
	private String created_at;
	private String updated_at;
	public Customer() {
	}

	public Customer(String id,
					String msisdn_id,
					String recipient_id,
					String owner,
					String account_number,
					String x_product_id,
					String x_product_list,
					String value,
					String display_name,
					String product_recharge_type,
					String client_reference,
					String x_vas_transaction_id,
					String status,
					String reversed,
					String created_at,
					String updated_at) {
		this.id = id;
		this.msisdn_id = msisdn_id;
		this.recipient_id = recipient_id;
		this.owner = owner;
		this.account_number = account_number;
		this.x_product_id = x_product_id;
		this.x_product_list = x_product_list;
		this.value = value;
		this.display_name = display_name;
		this.product_recharge_type = product_recharge_type;
		this.client_reference = client_reference;
		this.x_vas_transaction_id = x_vas_transaction_id;
		this.status = status;
		this.reversed = reversed;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsisdn_id() {
		return msisdn_id;
	}

	public void setMsisdn_id(String msisdn_id) {
		this.msisdn_id = msisdn_id;
	}

	public String getRecipient_id() {
		return recipient_id;
	}

	public void setRecipient_id(String recipient_id) {
		this.recipient_id = recipient_id;
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

	public String getX_product_id() {
		return x_product_id;
	}

	public void setX_product_id(String x_product_id) {
		this.x_product_id = x_product_id;
	}

	public String getX_product_list() {
		return x_product_list;
	}

	public void setX_product_list(String x_product_list) {
		this.x_product_list = x_product_list;
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

	public String getX_vas_transaction_id() {
		return x_vas_transaction_id;
	}

	public void setX_vas_transaction_id(String x_vas_transaction_id) {
		this.x_vas_transaction_id = x_vas_transaction_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReversed() {
		return reversed;
	}

	public void setReversed(String reversed) {
		this.reversed = reversed;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}


}