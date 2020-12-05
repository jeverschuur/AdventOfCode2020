package day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day1
{

   public static void main( String[] args ) throws IOException
   {
      new Day1();
   }

   public Day1() throws IOException {
      System.out.println( "Answer: " + solve( 2020 ) );
   }

   public int solve( int piSum ) throws IOException
   {
      List<Integer> lasInputValues = loadInput().stream().map( lsLine -> Integer.parseInt( lsLine ) )
         .collect( Collectors.toList() );

      for( int i = 0; i < lasInputValues.size() - 2; i++ )
      {
         int liFirst = lasInputValues.get( i );
         for( int j = i + 1; j < lasInputValues.size() - 1; j++ )
         {
            int liSecond = lasInputValues.get( j );

            for( int k = j + 1; k < lasInputValues.size(); k++ )
            {

               int liThird = lasInputValues.get( k );

               if( liFirst + liSecond + liThird == piSum )
               {
                  System.out.println(
                     String.format( "%d + %d + %d = %d and %d * %d * %d = %d", liFirst, liSecond, liThird, piSum,
                        liFirst,
                        liSecond, liThird, liFirst * liSecond * liThird ) );
                  return liFirst * liSecond * liThird;
               }
            }
         }
      }

      return -1;
   }

   protected List<String> loadInput() throws IOException
   {
      BufferedReader loBR = new BufferedReader(
         new InputStreamReader( getClass().getResourceAsStream( "input.txt" ) ) );
      String lsLine;
      List<String> lasLines = new ArrayList<String>();
      while( ( lsLine = loBR.readLine() ) != null )
      {
         if( lsLine.length() > 0 )
         {
            lasLines.add( lsLine );
         }
      }
      loBR.close();

      return lasLines;
   }

}
