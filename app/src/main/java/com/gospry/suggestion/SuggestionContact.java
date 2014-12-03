package com.gospry.suggestion;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.gospry.MeetMeApp;

import java.util.Locale;

/**
 * Created by Alex on 02.12.2014.
 */
public class SuggestionContact extends Suggestion {
    private long phonenumber;

    public SuggestionContact(String value, SuggestionTypes type, String phonenumber) {
        super(value, type);
        setPhonenumber(phonenumber);
    }

    public SuggestionContact(String value, SuggestionTypes type, long phonenumber) {
        super(value, type);
        this.phonenumber = phonenumber;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String number) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phonenumber = new Phonenumber.PhoneNumber();
        try {
            //TODO: Get the Right Locale for Austria/Germany, atm austria is hardcoded
            Locale current = MeetMeApp.getLocale();
            //phonenumber = phoneUtil.parse(number, current.getCountry());
            phonenumber = phoneUtil.parse(number, "AT");
            String countrycode = Integer.toString(phonenumber.getCountryCode());
            String nationalnumber = Long.toString(phonenumber.getNationalNumber());
            number = countrycode + nationalnumber;
            this.phonenumber = Long.parseLong(number);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
    }


}
