package com.example.clientmovies.dao;

import com.example.clientmovies.entity.NormalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NormalUserDao extends JpaRepository<NormalUserEntity,Integer> {
    @Query("select t from NormalUserEntity t where t.name=?1 and t.password=?2")
    public NormalUserEntity tryGetUser(String name,String password);
}
