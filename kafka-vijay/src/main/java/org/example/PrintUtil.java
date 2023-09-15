package org.example;

public class PrintUtil {

    public static void print(String... args){
        String finalString="";
        for(String arg:args){
           finalString=finalString+arg+" ";
        }
        System.out.println(finalString.trim());

    }
}
