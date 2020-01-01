package ir.maktabsharif.onlinshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import ir.maktabsharif.onlinshop.R;
import ir.maktabsharif.onlinshop.models.Category;
import ir.maktabsharif.onlinshop.network.WooCommerceRequestQueue;

public class DiscountSliderAdapter extends SliderViewAdapter<DiscountSliderAdapter.DiscountImageHolder> {

    private List<Category> mCategories;
    private Context mContext;

    public DiscountSliderAdapter(Context context, List<Category> categories) {
        mContext = context;
        mCategories = categories;
    }

    @Override
    public DiscountImageHolder onCreateViewHolder(ViewGroup parent) {
        return new DiscountImageHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_image_slider, null));
    }

    @Override
    public void onBindViewHolder(DiscountImageHolder viewHolder, int position) {
        viewHolder.bind(mCategories.get(position));
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    public class DiscountImageHolder extends SliderViewAdapter.ViewHolder {

        private NetworkImageView mSliderImage;
        private ImageLoader mImageLoader;
        private Category mCategory;

        public DiscountImageHolder(View itemView) {
            super(itemView);
            mSliderImage = ((NetworkImageView) itemView);
            mImageLoader = WooCommerceRequestQueue.getInstance(mContext).getImageLoader();
        }

        public void bind(Category category){
            mCategory = category;
            mImageLoader.get(mCategory.getImage().getURL(),
                    ImageLoader.getImageListener(mSliderImage,
                            R.drawable.image_placeholder,
                            R.drawable.error_placeholder));


            mSliderImage.setImageUrl(mCategory.getImage().getURL(), mImageLoader);


        }
    }
}
