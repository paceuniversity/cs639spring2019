package com.kachi.room.wordsample;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

@Entity(tableName = "word_table")
public class Word implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    @NonNull
    @ColumnInfo(name = "definition")
    private String mDefinition;


    @ColumnInfo(name = "part_of_speech")
    @TypeConverters(SpeechPartConverter.class)
    @NonNull
    private SpeechPart mSpeechPart;

    //notice how constructor parameters map exactly to the names of the member variables
    public Word(@NonNull String word, @NonNull String definition, @NonNull SpeechPart speechPart) {
        this.mWord = word;
        this.mDefinition = definition;
        this.mSpeechPart = speechPart;
    }

    @Ignore
    protected Word(Parcel in) {
        mWord = in.readString();
        mDefinition = in.readString();
        mSpeechPart = SpeechPartConverter.toSpeechPart(in.readInt());
    }

    public String getWord() {
        return mWord;
    }


    public String getDefinition() {
        return mDefinition;
    }

    @NonNull
    public SpeechPart getSpeechPart() {
        return mSpeechPart;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mWord);
        dest.writeString(mDefinition);
        dest.writeInt(SpeechPartConverter.toInteger(mSpeechPart));
    }


    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

}
