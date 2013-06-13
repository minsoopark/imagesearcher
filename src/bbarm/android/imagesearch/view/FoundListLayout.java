package bbarm.android.imagesearch.view;

import java.util.ArrayList;

import com.androidquery.AQuery;

import bbarm.android.imagesearch.DetailActivity;
import bbarm.android.imagesearch.R;
import bbarm.android.imagesearch.model.FoundImage;
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

public class FoundListLayout extends LinearLayout {

	Context mContext;
	int count = 0;
	ArrayList<FoundImage> mList;

	public FoundListLayout(Context context) {
		super(context);
		this.mContext = context;
	}

	public FoundListLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public void addFoundImageViews(ArrayList<FoundImage> list) {
		mList = list;
		LayoutInflater li = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v;
		for (FoundImage foundImage : mList) {
			v = li.inflate(R.layout.each_found, null);
			ImageView thumbnailImageView = (ImageView) v
					.findViewById(R.id.thumbnailImageView);
			TextView titleTextView = (TextView) v
					.findViewById(R.id.titleTextView);
			TextView authorTextView = (TextView) v
					.findViewById(R.id.authorTextView);
			ImageButton imageButton = (ImageButton) v
					.findViewById(R.id.imageButton);

			if (foundImage.getThumbnail() != null) {
				AQuery query = new AQuery(mContext);
				query.id(thumbnailImageView).image(foundImage.getThumbnailBig(),
						false, false, 0, 0, null, AQuery.FADE_IN);
			} else {

			}

			titleTextView.setText(foundImage.getTitle());
			authorTextView.setText("by " + foundImage.getAuthor());

			final FoundImage image = foundImage;
			imageButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, DetailActivity.class);
					intent.putExtra("type", Image.FFFFOUND);
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

	public FoundImage getItem(int index) {
		return this.mList.get(index);
	}

}
