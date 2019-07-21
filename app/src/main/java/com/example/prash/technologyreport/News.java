package com.example.prash.technologyreport;

/**
 * Created by prash on 7/12/2018. News Class consisting of 4 elements Title of The story, author of it, Date published
 * and section of news.
 */

public class News {
    private String mTitle;
    private String mAuthor;
    private String mDate;
    private String mSection;
    private String mUrl;

    //Constructor to create News Object
    public News(String title, String author, String date, String section, String url) {
        mTitle = title;
        mAuthor = author;
        mDate = date;
        mSection = section;
        mUrl = url;

    }

    //Getter methods for getting details of News Object
    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmUrl() {
        return mUrl;
    }


}
