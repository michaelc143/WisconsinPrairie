import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;

public class WisconsinPrairie{
	
	private static PApplet processing; // PApplet object that represents the graphic
	// interface of the WisconsinPrairie application
	private static PImage backgroundImage; // PImage object that represents the
	// background image
	private static Cow[] cows; // array storing the current cows present
	// in the Prairie
	private static Random randGen; // Generator of random numbers
	/**
	* Defines the initial environment properties of the application
	* @param processingObj represents a reference to the graphical interface of
	* the application
	*/
	public static void setup(PApplet processingObj)
	{
		processing = processingObj; // initialize the processing field to the one passed
		// into the input argument parameter.
		// initialize and load the image of the background
		backgroundImage = processing.loadImage("images/background.png");
		// Draw the background image at the center of the screen
		
		//create new array of cows
		cows = new Cow[10];

		//generate a random number object
		randGen = new Random();

		// Draw the background image at the center of the screen
		processing.image(backgroundImage, processing.width / 2, processing.height / 2);
	}
	
	/**
	* Draws and updates the application display window.
	* This callback method called in an infinite loop.
	*/
	public static void draw()
	{
		processing.image(backgroundImage, processing.width / 2, processing.height / 2);
		// width [resp. height]: System variable of the processing library that stores
		// the width [resp. height] of the display window.
		//draws all the cows in the array cows
		for(int i=0; i<cows.length;i++)
		{
			if(cows[i]!=null)
				cows[i].draw();
		}
	}
	
	/**
	* Checks if the mouse is over a given cow whose reference is provided
	* as input parameter
	*
	* @param cow reference to a given cow object
	* @return true if the mouse is over the given cow object (i.e. over
	* the image of the cow), false otherwise
	*/
	public static boolean isMouseOver(Cow cow) 
	{
		boolean mouseOnCow = false; //starts with mouse not on the cow image
		
		//find the exact coordinates of the x and y of cow
		float cowPosX = cow.getPositionX();
		float cowPosY = cow.getPositionY();
		
		//find height and length of cow images
		float cowHeight = cow.getImage().height;
		float cowWidth = cow.getImage().width;
		
		//finds the left and right edges of the cow image
		float cowLeftBound = cowPosX-(cowWidth/2);
		float cowRightBound = cowPosX+(cowWidth/2);
		
		//finds the top and bottom of the cow image
		float cowTopBound = cowPosY+(cowHeight/2);
		float cowBottomBound = cowPosY-(cowHeight/2);
				
	
		//if mouse is within the area of a cow, returns true
		if(processing.mouseX>cowLeftBound&&processing.mouseX<cowRightBound
			&&processing.mouseY>cowBottomBound&&processing.mouseY<cowTopBound)
		{
			mouseOnCow=true;
		}
		return mouseOnCow;
	}
	/**
	* Callback method called each time the user presses the mouse
	*/
	public static void mousePressed() 
	{
		//the smallest index of what the mouse is clicking on
		int smallestIndex;
		for(int w=0; w<cows.length; w++)
		{
			//if the mouse is over cows at index w and has a cow at index W, sets smallestIndex equal to that index
			if (cows[w]!=null&&isMouseOver(cows[w])==true)
			{
				smallestIndex=w;
				for(int m=0;m<cows.length;m++)
				{
					if((cows[m]!=null&&isMouseOver(cows[m])&&m!=smallestIndex))
					{
						if(m<smallestIndex)
							smallestIndex=m;
					}
				}
				cows[smallestIndex].setDragging(true);
				
			}
				
		}
	}
	/**
	* Callback method called each time the mouse is released
	*/
	public static void mouseReleased() 
	{
		for(int p=0; p<cows.length; p++)
		{
			//sets all cows to not being dragged
			if(cows[p]!=null)
			cows[p].setDragging(false);
		}
	}
	
	/**
	* Callback method called each time the user presses a key
	*/
	public static void keyPressed() 
	{
		randGen = new Random();
		//if user types C or c then the program creates a cow at the lowest index at a random place on the screen
		if((processing.key==('c')||processing.key==('C')))
		{
			for(int m=0;m<cows.length;m++)
			{
				if(cows[m]==null)
				{
					cows[m]=new Cow(processing,(float)randGen.nextInt(processing.width),(float)randGen.nextInt(processing.height));
					break;
				}	
			}
		}
		//if user types d or D key, then the program deletes a cow at the highest index and sets that index equal to null
		if((processing.key==('d')||processing.key==('D')))
		{
			for(int w=0;w<cows.length;w++)
			{
				if(cows[w]!=null&&isMouseOver(cows[w]))
				{
					cows[w]=null;//deletes cow whenever mouse is over cow
					break;
				}
			}
		}
	}
	
	public static void main(String[]args)
	{
		Utility.startApplication();
	}

}
