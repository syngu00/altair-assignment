import React from 'react';
import { Breadcrumb, Layout, Menu, theme } from 'antd';
import LogsTable from "./components/LogsTable";

const { Header, Content, Footer } = Layout;

const App: React.FC = () => {
    return (
        <Layout className="layout">
            <Header style={{ display: 'flex', alignItems: 'center' }}>
                <div className="demo-logo" />
                <Menu
                    theme="dark"
                    mode="horizontal"
                    defaultSelectedKeys={['logs-menu-key']}
                    items={[{
                        key: "logs-menu-key",
                        label: `Requests log`,
                    }]}
                />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                    <Breadcrumb.Item>Requests log</Breadcrumb.Item>
                </Breadcrumb>
                <div className="site-layout-content">
                    <LogsTable></LogsTable>
                </div>
            </Content>
            <Footer style={{ textAlign: 'center' }}>Data Processor powered by AI</Footer>
        </Layout>
    );
};

export default App;
