package com.jetruby.nfc.example.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.jetruby.nfc.example.R;

/**
 * Created by Jenbodin T. on 4/7/2018 AD.
 * Modified By Jenbodin T. on 13/2/2019
 * - add `adjustIconBounds` support auto-resize icon size to fit with line height
 * - support specific height and width properties
 * - adjust view height automatically if icon size is larger than view height
 * <br />
 * display TextView with icons in front of text
 * Example :
 * [x] "Hello Image Text View"
 * <p>
 * [x] is an icon image
 */
public class ImageTextView extends AppCompatTextView {

    private static final String TEMP = "[scbImage]";
    /**
     * unit of DP
     */
    private static final int DEFAULT_ICON_SIZE = 24;
    private int mHeight;
    private int mWidth;
    private int mSpacingSize;
    private Drawable mDrawable;
    private CharSequence mText;

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
        init();
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
        init();
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView, 0, 0);
        int size = a.getDimensionPixelSize(R.styleable.ImageTextView_logoSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_ICON_SIZE, getResources().getDisplayMetrics()));
        if (a.hasValue(R.styleable.ImageTextView_logoHeight) && a.hasValue(R.styleable.ImageTextView_logoWidth)) {
            mHeight = a.getDimensionPixelSize(R.styleable.ImageTextView_logoHeight, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_ICON_SIZE, getResources().getDisplayMetrics()));
            mWidth = a.getDimensionPixelSize(R.styleable.ImageTextView_logoWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_ICON_SIZE, getResources().getDisplayMetrics()));
        } else {
            mHeight = size;
            mWidth = size;
        }
        mSpacingSize = a.getDimensionPixelSize(R.styleable.ImageTextView_logoSpacingSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        mDrawable = a.getDrawable(R.styleable.ImageTextView_image);
        mText = a.getText(R.styleable.ImageTextView_text);
        if (a.getBoolean(R.styleable.ImageTextView_adjustIconBounds, false)) {
            fitIconSizeWithLineHeight();
        }
        a.recycle();
    }

    private void init() {
        if (mDrawable != null) {
            setDrawable(mDrawable);
        }
        if (!TextUtils.isEmpty(mText)) {
            setText(mText);
        }
    }

    public synchronized void setDrawableBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (bitmap.getWidth() > mWidth) {
            bitmap = resizeBitmap(bitmap, mWidth, mHeight);
        }
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        drawable.setBounds(0, 0, mWidth, mHeight);
        setTextWithDrawable(drawable);
    }

    public synchronized void setDrawable(int image) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), image);
        if (drawable != null) {
            drawable.setBounds(0, 0, mWidth, mHeight);
        }
        setTextWithDrawable(drawable);
    }

    public synchronized void setDrawable(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        if (bitmapDrawable.getIntrinsicWidth() > mWidth) {
            Bitmap bitmap = resizeBitmap(((BitmapDrawable) drawable).getBitmap(), mWidth, mHeight);
            drawable = new BitmapDrawable(getResources(), bitmap);
        }
        drawable.setBounds(0, 0, mWidth, mHeight);
        setTextWithDrawable(drawable);
    }

    private synchronized void setTextWithDrawable(Drawable drawable) {
        mDrawable = drawable;
        SpannableString spIcon = new SpannableString(TEMP);
        spIcon.setSpan(new CenteredImageSpan(drawable, mSpacingSize), 0, TEMP.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(new SpannableStringBuilder()
                .append(spIcon)
                .append(getText().toString().trim().replace(TEMP, ""))
        );
    }

    /**
     * resize better quality of bitmap, due to Bitmap.createScaledBitmap create bad quality
     * <p>
     * https://stackoverflow.com/questions/39222769/bitmap-scale-destroy-quality
     */
    public Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap resizedBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(resizedBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
        return resizedBitmap;
    }

    /**
     * remove icon and show only text
     */
    public void removeIcon() {
        mDrawable = null;
        CharSequence text = getText();
        if (text != null) {
            setText(text.toString().replace(TEMP, ""));
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        boolean isTextNotContainIcon = mDrawable != null && text != null && !text.toString().contains(TEMP);
        if (isTextNotContainIcon || TextUtils.isEmpty(text)) {
            setTextWithDrawable(mDrawable);
        }
    }

    @Override
    public CharSequence getText() {
        CharSequence text = super.getText();
        if (text != null && text.toString().startsWith(TEMP)) {
            text = text.toString().substring(TEMP.length());
        }
        return text;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight = getMeasuredHeight();
        if (mDrawable != null && measuredHeight != 0) {
            Paint.FontMetricsInt pfm = getPaint().getFontMetricsInt();
            int lineHeight = getTextLineHeight(pfm);
            Rect drawableBounds = mDrawable.getBounds();
            int drawableHeight = Math.abs(drawableBounds.top) - Math.abs(drawableBounds.bottom);
            if (lineHeight < drawableHeight) {
                int gab = ((drawableHeight - lineHeight) / 2);
                measuredHeight = measuredHeight + gab + pfm.bottom;
                setPadding(getPaddingLeft(), getPaddingTop() + gab, getPaddingRight(), getPaddingBottom());
                if (measuredHeight < drawableHeight) {
                    measuredHeight = drawableHeight;
                }
                setMeasuredDimension(getMeasuredWidth(), measuredHeight);
                return;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void fitIconSizeWithLineHeight() {
        int lineHeight = getTextLineHeight(getPaint().getFontMetricsInt());
        if (mHeight > lineHeight) {
            float scale = ((float) lineHeight / (float) mHeight);
            mHeight = lineHeight;
            mWidth = (int) (mWidth * scale);
        }
    }

    private int getTextLineHeight(Paint.FontMetricsInt pfm) {
        return Math.abs(pfm.top) + Math.abs(pfm.bottom);
    }

    public void setImageSize(int mSize) {
        this.mHeight = mSize;
        this.mWidth = mSize;
    }

    public int getImageHeight() {
        return mHeight;
    }

    public void setImageHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getImageWidth() {
        return mWidth;
    }

    public void setImageWidth(int mWidth) {
        this.mWidth = mWidth;
    }

}
