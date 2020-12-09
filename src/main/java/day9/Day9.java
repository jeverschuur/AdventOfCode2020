package day9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day9
{

   private List<String> masInput;
   private Long[]       malNumbers;

   public static void main( String[] args ) throws IOException
   {
      new Day9();
   }

   public Day9() throws IOException {
      masInput = loadInput();

      malNumbers = masInput.stream().map( lsLine -> Long.valueOf( lsLine ) ).collect( Collectors.toList() )
         .toArray( new Long[0] );

      findError( 25 );
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

   public boolean findSum( Long[] palPreamble, Long plSum )
   {
      for( int i = 0; i < palPreamble.length - 1; i++ )
      {
         for( int j = i + 1; j < palPreamble.length; j++ )
         {
            if( palPreamble[j] + palPreamble[i] == plSum )
            {
               // System.out.println( "found sum: " + palPreamble[i] + " + " + palPreamble[j] +
               // " = " + plSum );
               return true;
            }
         }
      }

      return false;
   }

   public void findLargestContiguousSum( long plSum )
   {
      for( int i = 0; i < malNumbers.length - 1; i++ )
      {
         long llSum = 0;
         for( int j = i; j < malNumbers.length; j++ )
         {
            llSum += malNumbers[j];
            if( llSum == plSum && j > i )
            {
               List<Long> lalNumbersInSum = Arrays.asList( Arrays.copyOfRange( malNumbers, i, j + 1 ) );
               Collections.sort( lalNumbersInSum );
               System.out.println(
                  String.format( "Found contiguous %s to add up to %d", lalNumbersInSum, llSum ) );
               System.out.println(
                  "Sum of largest+smallest: "
                     + ( lalNumbersInSum.get( 0 ) + lalNumbersInSum.get( lalNumbersInSum.size() - 1 ) ) );
            }
         }
      }
   }

   public void findError( int piPreamble )
   {
      for( int i = piPreamble; i < malNumbers.length; i++ )
      {
         Long[] lalPreamble = Arrays.copyOfRange( malNumbers, i - piPreamble, i );
         if( !findSum( lalPreamble, malNumbers[i] ) )
         {
            System.out.println( "Incorrect sum: " + malNumbers[i] + " in " + Arrays.asList( lalPreamble ) );
            findLargestContiguousSum( malNumbers[i] );
            break;
         }
      }
   }
}
