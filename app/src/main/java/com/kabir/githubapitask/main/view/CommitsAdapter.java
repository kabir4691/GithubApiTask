package com.kabir.githubapitask.main.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kabir.githubapitask.R;
import com.kabir.githubapitask.main.model.CommitItem;
import com.kabir.githubapitask.util.ListUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kabir on 27-06-2016.
 */
public class CommitsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;

    private List<CommitItem> itemList;

    public CommitsAdapter(@NonNull Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommitViewHolder(layoutInflater.inflate(R.layout.commit_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        CommitViewHolder viewHolder = (CommitViewHolder) holder;
        CommitItem commitItem = itemList.get(position);

        if (commitItem == null) {
            return;
        }

        viewHolder.authorTextView.setText(commitItem.getAuthor());
        viewHolder.shaTextView.setText(commitItem.getShaDisplayText());
        viewHolder.messageTextView.setText(commitItem.getMessage());
    }

    @Override
    public int getItemCount() {
        return ListUtils.isNullOrEmpty(itemList) ? 0 : itemList.size();
    }

    public class CommitViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.author_text_view) TextView authorTextView;
        @Bind(R.id.sha_text_view) TextView shaTextView;
        @Bind(R.id.message_text_view) TextView messageTextView;

        public CommitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateDataSet(@Nullable List<CommitItem> list) {
        itemList = list;
        notifyDataSetChanged();
    }
}
