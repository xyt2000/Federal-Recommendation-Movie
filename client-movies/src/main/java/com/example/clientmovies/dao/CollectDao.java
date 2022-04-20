package com.example.clientmovies.dao;

import com.example.clientmovies.entity.CollectEntity;
import com.example.clientmovies.entity.LoveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectDao extends JpaRepository<CollectEntity, Integer> {
    @Query("select t from CollectEntity t where t.movieId=?1 and t.userId=?2")
    CollectEntity CollectExist(int movieId, int userId);
}
