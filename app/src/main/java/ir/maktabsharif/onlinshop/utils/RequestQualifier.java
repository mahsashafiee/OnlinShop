package ir.maktabsharif.onlinshop.utils;

import java.util.HashMap;
import java.util.Map;

import static ir.maktabsharif.onlinshop.utils.Utils.getQueryProductOrder;

public enum RequestQualifier {

    ON_SALE_PRODUCTS(null, new HashMap<String, String>(){{put("on_sale", String.valueOf(true));}}),
    RECENT_PRODUCTS(null, getQueryProductOrder("date")),
    POPULAR_PRODUCT(null, getQueryProductOrder("popularity")),
    TOP_RATED_PRODUCT(null, getQueryProductOrder("rating")),
    ALL_CATEGORIES("categories", new HashMap<String, String>(){{put("per_page", String.valueOf(15));}}),
    MAIN_CATEGORIES("categories", new HashMap<String, String>(){{put("parent", String.valueOf(0));}});

    private final String mPath;
    private final Map<String, String> mQueryParam;

    RequestQualifier(String path, Map<String, String> queryParam) {
        mPath = path;
        mQueryParam = queryParam;
    }

    public String getPath() {
        return mPath;
    }

    public Map<String, String> getQueryParam() {
        return mQueryParam;
    }
}
