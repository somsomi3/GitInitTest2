package com.dayone.service;

import com.dayone.model.Company;
import com.dayone.model.Dividend;
import com.dayone.model.ScrapedResult;
import com.dayone.persist.CompanyRepository;
import com.dayone.persist.DividendRepository;
import com.dayone.persist.entity.CompanyEntity;
import com.dayone.persist.entity.DividendEntity;
import com.dayone.scraper.Scraper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {

    private final Scraper yahooFinanceScraper;//스크래핑 코드를 사용하기 위해서.
    private final CompanyRepository companyRepository;//결과를 생성하기 위해
    private final DividendRepository dividendRepository;//결과를 생성하기 위해


    public Company save(String ticker){
        boolean exists = this.companyRepository.existsByTicker(ticker);//회사의 존재여부를 boolean값으로 받을수 있게됨.
        if(exists){//만약에 존재한다면, 에러를 발생시키도록 함.
            throw new RuntimeException("already exists ticker-> "+ ticker);
        }
        return this.storeCompanyAndDividend(ticker);//존재하지 않는 경우 이렇게 반환함.
    }

    public List<CompanyEntity> getAllCompany(){
        return this.companyRepository.findAll();
    }

    private Company storeCompanyAndDividend(String ticker){
        //ticker를 기준으로 회사를 스크래핑
        Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
        if(ObjectUtils.isEmpty(company)){
            throw new RuntimeException("failed to scrap ticker -> " + ticker);
        }

        //해당 회사가 존재할 경우, 회사의 배당금 정보를 스크래핑
        ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);


        //스크래핑 결과
        CompanyEntity companyEntity =this.companyRepository.save(new CompanyEntity(company));
        List<DividendEntity> dividendEntityList = scrapedResult.getDividends().stream()
                .map(e-> new DividendEntity(companyEntity.getId(), e))
                .collect(Collectors.toList());
        this.dividendRepository.saveAll(dividendEntityList);
        return null;
    }
}
