/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.artkostm.androidparsers.core.validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Artsiom
 */
public final class XsdSchemaDOMValidator {
    
    public static final String VALIDATION_SCHEMA_FILE_PATH = "./src/main/resources/validation_schema.xsd";
    /**
     * To validate DOM document.
     * 
     * @param doc
     * @return true if the collection of errors is empty.
     * @throws FileNotFoundException
     * @throws SAXException
     * @throws IOException 
     */
    public static boolean validate(Document doc) 
            throws FileNotFoundException, SAXException, IOException{
     
        InputStream xsdAsStream = new FileInputStream(VALIDATION_SCHEMA_FILE_PATH);
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(xsdAsStream));
        PErrorHandler er = new PErrorHandler();
        Validator validator = schema.newValidator();
        validator.setErrorHandler(er);
        validator.validate(new DOMSource(doc));
        return er.getAllErrors().isEmpty();
    }
    
    private XsdSchemaDOMValidator(){}
    
    public static class PErrorHandler implements ErrorHandler {
        /**
         * File is being validated.
         */
        @SuppressWarnings("unused")
        private File file;
        /**
         * Contains errors, occured while validation XML document.
         */
        @SuppressWarnings("rawtypes")
        private final Collection errors = new LinkedList();

        /**
         * Creates new instance of XSDErrorHandler.
         */
        public PErrorHandler() {
        }
        
        /**
         * 
         * @return Collection of errors
         */
        @SuppressWarnings("rawtypes")
        public Collection getAllErrors(){
            return errors;
        }
        
        /**
         * 
         * @param exception
         * @throws SAXException 
         */
        @SuppressWarnings("unchecked")
        @Override
        public void warning(SAXParseException exception) throws SAXException {
            errors.add(exception);
        }

        /**
         * 
         * @param exception
         * @throws SAXException 
         */
        @SuppressWarnings("unchecked")
        @Override
        public void error(SAXParseException exception) throws SAXException {
            errors.add(exception);
        }

        /**
         * 
         * @param exception
         * @throws SAXException 
         */
        @SuppressWarnings("unchecked")
        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            errors.add(exception);
        }
    }
}
