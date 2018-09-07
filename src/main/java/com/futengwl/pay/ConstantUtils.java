package com.futengwl.pay;


public interface ConstantUtils {


    public static final String SUCCESS = "SUCCESS";

    enum ORDER_SRC {
        WEIXIN(0),
        APP(1);
        int value;
        ORDER_SRC(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

}
