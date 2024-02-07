
import java.util.*;
/**
   Graph.java
*/

/** 
    Generic Graph implementation
    Vertex nodes are of type E
    By default we make the graph directed
    Pass a false boolean value to the constructor to specify un-directed graphs
*/

public class Graph<E>{
    private Map<E, List<E>> listMap; 
    public boolean directed ; 

    public Graph() {
	directed = true;
	listMap = new HashMap<>();
    }
    public Graph(boolean directed) {
	this.directed = directed;
	listMap = new HashMap<>();
    }

    /*********** Add a vertex to the graph */
    public boolean addVertex(E v)  throws Exception{
	if(listMap.containsKey(v)){
	    throw new Exception("Vertex exists");
	}
	else {
	    listMap.put(v, new LinkedList<E>());
	    return true;
	}
    }

    /*********** Add an Edge between two vertices */

    public void addEdge(E source,E destination) throws Exception{
	if ( (this.directed ==false) && hasEdge(destination, source)){
	    return;
	}
	if (!listMap.containsKey(source))
	    addVertex(source);
	
	if (!listMap.containsKey(destination))
            addVertex(destination);

	listMap.get(source).add(destination);

	if (this.directed == false) {
	    /* in this case add an edge from destination to source node */
            listMap.get(destination).add(source);
	}
    }

    /*********** Remove an edge from source to destination */

    public void removeEdge(E source, E dest) throws Exception {     
	if(!listMap.containsKey(source)){
	    throw new Exception("source node not found");
	}
	else {
	    listMap.get(source).remove(dest);	
	    if(this.directed == false){	    
		listMap.get(dest).remove(source);
	    }
	}
    }

    /*********************** Remove  a given vertex ******************/
    
    public void removeVertex(E vertex){
	/**
	   (a) If the vertex is a key in the map, we need to remove the key
	   (b) We need to remove the vertex from all adjacency lists
	*/
	
	if(listMap.containsKey(vertex)){
	    listMap.remove(vertex);
	    for(List l:listMap.values()){
		if (l.contains(vertex)){
		    l.remove(vertex);
		}
	    }
	}
	else {
	    System.out.println("No vertex to remove");
	    return;
	}
    }

    /******************** get an adjacency list of the given vertex */

    public List<E> getAdjacencyList(E v) {
		List<E> adjList = listMap.get(v);	
		return adjList;
    }
      
    /*********** Method to return  number of vertices in the graph */

    public int nodeCount(){
    	return listMap.keySet().size();
    }

    /***********  Method to return  number of  edges in the graph */	

    public int edgeCount(){
		int count = 0;
		for (E v : listMap.keySet()) {
		    count += listMap.get(v).size();
		}
		 if (directed == false) {
		     count = count / 2;
		 }
		return count;
    }


    /*********** checks if graph contains given vertex */
    public boolean hasVertex(E s){
    	return listMap.containsKey(s);
    }
	
		
    /*********** checks if graph contains an edge between two vertices */	
    public boolean hasEdge(E source, E dest){

		if(!hasVertex(source) || !hasVertex(dest)){
		    return false;
		}
		return listMap.get(source).contains(dest);
    }

    /*********** Print adjacency list of a vertex */
    
    public String printAdjacencyList(E v){
		List<E> adjList = getAdjacencyList(v);
		StringBuilder builder = new StringBuilder();	
		for(E e: adjList){
		    builder.append(e.toString() + " ");
		}
		builder.append("\n");	
		return builder.toString();
    }
	
    
    
    /*********** String representation of adjacency lists of each vertex */
	
    public String toString(){

	/** We could append using String object, but we use a StringBuilder instead.
	    See https://stackoverflow.com/questions/1532461/stringbuilder-vs-string-concatenation-in-tostring-in-java
	    for why that is better when you are appending to strings in a loop.
	*/
	    
	StringBuilder builder = new StringBuilder();	    
	for ( E v : listMap.keySet()) {
	    builder.append(v.toString() + ": ");
	    for (E w : listMap.get(v)) {
		builder.append(w.toString() + " ");
	    }
	    builder.append("\n");
	}    
	return (builder.toString());
    }


   

    /*********** Depth-First-search with Recursion**********/
    public Set<E> dfs(E startVertex){
    	Set<E> visited = new LinkedHashSet<E>();
    	return depthFirstRecursion(startVertex, visited);
    }
    
    //The recursive function of breath first search 
    public Set<E> depthFirstRecursion(E startVertex, Set<E> visited){
		visited.add(startVertex); // add the start vertex to the visited set

    	for (E vertex: getAdjacencyList(startVertex)){ // for each vertex in the adjacency list of the start vertex
			if (!visited.contains(vertex)){ // if the vertex is not in the visited set
				depthFirstRecursion(vertex, visited); // call the function again with the vertex and the visited set
			}
		}
    	return visited;
    }

  

    /*********** Breadth-First-Search with Recursion**********/
    public Set<E> bfs(E startVertex){
    	Set<E> visited = new LinkedHashSet<E>();
    	Queue<E> queue = new LinkedList<E>();
    	queue.add(startVertex);
    	return breathFirstRecursion(queue,visited);
    }
    
    //The recursive function of breath first search

	private Set<E> breathFirstRecursion(Queue<E> queue, Set<E> visited) {
		// Base case: if the queue is empty, return the visited set
		if (queue.isEmpty()) {
			return visited;
		}

		// Process the current level of nodes
		int levelSize = queue.size();
		for (int i = 0; i < levelSize; i++) {
			E vertex = queue.poll(); // remove the first element from the queue
			visited.add(vertex);     // add the vertex to the visited set

			for (E node : getAdjacencyList(vertex)) {
				// for each node in the adjacency list of the vertex
				if (!visited.contains(node)) {
					visited.add(node); // add the node to the visited set
					queue.add(node);   // add the node to the queue
				}
			}
		}

		// Recursive call for the next level
		return breathFirstRecursion(queue, visited);
	}




//  The following is not part of the assignment but was found on the web
//  and I wanted to leave it in for future reference for when I look back on the
//  assignment. It has not been tested and I cannot attest for its validity.


//	/*********** Breadth-First-Search with Iteration**********/
//	public Set<E> bfsIterative(E startVertex){
//		Set<E> visited = new LinkedHashSet<E>();
//		Queue<E> queue = new LinkedList<E>();
//		queue.add(startVertex);
//		visited.add(startVertex);
//		while (!queue.isEmpty()){
//			E vertex = queue.poll();
//			for (E v: getAdjacencyList(vertex)){
//				if (!visited.contains(v)){
//					visited.add(v);
//					queue.add(v);
//				}
//			}
//		}
//		return visited;
//	}
//
//	/*********** Depth-First-Search with Iteration**********/
//	public Set<E> dfsIterative(E startVertex){
//		Set<E> visited = new LinkedHashSet<E>();
//		Stack<E> stack = new Stack<E>();
//		stack.push(startVertex);
//		while (!stack.isEmpty()){
//			E vertex = stack.pop();
//			if (!visited.contains(vertex)){
//				visited.add(vertex);
//				for (E v: getAdjacencyList(vertex)){
//					stack.push(v);
//				}
//			}
//		}
//		return visited;
//	}
//
//	/*********** Method to return the shortest path between two vertices */
//	public List<E> shortestPath(E startVertex, E endVertex){
//		Map<E, E> parentMap = new HashMap<E, E>();
//		Queue<E> queue = new LinkedList<E>();
//		queue.add(startVertex);
//		parentMap.put(startVertex, null);
//		while (!queue.isEmpty()){
//			E vertex = queue.poll();
//			if (vertex.equals(endVertex)){
//				break;
//			}
//			for (E v: getAdjacencyList(vertex)){
//				if (!parentMap.containsKey(v)){
//					parentMap.put(v, vertex);
//					queue.add(v);
//				}
//			}
//		}
//		List<E> path = new ArrayList<E>();
//		E vertex = endVertex;
//		while (vertex != null){
//			path.add(vertex);
//			vertex = parentMap.get(vertex);
//		}
//		Collections.reverse(path);
//		return path;
//	}
//
//	/*********** Method to return the shortest path between two vertices */
//	public List<E> shortestPathIterative(E startVertex, E endVertex){
//		Map<E, E> parentMap = new HashMap<E, E>();
//		Queue<E> queue = new LinkedList<E>();
//		queue.add(startVertex);
//		parentMap.put(startVertex, null);
    
  
    
}


