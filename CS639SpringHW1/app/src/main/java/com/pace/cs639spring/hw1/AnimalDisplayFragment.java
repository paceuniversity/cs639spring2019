package com.pace.cs639spring.hw1;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by kachi on 1/31/18.
 */

public class AnimalDisplayFragment extends Fragment {

    //member variables that are going to be accessed often throughout program
    ImageView mSelectedImage;
    View mBirdDescription;
    View mCatDescription;
    View mDogDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate animal_display.xml
        View view = inflater.inflate(R.layout.animal_display, container, false);
        //find the views that are going to be accessed often only once in onCreateView
        //assign those views to member variables so we can reference them as many times as we want later on
        mBirdDescription = view.findViewById(R.id.bird_description);
        mCatDescription = view.findViewById(R.id.cat_description);
        mDogDescription = view.findViewById(R.id.dog_description);
        return view; //return the view that we inflated. It's going to be used as the main view of this fragment
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //once activity is created, lets attach the listeners.
        addImageClickListeners();
        addButtonClickListeners();
    }

    private void addImageClickListeners() {
        /**
         * Create a lamba that'll be used for our onClickListener.
         * See: https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
         *
         * In Java 8, lambda's can be used in place of anonymous classes.
         * See: https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html
         * Also: https://stackoverflow.com/questions/355167/how-are-anonymous-inner-classes-used-in-java
         */
        View.OnClickListener imageListener = view -> {
            //more information on ternary operator: https://www.sitepoint.com/java-ternary-operator/
            mSelectedImage = mSelectedImage == view ? null : (ImageView) view;
            int selectedImageId = mSelectedImage == null ? 0 : mSelectedImage.getId();
            //show the animal description if the animal's image id = selectedImageId. If not, hide it.
            mBirdDescription.setVisibility(selectedImageId == R.id.bird ? View.VISIBLE : View.INVISIBLE);
            mCatDescription.setVisibility(selectedImageId == R.id.cat ? View.VISIBLE : View.INVISIBLE);
            mDogDescription.setVisibility(selectedImageId == R.id.dog ? View.VISIBLE : View.INVISIBLE);
        };

        //add above listener to animal images
        getView().findViewById(R.id.bird).setOnClickListener(imageListener);
        getView().findViewById(R.id.cat).setOnClickListener(imageListener);
        getView().findViewById(R.id.dog).setOnClickListener(imageListener);
    }

    private void addButtonClickListeners() {
        getView().findViewById(R.id.rotate).setOnClickListener(view -> {
            if (isInputSelected()) mSelectedImage.setRotation(mSelectedImage.getRotation() + 90);
        });

        getView().findViewById(R.id.flip).setOnClickListener(view -> {
            if (isInputSelected()) mSelectedImage.setScaleX(mSelectedImage.getScaleX() * -1);
        });

        getView().findViewById(R.id.left).setOnClickListener(view -> {
            if (isInputSelected()) {
                mSelectedImage.setTranslationX(mSelectedImage.getTranslationX() - dpToPx(10));
            }
        });

        getView().findViewById(R.id.up).setOnClickListener(view -> {
            if (isInputSelected()) {
                mSelectedImage.setTranslationY(mSelectedImage.getTranslationY() - dpToPx(10));
            }
        });

        getView().findViewById(R.id.right).setOnClickListener(view -> {
            if (isInputSelected()) {
                mSelectedImage.setTranslationX(mSelectedImage.getTranslationX() + dpToPx(10));
            }
        });

        getView().findViewById(R.id.down).setOnClickListener(view -> {
            if (isInputSelected()) {
                mSelectedImage.setTranslationY(mSelectedImage.getTranslationY() + dpToPx(10));
            }
        });

        getView().findViewById(R.id.center).setOnClickListener(view -> {
            if (isInputSelected()) {
                mSelectedImage.setTranslationX(0);
                mSelectedImage.setTranslationY(0);
            }
        });
    }

    private boolean isInputSelected() {
        if (mSelectedImage == null) {
            Toast.makeText(getActivity(), R.string.please_select_an_animal_image_before_choosing_an_image_modification,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private float dpToPx(int dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
