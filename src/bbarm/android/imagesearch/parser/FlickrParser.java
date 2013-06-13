package bbarm.android.imagesearch.parser;


import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import com.gmail.yuyang226.flickr.Flickr;
import com.gmail.yuyang226.flickr.REST;
import com.gmail.yuyang226.flickr.photos.Photo;
import com.gmail.yuyang226.flickr.photos.PhotosInterface;
import com.gmail.yuyang226.flickr.photos.SearchParameters;

import android.os.AsyncTask;
import bbarm.android.imagesearch.parser.Parser.ParseCallBack;

public class FlickrParser {
	private String apiKey = "0d12cb9e6ded4f3f7f14f55355902e7a";
	private String svr = "www.flickr.com";
	private REST rest = null;
	private Flickr flickr;
	private SearchParameters searchParams;
	private PhotosInterface photosInterface;

	int page;

	private ParseCallBack callBack;
	private ArrayList<Photo> photoList;

	public FlickrParser(ParseCallBack callBack) {
		this.callBack = callBack;
		this.rest = null;
		try {
			rest = new REST();
			rest.setHost(svr);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		this.flickr = new Flickr(apiKey, rest);

		this.searchParams = new SearchParameters();
		searchParams.setSort(SearchParameters.DATE_POSTED_DESC);

		this.photosInterface = flickr.getPhotosInterface();
	}

	public void parsePage(String keyword, int page) {
		searchParams.setText(keyword);
		this.page = page;
		PhotoLoader loader = new PhotoLoader();
		loader.execute();
	}

	private class PhotoLoader extends AsyncTask<Void, Void, ArrayList<Photo>> {

		@Override
		protected ArrayList<Photo> doInBackground(Void... params) {
			try {
				photoList = photosInterface.search(searchParams, 20, page);
			} catch (Exception e) {
				callBack.parseFailedCallBack(e);
			}
			return photoList;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Photo> list) {
			callBack.parseFinishedCallBack(list);
		}
	}
}
