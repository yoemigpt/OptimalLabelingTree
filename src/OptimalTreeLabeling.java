import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A class for implementing the optimal tree labeling algorithm
 *
 * @author Mohamed Babana, Ecole Polytechnique
 * @version december 2018
 */

public class OptimalTreeLabeling {

	/**
	 * @param neighbours the neighbours of every vertex.
	 * @param labels map every character to a set of leaf vertices which their labels contain the character.
	 * @return the minimum labeling weight
	 */
	static int minimumLabelingWeight(ArrayList<List<Integer>> neighbours, Map<Character, Set<Integer>> labels) {
		// Initialize the weight to 0.
		int w = 0;

		// For every character compute the minimum binary labeling weight and add it to w.
		for(Set<Integer> oneVertices : labels.values()) {
			w += minimumBinaryLabelingWeight(neighbours, oneVertices);
		}
		return w;
	}

	/**
	 * @param neighbours the neighbours of every vertex.
	 * @param oneVertices the set of leaf vertices with label equals to 1.
	 * @return the minimum binary labeling weight
	 */

	static int minimumBinaryLabelingWeight(ArrayList<List<Integer>> neighbours, Set<Integer> oneVertices) {

		// Get the number of vertices.
		int N = neighbours.size();

		int r = 0;
		// Look for a non leaf vertex and root the tree on it.
		while (r < N && neighbours.get(r).size() < 2) r++;

		// if all vertices are leaves
		if(r == N) {
			return 0;
		}

		// Compute w0(r) and w1(r).
		int[] w = binaryLabelingWeights(r, r, neighbours, oneVertices);

		// return the minimum of w0(r) and w1(r).
		if(w[0] < w[1]) {
			return w[0];
		}
		else {
			return w[1];
		}

	}

	/**
	 * @param v a vertex
	 * @param neighbours the neighbours of every vertex.
	 * @param oneVertices the set of leaf vertices with label equals to 1.
	 * @param p the vertex parent of v.
	 * @param labels an array to store the labels.
	 * @return w0(v) and w1(v).
	 */
	static int[] binaryLabelingWeights(int v, int p, ArrayList<List<Integer>> neighbours, Set<Integer> oneVertices) {

		// Check if the vertex v is a leaf vertex
		if(neighbours.get(v).size() == 1) {
			// If the leaf vertex v has 1 as label then
			// w0(v) = 1 and w1(v) = 0.
			if(oneVertices.contains(v)) {
				return new int[]{1, 0};
			}
			// If the leaf vertex v has 0 as label then
			// w0(v) = 0 and w1(v) = 1.
			else {
				return new int[]{0, 1};
			}
		}
		else {
			// Initialize w0(v) to 0 and w1(v) to 0.
			int[] w = new int[]{0, 0};
			// For all neighbours of v.
			for(int s : neighbours.get(v)) {
				// Only consider vertices different from parent p of v.
				if(s != p) {
					// Get w0(s) and w1(s).
					int[] sw = binaryLabelingWeights(s, v, neighbours, oneVertices);

					// Add the minimum of wi(s) and 1 + w1-i(s) to wi(v).
					for(int i = 0; i < 2; i++) {
						if(sw[i] < 1 + sw[1-i]) {
							w[i] += sw[i];
						}
						else {
							w[i] += 1 + sw[1-i];
						}
					}
				}
			}
			return w;
		}
	}

	/**
	 * main function
	 */

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
    int weight = minimumLabelingWeight(neighbours, labels);
    System.out.println(weight);

  }
}
