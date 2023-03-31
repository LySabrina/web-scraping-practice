import axios from "axios"; //used to get information?
import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';
export default function Aside() {
  const [genres, setGenres] = useState([]);

  const loadGenre = async() =>{
    const result = await axios.get("http://localhost:8080/genres");
    setGenres(result.data);
  };

  useEffect(()=>{
    loadGenre();
  }, []);

  //Make an object that holds the style properties 
  const myStyle = {
        listStyleType : "none"
  };

  function passData(genre){
    const loadBookGenres = async() =>{
      const result = await axios.get(`http://localhost:8080/catalogue/${genre}`);
    };
  }
  return (
    <div>
        <ul>
            {
                genres.map((genre) =>(
                    <li style={myStyle}>
                      <Link to ={`/catalogue/${genre}`}> {genre}</Link>
                    </li>
                ))
            }
        </ul>
    </div>
  )
}
