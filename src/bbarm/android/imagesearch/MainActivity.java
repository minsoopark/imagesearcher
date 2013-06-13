package bbarm.android.imagesearch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	Button flickrButton;
	Button deviantButton;
	Button ffffoundButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		flickrButton = (Button) findViewById(R.id.flickrButton);
		deviantButton = (Button) findViewById(R.id.deviantButton);
		ffffoundButton = (Button) findViewById(R.id.ffffoundButton);

		flickrButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,
						SearchActivity.class);
				intent.putExtra("from", "flickr");
				startActivity(intent);
			}
		});
		deviantButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SearchActivity.class);
				intent.putExtra("from", "deviant");
				startActivity(intent);
			}
		});
		ffffoundButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						FoundActivity.class);
				startActivity(intent);
			}
		});
	}

}
