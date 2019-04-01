package jp.sou4j.podcast.io;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jp.sou4j.podcast.Podcast;
import jp.sou4j.podcast.Podcast.ITunesCategory;
import jp.sou4j.podcast.Podcast.ITunesOwner;
import jp.sou4j.podcast.PodcastEpisode;
import jp.sou4j.util.StringUtils;

public class PodcastFeedWriter {

	public String writeAsString(Podcast podcast) throws ParserConfigurationException, TransformerException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		Document document = builder.newDocument();
		Element rssElement = this.createRssElement(document);

		// itunes:type
		if( !StringUtils.isNullOrEmpty(podcast.getITunesInfo().getType()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "itunes:type", podcast.getITunesInfo().getType()));
		}
		// description
		if( !StringUtils.isNullOrEmpty(podcast.getDescription()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "description", podcast.getDescription()));
		}
		// itunes:subtitle
		if( !StringUtils.isNullOrEmpty(podcast.getITunesInfo().getSubtitle()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "itunes:subtitle", podcast.getITunesInfo().getSubtitle()));
		}
		// itunes:summary
		if( !StringUtils.isNullOrEmpty(podcast.getITunesInfo().getSummary()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "itunes:summary", podcast.getITunesInfo().getSummary()));
		}
		// title
		if( !StringUtils.isNullOrEmpty(podcast.getTitle()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "title", podcast.getTitle()));
		}
		// copyright
		if( !StringUtils.isNullOrEmpty(podcast.getCopyright()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "copyright", podcast.getCopyright()));
		}
		// itunes:author
		if( !StringUtils.isNullOrEmpty(podcast.getITunesInfo().getAuthor()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "itunes:author", podcast.getITunesInfo().getAuthor()));
		}
		// itunes:block
		if( podcast.getITunesInfo().getBlock() != null ) {
			String value = (podcast.getITunesInfo().getBlock()) ? "yes" : "no" ;
			rssElement.appendChild(this.createSimpleElement(document, "itunes:block", value));
		}
		// itunes:category
		if( podcast.getITunesInfo().getCategories().size() > 0 ) {
			for( ITunesCategory category : podcast.getITunesInfo().getCategories() ) {
				Element categoryElement = document.createElement("itunes:category");
				categoryElement.setAttribute("text", category.getName());
				if( category.getSubCategory() != null ) {
					Element subcategoryElement = document.createElement("itunes:category");
					subcategoryElement.setAttribute("text", category.getSubCategory().getName());
					categoryElement.appendChild(subcategoryElement);
				}
				rssElement.appendChild(categoryElement);
			}
		}
		// itunes:complete
		if( podcast.getITunesInfo().getComplete() != null ) {
			String value = (podcast.getITunesInfo().getComplete()) ? "yes" : "no" ;
			rssElement.appendChild(this.createSimpleElement(document, "itunes:complete", value));
		}
		// itunes:explicit
		if( podcast.getITunesInfo().getExplicit() != null ) {
			String value = (podcast.getITunesInfo().getExplicit()) ? "yes" : "no" ;
			rssElement.appendChild(this.createSimpleElement(document, "itunes:explicit", value));
		}
		// itunes:image
		if( !StringUtils.isNullOrEmpty(podcast.getITunesInfo().getImage()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "itunes:image", podcast.getITunesInfo().getImage()));
		}
		// itunes:new-feed-url
		if( !StringUtils.isNullOrEmpty(podcast.getITunesInfo().getNewFeedUrl()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "itunes:new-feed-url", podcast.getITunesInfo().getNewFeedUrl()));
		}
		// itunes:owner
		ITunesOwner owner = podcast.getITunesInfo().getOwner();
		if( owner != null && (!StringUtils.isNullOrEmpty(owner.getName()) || !StringUtils.isNullOrEmpty(owner.getEmail())) ) {
			Element iTunesOwnerElement = document.createElement("itunes:owner");
			if( !StringUtils.isNullOrEmpty(podcast.getITunesInfo().getOwner().getName()) ) {
				iTunesOwnerElement.appendChild(this.createSimpleElement(document, "itunes:name", podcast.getITunesInfo().getOwner().getName()));
			}
			if( !StringUtils.isNullOrEmpty(podcast.getITunesInfo().getOwner().getEmail()) ) {
				iTunesOwnerElement.appendChild(this.createSimpleElement(document, "itunes:email", podcast.getITunesInfo().getOwner().getEmail()));
			}
			rssElement.appendChild(iTunesOwnerElement);
		}
		// language
		if( !StringUtils.isNullOrEmpty(podcast.getLanguage()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "language", podcast.getLanguage()));
		}
		// link
		if( !StringUtils.isNullOrEmpty(podcast.getLink()) ) {
			rssElement.appendChild(this.createSimpleElement(document, "link", podcast.getLink()));
		}

		// item
		for( PodcastEpisode episode : podcast.getEpisodes() ) {
			Element itemElement = document.createElement("item");

			// content:encoded
			if( !StringUtils.isNullOrEmpty(episode.getContentEncoded()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "content:encoded", episode.getContentEncoded()));
			}
			// description
			if( !StringUtils.isNullOrEmpty(episode.getDescription()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "description", episode.getDescription()));
			}
			// itunes:subtitle
			if( !StringUtils.isNullOrEmpty(episode.getITunesInfo().getSubtitie()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "itunes:subtitle", episode.getITunesInfo().getSubtitie()));
			}
			// itunes:summary
			if( !StringUtils.isNullOrEmpty(episode.getITunesInfo().getSummary()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "itunes:summary", episode.getITunesInfo().getSummary()));
			}
			// title
			if( !StringUtils.isNullOrEmpty(episode.getTitle()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "title", episode.getTitle()));
			}
			// itunes:title
			if( !StringUtils.isNullOrEmpty(episode.getITunesInfo().getTitle()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "itunes:title", episode.getITunesInfo().getTitle()));
			}
			// enclosure
			if( episode.getEnclosure() != null ) {
				Element enclosureElement = document.createElement("enclosure");
				String length = (episode.getEnclosure().getLength() != null) ? episode.getEnclosure().getLength().toString() : null ;
				enclosureElement.setAttribute("url",    episode.getEnclosure().getUrl());
				enclosureElement.setAttribute("length", length);
				enclosureElement.setAttribute("type",   episode.getEnclosure().getType());
				itemElement.appendChild(enclosureElement);
			}
			// guid
			if( !StringUtils.isNullOrEmpty(episode.getGUID()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "guid", episode.getGUID()));
			}
			// itunes:author
			if( !StringUtils.isNullOrEmpty(episode.getITunesInfo().getAuthor()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "itunes:author", episode.getITunesInfo().getAuthor()));
			}
			// itunes:block
			if( episode.getITunesInfo().getBlock() != null ) {
				String value = (episode.getITunesInfo().getBlock()) ? "yes" : "no" ;
				itemElement.appendChild(this.createSimpleElement(document, "itunes:block", value));
			}
			// itunes:duration
			if( !StringUtils.isNullOrEmpty(episode.getITunesInfo().getDuration()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "itunes:duration", episode.getITunesInfo().getDuration()));
			}
			// itunes:episode
			if( episode.getITunesInfo().getEpisode() != null ) {
				itemElement.appendChild(this.createSimpleElement(document, "itunes:episode", episode.getITunesInfo().getEpisode().toString()));
			}
			// itunes:episodeType
			if( !StringUtils.isNullOrEmpty(episode.getITunesInfo().getEpisodeType()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "itunes:episodeType", episode.getITunesInfo().getEpisodeType()));
			}
			// itunes:explicit
			if( episode.getITunesInfo().getExplicit() != null ) {
				String value = (episode.getITunesInfo().getExplicit()) ? "yes" : "no" ;
				itemElement.appendChild(this.createSimpleElement(document, "itunes:explicit", value));
			}
			// itunes:image
			if( !StringUtils.isNullOrEmpty(episode.getITunesInfo().getImage()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "itunes:image", podcast.getITunesInfo().getImage()));
			}
			// itunes:isClosedCaptioned
			if( episode.getITunesInfo().getIsClosedCaptioned() != null ) {
				String value = (episode.getITunesInfo().getIsClosedCaptioned()) ? "yes" : "no" ;
				itemElement.appendChild(this.createSimpleElement(document, "itunes:isClosedCaptioned", value));
			}
			// itunes:order
			if( episode.getITunesInfo().getOrder() != null ) {
				itemElement.appendChild(this.createSimpleElement(document, "itunes:order", episode.getITunesInfo().getOrder().toString()));
			}
			// itunes:season
			if( episode.getITunesInfo().getSeason() != null ) {
				itemElement.appendChild(this.createSimpleElement(document, "itunes:season", episode.getITunesInfo().getSeason().toString()));
			}
			// link
			if( !StringUtils.isNullOrEmpty(episode.getLink()) ) {
				itemElement.appendChild(this.createSimpleElement(document, "link", episode.getLink()));
			}
			// pubDate
			if( episode.getPubDate() != null ) {
				SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
				String value = format.format(episode.getPubDate());
				itemElement.appendChild(this.createSimpleElement(document, "pubDate", value));
			}

			rssElement.appendChild(itemElement);
		}

		document.appendChild(rssElement);

		return this.toString(document);
	}

	private Element createSimpleElement(Document document, String tagName, String textValue) {
		Element element = document.createElement(tagName);
		element.appendChild(document.createTextNode(textValue));
		return element;
	}
	private Element createRssElement(Document document) {
		Element rssElement = document.createElement("rss");
		rssElement.setAttribute("version", "2.0");
		rssElement.setAttribute("xmlns:itunes", "http://www.itunes.com/dtds/podcast-1.0.dtd");
		return rssElement;
	}

	private String toString(Document document) throws TransformerException {
		StringWriter writer = new StringWriter();
		Transformer transformer = TransformerFactory.newInstance().newTransformer(); 
		transformer.transform(new DOMSource(document), new StreamResult(writer)); 
		return writer.toString();
	}
}
