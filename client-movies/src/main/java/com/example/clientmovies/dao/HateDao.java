package com.example.clientmovies.dao;

import com.example.clientmovies.entity.CollectEntity;
import com.example.clientmovies.entity.HateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HateDao extends JpaRepository<HateEntity, Integer> {
    @Query("select t from HateEntity t where t.movieId=?1 and t.userId=?2")
    HateEntity HateExist(int movieId, int userId);
}
