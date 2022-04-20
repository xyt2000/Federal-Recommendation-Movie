package com.example.clientmovies.service;

import com.example.clientmovies.entity.MoviesEntity;

import java.util.List;

public interface MovieService {
    public List<MoviesEntity> GetMovie();
    MoviesEntity findMoviesEntityById(int id);
    List<MoviesEntity> getTopMovies();
    List<MoviesEntity> findMoviesEntitiesByTypeContaining(String type);
    public List<MoviesEntity> GetLoveMovieByUserId(int id);
    public List<MoviesEntity> GetCollectMovieByUserId(int id);
    public List<MoviesEntity> GetHateMovieByUserId(int id);
}
