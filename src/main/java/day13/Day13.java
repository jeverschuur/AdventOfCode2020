package day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13
{
   private List<String> masInput;

   public static void main( String[] args ) throws IOException
   {
      new Day13();
   }

   public Day13() throws IOException {
      masInput = loadInput();

      int liTargetTime = Integer.parseInt( masInput.get( 0 ) );
      List<Integer> lasBuslines = Arrays.asList( masInput.get( 1 ).split( "," ) ).stream()
         .filter( lsLine -> lsLine.equals( "x" ) == false ).map( lsLine -> Integer.parseInt( lsLine ) )
         .collect( Collectors.toList() );

      solve1( lasBuslines, liTargetTime );

      lasBuslines = Arrays.asList( masInput.get( 1 ).split( "," ) ).stream()
         .map( lsLine -> lsLine.equals( "x" ) ? null : Integer.parseInt( lsLine ) )
         .collect( Collectors.toList() );
      // solve2( lasBuslines );

      long llStart = System.nanoTime();
      solve2( lasBuslines.toArray( new Integer[0] ) );
      long llEnd = System.nanoTime();
      System.out.println( "Finished in " + ( (double)( llEnd - llStart ) / 1000.0 ) + " us" );
   }

   public void solve1( List<Integer> paiBuslines, int piTargetTime )
   {
      for( Integer liLine : paiBuslines )
      {
         int liFirstDepartureAfter = findFirstDepartureAfter( liLine, piTargetTime );
         int liSolution = liLine * ( liFirstDepartureAfter - piTargetTime );

         System.out.println( String.format( "%d -> %d, solution: %d * %d = %d", liLine, liFirstDepartureAfter, liLine,
            ( liFirstDepartureAfter - piTargetTime ), liSolution ) );
      }
   }

   public long solve2( Integer[] paiBusLines )
   {
      long llTimestamp = paiBusLines[0];
      long llStep = paiBusLines[0];
      for( int i = 1; i < paiBusLines.length; i++ )
      {
         if( paiBusLines[i] == null )
            continue;

         while( ( llTimestamp + i ) % paiBusLines[i] != 0 )
         {
            llTimestamp += llStep;
         }

         llStep *= paiBusLines[i];
      }

      return llTimestamp;
   }

   public int findFirstDepartureAfter( int piBusLine, int piTargetTimestamp )
   {
      int liDeparture = 0;
      while( true )
      {
         if( liDeparture >= piTargetTimestamp )
         {
            return liDeparture;
         }
         liDeparture += piBusLine;
      }
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
