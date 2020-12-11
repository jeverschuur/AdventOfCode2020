package day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11
{

   private List<String> masInput;
   private Integer[][]  maabSeats;

   public static void main( String[] args ) throws IOException
   {
      new Day11();
   }

   public static Integer[] rowToSeats( String psRow )
   {
      return psRow.chars().boxed().map( liChar -> liChar == 76 ? 0 : null ).collect( Collectors.toList() )
         .toArray( new Integer[0] );
   }

   public Day11() throws IOException {
      masInput = loadInput();

      maabSeats = new Integer[masInput.size()][];

      for( int i = 0; i < masInput.size(); i++ )
      {
         maabSeats[i] = rowToSeats( masInput.get( i ) );
      }

      // Puzzle 1:
      int liMigrations = 0;
      Migration loMig;
      while( ( loMig = migrate1() ).mbMutated )
      {
         // dump();
         liMigrations++;
      }
      System.out.println( "Migrations: " + liMigrations + ": " + loMig );

      // Puzzle 2:
      for( int i = 0; i < masInput.size(); i++ )
      {
         maabSeats[i] = rowToSeats( masInput.get( i ) );
      }

      liMigrations = 0;
      while( ( loMig = migrate2() ).mbMutated )
      {
         // dump();
         liMigrations++;
      }
      System.out.println( "Migrations: " + liMigrations + ": " + loMig );
   }

   public void dump()
   {
      System.out.println( "Dump:" );
      for( Integer[] labRow : maabSeats )
      {
         for( int i = 0; i < labRow.length; i++ )
         {
            System.out.print( labRow[i] == null ? "." : Integer.toString( labRow[i] ) );
         }
         System.out.println();
      }
   }

   public int isOccupied( int piRow, int piCol )
   {
      if( piRow < 0 || piRow >= maabSeats.length )
         return 0;
      if( piCol < 0 || piCol >= maabSeats[0].length )
         return 0;

      return maabSeats[piRow][piCol] == null ? 0 : maabSeats[piRow][piCol]; // empty spots or empty seats return 0
   }

   public int adjacentsOccupied( int piRow, int piCol )
   {
      // if( piRow == 0 && piCol == 3 )
      // {
      // System.out.println( isOccupied( piRow - 1, piCol - 1 ) );
      // System.out.println( isOccupied( piRow - 1, piCol ) );
      // System.out.println( isOccupied( piRow - 1, piCol + 1 ) );
      // System.out.println( isOccupied( piRow, piCol - 1 ) );
      // System.out.println( isOccupied( piRow, piCol + 1 ) );
      // System.out.println( isOccupied( piRow + 1, piCol - 1 ) );
      // System.out.println( isOccupied( piRow + 1, piCol ) );
      // System.out.println( isOccupied( piRow + 1, piCol + 1 ) );
      // }
      return isOccupied( piRow - 1, piCol - 1 ) + isOccupied( piRow - 1, piCol )
         + isOccupied( piRow - 1, piCol + 1 )
         + isOccupied( piRow, piCol - 1 ) + isOccupied( piRow, piCol + 1 ) +
         isOccupied( piRow + 1, piCol - 1 )
         + isOccupied( piRow + 1, piCol ) + isOccupied( piRow + 1, piCol + 1 );
   }

   public int isOccupied2( int piRow, int piCol, int piRowIncrease, int piColIncrease )
   {
      int liRow = piRow + piRowIncrease;
      int liCol = piCol + piColIncrease;
      while( liRow >= 0 && liRow < maabSeats.length && liCol >= 0 && liCol < maabSeats[0].length )
      {
         if( maabSeats[liRow][liCol] != null )
         {
            if( maabSeats[liRow][liCol] == 1 )
            {
               return 1;
            }
            else
            {
               return 0;
            }
         }

         liRow += piRowIncrease;
         liCol += piColIncrease;
      }

      return 0;
   }

   public int adjacentsOccupied2( int piRow, int piCol )
   {
      return isOccupied2( piRow, piCol, -1, -1 ) + isOccupied2( piRow, piCol, -1, 0 )
         + isOccupied2( piRow, piCol, -1, 1 )
         + isOccupied2( piRow, piCol, 0, -1 ) + isOccupied2( piRow, piCol, 0, 1 ) +
         isOccupied2( piRow, piCol, 1, -1 )
         + isOccupied2( piRow, piCol, 1, 0 ) + isOccupied2( piRow, piCol, 1, 1 );
   }

   public static class Migration
   {
      boolean mbMutated  = false;
      int     miOccupied = 0;

      @Override
      public String toString()
      {
         return String.format( "{mutated: %s, %d occupied}", String.valueOf( mbMutated ), miOccupied );
      }
   }

   public Migration migrate1()
   {
      Migration loMigration = new Migration();

      Integer[][] laabSeats = new Integer[maabSeats.length][];
      for( int i = 0; i < laabSeats.length; i++ )
      {
         laabSeats[i] = maabSeats[i].clone();
      }

      for( int i = 0; i < maabSeats.length; i++ )
      {
         for( int j = 0; j < maabSeats[i].length; j++ )
         {

            if( maabSeats[i][j] == null )
               continue;

            int liOccupiedAdjacent = adjacentsOccupied( i, j );

            // System.out.println( i + ", " + j + ": " + maabSeats[i][j] + " = " +
            // liOccupiedAdjacent );

            if( maabSeats[i][j] == 1 )
            {
               loMigration.miOccupied++;

               if( liOccupiedAdjacent >= 4 )
               {
                  laabSeats[i][j] = 0;
                  loMigration.mbMutated = true;
               }
            }
            else
            {
               if( liOccupiedAdjacent == 0 )
               {
                  laabSeats[i][j] = 1;
                  loMigration.mbMutated = true;
               }
            }
         }
      }

      maabSeats = laabSeats;

      return loMigration;
   }

   public Migration migrate2()
   {
      Migration loMigration = new Migration();

      Integer[][] laabSeats = new Integer[maabSeats.length][];
      for( int i = 0; i < laabSeats.length; i++ )
      {
         laabSeats[i] = maabSeats[i].clone();
      }

      for( int i = 0; i < maabSeats.length; i++ )
      {
         for( int j = 0; j < maabSeats[i].length; j++ )
         {

            if( maabSeats[i][j] == null )
               continue;

            int liOccupiedAdjacent = adjacentsOccupied2( i, j );

            // System.out.println( i + ", " + j + ": " + maabSeats[i][j] + " = " +
            // liOccupiedAdjacent );

            if( maabSeats[i][j] == 1 )
            {
               loMigration.miOccupied++;

               if( liOccupiedAdjacent >= 5 )
               {
                  laabSeats[i][j] = 0;
                  loMigration.mbMutated = true;
               }
            }
            else
            {
               if( liOccupiedAdjacent == 0 )
               {
                  laabSeats[i][j] = 1;
                  loMigration.mbMutated = true;
               }
            }
         }
      }

      maabSeats = laabSeats;

      return loMigration;
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
