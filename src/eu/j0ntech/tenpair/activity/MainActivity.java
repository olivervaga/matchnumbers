package eu.j0ntech.tenpair.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import eu.j0ntech.tenpair.R;

public class MainActivity extends Activity {
	
	private Button mStartButton;
	private Button mLoadButton;
	private Button mExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mStartButton = (Button) findViewById(R.id.button_start);
        mLoadButton = (Button) findViewById(R.id.button_load);
        mExitButton = (Button) findViewById(R.id.button_exit);
        
        mStartButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, GameActivity.class));
				
			}
		});
        
        mExitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();	
			}
		});
    }
    
}
