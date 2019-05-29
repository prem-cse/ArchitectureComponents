package com.example.architecturecomponents.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.Random;

public class DataGenerator extends ViewModel {

    private static final String TAG = "DataGenerator";
  /*
       String num;
       change to mutable live data which provide get and set value function
    */
    private MutableLiveData<String> num = new MutableLiveData<>();

    public MutableLiveData<String> getNum() {
        if(num == null) setNum();
        return num;
    }

    public void setNum() {
        Random random  = new Random();
        Log.i(TAG," number generated");
        num.setValue(String.valueOf(random.nextInt(10-1)));

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG," View model destroyed");
    }
}
