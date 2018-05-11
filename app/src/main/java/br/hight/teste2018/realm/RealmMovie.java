package br.hight.teste2018.realm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Thiago Azevedo on 28/04/2018.
 */
@RealmClass
public class RealmMovie extends RealmObject{

    @PrimaryKey
    Integer id;

    Integer voteCount;
    Boolean video;
    Double voteAverage;
    String title;
    Double popularity;
    String posterPath;
    byte[] poster;
    String originalLanguage;
    String originalTitle;
    RealmList<RealmInteger> genreIds;
    String backdropPath;
    byte[] backdrop;
    Boolean adult;
    String overview;
    String releaseDate;

    public RealmMovie(){
    }

    public RealmMovie(Integer id, Integer voteCount, Boolean video, Double voteAverage, String title, Double popularity, String posterPath, String originalLanguage, String originalTitle, ArrayList<Integer> genreIds, String backdropPath, Boolean adult, String overview, String releaseDate) {
        this.id = id;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        for(Integer i : genreIds){
            this.genreIds.add(new RealmInteger(i));
        }
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public RealmList<RealmInteger> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(RealmList<RealmInteger> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public RealmMovie getByPrimaryKey(Realm realm, Integer id) {
        return realm.where(getClass()).equalTo("id", id).findFirst();
    }

    public RealmMovie getByIndex(Realm realm, int i) {
        return realm.where(RealmMovie.class).findAll().get(i);
    }

    public long getSize(Realm realm){
        return realm.where(getClass()).count();
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public byte[] getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(byte[] backdrop) {
        this.backdrop = backdrop;
    }
}
