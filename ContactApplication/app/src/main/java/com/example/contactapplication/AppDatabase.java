package com.example.contactapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .addMigrations(MIGRATION_1_2) // Add migration path from version 1 to 2
                    .build();
        }
        return instance;
    }

    // Migration from version 2 to 3
    private static final Migration MIGRATION_1_2 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Execute SQL commands to update the database schema
            database.execSQL("ALTER TABLE Contact ADD COLUMN avatar TEXT");
        }
    };
}
