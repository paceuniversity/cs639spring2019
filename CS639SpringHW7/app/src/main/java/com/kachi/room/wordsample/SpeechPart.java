package com.kachi.room.wordsample;

/**
 * Enumaration used to represent the parts of speech in a word
 */
public enum SpeechPart {
    NOUN(0, "n."),
    ADJECTIVE(1, "adj."),
    ADVERB(2, "adv."),
    PREPOSITION(3, "prep.");

    private int mValue;
    private String mAbbreviation;

    SpeechPart(int value, String abbreviation) {
        this.mValue = value;
        this.mAbbreviation = abbreviation;
    }

    public int getValue() {
        return mValue;
    }

    public String getAbbreviation() {
        return mAbbreviation;
    }
}
