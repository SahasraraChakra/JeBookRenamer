/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EBookRenamerJ;

/**
 *
 * @author Jeevan Kumar
 */
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class ConfigXmlUtil {

    private static final File XML_FILE = new File("jsebr_config.xml");

    public static Document loadDocument() throws Exception {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        return b.parse(XML_FILE);
    }

    public static List<LanguageItem> loadLanguages(Document doc) {
        List<LanguageItem> list = new ArrayList<>();
        NodeList nodes = doc.getElementsByTagName("language");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            list.add(new LanguageItem(
                    e.getAttribute("id"),
                    e.getTextContent().trim()
            ));
        }
        return list;
    }

    public static List<String> loadCategories(Document doc) {
        List<String> list = new ArrayList<>();
        NodeList nodes = doc.getElementsByTagName("category");
        for (int i = 0; i < nodes.getLength(); i++) {
            list.add(nodes.item(i).getTextContent().trim());
        }
        return list;
    }
    
    public static void saveCategories(Document doc, Collection<String> categories) throws Exception {
        Node categoriesNode = doc.getElementsByTagName("categories").item(0);

        // Remove old children
        while (categoriesNode.hasChildNodes()) {
            categoriesNode.removeChild(categoriesNode.getFirstChild());
        }

        // Sort and add unique categories
        List<String> sortedCategories = new ArrayList<>(new HashSet<>(categories));
        sortedCategories.sort(String.CASE_INSENSITIVE_ORDER);

        for (String c : sortedCategories) {
            Element e = doc.createElement("category");
            e.setTextContent(c);
            categoriesNode.appendChild(e);
        }

        // Clean empty text nodes to prevent blank lines
        removeEmptyTextNodes(doc);

        // Save with pretty-printing; Transformer will handle newlines automatically
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        tf.transform(new DOMSource(doc), new StreamResult(XML_FILE));
    }

    /**
     * Recursively removes empty text nodes from the DOM to prevent extra blank
     * lines
     */
    private static void removeEmptyTextNodes(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE && child.getTextContent().trim().isEmpty()) {
                node.removeChild(child);
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                removeEmptyTextNodes(child);
            }
        }
    }
}
