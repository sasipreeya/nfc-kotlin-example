package com.jetruby.nfc.example.view.custom;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;

import java.lang.ref.WeakReference;

/**
 * source code: https://stackoverflow.com/questions/25628258/align-text-around-imagespan-center-vertical
 */
public class CenteredImageSpan extends ImageSpan {

    private WeakReference<Drawable> mDrawableRef;
    private int mSpacingSize;
    private int extraSpace = 0;

    public CenteredImageSpan(Drawable drawable) {
        this(drawable, 0);
    }

    public CenteredImageSpan(Drawable drawable, int spacingSize) {
        super(drawable);
        mDrawableRef = new WeakReference<>(drawable);
        mSpacingSize = spacingSize;
    }

    @Override
    public int getSize(Paint paint, CharSequence text,
                       int start, int end,
                       Paint.FontMetricsInt fm) {
        Drawable d = getCachedDrawable();
        if (d == null) {
            return 0;
        }
        Paint.FontMetricsInt pfm = paint.getFontMetricsInt();
        Rect rect = d.getBounds();
        if (fm != null) {
            // Centers the text with the ImageSpan.
            if (rect.bottom - (pfm.descent - pfm.ascent) >= 0) {
                // Stores the initial descent and computes the margin available.
                extraSpace = rect.bottom - (pfm.descent - pfm.ascent);
            }

            fm.descent = extraSpace / 2 + pfm.descent;
            fm.bottom = pfm.descent;

            fm.ascent = pfm.descent - rect.bottom;
            fm.top = pfm.ascent;
        }

        return rect.right + mSpacingSize;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, @NonNull Paint paint) {
        Drawable b = getCachedDrawable();
        canvas.save();

        int drawableHeight = b.getIntrinsicHeight();
        int canvasHeight = canvas.getHeight();
        int fontAscent = paint.getFontMetricsInt().ascent;
        int fontDescent = paint.getFontMetricsInt().descent;

        // Adjust drawable height not exceed canvas height.
        if (drawableHeight > canvasHeight) {
            int drawableWidth = b.getIntrinsicWidth();
            float heightChangeRatio = (float) canvasHeight / drawableHeight;
            // Change drawable width by ratio of height diff.
            int newDrawableWidth = (int) (drawableWidth * heightChangeRatio);
            // Replace drawable height with canvas height.
            drawableHeight = canvasHeight;

            b.setBounds(0, 0, newDrawableWidth, drawableHeight);
        }

        int transY = bottom - b.getBounds().bottom + // align bottom to bottom
                (drawableHeight - fontDescent + fontAscent) / 2; // align center to center

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

    // Redefined locally because it is a private member from DynamicDrawableSpan
    private Drawable getCachedDrawable() {
        WeakReference<Drawable> wr = mDrawableRef;
        Drawable d = null;

        if (wr != null)
            d = wr.get();

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<>(d);
        }

        return d;
    }
}
