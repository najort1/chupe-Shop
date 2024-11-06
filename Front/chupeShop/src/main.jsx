import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { NextUIProvider } from '@nextui-org/react'
import { GoogleOAuthProvider } from '@react-oauth/google'
import { PrimeReactProvider, PrimeReactContext } from 'primereact/api';
        

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <GoogleOAuthProvider clientId='741427433448-5i44v3r03e9rucd9kvkrkm6gcnm9o8u6.apps.googleusercontent.com'>
      <NextUIProvider>
        <PrimeReactProvider>
              <App />
        </PrimeReactProvider>
      </NextUIProvider>
    </GoogleOAuthProvider>

  </StrictMode>,
)
