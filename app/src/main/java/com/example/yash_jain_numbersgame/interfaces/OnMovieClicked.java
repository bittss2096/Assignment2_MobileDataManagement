package com.example.yash_jain_numbersgame.interfaces;

import com.example.yash_jain_numbersgame.finalcode.Movies_Model;

public interface OnMovieClicked
{
    public void on_movie_clicked(String title, int id, Movies_Model movie_details);

    public void on_delete_clicked(int id, Movies_Model movie_details);

    public void  on_edit_clicked(int id, Movies_Model movie_details);
}
