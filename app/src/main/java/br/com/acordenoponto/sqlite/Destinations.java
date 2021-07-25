package br.com.acordenoponto.sqlite;

import android.provider.BaseColumns;

public class Destinations {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Destinations() {}

    /* Inner class that defines the table contents */
    public static class DestinationEntry implements BaseColumns {
        public static final String TABLE_NAME = "destinations";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LONG = "long";
    }
}
