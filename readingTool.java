import java.util.*;
import java.text.*;

public class readingTool{
  public static String subject;
  public static int chapNum;
  public static int chapPage;
  public static long startMillis;
  public static ArrayList<Long> readingTimes = new ArrayList<Long>();
  public static ArrayList<Integer> excludeAverage = new ArrayList<Integer>();
  public static long tempMillis;

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
    startMillis = System.currentTimeMillis();
    tempMillis = startMillis;
  }

//estimation algo to try to count out a few outliers
  public static long createEstimate(float pagesRead){
    long minimum = System.currentTimeMillis();
    long maximum = 0;
    int minIndex = 0;
    int maxIndex = 0;
    int sum = 0;
    int capacity = 0;
    for(int i = 0; i < readingTimes.size(); i++){
      if(excludeAverage.size()+1 > readingTimes.size()*0.15){
        break;
      }
      else{
        float temp = readingTimes.size()*0.15F;
        capacity = (int) (temp-(temp%1));
      }

      if(readingTimes.get(i) < minimum){
        minimum = readingTimes.get(i);
        minIndex = i;
      }
      if(readingTimes.get(i) > maximum){
        maximum = readingTimes.get(i);
        maxIndex = i;
      }
    }

    excludeAverage.add(minIndex);
    excludeAverage.add(maxIndex);

    for(int i = 0; i < readingTimes.size(); i++){

      if((readingTimes.get(i) <= minimum + 60000) || (readingTimes.get(i) >= maximum - 60000)){
        excludeAverage.add(i);
      }
    }


    int excluded = 0;
    int numOfNums = 0;
    for(int i = 0; i < readingTimes.size(); i++){
      boolean addToSum = true;
      for(int j = 0; j < excludeAverage.size(); j++){
        if((i==excludeAverage.get(j)) && (excluded < capacity)){
          addToSum = false;
          break;
        }
      }

      if(addToSum){
        sum += readingTimes.get(i);
        numOfNums++;
      }
      else{
        excluded++;
      }
    }
    if(numOfNums == 0){
      return 0;
    }
    else{
      long average = (long) sum/numOfNums;
      return average;
    }
  }

  public static String getTime(boolean timeElapsed, long timeDifference){
    if(timeElapsed){
      long hoursElapsed = (timeDifference-timeDifference%3600000)/3600000;
      long minutesElapsed = timeDifference%3600000;
      SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
      String finalMinutes = minuteFormat.format(minutesElapsed );
      String returner = "";
      if(hoursElapsed>0){
        returner = returner.concat(Long.toString(hoursElapsed));
        if(hoursElapsed==1){
            returner = returner.concat(" hour, ");
        }
        else{
        returner = returner.concat(" hours, ");
        }
      }
      returner = returner.concat(finalMinutes);
      returner = returner.concat(" minutes");

      return returner;
    }
    else{
      long millisNow = System.currentTimeMillis()%86400000;
      long hoursNow = (millisNow-millisNow%3600000)/3600000;
      hoursNow +=8;
      hoursNow = hoursNow%24;
      String amPm;
      if(hoursNow<12){
        amPm = "am";
      }
      else{
        amPm = "pm";
        hoursNow -= 12;
      }
      long minutesNow = millisNow%3600000;
      SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
      String finalMinutes = minuteFormat.format(minutesNow);

      String returner = "";
      returner = returner.concat(Long.toString(hoursNow));
      returner = returner.concat(":");
      returner = returner.concat(finalMinutes);
      returner = returner.concat(" ");
      returner = returner.concat(amPm);

      return returner;
    }
  }


  public static void printInterface(float i, DecimalFormat df){
    System.out.print("\033[H\033[2J");
    System.out.println("Time: " + getTime(false,0));
    System.out.println("Time Elapsed: " + getTime(true,System.currentTimeMillis()-startMillis));
    long estimateMillis = createEstimate(i)*(chapPage-(int)i);
    System.out.println("Estimated Time Remaining: " + getTime(true, estimateMillis));
    System.out.println("Studying: " + subject + " Chapter " + chapNum);
    System.out.println();
    System.out.println("Pages read: "+(int)i+"/"+chapPage);

    float percent = i/chapPage*100;
    System.out.print("Percent Completed: " + df.format(percent) +"%");
  }

  public static void main(String[]args){
    Scanner input = new Scanner(System.in);
		setup(input);
		DecimalFormat df = new DecimalFormat("##.##");
    int i_val=0;
    for(float i = -1; i<=chapPage;){
      printInterface(i, df);
      if(i==chapPage){
          i_val = (int) i;
          break;
      }
      else{
        input.nextLine();
        readingTimes.add(System.currentTimeMillis()%86400000-tempMillis);
        tempMillis = System.currentTimeMillis()%86400000;
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
