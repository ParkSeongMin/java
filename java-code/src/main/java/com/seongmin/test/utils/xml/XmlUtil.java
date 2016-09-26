package com.seongmin.test.utils.xml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.seongmin.test.utils.DevUtil;
import com.seongmin.test.utils.cache.ThreadLocalMap;



public class XmlUtil {

	public static final String DEFAULT_CHARSET = "utf8"; 
	/**
	 * 전달된 ContentHandler를 가지고 xmlFileName의 xml 파일을 읽어서 처리.
	 * @param xmlFileName
	 * @param contentHandler
	 */
	public static void parse(String xmlFileName, ContentHandler contentHandler) throws ParserConfigurationException, SAXException, IOException {

		InputStream xmlInputStream = DevUtil.getResourceAsInputStream(xmlFileName);
		parse(xmlInputStream, contentHandler);
		if(xmlInputStream!=null) { try { xmlInputStream.close(); } catch(IOException e) {} }
	}

	
	/**
	 * 전달된 ContentHandler를 가지고 xmlFileName의 xml 파일을 읽어서 처리.
	 * @param xmlInputStream
	 * @param contentHandler
	 */
	public static void parse(InputStream xmlInputStream, ContentHandler contentHandler) throws ParserConfigurationException, SAXException, IOException {

//		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance("com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl", XmlUtil.class.getClassLoader());
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();

		XMLReader xmlReader = saxParser.getXMLReader();

		xmlReader.setContentHandler(contentHandler);
		xmlReader.parse(new InputSource(xmlInputStream));
	}
	

	/**
	 * xmlFileName의 xml 파일을 읽어서 Document를 반환한다.
	 * 내부적으로는 parseAndGetDocument(xmlFileName, false)를 호출한다.
	 * 
	 * @param xmlFileName
	 * @return
	 */
	public static Document parseAndGetDocument(String xmlFileName) throws XmlParseException {
		boolean shouldValidate = false;
		try {
			return parseAndGetDocument(xmlFileName, shouldValidate);
		} catch (IOException e) {
			throw new XmlParseException("xml parsing failed. file="+xmlFileName, e);
		} catch (XmlParseException e) {
			throw new XmlParseException("xml parsing failed. file="+xmlFileName, e);
		}
	}
	
	/**
	 * xmlFileName의 xml 파일을 읽어서 Document를 반환한다.
	 * 
	 * @param xmlFileName
	 * @param shouldValidate document 생성시 validation을 할지 여부.
	 * @return
	 * @throws XmlParsingException 
	 */
	public static Document parseAndGetDocument(String xmlFileName, boolean shouldValidate) throws IOException, XmlParseException {
		
		StringBuffer stringBuffer = new StringBuffer();
		String str;
		Reader reader = null;
		BufferedReader bufferedReader = null;
		
		try {
			reader = new InputStreamReader(new FileInputStream(xmlFileName), DEFAULT_CHARSET);
			bufferedReader = new BufferedReader(reader);
			while ((str = bufferedReader.readLine()) != null) {
				stringBuffer.append(str).append("\n");
			}
		} finally {
			if(bufferedReader != null) {
				bufferedReader.close();
			}
			if(reader != null) {
				reader.close();
			}
			
		}
		
		return transformString2Document(stringBuffer.toString());
		
	}

    
	/**
	 * 모든 자식 노드를 배열로 반환한다.
	 */
	public static Node[] getChildNodes(Node sourceNode) {
		
		NodeList nodeList = sourceNode.getChildNodes();

		Node[] nodes = new Node[nodeList.getLength()];
		for(int i=0; i<nodeList.getLength(); i++) {
			nodes[i] = nodeList.item(i);
		}
		return nodes;

	}
	
	
	/**
	 * targetNodeName을 가진 자식 node를 배열로 반환한다.
	 */
	public static Node[] getChildNodes(Node sourceNode, String nodeName) {
		
		List<Node> selectedNodeList = new ArrayList<Node>();
		
		if(nodeName==null) { return null; }
		if(sourceNode==null) { return null; }
		NamedNodeMap attributeMap = sourceNode.getAttributes();
		if(attributeMap!=null) {
			Node attribute = attributeMap.getNamedItem(nodeName);
			if(attribute!=null) { selectedNodeList.add(attribute); }
		}
		
		NodeList nodeList = sourceNode.getChildNodes();
		for(int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			String currentNodeName = node.getNodeName();
			if(nodeName.equalsIgnoreCase(currentNodeName)) {
				selectedNodeList.add(node);
			}
		}
		
		Node[] nodes = new Node[selectedNodeList.size()];
		nodes = selectedNodeList.toArray(nodes);
		return nodes;
		
	}
	
	
	/**
	 * targetNodeName을 가진 첫번째 자식 node를 반환한다.
	 */
	public static Node getChildNode(Node sourceNode, String targetNodeName) {
		if(targetNodeName==null) { return null; }
		if(sourceNode==null) { return null; }
		
		Node[] nodes = getChildNodes(sourceNode, targetNodeName);
		if(nodes==null) { return null; }
		if(nodes.length==0) { return null; }
		return nodes[0];
	}

	/**
	 * 전달되는 노드의 속성을 Attr 객체로 반환한다.
	 * 
	 * @param sourceNode
	 * @param attributeName
	 * @return
	 */
	public static Attr getAttribute(Node sourceNode, String attributeName) {

		if(sourceNode==null) { return null; }
		if(attributeName==null) { return null; }
		
		NamedNodeMap namedNodeMap = sourceNode.getAttributes();
		if(namedNodeMap==null) { return null; }

		Node attributeNode = namedNodeMap.getNamedItem(attributeName);
		if(attributeNode==null) { return null; }
		
		return (Attr)attributeNode;

	}
	
	/**
	 * 특정 노드의 특정 속성의 값을 전달된 값으로 설정한다.
	 * @param sourceNode
	 * @param attributeName
	 * @param attributeValue
	 */
	public static void setAttributeValue(Node sourceNode, String attributeName, String attributeValue) {
		Attr attribute = getAttribute(sourceNode, attributeName);
		if(attribute==null) { return; }
		attribute.setNodeValue(attributeValue);
	}
	
	/**
	 * attributeName의 어트리뷰트 값을 반환한다.
	 */
	public static String getAttributeValue(Node sourceNode, String attributeName) {
		
		Attr attribute = getAttribute(sourceNode, attributeName);
		if(attribute==null) { return null; }
		String attributeValue = attribute.getNodeValue();
		return attributeValue;
		
	}
	
	/**
	 * sourceNode 밑의 nodeName 이름의 모든 노드의 텍스트 값들을 배열로 반환한다.
	 * 
	 * @param sourceNode
	 * @param nodeName
	 * @return
	 */
	public static String[] getTextsOfChildNode(Node sourceNode, String nodeName) {
		if(sourceNode==null) { return null; }
		if(nodeName==null) { return null; }
		Node[] nodes = getChildNodes(sourceNode, nodeName);
		if(nodes==null) { return null; }
		String[] texts = new String[nodes.length];
		for(int i=0; i<nodes.length; i++) {
			String text = getText(nodes[i]);
//			if(text==null) text = "";
			texts[i] = text;
		}
		return texts;
	}
	
	/**
	 * sourceNode 밑의 nodeName 이름의 첫번째 노드의 텍스트 값을 반환한다.
	 * 만약 해당 이름의 노드가 없으면 null을 반환한다.
	 * @param sourceNode
	 * @param nodeName
	 * @return
	 */
	public static String getTextOfChildNode(Node sourceNode, String nodeName) {
		if(sourceNode==null) { return null; }
		if(nodeName==null) { return null; }
		String[] texts = getTextsOfChildNode(sourceNode, nodeName);
		if(texts==null) { return null; }
		if(texts.length==0) { return null; }
		return texts[0];
	}
	
	/**
	 * 특정 노드의 텍스트 값을 반환한다.
	 * 값이 없으면 null을 반환한다.
	 * @param sourceNode
	 * @return
	 */
	public static String getText(Node sourceNode) {
		if(sourceNode==null) { return null; }
		NodeList nodeList = sourceNode.getChildNodes();
		for(int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			int nodeType = node.getNodeType();
			if(nodeType==Element.TEXT_NODE || nodeType==Element.CDATA_SECTION_NODE) {
				// java 1.4의 org.w3c.dom.Node에는 getTextContent()메소드가 없다. 
				String text = ((com.sun.org.apache.xerces.internal.dom.NodeImpl)node).getTextContent();
				return text;
			}
		}
		return null;
	}
	

	

	/**
	 * Node 객체로 표현된 xml을 String으로 변환하여 반환한다.
	 * 
	 * @param node
	 * @param encoding
	 * @return
	 * @throws TransformerException
	 */
	public static String transformDOM2String(Node node) throws TransformerException {

		String encoding = DEFAULT_CHARSET;
		return transformDOM2String(node, encoding);
		
	}
	

	/**
	 * Node 객체로 표현된 xml을 String으로 변환하여 반환한다.
	 * 
	 * @param node
	 * @param encoding
	 * @return
	 * @throws TransformerException
	 */
	public static String transformDOM2String(Node node, String encoding) throws TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(); 
        if (encoding != null) {
        	transformer.setOutputProperty("encoding", encoding);
        }
        
		DOMSource source = new DOMSource(node);
		Writer stringWriter = new StringWriter();
		StreamResult result = new StreamResult(stringWriter);

        transformer.transform(source, result); 
		String nodeString = stringWriter.toString();
		nodeString = nodeString.replaceAll("&#13;", "\r");
        return nodeString;
		
	}

	/**
	 * String으로 표현된 xml을 Document로 반환한다.
	 * @param xmlString
	 * @return
	 * @throws XmlParsingException
	 */
	public static Document transformString2Document(String xmlString) throws XmlParseException {
		String charset = DEFAULT_CHARSET;
		return transformString2Document(xmlString, charset);
	}
	
	/**
	 * String으로 표현된 xml을 Document로 반환하다.
	 * @param xmlString
	 * @param charset xml 스트링을 파싱할 때 사용할 charset
	 * @return
	 * @throws XmlParsingException
	 */
	public static Document transformString2Document(String xmlString, String charset) throws XmlParseException {
		
		Document doc = null;
		DocumentBuilder builder = null;
		InputSource inputSource = null;

		try {
			
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance("com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl", XmlUtil.class.getClassLoader());
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
		    // CDATA를 TEXT 노드로 처리.
		    factory.setCoalescing(true);
		    factory.setIgnoringElementContentWhitespace(true);
		    factory.setIgnoringComments(true);
		    factory.setNamespaceAware(true);
		    builder = factory.newDocumentBuilder();
			if(xmlString==null) {
			   return builder.newDocument();
			}
			
			
			inputSource = new InputSource(new ByteArrayInputStream(xmlString.getBytes(charset)));
			
			if(charset != null) {
				inputSource.setEncoding(charset);
			}
			
			doc = builder.parse(inputSource);
			
		} catch (ParserConfigurationException e) {
			throw new XmlParseException("xml parsing fail.",e);
		} catch (SAXException e) {
			throw new XmlParseException("xml parsing fail.",e);
		} catch (IOException e) {
			throw new XmlParseException("xml parsing fail.",e);
		}
		
		return doc;
		
	}

	/**
	 * xpath를 가지고 node를 찾은 후, 그 노드의 값을 반환한다.
	 * 전달되는 node는 xpath를 가지고 노드를 찾기 위해 사용된다. 단순히 찾을 노드를 포함하는 xml의 임의의 노드이다. 
	 * @param xmlNode 찾을 노드를 포함하는 xml의 임의의 노드
	 * @param xpathString
	 * @return
	 * @throws XPathExpressionException
	 */
	public static String findNodeWithXpathAndGetNodeValue(Node xmlNode, String xpathString) throws XPathExpressionException {
	
		Node node = findNodeWithXpath(xmlNode, xpathString);
		if(node==null) { return null; }
		return getNodeValue(node);
		
	}
	
	/**
	 * node의 값을 반환한다.
	 * 3가지 경우만 반환할 수 있다. Node의 티입이 속성인경우, 첫번째 노드가 TEXT나 CDATA 타입인 경우.
	 * 이외는 null을 반환한다.
	 * 
	 * @param node
	 * @return
	 */
	public static String getNodeValue(Node node) {

		if(node==null) { return null; }

		if(node.getNodeType()==Node.ATTRIBUTE_NODE) {
			return node.getNodeValue();
		}
		
		String value = null;
		NodeList childNodeList =  node.getChildNodes();
		Node valueNode;
		if(childNodeList!=null) {
			if(childNodeList.getLength()==1) {
				valueNode = childNodeList.item(0);
				if(valueNode.getNodeType()==Node.TEXT_NODE) {
					value = valueNode.getNodeValue();
				}
				else if(valueNode.getNodeType()==Node.CDATA_SECTION_NODE) {
					value = valueNode.getNodeValue();
				}
			}
		}
		
		return value;
		
	}
	
	
	
	
	/**
	 * xmlNode가 포함된 xml에서 xpathString에 해당하는 Node를 반환한다.
	 * 만약 노드가 복수라면 첫번 째 것을 반환한다.
	 * @param xmlNode 찾을 노드를 포함하는 xml의 임의의 노드
	 * @param xpathString
	 * @return
	 * @throws XPathExpressionException
	 */
	public static Node findNodeWithXpath(Node xmlNode, String xpathString) throws XPathExpressionException {
		Node[] nodes = findNodesWithXpath(xmlNode, xpathString);
		if(nodes==null) { return null; }
		if(nodes.length==0) { return null; }
		return nodes[0];
	}
	
	
	/**
	 * xpath를 가지고 노드들을 찾는다.
	 * 이렇게 따로 메소드로 뽑아 놓은 이유는 name space에 해당하는 prefix가 xpath에 포함되어 있을때
	 * 호출하는 처리가 약간 복잡하기 때문이다.
	 * 따라서 전달되는 xpath는 전달되는 node가 아닌 root node를 기준으로 한 값이어야 한다.
	 * @param node
	 * @param xpath
	 * @return
	 * @throws XPathExpressionException 
	 * @throws TransformerException
	 */
	private static Map<String, Node[]> xpath2NodesCache = new ThreadLocalMap<String, Node[]>();
	public static Node[] findNodesWithXpath(Node node, String xpathString) throws XPathExpressionException {

		String cacheKey = null;
		Node[] foundNodes = null;

		if(node.getOwnerDocument()!=null) {
			// 희안하게도 owner document가 null인 경우가 있다.
			// SerializedXmlConverterTest에서 실패한다.
			cacheKey = node.getOwnerDocument().hashCode()+"-"+xpathString;
			foundNodes = xpath2NodesCache.get(cacheKey);
			if(foundNodes!=null) { return foundNodes; }
		}
		
		// anonymous name space의 경우 동작 불가하다.
		// xpath를 변형해 주자.
		xpathString = getLocalizedXpathString(xpathString);
		NodeList nodeList;

		// code reference : http://www.edankert.com/defaultnamespaces.html
		XPathFactory factory = XPathFactory.newInstance();
		
		XPath xpath = factory.newXPath();
		
		NamespaceContext nameSpaceContext = getNameSpaceContext(node);
		xpath.setNamespaceContext(nameSpaceContext);

		XPathExpression expr = xpath.compile(xpathString);
		nodeList = (NodeList)expr.evaluate(node, XPathConstants.NODESET);

		foundNodes = new Node[nodeList.getLength()];
		for(int i=0; i<nodeList.getLength(); i++) {
			foundNodes[i] = nodeList.item(i);
		}
	
		if(cacheKey!=null) {
			xpath2NodesCache.put(cacheKey, foundNodes);
		}
		
		return foundNodes;
		
	}
	
	/**
	 * 전달된 xpath에 해당하는 노드들의 자식 노드들을 반환한다.
	 * 
	 * @param node
	 * @param xpathString
	 * @return
	 * @throws XPathExpressionException
	 */
	public static Node[] getChildNodesOfNodeOfXpath(Node node, String xpathString) throws XPathExpressionException {
		
		Node[] xpathNodes = findNodesWithXpath(node, xpathString);
		
		List<Node> childNodeList = new ArrayList<Node>();
		
		Node[] childNodes;
		for(int i=0; i<xpathNodes.length; i++) {
			childNodes = getChildNodes(xpathNodes[i]);
			for(int j=0; j<childNodes.length; j++) {
				childNodeList.add(childNodes[j]);
			}
		}
		
		childNodes = new Node[childNodeList.size()];
		
		return childNodeList.toArray(childNodes);
		
	}
	
	/**
	 * xpath를 가지고 노드를 찾고, 그 중에서 전달된 node와 거리가 가장 가까운 노드를 반환한다.
	 * @param node
	 * @param xpathString
	 * @return
	 * @throws XPathExpressionException
	 */
	public static Node findMostCloseNodeWithXpath(Node node, String xpathString) throws XPathExpressionException {
		
		Node[] nodes = findNodesWithXpath(node, xpathString);
		
		if(nodes==null) { return null; }
		if(nodes.length==0) { return null; }
		int[] distances = new int[nodes.length];
		int minDistanceIndex = nodes.length;
		int minDistance = Integer.MAX_VALUE;
		for(int i=0; i<nodes.length; i++) {
			distances[i] = getRelationDistance(node, nodes[i]);
			if(distances[i]<minDistance) {
				minDistance = distances[i];
				minDistanceIndex = i;
			}
		}
		
		return nodes[minDistanceIndex];
	}
	
	/**
	 * xpath를 가지고 노드를 찾고, 그 중에서 전달된 node와 거리가 가장 가까운 노드를 반환한다.
	 * @param node
	 * @param xpathString
	 * @return
	 * @throws XPathExpressionException
	 */
	public static Node[] findMostCloseNodesWithXpath(Node node, String xpathString) throws XPathExpressionException {
		
		Node[] nodes = findNodesWithXpath(node, xpathString);
		
		if(nodes==null) { return null; }
		if(nodes.length==0) { return null; }
		int[] distances = new int[nodes.length];
		int minDistanceIndex = nodes.length;
		int minDistance = Integer.MAX_VALUE;
		for(int i=0; i<nodes.length; i++) {
			distances[i] = getRelationDistance(node, nodes[i]);
			if(distances[i]<minDistance) {
				minDistance = distances[i];
				minDistanceIndex = i;
			}
		}
		
		List<Node> candidateNodeList = new ArrayList<Node>();
		
		for(int i=0; i<nodes.length; i++) {
			
			if(distances[i] == distances[minDistanceIndex]) {
				candidateNodeList.add(nodes[i]);
			}
		}
		
		if(candidateNodeList.size() == 0) { return null; } 
		
		return (Node[])candidateNodeList.toArray(new Node[candidateNodeList.size()]);
		
	}

	/**
	 * 두개의 노드 사이의 거리를 반환한다.
	 * 노드의 거리는 한번의 아버지<->자식관계가 1이다.
	 * 형제 간의 거리는 2가 된다.
	 * @param firstNode
	 * @param secondNode
	 * @return
	 */
	public static int getRelationDistance(Node firstNode, Node secondNode) {
		Node[] firstNodeParents = getAllParentNodes(firstNode);
		Node[] secondNodeParents = getAllParentNodes(secondNode);
		int branchingNodeIndex = -1;
		for(int i=0; i<firstNodeParents.length&&i<secondNodeParents.length; i++) {
			branchingNodeIndex = i;
			if(firstNodeParents[i]!=secondNodeParents[i]) {
				break;
			}
		}
		
/*
 * 다음과 같이 <d>와 <c2>사이의 거리는 distance(<b>, <d>) + distance(<b>, <c2>) = 2 + 1 = 3이다.
<a>
	<b id="id1">
		<c1>
			<d>HI0</d>
			<d>HI1</d>
			<d>HI2</d>
		</c1>
		<c2 id="ID">hi1</c2>
	</b>
	위에서 구한 branchingNodeIndex는 <b>에 해당하는 1(2번째 )이다.
			
 */
	
		// 위의 예의 경우 (4 - 1 - 1) + (3 - 1- 1) 
		int relationDistance 
			= (firstNodeParents.length - branchingNodeIndex - 1)
			+ (secondNodeParents.length - branchingNodeIndex - 1);
		return relationDistance;
		
	}
	
	/**
	 * root 노드부터 시작해서 자신의 노드까지 차례로 담은 Node 배열을 반환한다.
	 * @param node
	 * @return
	 */
	private static Node[] getAllParentNodes(Node node) {
		if(node==null) { return new Node[0]; }
		
		List<Node> parentNodeList = new ArrayList<Node>();
		parentNodeList.add(node);
		
		addParentToList(parentNodeList, node);
		Node[] nodes = new Node[parentNodeList.size()];
		Collections.reverse(parentNodeList);
		return parentNodeList.toArray(nodes);
	}

	private static void addParentToList(List<Node> parentNodeList, Node node) {
		Node parentNode = node.getParentNode();
		if(parentNode==null) { return; }
		parentNodeList.add(parentNode);
		addParentToList(parentNodeList, parentNode);
		
	}
	

	private static Map<Node, GeneralNameSpaceContext> nameSpaceContextCache = new ThreadLocalMap<Node, GeneralNameSpaceContext>();
	
	private static NamespaceContext getNameSpaceContext(Node node) {

		if(node==null) { return new GeneralNameSpaceContext(); }

		Document document;
		if(node.getNodeType()==Node.DOCUMENT_NODE) {
			document = (Document) node;
		}
		else {
			document = node.getOwnerDocument();
		}

		Node rootNode = document.getFirstChild();
		
		GeneralNameSpaceContext nameSpaceContext = null;
		nameSpaceContext = nameSpaceContextCache.get(rootNode);
		if(nameSpaceContext!=null) { return nameSpaceContext; }

		nameSpaceContext = new GeneralNameSpaceContext();

		
		collectNameSpace(nameSpaceContext, rootNode);
		
		nameSpaceContextCache.put(rootNode, nameSpaceContext);
		
		return nameSpaceContext;
	}

	private static void collectNameSpace(GeneralNameSpaceContext nameSpaceContext, Node node) {

		NamedNodeMap attributeMap = node.getAttributes();
		if(attributeMap!=null) {
			Node attribute;
			String attributeName;
			String prefix;
			String nameSpaceUrl;
			for(int i=0; i<attributeMap.getLength(); i++) {
				attribute = attributeMap.item(i);
				attributeName = attribute.getNodeName();
				if(attributeName.startsWith("xmlns") || attributeName.startsWith("xml")) {
					nameSpaceUrl = attribute.getNodeValue();
					if(attributeName.indexOf(":")>0) {
						prefix = attributeName.substring(attributeName.indexOf(":")+1);
						nameSpaceContext.add(prefix, nameSpaceUrl);
					}
					else if(attributeName.equals("xmlns") || attributeName.equals("xml")) {
						nameSpaceContext.setDefaultNameSpaceUrl(nameSpaceUrl);
					}
				}
			}
		}
		
		NodeList childList = node.getChildNodes();
		Node child;
		for(int i=0; i<childList.getLength(); i++) {
			child = childList.item(i);
			if(child.getNodeType()==Node.ELEMENT_NODE) {
				collectNameSpace(nameSpaceContext, child);
			}
		}
	}
	
	/*
	 * 다음과 같이 anonymous name space를 사용하는 경우 
	 * "/root/next/another"와 같은 일반적인 xpath로 조회가 불가능하다.
	 * 무식하지만 하려고 하려면 "/root/next/*[local-name()='another']"와 같이 
	 * name space를 무시한 local-name() 함수를 사용하여 조회하여야 한다.
		<?xml version="1.0" encoding="utf-8"?>
		<root xmlns="http://w3c.org">
		    <next>
		        <another xmlns="http://some.com">
		            <other/>
		            <other/>
		            <other/>
		            ....
		        </another>
		    </next>
		</root>
	 * 모든 부분을 전부 local-name()을 사용하지는 않고 다음과 같이 prefix가 명시되지 않은 부분만 변경한다.
	 * 	"/a/b/c" -> "/*[local-name()='a']/*[local-name()='b']/*[local-name()='c']"
	 * 	"/p:a/b/c" -> "/p:a/*[local-name()='b']/*[local-name()='c']"
	 * 	"/p:a/p:b/c" -> "/p:a/p:b/*[local-name()='c']"
	 */
	// TODO : caching 하자.
	// TODO : xpath내에 이미 함수가 사용되는 경우는 간단치 않다. 그런 경우도 처리하자.
	private static Map<String, String> localizedXpathCache = new ThreadLocalMap<String, String>();
	private static String getLocalizedXpathString(String xpathString) {
		
		if(xpathString==null) { return null; }
		String localizedXpath = localizedXpathCache.get(xpathString);
		if(localizedXpath!=null) { return localizedXpath; }
		
		String[] xpathlets = xpathString.split("/");
		if(xpathlets.length==1) { return null; }
		StringBuffer newXpathSb = new StringBuffer();
		for(int i=1; i<xpathlets.length; i++) {
			
			newXpathSb.append("/");
			
			if(xpathlets[i].indexOf(":")>=0 && xpathlets[i].indexOf("@")<0) {
				// prefix가 있는 경우.
				newXpathSb.append(xpathlets[i]);
			}
			else if(xpathlets[i].indexOf("(")>=0) {
				// 함수가 사용된 경우이다 변경하지 말자.
				newXpathSb.append(xpathlets[i]);
			}
			else if(xpathlets[i].indexOf("[")>=0) {
				// 역시  변경하지 말자.
				newXpathSb.append(xpathlets[i]);
			}
			else if(xpathlets[i].indexOf("*")>=0) {
				// 역시  변경하지 말자.
				newXpathSb.append(xpathlets[i]);
			}
			else if(xpathlets[i].indexOf("'")>=0) {
				// 역시  변경하지 말자.
				newXpathSb.append(xpathlets[i]);
			}
			else {
				// prefix가 없는 경우
				
				if(xpathlets[i].startsWith("@")) {
					newXpathSb.append("@*[local-name()='").append(xpathlets[i].substring(1)).append("']");
				}
				else if(xpathlets[i].indexOf("@")>0) {
					// /name@id와 같이 @가 /로 구분되지 않은 경우이다.
					String elementName = xpathlets[i].substring(0, xpathlets[i].indexOf("@"));
					String attributeName = xpathlets[i].substring(xpathlets[i].indexOf("@")+1);
					newXpathSb.append("*[local-name()='").append(elementName).append("']");
					newXpathSb.append("/");
					
					if(attributeName.indexOf(":")<0 )
					{
						newXpathSb.append("@*[local-name()='").append(attributeName).append("']");
					}
					else
					{
						newXpathSb.append("@"+attributeName);
					}
					
				}
				else {
					// attribute가 아닌 경우
					newXpathSb.append("*[local-name()='").append(xpathlets[i]).append("']");
				}
			}
		}
		localizedXpath = newXpathSb.toString();
		localizedXpathCache.put(xpathString, localizedXpath);
		return localizedXpath;
		
	}
	
	
	/**
	 * XpathString 에서 말단 node 명을 리턴 하는 메서드
	 * 
	 * ex> 다음과 같은 xpath 일 경우 "/root/next/another"
	 * 'another'를 반환한다.
	 * 
	 * @param xpathString
	 * @return
	 */
	public static String getLocalizedNameString(String xpathString) {
		
		if(xpathString == null) { return null; }
		
		if (xpathString.lastIndexOf("/") != -1) {
			xpathString = xpathString.substring(xpathString.lastIndexOf("/") + 1);
		}
		if (xpathString.lastIndexOf("@") != -1) {
			xpathString = xpathString.substring(xpathString.lastIndexOf("@") + 1);
		}
		if(xpathString.lastIndexOf(")") != -1) {  // 함수가 사용된 경우이다. 함수 제거 한다.
			xpathString = xpathString.substring(xpathString.lastIndexOf(")") + 1);
		}
		if(xpathString.lastIndexOf("]") != -1) {
			xpathString = xpathString.substring(xpathString.lastIndexOf("]") + 1);
		}
		if(xpathString.lastIndexOf("*") != -1) {
			xpathString = xpathString.substring(xpathString.lastIndexOf("*") + 1);
		}
		if(xpathString.lastIndexOf("'") != -1) {
			xpathString = xpathString.substring(xpathString.lastIndexOf("'") + 1);
		}
		
		return xpathString;

	}
	
	
	
	
	static class GeneralNameSpaceContext implements NamespaceContext {

		private List<Pair> pairList;
		String defaultNameSpaceUrl = null;
		
		// namespace 예 : "http://www.w3.org/2000/xmlns/"
		// prefix 예 : "xmlns"
		
		public GeneralNameSpaceContext() {
			init();
		}

		public void setDefaultNameSpaceUrl(String defaultNameSpaceUrl) {
			this.defaultNameSpaceUrl = defaultNameSpaceUrl;
			// TODO : implement setDefaultNameSpaceUrl()
			
		}

		private void init() {
			pairList = new ArrayList<Pair>();
			pairList.add(new Pair("xml", "http://www.w3.org/XML/1998/namespace"));
			pairList.add(new Pair("xmlns", "http://www.w3.org/2000/xmlns/"));
		}
		
		public void add(String prefix, String nameSpaceUrl) {
			
			for(Pair pair : pairList) {
				if(pair.prefix.equals(prefix) && pair.nameSpaceUrl.equals(nameSpaceUrl)) {
					return;
				}
			}
			
			pairList.add(new Pair(prefix, nameSpaceUrl));
		}
			
		public String getNamespaceURI(String prefix) {
			if(prefix==null||prefix.equals("")) {
				return defaultNameSpaceUrl;
			}
			
			for(Pair pair : pairList) {
				if(pair.prefix.equals(prefix)) {
					return pair.nameSpaceUrl;
				}
			}
			return null;
		}

		public String getPrefix(String nameSpaceUrl) {
			for(Pair pair : pairList) {
				if(pair.nameSpaceUrl.equals(nameSpaceUrl)) {
					return pair.prefix;
				}
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		public Iterator getPrefixes(String nameSpaceUrl) {

	        final String result = getPrefix(nameSpaceUrl);
            
	        return new Iterator() {

	        	boolean isFirstIteration = (result != null);
	        	
	            public boolean hasNext()
	            {
	                return isFirstIteration;
	            }

	            public Object next()
	            {
	                if(isFirstIteration)
	                {
	                    isFirstIteration = false;
	                    return result;
	                } else
	                {
	                    return null;
	                }
	            }

	            public void remove()
	            {
	                throw new UnsupportedOperationException();
	            }


	        };
		}


	}
	
	/**
	 * oldNode가 youngNode의 부모혹은 할아버지인지 확인한다.
	 * 
	 * @param youngNode 젊은 노드
	 * @param oldNode 나이많은 할아버지 노드.
	 * @return
	 */
	private static Map<String, Boolean> isParentCache = new ThreadLocalMap<String, Boolean>();
	public static boolean isParentOf(Node youngNode, Node oldNode) {
		if(youngNode==null) { return false; }
		if(oldNode==null) { return false; }
		String cacheKey = youngNode.hashCode()+"-"+oldNode.hashCode();
		Boolean isParent = isParentCache.get(cacheKey);
		if(isParent!=null) {
			return isParent; 
		}
		
		if(youngNode==oldNode) {
			isParent = true;
			isParentCache.put(cacheKey, isParent);
			return isParent;
		}
		
		isParent = isParentOf(youngNode.getParentNode(), oldNode);
		isParentCache.put(cacheKey, isParent);
		return isParent;
	}
	
	static class Pair {
		String prefix;
		String nameSpaceUrl;
		public Pair(String prefix, String nameSpaceUrl) {
			this.prefix = prefix;
			this.nameSpaceUrl = nameSpaceUrl;
		}
	}

	/**
	 * 전달되는 node와 그 하부의 모든 노드의 xpath를 담은 Set<String>을 반환한다.
	 * @param node
	 * @return
	 */
	public static Set<String> getAllSubNodeXpathSet(Node node) {
		String parentXpath = null;
		return getAllSubNodeXpathSet(node, parentXpath);
	}
	
	/**
	 * 전달되는 node와 그 하부의 모든 노드의 xpath를 담은 Set<String>을 반환한다.
	 * 전달되는 parentXpath는 Set에 담기는 모든 xpath앞에 붙는다. 
	 * @param node
	 * @param parentXpath
	 * @return
	 */
	public static Set<String> getAllSubNodeXpathSet(Node node, String parentXpath) {
		
		Set<String> xpathSet = new HashSet<String>();
		
		if(node.getNodeType()!=Node.ELEMENT_NODE) { return xpathSet; }
		String path = node.getNodeName();
		if(path.equalsIgnoreCase("xmlns") || path.startsWith("xmlns:")) { return xpathSet; }
		if(path.equalsIgnoreCase("xml") || path.startsWith("xmlns:")) { return xpathSet; }
		
		
		if(parentXpath==null) {
			parentXpath = "";
		}

		String fullXpath = parentXpath+"/"+path;

		NamedNodeMap attributeMap = node.getAttributes();
		Node attributeNode;
		String attributeXpath;
		for(int i=0; i<attributeMap.getLength(); i++) {
			attributeNode = attributeMap.item(i);
			String attributeName = attributeNode.getNodeName();
			if(attributeName.equals("xmlns") || attributeName.startsWith("xmlns:")) { continue; }
			if(attributeName.equals("xml") || attributeName.startsWith("xmlns:")) { continue; }
			attributeXpath = fullXpath+"/@"+attributeNode.getNodeName();
			xpathSet.add(attributeXpath);
		}


		NodeList childNodeList = node.getChildNodes();

		Node childNode = null;
		for(int i=0; i<childNodeList.getLength(); i++) {
			childNode = childNodeList.item(i);
			if(childNode.getNodeType()==Node.ELEMENT_NODE) {
				xpathSet.addAll(getAllSubNodeXpathSet(childNode, fullXpath));
			}
		}
		
		return xpathSet;

	}
	
	/** 
	 * xml String을 가지고 Node를 만들어 반환한다.
	 * 구체적으로 Document 객체를 만들고, 그의 첫번째 자식 노드를 반환한다.
	 * @param xmlString
	 * @return
	 * @throws ParsingFailException
	 */
	public static Node buildXmlNode(String xmlString) throws XmlParseException {
	
		try {
			Document document = transformString2Document(xmlString);
			Node rootNode = document.getFirstChild();
			if(rootNode==null) {
				throw new XmlParseException("null xml document");
			}
			return rootNode;
		} catch (XmlParseException e) {
			throw new XmlParseException("xml parsing failed.", e);
		}
	
	}

	/**
	 * columnXpath의 노드들을 찾고, 이 중 rowNode의 줄기에 있는 것을 반환한다.
	 * 
	 * @param rowNode 
	 * @param xpathString full xpath as like "/aaa/bbb/ccc"
	 * @throws XPathExpressionException 
	 */
	public static Node findNodeWithXpathInSameBranch(Node targetNode, String xpathString) throws XPathExpressionException {
		
		
		if(targetNode==null) { return null; }
		if(xpathString==null) { return null; }
		
		Node[] nodes = findNodesWithXpath(targetNode, xpathString);
	
		if(nodes.length==0) {
			return null;
		}
		else {
			// 여러개 있다면 targetNode와 비교해서 같은 줄기안에 있는 것을 찾아야 한다.
			// 다음과 같이 b.id는 여러개 이다. 현재 <c>의 줄기에 있는 것인지 확인하려면
			// id의 parent 즉 <b>가 <c>의 parent인지 확인해야 한다.
			// candidateNode는 b.id 이고 parentCandidateNode는 <b>가 된다.
			// <a>
			//   <b id="">
			//     <c>
			//   <b id="">
			//     <c>
			Node parentCandidateNode;
			for(Node candidateNode : nodes) {
				if(isInSameBranch(targetNode, candidateNode)) {
					return candidateNode;
				}

			}
			
			return null;
		}
		
	}
	
	public static Node[] findNodesWithXpathInSameBranch(Node targetNode, String xpathString) throws XPathExpressionException {
		
		
		if(targetNode==null) { return null; }
		if(xpathString==null) { return null; }
		
		Node[] nodes = findNodesWithXpath(targetNode, xpathString);
	
		if(nodes.length==0) {
			return null;
		}
		else {
			// 여러개 있다면 targetNode와 비교해서 같은 줄기안에 있는 것을 찾아야 한다.
			// 다음과 같이 b.id는 여러개 이다. 현재 <c>의 줄기에 있는 것인지 확인하려면
			// id의 parent 즉 <b>가 <c>의 parent인지 확인해야 한다.
			// candidateNode는 b.id 이고 parentCandidateNode는 <b>가 된다.
			// <a>
			//   <b id="">
			//     <c>
			//   <b id="">
			//     <c>
			List<Node> candidateNodeList = new ArrayList<Node>();
			for(Node candidateNode : nodes) {
				if(isInSameBranch(targetNode, candidateNode)) {
					candidateNodeList.add(candidateNode);
				}
			}
			
			if(candidateNodeList.size() == 0) { return null; } 
				
			return (Node[])candidateNodeList.toArray(new Node[candidateNodeList.size()]);
		}
		
	}
	
	
	
	private final static Map<String, Boolean> isSameBranchCache = new ThreadLocalMap<String, Boolean>();
	public static boolean isInSameBranch(Node firstNode, Node secondNode) {
		
		if(firstNode==null) { return false; }
		if(secondNode==null) { return false; }
		if(firstNode==secondNode) { return true; }
		
		Boolean isSameBranch = null;
		isSameBranch = isSameBranchCache.get(firstNode.hashCode()+"-"+secondNode.hashCode());
		if(isSameBranch!=null) { return isSameBranch; }
		isSameBranch = isSameBranchCache.get(secondNode.hashCode()+"-"+firstNode.hashCode());
		if(isSameBranch!=null) { return isSameBranch; }

		
		if(firstNode.getNodeType()==Node.ATTRIBUTE_NODE) {
			firstNode = ((Attr)firstNode).getOwnerElement();
		}
		if(secondNode.getNodeType()==Node.ATTRIBUTE_NODE) {
			secondNode = ((Attr)secondNode).getOwnerElement();
		}
		
		String firstNodeXpath = getFullXpath(firstNode);
		String secondNodeXpath = getFullXpath(secondNode);
		if(!firstNodeXpath.startsWith(secondNodeXpath)&&!secondNodeXpath.startsWith(firstNodeXpath)) {
			isSameBranchCache.put(firstNode.hashCode()+"-"+secondNode.hashCode(), new Boolean(false));
			isSameBranchCache.put(secondNode.hashCode()+"-"+firstNode.hashCode(), new Boolean(false));
			return false;
		}
		
		if(isParentOf(firstNode, secondNode)) {
			isSameBranchCache.put(firstNode.hashCode()+"-"+secondNode.hashCode(), new Boolean(true));
			isSameBranchCache.put(secondNode.hashCode()+"-"+firstNode.hashCode(), new Boolean(true));
			return true; 
		}
		if(isParentOf(secondNode, firstNode)) {
			isSameBranchCache.put(firstNode.hashCode()+"-"+secondNode.hashCode(), new Boolean(true));
			isSameBranchCache.put(secondNode.hashCode()+"-"+firstNode.hashCode(), new Boolean(true));
			return true;
		}


		isSameBranchCache.put(firstNode.hashCode()+"-"+secondNode.hashCode(), new Boolean(false));
		isSameBranchCache.put(secondNode.hashCode()+"-"+firstNode.hashCode(), new Boolean(false));

		return false;
		
	}
	
	/**
	 * columnXpath의 노드들을 찾고, 이 중 rowNode의 줄기에 있는 것의 value를 반환한다.
	 * 
	 * @param rowNode 
	 * @param xpath full xpath as like "/aaa/bbb/ccc"
	 * @throws XPathExpressionException 
	 */
	public static String getNodeValueOfSameBranch(Node rowNode, String xpath) throws XPathExpressionException {
		
		
		if(rowNode==null) { return ""; }
		if(xpath==null) { return ""; }
		
		Node targetNode = findNodeWithXpathInSameBranch(rowNode, xpath);
		if(targetNode==null) { return ""; }

		return getNodeValue(targetNode);
		
	}
	
	
	
	/**
	 * 전달되는 노드를 기준으로 xpath의 노드를 찾아서 그 값을 반환한다.
	 * /a/b/c/d/e 만약 rowNode가 "a/b" 라면 넘겨지는 xpath는 "/c/d" 처럼 rowNode이하의 것.
	 * @param rowNode 
	 * @param childColumnXpath xpath as like "/aaa/bbb/ccc"
	 */
	public static String getChildNodeValue(Node rowNode, String childColumnXpath) throws TransformerException {

		if(rowNode==null) { return ""; }
		if(childColumnXpath==null) { return ""; }

		// 자기 자신이다.
		if(childColumnXpath.equals("")) {
			return XmlUtil.getNodeValue(rowNode);
		}

		//"@att"와 같이 "/"가 없이 넘어오는 경우가 있다. 
		if(childColumnXpath.startsWith("@")) {
			childColumnXpath = "/"+childColumnXpath;
		}

		String[] xpathPieces = childColumnXpath.split("/");
		if(xpathPieces.length<2) { return ""; }
		String targetNodeName = xpathPieces[1];	// 2번 째다. 1번째는 항상 ""이니까.
		if(targetNodeName==null) { return ""; }
		if(targetNodeName.equals("")) { return ""; }

		String columnValue = "";

		// attribute인 경우네.
		if(targetNodeName.startsWith("@")) {
			NamedNodeMap attributeMap = rowNode.getAttributes();
			for(int i=0; i<attributeMap.getLength(); i++) {
				Node attributeNode = attributeMap.item(i);
				if(targetNodeName.substring(1).equals(attributeNode.getNodeName())) {
					return attributeNode.getNodeValue();
				}
			}
		}

		NodeList childNodeList = rowNode.getChildNodes();
		if(childNodeList==null) { return ""; }
		if(childNodeList.getLength()==0) { return ""; }


		Node childNode;
		for(int i=0; i<childNodeList.getLength(); i++) {
			childNode = childNodeList.item(i);
			if(targetNodeName.equals(childNode.getNodeName())) {
				if(xpathPieces.length==2) {
					columnValue = XmlUtil.getNodeValue(childNode);
					if(columnValue==null) { columnValue = ""; }
				}
				else {
					String childNodeXpath = "";
					for(int j=2; j<xpathPieces.length; j++) {
						childNodeXpath += "/" + xpathPieces[j];
					}
					return getChildNodeValue(childNode, childNodeXpath);
				}
			}
		}

		return columnValue;

	}

	
	private static Map<Node, String> fullXpathCache = new ThreadLocalMap<Node, String>();
	public static String getFullXpath(Node node) {
		
		String fullXpathString = fullXpathCache.get(node);
		if(fullXpathString!=null) { return fullXpathString; }
		
		Node parentNode = node.getParentNode();
		if(parentNode.getLocalName()==null) {
			// 부모의 이름이 null이면 자기 자신이 root이다. 부모는 Document
			fullXpathString = "/"+node.getLocalName();
			fullXpathCache.put(node, fullXpathString);
			return fullXpathString;
		}
		else {
			if(node.getNodeType()==Node.ATTRIBUTE_NODE) {
				fullXpathString = getFullXpath(parentNode)+"/@"+node.getLocalName(); 
				fullXpathCache.put(node, fullXpathString);
				return fullXpathString;
			}
			else {
				fullXpathString = getFullXpath(parentNode)+"/"+node.getLocalName(); 
				fullXpathCache.put(node, fullXpathString);
				return fullXpathString;
			}
		}
	}


	
	

}
