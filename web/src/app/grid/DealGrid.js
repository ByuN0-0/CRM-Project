import React, { useState } from "react";
import { createRoot } from "react-dom/client";
import { AgGridReact } from "ag-grid-react"; // React Grid Logic
import "ag-grid-community/styles/ag-grid.css"; // Core CSS
import "ag-grid-community/styles/ag-theme-quartz.css"; // Theme

// Create new GridExample component
const DealGrid = () => {
  // Row Data: The data to be displayed.
  const [rowData, setRowData] = useState([
    { Company: "Tesla", name: "deal1", price: 64950, deal: true },
    { Company: "Ford", name: "deal2", price: 33850, deal: false },
    { Company: "Toyota", name: "deal3", price: 29600, deal: false },
    { Company: "Mercedes", name: "deal5", price: 48890, deal: true },
    { Company: "Fiat", name: "deal7", price: 15774, deal: false },
    { Company: "Nissan", name: "deal9", price: 20675, deal: false },
  ]);

  // Column Definitions: Defines & controls grid columns.
  const [colDefs, setColDefs] = useState([
    { field: "Company" },
    { field: "name" },
    { field: "price" },
    { field: "deal" },
  ]);

  const defaultColDef = {
    flex: 1,
  };

  // Container: Defines the grid's theme & dimensions.
  return (
    <div
      className={
        "ag-theme-quartz"
      }
      style={{width: "100%", height: "500"}}
    >
      <AgGridReact
        rowData={rowData}
        columnDefs={colDefs}
        defaultColDef={defaultColDef}
      />
    </div>
  );
};
export default DealGrid;