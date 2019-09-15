package com.example.mycalender;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Calendar calendar =Calendar.getInstance();
    TextView show;
    DatePicker datePicker;
    ImageView im;

    DBHelper Mydb;
    String selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        show=findViewById(R.id.show);
        Mydb=new DBHelper(this);

        datePicker=(DatePicker)findViewById(R.id.datepicker);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate= String.valueOf(dayOfMonth)+String.valueOf(monthOfYear+1)+String.valueOf(year);

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = Mydb.getAlldata(selectedDate);
                Toast.makeText(MainActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","No Event found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Date :"+ res.getString(1)+"\n");
                    buffer.append("Event :"+ res.getString(2)+"\n");
                    buffer.append("EventType :"+ res.getString(3)+"\n\n");
                }

                // Show all data
                showMessage("Event",buffer.toString());
            }
        });


        im=findViewById(R.id.add);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Second.class);
                i.putExtra("d",selectedDate);

                startActivity(i);
                finish();
            }
        });
      /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int id_To_Search = position+1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("Id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),Second.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });*/

    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
