package com.kachi.room.wordsample;

import androidx.room.TypeConverter;

public class SpeechPartConverter {


        @TypeConverter
        public static SpeechPart toSpeechPart(int value) {
            if (value == SpeechPart.NOUN.getValue()) return SpeechPart.NOUN;
            else if (value == SpeechPart.ADJECTIVE.getValue()) return SpeechPart.ADJECTIVE;
            else if (value == SpeechPart.ADVERB.getValue()) return SpeechPart.ADVERB;
            else if (value == SpeechPart.PREPOSITION.getValue()) return SpeechPart.PREPOSITION;
            else {
                throw new IllegalArgumentException("Could not recognize speech part");
            }
        }

        @TypeConverter
        public static int toInteger(SpeechPart speechPart) {
            return speechPart.getValue();
        }
}
