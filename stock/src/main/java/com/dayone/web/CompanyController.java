package com.dayone.web;

import com.dayone.model.Company;
import com.dayone.persist.entity.CompanyEntity;
import com.dayone.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword){
        return null;
    }
    //회사 리스트를 조회하는 api만들기
    @GetMapping("")
    public ResponseEntity<?> searchCompany(){
        List<CompanyEntity> companies= this.companyService.getAllCompany();
        return ResponseEntity.ok(companies);
    }


    @PostMapping("")
    public ResponseEntity<?> addCompany(@RequestBody Company request){
        String ticker = request.getTicker().trim();
        if (ObjectUtils.isEmpty((ticker))){
            throw new RuntimeException("ticker is empty");
        }
        Company company = this.companyService.save(ticker);
        return ResponseEntity.ok(company);
    }

    //회사를 삭제하는
    @DeleteMapping("")
    public ResponseEntity<?> deleteCompany(){
        return null;
    }
}
