import React, { useState } from 'react';
import { AgGridReact } from 'ag-grid-react';
import { Button, Modal, Input, Select } from 'antd';
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-quartz.css';

const { Option } = Select;

const DealGrid = ({ companies = [] }) => {
  const [rowData, setRowData] = useState([
    { Company: "Tesla", name: "deal1", price: 64950, deal: true, phone: '123-456-7890', email: 'deal1@tesla.com', memo: 'Initial deal', createdAt: '2023-01-01 12:00', updatedAt: '2023-01-01 12:00' },
    { Company: "Ford", name: "deal2", price: 33850, deal: false, phone: '234-567-8901', email: 'deal2@ford.com', memo: 'Second deal', createdAt: '2023-01-02 12:00', updatedAt: '2023-01-02 12:00' },
    { Company: "Toyota", name: "deal3", price: 29600, deal: false, phone: '345-678-9012', email: 'deal3@toyota.com', memo: 'Third deal', createdAt: '2023-01-03 12:00', updatedAt: '2023-01-03 12:00' },
    { Company: "Mercedes", name: "deal5", price: 48890, deal: true, phone: '456-789-0123', email: 'deal5@mercedes.com', memo: 'Fifth deal', createdAt: '2023-01-04 12:00', updatedAt: '2023-01-04 12:00' },
    { Company: "Fiat", name: "deal7", price: 15774, deal: false, phone: '567-890-1234', email: 'deal7@fiat.com', memo: 'Seventh deal', createdAt: '2023-01-05 12:00', updatedAt: '2023-01-05 12:00' },
    { Company: "Nissan", name: "deal9", price: 20675, deal: false, phone: '678-901-2345', email: 'deal9@nissan.com', memo: 'Ninth deal', createdAt: '2023-01-06 12:00', updatedAt: '2023-01-06 12:00' },
  ]);

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isColumnModalOpen, setIsColumnModalOpen] = useState(false);
  const [newDeal, setNewDeal] = useState({
    Company: '',
    name: '',
    price: 0,
    deal: false,
    phone: '',
    email: '',
    memo: '',
    createdAt: '',
    updatedAt: ''
  });
  const [newColumn, setNewColumn] = useState({
    headerName: '',
    field: '',
    editable: true,
  });
  const [selectedRows, setSelectedRows] = useState([]);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const showColumnModal = () => {
    setIsColumnModalOpen(true);
  };

  const formatDateTime = (date) => {
    return new Date(date).toLocaleString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      hour12: false,
    }).replace(/\./g, '-').replace(' ', 'T');
  };

  const handleOk = () => {
    const now = new Date();
    const formattedDate = formatDateTime(now);

    const newEntry = {
      ...newDeal,
      createdAt: formattedDate,
      updatedAt: formattedDate,
    };

    setRowData([...rowData, newEntry]);
    setIsModalOpen(false);
    setNewDeal({
      Company: '',
      name: '',
      price: 0,
      deal: false,
      phone: '',
      email: '',
      memo: '',
      createdAt: '',
      updatedAt: ''
    });
  };

  const handleColumnOk = () => {
    setColDefs([...colDefs, newColumn]);
    setIsColumnModalOpen(false);
    setNewColumn({
      headerName: '',
      field: '',
      editable: true,
    });
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  const handleColumnCancel = () => {
    setIsColumnModalOpen(false);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewDeal(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleColumnInputChange = (e) => {
    const { name, value } = e.target;
    setNewColumn(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSelectChange = (value) => {
    setNewDeal(prevState => ({
      ...prevState,
      Company: value
    }));
  };

  const handleSelectionChanged = (event) => {
    setSelectedRows(event.api.getSelectedRows());
  };

  const handleDeleteSelected = () => {
    const updatedRowData = rowData.filter(row => !selectedRows.includes(row));
    setRowData(updatedRowData);
    setSelectedRows([]);
  };

  const onCellValueChanged = (params) => {
    const updatedRowData = rowData.map(row => {
      if (row === params.data) {
        return {
          ...row,
          updatedAt: formatDateTime(new Date()),
        };
      }
      return row;
    });
    setRowData(updatedRowData);
  };

  const [colDefs, setColDefs] = useState([
    { field: "Company", headerName: "회사", checkboxSelection: true, editable: true },
    { field: "name", headerName: "거래 이름", editable: true },
    { field: "price", headerName: "가격", editable: true },
    { field: "deal", headerName: "거래 상태", editable: true },
    { field: "phone", headerName: "전화번호", editable: true },
    { field: "email", headerName: "이메일", editable: true },
    { field: "memo", headerName: "메모", editable: true },
    { field: "createdAt", headerName: "생성일시", editable: false },
    { field: "updatedAt", headerName: "수정일시", editable: false },
  ]);

  const defaultColDef = {
    flex: 1,
    minWidth: 100,
    filter: true,
    resizable: true,
  };

  const buttonStyle = {
    backgroundColor: "navy",
    color: "white",
    margin: "10px",
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "16px"
  };

  return (
    <div>
      <Button type="primary" onClick={showModal} style={buttonStyle}>
        거래 추가
      </Button>
      <Button type="primary" onClick={showColumnModal} style={buttonStyle}>
        열 추가
      </Button>
      {selectedRows.length > 0 && (
        <Button type="danger" onClick={handleDeleteSelected} style={buttonStyle}>
          선택된 거래 삭제
        </Button>
      )}
      <Modal title="거래 추가" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
        <label>거래 이름을 입력해 주세요: </label>
        <Input type='text' name="name" value={newDeal.name} onChange={handleInputChange} /> <br /><br />

        <label>회사를 선택해 주세요: </label>
        <Select name="Company" value={newDeal.Company} onChange={handleSelectChange} style={{ width: '100%' }}>
          {companies.map(company => (
            <Option key={company.key} value={company.name}>{company.name}</Option>
          ))}
        </Select><br /><br />

        <label>담당자 전화번호: </label>
        <Input type='text' name="phone" value={newDeal.phone} onChange={handleInputChange} /> <br /><br />

        <label>담당자 이메일: </label>
        <Input type='text' name="email" value={newDeal.email} onChange={handleInputChange} /> <br /><br />

        <label>메모: </label>
        <Input type='text' name="memo" value={newDeal.memo} onChange={handleInputChange} /> <br /><br />
      </Modal>

      <Modal title="열 추가" open={isColumnModalOpen} onOk={handleColumnOk} onCancel={handleColumnCancel}>
        <label>열 이름을 입력해 주세요: </label>
        <Input type='text' name="headerName" value={newColumn.headerName} onChange={handleColumnInputChange} /> <br /><br />

      </Modal>

      <div
        className="ag-theme-quartz"
        style={{ height: 500, width: "1200px", overflowX: 'auto' }}
      >
        <AgGridReact
          rowData={rowData}
          columnDefs={colDefs}
          defaultColDef={defaultColDef}
          rowSelection="multiple"
          onSelectionChanged={handleSelectionChanged}
          onCellValueChanged={onCellValueChanged}
        />
      </div>
    </div>
  );
};

export default DealGrid;
