import java.util.*;
import java.awt.*;
import java.io.*;
import java.io.File; 
import java.io.IOException; 
import javax.imageio.ImageIO; 
import java.awt.image.*;
import org.jfugue.player.Player;
import org.jfugue.pattern.Pattern;
import javax.sound.midi.InvalidMidiDataException;
import org.jfugue.midi.MidiFileManager;
import javax.sound.midi.*;

public class SheetMusicObjectStyle
{
   private Queue<String> song;
   private int BPM;
   private int width;
   private int height;
   private String pattern;
   private File midi;
   public SheetMusicObjectStyle(Queue<String> song)
   
   {
      this.song = song;
      this.BPM = 120;
      this.width = 500;
      this.height = 250;
      this.pattern = pattern;
      this.midi = midi;
   }
   
   
      
      
   public void toMidi()
   {
      Player player = new Player();
      Pattern pattern = new Pattern(this.pattern);
      Sequence sequence = player.getSequence(pattern);
      this.midi = new File("SheetMusic.mid");
      try
      {
         MidiFileManager.save(sequence, this.midi);
      }
      catch (Exception e)
      {
         System.out.println("Oops, something went wrong.");
      }
      
   }  
   
   public void load

   
   private void reverse()
   {
      Stack<String> reverseStack = new Stack<String>();
      int len = this.song.size() - 1;
      for (int i = len; i >= 0; i--)
      {
         reverseStack.push(this.song.poll());
      }
      
      for (int i = len; i >= 0; i--)
      {
         this.song.add(reverseStack.pop()); 
         
      }
   }
   
     
   private void append(SheetMusicObjectStyle other)
   {
      if (other == null)
      {
         throw new IllegalArgumentException();
      }
      
      
      for (int i = other.song.size() - 1; i >= 0; i--)
      { 
         
         this.song.add(other.song.poll());
      }
   }
   
   private void playNote()
   {
   Player player = new Player();
   this.song.add(this.song.peek());
   player.play(this.song.poll());     
   }  
  
   
   public void pullInterface()
   {
      DrawingPanel panel = new DrawingPanel(this.width, this.height);
      Graphics g = panel.getGraphics();
      g.setColor(Color.BLACK);
      g.drawLine(0, this.height - (6*this.height/8), this.width, this.height - (6*this.height/8));
      g.drawLine(0, this.height - (5*this.height/8), this.width, this.height - (5*this.height/8));
      g.drawLine(0, this.height - (4*this.height/8), this.width, this.height - (4*this.height/8));
      g.drawLine(0, this.height - (3*this.height/8), this.width, this.height - (3*this.height/8));
      File trebleClef = new File("treble.png");
      try
      {
         Image image = ImageIO.read(trebleClef);
         g.drawImage(image, 0, this.height - (6*this.height/8), (this.width/10), (this.height*1/2), null);
      }
      catch (Exception e)
      {
         System.out.println("Error: invalid file");
      }
      
      for (int i = 0; i < this.song.size(); i++)
      {
         playNote();
      }
   }   
      

      
           
      
      //for ( int i = 0; i < this.song.size; i++)
      //{
         
      //}
} 
   
     
      
  //  private double getDuration()
//    {
//       double totalDuration = 0.0;
//       double currentMax = 0;
//       double trackMax = 0;
//       String nextToken = "";
//       for (int i = 0; i < this.song.size(); i++)
//       {
//          System.out.println("1");
//          this.song.add(this.song.peek());
//          String line = this.song.poll(); // goes line by line
//          Scanner lineScanner = new Scanner(line);
//          while(lineScanner.hasNext()) // goes token by token
//          {
//             System.out.println("2");
//             String token = lineScanner.next(); // gets next token
//             if (token.substring(0,1) == "V") // checks if it's a separate track
//             {
//                System.out.println("check here");
//                totalDuration = totalDuration + trackMax; // total of all tracks
//                trackMax = 0;
//                lineScanner.next(); // gets off the track indicator
//                i++;               
//             }
//             else
//             {
//                System.out.println("check HERE");
//                System.out.println(" AHHHH " + token.length());
//                
//                for (int j = 1; j <= token.length(); j++) // goes leter by letter
//                {
//                   String subToken = token.substring(j-1,j);
//                   System.out.println("try");
//                   try
//                   {
//                      nextToken = token.substring(j, j+1);
//                      System.out.println("maybe?");
//                   }
//                   catch (Exception e)
//                   {
//                      nextToken = null;
//                      System.out.println("here");
//                      
//                   }   
//                   if (subToken == "w" || subToken == "h" || subToken == "q" || subToken == "i" || // if it's a note length indicator
//                       subToken == "s" || subToken == "t" || subToken == "x" || subToken == "o"  // and it's the end of an expression
//                       && (nextToken == null || nextToken == "+"))
//                   {
//                      System.out.println("made it!");
//                      double tempTime = 0.25; // default value is quarter note
//                      if (subToken == "w") {
//                         tempTime = 4.0/(BPM/60);
//                    } else if (subToken == "h") {
//                         tempTime = 2/(BPM/60);
//                    } else if (subToken == "q") {
//                         tempTime = 1/(BPM/60);
//                    } else if (subToken == "i") {
//                         tempTime = 0.5/(BPM/60);
//                    } else if (subToken == "s") {
//                         tempTime = 0.25/(BPM/60);
//                    } else if (subToken == "t") {
//                         tempTime = 0.125/(BPM/60);
//                    } else if (subToken == "x") {
//                         tempTime = 0.0625/(BPM/60);
//                    } else if (subToken == "o") {
//                         tempTime = 0.03125/(BPM/60);
//                    }     
//                      else {
//                         return 9999999999.0; // this is a check for me to see if something is up; code should never get here
//                      }   
//                      
//                      trackMax = Math.max(tempTime, trackMax); // the max of the current track
//                   }
//                   else // if there's no indicator
//                   {
//                      trackMax = 0.25; // default value
//                   }
//                }
//                System.out.println("End of for loop"); 
//             }
//          }  
//       }   
//       return totalDuration;
//    } 