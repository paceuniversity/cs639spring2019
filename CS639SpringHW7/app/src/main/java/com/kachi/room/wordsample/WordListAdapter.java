package com.kachi.room.wordsample;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView mWordItemView;

        private WordViewHolder(View itemView, final RecyclerViewClickListener listener) {
            super(itemView);
            mWordItemView = itemView.findViewById(R.id.textView);
            mWordItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onViewHolderClick(WordViewHolder.this, getAdapterPosition());
                }
            });
        }
    }

    Context mContext;
    private List<Word> mWords;
    private RecyclerViewClickListener mClickListener;

    WordListAdapter(Context context, RecyclerViewClickListener listener) {
        mContext = context;
        mClickListener = listener;
    }


    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WordViewHolder(View.inflate(mContext, R.layout.recyclerview_item, null), mClickListener);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mWords == null) {
            holder.mWordItemView.setText(R.string.no_word);
        } else {
            Word current = mWords.get(position);
            String label = String.format("%1$s (%2$s) - %3$s", current.getWord(), current.getSpeechPart().getAbbreviation(),
                    current.getDefinition());
            holder.mWordItemView.setText(label);
        }
    }


    void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    Word getWord(int position) {
        return position < mWords.size() ? mWords.get(position) : null;
    }

    @Override
    public int getItemCount() {
        if (mWords == null) return 0;
        else return mWords.size();
    }



}
