package anton.top.dev.horizontalcompassview.compassView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/*
 * Created by Anton Popov.
 */
public abstract class AbstractCompassGauge extends View {

    protected final Paint stripPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    protected final Paint needlePaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    protected final Paint degreesTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    protected final Paint directionTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    protected final Paint needleBgPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

    private static final int NEEDLE_HEIGHT_DP = 35;
    private static final int NEEDLE_WIDTH_DP = 2;
    private static final int DEGREES_TEXT_SIZE_SP = 9;
    private static final int DIRECTION_TEXT_SIZE_SP = 16;
    private static final int LINE_HEIGHT_DP = 10;
    private static final int LINE_MARGIN_TOP_BOTTOM_DP = 7;
    private static final int CIRCLE_RADIUS_DP = 1;

    protected float needleHeightPx;
    protected float needleWidthPx;
    protected float degreesTextSizePx;
    protected float directionTextSizePx;
    protected float lineHeightPx;
    protected float lineMarginTopBottomPx;
    protected float circleRadiusPx;

    public AbstractCompassGauge(Context context, AttributeSet attrs) {
        super(context, attrs);

        initSizes(context);
        initPaint();
    }

    private void initSizes(Context context) {
        needleHeightPx = convertDpToPixels(context, NEEDLE_HEIGHT_DP);
        needleWidthPx = convertDpToPixels(context, NEEDLE_WIDTH_DP);
        lineHeightPx = convertDpToPixels(context, LINE_HEIGHT_DP);
        degreesTextSizePx = convertSpToPixels(context, DEGREES_TEXT_SIZE_SP);
        directionTextSizePx = convertSpToPixels(context, DIRECTION_TEXT_SIZE_SP);
        lineMarginTopBottomPx = convertDpToPixels(context, LINE_MARGIN_TOP_BOTTOM_DP);
        circleRadiusPx = convertDpToPixels(context, CIRCLE_RADIUS_DP);
    }

    private void initPaint() {
        stripPaint.setColor(Color.WHITE);
        stripPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        stripPaint.setStrokeWidth(2.0f);
        stripPaint.setAntiAlias(true);
        degreesTextPaint.setColor(Color.WHITE);
        degreesTextPaint.setTextSize(degreesTextSizePx);
        directionTextPaint.setColor(Color.WHITE);
        directionTextPaint.setTextSize(directionTextSizePx);
        needlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        needlePaint.setColor(Color.rgb(127, 240, 30));
        needlePaint.setStrokeWidth(2.0f);
        needlePaint.setAntiAlias(true);

        needleBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        needleBgPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = resolveSize(getSuggestedMinimumWidth(),
                widthMeasureSpec);

        final int height = resolveSize(getSuggestedMinimumHeight(),
                heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    protected String getStringFromDegree(int degree) {
        String result = "";
        switch (degree) {
            case 0:
                result = "N";
                break;
            case 45:
                result = "NE";
                break;
            case 90:
                result = "E";
                break;
            case 135:
                result = "SE";
                break;
            case 180:
                result = "S";
                break;
            case 225:
                result = "SW";
                break;
            case 270:
                result = "W";
                break;
            case 315:
                result = "NW";
                break;
            default:
                break;
        }

        return result;
    }

    protected float convertDpToPixels(Context ctx, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getResources().getDisplayMetrics());
    }

    protected float convertSpToPixels(Context ctx, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, ctx.getResources().getDisplayMetrics());
    }

}