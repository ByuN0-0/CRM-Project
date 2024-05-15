"use client";
import React from 'react';
import { useState } from 'react';
import './login.css'

const SignIn = () => {
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');

    return (
        <div div class="signInbox">
            <form action="pages/crm" method="post">
                <div className='signInInput'>
                    
                   
                    <input 
                        type="text" 
                        id="id" 
                        value={id} 
                        onChange={(e) => setId(e.target.value)}
                        placeholder='ID' 
                    />
                </div >
                <div className='signInInput'>
                
                   
                    <input 
                        type="password" 
                        id="password" 
                        value={password} 
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder='PASSWORD' 
                    />
                </div>
                    <div className='signInInput'>
                <button class="signInbtn">Login</button>
                </div>
            </form>
        </div>
    );
};

export default SignIn;
