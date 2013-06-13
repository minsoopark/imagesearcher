package bbarm.android.imagesearch.view;

import java.util.ArrayList;

import com.androidquery.AQuery;

import bbarm.android.imagesearch.DetailActivity;
import bbarm.android.imagesearch.R;
import bbarm.android.imagesearch.model.DeviantImage;
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

public class DeviantListLayout extends LinearLayout {

	Context mContext;
	int count = 0;
	ArrayList<DeviantImage> mList;

	public DeviantListLayout(Context context) {
		super(context);
		this.mContext = context;
	}

	public DeviantListLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public void addDeviantImageViews(ArrayList<DeviantImage> list) {
		mList = list;
		LayoutInflater li = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v;
		for (DeviantImage deviantImage : mList) {
			v = li.inflate(R.layout.each_deviant, null);
			ImageView thumbnailImageView = (ImageView) v
					.findViewById(R.id.thumbnailImageView);
			TextView titleTextView = (TextView) v
					.findViewById(R.id.titleTextView);
			ImageButton imageButton = (ImageButton) v
					.findViewById(R.id.imageButton);

			if (deviantImage.getThumbnail() != null) {
				AQuery query = new AQuery(mContext);
				query.id(thumbnailImageView).image(deviantImage.getThumbnail(),
						false, false, 0, 0, null, AQuery.FADE_IN);
			} else {

			}

			titleTextView.setText(deviantImage.getTitle());

			final DeviantImage image = deviantImage;
			
			imageButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, DetailActivity.class);
					intent.putExtra("type", Image.DEVIANT);
					intent.putExtra("image", image);
					mContext.startActivity(intent);
				}
			});
			
			this.removeView(v);
			this.count++;
			this.addView(v);
		}
	}

	public int getCount() {
		return this.count;
	}

	public DeviantImage getItem(int index) {
		return this.mList.get(index);
	}

}
