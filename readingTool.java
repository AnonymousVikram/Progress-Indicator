import java.util.*;
import java.text.*;

public class readingTool{
  public static String subject;
  public static int chapNum;
  public static int chapPage;
  public static long startMillis;

  public static void setup(Scanner input){
    System.out.print("\033[H\033[2J");
    System.out.println("Welcome. Please enter the subject you are studying right now:");
		subject = input.nextLine();
		System.out.println();

		System.out.println("Please enter the chapter number you are reading: ");
		chapNum = input.nextInt();
		System.out.println();

		System.out.println("How many pages are there in this chapter?");
		chapPage = input.nextInt();
		System.out.print("\033[H\033[2J");
    startMillis = System.currentTimeMillis()%86400000;
  }

  public static String getTime(){
    long millisNow = System.currentTimeMillis()%86400000;
    long hoursNow = (millisNow-millisNow%3600000)/3600000;
    hoursNow +=8;
    hoursNow = hoursNow%24;
    boolean isMorning;
    String amPm;
    if(hoursNow<12){
      isMorning = true;
      amPm = "am";
    }else{
      isMorning = false;
      amPm = "pm";
      hoursNow -= 12;
    }
    long minutesNow = millisNow%3600000;
    minutesNow = (minutesNow-minutesNow%60000)/60000;
    DecimalFormat minuteFormat = new DecimalFormat("##");
    String finalMinutes = minuteFormat.format(minutesNow);

    String returner = "";
    returner = returner.concat(Long.toString(hoursNow));
    returner = returner.concat(":");
    returner = returner.concat(finalMinutes);
    returner = returner.concat(" ");
    returner = returner.concat(amPm);


    return returner;
  }
  public static void printInterface(float i, DecimalFormat df){
    System.out.print("\033[H\033[2J");
    System.out.println("Time: ".concat(getTime()).concat("\n"));
    System.out.println("Studying: " + subject + " Chapter " + chapNum);
    float percent = i/chapPage*100;
    System.out.println();
    System.out.println("Pages read: "+(int)i+"/"+chapPage);
    System.out.print("Percent Completed: " + df.format(percent) +"%");
  }

  public static void main(String[]args){
    Scanner input = new Scanner(System.in);
		/*--------------------------------------------------------------------------------------------
								Setting up pre-req info
		--------------------------------------------------------------------------------------------*/
		setup(input);
		DecimalFormat df = new DecimalFormat("##.##");

		/*--------------------------------------------------------------------------------------------
								Iterations
		--------------------------------------------------------------------------------------------*/
    int i_val=0;
    for(float i = -1; i<=chapPage;){
      printInterface(i, df);
      if(i==chapPage){
          i_val = (int) i;
          break;
      }
      else{
        input.nextLine();
        i++;
      }
    }

    if(i_val==chapPage){
      System.out.println("\nCongratulations! You've finished your reading. Thank you for using this program.");
    } else{
      System.out.println("\nUnknown Error: Please run the program again. Sorry about the inconvenience caused.");
    }
  }
}
