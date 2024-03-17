package com.dayone.scraper;

import com.dayone.model.Company;
import com.dayone.model.Dividend;
import com.dayone.model.ScrapedResult;
import com.dayone.model.constants.Month;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class YahooFinanceScraper implements Scraper {

    private static final String STATISTICS_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
        //url을 멤버변수로 빼주기
    private  static  final String SUMMAR_URL = "https://finance.yahoo.com/quote/%s/?p=%s";
    private static final long START_TIME =86400; //60초*60분*24
    @Override
    public ScrapedResult scrap(Company company){
        var scrapResult = new ScrapedResult();
        scrapResult.setCompany(company);

        try{
            long start = 0 ;
            long end = System.currentTimeMillis() / 1000;
            String url = String.format(STATISTICS_URL, company. getTicker(), start, end);
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableEle = parsingDivs.get(0);

            Element tbody = tableEle.children().get(1);

            List<Dividend> dividends =new ArrayList<>();
            for(Element e : tbody.children()){

                String txt = e.text();
                if(!txt.endsWith("Dividend")){
                    continue;
                }
                String[] splits = txt.split(" ");

                int month = Month.strToNumber(splits[0]);
                int day = Integer.valueOf(splits[1].replace(",",""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];

                if(month < 0){
                    throw new RuntimeException("Unexpected Month erum value -> "+splits[0]);
                }

                dividends.add(Dividend.builder()
                        .date(LocalDateTime.of(year, month, day, 0, 0))
                        .dividend(dividend)
                        .build());

//                System.out.println(year +"/" + month +"/" + day + " -> " + dividend);
            }
            scrapResult.setDividendEntities(dividends);

        }catch (IOException e){
            //TODO
            e.printStackTrace();
        }


        ScrapedResult ScrapeResult;
        return scrapResult;
    }
    @Override
    public Company scrapCompanyByTicker(String ticker){
        String url = String.format(SUMMAR_URL, ticker, ticker);

        try {
            Document document = Jsoup.connect(url).get();
            Element titledEle = document.getElementsByTag("h1").get(0);
            String title = titledEle.text().split(" - ")[1].trim();

            return  Company.builder()
                    .ticker(ticker)
                    .name(title)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
