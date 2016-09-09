package com.zheng.qq.ui;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zheng.qq.R;

/**
 * Created by zheng on 2016/9/9.
 * 自定义DragLayout实现拖拽
 */
public class DragLayout extends FrameLayout {

    private ViewDragHelper mDragHelper;
    private ViewGroup mLeftContent,mMainContent;


    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        /**
         * forParent 滑动子view的父容器
         * sensivity 敏感度 默认1.0
         * cb 回调
         *
         */

        //1.初始化
        mDragHelper = ViewDragHelper.create(this, 1.0f, mCallBack);


    }

    ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {

        /**
         *
         * @param child 当前被拖拽的view
         * @param pointerId 区分多点触摸的id
         * @return 根据返回结果确定当前view是否可以拖动
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //允许被拖拽的子view
            //主面板允许拖拽
            return child==mMainContent;
        }

        /**
         *
         * @param child
         * @param left
         * @param dx
         * @return 根据建议值修正将要移动到（水平）的位置
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }
    };

    //2.传递触摸事件

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //传递给mDragHelper
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //多点触控
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回true 持续接受事件
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //容错性检查 至少要有两个子View
        if (getChildCount() < 2) {
            throw new IllegalStateException(getResources().getString(R.string.Illegal_view_count));
        }
        //子View为ViewGroup的子类
        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalArgumentException(getResources().getString(R.string.Illegal_view_instance));
        }

         mLeftContent =  (ViewGroup) getChildAt(0);
         mMainContent = (ViewGroup) getChildAt(1);



    }
}
