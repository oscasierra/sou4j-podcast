package jp.sou4j.podcast;

import java.util.ArrayList;
import java.util.List;

public class Podcast {

	private String title;
	private String description;
	private String link;
	private String language;
	private String copyright;
	private ITunesInfo iTunesInfo;
	private List<PodcastEpisode> episodes;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public ITunesInfo getITunesInfo() {
		if( this.iTunesInfo == null ) {
			this.iTunesInfo = new ITunesInfo();
		}
		return iTunesInfo;
	}
	public void setITunesInfo(ITunesInfo iTunesInfo) {
		this.iTunesInfo = iTunesInfo;
	}

	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public List<PodcastEpisode> getEpisodes() {
		if( this.episodes == null ) this.episodes = new ArrayList<PodcastEpisode>();
		return episodes;
	}
	public void setEpisodes(List<PodcastEpisode> episodes) {
		this.episodes = episodes;
	}

	public class ITunesInfo {

		private String author;
		private Boolean block;
		private Boolean complete;
		private String newFeedUrl;
		private String summary;
		private ITunesOwner owner;
		private Boolean explicit;
		private String image;
		private String subtitle;
		private String type;
		private List<ITunesCategory> categories = new ArrayList<ITunesCategory>();

		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public Boolean getBlock() {
			return block;
		}
		public void setBlock(Boolean block) {
			this.block = block;
		}
		public String getSummary() {
			return summary;
		}
		public void setSummary(String summary) {
			this.summary = summary;
		}
		public ITunesOwner getOwner() {
			if( this.owner == null ) {
				this.owner = new ITunesOwner();
			}
			return owner;
		}
		public void setOwner(ITunesOwner owner) {
			this.owner = owner;
		}
		public Boolean getExplicit() {
			return explicit;
		}
		public void setExplicit(Boolean explicit) {
			this.explicit = explicit;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public String getSubtitle() {
			return subtitle;
		}
		public void setSubtitle(String subtitle) {
			this.subtitle = subtitle;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public List<ITunesCategory> getCategories() {
			if( categories == null ) {
				categories = new ArrayList<ITunesCategory>();
			}
			return categories;
		}
		public void setCategories(List<ITunesCategory> categories) {
			this.categories = categories;
		}
		public Boolean getComplete() {
			return complete;
		}
		public void setComplete(Boolean complete) {
			this.complete = complete;
		}
		public String getNewFeedUrl() {
			return newFeedUrl;
		}
		public void setNewFeedUrl(String newFeedUrl) {
			this.newFeedUrl = newFeedUrl;
		}
	}

	public class ITunesOwner {

		private String name;
		private String email;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}

	}

	public class ITunesCategory {

		private String name;
		private ITunesCategory subCategory;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public ITunesCategory getSubCategory() {
			return subCategory;
		}
		public void setSubCategory(ITunesCategory subCategory) {
			this.subCategory = subCategory;
		}
	}
}
