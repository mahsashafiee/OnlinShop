package ir.maktabsharif.onlinshop.utils;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static String getStringURL(@Nullable String path, @NonNull Map<String, String> queryParam) {

        Uri.Builder uri = Uri.parse(Constants.BASE_URL)
                .buildUpon()
                .appendPath("products")
                .appendQueryParameter("consumer_key", Constants.CONSUMER_KEY)
                .appendQueryParameter("consumer_secret", Constants.CONSUMER_SECRET);

        if (path != null)
            uri.appendPath(path);

        for (Map.Entry<String, String> entry : queryParam.entrySet()) {
            uri.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        return uri.build().toString();
    }

    public static Map<String, String> getQueryProductOrder(String orderby){
        return new HashMap<String, String>(){{put("orderby", orderby);}};
    }
}

