package trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

//PreOrder - needs 1 stack,push root & process both of its children <push root,right,left>
//PostOrder - needs 2 stacks,push root & process both of its children <push root,left,right>
//InOrder - needs 1 stack,push root & process only one child <Push root,push left,next iteration push right>
public class BST {
	private static Map<Integer, List<Integer>> mapVerticalDistance = null;
	private static Map<Integer, Integer> bottomViewMap = new TreeMap<Integer, Integer>(); // maintains keys in sorted
																							// order
	private static Map<Integer, Integer> topViewMap = new TreeMap<Integer, Integer>();// maintains keys in sorted order
	int maxDiameterTillNow = Integer.MIN_VALUE;

	public static class Node {
		int data;
		Node left;
		Node right;
		Node parent;// for LCA

		Node(int data) {
			this.data = data;
			this.left = null;
			this.right = null;
			this.parent = null;
		}
	}

	private static class QueueNode {
		Node node;
		int hd;

		QueueNode(Node node, int hd) {
			this.node = node;
			this.hd = hd;

		}

	}

	// https://www.techiedelight.com/insertion-in-bst/
	public Node insertRec(Node root, int data) {

		if (root == null) {
			root = new Node(data);
			return root;
		} else if (data <= root.data) {
			root.left = insertRec(root.left, data);
		} else if (data > root.data) {
			root.right = insertRec(root.right, data);
		}
		return root;
	}

	// loop is mandatory in iterative solutions
	public Node insertIterative(Node root, int data) {
		if (root == null) {
			root = new Node(data);
			return root;
		}
		while (true) {

			// check if data has to be placed in left subtree or right subtree
			if (data <= root.data) {
				// check at what left level it has to be placed
				if (root.left == null) {
					root.left = new Node(data);
					return root;
				} else {
					root = root.left;
				}
			} else if (data > root.data) {
				if (root.right == null) {
					root.right = new Node(data);
					return root;
				} else {
					root = root.right;
				}

			}
		}

	}

	// need to use vertical distance - Iterative approach
	// push each node along with its hd into the queue,construct map of hds as keys
	// with list of nodes in that hd as values
	// https://www.techiedelight.com/vertical-traversal-binary-tree/
	// https://makeinjava.com/vertical-order-traversal-binary-tree-java-recursive-example/
	public static void verticalOrderUsingLevelorder(Node root) {
		if (root == null) {
			return;
		}
		Queue<QueueNode> queue = new LinkedList<QueueNode>();
		QueueNode qn = new QueueNode(root, 0);
		queue.add(qn);

		while (!queue.isEmpty()) {
			List<Integer> list = null;
			// poll() removes as well as return current head
			QueueNode tempNode = queue.poll();
			// check if u already had a list with the hd,if yes add item to the same list
			// else create a new list and add item into the list.
			if (mapVerticalDistance.containsKey(tempNode.hd)) {
				list = mapVerticalDistance.get(tempNode.hd);
			} else {
				list = new ArrayList<Integer>();
			}
			list.add(tempNode.node.data);
			mapVerticalDistance.put(tempNode.hd, list);
			if (tempNode.node.left != null) {
				queue.add(new QueueNode(tempNode.node.left, tempNode.hd - 1));
			}
			if (tempNode.node.right != null) {
				queue.add(new QueueNode(tempNode.node.right, tempNode.hd + 1));
			}
		}

	}

	// Recursive approach
	public static void verticalOrderUsingPreOrder(Node root, int hd) {
		if (root == null) {
			return;
		}

		List<Integer> list = null;
		if (mapVerticalDistance.containsKey(hd)) {
			list = mapVerticalDistance.get(hd);
		} else {
			list = new ArrayList<Integer>();
		}
		list.add(root.data);
		mapVerticalDistance.put(hd, list);
		verticalOrderUsingPreOrder(root.left, hd - 1);
		verticalOrderUsingPreOrder(root.right, hd + 1);
	}

	public static void printVerticalOrder(Node root) {
		if (null == mapVerticalDistance) {
			mapVerticalDistance = new HashMap<Integer, List<Integer>>();
		} else {
			mapVerticalDistance.clear();
		}

//		verticalOrderUsingPreOrder(root, 0);
		verticalOrderUsingLevelorder(root);
		mapVerticalDistance.forEach((key, value) -> System.out.println("Nodes at distance:::" + key + "::::" + value));

	}

	// the moment u encounter null in q,we change null reached to true,so in next
	// iteration we print d node value
	public static void leftViewIterative(Node root) {
		if (root == null)
			return;
		boolean nullReached = false;
		Queue<Node> q = new LinkedList<Node>();
		q.add(root);
		q.add(null);
		nullReached = true;
		while (!q.isEmpty()) {
			Node temp = q.poll();
			if (nullReached) {
				System.out.println("::::" + temp.data);
			}

			if (temp == null && !q.isEmpty()) {

				q.add(null);
				nullReached = true;

			} else {
				// else add the children of extracted node.
				if (temp.left != null) {
					q.add(temp.left);
				}
				if (temp.right != null) {
					q.add(temp.right);
				}
				nullReached = false;
			}
		}
		q.clear();

	}

	public static void rightViewIterative(Node root) {
		if (root == null) {
			return;
		}
		boolean nullReached = false;
		int lastBeforeNull;
		Queue<Node> q = new LinkedList<Node>();
		q.add(root);
		q.add(null);
		nullReached = true;
		lastBeforeNull = root.data;

		while (!q.isEmpty()) {
			Node temp = q.poll();
			if (nullReached) {
				System.out.println("::::" + lastBeforeNull);
			}
			if (temp == null && !q.isEmpty()) {

				q.add(null);
				nullReached = true;

			} else {
				// else add the children of extracted node.
				if (temp.left != null) {
					q.add(temp.left);
					lastBeforeNull = temp.left.data;
				}
				if (temp.right != null) {
					q.add(temp.right);
					lastBeforeNull = temp.right.data;
				}
				nullReached = false;

			}
		}
		q.clear();

	}

	// https://www.geeksforgeeks.org/bottom-view-binary-tree/
	// using horizontal distance computed using vertical order traversal
	// instead of list of nodes stored against a hd, we always replace the value
	// with last or latest node
	// poll & put in map
	public static void printBottomView(Node root) {
		if (root == null) {
			return;
		}

		Queue<QueueNode> q = new LinkedList<QueueNode>();
		QueueNode qn = new QueueNode(root, 0);
		q.add(qn);

		while (!q.isEmpty()) {

			QueueNode temp = q.poll();
			bottomViewMap.put(temp.hd, temp.node.data);

			if (temp.node.left != null) {
				q.add(new QueueNode(temp.node.left, temp.hd - 1));

			}
			if (temp.node.right != null) {
				q.add(new QueueNode(temp.node.right, temp.hd + 1));
			}

			System.out.println(q);
		}

		System.out.println(bottomViewMap);
		bottomViewMap.forEach((key, value) -> System.out.println("Bottom View:::::::" + value));

		/*
		 * // Extract the entries of map into a set to traverse // an iterator over
		 * that. Set<Entry<Integer, Integer>> set = map.entrySet();
		 * 
		 * // Make an iterator Iterator<Entry<Integer, Integer>> iterator =
		 * set.iterator();
		 * 
		 * // Traverse the map elements using the iterator. while (iterator.hasNext()) {
		 * Map.Entry<Integer, Integer> me = iterator.next();
		 * System.out.print(me.getValue()+" "); }
		 */
	}

	// https://www.geeksforgeeks.org/print-nodes-top-view-binary-tree/
	public static void topView(Node root) {

		if (root == null) {
			return;
		}

		Queue<QueueNode> q = new LinkedList<QueueNode>();
		q.add(new QueueNode(root, 0));

		while (!q.isEmpty()) {

			QueueNode temp = q.poll();
			// insert it only one time for a horizontal distance and ignore the rest
			// updations on the existing hds.
			if (!topViewMap.containsKey(temp.hd)) {
				topViewMap.put(temp.hd, temp.node.data);
			}

			if (temp.node.left != null) {
				q.add(new QueueNode(temp.node.left, temp.hd - 1));

			}
			if (temp.node.right != null) {
				q.add(new QueueNode(temp.node.right, temp.hd + 1));
			}

			System.out.println(q);
		}

		topViewMap.forEach((key, value) -> System.out.println("Top View:::::::" + value));

	}

	// https://www.geeksforgeeks.org/diameter-of-a-binary-tree-in-on-a-new-method/
	// printNodesAtKDistanceFromRoot
	public static void printNodesAtLevel(Node root, int level) {
		if (root == null) {
			return;
		} else {
			int height = 0;
			Queue<Node> q = new LinkedList<>();
			q.add(root);
			q.add(null);

			while (!q.isEmpty()) {

				if (height == level - 1) {
					// System.out.println("Node at level::::" + level + ":::::" + temp.data);
					break;
				}

				Node temp = q.poll();

				if (temp == null && !q.isEmpty()) {

					q.add(null);
					height = height + 1;
					System.out.println("Current Height::::" + height);

				} else {

					if (temp.left != null) {
						q.add(temp.left);
					}
					if (temp.right != null) {
						q.add(temp.right);
					}

				}
			}

			while (!q.isEmpty()) {
				Node temp = q.poll();
				if (temp != null)
					System.out.println(temp.data);
			}
		}
	}

	// recursive
	// https://javabypatel.blogspot.com/2015/12/diameter-of-binary-tree.html
	// O(n2)
	public static int diameter(Node root) {
		/* base case if tree is empty */
		if (root == null)
			return 0;

		/*
		 * get the height of left and right sub trees(for calculating diameter passing
		 * thru root)
		 */
		int lheight = height(root.left);
		int rheight = height(root.right);

		/*
		 * get the diameter of left and right subtrees(if diameter isnt passing thru
		 * root)
		 */
		int ldiameter = diameter(root.left);
		int rdiameter = diameter(root.right);

		/*
		 * Return max of following three 1) Diameter of left subtree 2) Diameter of
		 * right subtree 3) Height of left subtree + height of right subtree + 1
		 */
		return Math.max(lheight + rheight + 1, Math.max(ldiameter, rdiameter));
	}

	// O(n)
	private int getDiameterOfBinaryTreeUsingGlobalVariable(Node startNode) {
		if (startNode == null)
			return 0;

		int leftHeight = getDiameterOfBinaryTreeUsingGlobalVariable(startNode.left); // Height of Left Subtree
		int rightHeight = getDiameterOfBinaryTreeUsingGlobalVariable(startNode.right); // Height of Right Subtree

		if (leftHeight + rightHeight + 1 > maxDiameterTillNow) { // + 1 for node itself
			maxDiameterTillNow = leftHeight + rightHeight + 1;
		}
//		if (leftHeight > rightHeight)
//			return leftHeight + 1;
//		else
//			return rightHeight + 1;
		return Math.max(leftHeight, rightHeight) + 1;
	}

	// https://www.geeksforgeeks.org/write-a-c-program-to-find-the-maximum-depth-or-height-of-a-tree/
	/*
	 * Compute the "maxDepth" of a tree -- the number of nodes along the longest
	 * path from the root node down to the farthest leaf node.
	 */
	// https://www.youtube.com/watch?v=rrXAISjtbAA
	// O(n) constant work at every node
	static int height(Node node) {
		if (node == null)
			return 0;
		else {
			/* compute the depth of each subtree */
			int leftHeight = height(node.left);
			int rightHeight = height(node.right);

			/* use the larger one */
			int h;
			if (leftHeight > rightHeight)
				h = leftHeight + 1;
			else
				h = rightHeight + 1;
			return h;
		}
	}

	public boolean checkIfIdenticalRecursive(Node x, Node y) {
		// base case & for leaf nodes
		if (x == null && y == null) {
			return true;
		}

		// If only one is empty
		if (x == null || y == null)
			return false;

		return (x != null && y != null) && (x.data == y.data) && checkIfIdenticalRecursive(x.left, y.left)
				&& checkIfIdenticalRecursive(x.right, y.right);
	}

	// https://www.geeksforgeeks.org/check-if-two-trees-are-mirror/
	public static boolean checkMirrorRecursive(Node a, Node b) {
		/* Base case : Both empty */
		if (a == null && b == null)
			return true;

		// If only one is empty
		if (a == null || b == null)
			return false;

		/*
		 * Both non-empty, compare them recursively Note that in recursive calls, we
		 * pass left of one tree and right of other tree
		 */
		return (a != null && b != null) && (a.data == b.data) && checkMirrorRecursive(a.left, b.right)
				&& checkMirrorRecursive(a.right, b.left);
	}

	// https://www.geeksforgeeks.org/iterative-function-check-two-trees-identical/
	// Iterative method to check if trees are identical
	static boolean areIdentical(Node root1, Node root2) {

		// Return true if both trees are empty
		if (root1 == null && root2 == null)
			return true;

		// Return false if one is empty and other is not
		if (root1 == null || root2 == null)
			return false;

		// Create an empty queues for simultaneous traversals
		Queue<Node> q1 = new LinkedList<Node>();
		Queue<Node> q2 = new LinkedList<Node>();

		// Enqueue Roots of trees in respective queues
		q1.add(root1);
		q2.add(root2);

		while (!q1.isEmpty() && !q2.isEmpty()) {
			// Get front nodes and compare them
			Node n1 = q1.poll();
			Node n2 = q2.poll();

			// pop & compare node data
			if (n1.data != n2.data)
				return false;

			/* Enqueue left children of both nodes */
			if (n1.left != null && n2.left != null) {
				q1.add(n1.left);
				q2.add(n2.left);
			}

			// If one left child is empty and other is not
			else if (n1.left != null || n2.left != null)
				return false;

			// Right child code (Similar to left child code)
			if (n1.right != null && n2.right != null) {
				q1.add(n1.right);
				q2.add(n2.right);
			} else if (n1.right != null || n2.right != null)
				return false;
		}

		return true;
	}

	// https://www.geeksforgeeks.org/iterative-method-check-two-trees-mirror/
	// iterative inorder traversal of one tree and iterative reverse inorder
	// traversal of the other tree in parallel.
	// Remember In Iterative inOrder we processed each half in each iterative
	public static boolean checkMirrorIterative(Node root1, Node root2) {

		Stack<Node> st1 = new Stack<Node>();
		Stack<Node> st2 = new Stack<Node>();

		while (true) {
			// iterative inorder traversal of 1st tree and
			// reverse inorder traversal of 2nd tree
			while (root1 != null && root2 != null) {
				// if the corresponding nodes in the two traversal
				// have different data values, then they are not
				// mirrors of each other.
				if (root1.data != root2.data)
					return false;

				st1.push(root1);
				st2.push(root2);
				root1 = root1.left;
				root2 = root2.right;
			}

			// if at any point one root becomes null and
			// the other root is not null, then they are
			// not mirrors. This condition verifies that
			// structures of tree are mirrors of each other.
			if (!(root1 == null && root2 == null))
				return false;

			if (!st1.isEmpty() && !st2.isEmpty()) {
				root1 = st1.pop();
				root2 = st2.pop();

				/*
				 * we have visited the node and its left subtree. Now, it's right subtree's turn
				 */
				root1 = root1.right;

				/*
				 * we have visited the node and its right subtree. Now, it's left subtree's turn
				 */
				root2 = root2.left;
			}

			// both the trees have been completely traversed
			else
				break;
		}
		return false;

	}

	// https://www.techiedelight.com/find-ancestors-of-given-node-binary-tree/
	public static void ancestorsOfNode(Node root, Node target) {

		if (root == null) {
			return;
		}
		if (target == root) {
			System.out.println("No parent found..it is root node");
		}
		HashMap<Node, Node> parentMap = constructParentMap(root);

		boolean i = true;
		while (i) {
			Node targetParent = parentMap.get(target);
			// runs untill root node is encountered
			if (targetParent != target) {
				System.out.println(targetParent.data);
			} else {
				i = false;
			}
			target = targetParent;
		}

	}

	// https://www.geeksforgeeks.org/print-ancestors-of-a-given-binary-tree-node-without-recursion/
	// If we take a closer look at the recursive postorder traversal, we can easily
	// observe that, when recursive function is called for a node, the recursion
	// call stack contains ancestors of the node. So idea is do iterative Postorder
	// traversal and stop the traversal when we reach the desired node.

	public static void ancestorsOfNodeUsingIterativePostOrder(Node root, int key) {
		if (root == null)
			return;

		// Create a stack to hold ancestors
		Stack<Node> st = new Stack<>();

		// Traverse the complete tree in postorder way till we find the key
		// Process both halves of each node
		while (true) {
			// Traverse the left side. While traversing, push the nodes into
			// the stack so that their right subtrees can be traversed later
			while (root != null && root.data != key) {
				st.push(root); // push current node
				root = root.left; // move to next node
			}

			// If the node whose ancestors are to be printed is found,
			// then break the while loop.
			if (root != null && root.data == key)
				break;
			// Check if right sub-tree exists for the node at top
			// If not then pop that node because we don't need this
			// node any more.
			if (st.peek().right == null) {
				root = st.peek();
				st.pop();

				// If the popped node is right child of top, then remove the top
				// as well. Left child of the top must have processed before.
				while (st.empty() == false && st.peek().right == root) {
					root = st.peek();
					st.pop();
				}
			}
			// if stack is not empty then simply set the root as right child
			// of top and start traversing right sub-tree.
			root = st.empty() ? null : st.peek().right;
		}

		// If stack is not empty, print contents of stack
		// Here assumption is that the key is there in tree
		while (!st.empty()) {
			System.out.print(st.peek().data + " ");
			st.pop();
		}

	}

	// https://www.geeksforgeeks.org/lowest-common-ancestor-in-a-binary-tree-set-2-using-parent-pointer/
	public static Node LCA(Node root, Node n1, Node n2) {

		// Create a map to store ancestors of n1
		Map<Node, Boolean> ancestors = new HashMap<Node, Boolean>();

		// Insert n1 and all its ancestors in map
		while (n1 != null) {
			ancestors.put(n1, Boolean.TRUE);
			n1 = n1.parent;
		}

		// Check if n2 or any of its ancestors is in
		// map.
		while (n2 != null) {
			if (ancestors.containsKey(n2) != ancestors.isEmpty())
				return n2;
			n2 = n2.parent;
		}

		return null;
	}

	/*
	 * A utility function to insert a new node with given key in Binary Search Tree
	 */
	Node insert(Node node, int key) {
		/* If the tree is empty, return a new node */
		if (node == null)
			return new Node(key);

		/* Otherwise, recur down the tree */
		if (key < node.data) {
			node.left = insert(node.left, key);
			node.left.parent = node;
		} else if (key > node.data) {
			node.right = insert(node.right, key);
			node.right.parent = node;
		}

		/* return the (unchanged) node pointer */
		return node;
	}

	// We do not add the root element to the queue.Just the children of root are
	// inserted.
	// https://www.geeksforgeeks.org/check-symmetric-binary-tree-iterative-approach/
	public static boolean checkIfSymmetric(Node root) {
		/* This allows adding null elements to the queue */
		Queue<Node> q = new LinkedList<Node>();

		/* Initially, add left and right nodes of root */
		q.add(root.left);
		q.add(root.right);

		while (!q.isEmpty()) {
			/*
			 * remove the front 2 nodes to check for equality
			 */
			Node tempLeft = q.remove();
			Node tempRight = q.remove();

			/*
			 * if both are null, continue and chcek for further elements
			 */
			if (tempLeft == null && tempRight == null)
				continue;

			/* if only one is null---inequality, retun false */
			if ((tempLeft == null && tempRight != null) || (tempLeft != null && tempRight == null))
				return false;

			/*
			 * if both left and right nodes exist, but have different values-- inequality,
			 * return false
			 */
			if (tempLeft.data != tempRight.data)
				return false;

			/*
			 * Note the order of insertion of elements to the queue : 1) left child of left
			 * subtree 2) right child of right subtree 3) right child of left subtree 4)
			 * left child of right subtree
			 */
			q.add(tempLeft.left);
			q.add(tempRight.right);
			q.add(tempLeft.right);
			q.add(tempRight.left);
		}

		/* if the flow reaches here, return true */
		return true;
	}

	// https://algorithms.tutorialhorizon.com/construct-binary-search-tree-from-a-given-preorder-traversal-using-stack-without-recursion/
	public static Node reconstructBSTFromPreOrder(int[] preorder) {
		Stack<Node> s = new Stack<Node>();
		Node root = new Node(preorder[0]);// first node in preOrder is always root
		s.add(root);
		for (int i = 1; i < preorder.length; i++) {
			Node x = null;
			// if element is greater than root that is already there in stack,pop elements
			// from stack & push it as right child of last popped element,else push it as
			// left child of top element in stack

			while (!s.isEmpty() && preorder[i] > s.peek().data) {
				x = s.pop();
			}
			if (x != null) {
				x.right = new Node(preorder[i]);
				s.push(x.right);
			} else {
				s.peek().left = new Node(preorder[i]);
				s.push(s.peek().left);
			}
		}
		return root;
	}

	// https://www.youtube.com/watch?v=LU4fGD-fgJQ
	// https://www.youtube.com/watch?v=TWDigbwxuB4
	public static int checkIfHeightBalanced(Node currentNode) {

		if (currentNode == null) {
			return 0;
		}

		int leftSubtreeHeight = checkIfHeightBalanced(currentNode.left);
		if (leftSubtreeHeight == -1)
			return -1;

		int rightSubtreeHeight = checkIfHeightBalanced(currentNode.right);
		if (rightSubtreeHeight == -1)
			return -1;

		if (Math.abs(leftSubtreeHeight - rightSubtreeHeight) > 1) {
			return -1;
		}

		return (Math.max(leftSubtreeHeight, rightSubtreeHeight) + 1);
	}

	// https://www.youtube.com/watch?v=nPtARJ2cYrg
	// A tree is a directed acyclic connected graph,hence BFS-queue,but we need to
	// convert it
	// into an undirected graph to keep a track of the parent also.SO v need a
	// hashtable

	public static void nodesAtDistancek(Node root, Node startNode, int k) {

		if (startNode == null) {
			return;
		}

		HashMap<Node, Node> parentMap = constructParentMap(root);
//		parentMap.forEach((key, value) -> System.out.println("Parent of:::" + key.data + "is::" + value.data));

		int currentLayer = 0;
		Queue<Node> q = new LinkedList<>();
		// maintain visited nodes similar to BFS in graph/Maze
		HashMap<Node, Boolean> hm = new HashMap<>();
		q.add(startNode);
		q.add(null);
		hm.put(startNode, true);

		while (!q.isEmpty()) {

			if (currentLayer == k) {
				while (!q.isEmpty()) {
					if (q.peek() != null) {
						System.out.println(q.poll().data);
					} else {
						q.remove();
					}

				}
				return;

			}

			Node temp = q.poll();

			if (temp == null && !q.isEmpty()) {

				q.add(null);

				currentLayer = currentLayer + 1;
			} else {
				// if left child isnt null & unvisited
				if (temp.left != null && !hm.containsKey(temp.left)) {
					hm.put(temp.left, true);
					q.add(temp.left);

				}
				// if right child isnt null & unvisited
				if (temp.right != null && !hm.containsKey(temp.right)) {
					hm.put(temp.right, true);
					q.add(temp.right);

				}

				// add parent of current node to queue & mark it as visited
				if (!hm.containsKey(parentMap.get(temp))) {
					hm.put(parentMap.get(temp), true);
					q.add(parentMap.get(temp));

				}

			}

		}

	}

	// using level order traversal
	private static HashMap<Node, Node> constructParentMap(Node root) {
		HashMap<Node, Node> parentMap = new HashMap<>();
		if (root == null) {
			return null;
		}
		parentMap.put(root, root);
		Queue<Node> q = new LinkedList<>();
		q.add(root);
		q.add(null);

		while (!q.isEmpty()) {
			Node temp = q.poll();

			if (temp == null && !q.isEmpty()) {

				q.add(null);

			} else {
				if (temp.left != null) {
					q.add(temp.left);
					parentMap.put(temp.left, temp);
				}
				if (temp.right != null) {
					q.add(temp.right);
					parentMap.put(temp.right, temp);
				}
			}
		}

		parentMap.forEach((key, value) -> System.out.println("Parent of:::" + key.data + "is::" + value));
		return parentMap;
	}

	// https://www.techiedelight.com/build-binary-tree-given-parent-array/
	// Function to build the tree from given parent array
	public static Node createTree(int[] parent) {
		// create an empty map
		Map<Integer, Node> map = new HashMap<>();

		// create n new tree nodes each having value from 0 to n-1
		// and store them in a map
		for (int i = 0; i < parent.length; i++) {
			map.put(i, new Node(i));
		}

		// represents root node of binary tree
		Node root = null;

		// traverse the parent array and build the tree
		for (int i = 0; i < parent.length; i++) {
			// if parent is -1, set root to current node having
			// value i (stored in map[i])
			if (parent[i] == -1) {
				root = map.get(i);
			} else {
				// get parent node for current node
				Node ptr = map.get(parent[i]);

				// if parent's left child is filled, map the node to its right child
				if (ptr.left != null) {
					ptr.right = map.get(i);
				}
				// if parent's left child is empty, map the node to it
				else {
					ptr.left = map.get(i);
				}
			}
		}

		// return root of the constructed tree
		return root;
	}

	// https://www.techiedelight.com/determine-given-binary-tree-is-a-bst-or-not/
	// using inorder traversal and tracking last variable to check if its in sorted
	// order
	public boolean isBST(Node root, Node prev) {

		// base case: empty tree is a BST
		if (root == null) {
			return true;
		}

		// check if left subtree is BST or not
		boolean left = isBST(root.left, prev);

		// value of current node should be more than that of previous node
		if (root.data <= prev.data) {
			return false;
		}

		// update previous node data and check if right subtree is BST or not
		prev.data = root.data;

		return left && isBST(root.right, prev);
	}

	// Function to determine if given Binary Tree is a BST or not
	void isBST(Node node) {
		// pointer to store previous processed node in inorder traversal
		Node prev = new Node(Integer.MIN_VALUE);

		// check if nodes are nodes are processed in sorted order
		if (isBST(node, prev))
			System.out.println("This is a BST.");
		else
			System.out.println("This is NOT a BST!");
	}

	

	

	

	// https://www.techiedelight.com/place-convert-given-binary-tree-to-doubly-linked-list/
	// do an inorder traversal and for every node insert it at front of LL and then
	// finally reverse the linked list
	public static Node convertBT_DLL(Node root, Node head) {
		// base case: tree is empty
		if (root == null) {
			return head;
		}

		// recursively convert left subtree first
		convertBT_DLL(root.left, head);

		// store right child
		Node right = root.right;

		// insert current node in the beginning of DLL
		root.right = head;
		if (head != null) {
			head.left = root;
		}

		head = root;

		// recursively convert right subtree
		return convertBT_DLL(right, head);
	}

	

	

	

	// https://www.techiedelight.com/find-triplet-with-given-sum-bst/
	// traverse BST in inorder which gives all nodes in sorted order,now use hashing
	// to find out triplet with given sum - O(n^2)

	// https://www.techiedelight.com/find-pair-with-given-sum-bst/
	// traverse BSTin inorder and use hashing to find the pair -O(n)

	

//
//	public static void main(String args[]) {
//
//		Node root = new Node(12);
//		root.left = new Node(3);
//		root.right = new Node(30);
//		root.left.left = new Node(9);
//		root.left.right = new Node(59);
//		root.right.left = new Node(11);
//		root.right.right = new Node(23);
//		root.left.right.right = new Node(100);
//		root.right.left.right = new Node(89);
//		root.right.right.right = new Node(36);
//
////		printVerticalOrder(root);
////		leftViewIterative(root);
////		rightViewIterative(root);
//		printNodesAtLevel(root, 3);
////		printBottomView(root);
////		height(root);
////		nodesAtDistancek(root, root.left, 2);
////		ancestorsOfNode(root, root.right.right);
////		LCA(root, root.right.right.right, root.right);
//	}

//	// main function
	public static void main(String[] args) {
		/*
		 * Construct below tree 1 / \ / \ 2 3 / / \ / / \ 4 5 6 / / \ \ / / \ \ 7 8 9 10
		 */

		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.right.left = new Node(5);
		root.right.right = new Node(6);
		root.left.left.left = new Node(7);
		root.right.left.left = new Node(8);
		root.right.left.right = new Node(9);
		root.right.right.right = new Node(10);

		//printCornerNodes(root);
	}

}
