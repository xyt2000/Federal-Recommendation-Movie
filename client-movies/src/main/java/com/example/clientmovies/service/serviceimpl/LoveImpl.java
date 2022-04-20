package com.example.clientmovies.service.serviceimpl;

import com.example.clientmovies.dao.LoveDao;
import com.example.clientmovies.entity.LoveEntity;
import com.example.clientmovies.service.LoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoveImpl implements LoveService {
    private final LoveDao loveDao;

    public LoveImpl(LoveDao loveDao) {
        this.loveDao = loveDao;
    }

    @Override
    public LoveEntity LoveExist(int movieId, int userId) {
        return loveDao.LoveExist(movieId, userId);
    }

    @Override
    public LoveEntity save(LoveEntity loveEntity) {
        return loveDao.save(loveEntity);
    }
}
