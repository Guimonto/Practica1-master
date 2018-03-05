package sdm.pract1.whowantstobeamillionaire.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sdm.pract1.whowantstobeamillionaire.HighScore;
import sdm.pract1.whowantstobeamillionaire.adapter.ScoreAdapter;

/**
 * Created by Guill on 03/03/2018.
 */

public class GameSqlHelper extends SQLiteOpenHelper {

    ScoreAdapter adapter;

    private static GameSqlHelper instance;

    public synchronized static GameSqlHelper getInstance(Context context){
        if (instance == null) {
            instance = new GameSqlHelper(context.getApplicationContext());
        }
        return  instance;
    }

    private GameSqlHelper(Context context){
        super(context, "game_database", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ScoreTable " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT NOT NULL, puntuation TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public List<HighScore> getScores(){
        List<HighScore> result = new ArrayList<>();
        HighScore item;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                "ScoreTable", new String[]{"user", "puntuation"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            item = new HighScore();
            item.setName(cursor.getString(0));
            item.setScoring(cursor.getString(1));

            result.add(item);
        }
        cursor.close();
        db.close();

        return result;
    }

    public boolean isInScore(String text){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("ScoreTable", null, "score=?", new String[]{text}, null, null, null, null);

        final boolean result = (cursor.getCount() > 0);
        cursor.close();
        db.close();

        return result;
    }

    public void addScore(String user, String punctuation){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user", user);
        values.put("puntuation", punctuation);
        db.insert("ScoreTable", null, values);
        db.close();
    }

    public void clearAllUserScores(){
        SQLiteDatabase db = getWritableDatabase();

        db.delete("ScoreTable", null,null );
        db.close();
    }
}

