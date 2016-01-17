package tempcontroller.challenge.com.challenge75f.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import tempcontroller.challenge.com.challenge75f.R;
import tempcontroller.challenge.com.challenge75f.enums.Visibility;

/**
 * Created by Hemant on 9/26/15.
 */
public class CustomArcProgressBar extends View {

    private static final double PI = 22/7;
    private static final int OUTER_CIRCLE_WIDTH = 30;

    //Paint instances
    private Paint circlePaint;
    private Paint innerCirclePaint;
    private Paint circleBarsPaint;

    //Attrs
    private int outerCircleFillColor;
    private int innerCircleFillColor;
    private int circleBarColors;
    private int circleShadowColors;
    private int circleStartAngle;
    private int circleEndAngle;
    private int visibility;
    private float strokeWidth;


    public CustomArcProgressBar(Context context) {
        super(context);
    }


    public CustomArcProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initView(context, attrs);
    }

    public CustomArcProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        setFocusable(true);

        //get attributes specified in attrs.xml
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomArcProgressBar, 0, 0);

        try {
            outerCircleFillColor = a.getColor(R.styleable.CustomArcProgressBar_outer_circle_color, 0);
            innerCircleFillColor = a.getColor(R.styleable.CustomArcProgressBar_inner_circle_color, 0);
            circleBarColors = a.getColor(R.styleable.CustomArcProgressBar_inner_circle_bar_colors, 0);
            circleShadowColors = a.getColor(R.styleable.CustomArcProgressBar_outer_circle_shadow_color, 0);
            visibility = a.getInt(R.styleable.CustomArcProgressBar_outer_circle_shadow_visibility, Visibility.GONE.ordinal());
            strokeWidth = a.getFloat(R.styleable.CustomArcProgressBar_circle_stroke_width, (float)0.0);
        }finally {
            a.recycle();
        }

        circlePaint = new Paint();
        circlePaint.setShadowLayer(70, 0, 0, innerCircleFillColor);
        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleBarsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth()- getPaddingLeft() - getPaddingRight();
        int measureHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int size = Math.max(measuredWidth, measureHeight);
        setMeasuredDimension(size+getPaddingLeft()+getPaddingRight(), size+getPaddingTop()+getPaddingBottom());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //setBackgroundColor(getResources().getColor(R.color.atmospheric_temperature_color));

        int xCoord = getMeasuredWidth()/2;
        int yCoord = getMeasuredHeight()/2;

        //get the radius as half of the width or height, whichever is smaller
        //subtract ten so that it has some space around it
        int radius = 0;
        if(xCoord>yCoord)
            radius=xCoord;
        else
            radius=yCoord;


        radius = radius-(3*OUTER_CIRCLE_WIDTH)-(int)strokeWidth;

        //design outer circle
        circlePaint.setColor(outerCircleFillColor);
        int outerCircleRadius = radius + OUTER_CIRCLE_WIDTH;
        canvas.drawCircle(xCoord, yCoord, outerCircleRadius, circlePaint);

        //design inner circle
        innerCirclePaint.setColor(innerCircleFillColor);
        innerCirclePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(xCoord, yCoord, radius, innerCirclePaint);

        //design bars on inner circle
        circleBarsPaint.setColor(outerCircleFillColor);
        circleBarsPaint.setStrokeWidth((float) 5.0);
        for(int i=0; i<=360;) {
            double cosPI = Math.cos(i*PI);
            double sinPI = Math.sin(i*PI);
            double barX1Coord = xCoord+(radius-20)*cosPI;
            double barY1Coord = yCoord+(radius-20)*sinPI;
            double barX2Coord = xCoord+(radius-60)*cosPI;
            double barY2Coord = yCoord+(radius-60)*sinPI;

            canvas.drawLine((float)barX1Coord, (float)barY1Coord, (float)barX2Coord, (float)barY2Coord, circleBarsPaint);
            i+=20;
        }
    }

    //Rotating view
    public void doRotation(int degree) {
        setRotation(degree);
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }
}
