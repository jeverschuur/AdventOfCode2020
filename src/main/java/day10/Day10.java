package day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10
{

   private List<String> masInput;
   private Integer[]    maiJolts;

   public static void main( String[] args ) throws IOException
   {
      new Day10();
   }

   public Day10() throws IOException {
      masInput = loadInput();

      masInput.add( 0, "0" );
      List<Integer> laiJolts = masInput.stream().map( lsLine -> Integer.valueOf( lsLine ) ).sorted()
         .collect( Collectors.toList() );
      laiJolts.add( laiJolts.get( laiJolts.size() - 1 ) + 3 );

      maiJolts = laiJolts.toArray( new Integer[laiJolts.size()] );

      List<Integer> laoDiffs = new ArrayList<Integer>();
      for( int i = 1; i < maiJolts.length; i++ )
      {
         laoDiffs.add( maiJolts[i] - maiJolts[i - 1] );
      }

      Map<Object, List<Integer>> laoGroups = laoDiffs.stream().collect( Collectors.groupingBy( Integer::valueOf ) );
      System.out.println( "number of 1 jolts diffs * number of 3 jolt diffs: "
         + ( laoGroups.get( Integer.valueOf( 1 ) ).size() * laoGroups.get( Integer.valueOf( 3 ) ).size() ) );

      long llStart = System.currentTimeMillis();
      long llAnswer = 0;
      for( int i = 0; i < 1; i++ )
      {
         mciArrangements.clear();
         llAnswer = findArrangementCount( maiJolts[0], 0 );
         // System.out.println( );
      }
      System.out.println( "Answer: " + llAnswer + " in " + ( System.currentTimeMillis() - llStart ) + " ms" );
   }

   Map<Integer, Long> mciArrangements = new HashMap<Integer, Long>();

   public long findArrangementCount( int liCurrentJolt, int piCurrentIndex )
   {
      if( piCurrentIndex >= maiJolts.length - 1 )
      {
         return 1;
      }

      long llTotal = 0;

      for( int i = piCurrentIndex + 1; i < maiJolts.length && maiJolts[i] <= liCurrentJolt + 3; i++ )
      {
         // Check to see if we have memoized this part of the proces before
         Long llArrangements = mciArrangements.get( maiJolts[i] );
         if( llArrangements == null )
         {
            // If not, calculate!
            llArrangements = findArrangementCount( maiJolts[i], i );
            mciArrangements.put( maiJolts[i], llArrangements );
         }

         llTotal += llArrangements;
      }

      return llTotal;
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
