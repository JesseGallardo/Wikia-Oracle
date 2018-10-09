import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Parser {
    public Parser() {

    }

    public void getURL(String[] keywords) throws IOException{
        //Borrowed from user BalusC from Stack Overflow
        String google = "http://www.google.com/search?q=";
        String charSet = "UTF-8";
        StringBuilder search = new StringBuilder();
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] += " ";
            search.append(keywords[i]);
        }
        Elements links = Jsoup.connect(google + URLEncoder.encode(search.toString(), charSet)).userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)").get().select(".g>.r>a");
        boolean acceptable;

        for (Element link: links) {
            String title = link.text();
            String url = link.absUrl("href");
            url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

            if(!url.startsWith("http")){
                continue;
            }

            System.out.println("Title: " + title);
            System.out.println("URL: " + url);
        }
    }

    public void getInfo() throws IOException {
        Document doc = Jsoup.connect("http://gurrenlagann.wikia.com/wiki/Simon").get();
        //System.out.println(doc.title());
        //int it = 0;

        Elements sideBar = doc.getElementsByClass("portable-infobox");
        for (Element element : sideBar) {
            Elements sideBarSections = element.getElementsByTag("section");
            for (Element element1 : sideBarSections) {
                Elements items = element1.children();
                ArrayList<String> list = new ArrayList<>();
                for (Element element2 : items) {
                    if(!element2.toString().equals(" ")){
                        list.add(element2.toString());
                    }
                }

                for (int i = 0; i < list.size(); i++){
                    String[] html = list.get(i).split("\n");

                    for(int j = 0; j < html.length; j++){
                        if(html[j].contains("h2")){
                            Document document = Jsoup.parse(html[j]);
                            Element e = document.select("h2").first();
                            if(e == null){
                                continue;
                            }
                            System.out.println(e.text() + ":");
                        }
                        else if(html[j].contains("h3")){
                            Document document = Jsoup.parse(html[j]);
                            Element e = document.select("h3").first();
                            if(e == null){
                                continue;
                            }
                            System.out.println(e.text());
                        }
                        else if(html[j].contains("li")){
                            Document document = Jsoup.parse(html[j]);
                            Element e = document.select("li").first();
                            if(e == null){
                                continue;
                            }
                            System.out.println("- " + e.text());
                        }
                        else if(html[j].contains("div")){
                            Document document = Jsoup.parse(html[j]);
                            Element e = document.select("div").first();
                            if(e == null || e.text().equals("") || e.text().equals(" ")){
                                continue;
                            }
                            System.out.println("- " + e.text());
                        }
                        else if(html[j-1].contains("div class") && html[j+1].equals(" </div> ")){
                            if(html[j].contains("a href")){
                                Document document = Jsoup.parse(html[j]);
                                Element e = document.select("a").first();
                                if(e == null || e.text().equals("") || e.text().equals(" ")){
                                    continue;
                                }
                                System.out.println("- " + e.text());
                                continue;
                            }

                            String[] fuck = html[j].split(" ");
                            for (int k = 0; k < fuck.length; k++) {
                                if(!fuck[k].equals("")){
                                    System.out.print(fuck[k] + " ");
                                }
                            }
                            System.out.println();
                        }
                        else if(html[j].contains("href")){
                            Document document = Jsoup.parse(html[j]);
                            Element e = document.select("a").first();
                            if(e == null || e.text().equals("") || e.text().equals(" ")){
                                continue;
                            }
                            System.out.println("- " + e.text());
                        }
                    }

                    System.out.println();
                }
            }
        }//for-each #1
    }

}

