# sheet-music-2020
Sheet Music Player/Creator/Editor in Java
This program plays, creates, and edits sheet music using a GUI in Java. This code was made in 2020 in 1-2 weeks for a final project of 
CS III in sophomore year of high school.

The main file can be found in SheetMusicMain.java. Before running this, you should be sure to compile the rest of the files. You should also be sure to import jfugue-5.0.9.jar into your Java.


## TEXT FILE INFORMATION ##
Each line of the text file begins with V0, V1, and so forth. Then there is a space, and there are "notes" separated by spaces. Each V represents a "thread" of notes being played at once, all happening simultaneously. Notes are denoted by length, octave, and pitch with different letters to represent each one. Take a look at ViennaMore.txt to get a rough look at a text file. The output is a rough version (transcribed by hand so not perfect) of Vienna by Billy Joel.

You can see from the notes that the pitch comes first (C, Eb, A#, etc.), then the octave (1-8), then the letter denoting the length (s for sixteenth, h for half,  i for eighth [to avoid confusion with e notes], etc.). Note that R means rest. Currently this code only supports V0-V3, so 4 threads, but this is easily alterable.

You can also alter the instrument, tempo, etc. within the code itself, they should be in the variable initialization section. Take a look at the jfugue documentation to see how you can change them: http://www.jfugue.org/download.html

Note that this current code has one main issue: the editor. In order for the editor to work correctly, you will need to enter the notes from left to right. This should be changed in future iterations of this code in the editor section of the code.
