package com.pace.cs639spring.hw2;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AnimalDisplayListViewAdapter extends BaseAdapter {

    Context mContext;
    List<AnimalDisplay> mAnimalDisplays;
    //the button click listener that's going to be used called every time a user clicks on
    //the Next Fact button or the Delete Fact button. Both buttons will share the same onClickListener.
    //We will distinguish between which button is clicked by checking for the button id
    View.OnClickListener mButtonClickListener;

    //the currently selected position. Initialize to some negative value
    //AdapterView.INVALID_POSITION and Adapter.NO_SELECTION are both negative numbers, so either one
    //would work as an initial value
    private int mSelectedPosition = Adapter.NO_SELECTION;

    AnimalDisplayListViewAdapter(Context context, List<AnimalDisplay> animalDisplays, View.OnClickListener buttonClickListener) {
        mContext = context;
        mAnimalDisplays = animalDisplays;
        mButtonClickListener = buttonClickListener;
    }



    @Override
    public int getCount() {
        return mAnimalDisplays.size();
    }

    @Override
    public Object getItem(int position) {
        return mAnimalDisplays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item, null);
            ViewHolder viewHolder = new ViewHolder(convertView.findViewById(R.id.animal_icon),
                    convertView.findViewById(R.id.animal_description), convertView.findViewById(R.id.next_fact_button),
                    convertView.findViewById(R.id.delete_fact_button));
            //assign button click listener to both mNextButton & mDeleteButton
            viewHolder.mNextButton.setOnClickListener(mButtonClickListener);
            viewHolder.mDeleteButton.setOnClickListener(mButtonClickListener);
            convertView.setTag(viewHolder);
        }

        AnimalDisplay animalDisplay = (AnimalDisplay) getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mImageView.setImageResource(animalDisplay.getIcon());
        applyTransformToImage(viewHolder.mImageView, animalDisplay.getImageTransformation());
        viewHolder.mDescriptionTextView.setText(
                mContext.getString(R.string.name_return_description,
                        animalDisplay.getName(), animalDisplay.getCurrentFunFact()));
        //if this position is currently selected, show the description, else hide it
        viewHolder.mDescriptionTextView.setVisibility(position == mSelectedPosition ? View.VISIBLE : View.GONE);

        //set the visibility of the next & delete buttons to whatever the visibility for the
        //description is since they should always match based on requirements for the assignment
        viewHolder.mNextButton.setVisibility(viewHolder.mDescriptionTextView.getVisibility());
        //store the position associated w/ this particular button as this view's tag!
        viewHolder.mNextButton.setTag(position);
        viewHolder.mDeleteButton.setVisibility(viewHolder.mDescriptionTextView.getVisibility());
        //store the position associated w/ this particular button as this view's tag!
        viewHolder.mDeleteButton.setTag(position);
        return convertView;
    }

    private void applyTransformToImage(ImageView imageView, AnimalDisplay.ImageTransformation transformation) {
        imageView.setRotation(transformation.mRotation);
        imageView.setScaleX(transformation.mScaleX);
        imageView.setTranslationX(transformation.mTranslationX);
        imageView.setTranslationY(transformation.mTranslationY);
    }

    public void setSelectedPosition(int position) {
        //if the selected position is already selected, unselect it by setting mSelectedPosition to
        //Adapter.NO_SELECTION, else set mSelectedPosition to position
        mSelectedPosition = position == mSelectedPosition ? Adapter.NO_SELECTION : position;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public boolean isPositionSelected() {
        return mSelectedPosition != Adapter.NO_SELECTION;
    }

    private class ViewHolder {

        ImageView mImageView;
        TextView mDescriptionTextView;
        Button mNextButton;
        Button mDeleteButton;

        ViewHolder(ImageView imageView, TextView descriptionTextView, Button nextButton, Button deleteButton) {
            mImageView = imageView;
            mDescriptionTextView = descriptionTextView;
            mNextButton = nextButton;
            mDeleteButton = deleteButton;
        }
    }
}
