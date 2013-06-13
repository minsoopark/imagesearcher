package bbarm.android.imagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SearchActivity extends Activity {

	ImageView dLogoImageView;
	ImageView fLogoImageView;
	EditText keywordEditText;
	Button searchButton;
	
	Intent newIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		setTitle(getResources().getText(R.string.search));

		dLogoImageView = (ImageView) findViewById(R.id.dLogoImageView);
		fLogoImageView = (ImageView) findViewById(R.id.fLogoImageView);
		keywordEditText = (EditText) findViewById(R.id.keywordEditText);
		searchButton = (Button) findViewById(R.id.searchButton);

		Intent intent = getIntent();
		String from = intent.getStringExtra("from");
		
		if (from.equals("flickr")) {
			fLogoImageView.setVisibility(View.VISIBLE);
			newIntent = new Intent(SearchActivity.this,
					FlickrActivity.class);
		} else {
			dLogoImageView.setVisibility(View.VISIBLE);
			newIntent = new Intent(SearchActivity.this,
					DeviantActivity.class);
		}
		
		searchButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				newIntent.putExtra("keyword", keywordEditText.getText().toString());
				startActivity(newIntent);
			}
		});
	}

}
