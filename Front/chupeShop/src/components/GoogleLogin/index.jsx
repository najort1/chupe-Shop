import { GoogleLogin } from "@react-oauth/google";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";

const GoogleLoginComponent = ({setErro,setUsuarioLogado}) => {

    const navigate = useNavigate();

    const handleSuccess = (response) => {
        console.log("Login bem-sucedido:", response);
        const token = response.credential;
        localStorage.setItem("token", token);
        const user = jwtDecode(token);
        localStorage.setItem("user", JSON.stringify(user));
        setUsuarioLogado(true);
        navigate("/");
        
    };

    const handleError = () => {
        setErro("Erro ao fazer login com Google");
    };

    return (
        <GoogleLogin
            clientId="741427433448-5i44v3r03e9rucd9kvkrkm6gcnm9o8u6.apps.googleusercontent.com"
            onSuccess={handleSuccess}
            onError={handleError}
            render={(renderProps) => (
                <button
                    onClick={renderProps.onClick}
                    disabled={renderProps.disabled}
                    className="bg-secondary-500 text-white py-3 px-4 rounded hover:bg-secondary-600 transition-colors duration-300"
                >
                    Entrar com Google
                </button>
            )}
        />
    );
};

export default GoogleLoginComponent;