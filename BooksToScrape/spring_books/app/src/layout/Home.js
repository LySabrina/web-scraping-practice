import React, { useEffect, useState } from "react";
import axios from "axios"; //used to get information?
import "../css/Home.css";
import Aside from "./Aside";
import Search from "./Search";
import {Link, useParams} from 'react-router-dom';
import BookTable from "./BookTable";
export default function Home() {
  let loadBooks;
  const [book, setBooks] = useState([]);

  useEffect(() => {
    loadBooks();
  }, []);

  loadBooks= async () => {
        const result = await axios.get(`http://localhost:8080/book`);
        setBooks(result.data);
  };

  function getGenreBooks(e){
    setBooks(e.data);
  }
  return (
    <div>
      <Search/>
      <div className="container-home">
        <Aside />
        <BookTable book={book}/>

      </div>
    </div>
  );
}
