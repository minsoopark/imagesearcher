package bbarm.android.imagesearch.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FoundImage extends Image implements Parcelable{
	private String title;
	private String author;
	private String link;
	private String thumbnail;
	private String thumbnailBig;
	private String content;
	private String referer;
	
	public FoundImage() {
		this(null, null, null, null, null, null, null);
	}
	
	public FoundImage(String title, String author, String link,
			String thumbnail, String thumbnailBig, String content, String referer) {
		super();
		this.title = title;
		this.author = author;
		this.link = link;
		this.thumbnail = thumbnail;
		this.thumbnailBig = thumbnailBig;
		this.content = content;
		this.referer = referer;
	}

	public String getThumbnailBig() {
		return thumbnailBig;
	}
	public void setThumbnailBig(String thumbnailBig) {
		this.thumbnailBig = thumbnailBig;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = title;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
		dest.writeString(title);
		dest.writeString(author);
		dest.writeString(link);
		dest.writeString(content);
		dest.writeString(thumbnail);
		dest.writeString(thumbnailBig);
		dest.writeString(referer);
	}
	
	public FoundImage(Parcel in) {
		this.title = in.readString();
		this.author = in.readString();
		this.link = in.readString();
		this.content = in.readString();
		this.thumbnail = in.readString();
		this.thumbnailBig = in.readString();
		this.referer = in.readString();
	}
	
	public static final Parcelable.Creator<FoundImage> CREATOR = new Parcelable.Creator<FoundImage>() {
		public FoundImage createFromParcel(Parcel in) {
			return new FoundImage(in);
		}

		public FoundImage[] newArray(int size) {
			return new FoundImage[size];
		}
	};
}
