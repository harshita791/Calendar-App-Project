package com.example.mycalender;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.widget.Toast.makeText;

public class Second extends AppCompatActivity {


    DatePicker pickerDate;
    TimePicker pickerTime;
    Button buttonSetAlarm;
    TextView info;
    String selected;
    Calendar calendar = Calendar.getInstance();
    final static int RQS_1 = 1;
    EditText name;
    DBHelper mydb;
    TextView type;
    ImageView event,birth,anni;
    ImageView yes,no;
    String date,eventname,eventtype;
    String da,eve,typ;

    TextView textView;
    private int id_To_Update=0;
    String selectedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        name=findViewById(R.id.name);
        type=findViewById(R.id.type);
        event=findViewById(R.id.event);
        birth=findViewById(R.id.birth);
        anni=findViewById(R.id.anni);
        yes=findViewById(R.id.check);
        no=findViewById(R.id.close);
        Intent i=getIntent();
        date = i.getStringExtra("d");
        mydb=new DBHelper(this);



        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type.setText("Event");
            }
        });
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type.setText("Birthday");
            }
        });
        anni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type.setText("Anniversary");
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Second.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventname=name.getText().toString();
                eventtype=type.getText().toString();
                boolean insert=mydb.insertdata(date,eventname,eventtype);
                if(insert)
                    Toast.makeText(Second.this, "event added successfully", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Second.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });



        info = (TextView)findViewById(R.id.info);
        pickerDate = (DatePicker)findViewById(R.id.pickerdate);
        pickerTime = (TimePicker)findViewById(R.id.pickertime);

        //  Calendar calendar = Calendar.getInstance();

        pickerDate.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
        pickerTime.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        pickerTime.setCurrentMinute(calendar.get(Calendar.MINUTE));

        buttonSetAlarm = (Button)findViewById(R.id.setalarm);
        buttonSetAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Calendar current = Calendar.getInstance();

                Calendar cal = Calendar.getInstance();
                cal.set(pickerDate.getYear(),
                        pickerDate.getMonth(),
                        pickerDate.getDayOfMonth(),
                        pickerTime.getCurrentHour(),
                        pickerTime.getCurrentMinute(),
                        00);

                if(cal.compareTo(current) <= 0){
                    //The set Date/Time already passed
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();
                }else{
                    setAlarm(cal);

                    selectedTime=String.valueOf(cal.getTime());
                }

            }});

    }

    private void setAlarm(Calendar targetCal){

        info.setText("\n\n***\n"
                + "Alarm is set@ " + targetCal.getTime() + "\n"
                + "***\n");

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        //Intent intent=new Intent(Second.this,AlarmReceiver.class);
       // intent.putExtra("a",eventname);
       PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
       AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
       alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Second.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
