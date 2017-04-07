package sewisc.classroomfinder;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gian on 4/1/17.
 */

public class XMLParser {
    private static final String ns = null;


    public static List<Node> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private static List<Node> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Node> entries = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the Node tag
            if (name.equals("Node")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }

        return entries;
    }

    /*public static class Entry {
        public final String name;
        public final String type;
        public final String[] neighbors;
        public final int x;
        public final int y;
        public final int z;

        private Entry(String name, String type, String[] neighbors, int x, int y, int z) {
            this.name = name;
            this.type = type;
            this.neighbors = neighbors;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }*/

    private static Node readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Node");
        String name = parser.getAttributeValue(0);
        NodeType type = null;
        String neighbors = null;
        String[] neighborsList = new String[0];
        int x = -1;
        int y = -1;
        int z = -1;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String parserName= parser.getName();
            if (parserName.equals("type")) {
                type = readType(parser);
            } else if (parserName.equals("X")) {
                x = readX(parser);
            } else if (parserName.equals("Y")) {
                y = readY(parser);
            } else if (parserName.equals("Z")) {
                z = readZ(parser);
            } else if (parserName.equals("Neighbor")) {
                neighbors = readNeighbors(parser);
            } else {
                skip(parser);
            }
        }
        if(neighbors != null) neighborsList = neighbors.split(",");
        for (int i = 0; i < neighborsList.length; i++){
        	neighborsList[i].trim();
        }
        return new Node(type, x, y, z, name, neighborsList);
    }

    private static NodeType readType(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "type");
        String type = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "type");
        NodeType enumType;
        if (type.equalsIgnoreCase("Normal")){
        	enumType = NodeType.normal;
        }else if (type.equalsIgnoreCase("Bathroom")){
        	enumType = NodeType.bathroom;
        }else if (type.equalsIgnoreCase("Elevator")){
        	enumType = NodeType.elevator;
        }else if (type.equalsIgnoreCase("Stair")) {
            enumType = NodeType.stair;
        }else if (type.equalsIgnoreCase("Hall")) {
            enumType = NodeType.hall;
        }else{
        	//TODO Throw proper exception for type not found
        	// throw new XmlPullParserException();
            enumType = null;
        }
        return enumType;
    }

    private static int readX(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "X");
        int x = readNumber(parser);
        parser.require(XmlPullParser.END_TAG, ns, "X");
        return x;
    }

    private static int readY(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Y");
        int y = readNumber(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Y");
        return y;
    }

    private static int readZ(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Z");
        int z = readNumber(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Z");
        return z;
    }

    private static String readNeighbors(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Neighbor");
        String neighbors = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Neighbor");
        return neighbors;
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static int readNumber(XmlPullParser parser) throws IOException, XmlPullParserException {
        int result = -1;
        if (parser.next() == XmlPullParser.TEXT) {
            result = Integer.parseInt(parser.getText());
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}
