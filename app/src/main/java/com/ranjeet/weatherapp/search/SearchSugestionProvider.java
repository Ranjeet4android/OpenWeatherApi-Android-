package com.ranjeet.weatherapp.search;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Tomas on 03.08.2015
 */
public class SearchSugestionProvider extends SearchRecentSuggestionsProvider{
    public final static String AUTHORITY = "com.ranjeet.weatherapp.search.SearchSugestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;
    public SearchSugestionProvider(){
        setupSuggestions(AUTHORITY, MODE);
    }
}
