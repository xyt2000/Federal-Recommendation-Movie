package com.example.clientmovies.service.serviceimpl;

import com.example.clientmovies.dao.CollectDao;
import com.example.clientmovies.entity.CollectEntity;
import com.example.clientmovies.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectImpl implements CollectService {
    private final CollectDao collectDao;

    public CollectImpl(CollectDao collectDao) {
        this.collectDao = collectDao;
    }

    @Override
    public CollectEntity CollectExist(int movieId, int userId) {
        return collectDao.CollectExist(movieId, userId);
    }

    @Override
    public CollectEntity save(CollectEntity collectEntity) {
        return collectDao.save(collectEntity);
    }
}
