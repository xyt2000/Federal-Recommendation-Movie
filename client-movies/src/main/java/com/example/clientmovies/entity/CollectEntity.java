package com.example.clientmovies.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "collect", schema = "db_fd_movie", catalog = "")
public class CollectEntity {
    private int id;
    private Integer userId;
    private Integer movieId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userId", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "movieId", nullable = true)
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectEntity that = (CollectEntity) o;
        return id == that.id &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(movieId, that.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, movieId);
    }
}
