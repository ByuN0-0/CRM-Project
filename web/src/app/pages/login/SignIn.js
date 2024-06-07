"use client";
import React, { useState } from 'react';
import './login.css';
import { useRouter } from 'next/navigation';

const App = () => {
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const router = useRouter();
    
    const handleLogin = (e) => {
        e.preventDefault(); // 폼의 기본 제출 이벤트를 방지

        // 로그인 로직을 여기에 추가하세요.
        // 예를 들어, id와 password를 검증하는 로직 등

        // 검증 후 /crm 페이지로 이동
        router.push('/pages/crm');
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
