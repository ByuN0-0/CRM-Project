import React, {useEffect, useState} from 'react';
import {AgGridReact} from 'ag-grid-react';
import {Button, Modal, Input, Select} from 'antd';
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-quartz.css';
import Cookies from 'js-cookie';
import axios, {post} from "axios";
import { useRouter } from 'next/navigation';

const {Option} = Select;


const DealGrid = ({companies = []}) => {
    const router = useRouter();
    const [token, setToken] = useState('');
    const [workspaceId, setWorkspaceId] = useState('');
    const [colDefs, setColDefs] = useState([]);
    const [rowData, setRowData] = useState([]);
    const baseURL = 'http://127.0.0.1:8080';
    const getWorkspaceId = (token) => {
        // 요청을 보낼 URL
        const url = baseURL + '/api/workspaces'

        // 헤더 객체에 토큰을 추가
        const headers = {
            'Authorization': `Bearer ${token}`
        };
        //console.log('Headers:', headers, 'Token:', token)

        // Axios를 사용하여 요청을 보냄
        axios.get(url, {headers})
            .then(response => {
                // 요청이 성공한 경우 처리할 로직
                console.log('Data:', response.data);
                const workspaceId = response.data.workspaces[0].workspaceId;
                setWorkspaceId(workspaceId);
            })
            .catch(error => {
                // 요청이 실패한 경우 처리할 로직
                console.error('Error:', error);
            });
    };
    const getAttributes = async (workspaceId, token) => {
        try {
            // Post 요청을 보낼 URL
            const url = baseURL + `/api/workspaces/${workspaceId}/deals/attributes`;

            // 헤더 객체에 토큰을 추가
            const headers = {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            };

            // Get 요청을 보냄
            const response = await axios.get(url,{ headers });

            // 응답 데이터에서 attributes 추출하여 상태로 저장
            const receivedAttributes = response.data.attributeList.map(attribute => ({
                field: attribute.attributeId, // attributeId를 field로 설정
                headerName: attribute.attributeName, // attributeName을 headerName으로 설정
                checkboxSelection: false, // 필요에 따라 checkboxSelection과 editable을 추가적으로 설정할 수 있습니다.
                editable: true
            }));
            receivedAttributes.unshift({
                field: 'num',
                headerName: 'num',
                checkboxSelection: true,
                editable: true
            });
            receivedAttributes.unshift({
                field: 'dealId',
                headerName: 'dealId',
                checkboxSelection: false,
                editable: false,
                hide: true
            });
            receivedAttributes.push({
                field: 'createdDate',
                headerName: '생성 날짜',
                checkboxSelection: false,
                editable: false
            });
            receivedAttributes.push({
                field: 'updatedDate',
                headerName: '수정 날짜',
                checkboxSelection: false,
                editable: false
            });
            console.log('Data:', receivedAttributes);
            setColDefs(receivedAttributes);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const getDeal = async (workspaceId, token) => {
        try {
            const url = baseURL + `/api/workspaces/${workspaceId}/deals`;
            const headers = {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            };
            const response = await axios.get(url, {headers});
            const receivedDeals = response.data.dealList;
            console.log('Data:', receivedDeals);
            setRowData(receivedDeals);
        } catch (error) {
            console.error('Error:', error);
        }
    };
    const updateDeal = async (dealId, attributeId, value, workspaceId, token) => {
        try {
            const url = baseURL + `/api/workspaces/${workspaceId}/deals/${dealId}/attributes/${attributeId}`;

            const headers = {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            };

            const postData = {
                "value": value
                // 필요한 데이터 설정
            };
            console.log(postData)
            const response = await axios.put(url, postData, { headers });
            console.log(postData)
            console.log('Deal updated:', response.data);
        } catch (error) {
            console.error('Error updating deal:', error);
        }
    };

    useEffect(() => {
        // 페이지가 렌더링될 때 쿠키에서 토큰을 가져와 설정
        const storedToken = Cookies.get('jwtToken');
        if (storedToken) {
            setToken(storedToken);
        } else {
            // 토큰이 없으면 로그인 페이지로 이동하거나 다른 처리를 할 수 있습니다.
            router.push('/');
        }
    }, []);

    useEffect(() => {
        // token이 변경되었을 때만 getWorkspaceId 호출
        if (token) {
            getWorkspaceId(token);
        }
    }, [token]);

    useEffect(() => {
        // 토큰과 워크스페이스 아이디가 존재하면 API 호출
        if (token && workspaceId) {
            getAttributes(workspaceId, token).then(response => {
                console.log(response);
            });
        }
    }, [token, workspaceId]);

    useEffect(() => {
        if (token && workspaceId) {
            getDeal(workspaceId, token).then(response => {
                console.log(response);
            });
        }
    }, [token, workspaceId]);
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

    const handleOk = async () => {
        const now = new Date();
        //const formattedDate = formatDateTime(now);

        const newEntry = {
            //...newDeal,
            //createdAt: formattedDate,
            //updatedAt: formattedDate,
        };
        const headers = {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }

        try {
            // 새로운 거래 데이터를 서버로 보냄
            const response = await axios.post(baseURL+`/api/workspaces/${workspaceId}/deals`, newEntry, {headers});

            // 서버로부터 응답을 받으면 처리
            console.log('New deal added:', response.data);

            // 새로운 거래를 rowData에 추가
            setRowData([...rowData, response.data]);
            setIsModalOpen(false);

            await getDeal(workspaceId, token);
        } catch (error) {
            // 오류 처리
            console.error('Error adding new deal:', error);
        }
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
        const {name, value} = e.target;
        setNewDeal(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleColumnInputChange = (e) => {
        const {name, value} = e.target;
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

    const handleDeleteSelected = async () => {
        const updatedRowData = rowData.filter(row => !selectedRows.includes(row));
        const selectedDealIds = selectedRows.map(row => row.dealId);
        console.log('Selected deal IDs:', selectedDealIds);
        const url = baseURL + `/api/workspaces/${workspaceId}/deals`;
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
        for (const dealId of selectedDealIds) {
            try {
                await axios.delete(`${url}/${dealId}`, {headers});
                await getDeal(workspaceId, token);
            } catch (error) {
                console.error('Error deleting deal:', error);
            }
        }
        setRowData(updatedRowData);
        setSelectedRows([]);
    }

    const onCellValueChanged = async (params) => { //TODO
        const attributeId = params.colDef.field;

        await updateDeal(params.data.dealId, attributeId, params.newValue, workspaceId, token);
        const updatedDeal = {
            ...params.data, // 기존 데이터 복사
            [attributeId]: params.newValue // 새로운 값 업데이트
        };
        // 로컬 상태 업데이트
        const updatedRowData = rowData.map(row => {
            if (row.dealId === params.data.dealId) { // dealId로 매칭
                return updatedDeal;
            }
            return row;
        });
        setRowData(updatedRowData);
        await getDeal(workspaceId, token);
    };



    const defaultColDef = { // 기본 컬럼 정의
        flex: 1,
        minWidth: 100,
        filter: true,
        resizable: true,
    };

    const buttonStyle = {   // 버튼 스타일
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
                <Input type='text' name="name" value={newDeal.name} onChange={handleInputChange}/> <br/><br/>

                <label>회사를 선택해 주세요: </label>
                <Select name="Company" value={newDeal.Company} onChange={handleSelectChange} style={{width: '100%'}}>
                    {companies.map(company => (
                        <Option key={company.key} value={company.name}>{company.name}</Option>
                    ))}
                </Select><br/><br/>

                <label>담당자 전화번호: </label>
                <Input type='text' name="phone" value={newDeal.phone} onChange={handleInputChange}/> <br/><br/>

                <label>담당자 이메일: </label>
                <Input type='text' name="email" value={newDeal.email} onChange={handleInputChange}/> <br/><br/>

                <label>메모: </label>
                <Input type='text' name="memo" value={newDeal.memo} onChange={handleInputChange}/> <br/><br/>
            </Modal>

            <Modal title="열 추가" open={isColumnModalOpen} onOk={handleColumnOk} onCancel={handleColumnCancel}>
                <label>열 이름을 입력해 주세요: </label>
                <Input type='text' name="headerName" value={newColumn.headerName} onChange={handleColumnInputChange}/>
                <br/><br/>

            </Modal>

            <div
                className="ag-theme-quartz"
                style={{height: 500, width: "1200px", overflowX: 'auto'}}
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
