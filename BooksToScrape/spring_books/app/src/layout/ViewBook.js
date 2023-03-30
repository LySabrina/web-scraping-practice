import React, {useEffect, useState} from "react";
import {Link, useParams} from 'react-router-dom';
import axios from "axios"; //used to get information?
export default function ViewBook(){
    const [book, setBook] = useState({
        name:"",
        genre:"",
        price:""
        }
    );

    const {id} = useParams();

    useEffect(()=>{
        loadBook();
    }, []);

    const loadBook = async ()=>{
        const result = await axios.get(`http://localhost:8080/book/${id}`);
        setBook(result.data);
    }
    return(
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                   <h2 className="text-center m-4"> Book Details </h2>
                    <div className="card">
                        <div className="card-header">
                            <div className = "card-header">
                                Details of Book: {book.id}
                                <ul className="list-group list-group-flush">
                                    <li className="list-group-item">
                                        <b>Name: </b>
                                        {book.name}
                                    </li>
                                    <li className="list-group-item">
                                        <b>Genre: </b>
                                        {book.genre}
                                    </li>
                                    <li className="list-group-item">
                                        <b>Price: </b>
                                        ${book.price}
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <Link className="btn btn-primary my-2" to={"/"}> Back to Home </Link>
                </div>
            </div>
        </div>
    )
}