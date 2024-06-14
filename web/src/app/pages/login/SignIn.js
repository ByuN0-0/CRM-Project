"use client";
import React, { useState } from 'react';
import './login.css';
import { useRouter } from 'next/navigation';
import axios from "axios";
import Cookies from 'js-cookie';

const App = () => {
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const router = useRouter();
    const [token, setToken] = useState('');

    const handleLogin = (e) => {
        e.preventDefault(); // 폼의 기본 제출 이벤트를 방지
        const url = 'http://61.109.237.69:8080/login';
        // const url = 'http://127.0.0.1:8080/login'; // 로컬 테스트용 URL

        axios.post(url, {
            username: id,
            password: password,
        }, {headers: {'Content-Type': 'application/json'}})
            .then(response => {
                console.log('Success');
                const receivedToken = response.headers['authorization'].split(' ')[1];
                setToken(receivedToken);
                Cookies.set('jwtToken', receivedToken); // 토큰을 쿠키에 저장
                router.push('/pages/crm');
                // 로그인 성공
            })
            .catch(error => {
                console.error('Error:', error);
                // 로그인 실패
            });

        // 검증 후 /crm 페이지로 이동
    };

    const handleSignUpClick = (e) => {
        e.preventDefault(); // 버튼의 기본 이벤트를 방지
        router.push('/pages/signup');
    };

    return (
        <div className="signInbox">
            <form onSubmit={handleLogin}>
                <div className='signInInput'>
                    <input 
                        type="text"
                        id="id" 
                        value={id} 
                        onChange={(e) => setId(e.target.value)}
                        placeholder='  E-mail' 
                    />
                </div>
                <div className='signInInput'>
                    <input 
                        type="password" 
                        id="password" 
                        value={password} 
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder='  PASSWORD' 
                    />
                </div>
                <div className='signInInput'>
                    <button type="submit" className="signInbtn">Login</button>
                </div>
                {/* submit 대신 button 타입을 사용하여 폼 제출을 방지합니다. */}
                <button type="button" className="signInbtn" onClick={handleSignUpClick}>SignUp</button>
            </form>
        </div>
    );
};

export default App;
