package fr.univpau.paupark.screen;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.TipAddListener;

public class TipAddScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tip_add);
		Button button = (Button)findViewById(R.id.button_add_tip);
		button.setOnClickListener(new TipAddListener(this, MainScreen.TIPS));
		
	}
}
