package com.example.clientmovies.service.serviceimpl;

import com.example.clientmovies.dao.HateDao;
import com.example.clientmovies.entity.HateEntity;
import com.example.clientmovies.service.HateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HateImpl implements HateService {
    private final HateDao hateDao;

    public HateImpl(HateDao hateDao) {
        this.hateDao = hateDao;
    }

    @Override
    public HateEntity HateExist(int movieId, int userId) {
        return hateDao.HateExist(movieId, userId);
    }

    @Override
    public HateEntity save(HateEntity hateEntity) {
        return hateDao.save(hateEntity);
    }
}
