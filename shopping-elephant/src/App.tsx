import { useState } from 'react'
import React from 'react';
import { FC } from 'react'
import DatePicker, { registerLocale } from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import './App.css'
import CreateButton from './component/CreateButton'
import { sendPostRequest } from './controller/ShoppingTaskController';
import { Link } from "react-router-dom";
import ja from "date-fns/locale/ja"

const App: FC = () => {
  const today = new Date();
  registerLocale("ja", ja);

  const [inputItemName, setItemName] = useState('');
  const [inputExecutionDate, setExecutionDate] = React.useState(today);

  const setTodayAsFormatted = () => {
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return year + "/" + month + "/" + day;
  }
  const [executionDateToSend, setExecutionDateToSend] = useState(setTodayAsFormatted);

  const handleItemName = (event: React.ChangeEvent<HTMLInputElement>) => {
    setItemName(event.target.value);
  };

  const handleSelectedDate = (date: Date) => {
    setExecutionDate(date || today);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    setExecutionDateToSend(year + "/" + month + "/" + day);
    return year + "/" + month + "/" + day;
  }

  return (
    <>
      <h1>Shopping Elephant</h1>
      <p>
        購入予定の物品を以下フォームから登録してください。
      </p>
      <div>
        <input type="itemName" placeholder="購入商品名" value={inputItemName} onChange={handleItemName} />
      </div>
      <div>
        <DatePicker
          dateFormat="yyyy/MM/dd"
          selected={inputExecutionDate}
          locale="ja"
          minDate={today}
          onChange={selectedDate => { handleSelectedDate(selectedDate || today) }}
          placeholderText="日付を選択"
        />
      </div>
      <div>
        <CreateButton
          itemName={inputItemName}
          executionDate={executionDateToSend}
          sendPostRequest={sendPostRequest} />
      </div>
      <div>
        <nav>
          <Link to="/shoppingTaskList">一覧表示</Link>
        </nav>
      </div>
      <p className="read-the-docs">
        Created by zoeque. See <a href="https://github.com/zoeque">GitHub</a> for a detail.
      </p>
    </>
  )
}

export default App
