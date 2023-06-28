/* eslint-disable react-refresh/only-export-components */
/* eslint-disable react/prop-types */
/* eslint-disable no-undef */
import React, { useState, useEffect, useContext } from "react";

// create the useShipments hook with its own context
export const ShipmentsContext = React.createContext();

export const useShipments = () => {
	return useContext(ShipmentsContext);
};

export const ShipmentsProvider = ({ children }) => {
	const [shipments, setShipments] = useState([]);
	const [isLoading, setIsLoading] = useState(true);
	const [error, setError] = useState(null);

	// fetch the shipments from the API
	const fetchShipments = async () => {
		setIsLoading(true);

		try {
			const response = await fetch(
				"https://api.shipments.test-y-sbm.com/shipments",
				{
					headers: {
						"Content-Type": "application/json",
						Authorization: `Bearer ${process.env.REACT_APP_API_TOKEN}`,
					},
				}
			);
			const data = await response.json();

			setShipments(data);
			setIsLoading(false);
		} catch (error) {
			setError(error);
			setIsLoading(false);
		}
	};

	// call the fetchShipments function once on mount
	useEffect(() => {
		fetchShipments();
	}, []);

	// define the context value
	const context = {
		shipments,
		isLoading,
		error,
		fetchShipments,
	};

	// pass the value in the provider and return it
	return (
		<ShipmentsContext.Provider value={context}>
			{children}
		</ShipmentsContext.Provider>
	);
};
