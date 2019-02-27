import java.util.ArrayList;

public class Tree<T> {
	T data;
	Tree<T> parent;
	ArrayList<Tree<T>> leafs;
	
	public Tree(T data) {
		this.data = data;
		this.leafs = new ArrayList<Tree<T>>();	
	}
	public Tree<T> addLeaf (T leaf){
		Tree<T> leafNode = new Tree<T>(leaf);
		leafNode.parent = this;
		this.leafs.add(leafNode);
		return leafNode;
	}
	public Tree<T> search (T targetData, Tree<T> tree){
		//check if the data being searched is what we're asking for
		if(tree.data.equals(targetData)) {
			return tree;
		}
		if(tree.leafs.isEmpty()) {
			return null;
		}
		//search the children
		for(int i = 0; i < tree.leafs.size();i++) {
			Tree<T> possible = search(targetData,tree.leafs.get(i));
			if(possible.data.equals(targetData)) {
				return possible;
			}
		}
		return null;
		
	}
}
