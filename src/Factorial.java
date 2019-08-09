import java.math.BigInteger;
import javax.swing.*;
import java.io.PrintWriter;
import java.awt.*;
import java.io.*;
import java.lang.*;

import javax.swing.table.AbstractTableModel;
public class Factorial{

    //
    public static String parsedString = "";

    /* initializes variables to tell signal thread completion*/
    public volatile static boolean threadOneDone = true;
    public volatile static boolean threadTwoDone = true;
    public volatile static boolean threadThreeDone = true;
    public volatile static boolean threadFourDone = true;
    public volatile static boolean threadFiveDone = true;
    public volatile static boolean threadSixDone = true;
    public volatile static boolean threadSevenDone = true;
    public volatile static boolean threadEightDone = true;

    /* Initializes variables that decide thread workload */
    public static int a1end = 0;
    public static int b1end = 0;
    public static int c1end = 0;
    public static int d1end = 0;
    public static int e1end = 0;
    public static int f1end = 0;
    public static int g1end = 0;
    public static int h1end = 0;

    /*Initializes output BigIntegers*/
    public volatile static BigInteger a1output = new BigInteger ("1");
    public volatile static BigInteger b1output = new BigInteger ("1");
    public volatile static BigInteger c1output = new BigInteger ("1");
    public volatile static BigInteger d1output = new BigInteger ("1");
    public volatile static BigInteger e1output = new BigInteger ("1");
    public volatile static BigInteger f1output = new BigInteger ("1");
    public volatile static BigInteger g1output = new BigInteger ("1");
    public volatile static BigInteger h1output = new BigInteger ("1");

    public static void main (String args[]) throws Exception{

        /* Initializes threads */
        FirstThread a1 = new FirstThread();
        SecondThread b1 = new SecondThread();
        ThirdThread c1 = new ThirdThread();
        FourthThread d1 = new FourthThread();
        FifthThread e1 = new FifthThread();
        SixthThread f1 = new SixthThread();
        SeventhThread g1 = new SeventhThread();
        EighthThread h1 = new EighthThread();

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println(cores+" logical cores");

        BigInteger output = new BigInteger("0");
        boolean loop=true;
        int N = 1;
        JFrame frame = new JFrame();
        String displaymessage = "Input number to factorialize";
        while(loop) {
            frame = new JFrame();
            frame.setBounds(100, 100, 650, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            String input = "exit";
            input = (JOptionPane.showInputDialog(displaymessage));
            try {
                if (input.equals("exit")) {
                    System.exit(0);
                }
            }catch(Exception e){System.exit(0);}
            try {
                N = Integer.parseInt(input);
            } catch (Exception e) {
                N = 0;
            }
            if (N>0){
                loop=false;
            } else {
                displaymessage = "Invalid input, please enter a whole number greater than 0";
            }
        }
        long timerstart = System.currentTimeMillis();
        //progbar(N);
        System.out.println("Calculating factorial");

        boolean multiplythreads = true;

        // Use single core mode if number is under 30000
        if (N<=30000){
            cores = 1;
        }


        if (cores>=16) {
            System.out.println("16 or more logical cores, running 8 threads");

            /* sets threads as not done */
            threadOneDone = false;
            threadTwoDone = false;
            threadThreeDone = false;
            threadFourDone = false;
            threadFiveDone = false;
            threadSixDone = false;

            /* sets value of variable controlling each thread's load */
            a1end = (Math.round(N/8));
            b1end = (Math.round(N/4));
            c1end = (Math.round((N/8)*3));
            d1end = (Math.round((N/2)));
            e1end = (Math.round((N/8)*5));
            f1end = (Math.round((N/4)*3));
            g1end = (Math.round((N/8)*7));
            h1end = N;

            //initializes progress bar
            progbar(a1end);

            /* starts running the threads */
            a1.start();
            b1.start();
            c1.start();
            d1.start();
            e1.start();
            f1.start();
            g1.start();
            h1.start();
        } else {
            if (cores>=12){
                System.out.println("12-15 logical cores, running 6 threads");

                /* sets threads as not done */
                threadOneDone = false;
                threadTwoDone = false;
                threadThreeDone = false;
                threadFourDone = false;
                threadFiveDone = false;
                threadSixDone = false;

                /* sets value of variable controlling each thread's load */
                a1end = (Math.round(N/6));
                b1end = (Math.round(N/3));
                c1end = (Math.round(N/2));
                d1end = (Math.round((N/3)*2));
                e1end = (Math.round((N/6)*5));
                f1end = N;

                //initializes progress bar
                progbar(a1end);

                /* starts running the threads */
                a1.start();
                b1.start();
                c1.start();
                d1.start();
                e1.start();
                f1.start();
            } else {
                if (cores>=8){
                    System.out.println("8-11 logical cores, running 4 threads");

                    /* sets threads as not done */
                    threadOneDone = false;
                    threadTwoDone = false;
                    threadThreeDone = false;
                    threadFourDone =  false;

                    /* sets value of variable controlling each thread's load */
                    a1end = (Math.round(N/4));
                    b1end = (Math.round(N/2));
                    c1end = (Math.round((N/4)*3));
                    d1end = N;

                    //initializes progress bar
                    progbar(a1end);

                    /* starts running the threads */
                    a1.start();
                    b1.start();
                    c1.start();
                    d1.start();
                } else {
                    if (cores>=4){
                        System.out.println("4-7 logical cores, running 2 threads");

                        /* sets threads as not done */
                        threadOneDone = false;
                        threadTwoDone = false;

                        /* sets value of variable controlling each thread's load */
                        a1end = (Math.round(N/2));
                        b1end = N;

                        //initializes progress bar
                        progbar(a1end);

                        /* starts running the threads */
                        a1.start();
                        b1.start();
                    } else {

                        //initializes progress bar and runs factorial function
                        progbar(N);
                        System.out.println("less than 4 logical cores, running 1 thread");
                        output = output.add(factorial(N));
                        multiplythreads = false;
                    }
                }
            }
        }
        /* Multiplies threads */
        if (multiplythreads){
            while(!threadOneDone||!threadTwoDone||!threadThreeDone||!threadFourDone||!threadFiveDone||!threadSixDone||!threadSevenDone||!threadEightDone)
            {
                Thread.sleep(500);
            }
            output = h1output.multiply(g1output.multiply(f1output.multiply(e1output.multiply(d1output.multiply(c1output.multiply(b1output.multiply(a1output)))))));
        }


        System.out.println("Writing file");
        writeFile(output);

        f.setVisible(false);
        System.out.println("Displaying output");
        showLongTextMessageInDialog(output,frame, timerstart);
    }

    static BigInteger factorial(int N)
    {
        // Initialize result
        BigInteger fac = new BigInteger("1"); // Or BigInteger.ONE

        // Multiply f with 2, 3, ...N
        for (int counter = 2; counter <= N; counter++) {
            fac = fac.multiply(BigInteger.valueOf(counter));
            b.setValue(counter);
        }
        return fac;
    }
    private static void showLongTextMessageInDialog(BigInteger longMessage, JFrame frame, long timerstart) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        int rows = (int) Math.round(((40.0/800.0)*height)-2);
        int columns = (int) Math.round((90.0/1280.0)*width);

        System.out.println("screen res:"+height+" "+width);
        System.out.println("line amounts:"+(rows+1)+" "+columns);

        System.out.println("Wrapping text for display");
        String string = longMessage.toString();
        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < string.length(); i++) {
            // Append a \n after every 100th character.
            if((i > 0) && (i % (columns*1.5)) == 0) {
                buffer.append("\n");
            }
            // Just adds the next character to the StringBuffer.
            buffer.append(string.charAt(i));
        }

        parsedString = buffer.toString();

        SwingUtilities.invokeLater(() -> {
            JTextArea factorialoutput = new JTextArea(rows, columns);
            factorialoutput.setText(parsedString);
            factorialoutput.setCaretPosition(0);
            factorialoutput.setEditable(false);
            factorialoutput.setLineWrap(false);

            JTextArea charlength = new JTextArea(1, columns);
            charlength.setText("Contains "+longMessage.toString().length()+" characters.");
            charlength.setEditable(false);
            charlength.setLineWrap(true);

            JTextArea time = new JTextArea(1, columns);
            time.setText("Operation took " + (System.currentTimeMillis()-timerstart) + " milliseconds");
            System.out.println("finished in "+(System.currentTimeMillis()-timerstart)+" milliseconds.");
            time.setEditable(false);
            time.setLineWrap(true);

            JScrollPane scrollPane = new JScrollPane(charlength);
            JScrollPane scrollPane2 = new JScrollPane(factorialoutput);
            JScrollPane scrollPane3 = new JScrollPane(time);
            JPanel panel1 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel1.add(scrollPane, BorderLayout.NORTH);
            panel1.add(scrollPane2, BorderLayout.CENTER);
            panel1.add(scrollPane3, BorderLayout.SOUTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            int reply = JOptionPane.showConfirmDialog(frame,panel1, "Factorial:", JOptionPane.DEFAULT_OPTION);
            System.exit(0);
        });
    }
    public static void writeFile(BigInteger writeoutput) throws IOException{
        String filedir = System.getProperty("user.home") + "\\Desktop\\Factorial.txt";
        File file = new File(filedir);
        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");

            writer.println(writeoutput);
            writer.close();
        } catch (Exception e){
            try {
                filedir = System.getProperty("user.home") + "/Desktop/Factorial.txt";
                file = new File(filedir);
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                writer.println(writeoutput);
                writer.close();
            } catch (Exception e2){}
        }
    }

    // create a frame
    public static volatile JFrame f;

    public static volatile JProgressBar b;

    public static void progbar(int maxsize)
    {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        // create a frame
        f = new JFrame();
        f.setBounds((width / 2) - 100, (height / 2) - 25, 200, 50);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a panel
        JPanel p = new JPanel();

        // create a progressbar
        b = new JProgressBar(0, maxsize);

        // set initial value
        b.setValue(0);

        b.setStringPainted(true);

        // add progressbar
        p.add(b);

        // add panel
        f.add(p);

        // set the size of the frame
        f.setVisible(true);
    }

}



class FirstThread extends Thread
{
    public void run()
    {
        // Multiply f with 2, 3, ...N
        for (int counter = 2; counter <= Factorial.a1end; counter++) {
            Factorial.a1output = Factorial.a1output.multiply(BigInteger.valueOf(counter));
            Factorial.b.setValue(counter);
        }

        Factorial.threadOneDone = true;

        System.out.println(" First Thread Finished ");
    }
}
class SecondThread extends Thread
{
    public void run()
    {
        // Set value of BigInteger b1output
        Factorial.b1output = BigInteger.valueOf(Factorial.a1end+1);

        // Multiply f with 2, 3, ...N
        for (int counter = (Factorial.a1end+2); counter <= Factorial.b1end; counter++) {
            Factorial.b1output = Factorial.b1output.multiply(BigInteger.valueOf(counter));
        }

        Factorial.threadTwoDone = true;

        System.out.println(" Second Thread Finished ");
    }
}
class ThirdThread extends Thread
{
    public void run()
    {
        // Set value of BigInteger b1output
        Factorial.c1output = BigInteger.valueOf(Factorial.b1end+1);

        // Multiply f with 2, 3, ...N
        for (int counter = (Factorial.b1end+2); counter <= Factorial.c1end; counter++) {
            Factorial.c1output = Factorial.c1output.multiply(BigInteger.valueOf(counter));
        }

        Factorial.threadThreeDone = true;

        System.out.println(" Third Thread Finished ");
    }
}
class FourthThread extends Thread
{
    public void run()
    {
        // Set value of BigInteger b1output
        Factorial.d1output = BigInteger.valueOf(Factorial.c1end+1);

        // Multiply f with 2, 3, ...N
        for (int counter = (Factorial.c1end+2); counter <= Factorial.d1end; counter++) {
            Factorial.d1output = Factorial.d1output.multiply(BigInteger.valueOf(counter));
        }

        Factorial.threadFourDone = true;

        System.out.println(" Fourth Thread Finished ");
    }
}
class FifthThread extends Thread
{
    public void run()
    {
        // Set value of BigInteger b1output
        Factorial.e1output = BigInteger.valueOf(Factorial.d1end+1);

        // Multiply f with 2, 3, ...N
        for (int counter = (Factorial.d1end+2); counter <= Factorial.e1end; counter++) {
            Factorial.e1output = Factorial.e1output.multiply(BigInteger.valueOf(counter));
        }

        Factorial.threadFiveDone = true;

        System.out.println(" Fifth Thread Finished ");
    }
}
class SixthThread extends Thread
{
    public void run()
    {
        // Set value of BigInteger b1output
        Factorial.f1output = BigInteger.valueOf(Factorial.e1end+1);

        // Multiply f with 2, 3, ...N
        for (int counter = (Factorial.e1end+2); counter <= Factorial.f1end; counter++) {
            Factorial.f1output = Factorial.f1output.multiply(BigInteger.valueOf(counter));
        }

        Factorial.threadSixDone = true;

        System.out.println(" Sixth Thread Finished ");
    }
}
class SeventhThread extends Thread
{
    public void run()
    {
        // Set value of BigInteger b1output
        Factorial.g1output = BigInteger.valueOf(Factorial.f1end+1);

        // Multiply f with 2, 3, ...N
        for (int counter = (Factorial.f1end+2); counter <= Factorial.g1end; counter++) {
            Factorial.g1output = Factorial.g1output.multiply(BigInteger.valueOf(counter));
        }

        Factorial.threadSevenDone = true;

        System.out.println(" Seventh Thread Finished ");
    }
}
class EighthThread extends Thread
{
    public void run()
    {
        // Set value of BigInteger b1output
        Factorial.h1output = BigInteger.valueOf(Factorial.g1end+1);

        // Multiply f with 2, 3, ...N
        for (int counter = (Factorial.g1end+2); counter <= Factorial.h1end; counter++) {
            Factorial.h1output = Factorial.h1output.multiply(BigInteger.valueOf(counter));
        }

        Factorial.threadEightDone = true;

        System.out.println(" Eighth Thread Finished ");
    }
}