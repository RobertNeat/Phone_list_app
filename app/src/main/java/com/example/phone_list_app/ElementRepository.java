package com.example.phone_list_app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ElementRepository {
    private ElementDao mElementDao;
    private LiveData<List<Element>> mAllElements;

    ElementRepository(Application application){
        ElementRoomDatabase elementRoomDatabase = ElementRoomDatabase.getDatabase(application);

        mElementDao = elementRoomDatabase.elementDao();
        mAllElements = mElementDao.getOrderedPhones();
    }

    LiveData<List<Element>> getmAllElements(){
        return mAllElements;
    }

    void insert(Element element){
        ElementRoomDatabase.databaseWriteExecutor.execute(()->{
            mElementDao.insert(element);
        });
    }

    void update(Element element){
        ElementRoomDatabase.databaseWriteExecutor.execute(()->{
            mElementDao.update(element);
        });
    }

    void deleteAll(){
        ElementRoomDatabase.databaseWriteExecutor.execute(()->{
            mElementDao.deleteAll();
        });
    }

    void deleteElement(Element element){
        ElementRoomDatabase.databaseWriteExecutor.execute(()->{
            mElementDao.delete(element);
        });
    }

    //usunięcie elementu na podstawie klucza id
    void deleteElementById(int id) {
        ElementRoomDatabase.databaseWriteExecutor.execute(() -> {
            mElementDao.deleteById(id);
        });
    }

}
