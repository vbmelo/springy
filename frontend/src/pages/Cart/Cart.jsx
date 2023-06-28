import { useEffect, useState } from "react";
import { Table, Button, Form } from "react-bootstrap";
import { useAuth } from "../../hooks/useAuth";
import axios from "axios";
import { CREATE_ORDER, GET_POINTS } from "../../urls";
import { toast } from "react-toastify";
import styles from "./Cart.module.css";

export default function Cart() {
  const { cart, clearCart, auth } = useAuth();
  const [pickupPoints, setPickupPoints] = useState([]);
  const [selectedPickupPoint, setSelectedPickupPoint] = useState("");
  const [selectedDate, setSelectedDate] = useState("");

  useEffect(() => {
    fetchPickupPoints();
  }, []);

  const fetchPickupPoints = async () => {
    try {
      const response = await axios.get(GET_POINTS);
      setPickupPoints(response.data);
    } catch (error) {
      console.log("fetchPickupPoints -> error", error);
    }
  };

  const handleCreateOrder = async () => {
    const today = new Date().toISOString().split("T")[0];

    const orderData = {
      dateDelivery: selectedDate || today,
      products: cart,
      costumer: auth.username,
      pickupPoint: selectedPickupPoint,
    };

    try {
      const response = await axios.post(CREATE_ORDER, orderData);
      console.log("handleCreateOrder -> response", response);
      if (response.status === 200) {
        clearCart();
        toast.success("Order created successfully!");
        console.log("Success!", response.data);
      }
    } catch (error) {
      console.log("handleCreateOrder -> error", error);
      toast.error("Error creating order!");
    }
  };

  return (
    <div>
      <h1 className={styles.cart_title}>Cart</h1>
      {cart?.length > 0 ? (
        <div className={styles.cart_table_container}>
          <Table striped bordered hover variant="dark">
            <thead>
              <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Image</th>
              </tr>
            </thead>
            <tbody>
              {cart.map((product, index) => (
                <tr key={index}>
                  <td>{product.name}</td>
                  <td>{product.description}</td>
                  <td>{product.price}</td>
                  <td>
                    <img
                      src={product.image_url}
                      alt={product.name}
                      height="80"
                    />
                  </td>
                </tr>
              ))}
            </tbody>
            <tfoot>
              <tr>
                <td colSpan="4">
                  Total:{" "}
                  {cart.reduce((acc, curr) => acc + curr.price, 0)}€
                </td>
              </tr>
              <tr>
                <td colSpan="4">
                  <Form.Group>
                    <Form.Label>Select Pickup Point:</Form.Label>
                    <Form.Control
                      as="select"
                      value={selectedPickupPoint}
                      onChange={(e) => setSelectedPickupPoint(e.target.value)}
                    >
                      <option value="">-- Select Pickup Point --</option>
                      {pickupPoints.map((point) => (
                        <option key={point.id} value={point.name}>
                          {point.name}
                        </option>
                      ))}
                    </Form.Control>
                  </Form.Group>
                </td>
              </tr>
              <tr>
                <td colSpan="4">
                  <Form.Group>
                    <Form.Label>Select Delivery Date:</Form.Label>
                    <Form.Control
                      type="date"
                      value={selectedDate}
                      onChange={(e) => setSelectedDate(e.target.value)}
                      min={new Date().toISOString().split("T")[0]}
                    />
                  </Form.Group>
                </td>
              </tr>
              <tr>
                <td colSpan="4">
                  <Button variant="dark" onClick={handleCreateOrder}>
                    Create Order
                  </Button>
                </td>
              </tr>
            </tfoot>
          </Table>
        </div>
      ) : (
        <div className={styles.empty_cart}>
          <h2>Cart is Empty!</h2>
          <h3>Please add some products to your cart.</h3>
        </div>
      )}
    </div>
  );
}
