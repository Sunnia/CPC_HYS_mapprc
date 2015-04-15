package com.example.michael.mapprc;

/**
 * Created by michael on 13/04/15.
 */
import android.content.res.XmlResourceParser;
import android.provider.DocumentsContract;
import android.util.Xml;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class UseXmlStation {

    private static final String ns = null;


    public static List<Station> ReadStationXML(XmlPullParser xrp) throws Exception {

        try {
            return readResource(xrp);
        } finally {
            //in.close();
        }


    }
    private static List<Station> readResource(XmlPullParser xrp) throws XmlPullParserException, IOException {
        List<Station> stations = new ArrayList<Station>();
        Station sta = new Station();
        while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
            String tagname = xrp.getName();
            if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                if (tagname.endsWith("entry")) {
                    stations.add(readEntry(xrp));
                }
            }
            xrp.next();
        }
        return stations;
    }
    private static Station readEntry(XmlPullParser xrp) throws  XmlPullParserException, IOException{
        Station sta = new Station();
        while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
            if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                String tagname = xrp.getName();
                if (tagname.equals("title")) {
                    sta.setname(xrp.getAttributeValue(0));//name

                } else if (tagname.equals("address")) {
                    sta.setAddress(xrp.getAttributeValue(1));//name

                } else if (tagname.equals("position")) {
                    sta.setLatitude(xrp.getAttributeValue(0));//latitude
                    sta.setLongtitude(xrp.getAttributeValue(1));//longitude

                }
            }

            if(xrp.getEventType() == XmlResourceParser.END_TAG){
                String tagname = xrp.getName();
                if(tagname.equalsIgnoreCase("entry"))
                    return sta;
            }

            xrp.next();
        }

        return null;
    }



    private static Station SampleRead(XmlResourceParser xrp) throws  XmlPullParserException, IOException{
        return null;
    }

}