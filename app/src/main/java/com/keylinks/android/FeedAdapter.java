package com.keylinks.android;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;




class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);
        return new FeedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder feedHolder, int i) {
        //用户头像
        Glide.with(feedHolder.itemView.getContext())
                .load(getAvatarResId(i))

                .into(feedHolder.mIvAvatar);

        //内容图片
        Glide.with(feedHolder.itemView.getContext())
                .load(getContentResId(i))

                .into(feedHolder.mIvContent);

        //nickname
        feedHolder.mTvNickname.setText("NetEase " + i);
    }

    public int getContentResId(int position){
        switch (position % 4){
            case 0:
                return R.drawable.taeyeon_one;
            case 1:
                return R.drawable.taeyeon_two;
            case 2:
                return R.drawable.taeyeon_three;
            case 3:
                return R.drawable.taeyeon_four;
        }
        return 0;
    }

    private int getAvatarResId(int position){
        switch (position % 4){
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }
    @Override
    public int getItemCount() {
        return 30;
    }

    public class FeedHolder extends RecyclerView.ViewHolder{

        ImageView mIvAvatar;
        ImageView mIvContent;
        TextView mTvNickname;
        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            mIvAvatar = itemView.findViewById(R.id.iv_avatar);
            mIvContent = itemView.findViewById(R.id.iv_content);
            mTvNickname = itemView.findViewById(R.id.tv_nickname);
        }
    }
}
