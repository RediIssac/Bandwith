
//Java code to demonstrate Graph representation 
//using ArrayList in Java 

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.lang.reflect.Array; 

class bandwidth { 
	public static ArrayList<Integer> Vertices;
	public static int Max = 0;
	public static ArrayList<Integer> result;
//	public static ArrayList<Integer> a = new ArrayList<Integer>();
	static void addEdge(ArrayList<ArrayList<Integer> > adj, 
						int u, int v) 
	{ 
		adj.get(u).add(v); 
		adj.get(v).add(u); 
	} 
	
	public static void main(String[] args) throws IOException 
	{ 
		File file = new File("C:\\Users\\asus\\Desktop\\Semesters\\Fall 2020\\CSE 373\\HW-4\\g-p-17-16.txt"); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String numVertices = br.readLine();
		String numEdges = br.readLine();
		ArrayList<Integer> a = new ArrayList<Integer>();
        int V = Integer.parseInt(numVertices); 
//		int V = 3;
		Vertices = new ArrayList<Integer>(V);
		for (int i = 0; i < V ; i++) {
			Vertices.add(i, i+1);
		}
		ArrayList<ArrayList<Integer> > adj 
					= new ArrayList<ArrayList<Integer> >(V); 
		
		
		for (int i = 0; i < V+1; i++) 
			adj.add(new ArrayList<Integer>()); 
			

		String st; 
//		addEdge(adj, 1, 2);
//		addEdge(adj, 1, 3);
//		addEdge(adj, 2, 3);
//		addEdge(adj, 4, 2);
//		addEdge(adj, 1, 3);
//		addEdge(adj, 2, 3);
		
		while ((st = br.readLine()) != null) {
			String[] ver = st.split(" ", 2);
			int ver1 = Integer.parseInt(ver[0]);
			int ver2 = Integer.parseInt(ver[1].trim());
		    addEdge(adj,ver1, ver2);
		} 
		System.out.println("number of Vertices " + numVertices);
		System.out.println("number of Edges " + numEdges);
		result = Vertices;
		ArrayList<Integer> maxCollection = new ArrayList<Integer>();
		Max = Integer.MAX_VALUE;
		
		backtrack(new ArrayList<Integer>(), 0, Vertices, adj, maxCollection );
		System.out.println("Bandwidth: " + Max);
		System.out.println("best Permiutation: \n"+ result);
	} 
	public static ArrayList<Integer> Candidates(ArrayList<Integer> a , ArrayList<Integer> vertices, ArrayList<ArrayList<Integer>> adj) {
		ArrayList<Integer> candidate = new ArrayList<Integer>();
		boolean[] in_perm = new boolean[Vertices.size()];

		for(int i = 0; i < a.size(); i++) {
			in_perm[a.get(i)-1] = true;
		}
		for (int i = 0 ; i < in_perm.length; i++) {
			if(!in_perm[i]) {
				candidate.add(Vertices.get(i));
			}
		}
//		Collections.sort(candidate);
//		ArrayList<Integer> cand = new ArrayList<Integer>();
//		for(int i =candidate.size()-1; i >= 0; i--) {
//			cand.add(candidate.get(i));
//		}
		return candidate;
	}
	
	public static boolean isSolution(ArrayList<Integer> a, ArrayList<Integer> vertices, ArrayList<ArrayList<Integer>> adj) {
		return (a.size()== vertices.size() );
	}
	
	public static void processSolution( ArrayList<Integer> a, ArrayList<Integer> maxCollection) {
		int maxEdge = getBandwidth(maxCollection);
		if(Max > maxEdge) {
			Max = maxEdge;
			result = new ArrayList<Integer>(a);
//			result.addAll(a);
//			System.out.println("processing solution");
//			System.out.println(maxEdge);
//			System.out.println(a);
		}
	}
	
	public static int getBandwidth(ArrayList<Integer> maxCollection) {
		int bandwidth = 0;
//		System.out.println("max collection"+maxCollection);
		for(int i = 0; i < maxCollection.size(); i++) {
			bandwidth = Math.max(bandwidth, maxCollection.get(i));
		}
		return bandwidth;
	}
	
	public static void backtrack(ArrayList<Integer> a, int k, ArrayList<Integer> vertices,  ArrayList<ArrayList<Integer>> adj,  ArrayList<Integer> m) {
		
		if(isSolution(a, vertices, adj)) {
			processSolution(a,m);
		}else {
			k = k + 1;
			ArrayList<Integer> c = Candidates(a, vertices, adj);
			while(c.size() != 0) {
				
				ArrayList<Integer> maxCollection = new ArrayList<>(m);
				ArrayList<Integer> ap = new ArrayList<>(a);
				ap.add(c.get(0));
				int maxSofar =  getBandwidthSoFar(ap, adj, c.get(0));
				maxCollection.add(maxSofar);
//				System.out.println("cand " +c.get(0));
				c.remove(0);
//				System.out.println("max so far  "+maxSofar);
//				System.out.println("max "+Max);
//				System.out.println("a " +ap);
//				System.out.println("max collection " +maxCollection);
//				System.out.println();
//				System.out.println();
				if(maxSofar >= Max) {

					continue;
					
				}
				backtrack(ap, k, vertices, adj, maxCollection);
				ap.remove(ap.size()-1);
			}

		}
	}
	public static int getBandwidthSoFar(ArrayList<Integer> a, ArrayList<ArrayList<Integer>> adj, int v) {
		int bandwidth = 0;
		ArrayList<Integer> Edges = adj.get(v);
		for(int i =0; i < a.size(); i ++) {
			
			for(int j = 0; j < Edges.size(); j++) {
				int x= Edges.get(j) ;
				int y = a.get(i);
				if(x == y) {
					bandwidth = Math.max(bandwidth, Math.abs(a.size()-1-(i)));
				}
			}
		}
		
//		for 
		
//			int x = v;
//			ArrayList<Integer> Edges = adj.get(x);
//			int distance = 0;
//			
//			for(int j = 0; j < a.size(); j++) {
//				int e = a.get(j);
//				
//				for (int k =0; k < Edges.size(); k++) {
//					if( e == Edges.get(k)) {
//						distance = Math.max(distance, a.size() - 1 - k);
//						break;
//					}
//				}
				
//				if(distance > bandwidth) {
//					bandwidth = distance;
//				}
				
//			}
		return bandwidth;
	}
	
	
	static void printGraph(ArrayList<ArrayList<Integer> > adj){ 
		for (int i = 1; i < adj.size(); i++) { 
			System.out.println("\nAdjacency list of vertex " + i); 
			System.out.print("head"); 
			for (int j = 0; j < adj.get(i).size(); j++) { 
				System.out.print(" -> "+adj.get(i).get(j)); 
			} 
			System.out.println(); 
		} 
	} 
	public static int distance(int x, int y, ArrayList<ArrayList<Integer>> adj){
		int distance = 0;
		ArrayList<Integer> Edges = adj.get(x);
		
		for(int i = 0; i < Edges.size(); i++) {
			
			for (int j = 0; j < Edges.size(); j++) {
				if(y == Edges.get(j)) {
					distance = i+1;
					break;
				}
			}
			if(distance != 0) {
				break;
			}
			ArrayList<Integer> cEdges = new ArrayList<Integer>();
			for(int k = 0; k < Edges.size(); k++) {
				cEdges.addAll(adj.get(Edges.get(k)));
			}
			List<Integer> list = cEdges;
			list = cEdges.stream() 
                    .distinct() 
                    .collect(Collectors.toList()); 
			cEdges = new ArrayList<Integer>(list);
			Edges = cEdges;
		}
		return distance;
	}
	
	
	
	

} 



//
////Java code to demonstrate Graph representation 
////using ArrayList in Java 
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.io.*;
//import java.lang.reflect.Array; 
//
//class bandwidth { 
//	public static ArrayList<Integer> Vertices;
//	public static int Max = 0;
//	public static ArrayList<Integer> result;
//	public static ArrayList<Integer> maxCollection = new ArrayList<Integer>();
//	static void addEdge(ArrayList<ArrayList<Integer> > adj, 
//						int u, int v) 
//	{ 
//		adj.get(u).add(v); 
//		adj.get(v).add(u); 
//	} 
//	
//	public static void main(String[] args) throws IOException 
//	{ 
//		File file = new File("C:\\Users\\asus\\Desktop\\Semesters\\Fall 2020\\CSE 373\\HW-4\\g-bt-10-9.txt"); 
//		  
//		BufferedReader br = new BufferedReader(new FileReader(file)); 
//		String numVertices = br.readLine();
//		String numEdges = br.readLine();
//		ArrayList<Integer> a = new ArrayList<Integer>();
//        int V = Integer.parseInt(numVertices); 
////		int V = 4;
//		Vertices = new ArrayList<Integer>(V);
//		for (int i = 0; i < V ; i++) {
//			Vertices.add(i, i+1);
//		}
//		ArrayList<ArrayList<Integer> > adj 
//					= new ArrayList<ArrayList<Integer> >(V); 
//		
//		
//		for (int i = 0; i < V+1; i++) 
//			adj.add(new ArrayList<Integer>()); 
//			
//
//		String st; 
////		addEdge(adj, 1, 2);
////		addEdge(adj, 1, 3);
////		addEdge(adj, 2, 3);
////		addEdge(adj, 2, 4);
//		
////		
//		while ((st = br.readLine()) != null) {
//			String[] ver = st.split(" ", 2);
//			int ver1 = Integer.parseInt(ver[0]);
//			int ver2 = Integer.parseInt(ver[1].trim());
//		    addEdge(adj,ver1, ver2);
//		} 
//		System.out.println("number of Vertices " + numVertices);
//		System.out.println("number of Edges " + numEdges);
//		result = Vertices;
//		ArrayList<Integer> maxCollection = new ArrayList<Integer>();
//		Max = Integer.MAX_VALUE;
//		
//		backtrack(new ArrayList<Integer>(), Vertices, adj);
//		System.out.println("Bandwidth: " + Max);
//		System.out.println("best Permiutation: \n"+ result);
//	} 
//	public static ArrayList<Integer> Candidates(ArrayList<Integer> a , ArrayList<Integer> vertices, ArrayList<ArrayList<Integer>> adj) {
//		ArrayList<Integer> candidate = new ArrayList<Integer>();
//		boolean[] in_perm = new boolean[Vertices.size()];
//
//		for(int i = 0; i < a.size(); i++) {
//			in_perm[a.get(i)-1] = true;
//		}
//		for (int i = 0 ; i < in_perm.length; i++) {
//			if(!in_perm[i]) {
//				candidate.add(Vertices.get(i));
//			}
//		}
//		//ArrayList<Integer> maxCollection = new ArrayList<>(m);
//		//ArrayList<Integer> ap = new ArrayList<>(a);
//		//ap.add(candidate.get(0));
////		for(int i= 0; i < candidate.size(); i++){
////			if(ap.size() == 0) {
////				break;
////			}
////			int maxSofar = getBandwidthSoFar(ap, adj, candidate.get(i));
////			
////			if(maxSofar >= Max) {
////				candidate = new ArrayList<>();
////				break;
////			}
////			else {
////				maxCollection.add(maxSofar);
////			}
////		}
//		return candidate;
//	}
//	
//	public static boolean isSolution(ArrayList<Integer> a, ArrayList<Integer> vertices, ArrayList<ArrayList<Integer>> adj) {
//		return (a.size()== vertices.size() );
//	}
//	
//	public static void processSolution( ArrayList<Integer> a) {
//		int maxEdge = getBandwidth(maxCollection);
//		if(Max > maxEdge) {
//			Max = maxEdge;
//			result = new ArrayList<Integer>(a);
//		}
//	}
//	
//	public static int getBandwidth(ArrayList<Integer> maxCollection) {
//		int bandwidth = 0;
//		for(int i = 0; i < maxCollection.size(); i++) {
//			bandwidth = Math.max(bandwidth, maxCollection.get(i));
//		}
//		return bandwidth;
//	}
//	
//	public static void backtrack(ArrayList<Integer> a, ArrayList<Integer> vertices,  ArrayList<ArrayList<Integer>> adj) {
//		
//		if(isSolution(a, vertices, adj)) {
//			processSolution(a);
//		}else {
//			
//			ArrayList<Integer> c = Candidates(a, vertices, adj);
//			ArrayList<Integer> ap = new ArrayList<>(a);
//			for(int i= 0; i < c.size(); i++){
//				ap.add(c.get(0));
//				int maxSofar = getBandwidthSoFar(ap, adj, c.get(0));
//				c.remove(0);
//				maxCollection.add(maxSofar);
//				if(maxSofar >= Max) {
//					//c = new ArrayList<>();
//					continue;
//				}
////				else {
////					
////				}
////				//ArrayList<Integer> ap = new ArrayList<>(a);
//				
//				
//				backtrack(ap, vertices, adj);
//				ap.remove(ap.size()-1);
//			}
//			
//				
//			
//
//		}
//	}
//	public static int getBandwidthSoFar(ArrayList<Integer> a, ArrayList<ArrayList<Integer>> adj, int v) {
//		int bandwidth = 0;
//		ArrayList<Integer> Edges = adj.get(v);
//		for(int i =0; i < a.size(); i ++) {
//			
//			for(int j = 0; j < Edges.size(); j++) {
//				int x= Edges.get(j) ;
//				int y = a.get(i);
//				if(x == y) {
//					bandwidth = Math.max(bandwidth, Math.abs(a.size()-(i)));
//				}
//			}
//		}
//		return bandwidth;
//	}
//		
////		for 
//		
////			int x = v;
////			ArrayList<Integer> Edges = adj.get(x);
////			int distance = 0;
////			
////			for(int j = 0; j < a.size(); j++) {
////				int e = a.get(j);
////				
////				for (int k =0; k < Edges.size(); k++) {
////					if( e == Edges.get(k)) {
////						distance = Math.max(distance, a.size() - 1 - k);
////						break;
////					}
////				}
//				
////				if(distance > bandwidth) {
////					bandwidth = distance;
////				}
//				
////			}
//		
//	
//	
//	static void printGraph(ArrayList<ArrayList<Integer> > adj){ 
//		for (int i = 1; i < adj.size(); i++) { 
//			System.out.println("\nAdjacency list of vertex " + i); 
//			System.out.print("head"); 
//			for (int j = 0; j < adj.get(i).size(); j++) { 
//				System.out.print(" -> "+adj.get(i).get(j)); 
//			} 
//			System.out.println(); 
//		} 
//	} 
//	public static int distance(int x, int y, ArrayList<ArrayList<Integer>> adj){
//		int distance = 0;
//		ArrayList<Integer> Edges = adj.get(x);
//		
//		for(int i = 0; i < Edges.size(); i++) {
//			
//			for (int j = 0; j < Edges.size(); j++) {
//				if(y == Edges.get(j)) {
//					distance = i+1;
//					break;
//				}
//			}
//			if(distance != 0) {
//				break;
//			}
//			ArrayList<Integer> cEdges = new ArrayList<Integer>();
//			for(int k = 0; k < Edges.size(); k++) {
//				cEdges.addAll(adj.get(Edges.get(k)));
//			}
//			List<Integer> list = cEdges;
//			list = cEdges.stream() 
//                    .distinct() 
//                    .collect(Collectors.toList()); 
//			cEdges = new ArrayList<Integer>(list);
//			Edges = cEdges;
//		}
//		return distance;
//	}
//	
//	
//	
//	
//
//} 

