import java.util.*;
import java.text.*;
public class readingTool{
  public static void main(String[]args){
    Scanner input = new Scanner(System.in);
		System.out.print("\033[H\033[2J");
		/*--------------------------------------------------------------------------------------------
								Setting up pre-req info
		--------------------------------------------------------------------------------------------*/
		System.out.println("Welcome. Please enter the subject you are studying right now:");
		String subject = input.nextLine();
		System.out.println();

		System.out.println("Please enter the chapter number you are reading: ");
		int chapNum = input.nextInt();
		System.out.println();

		System.out.println("How many pages are there in this chapter?");
		int chapPage = input.nextInt();
		System.out.print("\033[H\033[2J");
		DecimalFormat df = new DecimalFormat("##.##");

		/*--------------------------------------------------------------------------------------------
								Iterations
		--------------------------------------------------------------------------------------------*/
    int i_val=0;
    for(float i = -1; i<=chapPage;){
      System.out.print("\033[H\033[2J");
			System.out.println("Studying: " + subject + " Chapter " + chapNum);
			float percent = i/chapPage*100;
			System.out.println();
			System.out.println("Pages read: "+(int)i+"/"+chapPage);
			System.out.print("Percent Completed: " + df.format(percent) +"%");
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
