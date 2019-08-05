package com.lrs.videobyl.vide_interface;

import android.view.MotionEvent;
import android.view.View;

/**
 * lrs
 */
public interface PlayInterface {
       //开始播放
       void onVideoStart();
       //播放暂停
       void onVideoPause();
       //继续播放
       void onVideoResume();
       //播放停止
//       void onVideoStop();
       //播放完成
       void onVideoCompletion();
       //播放进度
       /**
        * @param progress 当前播放进度（暂停后再播放可能会有跳动）
        * @param secProgress 当前内存缓冲进度（可能会有0值）
        * @param currentPosition 当前播放位置（暂停后再播放可能会有跳动）
        * @param duration 总时长
        */
       void onVideoPlayListener(int progress, int secProgress, int currentPosition, int duration);
       //播放出现问题
       void onVideoError(int what, int extra);
       //视图点击传递
       void ViewonClick(View view);
       //视图 滑动传递
       void ViewonTouch(View v, MotionEvent event);
}
