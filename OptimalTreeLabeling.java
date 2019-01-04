import java.util.*;

public class OptimalTreeLabeling {

		public int minimumLabelingWeight(ArrayList<LinkedList<Integer>> neighbours, ArrayList<Set<Integer>> labels) {
				int w = 0;

				for(HashSet<Integer> alabels : labels) {
						w += minimumLabelingWeight(neighbours, alabels);
				}
				return w;
		}

		public int minimumLabelingWeight(ArrayList<LinkedList<Integer>> neighbours, Set<Integer> labels) {

				int N = neighbours.size();
				int r = 0;

				while (neighbours.get(r).size() < 2) r++;

				int[][] weights = new int[2][N];
				boolean[] vertices = new boolean[N];
				for(int v = 0; v < N; v++) {
					vertices[v] = false;
				}

				minimumLabelingWeight(r, neighbours, labels, weights, vertices);
				return (weights[1][r] > weights[0][r]) ? weights[0][r] : weights[1][r];
		}

		void minimumLabelingWeight(int v, ArrayList<LinkedList<Integer>> neighbours, Set<Integer> labels, int[][] weights, boolean[] vertices) {
				vertices[v] = true;
				if(neighbours.get(v).size() == 1) {
						if(labels.contains(v)) {
								weights[0][v] = 1;
						}
						else {
								weights[0][v] = 0;
						}
						weights[1][v] = 1 - weights[0][v];
				}
				else {
						weights[1][v] = 0;
						weights[0][v] = 0;
						for(int s : neighbours.get(v)) {
								if(!vertices[s]) {
										minimumLabelingWeight(s, neighbours, labels, weights, vertices);
										for(int i = 0; i < 2; i++) {
												weights[i][v] += ((weights[i][s] < 1 + weights[1-i][s]) ? weights[i][s] : (1 + weights[1-i][s]));
										}
								}
						}
				}
		}
}
