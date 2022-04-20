package com.example.clientmovies.service.serviceimpl;

import com.example.clientmovies.dao.NormalUserDao;
import com.example.clientmovies.entity.NormalUserEntity;
import com.example.clientmovies.service.NormalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NormalUserImpl implements NormalUserService {
    public final NormalUserDao normalUserDao;

    public NormalUserImpl(NormalUserDao normalUserDao) {
        this.normalUserDao = normalUserDao;
    }

    @Override
    public NormalUserEntity tryGetUser(String name, String password) {
        return normalUserDao.tryGetUser(name, password);
    }

    @Override
    public void CreateNormalUserEntity(NormalUserEntity normalUserEntity) {
        normalUserDao.save(normalUserEntity);
    }
}
