package by.artkostm.androidparsers.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * 
 * @author Artsiom
 *
 */
public final class DOMUtil {
    
    private static final Logger log = Logger.getRootLogger();
    
    private DOMUtil() {}
    
    public static Document createDocument(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("Can't create a new document", e);
            throw new RuntimeException("Can't create a new document", e);
        }
        Document doc = builder.newDocument();
        return doc;
    }
    
    /**
     * 
     * @param file
     * @return root node
     */
    public static Node getDocument(File file){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
//            XsdSchemaDOMValidator.validate(doc);
            Element root = doc.getDocumentElement();
            return root;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Can't parse " + file.getName() + " file", e);
            throw new RuntimeException("Can't parse " + file.getName() + " file", e);
        }
    }
    
    public static Node getDocument(InputStream is){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
//            XsdSchemaDOMValidator.validate(doc);
            Element root = doc.getDocumentElement();
            return root;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Can't parse " + is.toString() + " stream", e);
            throw new RuntimeException("Can't parse " + is.toString() + " stream", e);
        }
    }
    
    /**
     * 
     * @param doc
     * @throws TransformerConfigurationException
     * @throws TransformerException 
     */
    public static void transformDOM(Document doc, File file) {
        try{
            TransformerFactory transformerFactory = TransformerFactory  
                    .newInstance();  
            Transformer transformer = transformerFactory.newTransformer();  
            DOMSource domSource = new DOMSource(doc);  
            StreamResult streamResult = new StreamResult(file);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);
        }catch(Exception e){
            log.error("Can't transform document to " + file.getName() + " file", e);
            throw new RuntimeException("Can't transform document to " + file.getName() + " file", e);
        }
    }
}
