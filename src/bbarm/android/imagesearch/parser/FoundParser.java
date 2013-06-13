package bbarm.android.imagesearch.parser;

import java.util.ArrayList;

import android.content.Context;
import bbarm.android.imagesearch.model.FoundImage;

@SuppressWarnings("unchecked")
public class FoundParser extends Parser {

	private int currentPage = 1;

	public FoundParser(Context context, ParseCallBack callBack) {
		super(context);
		this.list = new ArrayList<FoundImage>();
		this.callBack = callBack;
	}

	public ArrayList<FoundImage> getImages() {
		return (ArrayList<FoundImage>) this.list;
	}

	public ArrayList<FoundImage> parsePage(int page) {
		setType(FFFFOUND);
		String url = "http://ffffound.com/feed?offset=" + (page * 25);
		
		StreamLoader loader = new StreamLoader();
		loader.execute(url);

		return (ArrayList<FoundImage>) list;
	}

	public ArrayList<FoundImage> parseNextPage() {
		return parsePage(++currentPage);
	}

	public ArrayList<FoundImage> parsePrevPage() {
		return parsePage(--currentPage);
	}
}
