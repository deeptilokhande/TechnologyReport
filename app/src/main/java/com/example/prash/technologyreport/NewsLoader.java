package com.example.prash.technologyreport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by prash on 7/15/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    //Query url
    private String mUrl;

    //Contructor for loader
    public NewsLoader(Context c, String url) {
        super(c);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<News> newsList = QueryUtlis.fetchNewsData(mUrl);
        return newsList;
    }
}
