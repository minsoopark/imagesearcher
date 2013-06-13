package bbarm.android.imagesearch;

import java.util.ArrayList;

import bbarm.android.imagesearch.model.FlickrImage;
import bbarm.android.imagesearch.parser.FlickrParser;
import bbarm.android.imagesearch.parser.Parser.ParseCallBack;
import bbarm.android.imagesearch.view.FlickrListLayout;

import com.gmail.yuyang226.flickr.photos.Photo;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class FlickrActivity extends Activity {

	int currentPage = 1;
	ArrayList<Photo> photoList = new ArrayList<Photo>();
	ArrayList<FlickrImage> flickrList = new ArrayList<FlickrImage>();
	
	PullToRefreshScrollView scrollView;
	FlickrListLayout flickrListView;
	
	String keyword;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flickr);
		
		setTitle(getResources().getText(R.string.flickr));
		
		flickrListView = (FlickrListLayout) findViewById(R.id.flickrListView);
		scrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
		
		flickrListView.setOrientation(LinearLayout.VERTICAL);
		
		scrollView.setMode(Mode.PULL_FROM_END);
		
		scrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				loadPhotos(keyword, ++currentPage);
			}
		});
		
		Intent intent = getIntent();
		keyword = intent.getStringExtra("keyword");

		loadPhotos(keyword, currentPage);
	}

	private void loadPhotos(String keyword, int page) {
		FlickrParser parser = new FlickrParser(new ParseCallBack() {

			@SuppressWarnings("unchecked")
			@Override
			public void parseFinishedCallBack(ArrayList<?> result) {
				photoList = (ArrayList<Photo>) result;
				for (Photo photo : photoList) {
					FlickrImage image = new FlickrImage();
					image.setTitle(photo.getTitle());
					image.setDescription(photo.getDescription());
					image.setLink(photo.getUrl());

					String builtUrl = "http://farm" + photo.getFarm()
							+ ".static.flickr.com/" + photo.getServer() + "/"
							+ photo.getId() + "_" + photo.getSecret();
					image.setContent(builtUrl + "_d." + photo.getOriginalFormat());
					image.setThumbnail(builtUrl + "_n_d." + photo.getOriginalFormat());
					flickrList.add(image);
				}
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						flickrListView.addFlickrImageViews(flickrList);
						scrollView.onRefreshComplete();
					}
				});
			}

			@Override
			public void parseFailedCallBack(final Exception e) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(FlickrActivity.this, e.getMessage(),
								Toast.LENGTH_SHORT).show();
						scrollView.onRefreshComplete();
					}
				});
			}
		});

		parser.parsePage(keyword, page);
	}

}
