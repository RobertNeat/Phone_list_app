package com.example.phone_list_app;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ElementViewModel extends AndroidViewModel {
    private final ElementRepository mRepository;
    private final LiveData<List<Element>> mAllElements;

    public ElementViewModel(@NonNull Application application){
        super(application);
        mRepository = new ElementRepository(application);
        mAllElements = mRepository.getmAllElements();
    }

    LiveData<List<Element>> getmAllElements(){
        return mAllElements;
    }

    public void insert(Element element){
        mRepository.insert(element);
    }

    public void update(Element element){
        mRepository.update(element);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void deleteElement(Element element){
        mRepository.deleteElement(element);
    }

}
