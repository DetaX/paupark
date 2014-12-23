package fr.univpau.paupark.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import fr.univpau.paupark.R;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread background = new Thread(){
			public void run(){
				try {	
					sleep(30);
					Intent i = new Intent(getBaseContext(), MainScreen.class);
					startActivity(i);
					finish();
				}
				catch(Exception e){
				}
			}
		};
		
		background.start();
	}

}
