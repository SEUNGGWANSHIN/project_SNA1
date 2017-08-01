package com.example.ryu.sttsample.NLP;

/**
 * Created by Ryu on 2017-07-29.
 */
import android.os.AsyncTask;

public class OpenNLPAPITask extends AsyncTask<String, Void, String>
{
    @Override
    public String doInBackground(String... params) {
        OpenNLPAPIClient client = new OpenNLPAPIClient();
        String content = params[0];
        return client.getName(content);
    }
}

