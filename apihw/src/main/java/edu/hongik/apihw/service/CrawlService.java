package edu.hongik.apihw.service;

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
	// Jsoup을 이용해 웹사이트를 크롤링
    public static List<Students> crawlData() {

        final String url = "https://apl.hongik.ac.kr/lecture/dbms";
        List<Students> studentList = new ArrayList<>();
        
        try {
        	// 각 학위 별로 학생들을 크롤링
            Document doc = Jsoup.connect(url).get();

            Elements phdElements = doc.select("h2:contains(PhD Students) + ul > li");
            parseStudentElements(phdElements, "PhD", studentList);

            Elements masterElements = doc.select("h2:contains(Master Students) + ul > li");
            parseStudentElements(masterElements, "Masters", studentList);

            Elements ugrdElements = doc.select("h2:contains(Undergraduate Students) + ul > li");
            parseStudentElements(ugrdElements, "Undergraduate", studentList);
        } catch (IOException e) {
        	System.err.println("크롤링 도중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        
        return studentList;
    }
    
    private static void parseStudentElements(Elements elements, String degree, List<Students> studentList) {
    	// 크롤링한 학생들을 파싱하여 리스트에 저장
        for (Element element : elements) {
        	
            try {
                String text = element.text();
                String[] parts = text.split(", ");

                if (parts.length == 3) {
                String name = parts[0];
                String email = parts[1];
                int graduation = Integer.parseInt(parts[2].trim());
                studentList.add(new Students(name, email, degree, graduation));
                }
                
            } catch (Exception e) {
                System.err.println("요소를 파싱하는 도중 오류 발생: " + element.text());
            }
        }
    }
}


