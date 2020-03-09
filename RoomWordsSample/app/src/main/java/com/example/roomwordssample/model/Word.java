package com.example.roomwordssample.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "word_table",
        foreignKeys = @ForeignKey(
        entity = Food.class,
        parentColumns = "food",
        childColumns = "food")
)
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    @ColumnInfo(name = "food")
    private String mFood;

    public Word(@NonNull String word, String food) {
        this.mWord = word;
        this.mFood = food;
    }

    public String getWord() {
        return this.mWord;
    }
    public String getFood(){return this.mFood;}
}