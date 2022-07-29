package com.sakhi.utils;
import java.util.HashMap;
import java.util.*;
class CodeGenerator
{
    String fname;
    String rettype;
    String params[];
    String code;
    String retval;
    void addFunction(String fname, String rettype, String params[]) 
    {
        this.fname = fname;
        this.rettype = rettype;
        this.params = params;
    } 

    /*
     * for a specific type, we need to find the default value
     * if the type name is int, we want "1"
     * if type is double, we want "1.0"
     * if type is boolean, we want "true"
     * if type is string we want ""
     */
    private String get_default_value_for_type( String typename) 
    {
        HashMap<String, String> type_defaults = new HashMap<>();
        // add elements to hashmap
        type_defaults.put("int", "1");
        type_defaults.put("double", "1.0");
        type_defaults.put("boolean", "true");
        type_defaults.put("String", "");

        return type_defaults.get(typename);
    }

    private void create_function_signature()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(code)
        .append("\npublic static ")
        .append(rettype)
        .append(" ")
        .append(fname)
        .append("(");

        for( int i = 0 ; i < this.params.length ; i+=2)
        {
            if ( i > 0)
                sb.append(", ");
            sb.append(this.params[i]).append(" ").append(this.params[i+1]);
        }
        sb.append(")\n");
        code = sb.toString();
    }

    private void create_function_body()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(code)
        .append("{\n\t");
        sb.append(rettype);
        sb.append(" result = ");
        sb.append(get_default_value_for_type(rettype));
        sb.append(";\n\t");
        sb.append("return result;\n}\n");
        code = sb.toString();
    }

    private void create_test_function_signature()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(code)
        .append("\npublic static void test_");
        sb.append(fname);
        sb.append("(");
        for( int i = 0 ; i < this.params.length ; i+=2)
        {
            if ( i > 0)
                sb.append(", ");
            sb.append(this.params[i]).append(" ").append(this.params[i+1]);
        }
        sb.append(", ");
        sb.append(rettype);
        sb.append(" expected");
        sb.append(")\n");
        code = sb.toString();
    }

    private void create_test_function_body()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(code)
        .append("{\n\t");
        sb.append(rettype);
        sb.append(" actual = ");
        sb.append(fname);
        sb.append("(");
        for( int i = 0 ; i < this.params.length ; i+=2)
        {
            if ( i > 0)
                sb.append(", ");
            sb.append(this.params[i]).append(" ").append(this.params[i+1]);
        }
        sb.append(")\n\t");
        sb.append("if (actual == expected)\n\t{ \n");
        sb.append("\t\tSystem.out.println(\"Pass: \"");

        for ( int i = 0 ; i < params.length ; i++)
        {
            String pname = params[i];
            sb.append(" + " + " \"" + pname + "=>\" + " +  pname);
        }
        sb.append("+ \"expected=>\" + expected + \" actual=> \" + actual);\n") ;
        sb.append("\t}\n");
        sb.append("\telse\n\t{\n");
        sb.append("\t\tSystem.out.println(\"Fail: \"");

        for ( int i = 0 ; i < params.length ; i++)
        {
            String pname = params[i];
            sb.append(" + " + " \"" + pname + "=>\" + " +  pname);
        }
        sb.append("+ \"expected=>\" + expected + \" actual=> \" + actual);\n") ;
        sb.append("\t}\n}\n");
        code = sb.toString();
    }

    private void create_test_suite_function()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(code)
        .append("public static void suite_test_");
        sb.append(fname);
        sb.append("()\n{");
        code = sb.toString();
    }

    private void create_test_suite_body()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(code)
        .append("\n");
        String fcall_def_val = "";
        for ( int i = 0 ; i < params.length ; i+=2)
        {
            String defval = get_default_value_for_type(params[i]);
            if ( i > 0)
                fcall_def_val += ", ";
            
            fcall_def_val += defval;
        }
        sb.append("\ttest_" +fname+ "(" + fcall_def_val + ", " + get_default_value_for_type(rettype) + ")") ;
        sb.append("\n}");
        code = sb.toString();
    }
    /*
    islegalcode: return status true or false
    Hashmap
    .put("main", true)
    .put("package", true)

    if this key exists, than this is not a legal name
     */
    void GenCode()
    {
        code = "";
        //functioncall islegalcode
        //if false return : msg and exit
        create_function_signature();
        create_function_body();
        create_test_function_signature();
        create_test_function_body();
        create_test_suite_function();
        create_test_suite_body();
    }
    String getCode()
    {

        return code;
    }
}

class CodeGenParams
{
    String fname;
    String rettype;
    String params[];

    public String getfname()
    {
        return fname;
    }
    public String getrettype()
    {
        return rettype;
    }
    public String[] getparams()
    {
        return params;
    }
  
    CodeGenParams()
    {
    }

    public void GetParmsFromFile(String filename)
    {

    }
    public void GetUserInput()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the name of function: ");
        fname = scan.nextLine();
        System.out.print("What type of value does it return (int/double/string/boolean): ");
        rettype = scan.nextLine().trim().toLowerCase();

        System.out.print("How many parameters does it need: " );    
        int param_count = scan.nextInt();
        scan.nextLine();
        params = new String[param_count*2];
        for ( int i = 0 ; i < param_count*2 ; i += 2)
        {
            System.out.print("Give the parameter type: " );
            params[i] = scan.nextLine().trim().toLowerCase();
            System.out.print("Give the parameter name: " );
            params[i+1] =scan.nextLine().trim();
            
        }
        System.out.print("********************************************************************");
        scan.close();

    }

}

      /*   System.out.print("Enter the name of function: ");
        func_name = scan.nextLine();
        System.out.print("What type of value does it return (int/double/string/boolean): ");
        type = scan.nextLine();
        System.out.print("How many parameters does it need: " );    
        param_count = scan.nextInt();
        System.out.print("Give the parameter 1 type: " );
        type1 = scan.nextInt();
        System.out.print("Give the parameter 1 name: " );
        name1 = scan.nextInt();
        System.out.print("Give the parameter 2 type: " );
        type2 = scan.nextInt();
        System.out.print("Give the parameter 2 name: " );
        name2 = scan.nextInt(); */


        // user input
        // user input to be recieved from file
        // user input to be recieved as command line arguments
        // user input from command line using named parameters
        // integrate with springboot to create a REST API
        // create a HTML page to recieve input, and reply back with the data and show on web page

    
  class dummy {

        public static void main(String[] args)
        {
            
            Scanner scan = new Scanner(System.in);
            CodeGenerator cgen = new CodeGenerator();
            //CodeGenParams params = new CodeGenParams();
            //params.GetUserInput();
            //params.GetParmsFromFile("testdata.txt");
            //cgen.addFunction(params.getfname() , params.getrettype() , params.getparams());
            //  String  args[] = {"myfunc" ,"int" ,"3" ,"int" ,"p1" ,"double" ,"p2" ,"boolean" ,"p3"}
            // argcount
            //String[]  paramlist = {}
             String argsa[] =  {"myfunc" ,"int" ,"3" ,"int" ,"p1" ,"double" ,"p2" ,"boolean" ,"p3"};
             args = argsa;
            if ( args.length >= 5 )
            {
                // fname rettype argcount int p1 double p2
                int argcount = Integer.parseInt(args[2]);
                String[] paramlist = new String[argcount*2];

                for( int i = 3 ; i < args.length ; i++)
                {
                    paramlist[i-3] = args[i];
                }
                cgen.addFunction(args[0] , args[1], paramlist);
                
            } 
            else if (args.length == 1)
            {
                // read the input from file specified by argv[0]
            }
            else 
            {
                CodeGenParams params = new CodeGenParams();
                params.GetUserInput();
                cgen.addFunction(params.getfname() , params.getrettype() , params.getparams());
            }
    
            cgen.GenCode();
            System.out.println(cgen.getCode());
            scan.close();
        }
}