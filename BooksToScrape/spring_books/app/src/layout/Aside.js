import axios from "axios"; //used to get information?
import React, { useEffect, useState } from "react";

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

  return (
    <div>
        <ul>
            {
                genres.map((genre) =>(
                    <li style={myStyle}>{genre}</li>
                ))
                
            }
        </ul>
    </div>
  )
}
