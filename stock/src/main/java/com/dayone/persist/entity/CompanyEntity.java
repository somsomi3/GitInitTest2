package com.dayone.persist.entity;


import com.dayone.model.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity(name ="COMPANY")

@Getter
@ToString
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(unique =true)//중복이 되면 안되기때문에
    private String ticker;

    private String name;

    public  CompanyEntity(Company company){
        this.ticker = company.getTicker();
        this.name = company.getName();
    }

}
