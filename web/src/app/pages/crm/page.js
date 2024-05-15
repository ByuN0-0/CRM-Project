"use client";
import React, { useState } from 'react';
import CompanyModal from './CompanyModal';
import DealModal from './DealModal'
import {
  DesktopOutlined,
  ShopOutlined,
} from '@ant-design/icons';
import { Grid, Layout, Menu} from 'antd';
import DealGrid from '../../grid/DealGrid';

const { Sider } = Layout;

function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}

const App = () => {
  const [collapsed, setCollapsed] = useState(false);
  const [companies, setCompanies] = useState([
      {name: 'Apple', key: '1'},
      {name: 'Samsung', key: '2'},
  ]);

  const addCompany = (name) => {
      const newKey = String(companies.length + 1);
      const newCompany = { name, key: newKey };
      setCompanies([...companies, newCompany]);
  };

  const items = [
      getItem('Company', '1', <ShopOutlined />, companies.map(company => getItem(company.name, company.key))),
      getItem('Deals', '2', <DesktopOutlined />, [
          getItem('deal1', '3'),
          getItem('deal2', '4'),
      ]),
  ];

  return (
      <>
      <Layout style={{ minHeight: '100vh' }}>
          <Sider collapsible collapsed={collapsed} onCollapse={setCollapsed}>
              <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" items={items} />
              <CompanyModal addCompany={addCompany}/>
              <DealModal />
          </Sider>
          <DealGrid/>
      </Layout>
      </>
  );
};

export default App;
