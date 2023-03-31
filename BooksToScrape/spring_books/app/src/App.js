import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import './App.css';
import Navbar from "./layout/Navbar";
import Form from "./layout/Form";
import Home from "./layout/Home";
import Search from "./layout/Search";
import AddBook from "./layout/AddBook";
import {BrowserRouter as Router, Routes, Route, useParams} from "react-router-dom";
import ViewBook from "./layout/ViewBook";
import {useEffect, useState} from "react";
import axios from "axios";

function App() {
    const [book, setBooks] = useState([]);

    useEffect(() => {
        loadBooks();
    }, []);
    let {genre} = useParams();
    let loadBooks= async () => {
        console.log(genre);
        const result = await axios.get(`http://localhost:8080/book`);
        setBooks(result.data);
    };
  return (
    <div>
       <Router>
        <Navbar/>
    
        <Routes>
          <Route exact path ="/" element = {<Home/>}/>
          <Route exact path = "/addBook" element = {<AddBook/>}/>
            <Route exact path = "/viewBook/:id" element={<ViewBook/>}/>
            <Route exact path = "/catalogue/:genre" element={<Home data={genre}/>}/>
        </Routes>

       </Router>
       
    </div>
  );
}

export default App;
