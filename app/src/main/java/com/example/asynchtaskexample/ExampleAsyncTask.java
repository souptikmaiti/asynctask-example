package com.example.asynchtaskexample;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class ExampleAsyncTask extends AsyncTask<Integer,Integer,String> {  //1st arg used in doInBackground() for calculation..here used for sleep timer.
    //2nd arg used in onProgressUpdate().. here to calculate % of progress
    //3rd arg used as return variable of doInBackground().. which is also used in onPostExecute() to be used in ui

    //strong reference or normal reference eg. MainActivity activity = new MainActivity(); prevents activity from garbage collected
    //even if MainActivity is destroyed as AsyncTask is still running. This cause memory leak.
    WeakReference<MainActivity> activityWeakReference;  //weak reference will be garbage collected if MainActivity is destroyed even if AsyncTask is running
    public ExampleAsyncTask(MainActivity mainActivity ){
        this.activityWeakReference = new WeakReference<MainActivity>(mainActivity);
    }
    @Override
    protected void onPreExecute() {            // this method runs under ui thread so we can access ui object here
        super.onPreExecute();
        MainActivity activity = activityWeakReference.get(); //we get strong reference of MainActivity here.
        //But this strong reference is only in the scope of this method. So it will be ready to garbage collected outside this method's scope
        activity.progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    protected String doInBackground(Integer...integers) {    //this method should run under different background thread. so we can't access ui objects here
        for(int i =0; i<integers[0];i++){
            publishProgress((i*100)/integers[0]); //call on onProgressUpdate() with integer value of % progress
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Async Task Finished";
    }
    @Override
    protected void onProgressUpdate(Integer...values) {     // this method runs under ui thread so we can access ui object here
        super.onProgressUpdate(values);
        MainActivity activity = activityWeakReference.get(); //we get strong reference of MainActivity here.
        //But this strong reference is only in the scope of this method. So it will be ready to garbage collected outside this method's scope
        activity.progressBar.setProgress(values[0]);

    }

    @Override
    protected void onPostExecute(String str) {       // this method runs under ui thread so we can access ui object here
        super.onPostExecute(str);
        MainActivity activity = activityWeakReference.get(); //we get strong reference of MainActivity here.
        //But this strong reference is only in the scope of this method. So it will be ready to garbage collected outside this method's scope
        Toast.makeText(activity,str,Toast.LENGTH_LONG).show();
        activity.progressBar.setProgress(0);
        activity.progressBar.setVisibility(View.INVISIBLE);
    }
}
