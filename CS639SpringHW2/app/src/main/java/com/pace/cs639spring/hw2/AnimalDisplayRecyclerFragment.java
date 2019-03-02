package com.pace.cs639spring.hw2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.pace.cs639spring.hw2.AnimalDisplay.*;

/**
 * Homework 2 Implementation using a RecyclerView
 */

public class AnimalDisplayRecyclerFragment extends Fragment {

    RecyclerView mRecyclerView;
    AnimalDisplayRecyclerViewAdapter mAdapter;
    EditText mNewFunFactText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animal_display_recyclerview, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mNewFunFactText = view.findViewById(R.id.fun_fact_edit_text);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureListView();
        configureFunFactAdditionArea();
        addButtonClickListeners();
    }

    private void configureListView() {
        List<AnimalDisplay> animalDisplays = new ArrayList<>(10);
        animalDisplays.add(new AnimalDisplay(R.drawable.bird, getString(R.string.bird_title), getString(R.string.bird_description)));
        animalDisplays.add(new AnimalDisplay(R.drawable.cat, getString(R.string.cat_title), getString(R.string.cat_description)));
        animalDisplays.add(new AnimalDisplay(R.drawable.dog, getString(R.string.dog_title), getString(R.string.dog_description)));
        animalDisplays.add(new AnimalDisplay(R.drawable.fish, getString(R.string.fish_title), getString(R.string.fish_description)));
        animalDisplays.add(new AnimalDisplay(R.drawable.lizard, getString(R.string.lizard_title), getString(R.string.lizard_description)));
        animalDisplays.add(new AnimalDisplay(R.drawable.rabbit, getString(R.string.rabbit_title), getString(R.string.rabbit_description)));
        animalDisplays.add(new AnimalDisplay(R.drawable.rat, getString(R.string.rat_title), getString(R.string.rat_description)));
        animalDisplays.add(new AnimalDisplay(R.drawable.snake, getString(R.string.snake_title), getString(R.string.snake_description)));
        animalDisplays.add(new AnimalDisplay(R.drawable.squirrel, getString(R.string.squirrel_title), getString(R.string.squirrel_description)));
        animalDisplays.add(new AnimalDisplay(R.drawable.turtle, getString(R.string.turtle_title), getString(R.string.turtle_description)));

        //Instead of creating a new onClickListener every time a "Next Fact" & "Delete Fact"
        //button gets created, I'm instead going to create one onClickListener and pass it into
        //the adapter's constructor. This onClickListener will then be added to every
        //"Next Fact" & "Delete Fact" that gets created by the list.
        mAdapter = new AnimalDisplayRecyclerViewAdapter(getActivity(), animalDisplays, v -> {
            //retrieve the corresponding position for this button (see: AnimalDisplayListViewAdapter)
            //this will tell us what row this button belongs to. Use that information to get the
            //appropriate AnimalDisplay object from the list
            int position = (int) v.getTag();
            AnimalDisplay display = mAdapter.getItem(position);

            //examine the view id to find out which button was clicked. If the Next Fact button
            //was clicked on execute the code in the nextFunFact() method, else if the Delete Fact
            //button was clicked on execute the code in the deleteFunFact() method
            switch (v.getId()) {
                case R.id.row:
                    mAdapter.setSelectedPosition(position);
                    break;
                case R.id.next_fact_button:
                    nextFunFact(display);
                    break;
                case R.id.delete_fact_button:
                    deleteFunFact(display);
                    break;
            }
        });

        //apply LinearLayoutManager so that the RecyclerView is laid out like a list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Increase the fun fact index of the supplied AnimalDisplay object and notify the adapter of changes
     * @param display object to modify
     */
    private void nextFunFact(AnimalDisplay display) {
        display.incrementFunFactIndex();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Tries to delete the fun fact at the current index. If there's only one fun fact,
     * then let the user know via a toast message.
     * @param display object to modify
     */
    private void deleteFunFact(AnimalDisplay display) {
        boolean success = display.removeCurrentFunFact();
        if (success) mAdapter.notifyDataSetChanged();
        else {
            Toast.makeText(getActivity(), R.string.could_not_remove_fun_fact_at_least_one_needed, Toast.LENGTH_LONG).show();
        }
    }

    private void configureFunFactAdditionArea() {
        getView().findViewById(R.id.bttn_add).setOnClickListener(v -> {
            if (mNewFunFactText.getText().toString().trim().isEmpty()) { //check if fun fact is blank
                Toast.makeText(getActivity(), R.string.fun_fact_text_cannot_be_blank, Toast.LENGTH_LONG).show();
            } else if (!mAdapter.isPositionSelected()) { //make sure row is selected
                Toast.makeText(getActivity(), R.string.please_select_an_animal_image_before_adding_fun_fact,
                        Toast.LENGTH_LONG).show();
            } else {
                AnimalDisplay display = mAdapter.getItem(mAdapter.getSelectedPosition());
                display.addFunFact(mNewFunFactText.getText().toString());
                mNewFunFactText.setText("");
                mAdapter.notifyDataSetChanged(); //must notify adapter of changes
            }
        });
    }

    private void addButtonClickListeners() {
        //rotate click listener
        getView().findViewById(R.id.rotate).setOnClickListener(view -> {
            if (isInputSelected()) {
                AnimalDisplay display = mAdapter.getItem(mAdapter.getSelectedPosition());
                ImageTransformation transformation = display.getImageTransformation();
                transformation.mRotation += 90;
                mAdapter.notifyDataSetChanged();
            }
        });

        //flip click listener
        getView().findViewById(R.id.flip).setOnClickListener(view -> {
            if (isInputSelected()) {
                AnimalDisplay display = mAdapter.getItem(mAdapter.getSelectedPosition());
                ImageTransformation transformation = display.getImageTransformation();
                transformation.mScaleX *= -1;
                mAdapter.notifyDataSetChanged();
            }
        });

        //translation click listeners
        getView().findViewById(R.id.left).setOnClickListener(view -> {
            if (isInputSelected()) {
                AnimalDisplay display = mAdapter.getItem(mAdapter.getSelectedPosition());
                ImageTransformation transformation = display.getImageTransformation();
                transformation.mTranslationX -= dpToPx(10);
                mAdapter.notifyDataSetChanged();
            }
        });

        getView().findViewById(R.id.up).setOnClickListener(view -> {
            if (isInputSelected()) {
                AnimalDisplay display = mAdapter.getItem(mAdapter.getSelectedPosition());
                ImageTransformation transformation = display.getImageTransformation();
                transformation.mTranslationY -= dpToPx(10);
                mAdapter.notifyDataSetChanged();
            }
        });

        getView().findViewById(R.id.right).setOnClickListener(view -> {
            if (isInputSelected()) {
                AnimalDisplay display = mAdapter.getItem(mAdapter.getSelectedPosition());
                ImageTransformation transformation = display.getImageTransformation();
                transformation.mTranslationX += dpToPx(10);
                mAdapter.notifyDataSetChanged();
            }
        });

        getView().findViewById(R.id.down).setOnClickListener(view -> {
            if (isInputSelected()) {
                AnimalDisplay display = mAdapter.getItem(mAdapter.getSelectedPosition());
                ImageTransformation transformation = display.getImageTransformation();
                transformation.mTranslationY += dpToPx(10);
                mAdapter.notifyDataSetChanged();
            }
        });

        getView().findViewById(R.id.center).setOnClickListener(view -> {
            if (isInputSelected()) {
                AnimalDisplay display = mAdapter.getItem(mAdapter.getSelectedPosition());
                ImageTransformation transformation = display.getImageTransformation();
                transformation.mTranslationX = 0;
                transformation.mTranslationY = 0;
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private boolean isInputSelected() {
        if (mAdapter.isPositionSelected()) return true;
        else {
            Toast.makeText(getActivity(), R.string.please_select_an_animal_image_before_choosing_an_image_modification,
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private float dpToPx(int dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
