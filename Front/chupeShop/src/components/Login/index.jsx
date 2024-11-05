import Header from "../Header";
import Footer from "../Footer";
import axios from "axios";
import { GoogleOAuthProvider } from "@react-oauth/google";
import GoogleLoginComponent from "../GoogleLogin";
import { useState } from "react";

const LoginPage = () => {

    const [erro, setErro] = useState("");

    return (
        <>
            <Header />
            <div className="loginPage bg-gray-100 dark:bg-gray-900 min-h-screen flex flex-col justify-center items-center">
                <h1 className="text-3xl font-bold dark:text-white text-center mb-10">Faça seu login</h1>

                <div className="containerLogin bg-white dark:bg-gray-800 p-10 rounded-lg shadow-lg w-full max-w-md">
                    <div className="inputsContainerLogin flex flex-col gap-6">
                        <input 
                            type="email" 
                            placeholder="E-mail" 
                            className="inputLogin h-12 p-4 rounded border border-gray-300 dark:border-gray-700 dark:bg-gray-700 dark:text-white placeholder-gray-500 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary-500"
                        />
                        <input 
                            type="password" 
                            placeholder="Senha" 
                            className="inputLogin h-12 p-4 rounded border border-gray-300 dark:border-gray-700 dark:bg-gray-700 dark:text-white placeholder-gray-500 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary-500"
                        />
                    </div>
                    <div className="buttonsContainerLogin flex flex-col gap-4 mt-6">
                        <button className="bg-primary-500 text-white py-3 px-4 rounded hover:bg-primary-600 transition-colors duration-300">Entrar</button>
                        <button className="bg-secondary-500 text-white py-3 px-4 rounded hover:bg-secondary-600 transition-colors duration-300">Cadastrar</button>
                        <GoogleLoginComponent setErro={setErro} />

                    </div>
                    <p className="erro text-red-500 text-center mt-4">a</p>
                </div>
            </div>
            <Footer />
        </>
    );
}

export default LoginPage;