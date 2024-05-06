package androidwithseonmin.org.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AppDatabase.db";
    private static final int DATABASE_VERSION = 2; // previously 1

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS USER (" +
                "user_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "number TEXT," +
                "image BLOB," +
                "birth DATE," +
                "age INTEGER," +
                "sex TEXT," +
                "password TEXT" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS MEMO (" +
                "memo_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "feeling TEXT," +
                "date DATE," +
                "body_text TEXT," +
                "image BLOB,\n" +
                "author_ID INTEGER," +
                "FOREIGN KEY (author_ID) REFERENCES USER(user_ID)" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS GROUPS (" +
                "group_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "description TEXT," +
                "title TEXT," +
                "date DATE" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS CALENDAR (" +
                "calendar_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "private INTEGER,\n" +
                "memo TEXT,\n" +
                "start_date DATE,\n" +
                "end_date DATE,\n" +
                "title TEXT,\n" +
                "user_ID INTEGER,\n" +
                "FOREIGN KEY (user_ID) REFERENCES USER(user_ID)\n" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS USER_GROUP (" +
                "user_ID INTEGER," +
                "group_ID INTEGER," +
                "FOREIGN KEY (user_ID) REFERENCES USER(user_ID)," +
                "FOREIGN KEY (group_ID) REFERENCES GROUPS(group_ID)," +
                "PRIMARY KEY (user_ID, group_ID)" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS GROUP_MEMO (" +
                "group_ID INTEGER," +
                "memo_ID INTEGER," +
                "FOREIGN KEY (group_ID) REFERENCES GROUPS(group_ID)," +
                "FOREIGN KEY (memo_ID) REFERENCES MEMO(memo_ID)," +
                "PRIMARY KEY (group_ID, memo_ID)" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS share_calendar (" +
                "calendar_ID INTEGER," +
                "group_ID INTEGER," +
                "FOREIGN KEY (calendar_ID) REFERENCES CALENDAR(calendar_ID)," +
                "FOREIGN KEY (group_ID) REFERENCES GROUPS(group_ID)," +
                "PRIMARY KEY (calendar_ID, group_ID)" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS DIARY_ENTRY (" +
                "author_ID INT," +
                "entry_date DATE," +
                "entry_weather VARCHAR(255)," +
                "entry_mood BLOB," +
                "entry_location TEXT(255)," +
                "entry_image BLOB," +
                "entry_voice VARCHAR(255)," +
                "entry_content TEXT," +
                "PRIMARY KEY(author_ID, entry_date)," +
                "FOREIGN KEY (author_ID) REFERENCES USER(user_ID)" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS HAPPINESS_BOX (" +
                "author_ID INT," +
                "box_date DATE," +
                "PRIMARY KEY(author_ID, box_date)," +
                "FOREIGN KEY (author_ID, box_date) REFERENCES DIARY_ENTRY(author_ID, entry_date)" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS NOTIFICATIONS (" +
                "notification_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "author_ID INT," +
                "content TEXT," +
                "vibrate INTEGER," +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (author_ID) REFERENCES USER(user_ID)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS MEMO");
        db.execSQL("DROP TABLE IF EXISTS GROUPS");
        db.execSQL("DROP TABLE IF EXISTS CALENDAR");
        db.execSQL("DROP TABLE IF EXISTS USER_GROUP");
        db.execSQL("DROP TABLE IF EXISTS GROUP_MEMO");
        db.execSQL("DROP TABLE IF EXISTS share_calendar");
        db.execSQL("DROP TABLE IF EXISTS DIARY_ENTRY");
        db.execSQL("DROP TABLE IF EXISTS HAPPINESS_BOX");
        db.execSQL("DROP TABLE IF EXISTS NOTIFICATIONS");
        onCreate(db);
    }
}
