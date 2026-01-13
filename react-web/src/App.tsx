import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { HelmetProvider } from 'react-helmet-async';
import {HomePage} from "./pages/HomePage.tsx";

/**
 * Root application component with three context providers:
 *
 * 1. HelmetProvider - Manages document head (title, meta tags) for SEO across pages.
 *    Allows child components to safely update <head> without conflicts.
 *
 * 2. BrowserRouter - Provides client-side routing using browser's History API.
 *    Enables navigation between pages without full page reloads.
 *
 * 3. SessionProvider - Manages user authentication state (login/logout/session).
 *    Provides isLoggedIn, user info, and auth methods to all child components.
 */
function App() {
    return (
        <HelmetProvider>
            <BrowserRouter>
                    <div className="min-h-screen">
                        <main className="pt-16">
                            <Routes>
                                <Route path="/" element={<HomePage />} />
                            </Routes>
                        </main>
                    </div>
            </BrowserRouter>
        </HelmetProvider>
    );
}

export default App;
