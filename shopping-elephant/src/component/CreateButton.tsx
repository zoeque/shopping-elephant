import { useState } from 'react';
import './components.css'
import '../controller/StoredItemController'

function CreateButton({ 
    itemName, itemType, expiredDate, sendPostRequest 
}:{
    itemName: string;
    itemType: string;
    expiredDate: string;
    sendPostRequest: (itemName: string, itemType: string, expiredDate: string) => void;
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
            sendPostRequest(itemName, itemType, expiredDate);
            setMessage(itemName + 'を登録しました。')
        } catch (e) {
            setMessage("予期せぬエラーが発生しました。エラー：" + e);
        }
    };
    return (
        <div className="card">
            <button onClick={clickButton}>
                登録
            </button>
            {message && <p className="read-the-docs">{message}</p>}
        </div>

    );
};

export default CreateButton;
