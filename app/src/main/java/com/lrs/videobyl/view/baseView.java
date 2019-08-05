package com.lrs.videobyl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lrs.videobyl.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class baseView extends StandardGSYVideoPlayer {
    private static boolean progressSate = true;

    public baseView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public baseView(Context context) {
        super(context);
    }

    public baseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_standard;
    }

    //返回按钮显示隐藏
    public void setmBackVibi(int id) {
        mBackButton.setVisibility(id);
        mBackButton.requestLayout();
        mBackButton.invalidate();
    }

    //播放按键显示隐藏
    public void setStartVibi(int id) {
        mStartButton.setVisibility(id);
    }

    //封面显示隐藏
    public void setThumbVibi(int id) {
        mThumbImageView.setVisibility(id);
    }

    //加载框显示隐藏
    public void setLoadingProgressVibi(int id) {
        mLoadingProgressBar.setVisibility(id);
    }

    //进度条显示隐藏
    public void mProgressBarVibi(int id) {
        mProgressBar.setVisibility(id);
    }

    //全屏按键显示隐藏
    public void mFullscreenVibi(int id) {
        mFullscreenButton.setVisibility(id);
    }

    //返回按键显示隐藏
    public void mBackVibi(int id) {
        mBackButton.setVisibility(id);
    }

    //锁定图标显示隐藏
    public void mLockScreenVibi(int id) {
        mLockScreen.setVisibility(id);
    }

    //当前时间显示隐藏
    public void mCurrentTimeVibi(int id) {
        mCurrentTimeTextView.setVisibility(id);
    }

    //总时间显示隐藏
    public void mTotalTimeVibi(int id) {
        mTotalTimeTextView.setVisibility(id);
    }

    //名称显示隐藏
    public void mTitleVibi(int id) {
        mTitleTextView.setVisibility(id);
    }

    //顶部视图显示隐藏
    public void mTopContainerVibi(int id) {
        mTopContainer.setVisibility(id);
    }

    //底部功能隐藏
    public void mBottomContainerVibi(int id) {
        mBottomContainer.setVisibility(id);
    }

    //封面父布局显示隐藏
    public void mThumbVibi(int id) {
        mThumbImageViewLayout.setVisibility(id);
    }

    //底部进度调显示隐藏
    public void mBottomProgressVibi(int id) {
        if (id == GONE) {
            progressSate = false;
        } else {
            progressSate = true;
        }
        mBottomProgressBar.setVisibility(id);
    }

    @Override
    protected void setViewShowState(View view, int visibility) {
        if (!progressSate) {
            if (view.getId() == R.id.bottom_progressbar) {
                view.setVisibility(GONE);
                return;
            }
        }
        if (view != null) {
            view.setVisibility(visibility);
        }
    }
    //视图点击监听
    VideoViewTouchListening videoViewTouchListening;
    //设置视图监听
    public void setVideoViewTouchListening(VideoViewTouchListening viewTouchListening) {
        this.videoViewTouchListening =viewTouchListening;
    }
    @Override
    public void onClick(View v) {
        //视图点击监听
        if(videoViewTouchListening !=null) {
            videoViewTouchListening.ViewonClick(v);;
        }
        super.onClick(v);
    }

    interface VideoViewTouchListening {
        //点击事件监听
        void ViewonClick(View view);
        //滑动事件监听
        void ViewonTouch(View v, MotionEvent event);
    }
    public boolean onTouch(View v, MotionEvent event){
        //传递手势监听
        if(videoViewTouchListening!=null) {
            videoViewTouchListening.ViewonTouch(v,event);
        }
        return  super.onTouch(v,event);
    }

}
