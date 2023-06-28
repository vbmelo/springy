import React, { useEffect, useState } from "react";
import { Form, Button } from "react-bootstrap";
import styles from "./Register.module.css";
import { useAuth } from "../../hooks/useAuth";
import logoPng from "../../assets/AmanacuShipments.png";

export default function Register() {
	const { auth, submitRegister } = useAuth();
	const [username, setUsername] = useState("");
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");

	const handleSubmitRegister = (e) => {
		e.preventDefault();
		console.log("handleSubmitRegister", email, password);
		submitRegister(e, { username, email, password });
	};

	return (
		<div className={styles.register_page}>
			<div className={styles.register_logo_wrapper}>
				{logoPng ? <img src={logoPng} alt={logoPng}/> : "failed to load this image"}
			</div>
			<Form
				className={styles.register_wrapper}
				onSubmit={(e) => handleSubmitRegister(e)}
			>
				<div className={styles.register_upper_section}>Register</div>
				<Form.Group
					className={styles.register_form_field}
					controlId="formBasicUsername"
				>
					<Form.Label>Username</Form.Label>
					<Form.Control
						onChange={(e) => setUsername(e.target.value)}
						value={username}
						type="username"
						placeholder="Enter username"
					/>
				</Form.Group>

				<Form.Group
					className={styles.register_form_field}
					controlId="formBasicEmail"
				>
					<Form.Label>Email address</Form.Label>
					<Form.Control
						onChange={(e) => setEmail(e.target.value)}
						value={email}
						type="email"
						placeholder="Enter email"
					/>
					<Form.Text className="text-muted">
						We'll never share your email with anyone else.
					</Form.Text>
				</Form.Group>
				<Form.Group
					className={styles.register_form_field}
					controlId="formBasicPassword"
				>
					<Form.Label>Password</Form.Label>
					<Form.Control
						onChange={(e) => setPassword(e.target.value)}
						value={password}
						type="password"
						placeholder="Password"
					/>
				</Form.Group>
				<Form.Group className={styles.register_form_field_bottom_section}>
					<Button
						className={styles.register_form_register_button}
						variant="secondary"
						type="submit"
					>
						register
					</Button>
				</Form.Group>
				{auth.error && (
					<Form.Group >
						<Form.Text className="text-muted">
							{auth.error}
						</Form.Text>
					</Form.Group>
				)}
			</Form>
		</div>
	);
}
