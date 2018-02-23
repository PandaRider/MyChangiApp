package com.example.chris.changiappv3;

import android.provider.BaseColumns;

/**
 * Created by chris on 25/11/2017.
 */

class LocationsContract {

    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME ="Locations";
        public static final String COL_AMOUNT ="Amount";
        public static final String COL_LOCATIONNAME ="LocationName";
    }
}
