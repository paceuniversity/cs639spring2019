package com.pace.cs639spring.hw5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class DisplayTabsActivity extends AppCompatActivity implements TabbedContentFragment.ButtonClickListener {

    static final String ARG_IMAGE = "image";
    static final String ARG_NAME = "name";
    static final String ARG_BIOGRAPHY = "biography";
    static final String ARG_CURRENT_TAB_CONTENT_INDEX = "current_tab_index";
    static final String ARG_LAST_TAB_INDEX = "last_tab_index";

    //list that is going to hold all the tab information we received from the first activity
    List<TabInformation> mList;

    //the LinearLayout that stores all of the tabs at the bottom
    LinearLayout mTabsLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_tabs);

        mList = getIntent().getParcelableArrayListExtra(CreateTabsActivity.TAB_CONTENT);


        mTabsLayout = findViewById(R.id.contact_tabs);

        //in the display_tabs layout, all tabs have a visibility of GONE by default.
        //what we're going to do here is set the visibility of N number of tabs in mTabsLayout to VISIBLE
        //where N is the number of items in mList. So if the user created 1 tab, we'll set 1 tab in
        //mTabsLayout to VISIBLE. If the user created 2 tabs, we'll set 2 tabs in mTabsLayout to VISIBLE, etc
        int array[] = {R.id.tab_1, R.id.tab_2, R.id.tab_3, R.id.tab_4};
        for (int i = 0; i < mList.size(); i++) {
            ViewGroup child = mTabsLayout.findViewById(array[i]);
            ((TextView) child.getChildAt(0)).setText(mList.get(i).mName);
            mTabsLayout.findViewById(array[i]).setVisibility(View.VISIBLE);
        }

        //make sure the user see's the first tab's content when this screen is launched
        switchToTabContent(0);
    }

    public void onTabClicked(View view) {
        //get the index of the tab that was clicked. Use that index to get the appropriate
        //TabInformation object
        int index = mTabsLayout.indexOfChild(view);
        switchToTabContent(index);
    }

    @Override
    public void onNextClicked(int index) {
        switchToTabContent(index + 1); //index + 1 is next tab content
    }

    @Override
    public void onPreviousClicked(int index) {
        switchToTabContent(index - 1); //index - 1 is previous tab content
    }

    private void switchToTabContent(int index) {
        TabInformation info = mList.get(index);

        //create new Bundle to pass new arguments to Fragment we are about to create
        //I could've very easily just used args.putParcelable(info) here instead of
        //breaking it down into ARG_IMAGE, ARG_NAME, ARG_BIOGRAPHY, but I wanted to do this
        //instead for demonstration purposes
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE, info.mIncludeImage ? info.mImageResource : 0);
        args.putString(ARG_NAME, info.mName);
        args.putString(ARG_BIOGRAPHY, info.mBiography);
        args.putInt(ARG_CURRENT_TAB_CONTENT_INDEX, index);
        args.putInt(ARG_LAST_TAB_INDEX, mList.size() - 1);

        //create *NEW* fragment and pass it in the data from the Bundle.
        TabbedContentFragment fragment = new TabbedContentFragment();
        fragment.setArguments(args);
        //replace the existing Fragment in container w/ this new Fragment
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

}