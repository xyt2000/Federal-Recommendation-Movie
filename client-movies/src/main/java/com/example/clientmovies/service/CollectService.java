package com.example.clientmovies.service;

import com.example.clientmovies.entity.CollectEntity;

public interface CollectService {
    CollectEntity CollectExist(int movieId, int userId);
    CollectEntity save(CollectEntity collectEntity);
}
