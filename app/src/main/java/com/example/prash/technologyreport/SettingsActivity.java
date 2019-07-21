package com.example.prash.technologyreport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by prash on 7/25/2018. This Activity helps narrow down user preference for type of stories they want
 * to read.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference topic = findPreference(getString(R.string.settings_topic_key));
            bindPreferanceSummaryToValue(topic);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value = newValue.toString();
            preference.setSummary(value);
            return true;
        }

        private void bindPreferanceSummaryToValue(Preference pref){
            pref.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(pref.getContext());
            String prefString = preferences.getString(pref.getKey(),"");
            onPreferenceChange(pref,prefString);
        }
    }
}
