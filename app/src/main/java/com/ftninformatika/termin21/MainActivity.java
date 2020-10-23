package com.ftninformatika.termin21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvText;
    private Switch swSmer;
    private Button bStart;

    private boolean smer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = findViewById(R.id.tvText);
        swSmer = findViewById(R.id.swSmer);
        bStart = findViewById(R.id.bStart);
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    startThread(); ili to ili asyncTask
                startAsyncTask();
            }
        });
        swSmer.setText(smer ? "Pozitivan" : "Negativan");
        swSmer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                smer =! smer;
                swSmer.setText(smer ? "Pozitivan" : "Negtivan");
            }
        });
    }
    private void startAsyncTask(){
        MojAsyncTask mojAsyncTask = new MojAsyncTask();
        mojAsyncTask.execute(10);
    }
//    ili tred ili moj asink task :)
//    private void startThread() {
//        bStart.setEnabled(false);
//
//        Thread mojThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int sek = 10;
//                 while (sek > 1) {
//                     sek = smer ? sek + 1 : sek - 1;
//                     updateTextViewAsync(sek + "");
//                     try {
//                         Thread.sleep(1000);
//                     } catch (InterruptedException e) {
//                         e.printStackTrace();
//                     }
//
//                 }
//                 updateTextViewAsync("BOOM!!!");
//                 bStart.post(new Runnable() {
//                     @Override
//                     public void run() {
//                         bStart.setEnabled(true);
//                     }
//                 });
//            }
//        });
//        mojThread.start();
//    }
    private  void updateTextViewAsync(final String text){
        tvText.post(new Runnable() {
            @Override
            public void run() {
                tvText.setText(text);
            }
        });

    }
    private class MojAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onPreExecute() {
           bStart.setEnabled(false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           bStart.setEnabled(true);
           updateTextViewAsync("BOOM!!!");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           tvText.setText(values [0] + "");
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int sek = integers[0];
            do{
                sek = smer ? sek + 1 : sek - 1;
                publishProgress(sek);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (sek > 0);
            return null;
        }
    }

}