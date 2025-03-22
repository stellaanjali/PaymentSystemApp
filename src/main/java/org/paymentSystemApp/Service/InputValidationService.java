package org.paymentSystemApp.Service;

import org.paymentSystemApp.Exceptions.InputValidationException;

import static org.paymentSystemApp.Config.ShortenSOPln.print;

public class InputValidationService {

    public static void validatePhoneNumber(String phoneNumber) throws InputValidationException {
        int len = phoneNumber.length();
        if(len == 10){
            for(int i=0; i<len; i++){
                if(!(phoneNumber.charAt(i) >= '0' && phoneNumber.charAt(i)<='9')){
                    throw new InputValidationException("Phone Number does not contain all digits");
                }
            }
        }
        else{
            throw new InputValidationException("Phone Number is less than 10 digits");
        }

        }

    }


