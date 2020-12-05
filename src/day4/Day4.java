package day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4
{
   public final static Pattern       ATTRIBUTE_PATTERN = Pattern.compile( "([a-z]+):([^ ]+)" );
   private List<String>              masInput;
   private List<Map<String, String>> masPassports;

   private List<String> masRequiredAttributes = Arrays.asList( "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"/*
                                                                                                               * , "cid"
                                                                                                               */ );

   private List<String> masEyecolors = Arrays.asList( "amb", "blu", "brn", "gry", "grn", "hzl", "oth" );

   public static void main( String[] args ) throws IOException
   {
      new Day4();
   }

   public void gatherPassports()
   {
      List<String> lasPassportLines = new ArrayList<String>();
      String lsCurrentPassport = "";
      for( String lsInput : masInput )
      {
         if( lsInput.length() == 0 )
         {
            lasPassportLines.add( lsCurrentPassport );
            lsCurrentPassport = "";
         }
         else
         {
            lsCurrentPassport += lsInput + " ";
         }
      }

      if( lsCurrentPassport.length() > 0 )
      {
         lasPassportLines.add( lsCurrentPassport );
      }

      masPassports = new ArrayList<>();
      for( String lsPass : lasPassportLines )
      {
         Matcher loMatcher = ATTRIBUTE_PATTERN.matcher( lsPass );
         Map<String, String> lcsAttributes = new HashMap<>();
         while( loMatcher.find() )
         {
            lcsAttributes.put( loMatcher.group( 1 ), loMatcher.group( 2 ) );
         }

         masPassports.add( lcsAttributes );
      }
   }

   public boolean validateNumber( String psInput, int piMin, int piMax )
   {
      try
      {
         int piValue = Integer.parseInt( psInput );
         if( piValue < piMin || piValue > piMax )
            return false;
      }
      catch( Exception e )
      {
         return false;
      }
      return true;
   }

   public boolean isValid( Map<String, String> pcsPassport )
   {

      for( String lsAttribute : masRequiredAttributes )
      {
         if( pcsPassport.containsKey( lsAttribute ) == false )
            return false;
      }

      if( validateNumber( pcsPassport.get( "byr" ), 1920, 2002 ) == false ||
         validateNumber( pcsPassport.get( "iyr" ), 2010, 2020 ) == false ||
         validateNumber( pcsPassport.get( "eyr" ), 2020, 2030 ) == false )
      {
         return false;
      }

      Matcher loMatcher = Pattern.compile( "([0-9]+)(cm|in)" ).matcher( pcsPassport.get( "hgt" ) );
      if( loMatcher.matches() == false )
      {
         // System.out.println( pcsPassport.get( "hgt" ) );
         return false;
      }
      else
      {
         if( "cm".equals( loMatcher.group( 2 ) ) )
         {
            if( validateNumber( loMatcher.group( 1 ), 150, 193 ) == false )
            {
               // System.out.println( "cm: " + pcsPassport.get( "hgt" ) );
               return false;
            }
         }
         else
         {
            // "in"
            if( validateNumber( loMatcher.group( 1 ), 59, 76 ) == false )
            {
               // System.out.println( "in: " + pcsPassport.get( "hgt" ) );
               return false;
            }
         }
      }

      if( masEyecolors.contains( pcsPassport.get( "ecl" ) ) == false )
      {
         // System.out.println( "eye: " + pcsPassport.get( "ecl" ) );
         return false;
      }

      if( pcsPassport.get( "hcl" ).matches( "#[0-9a-f]{6}" ) == false )
      {
         // System.out.println( pcsPassport.get( "hcl" ) );
         return false;
      }

      if( pcsPassport.get( "pid" ).matches( "[0-9]{9}" ) == false )
      {
         // System.out.println( pcsPassport.get( "pid" ) );
         return false;
      }

      return true;
   }

   public Day4() throws IOException {
      masInput = loadInput();

      // System.out.println( masInput );
      gatherPassports();
      // System.out.println( masPassports );
      System.out.println( "Valid: " + masPassports.stream().filter( this::isValid ).count() );
   }

   protected List<String> loadInput() throws IOException
   {
      BufferedReader loBR = new BufferedReader(
         new InputStreamReader( getClass().getResourceAsStream( "input.txt" ) ) );
      String lsLine;
      List<String> lasLines = new ArrayList<String>();
      while( ( lsLine = loBR.readLine() ) != null )
      {
         lasLines.add( lsLine.trim() );
      }
      loBR.close();

      return lasLines;
   }

}
