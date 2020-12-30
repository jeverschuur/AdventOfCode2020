package day15;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day15
{
   private List<Integer>       maiInput;
   private LinkedList<Integer> maiNumbers;
   int                         miCount;

   private Map<Integer, RememberingInteger> maiValues;

   public static class RememberingInteger
   {
      int miValue;

      int miPosition         = -1;
      int miPreviousPosition = -1;

      public RememberingInteger( int piValue, int piCurrentPosition ) {
         this.miValue = piValue;
         this.miPosition = piCurrentPosition;
      }

      public void newPosition( int piNewPosition )
      {
         this.miPreviousPosition = miPosition;
         this.miPosition = piNewPosition;
      }

      public int getNewNumber()
      {
         return miPreviousPosition < 0 ? 0 : ( miPosition - miPreviousPosition );
      }

      @Override
      public String toString()
      {
         return String.format( "%d: { %d, %d }", miValue, miPosition, miPreviousPosition );
      }
   }

   public static void main( String[] args ) throws IOException
   {
      new Day15();
   }

   public Day15() throws IOException {

      maiInput = Arrays.asList( 6, 19, 0, 5, 7, 13, 1 );
      maiNumbers = new LinkedList<>( maiInput );
      solve1();

      System.out.println( "Last number: " + maiNumbers.getLast() );
      miCount = 0;
      maiValues = new HashMap<Integer, RememberingInteger>();
      long llStart = System.currentTimeMillis();
      solve2();
      System.out.println( "Time: " + ( System.currentTimeMillis() - llStart ) );
   }

   public void turn1()
   {
      int liLast = maiNumbers.removeLast(); // remove it so lastIndexOf below works as expected
      int liPreviousIndex = maiNumbers.lastIndexOf( liLast );
      maiNumbers.add( liLast ); // re-add the last number

      int liNewNumber = liPreviousIndex < 0 ? 0 : ( maiNumbers.size() - 1 - liPreviousIndex );
      maiNumbers.add( liNewNumber ); // add the new number
   }

   public void solve1()
   {
      while( maiNumbers.size() < 2020 )
      {
         turn1();
      }
   }

   public RememberingInteger addNumber( int piNum )
   {
      miCount++;
      RememberingInteger poValue = maiValues.get( piNum );
      if( poValue == null )
      {
         maiValues.put( piNum, ( poValue = new RememberingInteger( piNum, miCount ) ) );
      }
      else
      {
         poValue.newPosition( miCount );
      }

      return poValue;
   }

   public RememberingInteger turn2( RememberingInteger piLastSpoken )
   {
      return addNumber( piLastSpoken.getNewNumber() );
   }

   public void solve2()
   {
      maiValues = new HashMap<>();

      // addNumber( 0 );
      // addNumber( 3 );
      // RememberingInteger liLastSpoken = addNumber( 6 );
      addNumber( 6 );
      addNumber( 19 );
      addNumber( 0 );
      addNumber( 5 );
      addNumber( 7 );
      addNumber( 13 );
      RememberingInteger liLastSpoken = addNumber( 1 );

      while( miCount < 30000000 )
      {
         liLastSpoken = turn2( liLastSpoken );
      }

      System.out.println( liLastSpoken );
   }
}
