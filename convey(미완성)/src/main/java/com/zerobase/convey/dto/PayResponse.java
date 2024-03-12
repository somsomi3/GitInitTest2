package com.zerobase.convey.dto;

public class PayResponse {
    // 결제 결과
    PayResult  payResult;// option Enter을 쳐서 열거형(erum)을 친다.

    // 결제 성공 금액
    Integer paidAmount;


    //생성자도 만들어라(역시나 command n을 눌러서 맨위 Constructor)
    public PayResponse(PayResult payResult, Integer paidAmount) {
        this.payResult = payResult;
        this.paidAmount = paidAmount;
    }


    //개발자들이 알아볼 수 있기 때문에 command n을 눌러서, Getter Setter을 만들어 두는게 좋고,

    public PayResult getPayResult() {
        return payResult;
    }

    public void setPayResult(PayResult payResult) {
        this.payResult = payResult;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount = paidAmount;
    }
}
