package com.example.clientmovies.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movie_category", schema = "db_fd_movie", catalog = "")
public class MovieCategoryEntity {
    private String mid;
    private String cid;
    private int id;

    @Basic
    @Column(name = "mid", nullable = true, length = 20)
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    @Basic
    @Column(name = "cid", nullable = true, length = 30)
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCategoryEntity that = (MovieCategoryEntity) o;
        return id == that.id &&
                Objects.equals(mid, that.mid) &&
                Objects.equals(cid, that.cid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid, cid, id);
    }
}
