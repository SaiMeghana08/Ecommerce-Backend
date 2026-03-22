package com.JWT.demo.Model;

import lombok.Data;
@Data
public class TransactionDetails {

        private String orderId;
        private String currency;
        private Integer amount;
        private String key;

}
