package polytech.example.tp5;

import android.os.AsyncTask;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class CallWebApi extends AsyncTask<String, String, String> {
    private TextView mTextView;

    public CallWebApi(TextView mTextView) {
        this.mTextView = mTextView;
    }

    @Override
    protected String doInBackground(String... params) {
        String inputLine = "";
        StringBuilder result = null;
        URL url;

        try {
            url = url =new URL(params[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            in.close();
            return result.toString();

        } catch (Exception e) {
        }

        return "error";
    }

    protected void onPostExecute(String result) {
        mTextView.setText(result);
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
        } else if (name.equals("query")) {
            result.setQuery(parser.nextText());
        } else if (name.equals("country")) {
            result.setCountry(parser.nextText());
        }
    }
}
