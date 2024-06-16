"use client";
import React, { useState } from 'react';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import './signup.css'; // 스타일을 적용할 CSS 파일을 import

const SignUpPage = () => {
    const router = useRouter();
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        workspaceName: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const url = 'http://127.0.0.1:8080/api/register';

        axios.post(url, {
            username: formData.username,
            password: formData.password,
            workspaceName: formData.workspaceName
        }, { headers: { 'Content-Type': 'application/json' } })
            .then(response => {
                console.log('Success:', response.data);
                router.push('/')
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    return (
        <main>
            <div className="signUpBox">
                <h2 className="text-3xl font-semibold text-center mb-6">
                    Sign Up
                </h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div className="signUpInput">
                        <input
                            type="text"
                            name="workspaceName"
                            placeholder="WorkSpace Name"
                            value={formData.workspaceName}
                            onChange={handleChange}
                            className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:border-indigo-500"
                            required
                        />
                    </div>
                    <div className="signUpInput">
                        <input
                            type="email"
                            name="username"
                            placeholder="E-mail"
                            value={formData.username}
                            onChange={handleChange}
                            className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:border-indigo-500"
                            required
                        />
                    </div>
                    <div className="signUpInput">
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
                        className="signUpBtn"
                    >
                        Sign up
                    </button>
                </form>
            </div>
        </main>
    );
};

export default SignUpPage;
