package com.pace.cs639spring.hw5;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CreatedTabsAdapter extends BaseAdapter {

    Context mContext;
    private List<TabInformation> mTabsInformation;

    private View.OnClickListener mClickListener;


    public CreatedTabsAdapter(Context context, View.OnClickListener listener) {
        mContext = context;
        mTabsInformation = new ArrayList<>();
        mClickListener = listener;
    }


    public void addNewTab(TabInformation tabInfo) {
        mTabsInformation.add(tabInfo);
    }

    public void removeAllTabInformation() {
        mTabsInformation.clear();
    }

    public List<TabInformation> getTabInformation() {
        return mTabsInformation;
    }

    @Override
    public int getCount() {
        return mTabsInformation.size();
    }

    @Override
    public Object getItem(int position) {
        return mTabsInformation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item, null);

            ViewHolder viewHolder = new ViewHolder(convertView.findViewById(R.id.image),
                    convertView.findViewById(R.id.name), convertView.findViewById(R.id.bio),
                    convertView.findViewById(R.id.check_text), convertView.findViewById(R.id.checkbox));
            convertView.setTag(viewHolder);
            viewHolder.mCheckBox.setOnClickListener(mClickListener);
        }
        TabInformation personalInfo = (TabInformation) getItem(position);

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.mName.setText(personalInfo.mName);
        viewHolder.mBio.setText(personalInfo.mBiography);
        viewHolder.mCheckBox.setChecked(personalInfo.mIncludeImage);
        viewHolder.mCheckBox.setTag(position);

        boolean hasImage = personalInfo.mImageResource > 0;
        boolean showImage = personalInfo.mIncludeImage && hasImage;
        if (showImage) viewHolder.mImageView.setImageResource(personalInfo.mImageResource);
        viewHolder.mImageView.setVisibility(showImage ? View.VISIBLE : View.GONE);

        //only show these elements if an image has been included in TabInformation
        viewHolder.mIncludeImageText.setVisibility(hasImage ? View.VISIBLE : View.INVISIBLE);
        viewHolder.mCheckBox.setVisibility(hasImage ? View.VISIBLE : View.INVISIBLE);

        return convertView;
    }

    class ViewHolder {

        public ImageView mImageView;
        public TextView mName;
        public TextView mBio;
        public TextView mIncludeImageText;
        public CheckBox mCheckBox;


        public ViewHolder(ImageView imageView, TextView name, TextView bio, TextView includeImageText, CheckBox checkBox) {
            mImageView = imageView;
            mName = name;
            mBio = bio;
            mIncludeImageText = includeImageText;
            mCheckBox = checkBox;
        }
    }
}
