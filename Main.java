import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
      OptimalTreeLabelingTest optimalTreeLabelingTest = new OptimalTreeLabelingTest(new OptimalTreeLabeling(), System.out);
      for(String arg : args) {
        try {
          optimalTreeLabelingTest.test(arg);
        } catch (Exception e) {
          System.err.println("This file does not exist.");
        }

      }
    }
}
