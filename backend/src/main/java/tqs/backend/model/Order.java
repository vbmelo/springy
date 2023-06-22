package tqs.backend.model;

import java.sql.Date;
import java.util.HashMap;

public class Order {
	private Date dateCreated;
	private Date dateDelivery;
	private HashMap<Product, Integer> products;
	private String status;
	private User costumer;
	private Address address;
	private PicukpPoint pickupPoint;
	private String recipient;

	public Order(Date dateCreated, Date dateDelivery, HashMap<Product, Integer> products, String status, User costumer,
			Address address, PicukpPoint pickupPoint, String recipient) {
		this.dateCreated = dateCreated;
		this.dateDelivery = dateDelivery;
		this.products = products;
		this.status = status;
		this.costumer = costumer;
		this.address = address;
		this.pickupPoint = pickupPoint;
		this.recipient = recipient;
	}

	public Order() {
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateDelivery() {
		return this.dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public HashMap<Product, Integer> getProducts() {
		return this.products;
	}

	public void setProducts(HashMap<Product, Integer> products) {
		this.products = products;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getCostumer() {
		return this.costumer;
	}

	public void setCostumer(User costumer) {
		this.costumer = costumer;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public PicukpPoint getPickupPoint() {
		return this.pickupPoint;
	}

	public void setPickupPoint(PicukpPoint pickupPoint) {
		this.pickupPoint = pickupPoint;
	}

	public String getRecipient() {
		return this.recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@Override
	public String toString() {
		return "{" +
				" dateCreated='" + getDateCreated() + "'" +
				", dateDelivery='" + getDateDelivery() + "'" +
				", products='" + getProducts() + "'" +
				", status='" + getStatus() + "'" +
				", costumer='" + getCostumer() + "'" +
				", address='" + getAddress() + "'" +
				", pickupPoint='" + getPickupPoint() + "'" +
				", recipient='" + getRecipient() + "'" +
				"}";
	}

}
