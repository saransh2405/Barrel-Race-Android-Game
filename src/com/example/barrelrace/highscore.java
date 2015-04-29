/**
 * 
 */
package com.example.barrelrace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Pooja Jain
 *
 */
public class highscore extends Activity {

	Button back;
	File Dir,settingfile,file;
	String[][] scores;
	TextView r1;
	TextView n1,n2,n3,n4,n5;
	TextView s1,s2,s3,s4,s5;
	String name;
	float time;
	
	/**
	 * @author Pooja Jain 
	 * Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.high_score);
	    name = getIntent().getStringExtra("mytitle");
	    time = Float.parseFloat(getIntent().getStringExtra("mybody"));
	    init();
	    check();
	    
	    
	    r1 = (TextView) findViewById (R.id.textView2);
	    r1.setText(String.valueOf(time));
	    
	    n1 = (TextView) findViewById (R.id.textView4);
	    n2 = (TextView) findViewById (R.id.textView6);
	    n3 = (TextView) findViewById (R.id.textView8);
	    n4 = (TextView) findViewById (R.id.textView10);
	    n5 = (TextView) findViewById (R.id.textView12);
	    
	    s1 = (TextView) findViewById (R.id.textView5);
	    s2 = (TextView) findViewById (R.id.textView7);
	    s3 = (TextView) findViewById (R.id.textView9);
	    s4 = (TextView) findViewById (R.id.textView11);
	    s5 = (TextView) findViewById (R.id.textView13);
	    
	    n1.setText(scores[0][0]);
	    n2.setText(scores[1][0]);
	    n3.setText(scores[2][0]);
	    n4.setText(scores[3][0]);
	    n5.setText(scores[4][0]);
	    
	    s1.setText(setscore(0,1));
	    s2.setText(setscore(1,1));
	    s3.setText(setscore(2,1));
	    s4.setText(setscore(3,1));
	    s5.setText(setscore(4,1));
	    
	    
	    
	    back = (Button) findViewById (R.id.backbutton);
	    back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				savetofile();
				finish();
			}
		});
	}
	
	public String setscore(int i,int j)
	{
		String S="";   
		int milis = (int)Float.parseFloat(scores[i][j])%1000;
	    int seconds = (int) (Float.parseFloat(scores[i][j]) / 1000);
	    int minutes = seconds / 60;
	    seconds     = seconds % 60;
	    
	    if (seconds < 10) {
	           S="" + minutes + ":0" + seconds+":0"+milis;
	       } else {
	           S="" + minutes + ":" + seconds +":0"+ milis;            
	       }
	       
	    return S;
	}
	
	/**
	 * @author Pooja Jain
	 * To initialize the activity with highscores
	 */
	public void init()
	{
		Dir = new File(Environment.getExternalStorageDirectory(),"/barrelrace");
		if (!Dir.isDirectory()) {
			Dir.mkdir();
			settingfile = new File(Dir.getAbsolutePath() + File.separator
					+ "/highscores.txt");
			try {
				if (settingfile.createNewFile()) {
					BufferedWriter bwr = new BufferedWriter(
							new java.io.FileWriter(settingfile.getAbsoluteFile(), true));
					bwr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}}
		
		int i=0;
		File file = new File(Dir.getAbsolutePath() + File.separator	+ "/highscores.txt");
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			while ((br.readLine()) != null) {
				
				i++; 
			}
			br.close();
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		scores = new String[5][2];
		for(int x=0;x<5;x++)
		{
			scores[x][0]="Player";
			scores[x][1]="100000000.0";
		}
		i=0;
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
				scores[i][0]=line.split(":")[0];
				scores[i][1]=line.split(":")[1];
				i++; 
			}
			br.close();
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		sortlist();
	}
	
	
	
	/**
	 * 
	 * @author Pooja Jain
	 * To save the high scores to a file
	 */
	public void savetofile()
	{
		sortlist();
		String body="";
		file = new File(Dir.getAbsolutePath() + File.separator +"/"+ "highscores.txt");
		for(int i=0;i<scores.length;i++)
		{
			body+=scores[i][0]+":"+scores[i][1]+"\n";
		}
		if (!file.exists()) {
			try{
				file.createNewFile();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		try{
			BufferedWriter bwr = new BufferedWriter(new java.io.FileWriter(file.getAbsoluteFile(), false));
			
			bwr.write("");
			bwr.flush();
			bwr.close();
			bwr = new BufferedWriter(new java.io.FileWriter(file.getAbsoluteFile(), true));
			
			bwr.write(body);
			bwr.flush();
			
			bwr.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @author Pooja Jain
	 * To sort the high scores
	 */
	
	
	public void sortlist()
	{
		float min = 10000000;
		String temp1="",temp2="";
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<5;j++)
				if(min>Float.parseFloat(scores[j][1]))
				{
					min=Float.parseFloat(scores[j][1]);
					temp1 = scores[i][0];
					temp2 = scores[i][1];
					scores[i][0] = scores[j][0];
					scores[i][1] = scores[j][1];
					scores[j][0] = temp1;
					scores[j][1] = temp2;
					//Log.i("here", String.valueOf(temp1));
				}
		}
		savetofile();
	}
	
	
	/**
	 * 
	 * @author Pooja Jain
	 * only if the time is lesser than the last entry , it makes it to the high score board hence , sorting is called.
	 */
	public void check()
	{
		if(time<Float.parseFloat(scores[4][1]))
		{
			scores[4][0]=name;
			scores[4][1]=String.valueOf(time);
			sortlist();
		}
	}

}
