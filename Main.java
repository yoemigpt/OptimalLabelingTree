import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Mohamed Babana, Ecole Polytechnique
 * @version december 2018
 */

public class Main {

  public static void main(String[] args) {
    // Create a Scanner to read the input data from System.in
    Scanner sc = new Scanner(System.in);

    // Get the number of vertices
    int N = sc.nextInt();

    // Get the number of leaf vertices
    int L = sc.nextInt();

    // For every vertex we store its neighbours in a List<Integer>
    ArrayList<List<Integer>> neighbours = new ArrayList<List<Integer>>();
    for(int v = 0; v < N; v++) {
      neighbours.add(new LinkedList<Integer>());
    }

    // For every edge e = {u, v} we add v to the neighbours of u and
    // we add u to the neighbours of v
    for(int e = 0; e < N-1; e++) {
      // Get the first vertex u of the edge
      int u = sc.nextInt()-1;

      // Get the second vertex v of the edge
      int v = sc.nextInt()-1;

      // Add v to the neighbours of u
      neighbours.get(u).add(v);

      // Add u to the neighbours of v
      neighbours.get(v).add(u);
    }

    // For every character c we store the set of leaf vertices with labels contain
    // the character c in a Set<Integer>.
    Map<Character, Set<Integer>> labels = new HashMap<Character, Set<Integer>>();

    for(int l = 0; l < L; l++) {
      // Get the leaf vertex.
      int u = sc.nextInt()-1;

      // Assert that the vertex is a leaf by checking the number of their neighbours.
      assert(neighbours.get(u).size() == 1);

      // Get the label string.
      String label = sc.next();

      // Check if the label is different from "$" which represents the empty set.
      if(!label.equals("$")) {
        // For every character c in the label add the vertex u to the set of vertices
        // corresponding to the character c.
        for(char c : label.toCharArray()) {
          // Check if the character c already exists
          if(labels.get(c) == null) {
            // Create a new set for the character c and add it to labels.
            labels.put(c, new HashSet<Integer>());
          }
          // Add the vertex u to the set of vertices corresponding to the character c.
          labels.get(c).add(u);
        }
      }
    }
    // Close the Scanner.
    sc.close();

    // Print the minimum weight.
    System.out.println(OptimalTreeLabeling.minimumLabelingWeight(neighbours, labels.values()));
  }
}
