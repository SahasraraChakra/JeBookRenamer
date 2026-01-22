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

    private static final File XML_FILE = new File("config.xml");

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

        // remove old
        while (categoriesNode.hasChildNodes()) {
            categoriesNode.removeChild(categoriesNode.getFirstChild());
        }

        // add unique, sorted
        categories.stream()
                .distinct()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .forEach(c -> {
                    Element e = doc.createElement("category");
                    e.setTextContent(c);
                    categoriesNode.appendChild(e);
                });

        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.transform(new DOMSource(doc), new StreamResult(XML_FILE));
    }
}
