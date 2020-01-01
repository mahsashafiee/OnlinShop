package ir.maktabsharif.onlinshop.utils;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public class Utils {
    public static String getStringURL(@NonNull List<String> paths, @NonNull Map<String, String> queryParam) {

        Uri.Builder uri = Uri.parse(Constants.BASE_URL)
                .buildUpon()
                .appendQueryParameter("consumer_key", Constants.CONSUMER_KEY)
                .appendQueryParameter("consumer_secret", Constants.CONSUMER_SECRET);

        for (String path : paths) { uri.appendPath(path); }

        for (Map.Entry<String, String> entry : queryParam.entrySet()) {
            uri.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        return uri.build().toString();
    }
}

