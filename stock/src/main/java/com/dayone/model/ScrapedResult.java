package com.dayone.model;
//스크래핑한 결과를 주고받기 위한 클래스

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ScrapedResult {

    private Company company;
    private List<Dividend> dividends;
    public ScrapedResult() {
        this.dividends = new ArrayList<>();
    }
    public void setDividendEntities(List<Dividend> dividends) {
    }
}




