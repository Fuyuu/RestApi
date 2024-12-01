package edu.hongik.apihw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.hongik.apihw.crawl.DataInserter;
import edu.hongik.apihw.entity.Students;
import edu.hongik.apihw.service.CrawlService;

@RestController
public class CrawlController {
	
	@Autowired
    private CrawlService crawlService;

    @Autowired
    private DataInserter dataInserter;

    @GetMapping("/crawl")
    public String crawlAndSave() {
        // 웹페이지를 크롤링하여 학생 리스트를 생성
        List<Students> studentList = crawlService.crawlData();

        // 생성한 리스트를 DB에 저장
        dataInserter.insertCrawledData(studentList);

        return "크롤링과 데이터 삽입을 성공적으로 완료했습니다.";
    }
}
