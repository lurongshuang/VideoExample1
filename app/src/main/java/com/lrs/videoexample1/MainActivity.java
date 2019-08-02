package com.lrs.videoexample1;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.lrs.videobyl.GL.PixelationEffect;
import com.lrs.videobyl.builder.LrsOptionBuilder;
import com.lrs.videobyl.vide_interface.PlayInterface;
import com.lrs.videobyl.view.LrsPlayView;
import com.lrs.videobyl.view.baseView;
import com.shuyu.gsyvideoplayer.render.effect.AutoFixEffect;
import com.shuyu.gsyvideoplayer.render.effect.BarrelBlurEffect;
import com.shuyu.gsyvideoplayer.render.effect.BlackAndWhiteEffect;
import com.shuyu.gsyvideoplayer.render.effect.BrightnessEffect;
import com.shuyu.gsyvideoplayer.render.effect.ContrastEffect;
import com.shuyu.gsyvideoplayer.render.effect.CrossProcessEffect;
import com.shuyu.gsyvideoplayer.render.effect.DocumentaryEffect;
import com.shuyu.gsyvideoplayer.render.effect.DuotoneEffect;
import com.shuyu.gsyvideoplayer.render.effect.FillLightEffect;
import com.shuyu.gsyvideoplayer.render.effect.GammaEffect;
import com.shuyu.gsyvideoplayer.render.effect.GaussianBlurEffect;
import com.shuyu.gsyvideoplayer.render.effect.GrainEffect;
import com.shuyu.gsyvideoplayer.render.effect.HueEffect;
import com.shuyu.gsyvideoplayer.render.effect.InvertColorsEffect;
import com.shuyu.gsyvideoplayer.render.effect.LamoishEffect;
import com.shuyu.gsyvideoplayer.render.effect.NoEffect;
import com.shuyu.gsyvideoplayer.render.effect.OverlayEffect;
import com.shuyu.gsyvideoplayer.render.effect.PosterizeEffect;
import com.shuyu.gsyvideoplayer.render.effect.SampleBlurEffect;
import com.shuyu.gsyvideoplayer.render.effect.SaturationEffect;
import com.shuyu.gsyvideoplayer.render.effect.SepiaEffect;
import com.shuyu.gsyvideoplayer.render.effect.SharpnessEffect;
import com.shuyu.gsyvideoplayer.render.effect.TemperatureEffect;
import com.shuyu.gsyvideoplayer.render.effect.TintEffect;
import com.shuyu.gsyvideoplayer.render.effect.VignetteEffect;
import com.shuyu.gsyvideoplayer.render.view.GSYVideoGLView;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PlayInterface {
    private static int withnumber;
    LrsPlayView lrsPlayView;
    LinearLayout rlwin;
    boolean isPlay;
    boolean isPause;
    private int backupRendType;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lrsPlayView = findViewById(R.id.lrsview);
        rlwin = findViewById(R.id.rlwin);
        //权限
        getPermiss();
        //设置监听器
        lrsPlayView.SetPlayInterface(this);
        //静音
        lrsPlayView.setVolume(false);
        //循环播放
        lrsPlayView.setLooping(true);
        //隐藏返回按钮
        lrsPlayView.setmBackVibi(View.GONE);

        backupRendType = GSYVideoType.getRenderType();
        //设置为GL播放模式，才能支持滤镜，注意此设置是全局的
        GSYVideoType.setRenderType(GSYVideoType.GLSURFACE);

        findViewById(R.id.btmp4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lrsPlayView != null) {
                    if (lrsPlayView.isInPlayingState()) {
                        lrsPlayView.stop();
                    }
                    //底部进度隐藏  直播不需要进度
                    lrsPlayView.mBottomProgressVibi(View.VISIBLE);
                    //设置返回按钮隐藏 直播不需要进度
//                lrsPlayView.setmBackVibi(View.VISIBLE);
                    lrsPlayView.mTotalTimeVibi(View.VISIBLE);
                    lrsPlayView.mCurrentTimeVibi(View.VISIBLE);
                    lrsPlayView.mProgressBarVibi(View.VISIBLE);
                    //
                    lrsPlayView.setIsTouchWigetFull(true);
                    lrsPlayView.setVideoURL("https://vd4.bdstatic.com/mda-ij3q3bd7cassmnsc/sc/mda-ij3q3bd7cassmnsc.mp4");
                    lrsPlayView.prepare();

                }

            }
        });
        findViewById(R.id.btbs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提供类一种设置方式！！！！！！！！！！！！！！
//                LrsOptionBuilder lrsOptionBuilder = new LrsOptionBuilder();
//                lrsOptionBuilder.setSpeed(6f).build(lrsPlayView.getVideo_player());
                lrsPlayView.setSpeedPlaying(2f,true);
            }
        });
        findViewById(R.id.btm3u8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lrsPlayView != null) {
                    if (lrsPlayView.isInPlayingState()) {
                        lrsPlayView.stop();
                    }
                    //禁止滑动操作
                    lrsPlayView.setIsTouchWigetFull(false);
                    //底部进度隐藏  直播不需要进度
                    lrsPlayView.mBottomProgressVibi(View.GONE);
                    //设置返回按钮隐藏 直播不需要进度
                    lrsPlayView.setmBackVibi(View.GONE);
                    lrsPlayView.mTotalTimeVibi(View.INVISIBLE);
                    lrsPlayView.mCurrentTimeVibi(View.INVISIBLE);
                    lrsPlayView.mProgressBarVibi(View.INVISIBLE);
                    lrsPlayView.setVideoURL("http://183.251.61.207/PLTV/88888888/224/3221225829/index.m3u8");
                    lrsPlayView.prepare();
                }
            }
        });

        findViewById(R.id.btlj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resolveTypeUI(lrsPlayView.getVideo_player(), type);
                type += 1;
                if (type > 26) {
                    type = 0;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        lrsPlayView.resume(false);
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        //恢复到原本的绘制模式
        GSYVideoType.setRenderType(backupRendType);
        super.onDestroy();
        if (isPlay) {
            lrsPlayView.release();
        }
    }

    @Override
    public void onPause() {
        lrsPlayView.pause();
        super.onPause();
        isPause = true;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int currentOrientation = getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                SetonConfigurationChanged(2);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //权限获取
    private void getPermiss() {
        AndPermission.with(this).permission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CHANGE_CONFIGURATION,
                Manifest.permission.CHANGE_CONFIGURATION,
                Manifest.permission.WRITE_SETTINGS
        ).callback(new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

            }
        }).start();
    }

    /**
     * 屏幕旋转时调用此方法
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            竖屏
            SetonConfigurationChanged(2);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//           横屏
            SetonConfigurationChanged(1);
        }
    }

    /**
     * 布局处理
     *
     * @param type 1 横屏 2 竖屏
     */
    private void SetonConfigurationChanged(int type) {
        if (type == 1) {
            fullscreen(true);
            android.view.ViewGroup.LayoutParams pp = rlwin.getLayoutParams();
            withnumber = pp.height;
            pp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            pp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            rlwin.setLayoutParams(pp);
        } else {
            fullscreen(false);
            android.view.ViewGroup.LayoutParams pp = rlwin.getLayoutParams();
            pp.height = withnumber;
            pp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            rlwin.setLayoutParams(pp);
        }
    }


    //    //状态栏操作
    private void fullscreen(boolean enable) {
        if (enable) {  //隐藏状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            //显示状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void onVideoStart() {
        super.onStart();
    }

    @Override
    public void onVideoPause() {
    }

    @Override
    public void onVideoStop() {
        super.onStop();
    }

    @Override
    public void onVideoCompletion() {
    }

    @Override
    public void onVideoPlayListener(int progress, int secProgress, int currentPosition, int duration) {
        Log.e("onVideoPlayListener", "" + progress);
    }

    @Override
    public void onVideoError(int what, int exe) {
    }

    @Override
    public void onInfo(int what, int extra) {

    }

    @Override
    public void ViewonClick(View view) {
        //监听翻转
        if (view.getId() == com.lrs.videobyl.R.id.fullscreen) {
            int currentOrientation = getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                lrsPlayView.setNeedLockFull(false);
                lrsPlayView.setIfCurrentIsFullscreen(false);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                lrsPlayView.setNeedLockFull(true);
                lrsPlayView.setIfCurrentIsFullscreen(true);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }
    }

    @Override
    public void ViewonTouch(View v, MotionEvent event) {

    }

    @Override
    public void onBufferingUpdate(int percent) {

    }

    @Override
    public void onSeekComplete() {

    }

    @Override
    public void onVideoSizeChanged() {

    }

    @Override
    public void onBackFullscreen() {

    }

    @Override
    public void onVideoResume() {

    }

    @Override
    public void onVideoResume(boolean seek) {

    }

    /**
     * 切换滤镜
     */
    private float deep = 0.8f;

    private void resolveTypeUI(baseView view, int type) {
        GSYVideoGLView.ShaderInterface effect = new NoEffect();
        switch (type) {
            case 0:
                effect = new AutoFixEffect(deep);
                break;
            case 1:
                effect = new PixelationEffect();
                break;
            case 2:
                effect = new BlackAndWhiteEffect();
                break;
            case 3:
                effect = new ContrastEffect(deep);
                break;
            case 4:
                effect = new CrossProcessEffect();
                break;
            case 5:
                effect = new DocumentaryEffect();
                break;
            case 6:
                effect= new DuotoneEffect(Color.BLUE, Color.YELLOW);
                break;
            case 7:
                effect = new FillLightEffect(deep);
                break;
            case 8:
                effect = new GammaEffect(deep);
                break;
            case 9:
                effect = new GrainEffect(deep);
                break;
            case 10:
                effect = new GrainEffect(deep);
                break;
            case 11:
                effect = new HueEffect(deep);
                break;
            case 12:
                effect = new InvertColorsEffect();
                break;
            case 13:
                effect = new LamoishEffect();
                break;
            case 14:
                effect = new PosterizeEffect();
                break;
            case 15:
                effect = new BarrelBlurEffect();
                break;
            case 16:
                effect = new SaturationEffect(deep);
                break;
            case 17:
                effect = new SepiaEffect();
                break;
            case 18:
                effect = new SharpnessEffect(deep);
                break;
            case 19:
                effect = new TemperatureEffect(deep);
                break;
            case 20:
                effect = new TintEffect(Color.GREEN);
                break;
            case 21:
                effect = new VignetteEffect(deep);
                break;
            case 22:
                effect = new NoEffect();
                break;
            case 23:
                effect = new OverlayEffect();
                break;
            case 24:
                effect = new SampleBlurEffect(4.0f);
                break;
            case 25:
                effect = new GaussianBlurEffect(6.0f, GaussianBlurEffect.TYPEXY);
                break;
            case 26:
                effect = new BrightnessEffect(deep);
                break;
        }
        view.setEffectFilter(effect);
    }
}
