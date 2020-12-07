package day7;

import java.util.List;

public class BagsWithColor
{
   public String              msColor;
   public Integer             miCount;
   public List<BagsWithColor> maoContents;

   public BagsWithColor( String psColor, Integer piCount ) {
      this( psColor, piCount, null );
   }

   public BagsWithColor( String psColor, Integer piCount, List<BagsWithColor> paoContents ) {
      this.msColor = psColor;
      this.miCount = piCount;
      this.maoContents = paoContents;
   }

   @Override
   public String toString()
   {
      return String.format( "{ %d x %s, bags inside: %s }", this.miCount, this.msColor, this.maoContents );
   }
}
