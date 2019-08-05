package com.lrs.videobyl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lrs.videobyl.R;
import com.lrs.videobyl.vide_interface.PlayInterface;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYMediaPlayerListener;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoViewBridge;

import java.io.File;
import java.util.Map;

public class LrsPlayView extends FrameLayout implements baseView.VideoViewTouchListening ,GSYVideoProgressListener, GSYMediaPlayerListener {

    private baseView video_player;
    private GSYVideoManager manager;
    //向下传递监听
    PlayInterface playInterface;
    public LrsPlayView(Context context) {
        super(context);
        init();
    }

    public LrsPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LrsPlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LrsPlayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public  void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.lrsplayview, this);
        video_player = findViewById(R.id.video_player);
        //进度过渡
        video_player.setGSYVideoProgressListener(this);
        //点击滑动事件过渡
        video_player.setVideoViewTouchListening(this);
        //播放器生命周期过渡   GSYMediaPlayerListener
        manager.setListener(this);
        manager = GSYVideoManager.instance();
    }

    /**
     * 设置播放地址
     *
     * @param url 播放地址
     */
    public void setVideoURL(String url) {
        if (video_player != null) {
            video_player.setUp(url,true,"");
        }
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @param title         title
     * @return
     */
    public void setVideoURL(String url, boolean cacheWithPlay, File cachePath, String title) {
        video_player.setUp(url, cacheWithPlay, cachePath, title);
    }

    /**
     * 设置监听动作
     *
     * @param playInterface
     */
    public void SetPlayInterface(PlayInterface playInterface) {
        this.playInterface = playInterface;
    }

    /**
     * 是否显示声音
     *
     * @param volume 1显示声音 0 不显示声音
     */
    public void setVolume(boolean volume) {
        if (video_player != null && manager!=null) {
            manager.setNeedMute(volume);
        }
    }

    /**
     * 是否循环播放
     *
     * @param looping
     */
    public void setLooping(boolean looping) {
        if (video_player != null) {
            video_player.setLooping(looping);
        }
    }
    /**
     * 开始播放
     */
    public void prepare() {
        if (video_player != null) {
            video_player.startPlayLogic();
        }
    }
    public  void stop() {
        if (video_player != null && manager!=null ) {
            manager.stop();
        }
    }
    public  void release(){
        if (video_player != null) {
            video_player.release();
            manager.releaseMediaPlayer();
        }
    }
    public  void resume(boolean seek){
        if (video_player != null) {
            video_player.getCurrentPlayer().onVideoResume(seek);
        }
    }
    public  void  pause(){
        if (video_player != null) {
            video_player.onVideoPause();
        }
    }

    /**
     * 获取视屏对象
     * @return 视频对象
     */
    public  baseView getVideo_player() {
        return video_player;
    }

    /**
     * 播放管理对象
     * @return 管理者
     */
    public  GSYVideoManager getManager_GSYVideo() {
        return manager;
    }
    public GSYVideoViewBridge getManager_GSYVideoView() {
        return video_player.getGSYVideoManager();
    }
    /**
     * 获取播放当前UI
     */
    public  int getLayoutId(){
            return  video_player.getLayoutId();
    }
    /**
     * 获取当前播放状态
     */
    public  int getCurrentState(){
        return  video_player.getCurrentState();
    }
    /**
     * 根据状态判断是否播放中
     */
    public boolean isInPlayingState(){
        return  video_player.isInPlayingState();
    }
    /**
     * 播放tag防止错误，因为普通的url也可能重复
     */
    public String getPlayTag()
    {
        return  video_player.getPlayTag();
    }
    /**
     * 播放tag防止错误，因为普通的url也可能重复
     *
     * @param playTag 保证不重复就好
     */
    public void setPlayTag(String playTag)
    {
        video_player.setTag(playTag);
    }

    /**
     * 播放索引
     * @return
     */
    public int getPlayPosition() {
        return  video_player.getPlayPosition();
    }
    /**
     * 设置播放位置防止错位
     */
    public void setPlayPosition(int playPosition){
        video_player.setPlayPosition(playPosition);
    }
    /**
     * 网络速度
     * 注意，这里如果是开启了缓存，因为读取本地代理，缓存成功后还是存在速度的
     * 再打开已经缓存的本地文件，网络速度才会回0.因为是播放本地文件了
     */
    public long getNetSpeed(){
       return video_player.getNetSpeed();
    }
    /**
     * 网络速度
     * 注意，这里如果是开启了缓存，因为读取本地代理，缓存成功后还是存在速度的
     * 再打开已经缓存的本地文件，网络速度才会回0.因为是播放本地文件了
     */
    public String getNetSpeedText() {
        return video_player.getNetSpeedText();
    }

    /**
     * 从哪个开始播放
     * @return
     */
    public long getSeekOnStart(){
        return video_player.getSeekOnStart();
    }
    /**
     * 从哪里开始播放
     * 目前有时候前几秒有跳动问题，毫秒
     * 需要在prepare之前，即播放开始之前
     */
    public void setSeekOnStart(long seekOnStart){
        video_player.setSeekOnStart(seekOnStart);
    }

    /**
     * 缓冲进度/缓存进度
     * @return
     */
    public int getBuffterPoint(){
       return video_player.getBuffterPoint();
    }

    /**
     * 获取是否全屏
     * @return
     */
    public boolean isIfCurrentIsFullscreen(){
        return  video_player.isIfCurrentIsFullscreen();
    }

    /**
     * 设置全屏
     * @param ifCurrentIsFullscreen
     */
    public void setIfCurrentIsFullscreen(boolean ifCurrentIsFullscreen) {
        video_player.setIfCurrentIsFullscreen(ifCurrentIsFullscreen);
    }

    /**
     * 当前是否循环播放
     * @return
     */
    public boolean isLooping(){
        return  video_player.isLooping();
    }
    /**
     * 设置播放过程中的回调
     *
     * @param mVideoAllCallBack
     */
    public void setVideoAllCallBack(VideoAllCallBack mVideoAllCallBack) {
        video_player.setVideoAllCallBack(mVideoAllCallBack);
    }

    /**
     * 获取播放速度
     * @return
     */
    public float getSpeed() {
        return video_player.getSpeed();
    }
    /**
     * 播放速度
     *
     * @param speed      速度
     * @param soundTouch 是否对6.0下开启变速不变调
     */
    public void setSpeed(float speed, boolean soundTouch) {
        video_player.setSpeed(speed,soundTouch);
    }

    /**
     * 播放中生效的播放数据
     *
     * @param speed
     * @param soundTouch
     */
    public void setSpeedPlaying(float speed, boolean soundTouch) {
        video_player.setSpeedPlaying(speed,soundTouch);
    }

    /**
     * 是否显示暂停锁定效果
     * @return
     */
    public boolean isShowPauseCover(){
        return video_player.isShowPauseCover();
    }
    /**
     * 是否需要加载显示暂停的cover图片
     * 打开状态下，暂停退到后台，再回到前台不会显示黑屏，但可以对某些机型有概率出现OOM
     * 关闭情况下，暂停退到后台，再回到前台显示黑屏
     *
     * @param showPauseCover 默认true
     */
    public void setShowPauseCover(boolean showPauseCover) {
        video_player.setShowPauseCover(showPauseCover);
    }
    /**
     * seekto  进度条拖拽
     */
    public void seekTo(long position) {
        video_player.seekTo(position);
    }

    /**
     * 获取准备成功后是否播放
     * @return
     */
    public boolean isStartAfterPrepared() {
        return  video_player.isStartAfterPrepared();
    }
    /**
     * 准备成功之后立即播放
     *
     * @param startAfterPrepared 默认true，false的时候需要在prepared后调用startAfterPrepared()
     */
    public void setStartAfterPrepared(boolean startAfterPrepared) {
        video_player.setStartAfterPrepared(startAfterPrepared);
    }

    /**
     * 长时间失去焦点 是否暂停了播放器
     * @return
     */
    public boolean isReleaseWhenLossAudio() {
        return  video_player.isReleaseWhenLossAudio();

    }
    /**
     * 长时间失去音频焦点，暂停播放器
     *
     * @param releaseWhenLossAudio 默认true，false的时候只会暂停
     */
    public void setReleaseWhenLossAudio(boolean releaseWhenLossAudio) {
        video_player.setReleaseWhenLossAudio(releaseWhenLossAudio);
    }

    /**
     * 获取mapHeader
     * @return
     */
    public Map<String, String> getMapHeadData() {
        return video_player.getMapHeadData();
    }
    /**
     * 单独设置mapHeader
     *
     * @param headData
     */
    public void setMapHeadData(Map<String, String> headData) {
        video_player.setMapHeadData(headData);
    }

    /**
     * 获取 覆盖拓展类型
     * @return
     */
    public String getOverrideExtension(){
        return  video_player.getOverrideExtension();
    }
    /**
     * 是否需要覆盖拓展类型，目前只针对exoPlayer内核模式有效
     * @param overrideExtension 比如传入 m3u8,mp4,avi 等类型
     */
    public void setOverrideExtension(String overrideExtension) {
        video_player.setOverrideExtension(overrideExtension);
    }

    //返回按钮显示隐藏
    public void  setmBackVibi(int id) {
        video_player.setmBackVibi(id);
    }

    //播放按键显示隐藏
    public  void setStartVibi(int id) {
        video_player.setStartVibi(id);
    }
    //封面显示隐藏
    public void setThumbVibi(int id) {
        video_player.setThumbVibi(id);
    }
    //加载框显示隐藏
    public  void setLoadingProgressVibi(int id) {
        video_player.setLoadingProgressVibi(id);
    }
    //进度条显示隐藏
    public  void mProgressBarVibi(int id){
        video_player.mProgressBarVibi(id);
    }
    //全屏按键显示隐藏
    public  void mFullscreenVibi(int id) {
        video_player.mFullscreenVibi(id);
    }
    //返回按键显示隐藏
    public void mBackVibi(int id) {
        video_player.mBackVibi(id);
    }
    //锁定图标显示隐藏
    public  void mLockScreenVibi(int id) {
        video_player.mLockScreenVibi(id);
    }

    //当前时间显示隐藏
    public  void mCurrentTimeVibi(int id) {
        video_player.mCurrentTimeVibi(id);
    }
    //总时间显示隐藏
    public  void mTotalTimeVibi(int id) {
        video_player.mTotalTimeVibi(id);
    }
    //名称显示隐藏
    public  void mTitleVibi(int id) {
        video_player.mTitleVibi(id);
    }
    //顶部视图显示隐藏
    public  void mTopContainerVibi(int id ){
        video_player.mTopContainerVibi(id);
    }
    //低部视图显示隐藏
    public void mBottomContainerVibi(int id){
        video_player.mBottomContainerVibi(id);
    }
    //封面父布局显示隐藏
    public  void mThumbVibi(int id){
        video_player.mThumbVibi(id);
    }
    //底部进度调显示隐藏
    public  void mBottomProgressVibi(int id) {
        video_player.mBottomProgressVibi(id);
    }

    @Override
    public void ViewonClick(View view) {
        if(playInterface!=null)
            playInterface.ViewonClick(view);
    }
    @Override
    public void ViewonTouch(View v, MotionEvent event) {
        if(playInterface!=null)
            playInterface.ViewonTouch(v,event);
    }



    /**
     * 封面布局
     */
    public RelativeLayout getThumbImageViewLayout() {
        return video_player.getThumbImageViewLayout();
    }

    /***
     * 设置封面
     */
    public void setThumbImageView(View view) {
        video_player.setThumbImageView(view);
    }
    /***
     * 清除封面
     */
    public void clearThumbImageView() {
        video_player.clearThumbImageView();
    }
    /***
     * 获取封面
     */
    public View getThumbImageView() {
        return video_player.getThumbImageView();
    }


    /**
     * 获取头部title
     */
    public TextView getTitleTextView() {
       return video_player.getTitleTextView();
    }
    /**
     * 获取播放按键
     */
    public View getStartButton() {
        return video_player.getStartButton();
    }

    /**
     * 获取全屏按键
     */
    public ImageView getFullscreenButton() {
        return video_player.getFullscreenButton();
    }

    /**
     * 获取返回按键
     */
    public ImageView getBackButton() {
        return video_player.getBackButton();
    }

    /**
     * 设置右下角 全屏 按钮显示资源
     * @return
     */
    public int getEnlargeImageRes() {
        return video_player.getEnlargeImageRes();
    }

    /**
     * 设置右下角 显示切换到全屏 的按键资源
     * 必须在调用播放之前设置
     * 不设置使用默认
     */
    public void setEnlargeImageRes(int mEnlargeImageRes) {
        video_player.setEnlargeImageRes(mEnlargeImageRes);
    }

    /**
     * 获取右下角资源
     * @return
     */
    public int getShrinkImageRes() {
        return video_player.getShrinkImageRes();
    }

    /**
     * 设置右下角 显示退出全屏 的按键资源
     * 必须在播放之前设置
     * 不设置使用默认
     */
    public void setShrinkImageRes(int mShrinkImageRes) {
        video_player.setShrinkImageRes(mShrinkImageRes);
    }

    /**
     * 是否可以全屏滑动界面改变进度，声音等
     * 默认 true
     */
    public void setIsTouchWigetFull(boolean isTouchWigetFull) {
        video_player.setIsTouchWigetFull(isTouchWigetFull);
    }

    /**
     * 是否点击封面可以播放
     */
    public void setThumbPlay(boolean thumbPlay) {
        video_player.setThumbPlay(thumbPlay);
    }

    /**
     * 全屏是否隐藏了 虚拟按键
     * @return
     */
    public boolean isHideKey() {
        return video_player.isHideKey();
    }

    /**
     * 全屏隐藏虚拟按键，默认打开
     */
    public void setHideKey(boolean hideKey) {
        video_player.setHideKey(hideKey);
    }

    /**
     * 是否显示流量提示
     * @return
     */
    public boolean isNeedShowWifiTip() {
        return video_player.isNeedShowWifiTip();
    }

    /**
     * //是否支持非全屏滑动触摸有效
     * @return
     */
    public boolean isTouchWiget() {
        return video_player.isTouchWiget();
    }
    /**
     * 是否可以滑动界面改变进度，声音等
     * 默认true
     */
    public void setIsTouchWiget(boolean isTouchWiget) {
        video_player.setIsTouchWiget(isTouchWiget);
    }

    /**
     * 是否支持全屏滑动触摸有效
     * @return
     */
    public boolean isTouchWigetFull() {
        return video_player.isTouchWigetFull();
    }

    /**
     * 是否需要显示流量提示,默认true
     */
    public void setNeedShowWifiTip(boolean needShowWifiTip) {
        video_player.setNeedShowWifiTip(needShowWifiTip);
    }

    /**
     * 调整触摸滑动快进的比例
     *
     * @param seekRatio 滑动快进的比例，默认1。数值越大，滑动的产生的seek越小
     */
    public void setSeekRatio(float seekRatio) {
        video_player.setSeekRatio(seekRatio);
    }

    /**
     * 触摸滑动快进的比例
     * @return
     */
    public float getSeekRatio() {
        return video_player.getSeekRatio();
    }

    /**
     *  是否需要锁定屏幕
     * @return
     */
    public boolean isNeedLockFull() {
        return video_player.isNeedLockFull();
    }

    /**
     * 是否需要全屏锁定屏幕功能
     * 如果单独使用请设置setIfCurrentIsFullscreen为true
     */
    public void setNeedLockFull(boolean needLoadFull) {
        video_player.setNeedLockFull(needLoadFull);
    }

    /**
     * 锁屏点击 监听
     */
    public void setLockClickListener(LockClickListener lockClickListener) {
        video_player.setLockClickListener(lockClickListener);
    }

    /**
     * 设置触摸显示控制ui的消失时间
     *
     * @param dismissControlTime 毫秒，默认2500
     */
    public void setDismissControlTime(int dismissControlTime) {
        video_player.setDismissControlTime(dismissControlTime);
    }

    /**
     * 获取触摸显示控制ui的消失时间
     * @return
     */
    public int getDismissControlTime() {
        return video_player.getDismissControlTime();
    }

    //默认显示比例
    public final static int SCREEN_TYPE_DEFAULT = 0;

    //16:9
    public final static int SCREEN_TYPE_16_9 = 1;

    //4:3
    public final static int SCREEN_TYPE_4_3 = 2;

    //全屏裁减显示，为了显示正常 CoverImageView 建议使用FrameLayout作为父布局
    public final static int SCREEN_TYPE_FULL = 4;

    //全屏拉伸显示，使用这个属性时，surface_container建议使用FrameLayout
    public final static int SCREEN_MATCH_FULL = -4;
    /**
     * 播放器显示显示比例
     * @param showType SCREEN_TYPE_DEFAULT || SCREEN_TYPE_16_9||SCREEN_TYPE_4_3 ||SCREEN_TYPE_FULL || SCREEN_MATCH_FULL
     */
    public void setShowType(int showType) {
        GSYVideoType.setShowType(showType);
    }

    /**
     * TextureView,默认
     */
    public final static int TEXTURE = 0;
    /**
     * SurfaceView，与动画全屏的效果不是很兼容
     */
    public final static int SUFRACE = 1;
    /**
     * GLSurfaceView 主要用于OpenGL渲染的
     */
    public final static int GLSURFACE = 2;
    /**
     * 设置渲染规则  TEXTURE || SUFRACE || GLSURFACE
     */
    public  void setRenderType(int renderType){
        GSYVideoType.setRenderType(renderType);
    }
    /**
     * 进度回调
     */
    public void setGSYVideoProgressListener(GSYVideoProgressListener videoProgressListener) {
        video_player.setGSYVideoProgressListener(videoProgressListener);
    }

    @Override
    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
        if(playInterface!=null){
            playInterface.onVideoPlayListener(progress,secProgress,currentPosition,duration);
        }
    }

    @Override
    public void onPrepared() {
        if(playInterface!=null)
        playInterface.onVideoStart();
    }

    @Override
    public void onAutoCompletion() {
//        playInterface.onVideoCompletion();
    }

    @Override
    public void onCompletion() {
        if(playInterface!=null)
        playInterface.onVideoCompletion();
    }

    @Override
    public void onBufferingUpdate(int percent) {
        if(playInterface!=null)
            playInterface.onBufferingUpdate(percent);
    }

    @Override
    public void onSeekComplete() {
        if(playInterface!=null)
        playInterface.onSeekComplete();
    }

    @Override
    public void onError(int what, int extra) {
        if(playInterface!=null)
        playInterface.onVideoError(what,extra);

    }

    @Override
    public void onInfo(int what, int extra) {
        if(playInterface!=null)
        playInterface.onInfo(what,extra);
    }

    @Override
    public void onVideoSizeChanged() {
        if(playInterface!=null)
        playInterface.onVideoSizeChanged();
    }

    @Override
    public void onBackFullscreen() {
        if(playInterface!=null)
        playInterface.onBackFullscreen();
    }

    @Override
    public void onVideoPause() {
        if(playInterface!=null)
        playInterface.onVideoPause();
    }

    @Override
    public void onVideoResume() {
        if(playInterface!=null)
        playInterface.onVideoResume();
    }
    @Override
    public void onVideoResume(boolean seek) {
        if(playInterface!=null)
        playInterface.onVideoResume(seek);
    }
}
