import "bootstrap/dist/css/bootstrap.min.css";
import { Routes, Route } from "react-router-dom";
import "./App.css";
import "react-toastify/dist/ReactToastify.css";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import Layout from "./layouts/Layout";
import HomePage from "./pages/HomePage/HomePage";
import PickupPoints from "./pages/PickupPoints/PickupPoints";
import { useAuth } from "./hooks/useAuth";
import ProtectedRoute from "./components/ProtectedRoute/ProtectedRoute";

function App() {
	const { auth } = useAuth();
	return (
			<Routes>
				{/* Rotas não Autenticadas */}
				<Route path="/login" element={<Login />} />
				<Route path="/register" element={<Register />} />
				{/* Rotas Já Autenticadas*/}
				<Route element={<ProtectedRoute redirectPath="/login" isAllowed={!!auth.isAuthenticated} />}>
					<Route path="/" element={<Layout />}>
						<Route index element={<HomePage />} />
						<Route path="home" element={<HomePage />} />
						<Route path="pickup-points" element={<PickupPoints />} />
						<Route path="orders" element={<HomePage />} />
						<Route path="cart" element={<HomePage />} />
					</Route>
        </Route>
				<Route path="*" element={<p>There is nothing here: 404!</p>} />
			</Routes>
	);
}

export default App;
