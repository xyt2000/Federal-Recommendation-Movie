package com.example.clientmovies.dao;

import com.example.clientmovies.entity.LoveEntity;
import com.example.clientmovies.entity.MoviesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface LoveDao extends JpaRepository<LoveEntity,Integer> {
    @Query("select t from LoveEntity t where t.movieId=?1 and t.userId=?2")
    LoveEntity LoveExist(int movieId, int userId);
}
