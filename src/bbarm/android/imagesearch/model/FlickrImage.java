package bbarm.android.imagesearch.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FlickrImage extends Image implements Parcelable{
	private String title;
	private String description;
	private String link;
	private String content;
	private String thumbnail;
	
	public FlickrImage() {
		this(null, null, null, null, null);
	}
	
	public FlickrImage(String title, String description, String link,
			String thumbnail, String content) {
		super();
		this.title = title;
		this.description = description;
		this.link = link;
		this.thumbnail = thumbnail;
		this.content = content;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(link);
		dest.writeString(content);
		dest.writeString(thumbnail);
	}
	
	public FlickrImage(Parcel in) {
		this.title = in.readString();
		this.link = in.readString();
		this.content = in.readString();
		this.thumbnail = in.readString();
	}
	
	public static final Parcelable.Creator<FlickrImage> CREATOR = new Parcelable.Creator<FlickrImage>() {
		public FlickrImage createFromParcel(Parcel in) {
			return new FlickrImage(in);
		}

		public FlickrImage[] newArray(int size) {
			return new FlickrImage[size];
		}
	};
	
}
