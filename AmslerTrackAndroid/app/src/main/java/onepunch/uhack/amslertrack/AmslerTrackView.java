package onepunch.uhack.amslertrack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.*;
import android.view.*;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AmslerTrackView extends View {
    //Constants
    private static final int MARGIN_SIZE = 10;

    // Paints used to draw the Compass
    private Paint markerPaint;
    private Paint textPaint;
    private Paint mGridPaint;
    private Path mPath;
    private float prePointX;
    private float prePointY;
    private float curtPointX;
    private float curtPointY;


    /** Constructors **/
    public AmslerTrackView(Context context) {
        super(context);
        initCompassView();
    }

    public AmslerTrackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCompassView();
    }

    public AmslerTrackView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        initCompassView();
    }

    /** Initialize the Class variables **/
    protected void initCompassView() {
        setFocusable(true);

        // Get a reference to the external resources
        Resources r = this.getResources();

        // Create the paints
        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint.setColor(R.color.background_color);
        mGridPaint.setStrokeWidth(2);
        mGridPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        markerPaint = new Paint();;
        markerPaint.setAntiAlias(true);
        markerPaint.setStrokeWidth(6f);
        markerPaint.setColor(Color.BLACK);

        markerPaint.setStyle(Paint.Style.STROKE);
        markerPaint.setStrokeJoin(Paint.Join.ROUND);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(r.getColor(R.color.text_color));

        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // The compass is a circle that fills as much space as possible.
        // Set the measured dimensions by figuring out the shortest boundary,
        // height or width.
        int measuredWidth = measure(widthMeasureSpec);
        int measuredHeight = measure(heightMeasureSpec);

        int d = Math.min(measuredWidth, measuredHeight);

        setMeasuredDimension(d, d);
    }

    private int measure(int measureSpec) {
        int result;

        // Decode the measurement specifications.
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED) {
            // Return a default size of 200 if no bounds are specified.
            result = 200;
        } else {
            // As you want to fill the available space
            // always return the full available bounds.
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int nWidth = getMeasuredWidth();
        int nHeight = getMeasuredHeight();
        int nSideLen = Math.min(nWidth, nHeight) - (MARGIN_SIZE * 2);

        drawGrid(canvas, 10, 25, nSideLen);
        canvas.drawPath(mPath, markerPaint);
    }

    private void drawGrid(Canvas canvas, float nPosX, float nPosY, int nSideLen) {
        float fGridSize = (float)(nSideLen / 20.0);
        // Draw the center circle
        float fRadius = (float)(nSideLen/100.0);

        canvas.drawCircle((float)(nPosX + nSideLen/2.0), (float)(nPosY + nSideLen/2.0),
                fRadius, mGridPaint);

        //Draw the grid and circle in the center
        for (int i = 0;i < 21;i++) {
            canvas.drawLine((nPosX + i*fGridSize), nPosY, (nPosX + i*fGridSize), (nPosY + nSideLen), mGridPaint);
            canvas.drawLine(nPosX, (nPosY + i*fGridSize), (nPosX + nSideLen), (nPosY + i*fGridSize), mGridPaint);
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        float nPosX = event.getX();
        float nPosY = event.getY();

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mPath.lineTo(nPosX, nPosY);
            invalidate();
            return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            //ProgressBar spinner = findViewById(R.id.progressBar);
            //spinner.setVisibility(View.VISIBLE);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Warning! It seems you are not fixating at the centre");
            builder.setTitle("Warning!");
            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

            //Pop up window to tell user result
            Toast.makeText(getContext(),"Warning! It seems you are not fixating at the centre",
                    Toast.LENGTH_SHORT).show();

            return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPath.moveTo(nPosX, nPosY);

            return true;
        }
        else {
            return false;
        }
    }
}