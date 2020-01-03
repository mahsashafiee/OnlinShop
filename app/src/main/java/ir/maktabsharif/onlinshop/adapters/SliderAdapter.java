package ir.maktabsharif.onlinshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.models.Image;
import ir.maktabsharif.onlinshop.models.Product;
import ir.maktabsharif.onlinshop.network.WooCommerceRequestQueue;
import ir.maktabsharif.onlinshop.utils.SliderQualifier;

import static ir.maktabsharif.onlinshop.utils.Utils.setPaddingInDp;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.DiscountImageHolder> {

    private Product mProduct;
    private Context mContext;
    private SliderQualifier mQualifier;

    public SliderAdapter(Context context, Product product, @NonNull SliderQualifier qualifier) {
        mContext = context;
        mProduct = product;
        mQualifier = qualifier;
    }

    @Override
    public DiscountImageHolder onCreateViewHolder(ViewGroup parent) {

        return new DiscountImageHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_image_product_slider, null));
    }

    @Override
    public void onBindViewHolder(DiscountImageHolder viewHolder, int position) {
        viewHolder.bind(mProduct.getImages().get(position));
    }

    @Override
    public int getCount() {
        return mProduct.getImages().size();
    }

    public class DiscountImageHolder extends SliderViewAdapter.ViewHolder {

        private NetworkImageView mSliderImage;
        private ImageLoader mImageLoader;

        public DiscountImageHolder(View itemView) {
            super(itemView);
            mSliderImage = ((NetworkImageView) itemView);
            mImageLoader = WooCommerceRequestQueue.getInstance(mContext).getImageLoader();

            if (mQualifier.equals(SliderQualifier.HOME_SLIDER))
                mSliderImage.setScaleType(ImageView.ScaleType.FIT_XY);
            else {
                setPaddingInDp(mContext, mSliderImage, 8);
                mSliderImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
        }

        public void bind(Image image) {
            mImageLoader.get(image.getURL(),
                    ImageLoader.getImageListener(mSliderImage,
                            R.drawable.product_image_placeholder,
                            R.drawable.product_error_placeholder));

            mSliderImage.setImageUrl(image.getURL(), mImageLoader);

        }
    }
}
