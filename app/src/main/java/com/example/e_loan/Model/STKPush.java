package com.example.e_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class STKPush{

    @SerializedName("BusinessShortCode")
    @Expose
    public String businessShortCode;
    @SerializedName("Password")
    @Expose
    public String password;
    @SerializedName("Timestamp")
    @Expose
    public String timestamp;
    @SerializedName("TransactionType")
    @Expose
    public String transactionType;
    @SerializedName("Amount")
    @Expose
    public String amount;
    @SerializedName("PartyA")
    @Expose
    public String partyA;
    @SerializedName("PartyB")
    @Expose
    public String partyB;
    @SerializedName("PhoneNumber")
    @Expose
    public String phoneNumber;
    @SerializedName("CallBackURL")
    @Expose
    public String callBackURL;
    @SerializedName("AccountReference")
    @Expose
    public String accountReference;
    @SerializedName("TransactionDesc")
    @Expose
    public String transactionDesc;
    private final static long serialVersionUID = -7288256062988125001L;

    public STKPush(String businessShortCode, String password, String timestamp, String transactionType,
                   String amount, String partyA, String partyB,String phoneNumber,
                   String callBackURL, String accountReference, String transactionDesc) {
        super();
        this.businessShortCode = businessShortCode;
        this.password = password;
        this.timestamp = timestamp;
        this.transactionType = transactionType;
        this.amount = amount;
        this.partyA = partyA;
        this.partyB = partyB;
        this.phoneNumber = phoneNumber;
        this.callBackURL = callBackURL;
        this.accountReference = accountReference;
        this.transactionDesc = transactionDesc;
    }

}