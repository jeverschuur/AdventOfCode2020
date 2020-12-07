package day7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7
{
   Pattern EMPTY_BAGS     = Pattern.compile( "(.+?) bags contain no other bags." );
   Pattern NON_EMPTY_BAGS = Pattern.compile( "(.+?) bags contain ([0-9]+ .+? bag[s]?(, ([0-9]+ .+ bag[s]?))*)." );

   Pattern BAG_WITH_COLOR = Pattern.compile( "([0-9]+) (.+)" );

   private List<String>               masInput;
   private Map<String, BagsWithColor> mcoBags = new LinkedHashMap<>();

   public static void main( String[] args ) throws IOException
   {
      new Day7();
   }

   public Day7() throws IOException {
      masInput = loadInput();
      parseContents();

      System.out.println( "solution 1: " + countBagsContaining( "shiny gold" ) );
      System.out.println( "solution 2: " + countAllBagsIn( "shiny gold" ) );
   }

   public boolean bagContainsColor( BagsWithColor poBag, String psColor, int piIndent )
   {
      if( poBag.msColor.equals( psColor ) )
         return true;

      if( poBag.maoContents == null )
         return false;

      for( BagsWithColor loBag : poBag.maoContents )
      {
         BagsWithColor loBagWithContents = mcoBags.get( loBag.msColor );
         if( bagContainsColor( loBagWithContents, psColor, piIndent + 1 ) )
         {
            return true;
         }
      }

      return false;
   }

   public int countBagsContaining( String psColor )
   {
      int liCount = 0;
      for( BagsWithColor loBag : mcoBags.values() )
      {
         if( loBag.msColor.equals( psColor ) == false && bagContainsColor( loBag, psColor, 0 ) )
         {
            liCount++;
         }
      }

      return liCount;
   }

   public long countAllBagsIn( String psColor )
   {
      BagsWithColor loOuter = mcoBags.get( psColor );
      long liCount = 0;

      if( loOuter.maoContents != null )
      {
         for( BagsWithColor loBag : loOuter.maoContents )
         {
            liCount += loBag.miCount * ( 1 + countAllBagsIn( loBag.msColor ) );
         }
      }

      return liCount;
   }

   private void parseContents()
   {
      for( String lsContents : masInput )
      {
         Matcher loEmpty = EMPTY_BAGS.matcher( lsContents );
         if( loEmpty.matches() )
         {
            String lsColor = loEmpty.group( 1 );
            mcoBags.put( lsColor, new BagsWithColor( lsColor, null ) );
         }
         else
         {
            Matcher loNonEmpty = NON_EMPTY_BAGS.matcher( lsContents );
            if( loNonEmpty.matches() )
            {
               String[] lasBags = loNonEmpty.group( 2 ).split( ", " );

               List<BagsWithColor> laoContents = new ArrayList<>();
               for( String lsBag : lasBags )
               {
                  Matcher loBagWC = BAG_WITH_COLOR.matcher( lsBag );
                  if( loBagWC.matches() == false )
                  {
                     throw new RuntimeException( "Could not parse: " + lsBag );
                  }

                  laoContents.add( new BagsWithColor( loBagWC.group( 2 ).replace( " bags", "" ).replace( " bag", "" ),
                     Integer.parseInt( loBagWC.group( 1 ) ) ) );
               }
               String lsColor = loNonEmpty.group( 1 );
               mcoBags.put( lsColor, new BagsWithColor( lsColor, null, laoContents ) );
            }
            else
            {
               throw new RuntimeException( "Could not parse: " + lsContents );
            }
         }
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
