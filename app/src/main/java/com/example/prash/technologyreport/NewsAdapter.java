package com.example.prash.technologyreport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prash on 7/12/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    //Constructor to create NewsAdapter instance
    public NewsAdapter(Activity context, ArrayList<News> techNews) {
        super(context, 0, techNews);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView author = (TextView) convertView.findViewById(R.id.author);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView section = (TextView) convertView.findViewById(R.id.section);

        News currentNews = getItem(position);
        title.setText(currentNews.getmTitle());

        if (currentNews.getmAuthor() == "") {
            author.setVisibility(View.GONE);
        } else {
            author.setText(currentNews.getmAuthor());
        }
        date.setText(currentNews.getmDate());
        section.setText(currentNews.getmSection());

        return convertView;
    }
}
