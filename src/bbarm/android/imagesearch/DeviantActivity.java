package bbarm.android.imagesearch;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import bbarm.android.imagesearch.model.DeviantImage;
import bbarm.android.imagesearch.parser.DeviantParser;
import bbarm.android.imagesearch.parser.Parser.ParseCallBack;
import bbarm.android.imagesearch.view.DeviantListLayout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class DeviantActivity extends Activity {

	String keyword;
	int currentPage = 1;
	DeviantParser parser;

	PullToRefreshScrollView scrollView;
	DeviantListLayout[] listLayout;

	ArrayList<DeviantImage> imageList = new ArrayList<DeviantImage>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deviant);

		setTitle(getResources().getText(R.string.deviant));

		listLayout = new DeviantListLayout[3];

		scrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
		listLayout[0] = (DeviantListLayout) findViewById(R.id.leftListView);
		listLayout[1] = (DeviantListLayout) findViewById(R.id.centerListView);
		listLayout[2] = (DeviantListLayout) findViewById(R.id.rightListView);

		for (int i = 0; i < 3; i++) {
			listLayout[i].setOrientation(LinearLayout.VERTICAL);
		}

		Intent intent = getIntent();
		keyword = intent.getStringExtra("keyword");
		loadImages(keyword, currentPage);

		scrollView.setMode(Mode.PULL_FROM_END);

		scrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				loadImages(keyword, ++currentPage);
			}
		});
	}

	private void loadImages(String keyword, int page) {
		parser = new DeviantParser(this, new ParseCallBack() {

			@SuppressWarnings("unchecked")
			@Override
			public void parseFinishedCallBack(ArrayList<?> result) {
				final ArrayList<DeviantImage> listTmp = (ArrayList<DeviantImage>) result;
				final ArrayList<DeviantImage>[] lists = devideList(listTmp);
				imageList.addAll(listTmp);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						listLayout[0].addDeviantImageViews(lists[0]);
						listLayout[1].addDeviantImageViews(lists[1]);
						listLayout[2].addDeviantImageViews(lists[2]);
						scrollView.onRefreshComplete();
					}
				});
			}

			@Override
			public void parseFailedCallBack(final Exception e) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(DeviantActivity.this, e.getMessage(),
								Toast.LENGTH_SHORT).show();
						scrollView.onRefreshComplete();
					}
				});
			}

		});

		parser.setKeyword(keyword);
		parser.parsePage(page);
	}

	private ArrayList<DeviantImage>[] devideList(List<DeviantImage> list) {
		@SuppressWarnings("unchecked")
		ArrayList<DeviantImage>[] lists = new ArrayList[3];
		lists[0] = new ArrayList<DeviantImage>();
		lists[1] = new ArrayList<DeviantImage>();
		lists[2] = new ArrayList<DeviantImage>();
		for (int i = 0; i < list.size(); i++) {
			switch (i % 3) {
			case 0:
				lists[0].add(list.get(i));
				break;

			case 1:
				lists[1].add(list.get(i));
				break;

			case 2:
				lists[2].add(list.get(i));
				break;
			}
		}
		return lists;
	}
}
