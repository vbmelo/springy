import { useEffect, useState } from "react";
import { Form, Button, Table, Dropdown, DropdownButton } from "react-bootstrap";
import { ArrowsCounterClockwise, MagnifyingGlass } from "phosphor-react";
import ModalPickup from "../../components/ModalPickup/ModalPickup";
import styles from "./PickupPoints.module.css";
import { GET_POINTS } from "../../urls";
import axios from "axios";

export default function PickupPoints() {
	const [pickupPoints, setPickupPoints] = useState([]);
	const [selectedFilter, setSelectedFilter] = useState("Name");

	const [showModal, setShowModal] = useState(false);

	useEffect(() => {
		getPoints();
	}, []);


	const handleSelectedFilter = (e) => {
		console.log("Selected filter will be: ", e);
		setSelectedFilter(e);
	};
	const handleEdit = (e) => {
		e.preventDefault();
		setShowModal(true);
	};

	const getPoints = async () => {
		try {
			const response = await axios.get(GET_POINTS);
			console.log("getPickupPoints -> response: ", response);
			if (response.status === 200) {
				setPickupPoints(response.data);
			}
		} catch (error) {
			console.log("getPickupPoints -> error: ", error);
		}
	}


	return (
		<div className={styles.pickupPoints_wrapper}>
			<div className={styles.pickupPoints_title}>Pickup Points <ArrowsCounterClockwise size={32} className={styles.pickupPoints_refresh_button} onClick={() => getPoints()} /></div>
			{/* Render a list of Available Pickup Points */}
			{/* Add, Remove and Edit Pickup Point from the Table */}
			{/* Filter the table to show only available PickupPoints */}
			{/* Search for a PickupPoint by name, address, city, state, zip, phone */}
			<div className={styles.pickupPoints_search_wrapper}>
				<Form.Control
					// onChange={(e) => setSearchQuery(e.target.value)}
					type="text"
					placeholder="Search"
					className={styles.pickupPoints_search}
					maxLength={100}
				/>
				<DropdownButton
					variant="dark"
					title={`Filter by ${selectedFilter}`}
					id="input-group-dropdown-1"
					className={styles.pickupPoints_search_dropdownBtn}
					onSelect={(e) => handleSelectedFilter(e)}
				>
					<Dropdown.Item eventKey="Name">Name (default)</Dropdown.Item>
					<Dropdown.Item eventKey="Address">Address</Dropdown.Item>
					<Dropdown.Item eventKey="City">City</Dropdown.Item>
					<Dropdown.Item eventKey="State">State</Dropdown.Item>
					<Dropdown.Item eventKey="Zip">Zip</Dropdown.Item>
					<Dropdown.Item eventKey="Phone">Phone</Dropdown.Item>
				</DropdownButton>
				<Button className={styles.pickupPoints_search_button} variant="secondary" id="button-addon2">
					<MagnifyingGlass size={24} /> Search
				</Button>
			</div>

			<Table
				className={styles.pickupPoints_table}
				striped
				variant="dark"
				responsive="xl"
			>
				<thead>
					<tr>
						<th>Name</th>
						<th>Address</th>
						<th>Orders</th>
					</tr>
				</thead>
				<tbody>
					{
						pickupPoints.map((user, key) => {
							return (<>
								<tr key={key}>
									<td>{user.name}</td>
									<td>{user.address}</td>
									<td>{user.address}</td>
									{/* <td className={styles.pickupPoints_table_td_actions}>
										<a onClick={(e) => handleEdit(e, user)} className="">
											Edit
										</a>
										<span disabled className="ms-2">
											or
										</span>
										<a onClick={(e) => handleDelete(e)} className="ms-2">
											Delete
										</a>
									</td> */}
								</tr>
							</>)
						})
					}
				</tbody>
				<tfoot>
					<tr>
						<td colSpan="2">Total Pickup Points: {pickupPoints.length}</td>
						<td colSpan="1" className={styles.pickupPoints_table_td_actions}>
							<a className="" onClick={(e) => handleEdit(e)}>
								+ New
							</a>
						</td>
					</tr>
				</tfoot>
			</Table>
			{showModal && (
				<ModalPickup
					show={showModal}
					onHide={() => setShowModal(false)}
				/>
			)}
		</div>
	);
}
