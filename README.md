# Sheet Music Utility in Java (sheet-music-2020)

## Introduction

The Sheet Music Utility in Java is a multifunctional software tool for managing various aspects of sheet music, including playback, composition, and editing. Developed in 2020 as part of a final project during a sophomore year Computer Science III course, this utility offers a versatile solution for musicians, composers, and enthusiasts.

## Getting Started

To start using the Sheet Music Utility, you can access the main program through `SheetMusicMain.java`. Ensure a successful execution by compiling all required files and importing the `jfugue-5.0.9.jar` library into your Java environment.

## Supported Functions

This utility incorporates several essential functions and classes:

### 1. SheetMusic Class

The SheetMusic class serves as the core of the utility, offering numerous features:

- `setMidi(File f)`: Set the output MIDI file.
- `setText(PrintStream ps)`: Set the output text file.
- `combineStr()`: Combine multiple notes into a single string for further processing.
- `printNote(String duration, int currentX, int currentY, String accidentalStr, Note currentNote)`: Print individual notes on the sheet music.
- `strToNotes()`: Convert the sheet music string into note objects, handling chords and headers.
- `toMidi()`: Export the sheet music to a MIDI file.
- `toText()`: Export the sheet music to a text file.
- `playNotes()`: Play the notes from the sheet music.
- `printMeasures()`: Print the measures on the sheet music.

### 2. Supporting Libraries and Dependencies

The utility relies on several external libraries and dependencies:

- `java.util.*`: Java's standard utility library.
- `java.awt.*`: Java's Abstract Window Toolkit for graphical operations.
- `java.io.*`: Java's input and output functionality.
- `javax.imageio.ImageIO`: Java's image I/O library for image-related operations.
- `java.awt.image.BufferedImage`: Represents images in memory.
- `org.jfugue.player.Player`: JFugue library for playing music.
- `org.jfugue.pattern.Pattern`: JFugue library for working with music patterns.
- `org.jfugue.midi.MidiFileManager`: JFugue library for managing MIDI files.
- `org.jfugue.theory.Intervals`: JFugue library for working with musical intervals.
- `org.jfugue.theory.Chord`: JFugue library for chord manipulation.
- `org.jfugue.theory.Note`: JFugue library for note representation.
- `javax.sound.midi.InvalidMidiDataException`: Java's MIDI exception handling.
- `javax.sound.midi.*`: Java's MIDI library for MIDI-related operations.

### 3. Constants

Several constants are used within the utility:

- `HEIGHT` and `WIDTH`: Define the height and width of the drawing panel.
- `MEASURE`: Represents the pixel size of each measure.
- `accidentalIdentity`: Stores the accidental for notes from the creator.
- `unit`: Establishes a relationship to the height, determining note size and staff dimensions.
- `noteIdentity`: Represents the duration of notes in the creator.

## Conclusion

The Sheet Music Utility in Java empowers users to interact with sheet music efficiently. Whether you need to play, compose, or edit musical compositions, this utility offers a robust set of features. Explore, utilize, and enhance this tool to elevate your musical endeavors. Enjoy your journey in the world of sheet music!
