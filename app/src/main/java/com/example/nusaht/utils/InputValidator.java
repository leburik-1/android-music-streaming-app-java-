package com.example.nusaht.utils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.regex.*;

public class InputValidator {
   private String regex;
   private String candidate;
   private String inpuType;
   private String message;
   private Pattern pattern;
   private Matcher matcher;
   private boolean retval;
   private static final Logger log = Logger.getLogger(InputValidator.class.getName());
//   public InputValidator(String regex,String candidate)
//   {
//      this(regex,candidate,"");
//   }
   public InputValidator() {}

   public InputValidator(String rgex,String input)
   {
      if (!setRegex(rgex))
          throw new IllegalArgumentException("Invalid Regular expression submitted.");
      if (!setCandidate(input))
         throw new IllegalArgumentException("Invalid input detected.");
   }

   protected boolean setRegex(String regex)
   {
      boolean retval  = false;
      if (regex == null || regex.length() < 1)
      {
         log.info("Error in regular expression");
         throw new IllegalArgumentException("Regular expression pattern must be a valid string with more than 1 character");
      } else {
         this.regex = regex;
         retval = true;
      }
      return retval;
   }

   protected String getRegex(){ return regex; }

   protected boolean setCandidate(String candidate)
   {
      boolean retval  = false;
      if (candidate == null || candidate.isEmpty())
      {
         log.info("Candiadate Error in regular exp");
         System.err.println("Error in Candidate string");
      }
      else {
         this.candidate = candidate;
         retval = true;
      }
      return retval;
   }

   protected String getCandidate(){ return candidate; }

   public boolean validateCandidate()
   {
      boolean retva = false;
      retva = candidate.matches(regex);
      message = String.format("NO MATCH %nPATTERN => %s %nREG-EXP => %s%n",candidate,regex);

      if (retva){
         message = String.format("MATCH %nPATTERN => %s %nREG-EXP => %s%n",candidate,regex);
      }
      System.out.printf("%n%s%n",message);
      return retva;
   }

   public boolean isPasswordValid(String pass) {
      return pass != null && pass.length() >= 8;
   }

   public static boolean validateMe(String pattern,String value) {return value.matches(pattern);}

   public static boolean isNotEmpytNull(String value){return value != null && value.length() >= 0;};


///  public boolean validate()
//   {
//      retval = false;
//      try
//      {
//         pattern = Pattern.compile(candidate);
//      } catch (PatternSyntaxException ex)
//      {
//       error = new StringBuilder("There is a problem with the regular expression!\n" +
//                                 "Pattern : "+ex.getPattern() +"\nDescription : "+ex.getDescription() +
//                                 "\nMessage : "+ex.getMessage() + "\nIndex : " + ex.getIndex());
//       log.severe("error");
//      }
//      return retval;
//   }

//   public void setError(StringBuilder error)
//   {
//      this.error = error;
//   }
//
//   public StringBuilder getError()
//   {
//      return this.error;
//   }

}