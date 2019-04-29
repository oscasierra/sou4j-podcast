package jp.sou4j.podcast.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jp.sou4j.podcast.Podcast;
import jp.sou4j.podcast.PodcastEpisode;
import jp.sou4j.podcast.util.PodcastEpisodeConvertUtils;
import jp.sou4j.podcast.xml.NodeUtils;

public class PodcastFeedReader {

	public Podcast readAsPodcast(URL url) throws SAXException, IOException, ParserConfigurationException, ParseException {
		Document document = this.getDocument(url);
		Element rssElement = document.getDocumentElement();

		Podcast podcast = new Podcast();

		if( rssElement.getNodeName().equals("rss") ) {
			Node channelNode = this.getChildNodeByName(rssElement, "channel");
			if( channelNode != null ) {
				// title
				Node titleNode = this.getChildNodeByName(channelNode, "title");
				if( titleNode != null ) podcast.setTitle(NodeUtils.getChildTextNodeValue(titleNode)) ;

				// description
				Node descriptionNode = this.getChildNodeByName(channelNode, "description");
				if( descriptionNode != null ) podcast.setDescription(NodeUtils.getChildTextNodeValue(descriptionNode));

				// iTunes:type
				Node iTunesTypeNode = this.getChildNodeByName(channelNode, "itunes:type");
				if( iTunesTypeNode != null ) podcast.getITunesInfo().setType(NodeUtils.getChildTextNodeValue(iTunesTypeNode));

				// iTUnes:subtitle
				Node iTunesSubtitleNode = this.getChildNodeByName(channelNode, "itunes:subtitle");
				if( iTunesSubtitleNode != null ) podcast.getITunesInfo().setSubtitle(NodeUtils.getChildTextNodeValue(iTunesSubtitleNode));

				// itunes:summary
				Node iTunesSummaryNode = this.getChildNodeByName(channelNode, "itunes:summary");
				if( iTunesSummaryNode != null ) podcast.getITunesInfo().setSummary(NodeUtils.getChildTextNodeValue(iTunesSummaryNode));

				// copyright
				Node copyrightNode = this.getChildNodeByName(channelNode, "copyright");
				if( copyrightNode != null ) podcast.setCopyright(NodeUtils.getChildTextNodeValue(copyrightNode));

				// itunes:author
				Node iTunesAuthorNode = this.getChildNodeByName(channelNode, "itunes:author");
				if( iTunesAuthorNode != null ) podcast.getITunesInfo().setAuthor(NodeUtils.getChildTextNodeValue(iTunesAuthorNode));

				// itunes:block
				Node iTunesBlockNode = this.getChildNodeByName(channelNode, "itunes:block");
				if( iTunesBlockNode != null ) {
					String value = NodeUtils.getChildTextNodeValue(iTunesBlockNode);
					Boolean bool = (value.equals("Yes"));
					podcast.getITunesInfo().setBlock(bool);
				}

				// itunes:category
				List<Node> categoryNodes = this.getChildNodesByName(channelNode, "itunes:category");
				if( categoryNodes.size() > 0 ) {
					List<Podcast.ITunesCategory> categories = new ArrayList<Podcast.ITunesCategory>();
					for( Node node : categoryNodes ) {
						Podcast.ITunesCategory category = podcast.new ITunesCategory();
						String name = this.getAttributeValue(node, "text");
						category.setName(name);

						Node subcategoryNode = this.getChildNodeByName(node, "itunes:category");
						if( subcategoryNode != null ) {
							Podcast.ITunesCategory subcategory = podcast.new ITunesCategory();
							name = this.getAttributeValue(subcategoryNode, "text");
							subcategory.setName(name);
							category.setSubCategory(subcategory);
						}

						categories.add(category);
					}
					podcast.getITunesInfo().setCategories(categories);
				}

				// itunes:complete
				Node iTunesCompleteNode = this.getChildNodeByName(channelNode, "itunes:complete");
				if( iTunesCompleteNode != null ) {
					String value = NodeUtils.getChildTextNodeValue(iTunesCompleteNode);
					Boolean bool = (value.equals("Yes"));
					podcast.getITunesInfo().setComplete(bool);
				}

				// itunes:explicit
				Node iTunesExplicitNode = this.getChildNodeByName(channelNode, "itunes:explicit");
				if( iTunesExplicitNode != null ) {
					String value = NodeUtils.getChildTextNodeValue(iTunesExplicitNode);
					Boolean bool = (value.equals("yes") || value.equals("explicit") || value.equals("true"));
					podcast.getITunesInfo().setExplicit(bool);
				}

				// itunes:image
				Node iTunesImageNode = this.getChildNodeByName(channelNode, "itunes:image");
				if( iTunesAuthorNode != null ) {
					String value = this.getAttributeValue(iTunesImageNode, "href");
					podcast.getITunesInfo().setImage(value);
				}

				// itunes:new-feed-url
				Node iTunesNewFeedUrlNode = this.getChildNodeByName(channelNode, "itunes:new-feed-url");
				if( iTunesNewFeedUrlNode != null ) podcast.getITunesInfo().setNewFeedUrl(NodeUtils.getChildTextNodeValue(iTunesNewFeedUrlNode));

				// itunes:owner
				Node iTunesOwnerNode = this.getChildNodeByName(channelNode, "itunes:owner");
				if( iTunesOwnerNode != null ) {
					Node iTunesOwnerNameNode = this.getChildNodeByName(iTunesOwnerNode, "itunes:name");
					if( iTunesOwnerNameNode != null ) podcast.getITunesInfo().getOwner().setName(NodeUtils.getChildTextNodeValue(iTunesOwnerNameNode));
					Node iTunesOwnerEmailNode = this.getChildNodeByName(iTunesOwnerNode, "itunes:email");
					if( iTunesOwnerEmailNode != null ) podcast.getITunesInfo().getOwner().setEmail(NodeUtils.getChildTextNodeValue(iTunesOwnerEmailNode));
				}

				// language
				Node languageNode = this.getChildNodeByName(channelNode, "language");
				if( languageNode != null ) podcast.setLanguage(NodeUtils.getChildTextNodeValue(languageNode));

				// link
				Node linkNode = this.getChildNodeByName(channelNode, "link");
				if( linkNode != null ) podcast.setLink(NodeUtils.getChildTextNodeValue(linkNode));

				List<Node> itemNodes = this.getChildNodesByName(channelNode, "item");
				for(Node itemNode : itemNodes) {
					PodcastEpisode podcastEpisode = PodcastEpisodeConvertUtils.toPodcastEpisode(itemNode);
					podcast.getEpisodes().add(podcastEpisode);
				}
			}
		}

		return podcast;
	}

	private String getAttributeValue(Node node, String attributeName) {
		NamedNodeMap nodeMap = node.getAttributes();
		return nodeMap.getNamedItem(attributeName).getNodeValue();
	}
	private Node getChildNodeByName(Node node, String nodeName) {
		NodeList childNodeList = node.getChildNodes();
		for(int i=0; i<childNodeList.getLength(); i++) {
			Node childNode = childNodeList.item(i);
			if( childNode.getNodeName().equals(nodeName) ) {
				return childNode;
			}
		}
		return null;
	}
	private List<Node> getChildNodesByName(Node node, String nodeName) {
		NodeList childNodeList = node.getChildNodes();
		List<Node> nodes = new ArrayList<Node>();
		for(int i=0; i<childNodeList.getLength(); i++) {
			Node childNode = childNodeList.item(i);
			if( childNode.getNodeName().equals(nodeName) ) {
				nodes.add(childNode);
			}
		}
		return nodes;
	}

	private Document getDocument(URL url) throws SAXException, IOException, ParserConfigurationException {
		String xmlString = this.getContents(url);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new ByteArrayInputStream(xmlString.getBytes()));
	}

	private String getContents(URL url) throws IOException {
		StringBuffer buffer = new StringBuffer();

		URLConnection connection = url.openConnection();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String line = null;
			while((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}
		}

		return buffer.toString().trim();
	}
}
