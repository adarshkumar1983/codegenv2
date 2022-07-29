package com.sakhi.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


// include the code generator class in the package
// enhance the main function of app to recieve parameters from command line
//  java -cp ".\target\codegenerator-1.0-SNAPSHOT.jar;C:\Users\Gautam\.m2\repository\commons-cli\commons-cli\1.5.0\commons-cli-1.5.0.jar"
//  com.techbridge.gautam.App -f myfunc -r int -p "int,p1,double,p2,boolean,p3"
//  com.techbridge.gautam.App -b filename
// no params, ask user to provide input

public class App {
  

   public static void main(String[] args) throws ParseException {
      //***Definition Stage***
      // create Options object
      Options options = new Options();
      
      // add option "-f"
      options.addOption("f", true, "funtion signature");
      // add option "-r"
      options.addOption("r", true, " create_function_body");
      // add option "-p"
      options.addOption("p", true, "create_test_function_signatur");
      

      //***Parsing Stage***
      //Create a parser
      CommandLineParser parser = new DefaultParser();

      //parse the options passed as command line arguments
      CommandLine cmd = parser.parse( options, args);

      String fname="";
      String rettype="";
      String params="";
      //***Interrogation Stage***
      //hasOptions checks if option is present or not
      if(cmd.hasOption("f")) {
         fname = cmd.getOptionValue("f");
         System.out.println(" funtion name: " + cmd.getOptionValue("f"));
      } 
      
      if(cmd.hasOption("r")) {
         rettype = cmd.getOptionValue("r");
         System.out.println("function return type: " + cmd.getOptionValue("r"));
      } else 
      {
         rettype = "int";
      }

      if(cmd.hasOption("p")) {
        params = cmd.getOptionValue("p");
         System.out.println("function parameters: " + cmd.getOptionValue("p"));
      } else 
      {
         params = "int,p1";
      }

      CodeGenerator cgen = new CodeGenerator();
      String argsa[] =  params.split(",");
     cgen.addFunction(fname, rettype, argsa);
     cgen.GenCode();
     System.out.println(cgen.getCode());     

   }

// how to split a string to create a string array

/* 
      CodeGenerator cgen = new CodeGenerator();
       String argsa[] =  {"int" ,"p1" ,"double" ,"p2" ,"boolean" ,"p3"};
      cgen.addFunction("gautam", "int", argsa);
      cgen.GenCode();
      System.out.println(cgen.getCode());     
      */      
}

