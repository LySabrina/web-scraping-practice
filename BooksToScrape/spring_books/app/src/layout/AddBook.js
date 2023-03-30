import React, { useEffect, useState } from "react";
import axios from "axios"; //used to get information?
import { Link } from 'react-router-dom';
export default function AddBook() {
  const [genres, setGenres] = useState([]);

  const [book,setBook] = useState({
    name: "",
    genre:"",
    price:""
  });

  const{name, genre, price} = book;

  const onInputChange = (e) =>{
    setBook({...book, [e.target.name]:e.target.value})
  };

  const loadGenre = async() =>{
    const result = await axios.get("http://localhost:8080/genres");
    setGenres(result.data);
  };

  useEffect(()=>{
    loadGenre();
  }, []);

  return (
    <div className='container'>
      <div className='row'>
        <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
          <h2 className = 'text-center m-4'> Add new book</h2>
          <div className = 'mb-3'>
            <label htmlFor = 'Name' className = 'form-label'>
              Name 
            </label>
            <input type = {"text"} className = 'form-control' placeholder='Enter Book Name' name="name" value={name} onChange={(e) => onInputChange(e)}/>
          </div>

          <div className = 'mb-3'>
            <label htmlFor = 'Genre' className = 'form-label'>
              Genre
            </label>
            <div className = 'dropdown'>

            </div>
            <input type = {"text"} className = 'form-control' placeholder='Enter Book Name' name="genre" value={genre} onChange={(e) => onInputChange(e)}/>

          </div>


          <div className = 'mb-3'>
            <label htmlFor = 'Price' className = 'form-label'>
              Price
            </label>
            <input type = {"number"} className = 'form-control' placeholder='Enter Price' name ='price' min='1' step='any' value={price} onChange={(e) => onInputChange(e)}/>

          </div>
        <button type='submit' className='btn btn-outline-primary'>Submit</button>
          <button type='submit' className='btn btn-outline-danger'>Cancel</button>
        </div>
      </div>
    </div>
  )
}
