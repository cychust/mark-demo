package com.cyc.markdemo.taskdetail;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cyc.markdemo.R;

/**
 * Created by cyc20 on 2017/12/27.
 */

public class TaskDetailActivity extends AppCompatActivity {

    public static final int DURATION = 3000;
    private static final AccelerateDecelerateInterpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final String SCALE_WIDTH = "SCALE_WIDTH";
    private static final String SCALE_HEIGHT = "SCALE_HEIGHT";
    private static final String TRANSITION_X = "TRANSITION_X";
    private static final String TRANSITION_Y = "TRANSITION_Y";


    private TextView mCardView;
    private TextView mDescriptionTV;
    private int mScreenWidth;
    private int mScreenHeight;
    private Bundle mScaleBundle = new Bundle();
    private Bundle mTransitionBundle = new Bundle();
    private int mOriginWidth;
    private int mOriginHeight;
    private String mTaskId;
    private Rect mRect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_main);
        mCardView=(TextView) findViewById(R.id.detail_title_tv);
        mDescriptionTV=(TextView)findViewById(R.id.detail_description_tv);
        initial();
        getScreenSize();
        getBundleInfo();
        runEnterAinim();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        runExitAinim();
    }

    private void initial(){
       // mCardView=(CardView) getLayoutInflater().inflate(R.layout.card_item_main,null,false);
        mRect=getIntent().getSourceBounds();
        mTaskId=getIntent().getExtras().getString("taskId");
        mOriginHeight=mRect.bottom-mRect.top;
        mOriginWidth=mRect.right-mRect.left;
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(mOriginWidth,mOriginHeight);
        layoutParams.setMargins(mRect.left,mRect.bottom-getStatusBarHeight(),mRect.right,mRect.bottom);
        Log.d("initial", "initial: en");
        mCardView.setLayoutParams(layoutParams);
       // TextView textView=mCardView.findViewById(R.id.title_tv);
        //todo textView

    }
    private void runEnterAinim(){
        mCardView.animate()
                .setInterpolator(DEFAULT_INTERPOLATOR)
                .setDuration(DURATION)
                .scaleX(mScaleBundle.getFloat(SCALE_WIDTH))
                .scaleY(mScaleBundle.getFloat(SCALE_HEIGHT))
                .translationX(mTransitionBundle.getFloat(TRANSITION_X))
                .translationY(mTransitionBundle.getFloat(TRANSITION_Y))
                .start();
        Log.d("run", "runEnterAinim: start");
        mCardView.setVisibility(View.VISIBLE);
        mDescriptionTV.setVisibility(View.VISIBLE);

    }
    private void runExitAinim(){

    }
    private void getScreenSize(){
        Display display=getWindowManager().getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);
        mScreenWidth=size.x;
        mScreenHeight=size.y;
    }
    private void getBundleInfo(){
        if (mCardView.getWidth() >=mCardView.getHeight()) {
            Log.d("bundle", "getBundleInfo: "+mCardView.getWidth()+mCardView.getHeight());
            mScaleBundle.putFloat(SCALE_WIDTH, (float) mScreenWidth / mOriginWidth);
            mScaleBundle.putFloat(SCALE_HEIGHT, (float) mCardView.getHeight() / mOriginHeight);
        } else {
            Log.d("bundle", "getBundleInfo: "+mCardView.getWidth()+mCardView.getHeight());
            mScaleBundle.putFloat(SCALE_WIDTH, (float) mCardView.getWidth() / mOriginWidth);
            mScaleBundle.putFloat(SCALE_HEIGHT, (float) mScreenHeight / mOriginHeight);
        }
        mTransitionBundle.putFloat(TRANSITION_X,mScreenWidth/2-(mRect.left+(mRect.right-mRect.left)/2));
        mTransitionBundle.putFloat(TRANSITION_Y,mScreenHeight/2-(mRect.top+(mRect.bottom-mRect.top)/2));
        Log.d("bundle", "getBundleInfo: "+mCardView.getWidth()+mCardView.getHeight());
    }
    private int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }
}
