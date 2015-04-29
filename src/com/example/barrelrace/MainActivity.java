package com.example.barrelrace;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barrelrace.racecourse;


/*
 * Author:Saransh
 * The main activity where game starts
 * 
 */




public class MainActivity extends Activity implements SensorEventListener {

	Button clearbutton,startbutton,highscore;
	Sensor accelarometor;
	private Handler customHandler = new Handler();
	private Handler mHandler = new Handler();
	SensorManager sm;
	TextView nametext;
	racecourse rc;
	Runnable updateTimerThread;
	TextView tv2;
	Thread th;
	String name;
	int here =1;
	int secs,mins,milliseconds;
	long timeInMilliseconds = 0L;
	private long startTime = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	int pausecheck=0;
	long millis = 0L;
	float x=0,y=0,z=0;
	long delay;
	public static Handler UIHandler;

	static 
	{
	    UIHandler = new Handler(Looper.getMainLooper());
	}
	public static void runOnUI(Runnable runnable) {
	    UIHandler.post(runnable);
	}
	
	/*
	 * Author:saransh
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * first function which will run
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		
		/*
		 * Author : Saransh 
		 * To run a thread for time
		 */
		final Runnable mUpdateTimeTask = new Runnable() {
			   public void run() {
				   if(rc.isgameover()==0&&rc.isfinish()==0)
				   {
				       final long start = startTime;
				       millis = SystemClock.uptimeMillis() - start;
				       int milis = (int)millis%1000;
				       int seconds = (int) (millis / 1000);
				       int minutes = seconds / 60;
				       seconds     = seconds % 60;
				       
				       
				        
				       tv2 = (TextView) findViewById (R.id.textView2);
				       if (seconds < 10) {
				           tv2.setText("" + minutes + ":0" + seconds+":0"+milis);
				       } else {
				           tv2.setText("" + minutes + ":" + seconds +":0"+ milis);            
				       }
				     
				       mHandler.postAtTime(this,
				               start + (((minutes * 60) + seconds + 1) * 1000));
				   }
				   else if(rc.isfinish()==1&&here==1)
				   {
					    here=0;
					    nametext = (TextView) findViewById(R.id.editText1);
						name = nametext.getText().toString();
						float x = (float)millis;
						Log.i("sds", String.valueOf(x));
						Intent i = new Intent(MainActivity.this,highscore.class);
						i.putExtra("mytitle",name);
						i.putExtra("mybody",  String.valueOf(x));
						startActivity(i);
				   }
				}
			};
		
		
		nametext = (TextView) findViewById(R.id.editText1);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelarometor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		startbutton = (Button) findViewById(R.id.button3);
		rc = (racecourse)this.findViewById(R.id.gview);
		startbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rc.setgameover();
				startTime = SystemClock.uptimeMillis();
				rc.setFinishGame();
				here=1;
				nametext = (TextView) findViewById(R.id.editText1);
				nametext.setFocusable(false);
				mHandler.post(mUpdateTimeTask);
				startsensor();
				startbutton.setClickable(false);
				
				//customHandler.postDelayed(updateTimerThread, 0);
			}
			
		});
		
		
		highscore = (Button)findViewById(R.id.button2);
		highscore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nametext = (TextView) findViewById(R.id.editText1);
				name = nametext.getText().toString();
				Intent i = new Intent(MainActivity.this,highscore.class);
				i.putExtra("mytitle","Player");
				i.putExtra("mybody", "10000000");
				startActivity(i);
				onCreate(savedInstanceState);
			}
		});
		
		
		clearbutton = (Button)findViewById(R.id.button1);
		clearbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nametext.setText("");	
			}
		});
		
		

			
	}
	
	
	
	

	/* 
	 * Author:Saransh
	 * To refresh activity on result of another activity
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if(resultCode==RESULT_OK){
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        this.finish();
     }
    }
	
	/*
	 * Author:Saransh
	 * To create menu
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	/*
	 * (non-Javadoc)
	 * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Author:Saransh
	 * To start sensor
	 */
	public void startsensor()
	{
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelarometor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, accelarometor, SensorManager.SENSOR_DELAY_UI);
	}

	
	/*
	 * Author:Saransh
	 * To pause sensor
	 */
	public void pausesensor()
	{
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelarometor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.unregisterListener(this, accelarometor);
		
	}
	
	/*
	 * Author:Saransh
	 * To see the changed value from the sensor and update the view
	 * (non-Javadoc)
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
		{
			x = (event.values[0]);
			y = (event.values[1]);
			rc = (racecourse)this.findViewById(R.id.gview);
			x=x*-1;
			if(rc.isgameover()==1)
			{	
				pausesensor();
				startbutton = (Button) findViewById(R.id.button3);
				startbutton.setText("Restart");
				startbutton.setClickable(true);
				mHandler.removeCallbacks(updateTimerThread);
			}
			if(rc.istouchwall()==1)
			{
				startTime = startTime - 5000;
				
			}
			rc.updateposition(x+7,y,1);
		}
		
	}

	

	
}
