package day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5
{
   private List<String> masInput;

   public static void main( String[] args ) throws IOException
   {
      new Day5();
   }

   public Day5() throws IOException {
      masInput = loadInput();

      solve();
   }

   public void solve()
   {
      Stream<Integer> loSeatIds = masInput.parallelStream().map( lsBoardingPassLocation ->
      {
         String lsRow = lsBoardingPassLocation.substring( 0, 7 ).replace( 'F', '0' ).replace( 'B', '1' );
         int liRow = Integer.parseInt( lsRow, 2 );

         String lsSeat = lsBoardingPassLocation.substring( 7 ).replace( 'L', '0' ).replace( 'R', '1' );
         int liSeat = Integer.parseInt( lsSeat, 2 );

         return liRow * 8 + liSeat;
      } ).sorted();

      List<Integer> lasSeatIds = loSeatIds.collect( Collectors.toList() );
      
      // Puzzle 1 (highest seat ID):
      System.out.println( "Max seatId: " + lasSeatIds.get( lasSeatIds.size() - 1 ) );

      // Puzzle 2 (the missing seatID):
      int liPrevSeatId = -1;
      for( int liSeatId : lasSeatIds )
      {
         if( liPrevSeatId >= 0 )
         {
            if( liPrevSeatId < liSeatId - 1 )
            {
               System.out.println( "Missing seatID: " + ( liSeatId - 1 ) );
            }
         }
         liPrevSeatId = liSeatId;
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
