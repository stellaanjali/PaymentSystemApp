package org.paymentSystemApp.Service;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service

public class TransactionIdGenerationService {

    public TransactionIdGenerationService(){

    }

    public String generateTransactionId(String vpaId){
        String result = first8Digits();
        result += random4Digit();
        int len = vpaId.length();
        int found = vpaId.indexOf('@');
        String requiredString = "";
        for(int idx = found-1; idx > found-5; idx--){
            requiredString += vpaId.charAt(idx);
        }
        result += last4Digits(requiredString);
        return result;
    }

    private String first8Digits(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = now.format(formatter);
        return formattedDate;
    }

    private String random4Digit(){
        Random randomNumber = new Random();
        Integer first4Digits = randomNumber.nextInt(10000);
        String outputString = first4Digits.toString();
        int len2 = outputString.length();
        int rem = 4 - len2;
        String finalResult = padZero(outputString,rem);
        return finalResult;
    }


    private String polynomialHash(String inputString){
        int len = inputString.length();
        int primeNumber= 7;
        int hashedOutput = 0;
        int m = 10000;

        String outputString="";
        for(int i=0; i<len; i++){
            hashedOutput = (hashedOutput % m + (inputString.charAt(i)*(int)(Math.pow(primeNumber,i))) % m) % m;
        }
        Integer result = hashedOutput;
        outputString = result.toString();
        int len2 = outputString.length();
        int rem = 4 - len2;
        String finalResult = padZero(outputString,rem);
        return finalResult;
   }

   private String last4Digits(String input){
        String output = polynomialHash(input);
        return output;
   }

   private String padZero(String input, int digits){
        for(int i=0; i<digits; i++){
            input = "0" + input;
        }
        return input;
   }


}
