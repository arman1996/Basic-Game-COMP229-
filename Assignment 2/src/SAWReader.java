import bos.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SAWReader {
    List<String> contents;

    // Loads file contents into a String.
    public SAWReader(String filename) {
         try {
             contents = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(filename));
         } catch (IOException e){
             contents = new ArrayList();
         }
    }

    // Returns the sheep coordinates.
    public bos.Pair<Integer, Integer> getSheepLoc() {
        return searchForPair("sheep");
    }

    // Returns the wolf coordinates.
    public bos.Pair<Integer, Integer> getWolfLoc(){
        return searchForPair("wolf");
    }

    // Returns the shepherd coordinates.
    public bos.Pair<Integer, Integer> getShepherdLoc(){
        return searchForPair("shepherd");
    }

    // Searches for every instance of block and adds the coordinates as a pair into a list.
    // Returns the list of pairs.
    public List<bos.Pair<Integer, Integer>> getBlockLoc(){
        List<bos.Pair<Integer, Integer>> blocks = new ArrayList<>();
        for (String s: contents){
            Pattern p = Pattern.compile("block:\\s*\\((\\d+),\\s*(\\d+)\\)");
            Matcher m = p.matcher(s);
            if(m.matches()){
                blocks.add(new bos.Pair( Integer.parseInt(m.group(1).trim())
                        , Integer.parseInt(m.group(2).trim())));
            }
        }
        return blocks;

    }

    // Searches for the first instance of the target string.
    // Returns the coordinates as a pair.
    private bos.Pair<Integer,Integer> searchForPair(String target){
        for (String s: contents){
            Pattern p = Pattern.compile(target + ":\\s*\\((\\d+),\\s*(\\d+)\\)");
            Matcher m = p.matcher(s);
            if(m.matches()){
                return new bos.Pair( Integer.parseInt(m.group(1).trim())
                                   , Integer.parseInt(m.group(2).trim()));
            }
        }
        return new bos.Pair(0,0);
    }
}
