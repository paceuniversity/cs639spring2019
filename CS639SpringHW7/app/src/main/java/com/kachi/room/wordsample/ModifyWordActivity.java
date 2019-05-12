package com.kachi.room.wordsample;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ModifyWordActivity extends AppCompatActivity {

    public static final String EXTRA_WORD = "com.kachi.room.wordsample.WORD";

    private EditText mEditWord;
    private EditText mEditDefinition;
    private RadioGroup mSpeechPartRadioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_word);
        mEditWord = findViewById(R.id.edit_word);
        mEditDefinition = findViewById(R.id.edit_definiton);
        mSpeechPartRadioGroup = findViewById(R.id.part_of_speech_group);

        //if the user passed in a word to the intent, then populate the fields with the word's values
        populateWithExistingWordIfPossible();


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptSave()) finish();
            }
        });

    }

    private void populateWithExistingWordIfPossible() {
        Word word = getIntent().getParcelableExtra(EXTRA_WORD);
        if (word != null) {
            //populate first edit text with the word
            mEditWord.setText(word.getWord());
            //disable the first edit text so it cannot be modified by user
            mEditWord.setEnabled(false);
            //populate the second edit text w/ the definition
            mEditDefinition.setText(word.getDefinition());
            //check the correct radio button based on part of speech
            mSpeechPartRadioGroup.check(speechPartToId(word.getSpeechPart()));
        }
    }

    /**
     *
     * @param id - the id of the currently checked radio button in the radio group
     * @return the correct part of speech based on the supplied id
     */
    private SpeechPart idToSpeechPart(int id) {
        switch (id) {
            case R.id.noun:
                return SpeechPart.NOUN;
            case R.id.adjective:
                return SpeechPart.ADJECTIVE;
            case R.id.adverb:
                return SpeechPart.ADVERB;
            case R.id.preposition:
                return SpeechPart.PREPOSITION;
            default:
                return null;
        }
    }

    /**
     *
     * @param speechPart - a part of speech to map to an id
     * @return the id in the radio group that maps to supplied part of speech
     */
    private int speechPartToId(SpeechPart speechPart) {
        switch (speechPart) {
            case NOUN:
                return R.id.noun;
            case ADJECTIVE:
                return R.id.adjective;
            case ADVERB:
                return R.id.adverb;
            case PREPOSITION:
                return R.id.preposition;
            default:
                return 0;
        }
    }

    private boolean attemptSave() {
        //check if fields are all populated and radio button is checked
        if (TextUtils.isEmpty(mEditWord.getText()) || TextUtils.isEmpty(mEditDefinition.getText()) ||
                mSpeechPartRadioGroup.getCheckedRadioButtonId() == 0) {
            Toast.makeText(ModifyWordActivity.this, R.string.please_fill_in_all_values_before_saving, Toast.LENGTH_LONG).show();
            return false;
        } else {
            //create word object based on populated fields
            String word = mEditWord.getText().toString();
            String definition = mEditDefinition.getText().toString();
            SpeechPart speechPart = idToSpeechPart(mSpeechPartRadioGroup.getCheckedRadioButtonId());
            Word fullWord = new Word(word, definition, speechPart);
            Intent intent = getIntent().putExtra(EXTRA_WORD, fullWord);
            setResult(RESULT_OK, intent);
            return true;
        }
    }
}
