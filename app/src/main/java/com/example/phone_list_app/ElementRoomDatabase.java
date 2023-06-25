package com.example.phone_list_app;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//wykład a 151
//pdf s42

@Database(entities = {Element.class}, version = 1, exportSchema = false)
public abstract class ElementRoomDatabase extends RoomDatabase {

    public abstract ElementDao elementDao();

    private static volatile ElementRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ElementRoomDatabase getDatabase(final Context context){
        if(INSTANCE ==null){
            synchronized (ElementRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                                    ElementRoomDatabase.class,"phone_database")//PhoneRoomDatabase
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(()->{
                ElementDao dao = INSTANCE.elementDao();
                //tworzenie elementów (obiektów klasy Element) i dodawanie ich do bazy
                //za pomocą dao.insert() wprowadza się dane w czasie "onCreate"

                Element[] elements = {
                        new Element("Samsung","A53",13,"https://www.samsung.com/pl/smartphones/galaxy-a/galaxy-a53-5g-awesome-black-128gb-sm-a536bzkneue/buy/"),
                        new Element("Google","Google Pixel 7 Pro",13,"https://store.google.com/us/product/pixel_7_pro?hl=en-US"),
                        new Element("OnePlus","OnePlus 9 Pro",13,"https://www.oneplus.com/pl/9-pro"),
                        new Element("Xiaomi","Xiaomi Mi 11",11,"https://mi-store.pl/product-pol-1302-Smartfon-Xiaomi-Mi-11-8-128GB-Cloud-White-1-rok-gwarancji-na-ekran.html")
                };

                for(Element e:elements)
                    dao.insert(e);
            });
        }
    };

}
