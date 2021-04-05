/*
 Author: Mike O'Malley
 Source File: FibonacciMysteryBigIntegers.java
 Description: Explore the Fibonacci Mystery and more.
 Inspiration: Fibonacci Mystery - Numberphile, https://www.youtube.com/watch?v=Nu-lW-Ifyec
 GitHub:      https://github.com/MooseValley/Math---Fibonacci-Mystery

Ammendment History
Ver   Date        Author    Details
----- ----------- --------  ----------------------------------------------------
0.001 05-Apr-2021  Mike O    Created.  Works great, but limited to Long integers.
0.002 05-Apr-2021  Mike O    Change over to BigIntegers.
0.003 05-Apr-2021  Mike O    Calculate frequency of Period (Pattern Length) numbers.
0.004 05-Apr-2021  Mike O    Write all output to file: 'output.txt'.

*/
/*
https://www.youtube.com/watch?v=Nu-lW-Ifyec
Fibonacci Mystery - Numberphile
MoosesValley
Mon, 05-Apr-2021, 11:19 AM
The Fibonacci Sequence sure has hidden depths.  Fascinating video !  Thank you !!

MoosesValley
Mon, 05-Apr-2021, 12:47 PM
As happens when I watch a Numberphile or Computerphile video, my OCD has kicked in (again) ... and I just have to write my own code to explore everything in the video (and more).  Written a program to generate the 1st 20K+ Fib numbers and determine the Period Lengths for MOD 2, 3, 4, 5, ...  and finding some interesting results.  Also doing a frequency analysis of the Pattern lengths ... and finding more interesting results.  For example, most numbers appear in the list of Pattern Lengths only once, but some numbers appear very frequently.  e.g. 240 appears 219 times, 360 appears 169 times, and so on (for the numbers / lists I have generated so far).  Wonder why ?  Probably nothing that hasn't been found / seen before, but sure is interesting for me at least.  Anyway, uploading my Java code and results to Github if anyone else is interested.


https://en.wikipedia.org/wiki/Fibonacci
Fibonacci (c. 1170 � 1240�50) also known as Leonardo Bonacci, Leonardo of Pisa, or Leonardo Bigollo Pisano.

Pisano Period: you can divide by any positive integer > 1 and still get a repeating pattern of digits - but the length of the pattern would vary.

Fibonacci Numbers:
1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296, 433494437, 701408733, 1134903170, 1836311903, 2971215073, 4807526976, 7778742049, 12586269025, 20365011074, 32951280099, 53316291173, 86267571272, 139583862445, 225851433717, 365435296162, 591286729879, 956722026041, 1548008755920, 2504730781961, 4052739537881, 6557470319842, 10610209857723, 17167680177565, 27777890035288, 44945570212853, 72723460248141, 117669030460994, 190392490709135, 308061521170129, 498454011879264, 806515533049393, 1304969544928657, 2111485077978050, 3416454622906707, 5527939700884757, 8944394323791464, 14472334024676221, 23416728348467685, 37889062373143906, 61305790721611591, 99194853094755497, 160500643816367088, 259695496911122585, 420196140727489673, 679891637638612258, 1100087778366101931, 1779979416004714189, 2880067194370816120,

Mod 7 (period length = 16):   1, 1, 2, 3, 5, 1, 6, 0, 6, 6, 5, 4, 2, 6, 1, 0,
Mod 5 (period length = 20):   1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4, 4, 3, 2, 0, 2, 2, 4, 1, 0,
Mod 10 (i.e. get last digit, period length = 60):   1, 1, 2, 3, 5, 8, 3, 1, 4, 5, 9, 4, 3, 7, 0, 7, 7, 4, 1, 5, 6, 1, 7, 8, 5, 3, 8, 1, 9, 0, 9, 9, 8, 7, 5, 2, 7, 9, 6, 5, 1, 6, 7, 3, 0, 3, 3, 6, 9, 5, 4, 9, 3, 2, 5, 7, 2, 9, 1, 0,
MOD 100 (last 2 digits, period length = 300)


The period (the repeating pattern of digits) can only have 1 x 0, 2 x 0's, or 4 x 0's.

Fn | Fm iff n | m
The nth Fibonacci number "exactly divides" (no remainder) the mth Fibonacci number if and only if n "exactly divides" (no remainder) m.
Every 5th Fibonacci number (which just happens to be a 5) is evenly divisable by 5 !
Every 6th Fibonacci number (6) is evenly divisable by 8 !


The Fibonacci rule still holds for the repeating period sets above.
Fn = Fn-1 + Fn-2

When the digits go back to 1, 1, then you are at the start of the Fibonacci sequence and at the start of a period (the repeating pattern of digits) !

There is no general formula for the length of the period.

*/
import java.util.ArrayList;
import java.math.BigInteger;
import java.io.*;


public class FibonacciMysteryBigIntegers
{
   static final int MAX_SIZE = 20_000; // After this, they exceed the MAX Long integer.  i.e. overflow.

   static ArrayList<BigInteger> fibonacciSequenceArrayList = new ArrayList<BigInteger> ();


   // *** Copied from my: 00__common_code/Moose_Utils.java
   // strToFile, StringToFile
   // Example use:
   //   Moose_Utils.writeOrAppendStringToFile ("names.dat", "Mike\nFrankie\nBella", false);
   public static boolean writeOrAppendStringToFile (String fileName, String dataToWrite, boolean appendToFile)
   {
      BufferedWriter bw = null;
      FileWriter     fw = null;
      boolean result    = false; // ERROR / No data written.

      try
      {
         File file = new File(fileName);

         // if file doesnt exist, then create it:
         if (file.exists() == false)
         {
            file.createNewFile();
         }

         // if appendToFile is true, then append file.
         fw = new FileWriter(file.getAbsoluteFile(), appendToFile);
         bw = new BufferedWriter(fw);

         bw.write(dataToWrite);

         bw.close();
         fw.close();

         //System.out.println("Write to '" + fileName + "' = Done.");

         result  = true; // Data written.
      }
      catch (IOException e)
      {
         e.printStackTrace();
         result  = false; // ERROR / No data written.
      }
      finally
      {
         // Is this *really* necessary ?
         try
         {
            if (bw != null)
               bw.close();

            if (fw != null)
               fw.close();

         }
         catch (IOException ex)
         {
            ex.printStackTrace();
            result  = false; // ERROR / No data written.
         }
      }

      return result;
   } // public static boolean writeOrAppendStringToFile



   public static long displayFibonacciSequenceModN (int modValue)
   {
      long periodLength = -1; // None found !

      int priorNumberModded    = 0;
      int prepriorNumberModded = 0;

      BigInteger modValueBigInteger = new BigInteger ("" + modValue);

      // Display all MOD modValue:
      //System.out.print ("\n" + "Fibonacci Numbers MOD " + modValue + ":");
      for (int k = 0; k < MAX_SIZE; k++)
      {
         //System.out.print ( (fibonacciSequenceArrayList.get (k) % modValue) + ", ");
         //System.out.print ( fibonacciSequenceArrayList.get (k).mod (modValueBigInteger) + ", ");
         /*
         if (k             > 2)    // We are NOT at the start of the Fibonacci sequence
         {
            System.out.print ( fibonacciSequenceArrayList.get (k).mod (modValueBigInteger) +
               " ( " +
               fibonacciSequenceArrayList.get (k - 1).mod (modValueBigInteger) +
               ", " +
               fibonacciSequenceArrayList.get (k - 2).mod (modValueBigInteger) +
               ")" + ", ");
         }
         */

         if ((periodLength  < 0) &&   // We have not found the period yet.
             (k             > 2) &&   // We are NOT at the start of the Fibonacci sequence
             (fibonacciSequenceArrayList.get (k - 1).mod (modValueBigInteger).compareTo (BigInteger.ONE) == 0 ) && // The prior 2 digits were 1
             (fibonacciSequenceArrayList.get (k - 2).mod (modValueBigInteger).compareTo (BigInteger.ONE) == 0 ) )
         {
            periodLength = k - 2;
         }

      }
      //System.out.println ();
      //System.out.println (" Period Length = " + periodLength);

      return periodLength;
   }

   public static void main (String[] args)
   {
      // NOTE: This program needs to use x64 Java runtime.
      // Data too big for x32 bit.  StringBuilder runs out of space.

      StringBuilder sb    = new StringBuilder();

      sb.append ("\n");
      sb.append ("----------------------------------------------------------------" + "\n");
      sb.append ("Fibonacci Mystery Analysis - by Moose OMalley" + "\n");
      sb.append ("v0.04" + "\n");
      sb.append ("\n");
      sb.append ("GitHub: https://github.com/MooseValley/Math---Fibonacci-Mystery"  + "\n");
      sb.append ("----------------------------------------------------------------" + "\n");
      sb.append ("\n");

      sb.append ("NOTE: This program needs to use x64 Java runtime." + "\n");
      sb.append ("      Data too big for x32 bit.  StringBuilder runs out of space." + "\n");
      sb.append ("\n");

      sb.append ("java.version:    " + System.getProperty("java.version")           + "\n");
      sb.append ("32/64 bit:        x" + System.getProperty ("sun.arch.data.model") + "\n");
      sb.append ("java.vendor:     " + System.getProperty("java.vendor")            + "\n");
      sb.append ("java.vendor.url: " + System.getProperty("java.vendor.url")        + "\n");
      System.out.println(sb.toString() );



      // *** Part 1: generate Fibonacci Sequence numbers

      System.out.println ("\n" + "Fibonacci Sequence: generating the first " + MAX_SIZE + " numbers ...");
      sb.append ("\n");
      sb.append ("\n");
      sb.append ("Fibonacci Sequence: generating the first " + MAX_SIZE + " numbers ..." + "\n");

      fibonacciSequenceArrayList.add (new BigInteger ("1") );
      fibonacciSequenceArrayList.add (new BigInteger ("1") );

      for (int k = 2; k < MAX_SIZE; k++)
      {
         if (k % 1000 == 0)
            System.out.print ("."); // Print "." every 1,000 so I know all is OK, because things slow down for big numbers.

         BigInteger nextNum = new BigInteger ("" + fibonacciSequenceArrayList.get(k - 1) );
         nextNum = nextNum.add (fibonacciSequenceArrayList.get(k - 2) );

         fibonacciSequenceArrayList.add (nextNum );

         //sb.append (nextNum.toString() + ", ");
      }
      System.out.println ("\n" + "-> DONE !");
      //sb.append ("\n");
      sb.append ("-> Not output to reduce 'output.txt' file size." + "\n");
      sb.append ("-> DONE !" + "\n");


/*
      System.out.println ("\n" + "Fibonacci Numbers:");
      for (int k = 0; k < MAX_SIZE; k++)
      {
         System.out.print (fibonacciSequenceArrayList.get (k) + ", ");
      }
      System.out.println ();

      displayFibonacciSequenceModN (7);
      displayFibonacciSequenceModN (5);
      displayFibonacciSequenceModN (10);
*/


      // *** Part 2: determine Period Lengths (Repeating Pattern Lengths)

      StringAndCounter frequencyArray = new StringAndCounter (true, false); // Sorted list, NOT case sensitive.

      System.out.println ("\n" + "Fibonacci Sequence Repeating Pattern Lengths for Mod 2, 3, 4, 5, ... " + (MAX_SIZE / 2) );
      sb.append ("\n");
      sb.append ("Fibonacci Sequence Repeating Pattern Lengths for Mod 2, 3, 4, 5, ... " + (MAX_SIZE / 2) + "\n");
      for (int k = 2; k < MAX_SIZE / 2; k++)
      {
         if (k % 1000 == 0)
            System.out.print ("."); // Print "." every 1,000 so I know all is OK, because things slow down for big numbers.

         long patternLength = displayFibonacciSequenceModN (k);

         frequencyArray.addStringAndCount ("" + patternLength);

         //System.out.print (patternLength + ", ");
         sb.append (patternLength + ", ");
      }
      sb.append ("\n");
      sb.append ("-> DONE !" + "\n");
      System.out.println ("\n" + "-> DONE !");


      // *** Part 3: Period Lengths (Repeating Pattern Lengths) Frequency analysis

      System.out.println ("\n" + "Pattern Length Frequency:" );
      sb.append ("\n");
      sb.append ("Pattern Length Frequency:" + "\n");
      //System.out.println (frequencyArray.toString ());
      sb.append ("\n");
      sb.append (frequencyArray.toString () + "\n");
      sb.append ("\n");
      sb.append ("-> DONE !" + "\n");
      sb.append ("\n");
      System.out.println ("-> DONE !");
      System.out.println ();


      // *** Part 99: Write all output to file.

      writeOrAppendStringToFile ("output.txt", sb.toString(), false);
      System.out.println ();
      System.out.println ("All output written to file: 'output.txt'.");
      System.out.println ();

   }
}