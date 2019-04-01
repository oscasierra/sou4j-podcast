package jp.sou4j.podcast.util;

import java.text.ParseException;
import java.util.Date;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jp.sou4j.podcast.PodcastEpisode;
import jp.sou4j.podcast.PodcastEpisode.Enclosure;
import jp.sou4j.podcast.xml.NodeUtils;
import jp.sou4j.util.StringUtils;

public class PodcastEpisodeConvertUtils {

	public static PodcastEpisode toPodcastEpisode(Node node) throws ParseException {
		PodcastEpisode episode = new PodcastEpisode();

		NodeList childNodes = node.getChildNodes();
		for(int i=0; i<childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			if( childNode.getNodeName().equals("content:encoded") ) {
				episode.setContentEncoded(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("description") ) {
				episode.setDescription(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("itunes:subtitle") ) {
				episode.getITunesInfo().setSubtitie(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("itunes:summary") ) {
				episode.getITunesInfo().setSummary(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("title") ) {
				episode.setTitle(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("itunes:title") ) {
				episode.getITunesInfo().setTitle(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("enclosure") ) {
				Enclosure enclosure = episode.new Enclosure();
				String  url   = NodeUtils.getAttributeValue(childNode, "url");
				String  type  = NodeUtils.getAttributeValue(childNode, "type");
				String length = NodeUtils.getAttributeValue(childNode, "length");
				enclosure.setUrl(url);
				enclosure.setType(type);
				if( !StringUtils.isNullOrEmpty(length) ) {
					enclosure.setLength(new Integer(length));
				}
				episode.setEnclosure(enclosure);
			}
			else if( childNode.getNodeName().equals("guid") ) {
				episode.setGUID(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("itunes:author") ) {
				episode.getITunesInfo().setAuthor(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("itunes:block") ) {
				String value = NodeUtils.getChildTextNodeValue(childNode);
				Boolean bool = (value.equals("Yes"));
				episode.getITunesInfo().setBlock(bool);
			}
			else if( childNode.getNodeName().equals("itunes:duration") ) {
				episode.getITunesInfo().setDuration(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("itunes:episode") ) {
				String value = NodeUtils.getChildTextNodeValue(childNode);
				episode.getITunesInfo().setEpisode(new Integer(value));
			}
			else if( childNode.getNodeName().equals("itunes:episodeType") ) {
				episode.getITunesInfo().setEpisodeType(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("itunes:explicit") ) {
				String value = NodeUtils.getChildTextNodeValue(childNode);
				Boolean bool = (value.equals("yes") || value.equals("explicit") || value.equals("true"));
				episode.getITunesInfo().setExplicit(bool);
			}
			else if( childNode.getNodeName().equals("itunes:image") ) {
				String value = NodeUtils.getAttributeValue(childNode, "href");
				episode.getITunesInfo().setImage(value);
			}
			else if( childNode.getNodeName().equals("itunes:isClosedCaptioned") ) {
				String value = NodeUtils.getChildTextNodeValue(childNode);
				Boolean bool = (value.equals("Yes"));
				episode.getITunesInfo().setIsClosedCaptioned(bool);
			}
			else if( childNode.getNodeName().equals("itunes:order") ) {
				String value = NodeUtils.getChildTextNodeValue(childNode);
				episode.getITunesInfo().setOrder(new Integer(value));
			}
			else if( childNode.getNodeName().equals("tunes:season") ) {
				String value = NodeUtils.getChildTextNodeValue(childNode);
				episode.getITunesInfo().setSeason(new Integer(value));
			}
			else if( childNode.getNodeName().equals("link") ) {
				episode.setLink(NodeUtils.getChildTextNodeValue(childNode));
			}
			else if( childNode.getNodeName().equals("pubDate") ) {
				String value = NodeUtils.getChildTextNodeValue(childNode);
				if( !StringUtils.isNullOrEmpty(value) ) {
					Date date = DateUtils.parse(value);
					episode.setPubDate(date);
				}
			}
		}

		return episode;
	}
}
