import { useState } from 'react';
import './components.css'
import '../controller/ShoppingTaskDropController'
import React from 'react';

interface DropButtonProps {
    identifier: Int16Array;
    itemName: string;
    executionDate: string;
    sendDropRequest: (
        identifier: Int16Array,
        itemName: string,
        executionDate: string
    ) => void;
    // function to set the message in the parent component
    setMessage: (message: string) => void; 
}

function DropButton({ 
    identifier, itemName, executionDate, sendPostRequest 
}:{
    identifier: Int16Array;
    itemName: string;
    executionDate: string;
    sendPostRequest: (identifier: Int16Array, itemName: string, executionDate: string) => void;
}) {

    const [message, setMessage] = useState('');
    const validateInputName = (itemName: string) => {
        if (itemName == '') {
            setMessage('名前を入力してください。');
            return false;
        }
        return true;
    };
    function clickButton() {
        try {
            if (!validateInputName(itemName)) {
                return;
            }
            {/** Send StoredItem as JSON after the validation */ }
            sendPostRequest(identifier, itemName, executionDate);
            setMessage(itemName + 'を削除しました。')
        } catch (e) {
            setMessage("予期せぬエラーが発生しました。エラー：" + e);
        }
    };
    return (
        <div className="card">
            <button onClick={clickButton}>
                削除
            </button>
            {message && <p className="read-the-docs">{message}</p>}
        </div>

    );
};

export default DropButton;
