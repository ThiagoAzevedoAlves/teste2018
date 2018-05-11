package br.hight.teste2018.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Thiago Azevedo on 04/05/2018.
 */

@RealmClass
public class RealmInteger extends RealmObject{

    @PrimaryKey
    Integer id;
    Integer value;

    public RealmInteger() {
    }

    public RealmInteger(Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
