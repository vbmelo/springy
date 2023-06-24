package tqs.backend.model;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class Cart {

	@Id
	private String id;

	@DBRef
	private HashMap<Product, Integer> itens;

	public Cart() {
	}

	public Cart(String id, HashMap<Product, Integer> itens) {
		this.id = id;
		this.itens = itens;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<Product, Integer> getItens() {
		return this.itens;
	}

	public void setItens(HashMap<Product, Integer> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "{" +
				" id='" + getId() + "'" +
				", itens='" + getItens() + "'" +
				"}";
	}

}
