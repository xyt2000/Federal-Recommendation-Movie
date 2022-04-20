package com.example.clientmovies.dao;

import com.example.clientmovies.entity.MoviesEntity;
import com.example.clientmovies.entity.NormalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface MovieDao extends JpaRepository<MoviesEntity,Integer> {
    @Query("select t from MoviesEntity t")
    public List<MoviesEntity> GetMovie();
    MoviesEntity findMoviesEntityById(int id);
    @Query("select t from MoviesEntity t order by t.rate desc")
    List<MoviesEntity> getTopMovies();
    List<MoviesEntity> findMoviesEntitiesByTypeContaining(String type);
    @Query("select m from MoviesEntity m, NormalUserEntity n, LoveEntity l where m.id=l.movieId and n.id=l.userId and n.id=?1")
    public List<MoviesEntity> GetLoveMovieByUserId(int id);
    @Query("select m from MoviesEntity m, NormalUserEntity n, CollectEntity l where m.id=l.movieId and n.id=l.userId and n.id=?1")
    public List<MoviesEntity> GetCollectMovieByUserId(int id);
    @Query("select m from MoviesEntity m, NormalUserEntity n, HateEntity l where m.id=l.movieId and n.id=l.userId and n.id=?1")
    public List<MoviesEntity> GetHateMovieByUserId(int id);
}
