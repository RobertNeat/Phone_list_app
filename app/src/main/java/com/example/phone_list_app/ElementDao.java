package com.example.phone_list_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ElementDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)//IGNORE <- wtedy będzie pozwalało wielokrotnie dodawać to samo
    void insert(Element phone);

    @Update
    void update(Element phone);

    @Delete
    void delete(Element phone);

    @Query("DELETE FROM phone_table")
    void deleteAll();

    @Query("SELECT * FROM phone_table ORDER BY id ASC")
    LiveData<List<Element>> getOrderedPhones();//ordered by adding order

    //usunięcie elementu na podstawie klucza id
    @Query("DELETE FROM phone_table WHERE id = :id")
    void deleteById(int id);

}
