import styles from './Catalogue.module.css';
import { useState, useEffect } from 'react';
import { GET_PRODUCTS } from '../../urls';
import axios from 'axios';
import { Button } from 'react-bootstrap';
import { useAuth } from '../../hooks/useAuth';

export default function Catalogue () {
	const {addToCart} = useAuth();
	const [products, setProducts] = useState([]);
	useEffect(() => {
		getProducts();
	}, []);

	const getProducts = async () => {
		try {
			const response = await axios.get(GET_PRODUCTS);
			console.log("getProducts -> response: ", response);
			if (response.status === 200) {
				setProducts(response.data);
			}
		} catch (error) {
			console.log("getProducts -> error: ", error);
		}
	}

	const handleAddToCart = (product) => {
		console.log("Adding this, ", product, " to cart")
		addToCart(product);
	}

		
	return (
		<div className={styles.catalogue_page}>
			<h1 className={styles.catalogue_page_title}>
					Catalogue
			</h1>	
			<div className={styles.catalogue_page_wrapper}>
				{
					products.map((product, index) => {
						return (
							<div className={styles.product_card} key={index}>
								<div className={styles.product_card_image}>
									<img src={product.image_url} alt={product.name} />
								</div>
								<div className={styles.product_card_price}>
									<div className={styles.product_card_name}>
										{product.name}
									</div>
									{product.price}€
								</div>
								<Button 
									variant="dark" 
									className={styles.button_add_cart}
									onClick={() => handleAddToCart(product)}
								>
									Add to cart
								</Button>
							</div>
						)
					})
				}
			</div>
		</div>
	)
}