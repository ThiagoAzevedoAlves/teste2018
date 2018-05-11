package br.hight.teste2018.realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Thiago Azevedo on 28/04/2018.
 */
@RealmClass
public class RealmBitmap extends RealmObject{

    @PrimaryKey
    Integer id;

    byte[] poster;

    public RealmBitmap(){
    }

    public RealmBitmap(byte[] p){
        this.poster = p;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public RealmBitmap getByIndex(Realm realm, int i) {
        return realm.where(RealmBitmap.class).findAll().get(i);
    }

    public long getSize(Realm realm){
        return realm.where(getClass()).count();
    }
}
