package com.example.clientmovies.service.serviceimpl;

import com.example.clientmovies.dao.MovieDao;
import com.example.clientmovies.entity.MoviesEntity;
import com.example.clientmovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieImpl implements MovieService {
    private final MovieDao movieDao;

    public MovieImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<MoviesEntity> GetMovie() {
        return movieDao.GetMovie();
    }

    @Override
    public MoviesEntity findMoviesEntityById(int id) {
        return movieDao.findMoviesEntityById(id);
    }

    @Override
    public List<MoviesEntity> getTopMovies() {
        return movieDao.getTopMovies();
    }

    @Override
    public List<MoviesEntity> findMoviesEntitiesByTypeContaining(String type) {
        return movieDao.findMoviesEntitiesByTypeContaining(type);
    }

    @Override
    public List<MoviesEntity> GetLoveMovieByUserId(int id) {
        return movieDao.GetLoveMovieByUserId(id);
    }

    @Override
    public List<MoviesEntity> GetCollectMovieByUserId(int id) {
        return movieDao.GetCollectMovieByUserId(id);
    }

    @Override
    public List<MoviesEntity> GetHateMovieByUserId(int id) {
        return movieDao.GetHateMovieByUserId(id);
    }

}
