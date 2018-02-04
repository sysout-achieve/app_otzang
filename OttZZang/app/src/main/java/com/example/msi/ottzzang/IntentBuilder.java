package com.example.msi.ottzzang;

import android.content.Context;
import android.content.Intent;
import android.media.Image;

import java.util.Map;

/**
 * Created by MSI on 2018-01-27.
         */

public class IntentBuilder {

    public static Intent build(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        return intent;
    }
}
