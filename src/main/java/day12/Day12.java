package day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day12
{
   private List<String> masInput;

   public static void main( String[] args ) throws IOException
   {
      new Day12();
   }

   public static Integer[] rowToSeats( String psRow )
   {
      return psRow.chars().boxed().map( liChar -> liChar == 76 ? 0 : null ).collect( Collectors.toList() )
         .toArray( new Integer[0] );
   }

   final static double DEG_TO_RAD = 180 / Math.PI;

   double mfDirection = 0 / Math.PI;
   int    miX         = 0;
   int    miY         = 0;

   public static class Vector
   {
      public int x;
      public int y;

      public Vector( int x, int y ) {
         this.x = x;
         this.y = y;
      }

      public void rotate( int piDeg )
      {
         double rad = piDeg / DEG_TO_RAD;
         double cs = Math.cos( rad );
         double sn = Math.sin( rad );

         int liNewX = (int)Math.round( (double)x * cs - (double)y * sn );
         y = (int)Math.round( (double)x * sn + (double)y * cs );
         x = liNewX;
      }

      public void translate( int x, int y )
      {
         this.x += x;
         this.y += y;
      }

      @Override
      public String toString()
      {
         return String.format( "{ %d, %d }", x, y );
      }
   }

   Vector moWaypoint = new Vector( 10, 1 );

   public Day12() throws IOException {
      masInput = loadInput();

      // Puzzle 1:
      masInput.forEach( lsAction ->
      {
         // System.out.println( lsAction + ", " + miX + ", " + miY + ", " + mfDirection
         // );
         int liNumber = Integer.parseInt( lsAction.substring( 1 ) );
         switch( lsAction.charAt( 0 ) )
         {
            case 'F':
               miX += Math.round( Math.cos( mfDirection ) * liNumber );
               miY -= Math.round( Math.sin( mfDirection ) * liNumber );
               break;
            case 'L':
               mfDirection -= ( liNumber / DEG_TO_RAD );
               break;
            case 'R':
               mfDirection += ( liNumber / DEG_TO_RAD );
               break;
            case 'N':
               miY += liNumber;
               break;
            case 'E':
               miX += liNumber;
               break;
            case 'S':
               miY -= liNumber;
               break;
            case 'W':
               miX -= liNumber;
               break;
         }
         //
         // System.out.println( "=> " + miX + ", " + miY + ", " + mfDirection );
      } );

      // System.out.println( miX + ", " + miY + ", " + mfDirection );

      System.out.println( "Manhattan distance: " + ( Math.abs( miX ) + Math.abs( miY ) ) );

      // Puzzle 2:
      miX = miY = 0;

      masInput.forEach( lsAction ->
      {
         // System.out.println( lsAction + ", " + miX + ", " + miY + ", " + mfDirection
         // );
         int liNumber = Integer.parseInt( lsAction.substring( 1 ) );
         switch( lsAction.charAt( 0 ) )
         {
            case 'F':
               miX += moWaypoint.x * liNumber;
               miY += moWaypoint.y * liNumber;
               break;
            case 'L':
               moWaypoint.rotate( liNumber );
               break;
            case 'R':
               moWaypoint.rotate( -liNumber );
               break;
            case 'N':
               moWaypoint.translate( 0, liNumber );
               break;
            case 'E':
               moWaypoint.translate( liNumber, 0 );
               break;
            case 'S':
               moWaypoint.translate( 0, -liNumber );
               break;
            case 'W':
               moWaypoint.translate( -liNumber, 0 );
               break;
         }
         //
         System.out
            .println( String.format( "Ship{x=%d, y=%d}\nWaypoint{x=%d, y=%d}", miX, miY, moWaypoint.x, moWaypoint.y ) );
      } );

      System.out.println( "Manhattan distance: " + ( Math.abs( miX ) + Math.abs( miY ) ) );

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
