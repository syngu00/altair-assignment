import React, {useCallback, useEffect, useState} from 'react';
import {Button, Card, Popover, Table} from 'antd';
import axios from 'axios';
import moment, {Moment} from "moment";

/*
    TODO serverside pageing would be better option with this kind of data with some what advanced query
 */
const LogsTable = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);


    const fetchData = useCallback(async () => {
        setLoading(true);
        const result = await axios('/api/log');
        setData(result.data);
        setLoading(false);
    }, []);

    useEffect(() => {
        fetchData();
    }, [fetchData]);

    const columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'Request DateTime',
            dataIndex: 'requestDateTime',
            key: 'requestDateTime',
            render: (text: string) => {
                const reqDateTime: Moment = moment(text)
                return (<Popover content={text}>
                    <a> {reqDateTime.format("LL")} {reqDateTime.format("LTS")}</a>
                </Popover>)
            },

        },
        {
            title: 'Method',
            dataIndex: 'method',
            key: 'method',
        },
        {
            title: 'Requested path',
            dataIndex: 'uri',
            key: 'uri',
        },
        {
            title: 'Response status',
            dataIndex: 'status',
            key: 'status',
        },
        {
            title: 'Duration',
            dataIndex: 'duration',
            key: 'duration',
            render: (text: number) => `${text} ms`,
        },
        // other columns here...
        {
            title: 'Request Body',
            dataIndex: 'requestBody',
            key: 'requestBody',
            render: (text: string) => {
                if (!text || text.length < 20) {
                    return text;
                }

                return (
                    <Popover content={text} title="Request Body">
                        <a>View Content</a>
                    </Popover>
                );
            },
        },
        {
            title: 'Response Body',
            dataIndex: 'responseBody',
            key: 'responseBody',
            render: (text: string) => {
                if (!text || text.length < 20) {
                    return text;
                }

                return (
                    <Popover content={text} title="Response Body">
                        <a>View Content</a>
                    </Popover>
                );
            },
        },
    ];

    return (
        <div>
            <Card>
                <div style={{marginBottom: 16}}>
                    <Button type="primary" onClick={fetchData} loading={loading}>
                        Refresh
                    </Button>
                </div>
                <Table dataSource={data} columns={columns} loading={loading}/>
            </Card>
        </div>
    );
};

export default LogsTable;
