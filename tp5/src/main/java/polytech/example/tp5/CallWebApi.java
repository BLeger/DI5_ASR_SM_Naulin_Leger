package polytech.example.tp5;

import android.os.AsyncTask;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class CallWebApi extends AsyncTask<String, String, GeoIP> {
    private MainActivity activity;

    public CallWebApi(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected GeoIP doInBackground(String... params) {
        String inputLine = "";
        GeoIP result = null;
        URL url;

        try {
            url = new URL(params[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new GeoIP();

            // Parse XML
            XmlPullParserFactory pullParserFactory;
            try {
                pullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = pullParserFactory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                result = parseXML(parser);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }*/

            in.close();
            return result;

        } catch (Exception e) {
        }

        return null;
    }

    protected void onPostExecute(GeoIP result) {
        this.activity.getResult(result);
    }

    private GeoIP parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        GeoIP result = new GeoIP();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    parseGeoIPField(parser, result);
                    break;
                case XmlPullParser.END_TAG:
                    break;
            } // end switch
            eventType = parser.next();
        } // end while
        return result;
    }

    private void parseGeoIPField(XmlPullParser parser, GeoIP result) throws IOException, XmlPullParserException {
        String name = parser.getName();

        if (name.equals("Error")) {
            System.out.println("Web API Error!");
        } else if (name.equals("status")) {
            result.setStatus(parser.nextText());
        } else if (name.equals("countryCode")) {
            result.setCountryCode(parser.nextText());
        } else if (name.equals("country")) {
            result.setCountry(parser.nextText());
        }
    }
}
