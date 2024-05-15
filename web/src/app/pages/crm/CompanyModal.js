import React, { useState } from 'react';
import { Button, Modal } from 'antd';
const App = ({ addCompany }) => { // props로 addCompany 함수를 받음
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [comName, setComName] = useState('');

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = () => {
    addCompany(comName); // 새 회사 추가
    setIsModalOpen(false);
    setComName(''); // 입력 필드 초기화
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <Button type="primary" onClick={showModal}>
        Add Company
      </Button>
      <Modal title="Add company" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
        <input type='text' value={comName} placeholder="Add company" onChange={(e) => setComName(e.target.value) }/>
      </Modal>
    </>
  );
};

export default App;