package tqs.backend.model;

import java.sql.Date;
import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {

	@Id
	private String Id;
	private Date dateCreated;
	private Date dateDelivery;
	@DBRef
	private HashMap<Product, Integer> products;
	private String status;
	@DBRef
	private User costumer;
	@DBRef
	private PickupPoint pickupPoint;
	private String recipient;

	public Order(Date dateCreated, Date dateDelivery, HashMap<Product, Integer> products, String status, User costumer,
			PickupPoint pickupPoint, String recipient) {
		this.dateCreated = dateCreated;
		this.dateDelivery = dateDelivery;
		this.products = products;
		this.status = status;
		this.costumer = costumer;
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

	public PickupPoint getPickupPoint() {
		return this.pickupPoint;
	}

	public void setPickupPoint(PickupPoint pickupPoint) {
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
				", pickupPoint='" + getPickupPoint() + "'" +
				", recipient='" + getRecipient() + "'" +
				"}";
	}

}
