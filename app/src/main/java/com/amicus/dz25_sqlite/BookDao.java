package com.amicus.dz25_sqlite;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(Book book);
    @Query("SELECT * FROM booksDB")
    List<Book> getAllBooks();
    @Query("DELETE FROM booksDB WHERE (name=:name and author=:author)")
    void deleteByNameAndAuthor(String name,String author);
    @Query("DELETE FROM booksDB")
    void clearTable();
}
