package com.pace.cs639spring.hw5;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TabbedContentFragment extends Fragment {

    public interface ButtonClickListener {
        void onPreviousClicked(int index);
        void onNextClicked(int index);
    }


    ButtonClickListener mButtonClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mButtonClickListener = (ButtonClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implement ButtonClickListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();

        //get the name and bio from the arguments and set the appropriate textviews to their values
        int image = args.getInt(DisplayTabsActivity.ARG_IMAGE);
        final ImageView imageView = (ImageView) getView().findViewById(R.id.image);
        imageView.setImageResource(image);
        imageView.setVisibility(image > 0 ? View.VISIBLE : View.INVISIBLE);
        ((TextView)getView().findViewById(R.id.name)).setText(args.getString(DisplayTabsActivity.ARG_NAME));
        ((TextView)getView().findViewById(R.id.bio)).setText(args.getString(DisplayTabsActivity.ARG_BIOGRAPHY));


        //the index of the tab that is currently selected
        final int currentTabIndex = args.getInt(DisplayTabsActivity.ARG_CURRENT_TAB_CONTENT_INDEX);
        //the index of the last tab
        int highestTabIndex = args.getInt(DisplayTabsActivity.ARG_LAST_TAB_INDEX);


        //configure the next button
        View nextButton = getView().findViewById(R.id.bttn_next);
        //if the index of the currently selected tab is equal to the index of the last tab,
        //then don't show the Next button, since there is essentially no "Next" to go to.
        nextButton.setVisibility(currentTabIndex == highestTabIndex ? View.GONE : View.VISIBLE);
        nextButton.setOnClickListener(v -> {
            //call our interface and pass in the value of the currently selected tab.
            //Knowing which tab we pressed next from will give us an idea of what tab to go to
            mButtonClickListener.onNextClicked(currentTabIndex);
        });

        //configure the previous button
        View prevButton = getView().findViewById(R.id.bttn_prev);
        //if the index of the currently selected tab is equal to 0,
        //then don't show the Previous button, since there is essentially no "Previous" to go to.
        prevButton.setVisibility(currentTabIndex == 0 ? View.GONE : View.VISIBLE);

        prevButton.setOnClickListener(v -> {
            //call our interface and pass in the value of the currently selected tab.
            //Knowing which tab we pressed previous from will give us an idea of what tab to go to
            mButtonClickListener.onPreviousClicked(currentTabIndex);
        });




    }

}
