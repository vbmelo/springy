/* eslint-disable react/prop-types */
import { useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import { CREATE_POINT } from "../../urls";
import axios from "axios";

export default function ModalPickup(props) {
	const [name, setName] = useState("");
	const [address, setAddress] = useState("");
	
	const handleSubmit = async (e) => {
		e.preventDefault();
		let data = JSON.stringify({
			"name": name,
			"address": address
		});
		
		let config = {
			method: 'post',
			maxBodyLength: Infinity,
			url: CREATE_POINT,
			headers: { 
				'Content-Type': 'application/json'
			},
			data : data
		};
		try {
			const response = await axios.request(config);
			if (response.status === 200) {
				console.log(response.data);
			}
		} catch (error) {
			console.log("Error creating order:", error);
		}
		props.onHide();
	};

	return (
		<Modal
			show={props.show}
			onHide={props.onHide}
			size="lg"
			aria-labelledby="contained-modal-title-vcenter"
			centered
		>
			<Modal.Header closeButton>
				<Modal.Title id="contained-modal-title-vcenter">
					Create an order
				</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				<Form>
					<Form.Group className="mb-3" controlId="formGridEmail">
						<Form.Label>Name</Form.Label>
						<Form.Control
							type="text"
							placeholder="Enter name"
							value={name}
							onChange={(e) => setName(e.target.value)}
						/>
					</Form.Group>

					<Form.Group className="mb-3" controlId="formGridPassword">
						<Form.Label>Address</Form.Label>
						<Form.Control
							type="text"
							placeholder="Enter address"
							value={address}
							onChange={(e) => setAddress(e.target.value)}
						/>
					</Form.Group>

					<Button variant="primary" type="submit" onClick={(e)=>handleSubmit(e)}>
						Create
					</Button>
				</Form>
			</Modal.Body>
		</Modal>
	);
}
