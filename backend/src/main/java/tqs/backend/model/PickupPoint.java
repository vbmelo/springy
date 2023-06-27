package tqs.backend.model;

import java.util.HashMap;

public class PickupPoint {
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
