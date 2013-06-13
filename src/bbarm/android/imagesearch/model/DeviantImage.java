package bbarm.android.imagesearch.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviantImage extends Image implements Parcelable{
	private int width;
	private int height;
	private String title;
	private String link;
	private String thumbnail;
	private String content;
	
	public DeviantImage() {
		this(-1, -1, null, null, null, null);
	}
	
	public DeviantImage(int width, int height, String title, String link,
			String thumbnail, String content) {
		super();
		this.width = width;
		this.height = height;
		this.title = title;
		this.link = link;
		this.thumbnail = thumbnail;
		this.content = content;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
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
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(width);
		dest.writeInt(height);
		dest.writeString(title);
		dest.writeString(link);
		dest.writeString(content);
		dest.writeString(thumbnail);
	}
	
	public DeviantImage(Parcel in) {
		this.width = in.readInt();
		this.height = in.readInt();
		this.title = in.readString();
		this.link = in.readString();
		this.content = in.readString();
		this.thumbnail = in.readString();
	}
	
	public static final Parcelable.Creator<DeviantImage> CREATOR = new Parcelable.Creator<DeviantImage>() {
		public DeviantImage createFromParcel(Parcel in) {
			return new DeviantImage(in);
		}

		public DeviantImage[] newArray(int size) {
			return new DeviantImage[size];
		}
	};
	
}
