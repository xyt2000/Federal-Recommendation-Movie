package com.example.clientmovies.service;

import com.example.clientmovies.entity.LoveEntity;
import com.example.clientmovies.entity.NormalUserEntity;

public interface LoveService {
    LoveEntity LoveExist(int movieId, int userId);
    LoveEntity save(LoveEntity loveEntity);

}
