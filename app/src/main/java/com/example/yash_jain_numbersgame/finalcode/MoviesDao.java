package com.example.yash_jain_numbersgame.finalcode;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.yash_jain_numbersgame.Demox;

import java.util.List;

@Dao
public interface MoviesDao
{
	@Insert
	void insert(Movies_Model movie);

	@Update
	void update(Movies_Model movie);

	@Delete
	void delete(Movies_Model movie);

	@Query("SELECT * FROM "+ Demox.TABLE_MOVIES+" WHERE id = :userId")
	Movies_Model get_movie_by_id(int userId);

	@Query("SELECT * FROM " + Demox.TABLE_MOVIES)
	List<Movies_Model> get_all_movies();

	@Query("DELETE FROM "+Demox.TABLE_MOVIES+" WHERE id = :userId")
	void deleteById(int userId);

}
