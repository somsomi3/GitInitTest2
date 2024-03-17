package com.dayone.scraper;

import com.dayone.model.Company;
import com.dayone.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);

    ScrapedResult scrap(Company company);


}
