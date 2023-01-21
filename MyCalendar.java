/*******************************************************************************************************
 * MyCalendar.java
 *
 * A program to print out a monthly calendar given the input of year and month (as integers).
 * The program works for years > 1600 and < 2800.
 *
 *   Date:  May 2021
 ******************************************************************************************************/

import java.util.*;
import java.io.*;


public class MyCalendar {

	// Declare arrays for global use

	public static void main(String[] args) {

		// Scanner object for input
		Scanner myScan = new Scanner(System.in);
		
		System.out.println("Enter 'FR' to use the French calendar, and 'EN'to have it in English: ");  // This block of code makes the user enter which language they want the calendar to be in
		String lang = myScan.nextLine();
		while (!lang.toLowerCase().equals("fr") && !lang.toLowerCase().equals("en"))
		{
			System.out.println("Enter FR to use the French calendar, and EN to have it in English: ");
			lang = myScan.nextLine();
		}	// end while
		
		System.out.println("Enter 'large' for a large calendar and 'small' for a small calendar: ");  // Makes the user enter the desired size of the calendar
		String calendarSize = myScan.nextLine();
		while (!calendarSize.toLowerCase().equals("small") && !calendarSize.toLowerCase().equals("large"))
		{
			System.out.println("Enter 'large' for a large calendar and 'small' for a small calendar: ");
			calendarSize = myScan.nextLine();
		}	// end while
		
		int size = 0;  // initializing a variable to change the size of grids in the calendar
		
		if (calendarSize.toLowerCase().equals("large"))  // makes the space blocks take up 10 spaces if large is chosen
		{
			size = -10;
		} 
		else if (calendarSize.toLowerCase().equals("small"))  // makes the space blocks take up 4 spaces if small is chosen
		{
			size = -4;
		}
		
		String[] month = getMonth("monthNames");  // fills the month array with 
		if(lang.toLowerCase().equals("fr")) 
		{
			month = getMonth("monthNames_FR");
		}
				
		int[] daysOfMonth = getDaysOfMonth();  // fills the amount of days in each month into the daysOfMonth array
			
		String[] date;	// initializing the date array to allow the user to enter date
		int monthNum = 0;
		int inYear = 0;
		
		while (true) 	// loop to make the user enter valid dates
		{
			System.out.println("Enter the year, or month and year for which you would like to create a calendar: ");
			date = myScan.nextLine().split(" ");
		
			if (date.length == 1) // if only a year was entered
			{
				try 	// try catch block to ensure the integer is parsed correctly
				{
					inYear = Integer.parseInt(date[0]);
				} 
				catch (NumberFormatException err) 
				{
					System.out.println("Invalid Input.");
				}
			}
			
			if (date.length == 2) // if a month and year is entered
			{
				switch (date[0].toLowerCase()) 		// switch case statement to convert the month strings into numbers to represent each month
				{
					case "jan":
					case "january":
						monthNum = 1;
						break;
					case "feb":
					case "february":
						monthNum = 2;
						break;
					case "mar":
					case "march":
						monthNum = 3;
						break;
					case "apr":
					case "april":
						monthNum = 4;
						break;
					case "may":
						monthNum = 5;
						break;
					case "jun":
					case "june":
						monthNum = 6;
						break;
					case "jul":
					case "july":
						monthNum = 7;
						break;
					case "aug":
					case "august":
						monthNum = 8;
						break;
					case "sep":
					case "september":
						monthNum = 9;
						break;
					case "oct":
					case "october":
						monthNum = 10;
						break;
					case "nov":
					case "november":
						monthNum = 11;
						break;
					case "dec":
					case "december":
						monthNum = 12;
						break;
					default:
						System.out.println("Enter a valid month.");
						continue;
				} 
				
				try 
				{
					inYear = Integer.parseInt(date[1]);
				} 
				catch (NumberFormatException err) 
				{
					System.out.println("Enter a valid number.");
				}
				
			} // end else if
			
			if (inYear <= 2800 && inYear >= 1600 && monthNum <= 12 && monthNum >= 0) {
				break;
			} // end if
			
			System.out.println("You have entered a year outside of the range of 1600 to 2800 or an invalid month.");
			
		} // end while
		
		myScan.close();
		
		if (date.length == 2) // choosing which method to use based on the format of date entered
		{
			printCalendar(daysOfMonth, month, inYear, monthNum, lang, size);
			System.out.println("A calendar for the month of " + date[0] + " " + inYear + " has succesfully been created.");
		}
		else if (date.length == 1)
		{
			printCalendar(daysOfMonth, month, inYear, lang, size);
			System.out.printf("A calendar for the year of " + inYear + " has succesfully been created.");
		}
	} // end main
		
	/*******************************************************************************************************
	 * getDayOfWeek
	 *
	 * This method calculates to find the day of the week given the month, day and
	 * year
	 * It only works for dates from years 1600 to 2800 inclusive
	 * It returns the day of the week as an integer (0 for Mon, 1 for Tue...)
	 *
	 * See http:\\worldmentalcalculation.com/how-to-calculate-calendar-dates/  for the calculation
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return calculation of the day of the week
	 ******************************************************************************************************/

	private static int getDayOfWeek(int year, int month, int day)
	{
		// Initialize variables
		int dayOfWeek = 0;
		int cCentury = 0;
		int cYear = 0;
		int cMonth = 0;
		int cDay = day;

		// Calculate the Century component

		if ((year >= 1600 && year < 1700) || (year >= 2000 && year < 2100) || (year >= 2400 && year < 2500))
			cCentury = 2;
		else if ((year >= 1800 && year < 1900) || (year >= 2200 && year < 2300) || (year >= 2600 && year < 2700))
			cCentury = 5;
		else if ((year >= 1900 && year < 2000) || (year >= 2300 && year < 2400) || (year >= 2700 && year < 2800))
			cCentury = 3;

		// Calculate the Year component

		cYear = (((year % 100) / 4) + (year % 100)) % 7;

		// Calculate the Month component

		switch (month)
		{
			case 1:
			case 10:
				cMonth = 4;
				break;
			case 2:
			case 3:
			case 11:
				cMonth = 0;
				break;
			case 4:
			case 7:
				cMonth = 3;
				break;
			case 5:
				cMonth = 5;
				break;
			case 6:
				cMonth = 1;
				break;
			case 8:
				cMonth = 6;
				break;
			case 9:
			case 12:
				cMonth = 2;
				break;
		}

		// Calculate the Day component

		cDay = day % 7;

		// Return the calculation of the day of the week

		dayOfWeek = ((cCentury + cYear + cMonth + cDay) % 7);

		//  Adjust for a leap year

		if ((year % 4 == 0) && (month == 1 || month == 2))
		{
			if (dayOfWeek == 0)
				dayOfWeek = 6;
			else
				dayOfWeek--;
		}

		dayOfWeek = dayOfWeek % 7;

		return dayOfWeek;
	}
	
	/*******************************************************************************************************
	 * printCalendar
	 *
	 * This method outputs the formatted calendar to a file
	 *
	 * @param daysOfMonth[]
	 * @param month[]
	 * @param inYear
	 * @param monthNum
	 * @param langStatus
	 * @param size
	 ******************************************************************************************************/

	private static void printCalendar (int[]daysOfMonth, String[] month, int inYear, int monthNum, String langStatus, int size) 
	{
		// Call to method to return the day of the week (as a number 0 - 6), that the month starts on,
		// given the year, month and day (1 to get the first day of the month)
		// Method can be used to get the day of the week for any year, month and day
	
		try 
		{	
			// initialize files, printWriter, and the name of the file based on entered year and month
			String fileName = "Calendar_" + inYear + "_" + monthNum;
			File  outputFile = new File(fileName);
			FileWriter fileWriter = new FileWriter(outputFile);
			PrintWriter printWriter = new PrintWriter(fileWriter, true);
			String language = langStatus;
			int sizeValue = size;
			
			int dayOfWeek = getDayOfWeek(inYear, monthNum, 1);
		
			// Print calendar month
			printWriter.println("\n\n" + inYear + "\n" + month[monthNum - 1]);
			printWriter.println("---------------------------");
			
			// if statement to print the days of the week on the calendar based on selected language
			if (language.toLowerCase().equals("fr"))
			{
				printWriter.printf("%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%n","Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam");
			}
			else 
			{
				printWriter.printf("%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%n","Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
			}
			
			// Loops to iterate through the days and weeks of the month
			int j = 0;                           //  Counts the calendar spaces
			int dayCount = 1;                    //  Counts the days of the month
	
			for (int i = 1; i < 7; i++)          //  There are a maximum of 6 rows in the calendar
			{
				for (int k = 1; k < 8; k++)      //  Print out a week each loop
				{
					if ((j >= dayOfWeek) && (dayCount <= daysOfMonth[monthNum - 1]))
					{
						printWriter.printf("%" + size + "s", dayCount);
						dayCount++;
					}
					else
					{
						printWriter.printf("%" + size + "s", "    ");
					}
					j++;
				}
				printWriter.println("");			//  After printing a week move to a new line
				
				if (sizeValue == -10) 
				{
					printWriter.printf("%n%n%n%n");
				}
			}	
			
			printWriter.close();
		}
		catch (IOException e) 
		{
			System.out.println("Could not create a file to output the calendar.");
			System.exit(-1);
		}
	}
	
	/*******************************************************************************************************
	 * printCalendar
	 *
	 * This method outputs the formatted calendar to a file
	 *
	 * @param daysOfMonth[]
	 * @param month[]
	 * @param inYear
	 * @param langStatus
	 * @param size
	 ******************************************************************************************************/

	private static void printCalendar (int[]daysOfMonth, String[] month, int inYear, String langStatus, int size) 
	{
		// Call to method to return the day of the week (as a number 0 - 6), that the month starts on,
		// given the year, month and day (1 to get the first day of the month)
		// Method can be used to get the day of the week for any year, month and day
	
		try {
			
			// initialize the files and printWriter, also the string to decide file name of output based on the year entered
			String fileName = "Calendar_" + inYear;
			File  outputFile = new File(fileName);
			FileWriter fileWriter = new FileWriter(outputFile);
			PrintWriter printWriter = new PrintWriter(fileWriter, true);
			String language = langStatus;
			int sizeValue = size;
			
			// for loop to make the program print a calendar for every month of the year
			for (int monthNum = 1; monthNum < 13; monthNum++)
			{
				int dayOfWeek = getDayOfWeek(inYear, monthNum, 1);
			
				// Print calendar month
			
				printWriter.println("\n\n" + inYear + "\n" + month[monthNum - 1]);
				printWriter.println("---------------------------");
				
				// if statement to print the days of the week on the calendar based on selected language
				if (language.toLowerCase().equals("fr"))
				{
					printWriter.printf("%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%n","Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam");
				}
				else 
				{
					printWriter.printf("%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%" + size + "s%n","Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
				}
				
				// Loops to iterate through the days and weeks of the month
				int j = 0;                           //  Counts the calendar spaces
				int dayCount = 1;                    //  Counts the days of the month
		
				for (int i = 1; i < 7; i++)          //  There are a maximum of 6 rows in the calendar
				{
					for (int k = 1; k < 8; k++)      //  Print out a week each loop
					{
						if ((j >= dayOfWeek) && (dayCount <= daysOfMonth[monthNum - 1]))
						{
							printWriter.printf("%" + size + "s", dayCount);
							dayCount++;
						}
						else
						{
							printWriter.printf("%" + size + "s", "    ");
						}
						j++;
					}
					printWriter.println("");			//  After printing a week move to a new line
					
					if (sizeValue == -10) 
					{
						printWriter.printf("%n%n%n%n");
					}
				}	
			}
			
			printWriter.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Could not create a file to output the calendar.");
			System.exit(-1);
		}
	}
	
	/*******************************************************************************************************
	 * getMonth
	 *
	 * This method fills an array with the names of the months from a file
	 *
	 * @param fileName
	 * @return array with the correct values inserted
	 ******************************************************************************************************/
	
	private static String[] getMonth (String fileName) 
	{
		// initializes the month array to fill with names of every month
		String[] month = {};
		
		try {		// try catch loop to ensure the monthNames file is opened or a message is shown if there is an exception
			File monthNames = new File(fileName);
			Scanner monthNameScan = new Scanner(monthNames);
			
			while (monthNameScan.hasNext()) 	// keeps adding months into array if there are tokens in the file
			{
				month = Arrays.copyOf(month, month.length + 1);
				month[month.length - 1] = monthNameScan.next();
			}
			
			monthNameScan.close();
		} catch (IOException e) {
			System.out.println("Could not open the monthNames file.");
			System.exit(-1);
		}
		
		return month;
	}
	
	/*******************************************************************************************************
	 * getDaysOfMonth
	 *
	 * This method fills an array with the number of days in each month
	 *
	 * @return array with the correct values inserted
	 ******************************************************************************************************/
	
	private static int[] getDaysOfMonth ()
	{
		int[] daysOfMonth = {};		// initializing the array which holds the number of days in each months
		
		try {
			
			// initializing the file and scanner
			File daysInMonth = new File("daysInMonth");
			Scanner daysScan = new Scanner(daysInMonth);
			
			while (daysScan.hasNext()) 		// keeps adding day integers into array if there are tokens in the file
			{
				daysOfMonth = Arrays.copyOf(daysOfMonth, daysOfMonth.length + 1);
				daysOfMonth[daysOfMonth.length - 1] = daysScan.nextInt();
			}
			
			daysScan.close();			
			
		} catch (IOException e) {
			System.out.println("Unable to open daysInMonth.");
			System.exit(-1);
		}
		
		return daysOfMonth;
	}
} // end myCalendar