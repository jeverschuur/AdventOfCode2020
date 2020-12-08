package day8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import day8.Day8.Instruction.InstructionType;
import day8.Day8Parser.InputContext;
import day8.Day8Parser.InstructionContext;

public class Day8
{
   private Set<Integer> mcoVisited = new HashSet<Integer>();
   private InputContext moInput;

   int                       miCurrentInstruction = 0;
   private int               miAccumulator        = 0;
   private List<Instruction> maoCommands          = new ArrayList<>();

   public static class Instruction
   {
      public enum InstructionType {
         jmp, nop, acc
      };

      public InstructionType moInstruction;
      public int             miOffset;
   }

   public static void main( String[] args ) throws IOException
   {
      new Day8();
   }

   public Day8() throws IOException {
      Day8Lexer loLexer = new Day8Lexer( CharStreams.fromStream( getClass().getResourceAsStream( "input.txt" ) ) );
      CommonTokenStream loTokenStream = new CommonTokenStream( loLexer );
      Day8Parser loParser = new Day8Parser( loTokenStream );
      moInput = loParser.input();
      // System.out.println( moInput );
      // System.out.println( moInput.getChildCount() );
      // for( int i = 0; i < moInput.getChildCount(); i++ )
      // {
      // InstructionContext loInstruction = (InstructionContext)moInput.getChild( i );
      // System.out.println( loInstruction.action.getText() + " / " +
      // loInstruction.offset.getText() );
      // }

      for( int i = 0; i < moInput.getChildCount(); i++ )
      {
         InstructionContext loInstr = (InstructionContext)moInput.getChild( i );
         Instruction loInstruction = new Instruction();
         loInstruction.miOffset = Integer.parseInt( loInstr.offset.getText() );
         loInstruction.moInstruction = InstructionType.valueOf( loInstr.action.getText() );

         maoCommands.add( loInstruction );
      }

      try
      {
         execute( maoCommands );
      }
      catch( Exception e )
      {
         System.err.println( e.getMessage() );
      }
      System.out.println( miAccumulator );

      executeWithCorrection();

   }

   public void execute( List<Instruction> paoCommands )
   {
      mcoVisited.clear();
      miCurrentInstruction = 0;
      miAccumulator = 0;

      while( true )
      {
         if( mcoVisited.contains( miCurrentInstruction ) )
         {
            throw new RuntimeException( "Infinite loop detected" );
         }
         mcoVisited.add( miCurrentInstruction );

         Instruction loInstruction = paoCommands.get( miCurrentInstruction );

         switch( loInstruction.moInstruction )
         {
            case jmp:
               miCurrentInstruction += loInstruction.miOffset;
               if( miCurrentInstruction < 0 || miCurrentInstruction > moInput.getChildCount() - 1 )
               {
                  throw new RuntimeException( "Illegal jump to instruction " + miCurrentInstruction );
               }
               break;
            case acc:
               miAccumulator += loInstruction.miOffset;
            case nop:
               miCurrentInstruction++;
         }

         if( miCurrentInstruction >= paoCommands.size() - 1 )
         {
            // Stop at end of instructions:
            break;
         }
      }
   }

   public boolean testSolution( List<Instruction> paoCommands )
   {
      try
      {
         execute( paoCommands );
         System.out.println( "Solution, accumulator = " + miAccumulator );
      }
      catch( Exception e )
      {
         // incorrect, skip
         return false;
      }

      return true;
   }

   public void executeWithCorrection()
   {
      for( int i = 0; i < maoCommands.size(); i++ )
      {
         // if( testSolution( maoCommands ) )
         // return;

         switch( maoCommands.get( i ).moInstruction )
         {
            case jmp:

               maoCommands.get( i ).moInstruction = InstructionType.nop;
               if( testSolution( maoCommands ) )
               {
                  System.out.println( "Instruction " + i + " corrected" );
                  return;
               }

               maoCommands.get( i ).moInstruction = InstructionType.jmp;
               break;

            case nop:

               maoCommands.get( i ).moInstruction = InstructionType.jmp;
               if( testSolution( maoCommands ) )
               {
                  System.out.println( "Instruction " + i + " corrected" );
                  return;
               }
               maoCommands.get( i ).moInstruction = InstructionType.nop;
               break;

            case acc:
               break; // ignore these
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
