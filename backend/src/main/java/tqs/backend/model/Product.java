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
	private String image_url;

	public Product(String name, String description, Double price, String image_url) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.image_url = image_url;
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

	public String getImage_url() {
		return this.image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	@Override
	public String toString() {
		return "{" +
				" name='" + getName() + "'" +
				", description='" + getDescription() + "'" +
				", price='" + getPrice() + "'" +
				", image_url='" + getImage_url() + "'" +
				"}";
	}

}