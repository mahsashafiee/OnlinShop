package ir.maktabsharif.onlinshop.utils;

import android.content.Context;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;

public class SquareNetworkImage extends NetworkImageView {

    public SquareNetworkImage(Context context) {
        super(context);
    }

    public SquareNetworkImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareNetworkImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
