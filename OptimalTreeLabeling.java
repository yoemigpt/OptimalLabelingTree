import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collection;

/**
 * A class for implementing the optimal tree labeling algorithm
 *
 * @author Mohamed Babana, Ecole Polytechnique
 * @version december 2018
 */

public class OptimalTreeLabeling {

	/**
	 * @param neighbours the neighbours of every vertex.
	 * @param labels collection of a set vertices, the set of vertices contains the leaf vertices which have the character in their labels.
	 * @return the minimum labeling weight
	 */
	static int minimumLabelingWeight(ArrayList<List<Integer>> neighbours, Collection<Set<Integer>> labels) {
		// Initialize the weight to 0.
		int w = 0;

		// For every character compute the minimum binary labeling weight and add it to w.
		for(Set<Integer> leafVerticesWithOneLabel : labels) {
				w += minimumBinaryLabelingWeight(neighbours, leafVerticesWithOneLabel);
		}
		return w;
	}

	/**
	 * @param neighbours the neighbours of every vertex.
	 * @param leafVerticesWithOneLabel the set of leaf vertices with label equals to 1.
	 * @return the minimum binary labeling weight.
	 */

	static int minimumBinaryLabelingWeight(ArrayList<List<Integer>> neighbours, Set<Integer> leafVerticesWithOneLabel) {

		// Get the number of vertices.
		int N = neighbours.size();

		int r = 0;
		// Look for a non leaf vertex and root the tree on it.
		while (r < N && neighbours.get(r).size() < 2) r++;

		// if all vertices are leaves
		if(r == N) {
			return 0;
		}

		// Create a boolean array to store the visited vertices.
		boolean[] vertices = new boolean[N];
		for(int v = 0; v < N; v++) {
			vertices[v] = false;
		}

		// Compute w0(r) and w1(r).
		int[] w = binaryLabelingWeights(r, neighbours, leafVerticesWithOneLabel, vertices);

		// return the minimum of w0(r) and w1(r).
		return (w[0] < w[1]) ? w[0] : w[1];

	}

	/**
	 * @param v a vertex
	 * @param neighbours the neighbours of every vertex.
	 * @param leafVerticesWithOneLabel the set of leaf vertices with label equals to 1.
	 * @param vertices an array mark the visited vertices.
	 * @return w0(v) and w1(v).
	 */
	static int[] binaryLabelingWeights(int v, ArrayList<List<Integer>> neighbours, Set<Integer> leafVerticesWithOneLabel, boolean[] vertices) {
		// Mark the vertex v as visited.
		vertices[v] = true;

		// Check if the vertex v is a leaf vertex
		if(neighbours.get(v).size() == 1) {
			// If the leaf vertex v has 1 as label then
			// w0(v) = 1 and w1(v) = 0.
			if(leafVerticesWithOneLabel.contains(v)) {
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
				// Look for non visted vertices thoses are the children of the vertex v.
				if(!vertices[s]) {
					// Get w0(s) and w1(s).
					int[] sw = binaryLabelingWeights(s, neighbours, leafVerticesWithOneLabel, vertices);

					// Add the minimum of wi(s) and 1 + w1-i(s) to wi(v).
					for(int i = 0; i < 2; i++) {
							w[i] += ((sw[i] < 1 + sw[1-i]) ? sw[i] : (1 + sw[1-i]));
					}
				}
			}
			return w;
		}
	}
}
