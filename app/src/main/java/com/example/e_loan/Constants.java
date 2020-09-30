package com.example.e_loan;

public class Constants {
    public static final int CONNECT_TIMEOUT = 60 * 1000;

    public static final int READ_TIMEOUT = 60 * 1000;

    public static final int WRITE_TIMEOUT = 60 * 1000;

    public static final String BASE_URL = "https://sandbox.safaricom.co.ke/";

    public static final String BUSINESS_SHORT_CODE = "222111";
    public static final String PASSKEY = "MjIyMTExYmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMjAwOTMwMTkzNzMw";
    public static final String TRANSACTION_TYPE = "CustomerPayBillOnline";
    public static final String PARTYB = "222111"; //same as business shortcode above
    public static final String CALLBACKURL = "http://mpesa-requestbin.herokuapp.com/1d8z2e71";

//values gotten from safaricom daraja api
}