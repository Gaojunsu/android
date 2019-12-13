package com.keylinks.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keylinks.android.annotation.LoginBehavior;



public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FeedAdapter mFeedAdapter;
    private RelativeLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private ImageView mSuspensionIv;
    private int mSuspensionBarHeight;
    private int mCurrentPosition;
    private FloatingActionButton mFloatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        immersive();
        setHeightAndPadding(this,findViewById(R.id.toolbar));
        mSuspensionBar = findViewById(R.id.suspension_bar);
        mSuspensionTv = findViewById(R.id.tv_nickname);
        mSuspensionIv = findViewById(R.id.iv_avatar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mFloatingActionButton= findViewById(R.id.jump);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mFeedAdapter=new FeedAdapter();
        mRecyclerView.setAdapter(mFeedAdapter);
        mRecyclerView.setHasFixedSize(true);
        //滚动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取悬浮条的高度
                mSuspensionBarHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //对悬浮条的位置进行调整
                //找到下一个itemView
                View view  = layoutManager.findViewByPosition(mCurrentPosition + 1);
                if (view!=null){
                    if (view.getTop()<=mSuspensionBarHeight){
                        //移动
                        mSuspensionBar.setY(-(mSuspensionBarHeight-view.getTop()));
                    }else{
                        //保持原来的位置
                        mSuspensionBar.setY(0);
                    }
                }
                if (mCurrentPosition!=layoutManager.findFirstVisibleItemPosition()){
                    mCurrentPosition=layoutManager.findFirstVisibleItemPosition();
                    updateSuspensionBar();
                }
            }
        });
        updateSuspensionBar();



        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump();
            }
        });

    }

    @LoginBehavior
    private void jump(){
        Intent intent = new Intent(MainActivity.this,PlayActivity.class);
        startActivity(intent);
    }


    private void immersive() {

        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.KITKAT){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色透明
            window.setStatusBarColor(Color.TRANSPARENT);
            int visibility = window.getDecorView().getSystemUiVisibility();
            //布局内容全屏显示
            visibility|=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            //隐藏虚拟导航栏
            visibility|=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            //防止内容区域发生变化
            visibility|=View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(visibility);
        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public int getStatusBarHeight(Context context){
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0){
            return context.getResources().getDimensionPixelSize(resId);
        }
        return 0;
    }

    public void setHeightAndPadding(Context context, View view){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height += getStatusBarHeight(context);
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context), view.getPaddingRight(), view.getPaddingBottom());
    }
    private void updateSuspensionBar() {

        Glide.with(this)
                .load(getAvatarResId(mCurrentPosition))

                .into(mSuspensionIv);
        mSuspensionTv.setText("NetEase "  + mCurrentPosition);
    }

    private int getAvatarResId(int mCurrentPosition) {

        switch (mCurrentPosition % 4){
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
}
