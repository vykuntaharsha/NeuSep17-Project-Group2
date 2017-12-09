package com.neuSep17.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;  
import java.io.File;   
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.util.Scanner;

public class Login {  
    static Scanner scanner = new Scanner(System.in);  
    static File recordFile = new File("D:/HAH.txt");
    
    
    public static void main(String[] args) throws IOException {  
           // test code
           /*System.out.println("Choose:Login（A）or Register（B）");   
            String opt = scanner.next();  
            if("A".equalsIgnoreCase(opt)){  
               // show();  
                login();  
            }else if("B".equalsIgnoreCase(opt)){  
                register();  
            }else{  
                System.out.println("Error. Please choose again.");  
            }  
         */
          
    }  
    public static void register() throws IOException {  
        //File recordFile = null;  
        while(true){  
            System.out.println("Account：");  
            String userName = scanner.next();  
            recordFile = new File("D:/HAH.txt");  
            FileReader fileReader = new FileReader(recordFile);  
            BufferedReader bufferedReader = new BufferedReader(fileReader);  
            String line = null;  
            boolean flag = false;   
            while((line = bufferedReader.readLine()) != null){  
                String[] split = line.split("\t");  
                if(userName.equals(split[0])){
                    flag = true;  
                    System.out.println("The account has existed. Please try again.");  
                    register();
                    break;  
                }  
            }  
            if(flag){
                break;  
            }  
            System.out.println("Password：");  
            String passWord = scanner.next();  
              
            FileWriter fileWriter = new FileWriter(recordFile, true);  
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);  
            bufferedWriter.write(userName + "\t" + passWord);   
            bufferedWriter.newLine();  
            System.out.println("Congratulations  " + userName + "  Login sucessful");  
            bufferedWriter.close();  
            bufferedReader.close();  
            break;  
        }  
    }  
   
      
    public static void login() throws IOException {  
          
        FileReader fileReader = new FileReader(recordFile);  
        BufferedReader bufferedReader = new BufferedReader(fileReader);   
        System.out.println("Account：");  
        String userName = scanner.next();  
        System.out.println("Password：");  
        String passWord = scanner.next();  
        String line = null;  
        boolean flag = false; 
        while((line = bufferedReader.readLine()) != null){  
            String[] split = line.split("\t");  
            if(userName.equals(split[0]) && passWord.equals(split[1])){  
                flag = true;  
                break;  
            }  
        }  
        if(flag){  
            System.out.println("Congratulations  " + userName + "  Login successful!");  
        }else{  
            System.out.println("Invalid username or password.");  
            login();
        }  
          
        bufferedReader.close();  
    }
    
}  
