"use client";
import React, {useState} from 'react';
import axios from 'axios';
import {useRouter} from 'next/navigation';

const SignUpPage = () => {
    const router = useRouter();
    const [formData, setFormData] = useState({
        username: '', // name -> username으로 변경
        password: '',
        workspaceName: '', // 필드 추가
    });

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData({...formData, [name]: value});
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        //const url = 'http://61.109.237.69:8080/api/register';
        const url = 'http://127.0.0.1:8080/api/register'; // 로컬 테스트용 URL

        // axios를 사용하여 회원가입 처리 로직 추가
        axios.post(url, {
            username: formData.username,
            password: formData.password,
            workspaceName: formData.workspaceName
        }, {headers: {'Content-Type': 'application/json'}})
            .then(response => {
                console.log('Success:', response.data);
                // 회원가입 성공 후 처리 로직
                router.push('/')
            })
            .catch(error => {
                console.error('Error:', error);
                // 에러 처리 로직
            });
    };

    return (
        <div className="min-h-screen flex items-center justify-center">
            <div className="bg-white p-8 rounded-lg shadow-lg w-96">
                <h2 className="text-3xl font-semibold text-center text-gray-800 mb-6">
                    Sign Up
                </h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <input
                            type="text"
                            name="workspaceName" // name 값을 workspaceName으로 변경
                            placeholder="Workspace Name"
                            value={formData.workspaceName}
                            onChange={handleChange}
                            className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:border-indigo-500"
                            required
                        />
                    </div>
                    <div>
                        <input
                            type="email"
                            name="username" // name 값을 username으로 변경
                            placeholder="Email"
                            value={formData.username}
                            onChange={handleChange}
                            className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:border-indigo-500"
                            required
                        />
                    </div>
                    <div>
                        <input
                            type="password"
                            name="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange={handleChange}
                            className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:border-indigo-500"
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full py-2 px-4 bg-indigo-600 text-white rounded-lg font-semibold hover:bg-indigo-700 transition duration-300"
                    >
                        Sign Up
                    </button>
                </form>
            </div>
        </div>
    );
};

export default SignUpPage;
