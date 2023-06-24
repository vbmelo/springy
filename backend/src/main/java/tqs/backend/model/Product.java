package tqs.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

	@Id
	private String id;
	private String name;
	private String description;
	private Double price;
	private String category;
	private String image_url;
	private Integer stock;

	public Product(String name, String description, Double price, String category, String image_url, Integer stock) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.image_url = image_url;
		this.stock = stock;
	}

	public Product() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage_url() {
		return this.image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "{" +
				" name='" + getName() + "'" +
				", description='" + getDescription() + "'" +
				", price='" + getPrice() + "'" +
				", category='" + getCategory() + "'" +
				", image_url='" + getImage_url() + "'" +
				", stock='" + getStock() + "'" +
				"}";
	}

}