package com.example.clientmovies.service;

import com.example.clientmovies.entity.HateEntity;
import com.example.clientmovies.entity.MoviesEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HateService {
    HateEntity HateExist(int movieId, int userId);
    HateEntity save(HateEntity hateEntity);


}
