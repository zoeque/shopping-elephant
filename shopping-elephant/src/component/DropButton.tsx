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
    identifier,
    itemName,
    executionDate,
    sendDropRequest,
    setMessage,
}: DropButtonProps) {
    function clickButton() {
        try {
            // send the drop request to the controller
            sendDropRequest(identifier, itemName, executionDate);
            // set the message to indicate success
            setMessage(`${itemName}を削除しました。`); 
        } catch (e) {
            // handle error and set the message
            setMessage(`予期せぬエラーが発生しました。エラー：${e}`); 
        }
    }

    return (
        <div className="dropbutton">
            <button onClick={clickButton}>削除</button>
        </div>
    );
}

export default DropButton;