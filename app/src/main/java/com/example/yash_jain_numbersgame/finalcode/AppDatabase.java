package com.example.yash_jain_numbersgame.finalcode;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.yash_jain_numbersgame.Demox;

@Database(entities = {Movies_Model.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
	public abstract MoviesDao moviesDao();

	private static volatile AppDatabase INSTANCE;

	public static AppDatabase getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (AppDatabase.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
									AppDatabase.class, Demox.ROOM_DB_NAME)
							.build();
				}
			}
		}
		return INSTANCE;
	}
}
