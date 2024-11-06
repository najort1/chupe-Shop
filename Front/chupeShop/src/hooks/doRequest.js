import axios from "axios";

const doReqeust = async (url, method, data) => {
    try {
        const response = await axios({
        method,
        url,
        data,
        validateStatus: function (status) {
            return status <= 500;
        },
        });
    
        return response;
    } catch (error) {
        console.error(error);
        return error;
    }
    }

export default doReqeust;