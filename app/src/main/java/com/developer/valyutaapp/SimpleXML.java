package com.developer.valyutaapp;

/**
 * Created by User on 12.06.2018.
 */
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Shoorik on 26.07.2017.
 */
public class SimpleXML {
    boolean Debuger = false;
    Document doc = null;
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    public Document getDomElement(String xml){
        doc = null;
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

            doc.getDocumentElement().normalize();

        } catch (ParserConfigurationException e) {
            if(Debuger) Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            if(Debuger) Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            if(Debuger) Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }
    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }


    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
    public String getValueByTag(String tagName){

        NodeList nodeList = doc.getElementsByTagName("ValCurs");
//	  NodeList nodeList = doc.getElementsByTagName("document");

        Node node = nodeList.item(0);
        Element fstElmnt = (Element) node;
        NodeList nameList = fstElmnt.getElementsByTagName(tagName);
        Element nameElement = (Element) nameList.item(0);
        nameList = nameElement.getChildNodes();
        String vrmValue = "";
        Node vrmPic = nameList.item(0);
        if(vrmPic != null)
            vrmValue = vrmPic.getNodeValue();

        if(Debuger) Log.d(tagName +": ", vrmValue);

        return vrmValue;


    }

}