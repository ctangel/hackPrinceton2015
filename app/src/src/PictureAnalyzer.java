import java.awt.image.BufferedImage;
import java.io.*;

import com.github.sarxos.webcam.Webcam;
import org.apache.commons.io.FileUtils;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

import java.awt.Desktop;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

import org.apache.http.HttpResponse;

import org.apache.http.NameValuePair;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;


public class PictureAnalyzer {
    private String API_KEY = "23afa04e9ba9c51082721a042f52da56";

    // Returns json string as response
    public String Sentiment(String data)
    {
        String jsonString = null;
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(
                "http://apiv2.indico.io/political?key=" + API_KEY);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("data", data));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            jsonString = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {

            // TODO Auto-generated catch block

            return("CPE" + e);

        } catch (IOException e) {

            // TODO Auto-generated catch block

            return ("IOE" + e);
        }

        return jsonString;
    }


    // Returns a buffered image from a picture name
    public BufferedImage Image(String filename)
    {
        BufferedImage image;
        // Read filename of image and put it into
        // buffered image variable
        int width, height;
        try {
            // try to read from file in working directory
            File file = new File(filename);
            if (file.isFile()) {
                image = ImageIO.read(file);
            }

            // now try to read from file in same directory as this .class file
            else {
                URL url = getClass().getResource(filename);
                if (url == null) { url = new URL(filename); }
                image = ImageIO.read(url);
            }
//            width  = image.getWidth(null);
//            height = image.getHeight(null);
        }
        catch (IOException e)
        {
            // e.printStackTrace();
            throw new RuntimeException("Could not open file: " + filename);
        }

        return image;
    }


    public String ImageFeatures(String base64img)
    {
        String jsonString = null;
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(
                "http://apiv2.indico.io/imagefeatures?key=" + API_KEY);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("data", base64img));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            jsonString = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {

            // TODO Auto-generated catch block

            return("CPE" + e);

        } catch (IOException e) {

            // TODO Auto-generated catch block

            return ("IOE" + e);
        }

        return jsonString;
    }



    public String getURL (String song, String artist)
    {

        String jsonString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(
                    "https://api.spotify.com/v1/search?q="
                            + URLEncoder.encode(song, "UTF-8") + "&type=track");
        }
        catch (UnsupportedEncodingException e)
        {

        }


        try {
//            // Add your data
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httpGet);
            jsonString = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {

            // TODO Auto-generated catch block

            return("CPE" + e);

        } catch (IOException e) {

            // TODO Auto-generated catch block

            return ("IOE" + e);
        }

        return jsonString;
    }



    public static void main(String[] args) throws JSONException{

        PictureAnalyzer test = new PictureAnalyzer();

        HappySad happy = new HappySad();
        File happyFile;
        Scanner happyScanner = null;
        try {
            happyFile = new File("Happy.txt");
            happyScanner = new Scanner(happyFile).useDelimiter(Pattern.compile("\\A"));
        }
        catch (FileNotFoundException e) {
        }

        int n = 0;
        while (happyScanner.hasNext())
        {
            String line = happyScanner.nextLine();
            int firstColon = line.indexOf(":");
            int secondColon = line.indexOf(":", firstColon + 1);
            String song = line.substring(0, firstColon);
            String artist = line.substring(firstColon + 1, secondColon);

            happy.putSong(song);
            happy.putArtist(artist);
            //happy.putScore(Integer.parseInt(line.substring(secondColon + 1, line.length() - 1)));

            n++;
        }

        HappySad sad = new HappySad();
        File sadFile;
        Scanner sadScanner = null;
        try {
            sadFile = new File("Sad.txt");
            sadScanner = new Scanner(sadFile).useDelimiter(Pattern.compile("\\A"));
        }
        catch (FileNotFoundException e) {
        }
        n = 0;
        while (sadScanner.hasNext())
        {
            String line = sadScanner.nextLine();
            int firstColon = line.indexOf(":");
            int secondColon = line.indexOf(":", firstColon + 1);
            String song = line.substring(0, firstColon);
            String artist = line.substring(firstColon + 1, secondColon);

            sad.putSong(song);
            sad.putArtist(artist);
            //sad.putScore(Integer.parseInt(line.substring(secondColon + 1, line.length())));

            n++;
        }

        String camImg = "camImage.jpg";
        Webcam webcam = Webcam.getDefault();
        System.out.println("Taking a picture of the environment...");
        webcam.open();
        try
        {
            ImageIO.write(webcam.getImage(), "JPEG", new File(camImg));
        }
        catch (IOException e)
        {

        }

        System.out.println("Done.");
        webcam.close();

        System.out.println("Determining the mood of the picture...");
        // Query image
//        BufferedImage queryImage = test.Image(args[0]);
        BufferedImage queryImage = test.Image(camImg);
        String queryFeatures = test.ImageFeatures(encodeToString(queryImage, "jpeg"));
        JSONObject queryObj = new JSONObject(queryFeatures);
        JSONArray queryVectors = queryObj.getJSONArray("results");

        File directory = new File("emotions");
        
        File[] moods = directory.listFiles();
        TreeMap<String, ArrayList<File>> moodTable = new TreeMap<String, ArrayList<File>>();
        TreeMap<String, Double> distanceTable = new TreeMap<String, Double>();
        double highestDist = Double.POSITIVE_INFINITY;
        File theMood = null;
        // Store each mood into the symbol table
        // Value is an array list of the pictures
        for(File eachMood : moods)
        {
            if (eachMood.listFiles() == null) continue;
            // From each mood in the symbol table,
            // Acquire its arraylist and add the pictures inside.
            Double distance = distanceTable.put(eachMood.getName(), 0.0);
            ArrayList<File> mood = new ArrayList<File>();
            moodTable.put(eachMood.getName(), mood);
            for (File picture : eachMood.listFiles()) {
                if (picture.getName().equals(".DS_Store")) continue;
                moodTable.get(eachMood.getName()).add(picture);

                String name = picture.getName().substring(0, picture.getName().length() - 4) + ".txt";
                String byte64 = null;
                try {
                    File txtFile = new File(directory.getName() + "/" +
                            eachMood.getName() + "/" + name);
                    byte64 = FileUtils.readFileToString(txtFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Calculate the euclidean distance
                String compareFeatures = test.ImageFeatures(byte64);
                JSONObject compareObj = new JSONObject(compareFeatures);
                JSONArray compareVectors = compareObj.getJSONArray("results");
                for (int i = 0; i < queryVectors.length() / 2; i++) {
                    distance = distanceTable.get(eachMood.getName());
                    distanceTable.put(eachMood.getName(), distance +
                            ((queryVectors.getDouble(i) - compareVectors.getDouble(i))
                                    * (queryVectors.getDouble(i) - compareVectors.getDouble(i))));
                }
            }
            //System.out.println(eachMood.getName() + " " + distanceTable.get(eachMood.getName()) / moods.length);
            if (distance / moods.length < highestDist)
            {
                highestDist = distance / moods.length;
                theMood = eachMood;
            }
        }

        System.out.println("A " + theMood.getName() + " song is now playing:");

        String song = null;
        if (theMood.getName().equals("Happy"))
            song = happy.getRandSong();
        if (theMood.getName().equals("Sad"))
            song = sad.getRandSong();

        System.out.println(song);
        String json = test.getURL(song, song);

        JSONObject query1Obj = new JSONObject(json);
        String query = query1Obj.get("tracks").toString();
        int k = query.indexOf("https://open.spotify.com/track/");
        int l = query.indexOf("\"", k);

        URI uriMan = URI.create(query.substring(k, l));

        try {
            Desktop.getDesktop().browse(uriMan);
        }
        catch (IOException e)
        {

        }
    }

    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageString;
    }

}