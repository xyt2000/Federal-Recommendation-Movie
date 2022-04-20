package com.example.clientmovies.controller;

import com.example.clientmovies.dao.MovieDao;
import com.example.clientmovies.entity.MoviesEntity;
import com.example.clientmovies.entity.NormalUserEntity;
import com.example.clientmovies.service.MovieService;
import com.example.clientmovies.service.NormalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("")
public class ClientController {
    public final NormalUserService normalUserService;
    public final MovieService movieService;
    public ClientController(NormalUserService normalUserService, MovieService movieService) {
        this.normalUserService = normalUserService;
        this.movieService = movieService;
    }

    @GetMapping("")
    public String defaultF(){
        return "login";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/top")
    public String top(){
        return "index";
    }

    @GetMapping("/drama")
    public String drama(){
        return "index";
    }

    @GetMapping("/action")
    public String action(){
        return "index";
    }

    @GetMapping("/cartoon")
    public String cartoon(){
        return "index";
    }

    @GetMapping("/suspense")
    public String suspense(){
        return "index";
    }

    @GetMapping("/comedy")
    public String comedy(){
        return "index";
    }

    @GetMapping("/horror")
    public String horror(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam String name,
                            @RequestParam String password, HttpSession session, RedirectAttributes attributes){
        NormalUserEntity normalUserEntity = normalUserService.tryGetUser(name,password);

        if(normalUserEntity!=null){
            session.setAttribute("user",normalUserEntity);
            return "redirect:/index";
        }
        else {
            attributes.addFlashAttribute("message","用户名或密码错误");

            // 打印出错误堆栈信息
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String newRegister(@RequestParam String name,
                           @RequestParam String password,
                           @RequestParam String phoneNum,
                           @RequestParam String mail,
                           @RequestParam String[] hobby){
        NormalUserEntity normalUserEntity = new NormalUserEntity();
        normalUserEntity.setName(name);
        normalUserEntity.setPassword(password);
        normalUserEntity.setPhonenum(phoneNum);
        normalUserEntity.setMail(mail);
        String Hobby = "";
        for(int i =0;i<hobby.length;i++){
            Hobby = Hobby +hobby[i] + "/";
        }
        Hobby=Hobby.substring(0,Hobby.length()-1);
        normalUserEntity.setHobby(Hobby);
        normalUserService.CreateNormalUserEntity(normalUserEntity);
        return "redirect:/login";
    }

    @RequestMapping(value="/movie/detail/{id}")
    //查看学习详情
    public String toSeeMovie(@PathVariable(required = true,value = "id")int id,HttpSession session, Model model){

        MoviesEntity moviesEntity = movieService.findMoviesEntityById(id);
        model.addAttribute("movie",moviesEntity);
        NormalUserEntity normalUserEntity = (NormalUserEntity) session.getAttribute("user");
        model.addAttribute("user",normalUserEntity);
        return "movieInfo";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model){
        NormalUserEntity normalUserEntity = (NormalUserEntity) session.getAttribute("user");
        model.addAttribute("user",normalUserEntity);
        return "person";
    }
    @GetMapping("/collect")
    public String collect(HttpSession session, Model model){
        NormalUserEntity normalUserEntity = (NormalUserEntity) session.getAttribute("user");
        model.addAttribute("user",normalUserEntity);
        return "person";
    }
    @GetMapping("/hate")
    public String hate(HttpSession session, Model model){
        NormalUserEntity normalUserEntity = (NormalUserEntity) session.getAttribute("user");
        model.addAttribute("user",normalUserEntity);
        return "person";
    }
}
