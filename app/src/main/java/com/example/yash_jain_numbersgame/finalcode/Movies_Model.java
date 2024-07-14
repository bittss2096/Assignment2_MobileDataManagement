package com.example.yash_jain_numbersgame.finalcode;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.yash_jain_numbersgame.Demox;

@Entity(tableName = Demox.TABLE_MOVIES)
public class Movies_Model
{
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = Demox.COLUMN_TITLE)
    public String title;
    @ColumnInfo(name = Demox.COLUMN_STUDIO)
    public String studio;
    @ColumnInfo(name = Demox.COLUMN_GENRES)
    public String genres;
    @ColumnInfo(name = Demox.COLUMN_DIRECTORS)
    public String directors;
    @ColumnInfo(name = Demox.COLUMN_WRITERS)
    public String writers;
    @ColumnInfo(name = Demox.COLUMN_ACTORS)
    public String actors;
    @ColumnInfo(name = Demox.COLUMN_YEARS)
    public String year;
    @ColumnInfo(name = Demox.COLUMN_LENGTH)
    public String length;
    @ColumnInfo(name = Demox.COLUMN_SHORTDESCRIPTION)
    public String shortDescription;
    @ColumnInfo(name = Demox.COLUMN_MAPRATING)
    public String mpaRating;
    @ColumnInfo(name = Demox.COLUMN_CRITICSRATING)
    public String criticsRating;
    @ColumnInfo(name = Demox.COLUMN_IMAGE)
    public String image;
}
