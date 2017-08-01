package com.example.ryu.sttsample.TTS;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Ryu on 2017-08-01.
 */

public class SynthesisTask extends AsyncTask<String,Void,Void> {

    @Override
    protected Void doInBackground(String... params) {
        Log.d("check", params[0]);
        NaverSynthesis.main(params[0]);
        return null;
    }
}
