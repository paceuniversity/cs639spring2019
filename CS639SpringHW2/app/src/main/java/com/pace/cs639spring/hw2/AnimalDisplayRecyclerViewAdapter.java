package com.pace.cs639spring.hw2;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AnimalDisplayRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<AnimalDisplay> mAnimalDisplays;
    //the button click listener that's going to be used called every time a user clicks on
    //the Next Fact button or the Delete Fact button. Both buttons will share the same onClickListener.
    //We will distinguish between which button is clicked by checking for the button id
    View.OnClickListener mClickListener;

    private int mSelectedPosition = Adapter.NO_SELECTION;

    public AnimalDisplayRecyclerViewAdapter(Context context, List<AnimalDisplay> animalDisplays, View.OnClickListener listener) {
        mContext = context;
        mAnimalDisplays = animalDisplays;
        mClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.list_item, null);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        //assign button click listener to both mNextButton & mDeleteButton
        holder.mNextButton.setOnClickListener(mClickListener);
        holder.mDeleteButton.setOnClickListener(mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AnimalDisplay animalDisplay = mAnimalDisplays.get(position);

        RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
        viewHolder.mImageView.setImageResource(animalDisplay.getIcon());
        applyTransformToImage(viewHolder.mImageView, animalDisplay.getImageTransformation());
        viewHolder.mDescriptionTextView.setText(mContext.getString(R.string.name_return_description,animalDisplay.getName(), animalDisplay.getCurrentFunFact()));
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

        viewHolder.itemView.setOnClickListener(mClickListener);
        viewHolder.itemView.setTag(position);

    }

    private void applyTransformToImage(ImageView imageView, AnimalDisplay.ImageTransformation transformation) {
        imageView.setRotation(transformation.mRotation);
        imageView.setScaleX(transformation.mScaleX);
        imageView.setTranslationX(transformation.mTranslationX);
        imageView.setTranslationY(transformation.mTranslationY);
    }

    @Override
    public int getItemCount() {
        return mAnimalDisplays.size();
    }

    public AnimalDisplay getItem(int position) {
        return mAnimalDisplays.get(position);
    }

    public void setSelectedPosition(int position) {
        //if the selected position is already selected, unselect it by setting mSelectedPosition to
        //Adapter.NO_SELECTION, else set mSelectedPosition to position
        mSelectedPosition =  position ==  mSelectedPosition ? Adapter.NO_SELECTION : position;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public boolean isPositionSelected() {
        return mSelectedPosition != Adapter.NO_SELECTION;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mDescriptionTextView;
        Button mNextButton;
        Button mDeleteButton;

        RecyclerViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.animal_icon);
            mDescriptionTextView = view.findViewById(R.id.animal_description);
            mNextButton = view.findViewById(R.id.next_fact_button);
            mDeleteButton = view.findViewById(R.id.delete_fact_button);
        }
    }
}
