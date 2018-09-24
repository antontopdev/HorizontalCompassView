package anton.top.dev.horizontalcompassview.compassView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/*
 * Created by Anton Popov.
 */
public class HorizontalCompassGauge extends AbstractCompassGauge {

    private static final int DEGREES_PER_GAUGE = 120;
    private static final int VIEW_HEIGHT = 60;
    private float canvasWidthPx;
    private float canvasHeightPx;

    Context mContext;

    public HorizontalCompassGauge(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        canvasWidthPx = metrics.widthPixels;
        canvasHeightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, VIEW_HEIGHT, metrics);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int leftDegree = Math.round(GlobalData.getBearing() - DEGREES_PER_GAUGE / 2);

        if (leftDegree < 0)
            leftDegree = 360 + leftDegree;

        float degreeWidth = canvasWidthPx / (float) DEGREES_PER_GAUGE;

        // Draw vertical stripes
        for (int i = 0; i < DEGREES_PER_GAUGE; i++) {
            int degree = (i + leftDegree) % 360;
            // Draw degree text
            if (degree % 30 == 0) {
                float textWidth = degreesTextPaint.measureText(String.valueOf(degree));
                canvas.drawText(String.valueOf(degree), i * degreeWidth - textWidth / 2, canvasHeightPx - lineHeightPx - 3 * lineMarginTopBottomPx + 5, degreesTextPaint);
            }

            if (degree % 10 == 0) {
                // Draw line
                canvas.drawLine(i * degreeWidth, canvasHeightPx - lineHeightPx - 2 * lineMarginTopBottomPx, i * degreeWidth, canvasHeightPx - 2 * lineMarginTopBottomPx - 2, stripPaint);
                // Draw circles
                canvas.drawCircle(i * degreeWidth + 5 * degreeWidth, canvasHeightPx - 2 * lineMarginTopBottomPx - lineHeightPx / 2 - 2, circleRadiusPx, stripPaint);
            }

            if (degree % 45 == 0) {
                // Draw direction text
                String directionText = getStringFromDegree(degree);
                float textWidth = directionTextPaint.measureText(directionText);
                canvas.drawText(directionText, i * degreeWidth - textWidth / 2, canvasHeightPx - 3 * lineMarginTopBottomPx - lineHeightPx - degreesTextSizePx, directionTextPaint);
            }

            drawNeedle(canvas);
        }

    }

    private void drawNeedle(Canvas canvas) {
        canvas.drawRect(canvasWidthPx / 2 - 4, 0, canvasWidthPx / 2 + 4, needleHeightPx - 3, needleBgPaint);

        if (!Utils.getSharedPreferences(mContext).getBoolean("btnTrueNorth", true)) {       //True North in settings
            needlePaint.setColor(Color.RED);
            canvas.drawRect(canvasWidthPx / 2 - 1, 0, canvasWidthPx / 2 + 1, needleHeightPx / 2, needlePaint);
            needlePaint.setColor(Color.BLUE);
            canvas.drawRect(canvasWidthPx / 2 - 1, needleHeightPx / 2 + 1, canvasWidthPx / 2 + 1, needleHeightPx, needlePaint);
        } else {
            needlePaint.setColor(Color.rgb(127, 240, 30));
            canvas.drawRect(canvasWidthPx / 2 - 1, 0, canvasWidthPx / 2 + 1, needleHeightPx - 5, needlePaint);
        }
    }
}