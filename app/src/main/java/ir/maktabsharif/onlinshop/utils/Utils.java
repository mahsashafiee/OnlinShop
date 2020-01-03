package ir.maktabsharif.onlinshop.utils;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.toolbox.NetworkImageView;

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

    public static void setPaddingInDp(Context context, NetworkImageView imageView, int dpPadding) {
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (dpPadding * scale + 0.5f);
        imageView.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
    }

    public static Map<String, String> getQueryProductOrder(String orderby) {
        return new HashMap<String, String>() {{
            put("orderby", orderby);
        }};
    }

    public static String textProcessor(String string) {

        StringBuilder text = new StringBuilder();

        if (string != null) {
            for (Character character : string.toCharArray()) {
                if (character.equals('<') || character.equals('>') || character.equals('/') ||
                        character.equals('p') || character.equals('b') || character.equals('r')) {
                } else
                    text.append(character);
            }
        }

        return text.toString();
    }
}

