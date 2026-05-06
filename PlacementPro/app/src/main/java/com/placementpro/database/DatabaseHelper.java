package com.placementpro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.placementpro.models.Task;
import com.placementpro.models.Goal;
import com.placementpro.models.ScheduleItem;
import com.placementpro.models.TopicProgress;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "placementpro.db";
    private static final int DB_VERSION = 1;

    // Tables
    public static final String TABLE_TASKS = "tasks";
    public static final String TABLE_GOALS = "goals";
    public static final String TABLE_SCHEDULE = "schedule";
    public static final String TABLE_TOPIC_PROGRESS = "topic_progress";
    public static final String TABLE_QUIZ_SCORES = "quiz_scores";

    // Task columns
    public static final String COL_ID = "id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_TITLE = "title";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_PRIORITY = "priority";
    public static final String COL_IS_DONE = "is_done";
    public static final String COL_DUE_DATE = "due_date";
    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_CATEGORY = "category";

    // Goal columns
    public static final String COL_TARGET_DATE = "target_date";
    public static final String COL_PROGRESS = "progress";
    public static final String COL_TARGET = "target";
    public static final String COL_UNIT = "unit";

    // Schedule columns
    public static final String COL_DAY = "day";
    public static final String COL_TIME_SLOT = "time_slot";
    public static final String COL_SUBJECT = "subject";
    public static final String COL_DURATION = "duration";
    public static final String COL_COLOR = "color";

    // Topic progress columns
    public static final String COL_SUBJECT_ID = "subject_id";
    public static final String COL_TOPIC_NAME = "topic_name";
    public static final String COL_IS_COMPLETED = "is_completed";
    public static final String COL_NOTES = "notes";

    // Quiz score columns
    public static final String COL_SUBJECT_NAME = "subject_name";
    public static final String COL_SCORE = "score";
    public static final String COL_TOTAL = "total";
    public static final String COL_DATE = "date";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tasks table
        db.execSQL("CREATE TABLE " + TABLE_TASKS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USER_ID + " TEXT NOT NULL," +
                COL_TITLE + " TEXT NOT NULL," +
                COL_DESCRIPTION + " TEXT," +
                COL_PRIORITY + " TEXT DEFAULT 'MEDIUM'," +
                COL_IS_DONE + " INTEGER DEFAULT 0," +
                COL_DUE_DATE + " TEXT," +
                COL_CATEGORY + " TEXT," +
                COL_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP)");

        // Goals table
        db.execSQL("CREATE TABLE " + TABLE_GOALS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USER_ID + " TEXT NOT NULL," +
                COL_TITLE + " TEXT NOT NULL," +
                COL_DESCRIPTION + " TEXT," +
                COL_PROGRESS + " INTEGER DEFAULT 0," +
                COL_TARGET + " INTEGER DEFAULT 100," +
                COL_UNIT + " TEXT DEFAULT '%'," +
                COL_TARGET_DATE + " TEXT," +
                COL_IS_DONE + " INTEGER DEFAULT 0," +
                COL_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP)");

        // Schedule table
        db.execSQL("CREATE TABLE " + TABLE_SCHEDULE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USER_ID + " TEXT NOT NULL," +
                COL_DAY + " TEXT NOT NULL," +
                COL_TIME_SLOT + " TEXT NOT NULL," +
                COL_SUBJECT + " TEXT NOT NULL," +
                COL_DURATION + " INTEGER DEFAULT 60," +
                COL_COLOR + " TEXT DEFAULT '#2979FF'," +
                COL_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP)");

        // Topic progress table
        db.execSQL("CREATE TABLE " + TABLE_TOPIC_PROGRESS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USER_ID + " TEXT NOT NULL," +
                COL_SUBJECT_ID + " TEXT NOT NULL," +
                COL_TOPIC_NAME + " TEXT NOT NULL," +
                COL_IS_COMPLETED + " INTEGER DEFAULT 0," +
                COL_NOTES + " TEXT," +
                COL_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP)");

        // Quiz scores table
        db.execSQL("CREATE TABLE " + TABLE_QUIZ_SCORES + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USER_ID + " TEXT NOT NULL," +
                COL_SUBJECT_NAME + " TEXT NOT NULL," +
                COL_SCORE + " INTEGER NOT NULL," +
                COL_TOTAL + " INTEGER NOT NULL," +
                COL_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_SCORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPIC_PROGRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // ==================== TASK CRUD ====================

    public long addTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, task.getUserId());
        cv.put(COL_TITLE, task.getTitle());
        cv.put(COL_DESCRIPTION, task.getDescription());
        cv.put(COL_PRIORITY, task.getPriority());
        cv.put(COL_DUE_DATE, task.getDueDate());
        cv.put(COL_CATEGORY, task.getCategory());
        long id = db.insert(TABLE_TASKS, null, cv);
        db.close();
        return id;
    }

    public List<Task> getTasksByUser(String userId) {
        List<Task> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TASKS +
                " WHERE " + COL_USER_ID + "=? ORDER BY " + COL_IS_DONE + " ASC, " + COL_CREATED_AT + " DESC",
                new String[]{userId});
        if (c.moveToFirst()) {
            do {
                Task t = new Task();
                t.setId(c.getInt(c.getColumnIndexOrThrow(COL_ID)));
                t.setUserId(c.getString(c.getColumnIndexOrThrow(COL_USER_ID)));
                t.setTitle(c.getString(c.getColumnIndexOrThrow(COL_TITLE)));
                t.setDescription(c.getString(c.getColumnIndexOrThrow(COL_DESCRIPTION)));
                t.setPriority(c.getString(c.getColumnIndexOrThrow(COL_PRIORITY)));
                t.setDone(c.getInt(c.getColumnIndexOrThrow(COL_IS_DONE)) == 1);
                t.setDueDate(c.getString(c.getColumnIndexOrThrow(COL_DUE_DATE)));
                t.setCategory(c.getString(c.getColumnIndexOrThrow(COL_CATEGORY)));
                list.add(t);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public void toggleTaskDone(int id, boolean done) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_IS_DONE, done ? 1 : 0);
        db.update(TABLE_TASKS, cv, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public boolean deleteTask(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_TASKS, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    public int getPendingTaskCount(String userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_TASKS +
                " WHERE " + COL_USER_ID + "=? AND " + COL_IS_DONE + "=0", new String[]{userId});
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        db.close();
        return count;
    }

    public int getCompletedTaskCount(String userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_TASKS +
                " WHERE " + COL_USER_ID + "=? AND " + COL_IS_DONE + "=1", new String[]{userId});
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        db.close();
        return count;
    }

    // ==================== GOAL CRUD ====================

    public long addGoal(Goal goal) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, goal.getUserId());
        cv.put(COL_TITLE, goal.getTitle());
        cv.put(COL_DESCRIPTION, goal.getDescription());
        cv.put(COL_TARGET, goal.getTarget());
        cv.put(COL_PROGRESS, goal.getProgress());
        cv.put(COL_UNIT, goal.getUnit());
        cv.put(COL_TARGET_DATE, goal.getTargetDate());
        long id = db.insert(TABLE_GOALS, null, cv);
        db.close();
        return id;
    }

    public List<Goal> getGoals(String userId) {
        List<Goal> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_GOALS +
                " WHERE " + COL_USER_ID + "=? ORDER BY " + COL_CREATED_AT + " DESC",
                new String[]{userId});
        if (c.moveToFirst()) {
            do {
                Goal g = new Goal();
                g.setId(c.getInt(c.getColumnIndexOrThrow(COL_ID)));
                g.setUserId(c.getString(c.getColumnIndexOrThrow(COL_USER_ID)));
                g.setTitle(c.getString(c.getColumnIndexOrThrow(COL_TITLE)));
                g.setDescription(c.getString(c.getColumnIndexOrThrow(COL_DESCRIPTION)));
                g.setProgress(c.getInt(c.getColumnIndexOrThrow(COL_PROGRESS)));
                g.setTarget(c.getInt(c.getColumnIndexOrThrow(COL_TARGET)));
                g.setUnit(c.getString(c.getColumnIndexOrThrow(COL_UNIT)));
                g.setTargetDate(c.getString(c.getColumnIndexOrThrow(COL_TARGET_DATE)));
                g.setDone(c.getInt(c.getColumnIndexOrThrow(COL_IS_DONE)) == 1);
                list.add(g);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public void updateGoalProgress(int id, int progress) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PROGRESS, progress);
        if (progress >= 100) cv.put(COL_IS_DONE, 1);
        db.update(TABLE_GOALS, cv, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public boolean deleteGoal(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_GOALS, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    // ==================== SCHEDULE CRUD ====================

    public long addScheduleItem(ScheduleItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, item.getUserId());
        cv.put(COL_DAY, item.getDay());
        cv.put(COL_TIME_SLOT, item.getTimeSlot());
        cv.put(COL_SUBJECT, item.getSubject());
        cv.put(COL_DURATION, item.getDuration());
        cv.put(COL_COLOR, item.getColor());
        long id = db.insert(TABLE_SCHEDULE, null, cv);
        db.close();
        return id;
    }

    public List<ScheduleItem> getSchedule(String userId) {
        List<ScheduleItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_SCHEDULE +
                " WHERE " + COL_USER_ID + "=? ORDER BY " + COL_DAY + ", " + COL_TIME_SLOT,
                new String[]{userId});
        if (c.moveToFirst()) {
            do {
                ScheduleItem s = new ScheduleItem();
                s.setId(c.getInt(c.getColumnIndexOrThrow(COL_ID)));
                s.setUserId(c.getString(c.getColumnIndexOrThrow(COL_USER_ID)));
                s.setDay(c.getString(c.getColumnIndexOrThrow(COL_DAY)));
                s.setTimeSlot(c.getString(c.getColumnIndexOrThrow(COL_TIME_SLOT)));
                s.setSubject(c.getString(c.getColumnIndexOrThrow(COL_SUBJECT)));
                s.setDuration(c.getInt(c.getColumnIndexOrThrow(COL_DURATION)));
                s.setColor(c.getString(c.getColumnIndexOrThrow(COL_COLOR)));
                list.add(s);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public boolean deleteScheduleItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_SCHEDULE, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    // ==================== TOPIC PROGRESS ====================

    public void saveTopicProgress(String userId, String subjectId, String topicName, boolean completed) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_ID + " FROM " + TABLE_TOPIC_PROGRESS +
                " WHERE " + COL_USER_ID + "=? AND " + COL_SUBJECT_ID + "=? AND " + COL_TOPIC_NAME + "=?",
                new String[]{userId, subjectId, topicName});
        if (c.moveToFirst()) {
            int id = c.getInt(0);
            ContentValues cv = new ContentValues();
            cv.put(COL_IS_COMPLETED, completed ? 1 : 0);
            db.update(TABLE_TOPIC_PROGRESS, cv, COL_ID + "=?", new String[]{String.valueOf(id)});
        } else {
            ContentValues cv = new ContentValues();
            cv.put(COL_USER_ID, userId);
            cv.put(COL_SUBJECT_ID, subjectId);
            cv.put(COL_TOPIC_NAME, topicName);
            cv.put(COL_IS_COMPLETED, completed ? 1 : 0);
            db.insert(TABLE_TOPIC_PROGRESS, null, cv);
        }
        c.close();
        db.close();
    }

    public boolean isTopicCompleted(String userId, String subjectId, String topicName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_IS_COMPLETED + " FROM " + TABLE_TOPIC_PROGRESS +
                " WHERE " + COL_USER_ID + "=? AND " + COL_SUBJECT_ID + "=? AND " + COL_TOPIC_NAME + "=?",
                new String[]{userId, subjectId, topicName});
        boolean done = false;
        if (c.moveToFirst()) done = c.getInt(0) == 1;
        c.close();
        db.close();
        return done;
    }

    public int getCompletedTopicsCount(String userId, String subjectId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_TOPIC_PROGRESS +
                " WHERE " + COL_USER_ID + "=? AND " + COL_SUBJECT_ID + "=? AND " + COL_IS_COMPLETED + "=1",
                new String[]{userId, subjectId});
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        db.close();
        return count;
    }

    public int getTotalCompletedTopics(String userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_TOPIC_PROGRESS +
                " WHERE " + COL_USER_ID + "=? AND " + COL_IS_COMPLETED + "=1", new String[]{userId});
        int count = 0;
        if (c.moveToFirst()) count = c.getInt(0);
        c.close();
        db.close();
        return count;
    }

    // ==================== QUIZ SCORES ====================

    public void saveQuizScore(String userId, String subject, int score, int total) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, userId);
        cv.put(COL_SUBJECT_NAME, subject);
        cv.put(COL_SCORE, score);
        cv.put(COL_TOTAL, total);
        db.insert(TABLE_QUIZ_SCORES, null, cv);
        db.close();
    }

    public List<int[]> getQuizScores(String userId) {
        List<int[]> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_SCORE + ", " + COL_TOTAL +
                " FROM " + TABLE_QUIZ_SCORES + " WHERE " + COL_USER_ID + "=? ORDER BY " + COL_DATE + " DESC LIMIT 10",
                new String[]{userId});
        if (c.moveToFirst()) {
            do {
                list.add(new int[]{c.getInt(0), c.getInt(1)});
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }
}
