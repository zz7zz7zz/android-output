package com.open.test.jetpack.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Insert
    void insertUsers(List<User> users);

    @Delete()
    void delete(User user);

    @Query("DELETE from User")
    void deleteAll();

    @Query("SELECT * from User ORDER BY ID DESC")
    List<User> query();

    @Query("SELECT * from User where id = :id")
    User queryById(long id);

    @Update
    void updateUser(User user);


}
