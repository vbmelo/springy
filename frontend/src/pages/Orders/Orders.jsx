import { useState, useEffect } from "react";
import axios from "axios";
import { Table } from "react-bootstrap";
import { GET_ORDERS } from "../../urls";
import styles from "./Orders.module.css";
import { useAuth } from "../../hooks/useAuth";

export default function Orders() {
	const { auth } = useAuth();
	const [orders, setOrders] = useState([]);

	useEffect(() => {
		fetchOrders();
	}, []);

	const fetchOrders = async () => {
		try {
			const response = await axios.get(GET_ORDERS);
			let ordersArray = response.data.filter((order) => order.costumer === auth?.username)
			setOrders(ordersArray);
		} catch (error) {
			console.log("fetchOrders -> error", error);
		}
	};

	return (
		<div>
			<h2 className={styles.orders_title}>{auth?.username ? `${auth.username}'s`: null} orders</h2>
			{orders.length > 0 ? (
				<div className={styles.orders}>
					<Table striped bordered hover variant="dark">
						<thead>
							<tr>
								<th>Order ID</th>
								<th>Date</th>
								<th>Customer</th>
								<th>Pickup Point</th>
								<th>Products</th>
							</tr>
						</thead>
						<tbody>
							{orders.map((order) => (
								<tr key={order.id}>
									<td>{order.id}</td>
									<td>{order.dateCreated}</td>
									<td>{order.costumer}</td>
									<td>{order.pickupPoint}</td>
									<td>
										<ul>
											{order.products.map((product) => (
												<li key={product.id}>
													{product.name} - {product.price}€
												</li>
											))}
										</ul>
									</td>
								</tr>
							))}
						</tbody>
					</Table>
				</div>
			) : (
				<div className={styles.orders_empty}>
					<h3>No orders found.</h3>
				</div>
			)}
		</div>
	);
}
