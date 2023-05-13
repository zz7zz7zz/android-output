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


public class PentacleView extends View {

    private Context context;
    private Paint mPaint;
    private Paint mBgPaint;
    private Rect mRect;

    public PentacleView(Context context) {
        this(context, null);
    }

    public PentacleView(Context context,  AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PentacleView(Context context,  AttributeSet attrs, int defStyleAttr) {
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

        int zhijing = width > height ? height : width;
        int radius = zhijing >> 1;

        float circleX = width >> 1;
        float circleY = height >> 1;

        Log.v("PentacleView","width = " + width + " height = " +height);
        Log.v("PentacleView","circleX = " + circleX + " circleY = " +circleY + " radius = " + radius);

//        2023-05-10 15:44:49.879  2373-2373  PentacleView            com.open.test                        V  width = 1080 height = 2102
//        2023-05-10 15:44:49.879  2373-2373  PentacleView            com.open.test                        V  circleX = 540.0 circleY = 1051.0 radius = 540

        drawPentacleView(canvas, mPaint, circleX, circleY, radius);
    }

    private List<PointBean> mPointBeanList;

    /**
     * 根据圆心和半径求出五角星5个点
     */
    private void drawPentacleView(Canvas canvas, Paint paint, float circleX, float circleY, float radius) {

        mPointBeanList = new ArrayList<>();
        for (int i = 270; i >= -22; i -= 72) {

            float x = (float) (circleX + radius * Math.cos(Math.toRadians(i)));
            float y = (float) (circleY + radius * Math.sin(Math.toRadians(i)));

            Log.v("PentacleView",i + " -> x = " + x + " y = " +y);

            PointBean pointBean = new PointBean(x, y);
            mPointBeanList.add(pointBean);
        }

//        for (int i = 18; i < 360; i += 72) {
//            float x = (float) (circleX + radius * Math.cos(Math.toRadians(i)));
//            float y = (float) (circleY + radius * Math.sin(Math.toRadians(i)));

//            Log.v("PentacleView",i + " -> x = " + x + " y = " +y);

//            PointBean pointBean = new PointBean(x, y);
//            mPointBeanList.add(pointBean);
//        }
//        2023-05-10 15:41:00.067 32124-32124 PentacleView            com.open.test                        V  18 -> x = 1053.5706 y = 1217.8691
//        2023-05-10 15:41:00.067 32124-32124 PentacleView            com.open.test                        V  90 -> x = 540.0 y = 1591.0
//        2023-05-10 15:41:00.067 32124-32124 PentacleView            com.open.test                        V  162 -> x = 26.429482 y = 1217.8691
//        2023-05-10 15:41:00.067 32124-32124 PentacleView            com.open.test                        V  234 -> x = 222.59596 y = 614.1308
//        2023-05-10 15:41:00.067 32124-32124 PentacleView            com.open.test                        V  306 -> x = 857.40405 y = 614.1308

        drawPentacleView(canvas, paint, mPointBeanList);
    }

    /**
     * 画出五角星的5条线
     */
    private void drawPentacleView(Canvas canvas, Paint paint, List<PointBean> pointBeanList) {

        drawPentacleLine(canvas, paint, pointBeanList, 1, 4);
        drawPentacleLine(canvas, paint, pointBeanList, 4, 2);
        drawPentacleLine(canvas, paint, pointBeanList, 2, 0);
        drawPentacleLine(canvas, paint, pointBeanList, 0, 3);
        drawPentacleLine(canvas, paint, pointBeanList, 3, 1);
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
