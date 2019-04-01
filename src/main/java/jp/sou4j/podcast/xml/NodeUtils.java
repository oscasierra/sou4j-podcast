package jp.sou4j.podcast.xml;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeUtils {

	public static String getChildTextNodeValue(Node node) {
		NodeList nodeList = node.getChildNodes();
		for(int i=0; i<nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);
			if( childNode.getNodeType() == Node.TEXT_NODE || childNode.getNodeType() == Node.CDATA_SECTION_NODE ) {
				return childNode.getNodeValue();
			}
		}
		return null;
	}
	public static String getAttributeValue(Node node, String attributeName) {
		NamedNodeMap nodeMap = node.getAttributes();
		Node namedItem = nodeMap.getNamedItem(attributeName);
		if( namedItem == null ) return null;
		return namedItem.getNodeValue();
	}

}
