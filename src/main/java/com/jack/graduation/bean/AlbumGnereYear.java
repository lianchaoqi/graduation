package com.jack.graduation.bean;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@TableName("ads_album_gnere_year_nd")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlbumGnereYear implements Comparable<AlbumGnereYear> {
    private Integer id;
    private String genre;
    private String albumYearOfPub;
    private Long numOfTracks;
    private Long numOfSales;
    private String dt;
    private String filename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre == null ? null : genre.trim();
    }

    public String getAlbumYearOfPub() {
        return albumYearOfPub;
    }

    public void setAlbumYearOfPub(String albumYearOfPub) {
        this.albumYearOfPub = albumYearOfPub == null ? null : albumYearOfPub.trim();
    }

    public Long getNumOfTracks() {
        return numOfTracks;
    }

    public void setNumOfTracks(Long numOfTracks) {
        this.numOfTracks = numOfTracks;
    }

    public Long getNumOfSales() {
        return numOfSales;
    }

    public void setNumOfSales(Long numOfSales) {
        this.numOfSales = numOfSales;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt == null ? null : dt.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    //TODO:实现排序接口 重写compareTo方法，实现bean排序
    public int compareTo(AlbumGnereYear o) {
        if (Integer.parseInt(this.albumYearOfPub) > Integer.parseInt(o.albumYearOfPub)) {
            return -1;
        } else if (Integer.parseInt(this.albumYearOfPub) == Integer.parseInt(o.albumYearOfPub)) {
            return 0;
        } else {
            return 1;
        }
    }
}

