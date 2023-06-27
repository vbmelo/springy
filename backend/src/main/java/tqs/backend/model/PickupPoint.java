package tqs.backend.model;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "points")
public class PickupPoint {

	@Id
	private String Id;
	private String name;
	private String address;
	private HashMap<Order, Integer> orders;

	public PickupPoint(String name, String address, HashMap<Order, Integer> orders) {
		this.name = name;
		this.address = address;
		this.orders = orders;
	}

	public PickupPoint() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public HashMap<Order, Integer> getOrders() {
		return this.orders;
	}

	public void setOrders(HashMap<Order, Integer> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "{" +
				" name='" + getName() + "'" +
				", address='" + getAddress() + "'" +
				", orders='" + getOrders() + "'" +
				"}";
	}

}
