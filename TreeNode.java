/* * * * * * * * * * * * * * * * * * * * *
 * 	Urjanet - programming test #1 & #2   *
 * 	Joseph D. Yun				         *
 * 	10/28/2016 						     *
 * 								         *
 * * * * * * * * * * * * * * * * * * * * */
import java.util.ArrayList;
import java.util.LinkedList;

public class TreeNode implements GNode{
	private String name;
	private GNode[] children;
	
	//Constructors
	public TreeNode(String name){
		this.name = name;
	}
	
	public TreeNode(String name, GNode[] children){
		this.name = name;
		this.children = children;
	}

	public String getName() {
		return this.name;
	}

	public GNode[] getChildren() {
		return this.children;
	}
	
	
	//#1 - walkGraph()
	public ArrayList<GNode> walkGraph(GNode node){
		
		ArrayList<GNode> result_list = new ArrayList<GNode>();
		ArrayList<GNode> buffer_list = new ArrayList<GNode>();
		TreeNode current_node = null;
		int remaining_node = node.getChildren().length;
	
		// check if node has child nodes
		if(remaining_node <= 0){
			result_list.add(node);
			return result_list;
		}
		
		buffer_list.add(node);
		
		while( !(buffer_list.isEmpty()) ){
			current_node = (TreeNode) buffer_list.get(0);
			buffer_list.remove(0);
			
			//check duplicates
			if(buffer_list.contains(current_node) == false){
				//add first node in child_list to final_list
				result_list.add(current_node);	
			}
			
			remaining_node = current_node.getChildren().length;
			//current_node has child nodes
			if(remaining_node > 0){	
				//iterate to add all current_node children
				for(int i=0; i<remaining_node; i++){
					//add children to child_list
					buffer_list.add(current_node.getChildren()[i]);
				}
			}
		}
		return result_list;
		
	}//ends walkGraph
	
	
	// Pathlink(GNode, ArrayList<> of path)  
	private static class Pathlink{	
		private GNode node;
		private ArrayList<GNode> path;
		
		// node = target node
		// path = path with target node || target node + its children
		public Pathlink(GNode node, ArrayList<GNode> path){
			this.node = node;
			this.path = path;
		}
	}
	
	/*			 [A]
	 * 	   	[B]		  [C]
	 * 	 [D]   [E]		 [F]
	 *  
	 * -> [ [A,B,D],[A,B,E],[A,C,F] ]
	 * 
	 * paths returns Arraylist of Arraylists 
	 */ 
	//#2 - paths()
	public ArrayList<ArrayList<GNode>> paths(GNode node){
		
		ArrayList<Pathlink> buffer_list = new ArrayList<Pathlink>();
		ArrayList<ArrayList<GNode>> result_list = new ArrayList<ArrayList<GNode>>();
		
		buffer_list.add(new Pathlink(node, new ArrayList<GNode>()));
		
		while( !(buffer_list.isEmpty()) ){
			// instantiating Pathlink
			Pathlink current_node = buffer_list.get(0);
			buffer_list.remove(0);
			
			// adding current_node.node to its current_node.path
			// 
			// A:(A,[A]), B:(B,[A,B]), C:(C,[A,C])
			current_node.path.add(current_node.node);
			
			if(current_node.node.getChildren().length < 1){
				//add the path to result_list
				result_list.add(current_node.path);
			
			} else {
				//get current_node's children
				for(GNode child : current_node.node.getChildren()){
					
					// for every current_node's children, add into buffer_list
					//
					// (After each iteration)
					// buffer_list = (B,[A]), (C,[A]) ... (D,[A,B]),(E,[A,B]) ... (F,[A,C])
					buffer_list.add(new Pathlink(child, new ArrayList<GNode>(current_node.path)));
				}
				
			}
		}
		
		//return_list = [ [A,B,D],[A,B,E],[A,C,F] ]
		return result_list;
		
	}//ends paths

}
