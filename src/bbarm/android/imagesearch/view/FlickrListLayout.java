package bbarm.android.imagesearch.view;

import java.util.ArrayList;

import com.androidquery.AQuery;

import bbarm.android.imagesearch.DetailActivity;
import bbarm.android.imagesearch.R;
import bbarm.android.imagesearch.model.FlickrImage;
import bbarm.android.imagesearch.model.Image;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlickrListLayout extends LinearLayout {

	Context mContext;
	int count = 0;
	ArrayList<FlickrImage> mList;

	public FlickrListLayout(Context context) {
		super(context);
		this.mContext = context;
	}

	public FlickrListLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public void addFlickrImageViews(ArrayList<FlickrImage> list) {
		mList = list;
		LayoutInflater li = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = null;

		for (int i = 0; i < mList.size(); i++) {
			FlickrImage flickrImage = mList.get(i);
			AQuery query = new AQuery(mContext);
			if (i % 2 == 0) {
				v = li.inflate(R.layout.each_flickr, null);
				
				ImageView lThumbnailImageView = (ImageView) v
						.findViewById(R.id.lThumbnailImageView);
				TextView lTitleTextView = (TextView) v
						.findViewById(R.id.lTitleTextView);
				ImageButton lImageButton = (ImageButton) v
						.findViewById(R.id.lImageButton);

				if(flickrImage.getThumbnail() != null) {
					query.id(lThumbnailImageView).image(flickrImage.getThumbnail(),
							false, false, 0, 0, null, AQuery.FADE_IN);
				} else {
					
				}

				lTitleTextView.setText(flickrImage.getTitle());
				
				final FlickrImage image = flickrImage;
				lImageButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext, DetailActivity.class);
						intent.putExtra("type", Image.FLICKR);
						intent.putExtra("image", image);
						mContext.startActivity(intent);
					}
				});
			} else {
				ImageView rThumbnailImageView = (ImageView) v
						.findViewById(R.id.rThumbnailImageView);
				TextView rTitleTextView = (TextView) v
						.findViewById(R.id.rTitleTextView);
				ImageButton rImageButton = (ImageButton) v
						.findViewById(R.id.rImageButton);
				
				if(flickrImage.getThumbnail() != null) {
					query.id(rThumbnailImageView).image(flickrImage.getThumbnail(),
							false, false, 0, 0, null, AQuery.FADE_IN);
				} else {
					
				}
				
				rTitleTextView.setText(flickrImage.getTitle());
				
				final FlickrImage image = flickrImage;
				rImageButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext, DetailActivity.class);
						intent.putExtra("type", Image.FLICKR);
						intent.putExtra("image", image);
						mContext.startActivity(intent);
					}
				});
				
				this.removeView(v);
				this.count = this.count + 2;
				this.addView(v);
			}
		}
	}

	public int getCount() {
		return this.count;
	}

	public FlickrImage getItem(int index) {
		return this.mList.get(index);
	}

}
