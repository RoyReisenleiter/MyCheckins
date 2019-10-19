package task2.task2.database;

public class CheckinsDbSchema {

    public static final class CheckinsTable {
        public static final String NAME = "checkins";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String LIKED = "solved";
            public static final String DISLIKED = "dislike";
            public static final String SUSPECT = "suspect";
            public static final String LONGITUDE = "longitude";
            public static final String LATITUDE = "latitude";
            public static final String PLACE = "place";
            public static final String DETAILS = "details";

        }
    }
}
