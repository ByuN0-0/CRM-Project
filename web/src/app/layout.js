import { Inter } from "next/font/google";
import "./globals.css";
import Header from "./component/basic/header";
import Footer from "./component/basic/footer";

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: "Crm-Project",
  description: "capstone-design-team",
};

export default function RootLayout({ children }) {

  return (
    <html lang="en">
      <body className={inter.className}>
        <Header/>
        {children}
        <Footer/>
        
        
        </body>
    </html>
  );
}
