import React, { useState } from "react";
import { Outlet } from "react-router-dom";
import Menubar from "../components/Menubar/Menubar";

export default function Layout() {
	return (
		<div>
			<Menubar />
			<div className="layout_content">
				<Outlet />
			</div>
		</div>
	);
}
