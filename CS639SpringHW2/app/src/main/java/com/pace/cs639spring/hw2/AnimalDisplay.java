package com.pace.cs639spring.hw2;

import android.graphics.Color;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.List;


public class AnimalDisplay {

    private int mIcon;
    private ImageTransformation mTransformation; //object that represents the image transform
    private String mName;

    private List<String> mFunFacts; //a list of fun facts associated with the AnimalDisplay
    private int mFunFactIndex; //the index of the currently selected fun fact

    public AnimalDisplay(int icon, String name, String initialFunFact) {
        mIcon = icon;
        mTransformation = new ImageTransformation();
        mName = name;
        mFunFacts = new ArrayList<>();
        mFunFacts.add(initialFunFact);
        mFunFactIndex = 0;
    }

    int getIcon() {
        return mIcon;
    }

    ImageTransformation getImageTransformation() {
        return mTransformation;
    }

    String getName() {
        return mName;
    }

    String getCurrentFunFact() {
        return mFunFacts.get(mFunFactIndex);
    }

    void addFunFact(String funFact) {
        mFunFacts.add(funFact);
    }


    /**
     * If there is not more than one fact, then deletion is not possible and this method will return
     * false. Else, remove the fun fact at the current index.
     *
     * If mFunFactIndex is greater than the total amount of fun facts - 1 after a deletion occurs,
     * set the mFunFactIndex back to 0 so we don't get an IndexOutOfBounds exception
     */
    boolean removeCurrentFunFact() {
        if (mFunFacts.size() <= 1) return false;

        mFunFacts.remove(mFunFactIndex);
        if (mFunFactIndex > mFunFacts.size() - 1) mFunFactIndex = 0;
        return true;
    }

    /**
     * Increments the current fun fact index by one. If we're already pointing to the last fun fact
     * set the index back to 0 so it'll point to the beginning
     */
    void incrementFunFactIndex() {
        if (mFunFactIndex == mFunFacts.size() - 1) mFunFactIndex = 0;
        else mFunFactIndex++;
    }

    static class ImageTransformation {
        float mScaleX = 1;
        float mTranslationX = 0;
        float mTranslationY = 0;
        float mRotation = 0;
    }
}
