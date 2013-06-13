package bbarm.android.imagesearch;

import java.util.ArrayList;

import bbarm.android.imagesearch.model.FoundImage;
import bbarm.android.imagesearch.parser.FoundParser;
import bbarm.android.imagesearch.parser.Parser.ParseCallBack;
import bbarm.android.imagesearch.view.FoundListLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class FoundActivity extends Activity {

	PullToRefreshScrollView scrollView;
	FoundListLayout foundListView;

	FoundParser parser;

	int currentPage = 1;

	ArrayList<FoundImage> imageList = new ArrayList<FoundImage>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_found);

		setTitle(getResources().getText(R.string.ffffound));

		scrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
		foundListView = (FoundListLayout) findViewById(R.id.foundListView);
		
		foundListView.setOrientation(LinearLayout.VERTICAL);

		scrollView.setMode(Mode.PULL_FROM_END);

		scrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				loadImages(++currentPage);
			}
		});
		
		loadImages(currentPage);
	}

	private void loadImages(int page) {
		parser = new FoundParser(this, new ParseCallBack() {

			@SuppressWarnings("unchecked")
			@Override
			public void parseFinishedCallBack(ArrayList<?> result) {
				final ArrayList<FoundImage> listTmp = (ArrayList<FoundImage>) result;
				imageList.addAll(listTmp);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						foundListView.addFoundImageViews(listTmp);
						scrollView.onRefreshComplete();
					}
				});
			}

			@Override
			public void parseFailedCallBack(final Exception e) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(FoundActivity.this, e.getMessage(),
								Toast.LENGTH_SHORT).show();
						scrollView.onRefreshComplete();
					}
				});
			}
		});
		
		parser.parsePage(page);
	}

}
