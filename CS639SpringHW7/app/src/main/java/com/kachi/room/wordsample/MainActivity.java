package com.kachi.room.wordsample;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String NEW_WORD_NOTIFICATION_CHANNEL_ID = "new_word_notification_channel_id";
    private static final int NEW_WORD_NOTIFICATION_ID = 1001; //random number for notification id

    //request code to be used when the ModifyWordActivity is being used to add a new word to dictionary
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    //request code to be used when the ModifyWordActivity is being used to update a word in the dictionary
    private static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    private WordViewModel mWordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this, new RecyclerViewClickListener() {
            @Override
            public void onViewHolderClick(RecyclerView.ViewHolder viewHolder, int position) {
                WordListAdapter adapter = (WordListAdapter) recyclerView.getAdapter();
                Word word = adapter.getWord(position);
                //add the word in the specified position to the Intent used to start ths ModifyWordActivity
                Intent intent = new Intent(MainActivity.this, ModifyWordActivity.class).
                        putExtra(ModifyWordActivity.EXTRA_WORD, word);
                startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //starts the ModifyWordActivity without passing a word to the Intent. This signifies that
                //the user wants to create a new word, and not update an existing word
                Intent intent = new Intent(MainActivity.this, ModifyWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                adapter.setWords(words);
            }
        });
    }

    private void createNotificationChannel() {
        String name = getString(R.string.new_words);
        String description = getString(R.string.new_words_channel_description);

        NotificationChannel channel = new NotificationChannel(NEW_WORD_NOTIFICATION_CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(description);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.createNotificationChannel(channel);
    }

    private void createNewWordNotification(Word word) {
        String notificationText = getString(R.string.new_word_added_notification_content,
                word.getWord(), word.getSpeechPart().getAbbreviation(), word.getDefinition());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NEW_WORD_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_book).setContentTitle(getString(R.string.new_word_added_notification_title))
                .setContentText(notificationText).setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NEW_WORD_NOTIFICATION_ID, builder.build());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the result is okay, then proceed
        if (resultCode == RESULT_OK) {
            //if we were adding a new word to the database, then call insert, else call update
            if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE) {
                Word word = data.getParcelableExtra(ModifyWordActivity.EXTRA_WORD);
                mWordViewModel.insert(word);
                createNewWordNotification(word);
            } else if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE) {
                Word word = data.getParcelableExtra(ModifyWordActivity.EXTRA_WORD);
                mWordViewModel.update(word);
            }
        }
    }
}
