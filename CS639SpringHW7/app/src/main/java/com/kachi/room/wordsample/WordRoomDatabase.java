package com.kachi.room.wordsample;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

@Database(entities = {Word.class}, version = 2)
@TypeConverters(SpeechPartConverter.class)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static WordRoomDatabase sInstance;

    public static WordRoomDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (WordRoomDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database").addCallback(sRoomDatabaseCallback).
                            addMigrations(MIGRATION_1_2).build(); //add migration when creating DB
                }
            }
        }
        return sInstance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(sInstance).execute();

        }
    };

    public abstract WordDao wordDao();


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();
            Word word = new Word("aardvark", "a large, nocturnal, burrowing mammal, Orycteropus afer, of central and southern Africa, feeding on ants and termites and having a long, extensile tongue, strong claws, and long ears.", SpeechPart.NOUN);
            mDao.insert(word);
            word = new Word("abacterial", "not caused by or free from the presence of bacteria", SpeechPart.ADJECTIVE);
            mDao.insert(word);
            return null;
        }
    }

    //creating a Migration object that specifies what queries are needed to migrate from version 1 to version 2
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //alters our word_table database and adds two new columns, definition & part_of_speech
            //must specify a default value that existing records will use
            database.execSQL("ALTER TABLE word_table ADD COLUMN definition TEXT NOT NULL DEFAULT \"\"");
            database.execSQL("ALTER TABLE word_table ADD COLUMN part_of_speech INTEGER NOT NULL DEFAULT 1");
        }
    };

}
