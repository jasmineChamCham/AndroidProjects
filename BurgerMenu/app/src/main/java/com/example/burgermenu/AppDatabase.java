package com.example.burgermenu;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Order.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OrderDao orderDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .build();
        }
        return instance;
    }

    // Migration from version 2 to 3
//    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Execute SQL commands to update the database schema
//            database.execSQL("ALTER TABLE Contact ADD COLUMN avatar TEXT");
//        }
//    };
}
