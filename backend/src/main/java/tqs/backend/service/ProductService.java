package tqs.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tqs.backend.model.Product;
import tqs.backend.repository.ProductRepository;

public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public Boolean existsProductByName(String name) {
		return productRepository.existsByName(name);
	};

	public Product getProductByName(String name) {
		return productRepository.findByName(name);
	};

	public Product getProductById(String id) {
		return productRepository.findById(id).orElse(null);
	};

	public List<Product> getAll() {
		return productRepository.findAll();
	};

	public Product addProduct(Product product) {
		if (productRepository.existsByName(product.getName())) {
			return null;
		}
		return productRepository.save(product);
	};

	public void deleteProduct(String id) {
		productRepository.deleteById(id);
	};
}
