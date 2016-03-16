Doodle!
=======
- Created by Dongwoo Son
- Created at Feb 29, 2016
- Email: d3son@uwaterloo.ca

Preview
-------
![alt text](http://dongwoo1005.github.io/image/Doodle.png "Doodle preview")


How to make and run
-------------------
1. Go to src directory
2. Use Makefile
    - Run "make" to compile the .java files
    - Run "make run" to compile and run
    - Run "make clean" to clean .class files

How to use it
-------------

### Canvas
- Click and Drag to draw a stroke

### Palette
- Select Color: Click on the square filled with a color
- Change Width: Click on the colored circle and drag up/down

### Playback
- Play:   play the strokes drawn so far with animation as it is being drawn (Point-to-point animation is supported. But there are some minor unknown issue with the timer that keeps running sometimes despite of killing it when necessary)
- Slider: move it to go back/forward as you wish
- Start:  go to the beginning of the drawing
- End:    go to the end of the drawing
- Buttons are disabled as necessary when it can't be used

### Menubar
- File
	- New:  Prompt to save changes and make a new file
	- Load: Prompt to load saved files
	- Save: Prompt to save files
			(Only binary file save is supported at the moment)
	- Exit: Exit the program
- View
	- Actual Size:   The original image size will be shown
	- Fit to Screen: The original image will be scaled to fit to screen

More description
----------------
### Layout
- GridBagLayout for the main frame panel consist of Playback and MainPanel
	- MainPanel is set to OverlayLayout which is composed with Canvas and Palette on top of Canvas
	- Tried to set the mainPanel ScrollPane for full size, but it is not working at the moment.

- Playback panel will be resized accordingly to the frame resize
- Palette will be repositioned accordingly to the frame resize
