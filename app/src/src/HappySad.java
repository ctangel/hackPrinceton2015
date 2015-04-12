import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by chiemsaeteurn on 4/12/15.
 */
public class HappySad
{
    private ArrayList<String> songNames = new ArrayList<String>();
    private ArrayList<String> artists = new ArrayList<String>();
    private ArrayList<Integer> scores = new ArrayList<Integer>();

    public HappySad()
    {

    }

    public void putSong(String song)
    {
        songNames.add(song);
    }

    public void putArtist(String artist)
    {
        artists.add(artist);
    }

    public void putScore(int score)
    {
        scores.add(score);
    }

    public String getRandArtist()
    {
        double k = Math.random() * artists.toArray().length;

        return artists.get((int) k);
    }

    public String getRandSongArtist()
    {
        double k = Math.random() * artists.toArray().length;

        return songNames.get((int) k) + " by " + artists.get((int) k);
    }

    public String getRandSong()
    {
        double k = Math.random() * songNames.toArray().length;

        return songNames.get((int) k);
    }

    public static void main(String[] args)
    {

    }
}
