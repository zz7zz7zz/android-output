package com.open.test.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

//北斗七星
public class DipperView extends View {

    private Context context;
    private Paint mPaint;
    private Paint mBgPaint;
    private Rect mRect;

    public DipperView(Context context) {
        this(context, null);
    }

    public DipperView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DipperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {

        this.context = context;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.WHITE);
        mPaint.setColor(Color.RED);
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.drawRect(0, 0, width, height, mBgPaint);

        Log.v("DipperView","width = " + width + " height = " +height);

//        2023-05-10 15:44:49.879  2373-2373  PentacleView            com.open.test                        V  width = 1080 height = 2102
//        2023-05-10 15:44:49.879  2373-2373  PentacleView            com.open.test                        V  circleX = 540.0 circleY = 1051.0 radius = 540

        drawPentacleView(canvas, mPaint, width, height);
    }

    private List<PointBean> mPointBeanList;

    /**
     * 根据圆心和半径求出五角星5个点
     */
    private void drawPentacleView(Canvas canvas, Paint paint, int width, int height) {

        mPointBeanList = new ArrayList<>();

        int dx = 0;
        int dy = height * 2/3;
        float startX = width/7;
        float startY = dy;

        //第一个点
        float x = startX;
        float y = startY;
        PointBean pointBean = new PointBean(x, y);
        mPointBeanList.add(pointBean);

        //第二个点
        x = 2*startX;
        y = (float) (dy-startX * Math.tan(Math.toRadians(60)));
        pointBean = new PointBean(x, y);
        mPointBeanList.add(pointBean);

        //第三个点
        x = 3*startX;
        y = (float) (startY - (mPointBeanList.get(0).pointY -mPointBeanList.get(1).pointY) * 0.05);
        pointBean = new PointBean(x, y);
        mPointBeanList.add(pointBean);

        //第四个点
        x = 4*startX;
        y = (float) (dy-startX * Math.tan(Math.toRadians(50)));
        pointBean = new PointBean(x, y);
        mPointBeanList.add(pointBean);

        //第五个点
        x = 5*startX;
        y = (float) (startY + (mPointBeanList.get(0).pointY - mPointBeanList.get(1).pointY) * 0.08);
        pointBean = new PointBean(x, y);
        mPointBeanList.add(pointBean);

        //第六个点
        x = 6*startX;
        y = (float) (y-startX * Math.tan(Math.toRadians(58)));
        pointBean = new PointBean(x, y);
        mPointBeanList.add(pointBean);

        //第七个点
        x = (float) (3.95*startX);
        y = (float) (pointBean.pointY-2*startX * Math.sin(Math.toRadians(55)));
        pointBean = new PointBean(x, y);
        mPointBeanList.add(pointBean);


        for(int i =0;i<mPointBeanList.size();i++){
            Log.v("DipperView",i + " -> x = " + mPointBeanList.get(i).pointX + " y = " +mPointBeanList.get(i).pointY);
        }

//        2023-05-13 17:38:56.140 10701-10701 DipperView              com.open.test                        V  0 -> x = 154.0 y = 1401.0
//        2023-05-13 17:38:56.140 10701-10701 DipperView              com.open.test                        V  1 -> x = 308.0 y = 1134.2642
//        2023-05-13 17:38:56.140 10701-10701 DipperView              com.open.test                        V  2 -> x = 462.0 y = 1401.0
//        2023-05-13 17:38:56.140 10701-10701 DipperView              com.open.test                        V  3 -> x = 616.0 y = 1247.0
//        2023-05-13 17:38:56.141 10701-10701 DipperView              com.open.test                        V  4 -> x = 770.0 y = 1422.3389
//        2023-05-13 17:38:56.141 10701-10701 DipperView              com.open.test                        V  5 -> x = 924.0 y = 1217.974
//        2023-05-13 17:38:56.141 10701-10701 DipperView              com.open.test                        V  6 -> x = 608.3 y = 965.6752

        drawPentacleView(canvas, paint, mPointBeanList);
    }

    /**
     * 画出五角星的5条线
     */
    private void drawPentacleView(Canvas canvas, Paint paint, List<PointBean> pointBeanList) {

        drawPentacleLine(canvas, paint, pointBeanList, 0, 1);
        drawPentacleLine(canvas, paint, pointBeanList, 1, 2);
        drawPentacleLine(canvas, paint, pointBeanList, 2, 3);
        drawPentacleLine(canvas, paint, pointBeanList, 3, 4);
        drawPentacleLine(canvas, paint, pointBeanList, 4, 5);
        drawPentacleLine(canvas, paint, pointBeanList, 5, 6);
    }

    private void drawPentacleLine(Canvas canvas, Paint paint, List<PointBean> pointBeanList, int startIndex, int endIndex) {

        float startX = pointBeanList.get(startIndex).getPointX();
        float startY = pointBeanList.get(startIndex).getPointY();
        float endX = pointBeanList.get(endIndex).getPointX();
        float endY = pointBeanList.get(endIndex).getPointY();
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    public static class PointBean{
        public float pointX;
        public float pointY;

        public PointBean(float pointX, float pointY) {
            this.pointX = pointX;
            this.pointY = pointY;
        }

        public float getPointX() {
            return pointX;
        }

        public void setPointX(int pointX) {
            this.pointX = pointX;
        }

        public float getPointY() {
            return pointY;
        }

        public void setPointY(int pointY) {
            this.pointY = pointY;
        }
    }
}
