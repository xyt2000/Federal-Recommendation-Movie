package com.example.clientmovies.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movies", schema = "db_fd_movie", catalog = "")
public class MoviesEntity {
    private int id;
    private String postUrl;
    private String title;
    private String director;
    private String scriptwriter;
    private String actors;
    private String district;
    private String rate;
    private String date;
    private String language;
    private String duration;
    private String type;
    private String abs;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "post_url", nullable = true, length = 200)
    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 45)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "director", nullable = true, length = 45)
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Basic
    @Column(name = "scriptwriter", nullable = true, length = 45)
    public String getScriptwriter() {
        return scriptwriter;
    }

    public void setScriptwriter(String scriptwriter) {
        this.scriptwriter = scriptwriter;
    }

    @Basic
    @Column(name = "actors", nullable = true, length = 200)
    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Basic
    @Column(name = "district", nullable = true, length = 200)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "rate", nullable = true, length = 200)
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "date", nullable = true, length = 200)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "language", nullable = true, length = 45)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "duration", nullable = true, length = 45)
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 200)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "abs", nullable = true, length = 200)
    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoviesEntity that = (MoviesEntity) o;
        return id == that.id &&
                Objects.equals(postUrl, that.postUrl) &&
                Objects.equals(title, that.title) &&
                Objects.equals(director, that.director) &&
                Objects.equals(scriptwriter, that.scriptwriter) &&
                Objects.equals(actors, that.actors) &&
                Objects.equals(district, that.district) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(date, that.date) &&
                Objects.equals(language, that.language) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(type, that.type) &&
                Objects.equals(abs, that.abs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postUrl, title, director, scriptwriter, actors, district, rate, date, language, duration, type, abs);
    }
}
