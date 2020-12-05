package day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2
{
   public final static Pattern POLICY_PATTERN = Pattern.compile( "([0-9]+)\\-([0-9]+) ([a-z]): ([a-z]+)" );
   private List<String>        masInput;

   public static void main( String[] args ) throws IOException
   {
      new Day2();
   }

   public static class Check
   {
      private int    miLowerBound;
      private int    miUpperBound;
      private String msToMatch;
      private String msInput;
      private char   mcToMatch;

      public Check( String psPolicyAndPassword ) {
         Matcher loMatcher = POLICY_PATTERN.matcher( psPolicyAndPassword );
         if( loMatcher.matches() == false )
         {
            throw new RuntimeException( "Error parsing " + psPolicyAndPassword );
         }

         miLowerBound = Integer.parseInt( loMatcher.group( 1 ) );
         miUpperBound = Integer.parseInt( loMatcher.group( 2 ) );
         msToMatch = loMatcher.group( 3 );
         mcToMatch = msToMatch.charAt( 0 );
         msInput = loMatcher.group( 4 );
      }

      public boolean isValid()
      {
         String lsTemp = msInput.replace( msToMatch, "" );
         int liOccurrences = msInput.length() - lsTemp.length();
         boolean lbValid = liOccurrences >= miLowerBound && liOccurrences <= miUpperBound;
         // System.out
         // .println( String.format( "[%d]-[%d]-[%s]-[%s] => %s", miLowerBound,
         // miUpperBound, msToMatch, msInput,
         // lbValid ? "VALID" : "WRONG" ) );

         return lbValid;
      }

      public boolean isValid2()
      {
         boolean lbFirst = msInput.charAt( miLowerBound - 1 ) == mcToMatch;
         boolean lbSecond = msInput.charAt( miUpperBound - 1 ) == mcToMatch;

         boolean lbValid = ( lbFirst && !lbSecond ) || ( !lbFirst && lbSecond );

         // System.out
         // .println( String.format( "[%d]-[%d]-[%s]-[%s] => %s", miLowerBound,
         // miUpperBound, msToMatch, msInput,
         // lbValid ? "VALID" : "WRONG" ) );

         return lbValid;
      }
   }

   public Day2() throws IOException {
      masInput = loadInput();

      System.out.println( "Answer: " + solve() );
      System.out.println( "Answer2: " + solve2() );
   }

   public long solve() throws IOException
   {
      return masInput.stream().filter( lsLine ->
      {
         return new Check( lsLine ).isValid();
      } ).count();
   }

   public long solve2() throws IOException
   {
      return masInput.stream().filter( lsLine ->
      {
         return new Check( lsLine ).isValid2();
      } ).count();
   }

   protected List<String> loadInput() throws IOException
   {
      BufferedReader loBR = new BufferedReader(
         new InputStreamReader( getClass().getResourceAsStream( "input.txt" ) ) );
      String lsLine;
      List<String> lasLines = new ArrayList<String>();
      while( ( lsLine = loBR.readLine() ) != null )
      {
         if( lsLine.trim().length() > 0 )
         {
            lasLines.add( lsLine );
         }
      }
      loBR.close();

      return lasLines;
   }

}
