package com.meisterdevs.ftc;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by burgera on 1/1/18.
 * from https://android.jlelse.eu/how-to-make-the-perfect-singleton-de6b951dfdb0
 */

public class ContextHolder implements Serializable {

    private static volatile ContextHolder sSoleInstance;
    private Context context;

    //private constructor.
    private ContextHolder() {

        //Prevent form the reflection api.
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static ContextHolder getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (ContextHolder.class) {
                if (sSoleInstance == null) sSoleInstance = new ContextHolder();
            }
        }
        return sSoleInstance;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //Make singleton from serialize and deserialize operation.
    protected ContextHolder readResolve() {
        return getInstance();
    }

}
