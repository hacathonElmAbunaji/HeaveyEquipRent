package com.hacathon.heavyequipmentrent.utilis;

public class Utilities {

    public static String replaceArabicNumbers(String data){
        if (data == null){
            return "";
        }

        data = data.replace("١", "1");
        data = data.replace("٢", "2");
        data = data.replace("٣", "3");
        data = data.replace("٤", "4");
        data = data.replace("٥", "5");
        data = data.replace("٦", "6");
        data = data.replace("٧", "7");
        data = data.replace("٨", "8");
        data = data.replace("٩", "9");
        data = data.replace("٠", "0");

        return data;
    }


    public static String[] replaceArabicNumbersList(String[] data){

        for(int i = 0 ; i < data.length ; i++){
            data[i] = data[i].replace("١", "1");
            data[i] = data[i].replace("٢", "2");
            data[i] = data[i].replace("٣", "3");
            data[i] = data[i].replace("٤", "4");
            data[i] = data[i].replace("٥", "5");
            data[i] = data[i].replace("٦", "6");
            data[i] = data[i].replace("٧", "7");
            data[i] = data[i].replace("٨", "8");
            data[i] = data[i].replace("٩", "9");
            data[i] = data[i].replace("٠", "0");
        }

        return data;

    }

}
