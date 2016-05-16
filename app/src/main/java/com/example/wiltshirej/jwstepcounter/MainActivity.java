package com.example.wiltshirej.jwstepcounter;

import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    DatabaseHelper myDB;

    EditText editName, editSurname, editMarks, editId;
    Button btnAddData, btnViewAll, btnViewUpdate, btnDelete, btnDeleteAll, btnUpdateSteps;
    TextView editSteps;

    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;

    boolean activityRunning;
    public boolean active = false;
    private SensorManager sensorManager;
    private Sensor stepDetectorSensor;
    public int stepCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Debug","Starting main onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        //editName = (EditText) findViewById(R.id.editText_name);
        //editSurname = (EditText) findViewById(R.id.editText_surname);
        //editMarks = (EditText) findViewById(R.id.editText_marks);
        //editId = (EditText) findViewById(R.id.editText_id);
        editSteps = (TextView) findViewById(R.id.editText_steps);



        //btnAddData = (Button) findViewById(R.id.button_add);
        //btnViewAll = (Button) findViewById(R.id.button_viewAll);
        //btnViewUpdate = (Button) findViewById(R.id.button_update);
        //btnDelete = (Button) findViewById(R.id.button_delete);
        //btnDeleteAll = (Button) findViewById(R.id.button_deleteAll);
        btnUpdateSteps = (Button) findViewById(R.id.button_updateSteps);

        updateSteps();

    }



    public void updateSteps(){
        btnUpdateSteps.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //editSteps.setText("99");
                        if(!active){
                            sensorManager.registerListener(MainActivity.this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
                            btnUpdateSteps.setText("Stop");
                            active=true;
                        }
                        else{
                            sensorManager.unregisterListener(MainActivity.this, stepDetectorSensor);
                            btnUpdateSteps.setText("Start");
                            active=false;

                        }



                    }
                }
        );
    }



    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


    @Override
    protected void onResume() {
        super.onResume();
        //Count.open();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

    }

    //   private String getDateTime() {
    //     SimpleDateFormat dateFormat = new SimpleDateFormat(
    //            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    //    Date date = new Date();
    //    return dateFormat.format(date);
    //  }


    @Override
    protected void onPause() {
        super.onPause();
        //Count.close();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning & (active == true)) {
            stepCount++;
            //Countint = stepCount ;
            editSteps.setText(Integer.toString(stepCount));
            // Total_Count.setText(String.valueOf(event.values[0]));
        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
