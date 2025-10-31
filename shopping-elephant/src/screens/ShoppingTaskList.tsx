import { FC, useCallback, useEffect, useState } from "react";
import "../App.css"
import axios from "axios";
import { Link } from "react-router-dom";
import DropButton from '../component/DropButton'
import { sendDropRequest } from '../controller/ShoppingTaskDropController';

const isError = (error: unknown): error is Error => {
  return error instanceof Error;
};

interface ShoppingTask {
  identifier: Int16Array;
  itemName: string;
  executionDate: string;
}

export const ShoppingTaskList: FC = () => {

  const [item, setItem] = useState<ShoppingTask[]>([]);
  const [error, setError] = useState<Error | undefined>(undefined);
  const [message, setMessage] = useState<string>('');


  if (error) {
    return <div>{error.message}</div>;
  }

  const fetchShoppingTask = useCallback(async () => {
    try {
      axios.get("http://localhost:8080/find")
        .then((res) => {
          setItem(res.data);
        });
    } catch (e) {
      if (isError(e)) {
        setError(e);
      }
    }
  }, []);


  useEffect(() => {
    fetchShoppingTask();
  }, [fetchShoppingTask]);

  return (
    <div>
      <h2>一覧表示</h2>
      <Link to="/">戻る</Link>
      {message && <p>{message}</p>}
      <div onLoad={fetchShoppingTask}>
        <table className="table">
          <tr>
            <th>ID</th>
            <th>品名</th>
            <th>購入予定日</th>
            <th>操作</th>
          </tr>
          {item.map((shoppingTask) => (
            <tr>
              <td>{shoppingTask.identifier}</td>
              <td>{shoppingTask.itemName}</td>
              <td>{shoppingTask.executionDate}</td>
              <td>
                <DropButton
                  identifier={shoppingTask.identifier}
                  itemName={shoppingTask.itemName}
                  executionDate={shoppingTask.executionDate}
                  sendDropRequest={sendDropRequest}
                  setMessage={setMessage}
                />
              </td>
            </tr>
          ))}
        </table>
      </div>
    </div>
  );
};