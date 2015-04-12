import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by chiemsaeteurn on 4/12/15.
 */
public class JPEGtoByte64 {

    public static String encodeToString(BufferedImage image, String type)
    {
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

    public static void main(String[] args) throws IOException
    {
        File directory = new File("emotions");
        File[] moods = directory.listFiles();
        PictureAnalyzer pic = new PictureAnalyzer();

        for (File eachMood : moods)
        {
            if (eachMood.listFiles() == null) continue;
            for (File picture : eachMood.listFiles())
            {
                if (picture.getName().equals(".DS_Store")) continue;
                BufferedImage compareImage = pic.Image(directory.getName() + "/" +
                        eachMood.getName() + "/" + picture.getName());
                String encodedPicture = encodeToString(compareImage, "jpeg");
                File picFile = new File(picture.getName().substring(0, picture.getName().length() - 4) + ".txt");
                PrintWriter printWriter = new PrintWriter(directory.getName() + "/" +
                        eachMood.getName() + "/" + picFile);
                printWriter.println (encodedPicture);
                printWriter.close();
            }
        }
    }
}
