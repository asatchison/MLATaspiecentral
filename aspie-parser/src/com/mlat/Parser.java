package src.com.mlat;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yokoy on 1/16/16.
 */
public class Parser {

    final static String HTMLSTRING = "AspiesCentral.com Log in or Sign up AspiesCentral.com Home Forums > Announcements & Feedback > Site Questions, Suggestions & Feedback > Welcome to Aspies Central, a friendly forum to discuss Aspergers Syndrome, Autism, High Functioning Autism and related conditions. Your voice is missing! You will need to register to get access to the following site features: Reply to discussions and create your own threads. Our modern chat room. No add-ons or extensions required, just login and start chatting! Private Member only forums for more serious discussions that you may wish to not have guests or search engines access to. Your very own blog. Write about anything you like on your own individual blog. We hope to see you as a part of our community soon! Please also check us out @ https:\\/\\/www.twitter.com\\/aspiescentral";

    private static List<String> getStopwordsFromFile() {
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

    public static void main(String[] args) throws MalformedURLException, SolrServerException {
        SolrManager solr = new SolrManager();
        SolrDocumentList solrDocumentList = solr.getSolrDocumentList();
        List<String> stopwords = getStopwordsFromFile();
        BufferedWriter writer = null;
        try {
            for (int i=0; i < solrDocumentList.size(); ++i) {
                String filename = "thread" + i + ".txt";
                String url = (solrDocumentList.get(i).getFieldValue("url").toString()).replace("[", "").replace("]","");
                if (!url.endsWith("index.rss")) {
                    String title = (solrDocumentList.get(i).getFieldValue("title").toString()).replace("[", "").replace("]","").replace("| AspiesCentral.com", "");
                    String content = (solrDocumentList.get(i).getFieldValue("content").toString()).replace(HTMLSTRING, "");
                    // Replacing all non-alpha characters with empty strings
                    String filteredContent = content.replaceAll("[^A-Za-z ]", "");
                    // Separating each string by space and store them into array
                    String textStr[] = filteredContent.split("\\s+");
                    System.out.println("Title: " + title);
                    System.out.println("URL: " + url);
                    writer = new BufferedWriter(new FileWriter("output/" + filename, false));
                    writer.write("Title: " + title + "\n");
                    writer.write("URL: " + url + "\n");
                    for (int j = 0; j < textStr.length; ++j) {
                        // Don't include string that has length 1
                        // Don't include string that are stop words
                        if (textStr[j].length() > 1 && !stopwords.contains(textStr[j].toLowerCase())) {
                            writer.write(textStr[j] + "\n");
                        }
                    }
                    System.out.println("Successfully wrote to file >> " + filename);
                    System.out.println("--------------------------------------------------------------------------------");
                    writer.close();
                } else {
                    // avoid duplicate threads
                    System.out.println("Skipping " + url + "....");
                    System.out.println("--------------------------------------------------------------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
