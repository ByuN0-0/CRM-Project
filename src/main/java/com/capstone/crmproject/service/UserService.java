package com.capstone.crmproject.service;


import com.capstone.crmproject.model.User;
import com.capstone.crmproject.request.UserRegisterRequest;


public interface UserService{
    boolean registerUser(UserRegisterRequest userRegisterRequest);
}
