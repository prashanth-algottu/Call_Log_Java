package com.example.testingforefround;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;

public class Sound extends AsyncTask<String, Void, String> {
    private Context mContext;
    public Sound (Context context){
        mContext = context;
    }
    MediaPlayer mediaPlayer;

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 60000);
                mediaPlayer = MediaPlayer.create(mContext,R.raw.pikachu);
                mediaPlayer.start();

            }
        },3000);

    }
}
