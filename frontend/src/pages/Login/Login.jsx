import React, { useEffect, useState } from "react";
import { Form, Button } from "react-bootstrap";
import styles from "./Login.module.css";
import { useAuth } from "../../hooks/useAuth";
import logoPng from "../../assets/AmanacuShipments.png";

export default function Login() {
	const { auth, submitLogin } = useAuth();
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

	const handleSubmitLogin = (e) => {
		e.preventDefault();
		console.log("handleSubmitLogin", username, password);
		submitLogin(e, { username, password });
	};

	return (
		<div className={styles.login_page}>
			<div className={styles.login_logo_wrapper}>
				{logoPng ? <img src={logoPng} alt={logoPng}/> : "failed to load this image"}
			</div>
			<Form
				className={styles.login_wrapper}
				onSubmit={(e) => handleSubmitLogin(e)}
			>
				<div className={styles.login_upper_section}>Admin Login</div>
				<Form.Group
					className={styles.login_form_field}
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
					className={styles.login_form_field}
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
				<Form.Group className={styles.login_form_field_bottom_section}>
					<Button
						className={styles.login_form_login_button}
						variant="secondary"
						type="submit"
					>
						Login
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
