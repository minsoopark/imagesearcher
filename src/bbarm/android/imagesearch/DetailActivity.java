package bbarm.android.imagesearch;

import java.util.List;

import com.androidquery.AQuery;

import bbarm.android.imagesearch.model.DeviantImage;
import bbarm.android.imagesearch.model.FlickrImage;
import bbarm.android.imagesearch.model.FoundImage;
import bbarm.android.imagesearch.model.Image;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {

	ImageView imageView;
	TextView titleTextView;
	TextView authorTextView;
	Button linkButton;
	Button downloadButton;
	Button shareButton;

	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		imageView = (ImageView) findViewById(R.id.imageView);
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		authorTextView = (TextView) findViewById(R.id.authorTextView);
		linkButton = (Button) findViewById(R.id.linkButton);
		downloadButton = (Button) findViewById(R.id.downloadButton);
		shareButton = (Button) findViewById(R.id.shareButton);

		intent = getIntent();
		int type = intent.getIntExtra("type", -1);

		if (type == Image.FLICKR) {
			initFlickr();
		} else if (type == Image.DEVIANT) {
			initDeviant();
		} else {
			initFound();
		}
	}
	
	private void initFlickr() {
		final FlickrImage image = intent.getParcelableExtra("image");
		AQuery query = new AQuery(this);
		query.id(imageView).image(image.getContent(), false, false, 0, 0, null,
				AQuery.FADE_IN);
		
		titleTextView.setText(image.getTitle());
		initButtons(image.getTitle(), image.getLink(), image.getContent());

		setTitle(image.getTitle());
	}

	private void initDeviant() {
		final DeviantImage image = intent.getParcelableExtra("image");
		AQuery query = new AQuery(this);
		query.id(imageView).image(image.getContent(), false, false, 0, 0, null,
				AQuery.FADE_IN);
		
		titleTextView.setText(image.getTitle());
		initButtons(image.getTitle(), image.getLink(), image.getContent());

		setTitle(image.getTitle());
	}

	private void initFound() {
		final FoundImage image = intent.getParcelableExtra("image");
		AQuery query = new AQuery(this);
		query.id(imageView).image(image.getContent(), false, false, 0, 0, null,
				AQuery.FADE_IN);

		titleTextView.setText(image.getTitle());
		authorTextView.setText("by " + image.getAuthor());
		initButtons(image.getTitle(), image.getLink(), image.getContent());

		setTitle(image.getTitle());
	}

	private void initButtons(final String title, final String link,
			final String content) {

		linkButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse(link);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		downloadButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				Uri uriToDownload = Uri.parse(content);
				List<String> pathSegments = uriToDownload.getPathSegments();
				DownloadManager.Request request = new DownloadManager.Request(
						uriToDownload);
				request.setTitle("이미지 다운로드");
				request.setDescription(title);
				request.setDestinationInExternalPublicDir(
						Environment.DIRECTORY_DOWNLOADS,
						pathSegments.get(pathSegments.size() - 1));
				Environment.getExternalStoragePublicDirectory(
						Environment.DIRECTORY_DOWNLOADS).mkdirs();
				downloadManager.enqueue(request);
			}
		});
		shareButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
				intent.putExtra(android.content.Intent.EXTRA_TEXT, link);
				startActivity(Intent.createChooser(intent, "공유할 앱 선택"));
			}
		});

	}
}
