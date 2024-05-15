import React, { useState } from 'react';
import { Button, Modal } from 'antd';

const App = ({ companies = [] }) => { // companies 배열에 기본값 [] 설정
  const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    setIsModalOpen(false);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };
  return (
    <>
      <Button type="primary" onClick={showModal}>
        Add Deal
      </Button>
      <Modal title="Add Deal" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
        <label>거래 이름을 입력해 주세요 : </label> <input type='text'/> <br/><br/>

        <label>회사를 선택해주세요 : </label> 
        <select>
          {companies?.map(company => ( // 옵셔널 체이닝 사용
            <option key={company.key} value={company.name}>{company.name}</option>
          ))}
        </select><br/><br/>

        <label>담당자 전화번호 : </label> <input type='phone'/> <br/><br/>
        <label>담당자 이메일: </label><input type='email'/> <br/><br/>
        <label>메모 : </label><input type='textarea'/> <br/><br/>
        <label>생성일시 : </label><input type='date'/><input type='time'/> <br/><br/>
        <label>업데이트일시 : </label><input type='date'/><input type='time'/> 
      </Modal>
    </>
  );
};
export default App;