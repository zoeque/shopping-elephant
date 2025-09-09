import axios from "axios";

export const sendDropRequest = async (identifier: Int16Array, itemName: string, executionDate: string) => {

    try {
         await axios.post('http://localhost:8080/drop', {
            identifier: identifier,
            itemName: itemName,
            executionDate: executionDate
        });
    } catch (error) {
        console.warn(error);
    }
}

