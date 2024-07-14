package com.example.yash_jain_numbersgame.finalcode;

import static com.example.yash_jain_numbersgame.finalcode.HomeScreenActivity.appDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yash_jain_numbersgame.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddActivity extends AppCompatActivity {

	TextView tv_title;
	EditText et_movie, et_studio, et_cricrate;
	ImageView back_button;

	String movie_title, studio, cric_rating;
	int current_view, current_id;
	TextView tv_done;
	LinearLayout ll_view;

	public MoviesDao movie_dao;

	public ExecutorService executorService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_actiivty);
		tv_title = findViewById(R.id.tv_title);
		back_button = findViewById(R.id.back_button);
		et_movie = findViewById(R.id.et_movie);
		et_studio = findViewById(R.id.et_studio);
		et_cricrate = findViewById(R.id.et_cricrate);
		ll_view = findViewById(R.id.ll_view);
		tv_done = findViewById(R.id.tv_done);

		// Initialize the MoviesDao
		movie_dao = appDatabase.moviesDao();

		movie_title = getIntent().getStringExtra("movie_title");
		studio = getIntent().getStringExtra("studio");
		cric_rating = getIntent().getStringExtra("cric_rating");

		current_id = getIntent().getIntExtra("id", 0);
		current_view = getIntent().getIntExtra("is_from_add", 0);

		executorService = Executors.newSingleThreadExecutor();
		switch (current_view) {
			case 0:
				tv_title.setText("Review Details");
				ll_view.setVisibility(View.GONE);
				et_movie.setEnabled(false);
				et_studio.setEnabled(false);
				et_cricrate.setEnabled(false);
				break;
			case 1:
				tv_title.setText("Edit Details");
				tv_done.setText("Update Movie");
				ll_view.setVisibility(View.VISIBLE);
				et_movie.setEnabled(true);
				et_studio.setEnabled(true);
				et_cricrate.setEnabled(true);

				break;
			case 2:
				tv_title.setText("Add Details");
				tv_done.setText("Add Movie");
				ll_view.setVisibility(View.VISIBLE);
				et_movie.setEnabled(true);
				et_studio.setEnabled(true);
				et_cricrate.setEnabled(true);

				break;
			default:
				et_movie.setEnabled(true);
				et_studio.setEnabled(true);
				et_cricrate.setEnabled(true);
		}


		et_movie.setText(movie_title);
		et_studio.setText(studio);
		et_cricrate.setText(cric_rating);


		tv_done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				switch (current_view) {
					case 0:
						break;
					case 1:
						movie_title = et_movie.getText().toString();
						studio = et_studio.getText().toString();
						cric_rating = et_cricrate.getText().toString();
						if (movie_title.length() > 0 && studio.length() > 0 && cric_rating.length() > 0) {

							update_data();
						} else {
							Toast.makeText(AddActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
						}
						break;
					case 2:
						movie_title = et_movie.getText().toString();
						studio = et_studio.getText().toString();
						cric_rating = et_cricrate.getText().toString();
						if (movie_title.length() > 0 || studio.length() > 0 || cric_rating.length() > 0)
						{
							add_data();
						} else {
							Toast.makeText(AddActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
						}
						break;

				}
			}


		});

		back_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});


	}

	public void update_data() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				Movies_Model mm = movie_dao.get_movie_by_id(current_id);
				mm.title=movie_title;
				mm.studio=studio;
				mm.criticsRating=cric_rating;
//				mm.genres=mm.genres;
//				String directors = mm.directors;
//				String writers = mm.writers;
//				String actors = mm.actors;
//				String year = mm.year;
//				String length = mm.length;
//				String shortDescription = mm.shortDescription;
//				String mpaRating = mm.mpaRating;
//				String criticsRating = mm.criticsRating;
//				String image = mm.image;

				try {
					movie_dao.update(mm);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(AddActivity.this, "Details Updated", Toast.LENGTH_SHORT).show();
							Intent i=new Intent(AddActivity.this, HomeScreenActivity.class);
							startActivity(i);
							finish();
						}
					});

				} catch (Exception e) {
					Log.e("mango", "exception_insert " + e.getMessage());
				}
			}
		});
	}

	public void add_data()
	{

		executorService.execute(new Runnable() {
			@Override
			public void run() {
				Movies_Model mm = new Movies_Model();
				mm.title=movie_title;
				mm.studio=studio;
				mm.criticsRating=cric_rating;
				mm.genres="";
				mm.directors="";
				mm.writers="";
				mm.actors="";
				mm.year="";
				mm.length="";
				mm.shortDescription="";
				mm.mpaRating="";
				mm.criticsRating="";
				mm.image="";

				try
				{
					movie_dao.insert(mm);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(AddActivity.this, "Details Added to database", Toast.LENGTH_SHORT).show();
							Intent i=new Intent(AddActivity.this, HomeScreenActivity.class);
							startActivity(i);
							finish();
						}
					});


				} catch (Exception e) {
					Log.e("mango", "exception_insert " + e.getMessage());
				}
			}
		});

	}
}