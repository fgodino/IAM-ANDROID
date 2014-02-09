package com.godino.emotion.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.godino.emotion.models.Person;
import com.godino.emotion.models.Status;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PostORM {

    private static final String TAG = "PostORM";

    private static final String TABLE_NAME = "friends";

    private static final String COMMA_SEP = ", ";

    private static final String COLUMN_ID_TYPE = "INTEGER PRIMARY KEY";
    private static final String COLUMN_ID = "id";

    private static final String COLUMN_NAME_TYPE = "TEXT";
    private static final String COLUMN_NAME = "name";

    private static final String COLUMN_PICTURE_TYPE = "TEXT";
    private static final String COLUMN_PICTURE = "picture_url";
    
    private static final String COLUMN_STATUS_TYPE = "TEXT";
    private static final String COLUMN_STATUS = "status";

    private static final String COLUMN_DATE_TYPE = "TEXT";
    private static final String COLUMN_DATE = "pubdate";


    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " " + COLUMN_ID_TYPE + COMMA_SEP +
                COLUMN_NAME  + " " + COLUMN_NAME_TYPE + COMMA_SEP +
                COLUMN_PICTURE + " " + COLUMN_PICTURE_TYPE + COMMA_SEP +
                COLUMN_STATUS + " " + COLUMN_STATUS_TYPE + COMMA_SEP +
                COLUMN_DATE + " " + COLUMN_DATE_TYPE +
            ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    
    private static final SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    
    public static void insertPerson(Context context, Person person) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getWritableDatabase();

        ContentValues values = postToContentValues(person);
        long postId = database.insert(PostORM.TABLE_NAME, "null", values);
        Log.i(TAG, "Inserted new Post with ID: " + postId);

        database.close();
    }

    /**
     * Packs a Post object into a ContentValues map for use with SQL inserts.
     */
    private static ContentValues postToContentValues(Person person) {
        ContentValues values = new ContentValues();
        values.put(PostORM.COLUMN_ID, person.getId());
        values.put(PostORM.COLUMN_NAME, person.getName());
        values.put(PostORM.COLUMN_STATUS, person.getStatus().getTitle());
        values.put(PostORM.COLUMN_DATE, _dateFormat.format(person.getStatus().getDate()));

        return values;
    }
    
    public static List<Person> getPosts(Context context) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + PostORM.TABLE_NAME, null);

        Log.i(TAG, "Loaded " + cursor.getCount() + " Posts...");
        List<Person> personList = new ArrayList<Person>();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
            	Person person = cursorToPerson(cursor);
                personList.add(person);
                cursor.moveToNext();
            }
            Log.i(TAG, "Posts loaded successfully.");
        }

        database.close();

        return personList;
    }

    /**
     * Populates a Post object with data from a Cursor
     * @param cursor
     * @return
     */
    private static Person cursorToPerson(Cursor cursor) {
    	Person person = new Person();
    	Status status = new Status();
    	person.setStatus(status);
    	
        person.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
        person.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        
        status.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));

        String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
        try {
            status.setDate(_dateFormat.parse(date));
        } catch (ParseException ex) {
            Log.e(TAG, "Failed to parse date " + date + " for Post " + person.getId());
            status.setDate(null);
        } 

        return person;
    }
}