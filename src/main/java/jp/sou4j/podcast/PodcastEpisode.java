package jp.sou4j.podcast;

import java.util.Date;

public class PodcastEpisode {

	private String title;
	private String contentEncoded;
	private String link;
	private String GUID;
	private Date pubDate;
	private String description;
	private ITunesInfo iTunesInfo;
	private Enclosure enclosure;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGUID() {
		return GUID;
	}
	public void setGUID(String gUID) {
		GUID = gUID;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContentEncoded() {
		return contentEncoded;
	}
	public void setContentEncoded(String contentEncoded) {
		this.contentEncoded = contentEncoded;
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

	public Enclosure getEnclosure() {
		if( this.enclosure == null ) {
			this.enclosure = new Enclosure();
		}
		return enclosure;
	}
	public void setEnclosure(Enclosure enclosure) {
		this.enclosure = enclosure;
	}

	public class ITunesInfo {

		private String subtitie;
		private String summary;
		private String title;
		private String author;
		private Boolean block;
		private String duration;
		private Integer episode;
		private String episodeType;
		private Boolean explicit;
		private String image;
		private Boolean isClosedCaptioned;
		private Integer order;
		private Integer season;

		public String getSubtitie() {
			return subtitie;
		}
		public void setSubtitie(String subtitie) {
			this.subtitie = subtitie;
		}
		public String getSummary() {
			return summary;
		}
		public void setSummary(String summary) {
			this.summary = summary;
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
		public Boolean getBlock() {
			return block;
		}
		public void setBlock(Boolean block) {
			this.block = block;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public Integer getEpisode() {
			return episode;
		}
		public void setEpisode(Integer episode) {
			this.episode = episode;
		}
		public String getEpisodeType() {
			return episodeType;
		}
		public void setEpisodeType(String episodeType) {
			this.episodeType = episodeType;
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
		public Boolean getIsClosedCaptioned() {
			return isClosedCaptioned;
		}
		public void setIsClosedCaptioned(Boolean isClosedCaptioned) {
			this.isClosedCaptioned = isClosedCaptioned;
		}
		public Integer getOrder() {
			return order;
		}
		public void setOrder(Integer order) {
			this.order = order;
		}
		public Integer getSeason() {
			return season;
		}
		public void setSeason(Integer season) {
			this.season = season;
		}
	}

	public class Enclosure {

		private String url;
		private String type;
		private Integer length;

		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Integer getLength() {
			return length;
		}
		public void setLength(Integer length) {
			this.length = length;
		}
	}
}
