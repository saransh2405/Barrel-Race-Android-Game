package com.example.barrelrace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;




/*
 * Author:Saransh
 * The view or the drawing part of the app handles all the drawing functions
 */
public class racecourse extends View {
	float x=0;
	float y=0;
	int barrel1=0,barrel2=0,barrel3=0;
	int circlewidth = 20;
	float cir1x=0,cir2x=0,cir3x=0,cir1y=0,cir2y=0,cir3y=0,cir4x=0,cir4y=0,cirtx=0,cirty=0;
	int gameover=0,notstarted = 0,finishgame=0;
	int maxwidth = 1000,maxheight=1000;
	int inwall = 0;
	int[][] roadcovered = new int[1000][1000];
	int finish = 0;
	int cir1[],cir2[],cir3[];
	
	

/*
 * Author:Saransh
 * The default constructor of this class sets min dimensions
 */
	public racecourse(Context cxt, AttributeSet attrs) {
        super(cxt, attrs);
        setMinimumHeight(100);
        setMinimumWidth(100);
    }
	
	/*
	 * 
	 */
	

/*
 * Author:Saransh
 * function to check if a barrel is touched
 */
	public void gameover()
    {
    	if(Math.hypot(cir4y-345,cir4x-328)<=40)
    	{
    		gameover = 1;
    	}
    	if(Math.hypot(cir4y-174, cir4x-484.3)<=40)
    	{
    		gameover = 1;
    	}
    	if(Math.hypot(cir4y-174, cir4x-174)<=40)
    	{
    		gameover = 1;
    	}
    }
	
	/*
	 * Author:Saransh
	 * function to check if a wall is touched
	 */
	public int istouchwall()
	{
		//Log.i("cir4x:", Float.toString(cir4x));
		//Log.i("cir4y:", Float.toString(cir4y));
		if(cir4y-2*circlewidth<maxheight)
			finish=1;
		if(cir4x+circlewidth>maxwidth&&inwall==0)
		{
			cir4x=maxwidth-2;
			inwall=1;
			return 1;
		}
		else if(cir4x-circlewidth<=0&&inwall==0)
		{
			cir4x=21;
			inwall=1;
			return 1;
		}
		else if(cir4y-circlewidth<=0&&inwall==0)
		{
			cir4y=21;
			inwall=1;
			return 1;
		}
		else if(cir4y+circlewidth>=maxheight&&inwall==0)
		{
			if((cir4x>2*maxwidth/5)&&(cir4x<3*maxwidth/5)&&(finish==1)&&barrel1>4&&barrel2>4&&barrel3>4)
			{
				finishgame = 1;
				gameover=1;
				
			}
			else
			{
				cir4y=cir4y-2;
				inwall=1;
				return 1;
			}
			Log.i("finish", String.valueOf(finishgame));
		}
		else{
			inwall=0;
			if(roadcovered[(int) cir4x][(int) cir4y]==1)
			{
				checkbarrel1();
				checkbarrel2();
				checkbarrel3();
				//Log.i("barrel", String.valueOf(barrel1));
			}
			else{
				roadcovered[(int) cir4x][(int) cir4y]=1;
			}
			return 0;
		}
		return 0;
	}
	
	
	/*
	 * Author:Saransh
	 * function to check if a barrel 1 has been covered
	 */
	public void checkbarrel1()
	{
		for(int t=0;t<345;t++)
		{
			if(roadcovered[328][t]==1)
			{
				barrel1+=1;
				break;
			}
		}
		for(int t=0;t<328;t++)
		{
			if(roadcovered[t][345]==1)
			{
				barrel1+=1;
				break;
			}
		}
		for(int t=345;t<999;t++)
		{
			if(roadcovered[328][t]==1)
			{
				barrel1+=1;
				break;
			}
		}
		for(int t=328;t<999;t++)
		{
			if(roadcovered[t][345]==1)
			{
				barrel1+=1;
				//initroad();
				break;
			}
		}
	}
	
	
	/*
	 * Author:Saransh
	 * function to check if a barrel 2 has been covered
	 */
	public void checkbarrel2()
	{
		for(int t=0;t<174;t++)
		{
			if(roadcovered[174][t]==1)
			{
				barrel2+=1;
				break;
			}
		}
		for(int t=0;t<174;t++)
		{
			if(roadcovered[t][174]==1)
			{
				barrel2+=1;
				break;
			}
		}
		for(int t=174;t<999;t++)
		{
			if(roadcovered[174][t]==1)
			{
				barrel2+=1;
				break;
			}
		}
		for(int t=174;t<999;t++)
		{
			if(roadcovered[t][174]==1)
			{
				barrel2+=1;
				//initroad();
				break;
			}
		}
	}
	
	
	/*
	 * Author:Saransh
	 * function to check if a barrel 3 has been covered
	 */
	public void checkbarrel3()
	{
		for(int t=0;t<174;t++)
		{
			if(roadcovered[484][t]==1)
			{
				barrel3+=1;
				break;
			}
		}
		for(int t=0;t<484;t++)
		{
			if(roadcovered[t][174]==1)
			{
				barrel3+=1;
				break;
			}
		}
		for(int t=174;t<999;t++)
		{
			if(roadcovered[484][t]==1)
			{
				barrel3+=1;
				break;
			}
		}
		for(int t=484;t<999;t++)
		{
			if(roadcovered[t][174]==1)
			{
				barrel3+=1;
				//initroad();
				break;
			}
		}
	}
	
	
	/*
	 * Author:Saransh
	 * function to initialize path matrix
	 */
	public void initroad()
	{
		for(int t=0;t<1000;t++)
        {
        	for(int l=0;l<1000;l++)
        	{
        		roadcovered[t][l] = 0;
        	}
        }
	}
	
	/*
	 * Author:Saransh
	 * function to check if game has been successfully completed
	 */
	public int isfinish()
	{
		if(finishgame==1)
			return 1;
		else
			return 0;
	}
	
	/*
	 * Author:Saransh
	 * function to set finishgame as 0
	 */
	public void setFinishGame()
	{
		finishgame=0;
	}
	
	
	/*
	 * Author:Saransh
	 * function to initialize canvas and all the values
	 */
	protected void init(Canvas cv) {
        cv.drawColor(Color.GREEN);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(5);
        Paint r = new Paint();
        r.setColor(Color.parseColor("#FF0000"));
        r.setStrokeWidth(5);
        Paint b = new Paint();
        b.setColor(Color.parseColor("#8B6914"));
        b.setStrokeWidth(5);
        /*cv.drawLine(0, 0, 0, cv.getWidth(), p);
        cv.drawLine(0, 0, cv.getWidth(), 0, p);
        cv.drawLine(cv.getWidth(), 0, cv.getWidth(), cv.getWidth(), p);
        cv.drawLine(0, cv.getWidth(), 2*cv.getWidth()/5, cv.getWidth(), p);
        cv.drawLine(3*cv.getWidth()/5, cv.getWidth(), cv.getWidth(), cv.getWidth(), p);
        cv.drawCircle(4*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
        cv.drawCircle(11*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
        cv.drawCircle(15*cv.getWidth()/30, 8*cv.getWidth()/15, circlewidth, r);*/
        cir1x=4*cv.getWidth()/15;
        cir2x=11*cv.getWidth()/15;
        cir3x=15*cv.getWidth()/30;
        cir1y=4*cv.getWidth()/15;
        cir2y=4*cv.getWidth()/15;
        cir3y=8*cv.getWidth()/30;
        //cv.drawCircle(15*cv.getWidth()/30, cv.getHeight()-circlewidth, circlewidth, b);
        cir4x=15*cv.getWidth()/30;
        cir4y=cv.getHeight()-circlewidth;
        initroad();
        int[] cir1 = new int[1000];
        
    }
	
	
	
	/*
	 * Author:Saransh
	 * function to draw barrels and horse on canvas
	 */
	@Override
    protected void onDraw(Canvas cv) {
    	if(notstarted == 0)
    	{
    		cv.drawColor(Color.GREEN);
            Paint p = new Paint();
            barrel1=0;
            barrel2=0;
            barrel3=0;
            p.setColor(Color.BLACK);
            p.setStrokeWidth(5);
            Paint r = new Paint();
            r.setColor(Color.parseColor("#FF0000"));
            r.setStrokeWidth(5);
            Paint b = new Paint();
            b.setColor(Color.parseColor("#8B6914"));
            b.setStrokeWidth(5);
            cv.drawLine(0, 0, 0, cv.getWidth(), p);
            cv.drawLine(0, 0, cv.getWidth(), 0, p);
            cv.drawLine(cv.getWidth(), 0, cv.getWidth(), cv.getWidth(), p);
            cv.drawLine(0, cv.getWidth(), 2*cv.getWidth()/5, cv.getWidth(), p);
            cv.drawLine(3*cv.getWidth()/5, cv.getWidth(), cv.getWidth(), cv.getWidth(), p);
            cv.drawCircle(4*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
            cv.drawCircle(11*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
            cv.drawCircle(15*cv.getWidth()/30, 8*cv.getWidth()/15, circlewidth, r);
            cv.drawCircle(15*cv.getWidth()/30, cv.getHeight()-circlewidth, circlewidth, b);
            
            maxheight = cv.getWidth();
            maxwidth = maxheight;
            //int[][] roadcovered = new int[(int)cv.getWidth()][(int)cv.getWidth()];
            initroad();
    	}
    	else if(gameover==0)
    	{
	        cv.drawColor(Color.GREEN);
	        Paint p = new Paint();
	        p.setColor(Color.BLACK);
	        p.setStrokeWidth(5);
	        Paint r = new Paint();
	        r.setColor(Color.parseColor("#FF0000"));
	        r.setStrokeWidth(5);
	        Paint b = new Paint();
	        b.setColor(Color.parseColor("#8B6914"));
	        b.setStrokeWidth(5);
	        cv.drawLine(0, 0, 0, cv.getWidth(), p);
	        cv.drawLine(0, 0, cv.getWidth(), 0, p);
	        cv.drawLine(cv.getWidth(), 0, cv.getWidth(), cv.getWidth(), p);
	        cv.drawLine(0, cv.getWidth(), 2*cv.getWidth()/5, cv.getWidth(), p);
	        cv.drawLine(3*cv.getWidth()/5, cv.getWidth(), cv.getWidth(), cv.getWidth(), p);
	        cv.drawCircle(4*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
	        cv.drawCircle(11*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
	        cv.drawCircle(15*cv.getWidth()/30, 8*cv.getWidth()/15, circlewidth, r);
	        cir1x=4*cv.getWidth()/15;
	        cir2x=11*cv.getWidth()/15;
	        cir3x=15*cv.getWidth()/30;
	        cir1y=4*cv.getWidth()/15;
	        cir2y=4*cv.getWidth()/15;
	        cir3y=8*cv.getWidth()/30;
	        cv.drawCircle((cv.getWidth()*x/10),(cv.getWidth()*(y)/10)+76, circlewidth, b);
	        cir4x=(cv.getWidth()*x/10);
	        cir4y=(cv.getWidth()*(y)/10)+76;
	        gameover();
	        if(istouchwall()==1)
	        {
	        	cv.drawLine(0, 0, 0, cv.getWidth(), p);
		        cv.drawLine(0, 0, cv.getWidth(), 0, p);
		        cv.drawLine(cv.getWidth(), 0, cv.getWidth(), cv.getWidth(), p);
		        cv.drawLine(0, cv.getWidth(), 2*cv.getWidth()/5, cv.getWidth(), p);
		        cv.drawLine(3*cv.getWidth()/5, cv.getWidth(), cv.getWidth(), cv.getWidth(), p);
		        cv.drawCircle(4*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
		        cv.drawCircle(11*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
		        cv.drawCircle(15*cv.getWidth()/30, 8*cv.getWidth()/15, circlewidth, r);
		        cv.drawCircle((cv.getWidth()*x/10),(cv.getWidth()*(y)/10)+76, circlewidth, b);
		        cir4x=(cv.getWidth()*x/10);
		        cir4y=(cv.getWidth()*(y)/10)+76;
	        }
    	}
    	else if(finishgame==1)
    	{
    		cv.drawColor(Color.GREEN);
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setStrokeWidth(5);
            Paint r = new Paint();
            r.setColor(Color.parseColor("#FF0000"));
            r.setStrokeWidth(5);
            Paint b = new Paint();
            b.setColor(Color.parseColor("#8B6914"));
            b.setStrokeWidth(5);
            Paint bl = new Paint();
            bl.setStrokeWidth(50);
            bl.setTextSize(64);
            bl.setColor(Color.parseColor("#8B6914"));
            cv.drawLine(0, 0, 0, cv.getWidth(), p);
            cv.drawLine(0, 0, cv.getWidth(), 0, p);
            cv.drawLine(cv.getWidth(), 0, cv.getWidth(), cv.getWidth(), p);
            cv.drawLine(0, cv.getWidth(), 2*cv.getWidth()/5, cv.getWidth(), p);
            cv.drawLine(3*cv.getWidth()/5, cv.getWidth(), cv.getWidth(), cv.getWidth(), p);
            cv.drawCircle(4*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
            cv.drawCircle(11*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
            cv.drawCircle(15*cv.getWidth()/30, 8*cv.getWidth()/15, circlewidth, r);
            initroad();
            barrel1=0;
            barrel2=0;
            barrel3=0;
            cv.drawCircle(15*cv.getWidth()/30, cv.getHeight()-circlewidth, circlewidth, b);
            cv.drawText("You Won.", cv.getWidth()/3, cv.getWidth()/2, bl);
    	}
    	else
    	{
    		cv.drawColor(Color.GREEN);
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setStrokeWidth(5);
            Paint r = new Paint();
            r.setColor(Color.parseColor("#FF0000"));
            r.setStrokeWidth(5);
            Paint b = new Paint();
            b.setColor(Color.parseColor("#8B6914"));
            b.setStrokeWidth(5);
            Paint bl = new Paint();
            bl.setStrokeWidth(50);
            bl.setTextSize(64);
            bl.setColor(Color.parseColor("#8B6914"));
            cv.drawLine(0, 0, 0, cv.getWidth(), p);
            cv.drawLine(0, 0, cv.getWidth(), 0, p);
            cv.drawLine(cv.getWidth(), 0, cv.getWidth(), cv.getWidth(), p);
            cv.drawLine(0, cv.getWidth(), 2*cv.getWidth()/5, cv.getWidth(), p);
            cv.drawLine(3*cv.getWidth()/5, cv.getWidth(), cv.getWidth(), cv.getWidth(), p);
            cv.drawCircle(4*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
            cv.drawCircle(11*cv.getWidth()/15, 4*cv.getWidth()/15, circlewidth, r);
            cv.drawCircle(15*cv.getWidth()/30, 8*cv.getWidth()/15, circlewidth, r);
            initroad();
            barrel1=0;
            barrel2=0;
            barrel3=0;
            cv.drawCircle(15*cv.getWidth()/30, cv.getHeight()-circlewidth, circlewidth, b);
            cv.drawText("Game Over.", cv.getWidth()/3, cv.getWidth()/2, bl);
    	}
        
    }
	
	
	/*
	 * Author:Saransh
	 * function to set game over value
	 */
	public void setgameover()
	{
		gameover=0;
	}
	
	
	/*
	 * Author:Saransh
	 * function to check game over value
	 */
	public int isgameover()
	{
		if(gameover==1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	
	
	/*
	 * Author:Saransh
	 * function to update horse position
	 */
	public void updateposition(float xf,float yf,int valid)
    {
    	//Toast.makeText(this.getContext(), "X:"+x+" Y:"+y, Toast.LENGTH_SHORT).show();
    	x=xf;
    	y=yf;
    	notstarted = valid;
    	invalidate();
    }
	
	
	
}