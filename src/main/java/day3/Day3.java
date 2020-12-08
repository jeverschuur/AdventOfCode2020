package day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day3
{
   private List<String> masInput;
   private boolean[][]  maabForest;

   public static void main( String[] args ) throws IOException
   {
      new Day3();
   }

   public Day3() throws IOException {
      masInput = loadInput();
      maabForest = new boolean[masInput.size()][];
      // ArrayList<boolean[]> laabForest = new ArrayList<>();
      for( int l = 0; l < masInput.size(); l++ )
      {
         String lsLine = masInput.get( l );
         boolean[] labRow = new boolean[lsLine.length()];
         for( int i = 0; i < lsLine.length(); i++ )
         {
            labRow[i] = lsLine.charAt( i ) == '#';
         }
         maabForest[l] = labRow;
      }

      System.out.println( "Answer1 (3x1): " + solve( 3, 1 ) );

      System.out.println( "Answer2 (1x1): " + solve( 1, 1 ) );
      System.out.println( "Answer2 (3x1): " + solve( 3, 1 ) );
      System.out.println( "Answer2 (5x1): " + solve( 5, 1 ) );
      System.out.println( "Answer2 (7x1): " + solve( 7, 1 ) );
      System.out.println( "Answer2 (1x2): " + solve( 1, 2 ) );

      long llTotal = solve( 1, 1 ) * solve( 3, 1 ) * solve( 5, 1 ) * solve( 7, 1 ) * solve( 1, 2 );
      System.out.println( "Answer2 total: " + llTotal );
   }

   public long solve( int piX, int piY ) throws IOException
   {
      int liX = 0;
      int liY = 0;
      int liTrees = 0;
      while( true )
      {
         liX += piX;
         liX %= maabForest[0].length;
         liY += piY;
         if( liY >= maabForest.length )
         {
            break;
         }
         if( maabForest[liY][liX] )
         {
            // System.out.println( String.format( "Hit on %d, %d", liX+1, liY+1 ) );
            liTrees++;
         }
      }

      return liTrees;
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
