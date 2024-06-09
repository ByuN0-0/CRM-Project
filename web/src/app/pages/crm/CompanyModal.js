import React, { useState } from 'react';
import { Button, Modal } from 'antd';

const App = ({ addCompany }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [comName, setComName] = useState('');

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = async () => { // 비동기 함수로 변경
    try {
      const response = await fetch('/api/company/add', {
        method: 'POST', // 데이터를 추가하기 위한 POST 메소드
        headers: {
          'Content-Type': 'application/json', // JSON 형태의 데이터를 전송하겠다고 서버에 알림
        },
        body: JSON.stringify({ name: comName }), // comName 상태를 JSON 문자열로 변환하여 전송
      });
      if (response.ok) {
        const data = await response.json(); // 서버로부터의 응답을 JSON 형태로 변환
        addCompany(comName); // 새 회사 추가 (로컬 UI 상태 업데이트)
      } else {
        // 서버에서 오류 응답을 보냈을 경우 처리
        alert('Error adding company');
      }
    } catch (error) {
      // 네트워크 오류 또는 요청 실패 처리
      console.error('Failed to add company:', error);
      alert('Failed to add company');
    }
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
