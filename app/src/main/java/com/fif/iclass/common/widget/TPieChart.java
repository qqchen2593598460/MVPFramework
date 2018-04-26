package com.fif.iclass.common.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.fif.baselib.widget.bean.MoaChartItem;
import com.fif.iclass.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.ListIterator;

/**
 * 参考自 Ahmed 的 作品 {https://github.com/Geek-1001/MagnificentChart} on 01.15.17.
 * 对其性能做了改进，减少不必要的绘制
 */
public class TPieChart extends View {

// #MARK - Constants

    // default initialization params


    // view properties
    private List<MoaChartItem> chartItemsList;
    private float totalValue;//所有数据加起来的总值
    private boolean isRound;
    private boolean isShadowShowing;
    private boolean isDisperse;//扇形是否分散开
    private float splitLineStroke; //分割线大小
    private boolean isMarkerShow;//是否显示标注
    private int shadowBackgroundColor;
    private int chartBackgroundColor;
    private int disperseLength = 0;//扇形的离散距离
    private Context context;
    private Typeface typeface = null;
    //private int width;
    // private int height;
    /**当下的绘制的角度，主要用于动画绘制*/
    private float globalCurrentAngle = 0.0f;

    //绘制扇形边框
    protected Paint mPaintArcBorder = null;

    //绘制标注
    protected TextPaint markerLinePaint = null;
// #MARK - Constructors
    /**
     * 标记线长度
     */
    private float markerLineLength = 30f;
    private float mTextHeight ;

    Paint currentPaint ;//绘制扇形的画笔

    RectF rect = new RectF();//绘制饼状图的区域

    public TPieChart(Context context) {
        super(context);
        init(context, null, 0, false, true, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public TPieChart(Context context, List<MoaChartItem> itemsList, int totalValue){
        super(context);
        init(context, itemsList, totalValue, false, true, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public TPieChart(Context context, List<MoaChartItem> itemsList, int totalValue, boolean isAnimated, boolean isRound, boolean showShadow, boolean showTitle){
        super(context);
        init(context, itemsList, totalValue, isRound, showShadow, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public TPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MoaChartView, 0, 0);
        try {
            boolean isRound = typedArray.getBoolean(R.styleable.MoaChartView_round, false);
            boolean showShadow = typedArray.getBoolean(R.styleable.MoaChartView_shadow, false);
            int shadowColor = typedArray.getColor(R.styleable.MoaChartView_shadowColor, Color.parseColor("#F2F2F2"));
            int backgroundColor = typedArray.getColor(R.styleable.MoaChartView_chart_background, Color.parseColor("#FFFFFF"));
            isDisperse = typedArray.getBoolean(R.styleable.MoaChartView_isDisperse,true);//默认扇形会分开
            splitLineStroke = typedArray.getDimensionPixelSize(R.styleable.MoaChartView_spliteLineStroke,
                    context.getResources().getDimensionPixelSize(R.dimen.pie_chart_default_splite_line_stroke));
            isMarkerShow = typedArray.getBoolean(R.styleable.MoaChartView_isMarkShow,true);//默认显示
            cirPadding = typedArray.getDimensionPixelSize(R.styleable.MoaChartView_circle_padding,
                    getResources().getDimensionPixelSize(R.dimen.pie_chart_default_circle_padding));
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.MoaChartView_mark_text_size,
                    context.getResources().getDimensionPixelSize(R.dimen.pie_chart_default_mark_text_size));
            disperseLength = typedArray.getDimensionPixelSize(R.styleable.MoaChartView_disperse_length,
                    context.getResources().getDimensionPixelSize(R.dimen.pie_chart_default_disperse_length));

            init(context, null, 0, isRound, showShadow, shadowColor, backgroundColor);
        } finally {
            typedArray.recycle();
        }

        getArcBorderPaint();
    }

// #MARK - Override class methods

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(clear){
            clear = false;
            clear(canvas);
        }else{
            animatedDraw(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        onReFindRectf();
    }

    @Override
    protected void onSizeChanged (int width, int height, int oldWidth, int oldHeight){
        // this.width = width;
        // this.height = height - getPaddingTop() - getPaddingBottom();
        //width = height;
        onReFindRectf();
    }



// #MARK - Custom methods

    private void init(Context context, List<MoaChartItem> itemsList, int maxValue, boolean isRound, boolean showShadow, int shadowColor, int backgroundColor){
        this.context = context;
        this.chartItemsList = itemsList;
        this.isRound = isRound;
        this.isShadowShowing = showShadow;
        this.shadowBackgroundColor = shadowColor;
        this.chartBackgroundColor = backgroundColor;
        this.totalValue = maxValue;
        currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currentPaint.setStyle(Paint.Style.FILL);
        invalidateTextPaintAndMeasurements();
    }

    private int measureWidth(int widthMeasureSpec){
        int result ;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        } else {
            result = screenWidth;
            if(specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        //this.width = result;
        return result;
    }

    private int measureHeight(int heightMeasureSpec){
        int result ;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;
        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        } else {
            result = screenHeight;
            if(specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        //this.height = result - getPaddingTop() - getPaddingBottom();
        //width = height;
        return result;
    }

    public void reset(){
        if(chartItemsList != null){
            chartItemsList.clear();
        }

        globalCurrentAngle = 0;
    }

    public void setRound(boolean state){
        this.isRound = state;
    }

    public void setShadowShowingState(boolean state){
        this.isShadowShowing = state;
    }

    public boolean getRound(){
        return this.isRound;
    }

    public boolean getShadowShowingState(){
        return this.isShadowShowing;
    }

    public void setTypeface(Typeface typeface){
        this.typeface = typeface;
    }

    public void setChartItemsList(List<MoaChartItem> itemsList){
        if(itemsList == null || itemsList.size() <= 0){
            totalValue = 0;
            return;
        }
        totalValue = 0;

        ListIterator<MoaChartItem> listIterator = itemsList.listIterator();
        while(listIterator.hasNext()){
            MoaChartItem item = listIterator.next();
            if(item.getValue() <= 0){
                listIterator.remove();
            }
        }
        for(MoaChartItem item : itemsList){
            totalValue += item.getValue();
        }

        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(1);  //保留到小数点后1位        显示：47.0%
        float angle = 360f;
        float startAngle = -90.f;
        for(MoaChartItem item : itemsList){
            double percent = item.getValue() / totalValue;
            if(percent < 0.001){
                percent = 0.001;//最小是0.1%
            }
            item.remark = nf.format(percent);
            item.sweepAngle = (float) (angle * percent);
            item.startAngle = startAngle;
            startAngle += item.sweepAngle;
        }

        this.chartItemsList = itemsList;
    }

    public void setShadowBackgroundColor(int color){
        this.shadowBackgroundColor = color;
    }

    public void setChartBackgroundColor(int color){
        this.chartBackgroundColor = color;
    }

    public void setTotalValue(int totalValue){
        this.totalValue = totalValue;
    }

    private double getPercent(double value, double maxValue){
        return value/maxValue;
    }


    /**
     * 绘制饼状图，带动画
     * */
    public void playAnimation(){

        ValueAnimator anim = ValueAnimator.ofFloat(0f, 360.0f);
        anim.setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                globalCurrentAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        TimeInterpolator interpolator = new LinearInterpolator();
        anim.setInterpolator(interpolator);
        anim.setStartDelay(100);
        clearDraw();
        anim.start();
    }

    private boolean  clear = false;

    private void clear(Canvas canvas){
        canvas.drawColor(chartBackgroundColor);
    }

    public void clearDraw(){
        clear = true;
        globalCurrentAngle = 0f;
        invalidate();
    }
// #MARK - Drawing Methods

    /**
     * 静态绘制
     * */

    public void regularDraw(){
        globalCurrentAngle = 360.0f;
        invalidate();
    }

    private void onReFindRectf(){
        int radius = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;//半径
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int rleft = centerX - radius;
        int rRight = centerX + radius;
        int rtop = centerY - radius;
        int rbottom = centerY + radius;
        rect.set(rleft, rtop, rRight, rbottom);
    }


    public void regularDraw(Canvas canvas){
      /*  Paint insideShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideShadowPaint.setColor(shadowBackgroundColor);
        Paint insideChartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideChartPaint.setColor(chartBackgroundColor);*/

        //drawMainCircle(canvas, insideShadowPaint, insideChartPaint, rect);

        if(this.chartItemsList != null && this.totalValue > 0){
            drawItems(canvas, rect);
            if(isMarkerShow){
                drawMarker(canvas,rect);//绘制标注
            }
            // drawInsideCircle(canvas, insideShadowPaint, insideChartPaint);
        }
    }


    /**
     * 绘制扇形，带动画效果，每一帧对要做该动作
     * */
    private void animatedDraw(Canvas canvas){
        Log.i("chart","animatedDraw");
       /* Paint insideShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideShadowPaint.setColor(shadowBackgroundColor);
        Paint insideChartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideChartPaint.setColor(chartBackgroundColor);*/

        // drawMainCircle(canvas, insideShadowPaint, insideChartPaint, rect);//阴影
        if(chartItemsList != null && totalValue > 0){//数据有效
            drawItems(canvas, rect);//把所有扇形都绘制了
            //关键代码，截取出扇形

            /*if(!isRound){
                if(isShadowShowing){
                    canvas.drawCircle(width/2, height/2, width/4 - 10, insideShadowPaint);
                }
                canvas.drawCircle(width/2, height/2, width/4 - 20, insideChartPaint);
            }*/
            if(globalCurrentAngle >= 360){
                Log.i("chart","globalCurrentAngle >= 360");
                if(isMarkerShow){
                    drawMarker(canvas,rect);//绘制标注
                }
            }
        }
    }

   /* private void drawInsideCircle(Canvas canvas, Paint insideShadowPaint, Paint insideChartPaint){
        if(!isRound){
            if(isShadowShowing){
                canvas.drawCircle(width/2, height/2, width/4 - 10, insideShadowPaint);
            }
            canvas.drawCircle(width/2, height/2, width/4 - 20, insideChartPaint);
        }
    }*/

    /*private void drawMainCircle(Canvas canvas, Paint insideShadowPaint, Paint insideChartPaint, RectF mainRectangle){
        if(isShadowShowing){
            canvas.drawCircle(width/2, height/2, width/2, insideShadowPaint);
        }
        canvas.drawArc(mainRectangle, 0f, 360f, true, insideChartPaint);
    }*/

    /**
     * 所有绘制扇形
     */
    private void drawItems(Canvas canvas, RectF mainRectangle){
        float startAngle;//从时钟的12点中方向开始画
        float sweepAngle;
        float totalSweepAngle = 0;
        MoaChartItem currentItem;
        for(int i = 0; i < chartItemsList.size(); i++){
            currentItem = chartItemsList.get(i);
            sweepAngle = currentItem.sweepAngle;//通过比例获取扇形的角度大小
            startAngle = currentItem.startAngle;//扇形的起始位置
            currentPaint.setColor(currentItem.getColor());

            if(totalSweepAngle + sweepAngle > globalCurrentAngle ){
                float realSweepAngle = globalCurrentAngle - totalSweepAngle;
                //绘制边框
                canvas.drawArc(mainRectangle, startAngle, realSweepAngle, true, currentPaint);
                if(isDisperse && chartItemsList.size() > 1 ){//只有一个扇形的时候不用画分割线
                    renderArcBorder(canvas,mainRectangle,currentItem.startAngle,sweepAngle);
                }

                break;
            }

            canvas.drawArc(mainRectangle, startAngle, sweepAngle, true, currentPaint);
            //绘制边框
            if(isDisperse && chartItemsList.size() > 1){//只有一个扇形的时候不用画分割线
                renderArcBorder(canvas,mainRectangle,startAngle,sweepAngle);
            }
             totalSweepAngle += sweepAngle;
        }
    }


    protected void clearPieChart(Canvas canvas, RectF mainRectangle){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(chartBackgroundColor);
        canvas.drawArc(mainRectangle, 0, 360, true, currentPaint);
        invalidate();
    }

    //**************绘制扇形边框相关代码，其实就是绘制一个圆弧************************\\
    /**
     * 获得绘制扇形边框画笔
     * @return	画笔
     */
    public Paint getArcBorderPaint()
    {
        if(null == mPaintArcBorder)
        {
            mPaintArcBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaintArcBorder.setStyle(Paint.Style.STROKE);
            mPaintArcBorder.setColor(chartBackgroundColor);
            mPaintArcBorder.setStrokeWidth(splitLineStroke);

        }
        return mPaintArcBorder;
    }

    /**
     * 绘制扇形边框
     * @param canvas 画布
     * @param rect 边框所在圆形
     * @param offsetAngle 开始角度
     * @param sweepAngle 结束角度
     */
    protected void renderArcBorder(Canvas canvas,RectF rect,
                                   float offsetAngle,float sweepAngle) {
        //边框
        if(null != mPaintArcBorder)
        {   //绘制，
           /* Path startBorder = new Path();
            startBorder.close();
            double rotateAngel = offsetAngle;// 标记线和水平相差旋转的角度
            float circleRadius = rect.width() / 2;//圆形半径

            //(circleRadius + 2) 把扇形分割线长度 + 2 是为了弥补误差带来的长度损失
            final float xS = (float) (rect.centerX() + (circleRadius + 2) * Math.cos(Math.toRadians(rotateAngel)));
            final float yS = (float) (rect.centerY() + (circleRadius + 2) * Math.sin(Math.toRadians(rotateAngel)));

            startBorder.moveTo(xS, yS);
            startBorder.lineTo(rect.centerX(), rect.centerY());
            canvas.drawPath(startBorder,mPaintArcBorder);


            Path endBorder = new Path();
            endBorder.close();
            double rotateAngel1 = offsetAngle + sweepAngle;// 标记线和水平相差旋转的角度

            //(circleRadius + 2) 把扇形分割线长度 + 2 是为了弥补误差带来的长度损失
            final float xS1 = (float) (rect.centerX() + (circleRadius + 2) * Math.cos(Math.toRadians(rotateAngel1)));
            final float yS1 = (float) (rect.centerY() + (circleRadius + 2) * Math.sin(Math.toRadians(rotateAngel1)));

           // startBorder.moveTo(rect.centerX(), rect.centerY());
            startBorder.lineTo(xS1, yS1);
            canvas.drawPath(startBorder,mPaintArcBorder);*/

        /*    if(sweepAngle >= 300f){
                offsetAngle = offsetAngle + sweepAngle;
                sweepAngle = Math.abs(360f - sweepAngle);
            }*/
            RectF oval = new RectF(rect.left - splitLineStroke,rect.top - splitLineStroke,rect.right + splitLineStroke,rect.bottom + splitLineStroke);
            canvas.drawArc(oval, offsetAngle, sweepAngle, true, mPaintArcBorder);
        }
    }

    protected void disperse(Canvas canvas,RectF rect){

        for(MoaChartItem item : chartItemsList){
            transSector(canvas, rect, item);
        }
    }

    protected void transSector(Canvas canvas,RectF rect, MoaChartItem item){
        double rotateAngel = item.startAngle + item.sweepAngle / 2;// 标记线和水平相差旋转的角度
        float dis = disperseLength;

        //标注绘制的中心坐标
        final float xS = (float) (rect.centerX() +  dis * Math.cos(Math.toRadians(rotateAngel)));
        final float yS = (float) (rect.centerY() +  dis * Math.sin(Math.toRadians(rotateAngel)));

        float radius = rect.width() / 2 ;
        if(item.rectF == null){
            item.rectF = new RectF(xS - radius,yS - radius,xS + radius,yS + radius);
        }else{
            item.rectF.set(xS - radius,yS - radius,xS + radius,yS + radius);
        }

        currentPaint.setColor(item.getColor());
        canvas.drawArc(item.rectF, item.startAngle, item.sweepAngle, true,currentPaint);

    }

    //---------------------标注--------------------------\\

    protected TextPaint getMarkerLinePaint(){
        if(markerLinePaint == null){
            markerLinePaint = new TextPaint();
            markerLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            markerLinePaint.setStyle(Paint.Style.FILL);
            markerLinePaint.setColor(getResources().getColor(android.R.color.white));
            markerLinePaint.setTextAlign(Paint.Align.CENTER);
            markerLinePaint.setStrokeWidth(2.0f);
        }
        return markerLinePaint;
    }

    public void drawMarker(Canvas canvas,RectF rect){
        if(chartItemsList == null){
            return;
        }

        for(MoaChartItem item : chartItemsList){
            drawPercentageRemarker(canvas,
                    item.getColor(),
                    item.rectF == null ? rect : rect,
                    item.startAngle,
                    item.sweepAngle,
                    item.remark,
                    item.remarkTextColor);
        }
    }


    float cirPadding = 8;//标注距离饼状图的距离
    private float textBottom;
    /**
     * 记录文字大小
     */
    private float mTextSize = 14;
    /**
     * 绘制标注线和标记文字
     *
     * //@param canvas      画布
     //* @param color       标记的颜色
     */
    protected void drawPercentageRemarker(Canvas canvas, int color, RectF rect,
                                          float offsetAngle, float sweepAngle, String text,int remarkTextcolor) {
        TextPaint paint = getMarkerLinePaint();
        if(remarkTextcolor >= 0){
            paint.setColor(remarkTextcolor);
        }
        double rotateAngel = offsetAngle + sweepAngle / 2;// 标记线和水平相差旋转的角度
        float circleRadius = rect.width() / 4;//圆形半径的一半，即扇形的中心位置
        //如果只有一个扇形，则标注显示在圆心
        float dis = chartItemsList.size() > 1 ? circleRadius + splitLineStroke : 0;

        //标注绘制的中心坐标
        final float xS = (float) (rect.centerX() +  dis * Math.cos(Math.toRadians(rotateAngel)));
        final float yS = (float) (rect.centerY() +  dis * Math.sin(Math.toRadians(rotateAngel)));

        canvas.drawText(text, xS , yS + mTextHeight / 2 - textBottom, paint);
    }

    private void invalidateTextPaintAndMeasurements() {
        TextPaint paint = getMarkerLinePaint();
        paint.setTextSize(mTextSize);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        mTextHeight = fontMetrics.descent - fontMetrics.ascent;
        textBottom = fontMetrics.bottom;
    }

}
