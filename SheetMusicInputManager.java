import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;



public class SheetMusicInputManager
{
   /**
       * A class for responding to mouse clicks on the drawing panel.
       */
       
   /** Variable that when set to true to catch and print any exceptions that occur */
   private static final boolean CATCH_EXCEPTIONS = true;
   
   
   public static class SheetMouseListener extends MouseInputAdapter 
   {
      private DrawingPanel panel;
      private SheetMusic sm;
       
      public SheetMouseListener(DrawingPanel panel, SheetMusic sm) 
      {
         this.panel = panel;
         this.sm = sm;
      }
       
      public void mousePressed(MouseEvent event) 
      {
         int x = event.getX() / panel.getZoom();
         int y = event.getY() / panel.getZoom();
         Graphics g = panel.getGraphics();
         try {
               this.sm.interfacePrintNote(panel, g, sm.V0, Color.RED);
               this.sm.interfacePrintNote(panel, g, sm.V0, Color.RED);
               this.sm.interfacePrintNote(panel, g, sm.V0, Color.RED);
         } 
         catch (RuntimeException e) 
         {
            if (CATCH_EXCEPTIONS) 
            {
               e.printStackTrace(System.err);
            } 
            else 
            {
               throw e;
            }
         }
      }
   }
}   
   
    /** A class for responding to key presses on the drawing panel.
     */
  public static class SheetKeyListener extends KeyAdapter 
   {
      private DrawingPanel panel;
       
      public SheetKeyListener(DrawingPanel panel) 
      {
         this.panel = panel;
      }
       
      public void keyPressed(KeyEvent event) 
      {
         int code = event.getKeyCode();
         if (code == KeyEvent.VK_N) 
         {
            Tile newTile = makeATile(tileConstructors, rand);
            list.addTile(newTile);
            Graphics g = panel.getGraphics();
            list.drawAll(g);
         } 
         else if (code == KeyEvent.VK_S) 
         {
            list.shuffle(WIDTH, HEIGHT);
            Graphics g = panel.getGraphics();
            panel.clear();
            list.drawAll(g);
         }
      }
   }
}   
   