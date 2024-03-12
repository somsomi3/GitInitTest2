package com.zerobase.convey.service;

import com.zerobase.convey.dto.ConvenienceType;
import com.zerobase.convey.dto.PayRequest;
import com.zerobase.convey.dto.PayResponse;
import com.zerobase.convey.dto.PayResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConveniencePayServiceTest {
    ConveniencePayService conveniencePayService = new ConveniencePayService();


    @Test// IntelliJ IDEA에서 환경설정에서 라이브탬블릿을 자바 체크하고 내용적어서 자바 체크하고 적용저장하면
            //자동으로 아래 값을 불러온다.
    void pay_success() {
        //given
        PayRequest payRequest = new PayRequest(ConvenienceType.G25,100);


        //when
        PayResponse payResponse = conveniencePayService.pay(payRequest);//conveniencePayService.pay(payRequest);쓰고 option command v하면
//자동으로 응답으로 바뀐다.= 오른쪽마우스 클릭하면 repacter중에 있음

        //then
        assertEquals(PayResult.SUCCESS, payResponse.getPayResult());
        assertEquals(100, payResponse.getPaidAmount());
    }



}