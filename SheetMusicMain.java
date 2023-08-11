import java.util.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;


   
public class SheetMusicMain
{
   private static final boolean CATCH_EXCEPTIONS = true;
          
   public static class TileKeyListener extends KeyAdapter 
   {
      private SheetMusic sm;
            
      public TileKeyListener(SheetMusic sm) 
      {
         this.sm = sm;
      }
      public void keyPressed(KeyEvent evt) {
         if (evt.getKeyCode() == KeyEvent.VK_P) {
            sm.playNotes();

         }
      }
      
   } 
   public static class TileMouseListener extends MouseInputAdapter 
   {
      private SheetMusic sm;
      
      public TileMouseListener(SheetMusic sm) 
      {
         this.sm = sm;
      }
      
      public void mousePressed(MouseEvent event) 
      {
         int x = event.getX() / sm.panel.getZoom();
         int y = event.getY() / sm.panel.getZoom();
         
         try {
            if (event.isControlDown() || SwingUtilities.isLeftMouseButton(event)) 
            {  
                  sm.buttonHitChange(x, y);
                  sm.addNote(x, y);

                  
            } 
            else
            {
               sm.deleteNote(x,y);
               //System.out.println("2");
            }
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
  

   

   public static void main(String[] args)
   {
   
      SheetMusic sm = null;
		
		Scanner console = new Scanner(System.in);
		String command = "nothing";
		while(!command.equalsIgnoreCase("quit")) {
         intro();
			System.out.print("What would you like to do? ");
			command = console.next();
			
			if(!(command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("load") || command.equalsIgnoreCase("create")) 
					&& sm == null) {
				System.out.println("You must load a song before trying to manipulate it"); 
            
               
			} else if(command.equalsIgnoreCase("load")) {
				System.out.print("Input File name? ");
				File file = checkFile(console.next(), console);
            Queue<String> input = new LinkedList<String>();

            
            
            
            try
            {
				   Scanner fileScanner = new Scanner(file);
               while (fileScanner.hasNext())
               {
                  input.add(fileScanner.nextLine());
               }
				   
            }
            catch (Exception e) {} 
            
            
            try
            {
               sm = new SheetMusic(input);
               sm.combineStr();
               sm.strToNotes();
               sm.pullEditInterface();
               TileKeyListener listener = new TileKeyListener(sm);
               sm.panel.addKeyListener(listener);
            }
            catch (Exception e)
            {
               System.out.println("Sheet Music was invalid. Check to see if you have the correct formatting, and try again.");
            }   
            System.out.print("Ouput MIDI File name? ");
            File f = new File(console.next());
            sm.setMidi(f);
            sm.toMidi();
            
           
            
            
          } else if(command.equalsIgnoreCase("create")) {
            Queue<String> placeHolder = new LinkedList<String>();
            
            
            
            try
            {     
               
               
               sm = new SheetMusic(placeHolder);
               sm.pullCreateInterface();
               TileMouseListener listener = new TileMouseListener(sm);
               sm.panel.addMouseListener(listener);
               TileKeyListener listener2 = new TileKeyListener(sm);
               sm.panel.addKeyListener(listener2);
               
               System.out.print("Ouput MIDI File name? ");
               File midi = new File(console.next());
            
               System.out.print("Ouput Text File name? ");
               PrintStream output = new PrintStream(new File(console.next())); 
               sm.setMidi(midi);
               sm.setText(output);
               
            }
            catch (Exception e) {}   
            
            
            
            

		 
			} else if (!command.equalsIgnoreCase("quit")) {
				System.out.println("Invalid command. Please try again.");
				intro();
			}		
		}
	}

   private static void intro() {
		System.out.println("\nWelcome to SheetMusicMain. Type the word in the left column to do the action on the right");
		System.out.println("load     : load existing sheet music from a text file to play it");
      		System.out.println("create   : create/edit/play new sheet music");
		System.out.println("quit     : exit the program");
	}

      
   
   public static File checkFile(String name, Scanner console) {
		File file = new File(name);
		while (!file.exists()) {
			System.out.print("Invalid file. File name? ");
			file = new File(console.next());
		}
		return file;
	}

}
