import axios from "axios";

export const sendPostRequest = async (itemName: string, executionDate: string) => {

    try {
         await axios.post('http://localhost:8080/create', {
            itemName: itemName,
            executionDate: executionDate
        });
    } catch (error) {
        console.warn(error);
    }
}

