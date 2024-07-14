package com.example.yash_jain_numbersgame.finalcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yash_jain_numbersgame.Demox;
import com.example.yash_jain_numbersgame.R;
import com.example.yash_jain_numbersgame.adapters.AllMoviesAdapter;
import com.example.yash_jain_numbersgame.interfaces.OnMovieClicked;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeScreenActivity extends AppCompatActivity {


	// for sqlite
	//DatabaseHelper Demox;

	// fo room database
	public static AppDatabase appDatabase;
	public ExecutorService executorService;
	public MoviesDao movie_dao;

	RecyclerView rv_movieslist;
	AllMoviesAdapter all_movies_adapter;

	ImageView iv_add;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	public Dialog discard_dialog;

	@SuppressLint("MissingInflatedId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testing_actiivty);


		iv_add = findViewById(R.id.iv_add);
		rv_movieslist = findViewById(R.id.rv_movieslist);
		appDatabase = AppDatabase.getDatabase(HomeScreenActivity.this);

		// Initialize the MoviesDao
		movie_dao = appDatabase.moviesDao();

		sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

// Get SharedPreferences editor instance
		editor = sharedPreferences.edit();

		executorService = Executors.newSingleThreadExecutor();

//        Demox = new DatabaseHelper(this);
//        Log.e("mango","Movi");


		init_method();

		iv_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(HomeScreenActivity.this, AddActivity.class);
				i.putExtra("movie_title", "");
				i.putExtra("studio", "");
				i.putExtra("cric_rating", "");
				i.putExtra("is_from_add", 2);
				i.putExtra("id", 0);
				startActivity(i);
			}
		});


	}

	public void init_method() {

		executorService.execute(new Runnable() {
			@Override
			public void run() {
				// Perform the database operation
				boolean is_data_loaded = sharedPreferences.getBoolean("is_data_loaded", false);

				if (is_data_loaded) {
					Log.e("mango", "data_already_loaded ");
				} else {
					loadJsonAndInsertIntoDatabase();
					Log.e("mango", "data_inserted ");
				}


				List<Movies_Model> mm_list = movie_dao.get_all_movies();
				if (mm_list != null && mm_list.size() > 0)
				{
					Log.e("mango", "mm_list " + mm_list.size());

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							LinearLayoutManager gridLayoutManager = new LinearLayoutManager(HomeScreenActivity.this, RecyclerView.VERTICAL, false);
							rv_movieslist.setLayoutManager(gridLayoutManager);

							all_movies_adapter = new AllMoviesAdapter(HomeScreenActivity.this, mm_list,onMovieClicked);
							rv_movieslist.setAdapter(all_movies_adapter);
						}
					});

//					ViewTreeObserver vto = rv_movieslist.getViewTreeObserver();
//					vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
//					{
//						@Override
//						public void onGlobalLayout()
//						{
//
//
//
//						}
//					});


				}


			}
		});
	}

	OnMovieClicked onMovieClicked = new OnMovieClicked() {
		@Override
		public void on_movie_clicked(String title, int id, Movies_Model movie_details)
		{
			//int id = movie_details.id;
			String movie_title = movie_details.title;
			String studio = movie_details.studio;
			String cric_rating = movie_details.criticsRating;
			Intent i = new Intent(HomeScreenActivity.this, AddActivity.class);
			i.putExtra("movie_title", "");
			i.putExtra("studio", "");
			i.putExtra("cric_rating", "");
			i.putExtra("is_from_add", 0);
			i.putExtra("id", id);
			startActivity(i);

		}

		@Override
		public void on_delete_clicked(int id, Movies_Model movie_details) {
			show_delete_dialog(movie_details);
//			Intent i = new Intent(HomeScreenActivity.this, AddActivity.class);
//			startActivity(i);
		}

		@Override
		public void on_edit_clicked(int id, Movies_Model movie_details)
		{

			String movie_title = movie_details.title;
			String studio = movie_details.studio;
			String cric_rating = movie_details.criticsRating;
			Intent i = new Intent(HomeScreenActivity.this, AddActivity.class);
			i.putExtra("movie_title", movie_title);
			i.putExtra("studio", studio);
			i.putExtra("cric_rating", cric_rating);
			i.putExtra("id", id);
			i.putExtra("is_from_add", 1);
			startActivity(i);

		}
	};

	@Override
	public void onBackPressed() {
		finishAffinity();
		super.onBackPressed();
	}

	private void loadJsonAndInsertIntoDatabase() {
		String jsonStr = loadJSONFromAsset();

		if (jsonStr != null) {
			try {
				JSONArray jsonArray = new JSONArray(jsonStr);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject recipe = jsonArray.getJSONObject(i);
					Movies_Model mm = new Movies_Model();
					String title = mm.title = recipe.getString(Demox.COLUMN_TITLE);
					String atudio = mm.studio = recipe.getString(Demox.COLUMN_STUDIO);
					String genres = mm.genres = recipe.getString(Demox.COLUMN_GENRES);
					String directors = mm.directors = recipe.getString(Demox.COLUMN_DIRECTORS);
					String writers = mm.writers = recipe.getString(Demox.COLUMN_WRITERS);
					String actors = mm.actors = recipe.getString(Demox.COLUMN_ACTORS);
					String year = mm.year = recipe.getString(Demox.COLUMN_YEARS);
					String length = mm.length = recipe.getString(Demox.COLUMN_LENGTH);
					String shortDescription = mm.shortDescription = recipe.getString(Demox.COLUMN_SHORTDESCRIPTION);
					String mpaRating = mm.mpaRating = recipe.getString(Demox.COLUMN_MAPRATING);
					String criticsRating = mm.criticsRating = recipe.getString(Demox.COLUMN_CRITICSRATING);
					String image = mm.image = recipe.getString(Demox.COLUMN_IMAGE);


					try {
						movie_dao.insert(mm);
						if (editor != null) {
							editor.putBoolean("is_data_loaded", true);
							editor.apply();
						}

					} catch (Exception e) {
						Log.e("mango", "exception_insert " + e.getMessage());
					}


					//    String title = recipe.getString(Demox.COLUMN_TITLE);
//                    String ingredients = recipe.getString("ingredients");
//                    String instructions = recipe.getString("instructions");
//                    String imageUrl = recipe.getString("imageUrl");
//                    String category = recipe.getString("category");
//                    String dietaryPreferences = recipe.getString("dietaryPreferences");

//                    Log.e("mango","Moview_Title  11111 -  "+title);

					// Demox.insertRecipe(title, ingredients, instructions, imageUrl, category, dietaryPreferences);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private String loadJSONFromAsset() {
		String json = null;
		try {
			InputStream is = getAssets().open("db_movies.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}


	@Override
	protected void onResume() {

		super.onResume();
	}

	private void show_delete_dialog(Movies_Model movie_details) {

		discard_dialog = new Dialog(HomeScreenActivity.this);
		discard_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		discard_dialog.setContentView(R.layout.dialog_alertdialog);
		discard_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		discard_dialog.setCanceledOnTouchOutside(true);


		TextView yes = discard_dialog.findViewById(R.id.tv_yes);
		TextView no = discard_dialog.findViewById(R.id.tv_cancel);


		yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				executorService.execute(new Runnable()
				{
					@Override
					public void run() {
						int id=movie_details.id;
						movie_dao.deleteById(id);
						Log.e("daalbhaat","id_is "+String.valueOf(id));
					}
				});


				init_method();
				discard_dialog.dismiss();
			}
		});

		no.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				discard_dialog.dismiss();
			}
		});


//        discard_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		discard_dialog.show();

	}
}