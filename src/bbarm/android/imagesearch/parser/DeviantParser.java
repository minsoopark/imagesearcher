package bbarm.android.imagesearch.parser;

import java.util.ArrayList;

import android.content.Context;
import bbarm.android.imagesearch.model.DeviantImage;

@SuppressWarnings("unchecked")
public class DeviantParser extends Parser{

	private String keyword = null;
	private int currentPage = 1;

	public DeviantParser(Context context, ParseCallBack callBack) {
		super(context);
		this.list = new ArrayList<DeviantImage>();
		this.callBack = callBack;
	}

	public ArrayList<DeviantImage> getImages() {
		return (ArrayList<DeviantImage>) this.list;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public ArrayList<DeviantImage> parsePage(int page) {
		setType(DEVIANT);
		String url = "http://backend.deviantart.com/rss.xml?type=deviation&q="
				+ keyword + "&offset=" + (page * 60);
		
		StreamLoader loader = new StreamLoader();
		loader.execute(url);

		return (ArrayList<DeviantImage>) list;
	}

	public ArrayList<DeviantImage> parseNextPage() {
		return parsePage(++currentPage);
	}

	public ArrayList<DeviantImage> parsePrevPage() {
		return parsePage(--currentPage);
	}
	
}
