package com.example.clientmovies.service;

import com.example.clientmovies.entity.NormalUserEntity;
import org.springframework.stereotype.Service;


public interface NormalUserService {
     NormalUserEntity tryGetUser(String name, String password);
     void CreateNormalUserEntity(NormalUserEntity normalUserEntity);
}
