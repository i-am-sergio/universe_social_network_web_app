package com.unsa.backend.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserModel> obtenerUsuarios(){
        return (ArrayList<UserModel>)userRepository.findAll();
    }
}
