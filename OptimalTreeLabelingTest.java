import java.util.*;
import java.io.*;

public class OptimalTreeLabelingTest {

    private OptimalTreeLabelingInterface box;

    public final static long TIMEOUT = 30000;

    private PrintStream out;

    OptimalTreeLabelingTest(OptimalTreeLabelingInterface box, PrintStream out) {
        this.box = box;
        this.out = out;
    }
    void printTree(int r, ArrayList<LinkedList<Integer>> neighbours, boolean[] vertices, ArrayList<HashSet<Integer>> labels, int d) {
        vertices[r] = true;
        out.print((r+1));
        if(neighbours.get(r).size() == 1) {
            printLabels(r, labels);
        }
        out.println();
        for(int v : neighbours.get(r)) {
            if(!vertices[v]) {
                for(int i = 0; i < d+1; i++)
                  out.print("\t");
                printTree(v, neighbours, vertices, labels, d+1);
            }
        }
    }
    void printTree(ArrayList<LinkedList<Integer>> neighbours, ArrayList<HashSet<Integer>> labels) {
        int N = neighbours.size();
        boolean[] vertices = new boolean[N];
        int r = 0;
        while(neighbours.get(r).size() == 1) r++;
        printTree(r, neighbours, vertices, labels, 0);
    }
    void printLabels(int u, ArrayList<HashSet<Integer>> labels) {
        out.print("{");
        for(HashSet<Integer> alabels : labels) {
            if(alabels.contains(u)) out.print(1);
            else out.print(0);
        }
        out.print("}");
    }
    public void test(InputStream in) {
        Scanner sc = new Scanner(in);

        int N = sc.nextInt();
        int L = sc.nextInt();

        ArrayList<LinkedList<Integer>> neighbours = new ArrayList<LinkedList<Integer>>();
        for(int i = 0; i < N; i++) {
            neighbours.add(new LinkedList<Integer>());
        }

        for(int i = 0; i < N-1; i++) {
            int u = sc.nextInt()-1;
            int v = sc.nextInt()-1;

            neighbours.get(u).add(v);
            neighbours.get(v).add(u);
        }

        ArrayList<Integer> labelValues = new ArrayList<Integer>();
        ArrayList<HashSet<Integer>> labels = new ArrayList<HashSet<Integer>>();

        for(int i = 0; i < L; i++) {
            int u = sc.nextInt()-1;
            assert(neighbours.get(u).size() == 1);
            String label = sc.next();
            if(!label.equals("$")) {
                for(char c : label.toCharArray()) {
                    int ascii = ((int) c) - 65;
                    int index = labelValues.indexOf(ascii);
                    if(index == -1) {
                        HashSet<Integer> set = new HashSet<Integer>();
                        set.add(u);
                        labels.add(set);
                        labelValues.add(ascii);
                    }
                    else {
                        labels.get(index).add(u);
                    }
                }
            }
        }
        out.println(this.box.minimumLabelWeight(neighbours, labels));
    }
}
