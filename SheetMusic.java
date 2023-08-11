import java.util.*;
import java.awt.*;
import java.io.*;
import java.io.File; 
import java.io.IOException; 
import javax.imageio.ImageIO; 
import java.awt.image.*;
import org.jfugue.player.Player;
import org.jfugue.pattern.Pattern;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.theory.Intervals;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.*;

public class SheetMusic
{
    /**
   * @value song
   * a queue of strings reprenting the song
   */
   private Queue<String> song;
   /**
   * @value patternStr
   * a string reprenting the song to be played
   */
   public String patternStr;
   /**
   * @value patternStr
   * a pattern representing the song to be played
   */
   private Pattern pattern;
   /**
   * @value patternStr
   * a File for the midi file
   */
   public File inputMidi;
   /**
   * @value patternStr
   * a printstream for the text file
   */
   public PrintStream inputText;
   /**
   * @value patternStr
   * an arraylist of notes representing the notes made in the creator
   */
   public ArrayList<Note> noteListCreator;
   /**
   * @value patternStr
   * an arraylist of xCoordinates for the notes made in the creator
   */
   public ArrayList<Integer> noteListXCoord;
   /**
   * @value patternStr
   * an arraylist of strings for the notes made in the creator
   */
   public ArrayList<String> patternCreator;
   /**
   * @values V0, V1, V2, and V3
   * Queues of notes representing different tracks played in the loader 
   */
   public Queue<Note> V0;
   public Queue<Note> V1;
   public Queue<Note> V2;
   public Queue<Note> V3;
   /**
   * @values V0bool, V1bool, V2bool, V3bool, 
   * Queues of strings representing if a note belongs to a chord or not 
   */
   public Queue<String> V0bool;
   public Queue<String> V1bool;
   public Queue<String> V2bool; 
   public Queue<String> V3bool; 
   /**
   * @values HEIGHT, WIDTH
   * integers representing the height and width of the drawing panel
   */    
   public static final int HEIGHT = 160;
   public static final int WIDTH = 5000;
   /**
   * @value MEASURE
   * integer representing the pixel size of each measure
   */
   public static final int MEASURE = 250;
    /**
   * @value MEASURE
   * String representing the accidental for notes from the creator
   */
   public String accidentalIdentity;
   /**
   * @value unit
   * A relationship to the height that decides note size, staff size, etc.
   */
   private int unit;
    /**
   * @value panel
   * A DrawingPanel for the sheetmusic
   */
   public DrawingPanel panel;
   /**
   * @value g
   * A graphics object for the panel
   */
   public Graphics g;
   /**
   * @value noteIdentity
   * A double representing the duration of the note for the creator
   */
   public double noteIdentity;
   
   
   /**
   * Constructor for the SheetMusic object
   */
   public SheetMusic(Queue<String> song)   
   {
      this.song = song;
      this.patternStr = "T[Largo] "; // default
      this.pattern = pattern;
      this.inputMidi = inputMidi;
      this.inputText = inputText;
      this.V0 = new LinkedList<Note>();
      this.V1 = new LinkedList<Note>();
      this.V2 = new LinkedList<Note>();
      this.V3 = new LinkedList<Note>();
      this.V0bool = new LinkedList<String>();
      this.V1bool = new LinkedList<String>();
      this.V2bool = new LinkedList<String>();
      this.V3bool = new LinkedList<String>();
      this.noteListCreator = new ArrayList<Note>();
      this.noteListXCoord = new ArrayList<Integer>();
      this.patternCreator = new ArrayList<String>();
      this.panel = new DrawingPanel(WIDTH, HEIGHT);
      this.g = panel.getGraphics();
      this.accidentalIdentity = "";
      this.unit = HEIGHT/32;
      this.noteIdentity = 0.25;
      
   }
   /*
   * sets the midi file to user input
   *
   * @param File f      output file for MIDI
   */
   public void setMidi(File f)
   {
      this.inputMidi = f;
   }
   
   /*
   * sets the text file to user input
   *
   * @param File f      output file for text
   */
   public void setText(PrintStream ps)
   {
      this.inputText = ps;
   }
   


   
   /*
   * combines all the voices for prepping in strToNotes()
   */
   public void combineStr()
   {
      String finalStr = "";
      for (int i = 0; i < this.song.size(); i++)
      {
         this.song.add(this.song.peek());
         finalStr = finalStr + this.song.poll() + " ";  
      }
      
      this.patternStr = finalStr;
   }
   /*
   * prints out one note
   *
   * @param duration    str rep of the note
   *
   * @param currentX    x location of the note
   *
   * @param currentY    y location of the note
   *
   * @param accidentalStr    string representing the accidental
   *
   * @param currentNote       the current note object being printed
   */
   public void printNote(String duration, int currentX, int currentY, String accidentalStr, Note currentNote)
   {
      if (duration.equals("w"))
      {
         this.g.drawOval(currentX, currentY, 4*this.unit, 2*this.unit);
      }
      else
      {
         if (duration.equals("q") || duration.equals("i") 
             || duration.equals("s") || duration.equals("t")
             || duration.equals("x") || duration.equals("o") || duration.equals("h"))
         {    
            this.g.drawOval(currentX, currentY, 2*this.unit, 2*this.unit);
            this.g.drawLine(currentX + (2*this.unit), currentY + this.unit, currentX + (2*this.unit), 
                            currentY - 4*this.unit);
            
            if (duration.equals("q") || duration.equals("i") 
             || duration.equals("s") || duration.equals("t")
             || duration.equals("x") || duration.equals("o"))
             
            {
               
               this.g.fillOval(currentX, currentY, 2*this.unit, 2*this.unit);
               
               if (duration.equals("i") || duration.equals("s") 
                || duration.equals("t") || duration.equals("x") 
                || duration.equals("o"))
               {
                  this.g.drawArc(currentX + (this.unit), currentY - 4*this.unit, 2*this.unit, 
                                 4*this.unit, 0, 90);                  
                  if  (duration.equals("s") || duration.equals("t") 
                    || duration.equals("x") || duration.equals("o"))
                  {
                     this.g.drawArc(currentX + (this.unit), currentY - 4*this.unit + 5, 2*this.unit, 
                                    4*this.unit, 0, 90);
                     
                     if (duration.equals("t") || duration.equals("x") || duration.equals("o"))
                     {
                        this.g.drawArc(currentX + (this.unit), currentY - 4*this.unit + 5, 2*this.unit, 
                                                   4*this.unit, 0, 90);
                        if (duration.equals("x") || duration.equals("o"))
                        {
                           this.g.drawArc(currentX + (this.unit), currentY - 4*this.unit + 5, 2*this.unit, 
                                           4*this.unit, 0, 90);
                           if (duration.equals("o"))
                           {
                              this.g.drawArc(currentX + (this.unit), currentY - 4*this.unit + 5, 2*this.unit, 
                                             4*this.unit, 0, 90);
                           } 
                        } 
                     } 
                  } 
               }  
            }
         }   
      }
         
      if (accidentalStr.equals("#") || accidentalStr.equals("b"))
      {
         this.g.drawString(accidentalStr, currentX - 7, currentY + 10);
      } 
      
   }
   

  
            
   /*
   * formats the entire string into note object - chords, voice headers, etc.
   */
   public void strToNotes()
   {
      String copyOfPatternStr = this.patternStr;
      Scanner strScanner = new Scanner(copyOfPatternStr);
      
      String voiceIndex = "V0";
      while(strScanner.hasNext())
      {
         String token = strScanner.next();
         
         
         //Header Cases
         if(token.substring(0,1).equals("V"))
         {
            
         
            if (token.equals("V0"))
            {
               voiceIndex = "V0";
               copyOfPatternStr = copyOfPatternStr.substring(copyOfPatternStr.indexOf("V1") + 3,
               copyOfPatternStr.length());
            }
   
            
            else if (token.equals("V1"))
            {
               voiceIndex = "V1";
               copyOfPatternStr = copyOfPatternStr.substring(copyOfPatternStr.indexOf("V1") + 3,
               copyOfPatternStr.length());
            }
            else if (token.equals("V2"))
            {
               voiceIndex = "V2";
               copyOfPatternStr = copyOfPatternStr.substring(copyOfPatternStr.indexOf("V2") + 3, 
               copyOfPatternStr.length());
            }
            else if (token.equals("V3"))
            {
               voiceIndex = "V3";
               copyOfPatternStr = copyOfPatternStr.substring(copyOfPatternStr.indexOf("V3") + 3, 
               copyOfPatternStr.length());
            }
            else
            {
               voiceIndex = "V0";
            }  
         }  
         
         // if (token.substring(0,1).equals 
         
         //Chord cases
         if (Chord.isValidChord(token) == true)
         {
            Chord chord = new Chord(token);
            Note[] notesFromChord = chord.getNotes();
            for (int i = 0; i < notesFromChord.length; i++)
            {
               if (voiceIndex.equals("V0"))
               {
                  this.V0.add((notesFromChord[i]));
                  this.V0bool.add("CHORD " + i);
               }
               else if (voiceIndex.equals("V1"))
               {
                  this.V1.add((notesFromChord[i]));
                  this.V1bool.add("CHORD " + i);
               }
               else if (voiceIndex.equals("V2"))
               {
                  this.V2.add((notesFromChord[i]));
                  this.V2bool.add("CHORD " + i);
               } 
               else if (voiceIndex.equals("V3"))
               {
                  this.V3.add((notesFromChord[i]));
                  this.V3bool.add("CHORD " + i);
               } 
            }
         }
         
         
         // + cases - multiple notes strung together
         else if (token.indexOf("+") != -1)
         {
            String[] notes = token.split("\\+");
            for (int i = 0; i < notes.length; i++)
            {
               Note note = new Note(notes[i]);
               if (voiceIndex.equals("V0"))
               {
                  this.V0.add(note);
                  this.V0bool.add("CHORD " + i);
               }
               else if (voiceIndex.equals("V1"))
               {
                  this.V1.add(note);
                  this.V1bool.add("CHORD " + i);
               }
               else if (voiceIndex.equals("V2"))
               {
                  this.V2.add(note);
                  this.V2bool.add("CHORD " + i);
               } 
               else if (voiceIndex.equals("V3"))
               {
                  this.V3.add(note);
                  this.V3bool.add("CHORD " + i);
               } // there WILL end up being an extra + at the start of these tokens, but this makes it
                 // easier to see when there
            }     
        }  
         
         //Regular note cases
         else if (Note.isValidNote(token) == true)
         {
            Note note = new Note(token);
            if (voiceIndex.equals("V0"))
            {
               this.V0.add(note);
               this.V0bool.add("NOCHORD 0");
            }
            else if (voiceIndex.equals("V1"))
            {
               this.V1.add(note);
               this.V1bool.add("NOCHORD 0");
            }
            else if (voiceIndex.equals("V2"))
            {
               this.V2.add(note);
               this.V2bool.add("NOCHORD 0");
            } 
            else if (voiceIndex.equals("V3"))
            {
               this.V3.add(note);
               this.V3bool.add("NOCHORD 0");
            } 
         }
         
         
         
         
      }
   }
   

      
   /*
   * exports file to a midi file
   */   
   public void toMidi()
   {
      combineStr();
      strToNotes();
      Player player = new Player();
      
      Sequence sequence = player.getSequence(this.patternStr);
      try
      {
         MidiFileManager.save(sequence, this.inputMidi);
      }
      catch (Exception e)
      {
         System.out.println("Oops, something went wrong when saving to MIDI.");
      }
      
   }  
   
   /*
   * exports file to a midi file
   */   
   public void toText()
   {
      combineStr();
      strToNotes();
      this.inputText.print(this.patternStr);
   }  

   /*
   * plays the notes out from the pattern string
   */
   public void playNotes()
   {
      Player player = new Player();
      player.play(this.patternStr);    
   }  
    /*
   * prints all the measures for the staff
   */
   public void printMeasures()
   {
      for (int i = 0; i < WIDTH/MEASURE; i++)
      {
         this.g.drawLine(60 + i*MEASURE, 8*this.unit, 60 + i*MEASURE, 28*this.unit);
      }

   }
   
    /*
   * prints ALL the notes for a given voice/queue of notes to the panel
   *
   * @param voice    queue of note objects to be printed
   *
   * @param chordCheck  queue representing if notes are a part of a chord or not
   *
   * @param color       color of notes to be printed
   */
   public void interfacePrintNote(Queue<Note> voice, Queue<String> chordCheck, Color color)
   {
      
      
      if (voice == null)
      {
         return;
      }
      int j = 0;
      int numNote = 0;
      int currentX = 65; // starting note position
      int durAsInt = 0;
      int currentY = this.unit*16; // middle C 
      boolean durationIncrease = true;
      double measureDur = 0.0;
      
      for (int i = 0; i < voice.size(); i++) // goes token by token
      {
         voice.add(voice.peek());
         Note currentNote = voice.poll();
         chordCheck.add(chordCheck.peek());
         String chordCheckStr = chordCheck.poll();
         int oldNumNote = numNote;
         numNote = Integer.valueOf(chordCheckStr.substring(chordCheckStr.length()-1, chordCheckStr.length()));
         
         byte noteIndicator = currentNote.getValue();
         String strNote = Note.getToneStringWithoutOctave(noteIndicator);
         int byteOctave = currentNote.getOctave(); 
         String octave = Integer.toString(byteOctave);
         String duration = Note.getDurationString(currentNote.getDuration());
         String accidentalStr = Note.getToneStringWithoutOctave(noteIndicator);
         
         if (accidentalStr.indexOf("#") != -1) //sets accidental str
         {
            accidentalStr = "#";
         }
         else if (accidentalStr.indexOf("b") != -1)
         {
            accidentalStr = "b";
         }
         
         
         // handles chords, doesn't advance position.
         // THANK YOU JFUGUE FOR MAKING WHAT USED TO BE
         // A SUPER CONVOLUTED PROCESS WITH STRINGS MUCH EASIER!
         if (chordCheckStr.substring(0,5).equals("CHORD")) 
         {
            if (j > 0 && numNote > oldNumNote)
            {
               currentX = currentX - (int) (currentNote.getDuration()*MEASURE);
            }
            else
            {
               j++;
            }   
         }
         else
         {
            j = 0;
         }
         

            
            
            
              
         // NOTE PLACEMENT CONTROL
         if (noteIndicator % 12 == 0 || noteIndicator % 12 == 1) { // C/C#
            currentY = 16*this.unit;
         } else if (noteIndicator % 12 == 2) { // D/D#
            currentY = 15*this.unit;
         } else if (noteIndicator % 12 == 3 || noteIndicator % 12 == 4) { // E/Eb
            currentY = 14*this.unit;
         } else if (noteIndicator % 12 == 5 || noteIndicator % 12 == 6) { // F / F#
            currentY = 13*this.unit;
         } else if (noteIndicator % 12 == 7 || noteIndicator % 12 == 8) { // G
            currentY = 12*this.unit;
         } else if (noteIndicator % 12 == 9) { // A
            currentY = 11*this.unit; 
         } else if (noteIndicator % 12 == 10  || noteIndicator % 12 == 11) { // Bb/B
            currentY = 10*this.unit;
         } 
         
                       
         
         
         
         // OCTAVE PLACEMENT CONTROL
         if (octave.equals("1")) {
            currentY = currentY + 4*(6*this.unit);
         } else if (octave.equals("0")) {
            currentY = currentY + 5*(6*this.unit);
         } else if (octave.equals("2")) {
            currentY = currentY + 3*(6*this.unit);
         } else if (octave.equals("3")) {
            currentY = currentY + 2*(6*this.unit);
         } else if (octave.equals("4")) {
            currentY = currentY + 8*(this.unit);
         } else if (octave.equals("5")) {
            currentY = currentY + this.unit;
         } else if (octave.equals("6")) {
            currentY = currentY - (6*this.unit);
         } else if (octave.equals("7")) {
            currentY = currentY - 2*(6*this.unit) - this.unit;
         } else if (octave.equals("8")) {
            currentY = currentY - 3*(6*this.unit) - this.unit;
         }

         
            

         
         if (numNote == 0)
         {
            double increment = (currentNote.getDuration());
            measureDur = measureDur + increment;
         }
         double moveIncrement = currentNote.getDuration();
         double durAsDouble = ((double) moveIncrement*this.unit*60);
         durAsInt = (int) Math.round(durAsDouble);
         
         
         if (currentNote.isRest() == true)
         {
            strNote = Note.getToneString(noteIndicator);

            if (duration.equals("w")) {
               currentY = (12*this.unit);
            } else if (duration.equals("h")) {
               currentY = (13*this.unit);
            } else if (duration.equals("q") || (duration.equals("i")) || duration.equals("s")) {
               currentY = (12*this.unit); 
            }     
               
            File restFile = new File("qrest.png"); // quarter rest
            boolean img = false;
            if (duration.equals("w") || duration.equals("h"))
            {
               this.g.fillRect(currentX, currentY, this.unit*2, this.unit);
            }
            
            else if (duration.equals("i"))
            {  
               restFile = new File("irest.png"); //eight rest
               img = true;
            }
            else if (duration.equals("s"))
            {  
               restFile = new File("srest.png"); //srest. NOTE - these won't print 32nd, 64th, or 128th rests because the 
                                                   // sheet music starts to look very messy, and blank spaces work better.
               img = true;
            }  
            else 
            {  
               restFile = new File("qrest.png");
               img = false;
               try
               {
                  Image rest  = ImageIO.read(restFile);
                  this.g.drawImage(rest, currentX, currentY - this.unit*2, this.unit*5, this.unit*5, null); // draws a qrest default
               }
               catch (Exception e)
               {
               }   
            }
            
            if (img == true)
            {             
               try
               {
                  Image rest  = ImageIO.read(restFile);
                  this.g.drawImage(rest, currentX, currentY, this.unit*3, this.unit*3, null); //draws rest
                  
               }
               catch (Exception e)
               {
               }
            }   
         }

         if(currentNote.isRest() == false)
         {           
            printNote(duration, currentX, currentY, accidentalStr, currentNote); // prints anything not a rest
         }   
         currentX = currentX + (int)(currentNote.getDuration()*MEASURE); // advances in position
      }
   }   
   
     
  
   /*
   * pulls up the load interface for the user
   */
   public void pullEditInterface()
   {
      File trebleClef = new File("treble.png");
      File bassClef = new File("bass.png");
      try
      {
         Image treble = ImageIO.read(trebleClef);
         this.g.drawImage(treble, 0, (this.unit)*5, 52, 12*this.unit, null); // draws treble/bass clefs
         Image bass  = ImageIO.read(bassClef);
         this.g.drawImage(bass, 0, 20*this.unit, 42, (8*this.unit), null);
         
      }
      catch (Exception e)
      {
         System.out.println("Error: invalid file");
      }
      this.g.setColor(Color.BLACK);
      this.g.drawLine(0, 8*this.unit, WIDTH, 8*this.unit);
      this.g.drawLine(0, 10*this.unit, WIDTH, 10*this.unit);
      this.g.drawLine(0, 12*this.unit, WIDTH, 12*this.unit);
      this.g.drawLine(0, 14*this.unit, WIDTH, 14*this.unit); // draws top staff
      this.g.drawLine(0, 16*this.unit, WIDTH, 16*this.unit);
      
   
      this.g.setColor(Color.BLACK);
      this.g.drawLine(0, 20*this.unit, WIDTH, 20*this.unit);
      this.g.drawLine(0, 22*this.unit, WIDTH, 22*this.unit);
      this.g.drawLine(0, 24*this.unit, WIDTH, 24*this.unit);
      this.g.drawLine(0, 26*this.unit, WIDTH, 26*this.unit); // draws bottom staff
      this.g.drawLine(0, 28*this.unit, WIDTH, 28*this.unit);
      
      printMeasures();
      
      
      
      
   

      combineStr();

      Color color = Color.BLACK;
      interfacePrintNote(this.V0, this.V0bool, color); // prints out voice 1

      interfacePrintNote(this.V1, this.V1bool, color); // prints out voice 2

      interfacePrintNote(this.V2, this.V2bool,color); // prints out voice 3

      interfacePrintNote(this.V3, this.V2bool, color); // prints out voice 4
   }   
      
      
      // listen for mouse clicks
   /*
   * returns whether or not a button is hit
   *
   * @return boolean of if it's hit or not
   */ 
   public boolean buttonIsHit(int xClick, int yClick, int xRect, int yRect, int width, int height) 
   { 
      return (xClick >= xRect && (xClick <= width + xRect)) && (yClick >= yRect && yClick <= (height + yRect));
   }
    /*
   * one singular button's layout
   */
   public void buttonLayout(int xLoc, int textAdjustment, String note) // makes one button
   {
      this.g.drawRect(xLoc, 0, WIDTH/50 + 50, 20);
      this.g.drawString(note, xLoc + textAdjustment, 15); // this is so small for factoring (conscice code!)
   }
   
    /*
   * pulls up the buttons in the create interface
   */
   public void createButtons() 
   {
      buttonLayout(WIDTH/50, 35, "Whole Note");
      buttonLayout(3*WIDTH/50, 35, "Half Note");
      buttonLayout(5*WIDTH/50, 30, "Quarter Note");
      buttonLayout(7*WIDTH/50, 25, "Eighth Note");
      buttonLayout(9*WIDTH/50, 20, "Sixteenth Note"); // button constructors
      buttonLayout(11*WIDTH/50, 70, "#");
      buttonLayout(13*WIDTH/50, 70, "b");
      buttonLayout(15*WIDTH/50, 65, "natural");
   }
   
    /*
   * gets the note identity based on the button the user clicks
   *
   * @return if a button was hit or not
   */
   public boolean buttonHitChange(int x, int y)
   {
      if  (buttonIsHit(x, y, WIDTH/50, 0, WIDTH/50 + 50, 20))
      {
         this.noteIdentity = 1.0; // whole note
         return true;
      }
      
      else if  (buttonIsHit(x, y, 3*WIDTH/50, 0, WIDTH/50 + 50, 20))
      {
         this.noteIdentity = 0.5; // half note
         return true;
      }
      
      else if  (buttonIsHit(x, y, 5*WIDTH/50, 0, WIDTH/50 + 50, 20))
      {
         this.noteIdentity = 0.25; // quarter note
         return true;
      }
      
      else if  (buttonIsHit(x, y, 7*WIDTH/50, 0, WIDTH/50 + 50, 20))
      {
         this.noteIdentity = 0.125; // eighth note
         return true;
      }
      
      else if  (buttonIsHit(x, y, 9*WIDTH/50, 0, WIDTH/50 + 50, 20))
      {
         this.noteIdentity = 0.0625; // sixteenth note
         return true;
      }
      else
      
      if (buttonIsHit(x, y, 11*WIDTH/50, 0, WIDTH/50 + 50, 20))
      {
         this.accidentalIdentity = "#"; // sharp
         return true;
      }
      
      else if (buttonIsHit(x, y, 13*WIDTH/50, 0, WIDTH/50 + 50, 20))
      {
         this.accidentalIdentity = "b"; // flat
         return true;
      }
      else if (buttonIsHit(x, y, 15*WIDTH/50, 0, WIDTH/50 + 50, 20))
      {
         this.accidentalIdentity = "natural"; // resets back to natural
         return true;
      }
      else
      {
         return false;
      }
   }
    /*
   * adds a note based on where the user clicks. DOESN'T DRAW, only adds it to the queue
   *
   * @param x  x click of user
   *
   * @param y  y click of user
   */
   public void addNote(int x, int y)
   {
      int upperMeasure = 0;
      for (int i = 1; i < WIDTH/MEASURE; i++)
      {
         if ((i*MEASURE) + 65 > x)
         {
            upperMeasure = i*MEASURE + 65;
            i = WIDTH/MEASURE;
         }
      }
      int lowerMeasure = upperMeasure - MEASURE; // gets the lower bound of the measure we're in
      
      //System.out.println(this.noteIdentity);
      
      x = x - lowerMeasure;
      x = 32*(Math.round(x/32)); // rounds x to the nearest interval of 32 for notes
      x = x + lowerMeasure; // isolates x to just the measure it's in
      //System.out.println("X: " + x);
      
      int lineUnit = HEIGHT/32;
      y = lineUnit*(Math.round(y/lineUnit));// gets click to the nearest line/space
      //System.out.println("Y: " + y);
      int refToMiddleC = (y - HEIGHT/2);      
      refToMiddleC = - (refToMiddleC/(HEIGHT/32) + 1); // gets line/space ref
      int noteNum = -1;
      int octave = 5;
      
      if (buttonHitChange(x,y) == true)
      {
         return;              // BUTTONS WILL NOT ADD A NOTE
      }

      
       // NOTE PLACEMENT CONTROL - the % 7 stands for each unique LINE/EMPTY SPACE where a note can be printed
  
         if (refToMiddleC >= 0)
         {
            if (refToMiddleC % 7 == 0) { // C/C#
               noteNum = 0;
            } else if (Math.abs(refToMiddleC % 7) == 1) { // D/D#
               noteNum = 2;
            } else if (Math.abs(refToMiddleC % 7) == 2) { // E/Eb
               noteNum = 4;
            } else if (Math.abs(refToMiddleC % 7) == 3) { // F / F#
               noteNum = 5;
            } else if (Math.abs(refToMiddleC % 7) == 4) { // G
               noteNum = 7;
            } else if (Math.abs(refToMiddleC % 7) == 5) { // A
               noteNum = 9;
            } else if (Math.abs(refToMiddleC % 7) == 6) { // b
               noteNum = 11;
            } 
            octave = 5 + ((refToMiddleC)/7);
         }    
         else
         {
            if (refToMiddleC % 7 == 0) { // C/C#
               noteNum = 0;
            } else if (Math.abs(refToMiddleC % 7) == 1) { // D/D#
               noteNum = 11;
            } else if (Math.abs(refToMiddleC % 7) == 2) { // E/Eb
               noteNum = 9;
            } else if (Math.abs(refToMiddleC % 7) == 3) { // F / F#
               noteNum = 7;
            } else if (Math.abs(refToMiddleC % 7) == 4) { // G
               noteNum = 5;
            } else if (Math.abs(refToMiddleC % 7) == 5) { // A
               noteNum = 4;
            } else if (Math.abs(refToMiddleC % 7) == 6) { // Bb/B
               noteNum = 2;
            } 
            octave = 4 + ((refToMiddleC)/8);
         }   
             
         if (this.accidentalIdentity.equals("#"))
         {
            noteNum = noteNum + 1;
         }
         else if (this.accidentalIdentity.equals("b"))
         {
            noteNum = noteNum -1;
         }
         int noteIndex = octave*12 + noteNum;
         

         

      //System.out.println(noteIndex);
      
      Note newNote = new Note(noteIndex, this.noteIdentity);
      int indexCap = this.noteListXCoord.size();

      
      for (int i = 0; i <= indexCap; i++)
      {
         if (this.noteListCreator.isEmpty() == true)
         {
            this.noteListXCoord.add(x);
            this.noteListCreator.add(newNote);
            i = indexCap + 1;
         }  
         else if (i == this.noteListXCoord.size() - 1)
         {
            this.noteListXCoord.add(x);
            this.noteListCreator.add(newNote);
            i = indexCap + 1;
         }
         else if (noteListXCoord.get(i) >= x)
         {
            this.noteListXCoord.add(i,x);
            this.noteListCreator.add(i, newNote);
            i = indexCap + 1;
         }
         
         
         
  
         
      } 
      //System.out.println(this.noteListCreator); 
      //System.out.println(this.noteListXCoord); 
      Queue<String> pattern = createdToStr(newNote, x, y);
      createPattern(pattern);
   }  
   
   
    /*
   * adds a note based on where the user clicks. DOESN'T DRAW, only adds it to the queue
   *
   * @param note  current note object just added
   *
   * @param x  x click of user
   *
   * @param y  y click of user
   */
    public Queue<String> createdToStr(Note note, int x, int y)
   {

      String noteStr = note.getToneString();
      
      String elementOne = noteListCreator.get(0).getToneString() + noteListCreator.get(0).getOctave() 
      + noteListCreator.get(0).getDurationString(noteListCreator.get(0).getDuration());
      
      String patternElement = "";
      Queue<String> pattern = new LinkedList<String>();
      printNote(note.getDurationString(note.getDuration()),x,y,this.accidentalIdentity, note);
      try
      { 
         if (!noteListXCoord.get(0).equals(noteListXCoord.get(1)))
         {
            pattern.add(elementOne);
         }
      }
      catch (Exception e)
      {
         pattern.add(elementOne);
      }      
      printNote(note.getDurationString(note.getDuration()),x,y,note.getToneStringWithoutOctave(note.getValue()), note);
      
      
      for (int i = 1; i < this.noteListCreator.size(); i++)
      {
         

         if (noteListXCoord.get(i).equals(noteListXCoord.get(i - 1)))
         {
            patternElement = noteListCreator.get(i-1).getToneString() + noteListCreator.get(i-1).getOctave() 
            + noteListCreator.get(i-1).getDurationString(noteListCreator.get(i-1).getDuration());
            
            while (i < this.noteListCreator.size() && noteListXCoord.get(i).equals(noteListXCoord.get(i -1)))
            {
               String rawToken = noteListCreator.get(i).getToneString() + noteListCreator.get(i).getOctave()
                + noteListCreator.get(i).getDurationString(noteListCreator.get(i).getDuration());
                
               patternElement = patternElement + "+" +  rawToken;
               i++;
               //System.out.println("PATTERNELEMENT: " + patternElement);
               
            }
         }
         else
         {
            String rawToken = noteListCreator.get(i).getToneString() + noteListCreator.get(i).getOctave() +
             noteListCreator.get(i).getDurationString(noteListCreator.get(i).getDuration());
             

            patternElement = " " + rawToken + " ";
         }
         pattern.add(patternElement);
         
      }
      //System.out.println("PATTERN: " + pattern);
      return pattern;   
   }

 
    /*
   * creates the final pattern to be played from the create interface
   *
   * @param finalPattern   the final pattern to be played
   *
   */
   public void createPattern(Queue<String> pattern)
   {
      this.patternStr = "";
      //System.out.println(noteListCreator);
      for (int i = 0; i < noteListCreator.size(); i++)
      {
         pattern.add(pattern.peek());
         this.patternStr = this.patternStr + pattern.poll();
         
            
         
      }
      //System.out.println("PATTERN STR: " + patternStr);
   }
   
   
   public void deleteNote(int x, int y)
   {
      int upperMeasure = 0;
      for (int i = 1; i < WIDTH/MEASURE; i++)
      {
         if ((i*MEASURE) + 65 > x)
         {
            upperMeasure = i*MEASURE + 65;
            i = WIDTH/MEASURE;
         }
      }
      int lowerMeasure = upperMeasure - MEASURE; // gets the lower bound of the measure we're in
      
      //System.out.println(this.noteIdentity);
      
      x = x - lowerMeasure;
      x = 32*(Math.round(x/32));
      x = x + lowerMeasure;
      //System.out.println("X: " + x);
      
      int lineUnit = HEIGHT/32;
      y = lineUnit*(Math.round(y/lineUnit));// gets click to the nearest line/space
      //System.out.println("Y: " + y);
      int refToMiddleC = (y - HEIGHT/2);      
      refToMiddleC = - (refToMiddleC/(HEIGHT/32) + 1); // gets line/space ref
      int noteNum = -1;
      int octave = 5;

      
       // NOTE PLACEMENT CONTROL
  
         if (refToMiddleC >= 0)
         {
            if (refToMiddleC % 7 == 0) { // C/C#
               noteNum = 0;
            } else if (Math.abs(refToMiddleC % 7) == 1) { // D/D#
               noteNum = 2;
            } else if (Math.abs(refToMiddleC % 7) == 2) { // E/Eb
               noteNum = 4;
            } else if (Math.abs(refToMiddleC % 7) == 3) { // F / F#
               noteNum = 5;
            } else if (Math.abs(refToMiddleC % 7) == 4) { // G
               noteNum = 7;
            } else if (Math.abs(refToMiddleC % 7) == 5) { // A
               noteNum = 9;
            } else if (Math.abs(refToMiddleC % 7) == 6) { // Bb/B 
            } 
            octave = 5 + ((refToMiddleC)/7);
         }    
         else
         {
            if (refToMiddleC % 7 == 0) { // C/C#
               noteNum = 0;
            } else if (Math.abs(refToMiddleC % 7) == 1) { // D/D#
               noteNum = 11;
            } else if (Math.abs(refToMiddleC % 7) == 2) { // E/Eb
               noteNum = 9;
            } else if (Math.abs(refToMiddleC % 7) == 3) { // F / F#
               noteNum = 7;
            } else if (Math.abs(refToMiddleC % 7) == 4) { // G
               noteNum = 5;
            } else if (Math.abs(refToMiddleC % 7) == 5) { // A
               noteNum = 4;
            } else if (Math.abs(refToMiddleC % 7) == 6) { // Bb/B
               noteNum = 2;
            } 
            octave = 4 + ((refToMiddleC)/8);
         }  
          
         int noteIndex = octave*12 + noteNum;
         int flatNoteIndex = noteIndex - 1;
         int sharpNoteIndex = noteIndex + 1;
         Note placeholderNote = new Note(0);

      
      for (int i = 0; i < this.noteListXCoord.size(); i++)
      {
         try
         {
            if (x == this.noteListXCoord.get(i) && (noteIndex == this.noteListCreator.get(i).getValue() || 
                flatNoteIndex == this.noteListCreator.get(i).getValue() || sharpNoteIndex == this.noteListCreator.get(i).getValue()))
            {
               this.g.setColor(Color.WHITE);
               Note note = new Note(noteIndex, this.noteIdentity);
               printNote(note.getDurationString(note.getDuration()),x,y,note.getToneStringWithoutOctave(note.getValue()), note);
               this.noteListXCoord.remove(i);
               this.noteListCreator.remove(i);
                Queue<String> newQueue = createdToStr(placeholderNote, 0 ,0);
                createPattern(newQueue);
            }
         }
         catch (Exception e) {}   
         this.g.setColor(Color.BLACK);
      }
   }   
   
      
      
      
      

     // interfacePrintNote(this.V0, Color.BLACK, false);
   
    /*
   * pulls up the create interface
   */
   public void pullCreateInterface()
   {
      File trebleClef = new File("treble.png");
      File bassClef = new File("bass.png");
      try
      {
         Image treble = ImageIO.read(trebleClef);
         this.g.drawImage(treble, 0, (2*HEIGHT/16), 52, 6*HEIGHT*1/16, null);
         Image bass  = ImageIO.read(bassClef);
         this.g.drawImage(bass, 10, HEIGHT - (7*HEIGHT/16), 42, (4*HEIGHT*1/16), null);
      }
      catch (Exception e)
      {
         System.out.println("Error: invalid file");
      }
      this.g.setColor(Color.BLACK);
      this.g.drawLine(0, HEIGHT - (13*HEIGHT/16), WIDTH, HEIGHT - (13*HEIGHT/16));
      this.g.drawLine(0, HEIGHT - (12*HEIGHT/16), WIDTH, HEIGHT - (12*HEIGHT/16));
      this.g.drawLine(0, HEIGHT - (11*HEIGHT/16), WIDTH, HEIGHT - (11*HEIGHT/16));
      this.g.drawLine(0, HEIGHT - (10*HEIGHT/16), WIDTH, HEIGHT - (10*HEIGHT/16));
      this.g.drawLine(0, HEIGHT - (9*HEIGHT/16), WIDTH, HEIGHT - (9*HEIGHT/16));
      
   
      this.g.drawLine(0, HEIGHT - (7*HEIGHT/16), WIDTH, HEIGHT - (7*HEIGHT/16));
      this.g.drawLine(0, HEIGHT - (6*HEIGHT/16), WIDTH, HEIGHT - (6*HEIGHT/16));
      this.g.drawLine(0, HEIGHT - (5*HEIGHT/16), WIDTH, HEIGHT - (5*HEIGHT/16));
      this.g.drawLine(0, HEIGHT - (4*HEIGHT/16), WIDTH, HEIGHT - (4*HEIGHT/16));
      this.g.drawLine(0, HEIGHT - (3*HEIGHT/16), WIDTH, HEIGHT - (3*HEIGHT/16));
   
   
      for (int i = 0; i < WIDTH/MEASURE; i++)
      {
         this.g.drawLine(60 + i*MEASURE, HEIGHT - (9*HEIGHT/16), 60 + i*MEASURE, HEIGHT - (13*HEIGHT/16));
         this.g.drawLine(60 + i*MEASURE, HEIGHT - (3*HEIGHT/16), 60 + i*MEASURE, HEIGHT - (7*HEIGHT/16));
      }
      createButtons();
   }   
}      
