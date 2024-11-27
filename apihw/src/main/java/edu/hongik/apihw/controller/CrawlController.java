package edu.hongik.apihw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.hongik.apihw.crawl.CrawlService;
import edu.hongik.apihw.crawl.DataInserter;

@RestController
public class CrawlController {
	
	@Autowired
    private CrawlService crawlService;

    @Autowired
    private DataInserter dataInserter;

    @GetMapping("/crawl")
    public String crawlAndSave() {
        // 1. 크롤링 데이터 가져오기
        var dataList = crawlService.crawlData();

        // 2. 데이터베이스에 저장
        dataInserter.insertCrawledData(dataList);

        return "Crawling and data insertion completed!";
    }
}
