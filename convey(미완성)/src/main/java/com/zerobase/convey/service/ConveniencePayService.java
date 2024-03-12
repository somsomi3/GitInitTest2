package com.zerobase.convey.service;

import com.zerobase.convey.dto.PayRequest;
import com.zerobase.convey.dto.PayResponse;
import com.zerobase.convey.dto.PayResult;

public class ConveniencePayService {
    public PayResponse pay(PayRequest payRequest) {  //코드정리: command option L, control option o

        return new PayResponse(PayResult.SUCCESS, 100); // 이파일에 대한 test파일을 만들려면,
        // command shift t를 누르면 자동으로 test 파일(여기서는 ConveniencePayServiceTest.java파일)을 만들어줌.


    }

    public void payCancel() {


    }
}
