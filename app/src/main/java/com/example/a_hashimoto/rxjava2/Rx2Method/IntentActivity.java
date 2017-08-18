package com.example.a_hashimoto.rxjava2.Rx2Method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by a-hashimoto on 2017/08/04.
 */

public class IntentActivity extends AppCompatActivity {


    public static final String COCOA = "cocoa";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("intentTest", "" + getIntent().hasExtra(COCOA));
        Log.d("intentTestGet", "" + getIntent().getBooleanExtra(COCOA, false));
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, IntentActivity.class);
//        intent.putExtra(COCOA, true);
        return intent;
    }
}
