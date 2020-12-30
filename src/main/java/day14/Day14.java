package day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Member;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14
{
   private List<String> masInput;

   public static void main( String[] args ) throws IOException
   {
      new Day14();
   }

   Pattern            INSTRUCTION = Pattern.compile( "mem\\[([0-9]+)\\] = ([0-9]+)" );
   Map<Long, Long>    mlMem       = new TreeMap<>();
   private BitSet     moOr;
   private BitSet     moAnd;
   private BitSet     moFloating;
   private long       mlOr;
   private List<Long> malFloatingPermutation;

   public static BitSet bitsFromString( String psInput )
   {
      BitSet loBits = new BitSet( psInput.length() );
      for( int i = 0; i < psInput.length(); i++ )
      {
         char lcChar = psInput.charAt( psInput.length() - 1 - i );
         if( '1' == lcChar )
         {
            loBits.set( i );
         }
      }

      return loBits;
   }

   public void parseMaskInput( String psMask )
   {
      String[] lasMaskElements = psMask.split( " = " );
      String lsBitMask = lasMaskElements[1];
      // BitSet loAnd = BitSet.valueOf( )

      String lsAnd = lsBitMask.replace( 'X', '1' );
      String lsOr = lsBitMask.replace( 'X', '0' );
      moOr = bitsFromString( lsOr );
      moAnd = bitsFromString( lsAnd );
   }

   public void parseMaskInputV2( String psMask )
   {
      String[] lasMaskElements = psMask.split( " = " );
      String lsBitMask = lasMaskElements[1];
      // BitSet loAnd = BitSet.valueOf( )

      // String lsAnd = lsBitMask.replace( 'X', '1' ).replace( '0', '1' ); // leave
      // zeroes untouched
      String lsOr = lsBitMask.replace( 'X', '0' ); // overwrite all ones with one
      String lsFloating = lsBitMask.replace( '1', '0' ).replace( 'X', '1' ); // all 'X'-es are floating
      moOr = bitsFromString( lsOr );
      mlOr = Long.parseLong( lsOr, 2 );
      moAnd = null; // unused
      // moAnd = bitsFromString( lsAnd );
      moFloating = bitsFromString( lsFloating );
      moFloating.flip( 0, lsBitMask.length() );

      // System.out.println( "Floating: " + moFloating );

      malFloatingPermutation = new ArrayList<>();
      for( int i = 0; i < lsBitMask.length(); i++ )
      {
         if( moFloating.get( i ) == false )
         {
            long llValue = 1L << i;
            List<Long> lalExtra = new ArrayList<>();
            for( Long llCurrentValue : malFloatingPermutation )
            {
               lalExtra.add( llCurrentValue + llValue );
            }
            malFloatingPermutation.add( llValue );
            malFloatingPermutation.addAll( lalExtra );
         }
      }
   }

   public Day14() throws IOException {
      masInput = loadInput();

      // solve1();

      solve2();
   }

   public void solve1()
   {
      for( String lsInput : masInput )
      {
         if( lsInput.startsWith( "mask =" ) )
         {
            System.out.println( "Changing mask: " + lsInput );
            parseMaskInput( lsInput );
         }
         else
         {
            Matcher loInstr = INSTRUCTION.matcher( lsInput );
            if( loInstr.find() )
            {
               int liMemPos = Integer.parseInt( loInstr.group( 1 ) );
               long llValue = Long.valueOf( loInstr.group( 2 ) );
               BitSet loValue = BitSet.valueOf( new long[] { llValue } );
               loValue.and( moAnd );
               loValue.or( moOr );

               System.out.print( "mem[" + liMemPos + "]: " );
               long[] llValues = loValue.toLongArray();
               for( long llPart : llValues )
               {
                  System.out.print( llPart );
               }
               System.out.println();

               mlMem.put( (long)liMemPos, llValues[0] );
            }
            else
            {
               System.err.println( "!!!! parse error" );
            }
         }
      }

      BigInteger loSum = BigInteger.valueOf( 0L );
      for( long llValue : mlMem.values() )
      {
         loSum = loSum.add( BigInteger.valueOf( llValue ) );
      }

      System.out.println( loSum );
   }

   public void solve2()
   {
      mlMem.clear();
      for( String lsInput : masInput )
      {
         if( lsInput.startsWith( "mask =" ) )
         {
            System.out.println( "Changing mask: " + lsInput );
            parseMaskInputV2( lsInput );
         }
         else
         {
            Matcher loInstr = INSTRUCTION.matcher( lsInput );
            if( loInstr.find() )
            {
               int liMemPos = Integer.parseInt( loInstr.group( 1 ) );
               long llValue = Long.valueOf( loInstr.group( 2 ) );

               BitSet loValue = BitSet.valueOf( new long[] { liMemPos } );
               // loValue.and( moAnd );
               loValue.or( moOr );
               loValue.and( moFloating );

               long[] llValues = loValue.toLongArray();
               long llMempos = llValues.length == 0 ? 0 : llValues[0];

//               System.out.println( "mem[" + llMempos + "]: " + llValue );
               mlMem.put( llMempos, llValue );
               for( Long llPermutation : malFloatingPermutation )
               {
                  long llFloatingPos = llMempos + llPermutation;
//                  System.out.println( "mem[" + llFloatingPos + "]: " + llValue );
                  if( mlMem.put( llFloatingPos, llValue ) != null )
                  {
//                     System.out.println( "!!!!duplicate" );
                  }
               }
            }
            else
            {
               System.err.println( "!!!! parse error" );
            }
         }
      }

      System.out.println( mlMem.size() );
      BigInteger loSum = BigInteger.valueOf( 0L );
      for( long llValue : mlMem.values() )
      {
         loSum = loSum.add( BigInteger.valueOf( llValue ) );
      }

      System.out.println( loSum );
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
