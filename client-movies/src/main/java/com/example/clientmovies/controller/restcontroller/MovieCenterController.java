package com.example.clientmovies.controller.restcontroller;

import com.example.clientmovies.dao.CollectDao;
import com.example.clientmovies.entity.CollectEntity;
import com.example.clientmovies.entity.HateEntity;
import com.example.clientmovies.entity.LoveEntity;
import com.example.clientmovies.entity.MoviesEntity;
import com.example.clientmovies.service.CollectService;
import com.example.clientmovies.service.HateService;
import com.example.clientmovies.service.LoveService;
import com.example.clientmovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
@RestController
@RequestMapping("/api/movieCenter")
@CrossOrigin
public class MovieCenterController {
    private final CollectService collectService;
    private final LoveService loveService;
    private final MovieService movieService;
    private final HateService hateService;
    public MovieCenterController(MovieService movieService, LoveService loveService, CollectService collectService, HateService hateService) {
        this.movieService = movieService;
        this.loveService = loveService;
        this.collectService = collectService;
        this.hateService = hateService;
    }
    @GetMapping("/movies")
    public List<MoviesEntity> getMovies() {
        return movieService.GetMovie();
    }

    @GetMapping("/top")
    public List<MoviesEntity> getTopMovies() {
        return movieService.getTopMovies();
    }

    @RequestMapping("/type")
    public List<MoviesEntity> getTypeMovies(@RequestParam("type")String type) {
        switch (type){
            case "drama":
                return movieService.findMoviesEntitiesByTypeContaining("剧情");
            case "action":
                return movieService.findMoviesEntitiesByTypeContaining("动作");
            case "cartoon":
                return movieService.findMoviesEntitiesByTypeContaining("动画");
            case "suspense":
                return movieService.findMoviesEntitiesByTypeContaining("悬疑");
            case "comedy":
                return movieService.findMoviesEntitiesByTypeContaining("喜剧");
            case "horror":
                return movieService.findMoviesEntitiesByTypeContaining("惊悚");
            default:
                return movieService.findMoviesEntitiesByTypeContaining("");
        }
    }

    @RequestMapping("/home")
    public List<MoviesEntity> getHomeMovies(@RequestParam("type")String kind,@RequestParam("uId")String uId) {
        switch (kind){
            case "home":
                return movieService.GetLoveMovieByUserId(Integer.parseInt(uId));
            case "hate":
                return movieService.GetHateMovieByUserId(Integer.parseInt(uId));
            case "collect":
                return movieService.GetCollectMovieByUserId(Integer.parseInt(uId));
            default:
                return null;
        }
    }

    @RequestMapping("/addLove")
    public boolean addLove(@RequestParam("mId")String mId, @RequestParam("uId")String uId) {
        if(loveService.LoveExist(Integer.parseInt(mId),Integer.parseInt(uId))!=null){
            return false;
        }
        else {
            LoveEntity loveEntity=new LoveEntity();
            loveEntity.setMovieId(Integer.parseInt(mId));
            loveEntity.setUserId(Integer.parseInt(uId));
            loveService.save(loveEntity);
            return true;
        }
    }
    @RequestMapping("/addHate")
    public boolean addHate(@RequestParam("mId")String mId, @RequestParam("uId")String uId) {
        if(hateService.HateExist(Integer.parseInt(mId),Integer.parseInt(uId))!=null){
            return false;
        }
        else {
            HateEntity hateEntity=new HateEntity();
            hateEntity.setMovieId(Integer.parseInt(mId));
            hateEntity.setUserId(Integer.parseInt(uId));
            hateService.save(hateEntity);
            return true;
        }
    }

    @RequestMapping("/addCollect")
    public boolean addCollect(@RequestParam("mId")String mId, @RequestParam("uId")String uId) {
        if(collectService.CollectExist(Integer.parseInt(mId),Integer.parseInt(uId))!=null){
            return false;
        }
        else {
            CollectEntity collectEntity=new CollectEntity();
            collectEntity.setMovieId(Integer.parseInt(mId));
            collectEntity.setUserId(Integer.parseInt(uId));
            collectService.save(collectEntity);
            return true;
        }
    }
}
