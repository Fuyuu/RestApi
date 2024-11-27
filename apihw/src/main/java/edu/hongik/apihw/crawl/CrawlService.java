package edu.hongik.apihw.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import edu.hongik.apihw.entity.Students;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrawlService {
	
    public static List<Students> crawlData() {

        final String url = "https://apl.hongik.ac.kr/lecture/dbms";
        List<Students> studentList = new ArrayList<>();
        
        try {
            Document doc = Jsoup.connect(url).get();

            Elements phdElements = doc.select("h2:contains(PhD Students) + ul > li");
            parseStudentElements(phdElements, "PhD", studentList);

            Elements masterElements = doc.select("h2:contains(Master Students) + ul > li");
            parseStudentElements(masterElements, "Masters", studentList);

            Elements undergradElements = doc.select("h2:contains(Undergraduate Students) + ul > li");
            parseStudentElements(undergradElements, "Undergraduate", studentList);
        } catch (IOException e) {
        	System.err.println("Error occurred during crawling: " + e.getMessage());
            e.printStackTrace();
        }
        
        return studentList;
    }
    
    private static void parseStudentElements(Elements elements, String degree, List<Students> studentList) {
        for (Element element : elements) {
            try {
            	
                String text = element.text();
                String[] parts = text.split(", ");

                if (parts.length == 3) {
                String name = parts[0];
                String email = parts[1];
                int grad = Integer.parseInt(parts[2].trim());
                studentList.add(new Students(name, email, degree, grad));
                }
                
            } catch (Exception e) {
                System.err.println("Error occured during parsing element: " + element.text());
            }
        }
    }
}


