import java.util.*;

public class Driver {

	public static void main(String[] args) throws Exception {

		/** create an undirected graph */
		Graph<String> g = new Graph<>(false);
		/** Add some edges */
		g.addEdge("GC", "CC");
	    g.addEdge("GC", "UC");
		g.addEdge("CC", "UC");
		g.addEdge("CC", "DC");
		g.addEdge("UC", "DC");
		g.addEdge("UC", "SC");
		g.addEdge("UC", "OC");
		g.addEdge("DC", "OC");
		g.addEdge("DC", "PC");
		g.addEdge("SC", "OC");
		g.addEdge("OC", "PC");

		//Print a string representation of the graph
		System.out.println("**********The adjacency list representation of the graph:\n"
	                           + g.toString());

		System.out.println("**********Depth-first-search:");
		Set<String> hs= g.dfs("UC");
		System.out.println();
		for (String i: hs){	    
		    System.out.print(i+" ");
		}
		System.out.println();
		
		System.out.println("**********Breadth-first-search:");
		 hs= g.bfs("UC");
		System.out.println();
		for (String i: hs){	    
		    System.out.print(i+" ");
		}
		System.out.println();

	}

}
