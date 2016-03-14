package com.mlat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yokoy on 1/16/16.
 */
public class Parser {

    final static String HTMLSTRING = "AspiesCentral.com Log in or Sign up AspiesCentral.com Home Forums > Announcements & Feedback > Site Questions, Suggestions & Feedback > Welcome to Aspies Central, a friendly forum to discuss Aspergers Syndrome, Autism, High Functioning Autism and related conditions. Your voice is missing! You will need to register to get access to the following site features: Reply to discussions and create your own threads. Our modern chat room. No add-ons or extensions required, just login and start chatting! Private Member only forums for more serious discussions that you may wish to not have guests or search engines access to. Your very own blog. Write about anything you like on your own individual blog. We hope to see you as a part of our community soon! Please also check us out @ https:\\/\\/www.twitter.com\\/aspiescentral";

    public static List<String> getStopwordsFromFile() {
        String line = "";
        List<String> stopwords = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("stop-words/stop-words_english_6_en.txt"));
            while ((line = br.readLine()) != null) {
                stopwords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopwords;
    }

//    public static void main(String[] args) {
//        JSONParser parser = new JSONParser();
//        List<String> stopwords = getStopwordsFromFile();
//        BufferedWriter writer = null;
//        try {
//            int size = 0;
//            JSONObject obj = (JSONObject) parser.parse(new FileReader("data/sample.txt"));
//            JSONObject response = (JSONObject) obj.get("response");
//            JSONArray docs = (JSONArray) response.get("docs");
//            while (docs.size() > size && docs.get(size) != null) {
//                JSONObject doc = (JSONObject) docs.get(size);
//                String title = doc.get("title").toString().replace("[", "").replace("]","").replace("\"", "");
//                String url =  (doc.get("url").toString()).replace("\\/", "/").replace("[", "").replace("]","").replace("\"", "");
//                if (!url.endsWith("index.rss")) {
//                    writer = new BufferedWriter(new FileWriter("output/thread" + size + ".txt"));
//                    // Replacing Html tags for every thread
//                    String content = doc.get("content").toString().replace(HTMLSTRING, "");
//
//                    System.out.println("Title: " + title);
//                    System.out.println("URL: " + url);
//
//                    // Replacing all non-alpha characters with empty strings
//                    String filteredContent = content.replaceAll("[^A-Za-z ]", "");
//                    // Separating each string by space and store them into array
//                    String textStr[] = filteredContent.split("\\s+");
//
//                    writer.write("Title: " + title + "\n");
//                    writer.write("URL: " + url + "\n");
//
//                    for (int i=0; i<textStr.length; ++i) {
//                        // Don't include string that has length 1
//                        // Don't include string that are stop words
//                        if (textStr[i].length() > 1 && !stopwords.contains(textStr[i].toLowerCase())) {
//                            writer.write(textStr[i] + "\n");
//                        }
//                    }
//
//                    System.out.println("Successfully wrote to file >" + "thread" + size + ".txt");
//                    System.out.println("----------------------------------------");
//                    writer.close();
//                } else {
//                    // avoid duplicate threads
//                    System.out.println("Skipping " + title + "....");
//                    System.out.println("----------------------------------------");
//
//                }
//                size++;
//            }
//        } catch (ParseException pe) {
//            System.out.println("position: " + pe.getPosition());
//            System.out.println(pe);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
