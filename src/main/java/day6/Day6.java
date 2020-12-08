package day6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day6
{
   private List<String> masInput;
   private List<String> masGroupInputs;

   public static void main( String[] args ) throws IOException
   {
      new Day6();
   }

   public Day6() throws IOException {
      masInput = loadInput();
      masGroupInputs = groupByEmptyLine();

      System.out.println( "Solution part 1:" + solve() );
      System.out.println( "Solution part 2:" + solve2() );
   }

   public long solve()
   {
      long llSum = masGroupInputs.stream()
         .map( lsGroupAnswers -> (Long)( lsGroupAnswers.replace( " ", "" ).chars().distinct().count() ) )
         .collect( Collectors.summingLong( Long::longValue ) );

      return llSum;
   }

   public long solve2()
   {
      long llSum = masGroupInputs.stream().map( lsGroupAnswers ->
      {
         String lsSpacesRemoved = lsGroupAnswers.replace( " ", "" );
         int liPersonCount = lsGroupAnswers.length() - lsSpacesRemoved.length() + 1;

         return lsSpacesRemoved.chars().boxed()
            .collect( Collectors.groupingBy( Integer::intValue ) ).values().stream()
            .filter( laiValues -> laiValues.size() == liPersonCount ).count();

      } ).collect( Collectors.summingLong( Long::longValue ) );

      return llSum;
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

   public List<String> groupByEmptyLine()
   {
      List<String> lasGroupAnswers = new ArrayList<String>();
      String lsGroup = "";
      for( String lsInput : masInput )
      {
         if( lsInput.length() == 0 )
         {
            lasGroupAnswers.add( lsGroup.trim() );
            lsGroup = "";
         }
         else
         {
            lsGroup += lsInput + " ";
         }
      }

      if( lsGroup.length() > 0 )
      {
         lasGroupAnswers.add( lsGroup.trim() );
      }

      return lasGroupAnswers;
   }
}
