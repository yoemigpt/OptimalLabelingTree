import java.util.*;

public class OptimalTreeLabeling implements OptimalTreeLabelingInterface {

		@Override
		public int minimumLabelWeight(ArrayList<LinkedList<Integer>> neighbours, ArrayList<HashSet<Integer>> labels) {
				int w = 0;

				for(HashSet<Integer> alabels : labels) {
						w += minimumLabelWeight(neighbours, alabels);
				}
				return w;
		}

		@Override
		public int minimumLabelWeight(ArrayList<LinkedList<Integer>> neighbours, HashSet<Integer> labels) {

				int N = neighbours.size();
				int r = 0;

				while (neighbours.get(r).size() < 2) r++;

				int[][] weights = new int[2][N];
				boolean[] vertices = new boolean[N];
				for(int v = 0; v < N; v++) {
						if(neighbours.get(v).size() == 1) {
								if(labels.contains(v)) {
										weights[0][v] = 1;
								}
								else {
										weights[0][v] = 0;
								}
								weights[1][v] = 1 - weights[0][v];
						}
				}

				minimumLabelWeight(r, neighbours, weights, vertices);
				return (weights[1][r] > weights[0][r]) ? weights[0][r] : weights[1][r];
		}

		void minimumLabelWeight(int v, ArrayList<LinkedList<Integer>> neighbours, int[][] weights, boolean[] vertices) {
				vertices[v] = true;
				if(neighbours.get(v).size() > 1) {
						weights[1][v] = 0;
						weights[0][v] = 0;
						for(int s : neighbours.get(v)) {
								if(!vertices[s]) {
										minimumLabelWeight(s, neighbours, weights, vertices);
										for(int i = 0; i < 2; i++) {
												weights[i][v] += ((weights[i][s] < 1 + weights[1-i][s]) ? weights[i][s] : (1 + weights[1-i][s]));
										}
								}
						}
				}
		}
}
