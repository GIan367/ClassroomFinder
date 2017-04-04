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


    public List parse(InputStream in) throws XmlPullParserException, IOException {
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

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();
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

    public static class Entry {
        public final String name;
        public final String type;
        public final int x;
        public final int y;
        public final int z;

        private Entry(String name, String type, int x, int y, int z) {
            this.name = name;
            this.type = type;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Node");
        String name = parser.getAttributeValue(0);
        String type = null;
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
            } else {
                skip(parser);
            }
        }
        return new Entry(name, type, x, y, z);
    }

    private String readType(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "type");
        String type = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "type");
        return type;
    }

    private int readX(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "X");
        int x = readNumber(parser);
        parser.require(XmlPullParser.END_TAG, ns, "X");
        return x;
    }

    private int readY(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Y");
        int y = readNumber(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Y");
        return y;
    }

    private int readZ(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Z");
        int z = readNumber(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Z");
        return z;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private int readNumber(XmlPullParser parser) throws IOException, XmlPullParserException {
        int result = -1;
        if (parser.next() == XmlPullParser.TEXT) {
            result = Integer.parseInt(parser.getText());
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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
