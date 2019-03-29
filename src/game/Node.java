package game;
import java.util.ArrayList;

public abstract class Node implements Comparable<Node>
{
    protected Node parentNode;
    protected int depth;
    protected int pathCost; //blocks left
    protected int priority;
    protected String operator;
    protected static ArrayList<String> solution = new ArrayList<String>(); //Prolly vai-se mudar isto

    public Node(Node parentNode, int depth, int pathCost, String operator)
    {
        this.parentNode = parentNode;
        this.depth = depth;
        this.pathCost = pathCost;
        this.operator = operator;
    }

    public Node(Node parentNode, String operator)
    {
        this.depth = parentNode.depth + 1;
        this.pathCost = 1;
        this.parentNode = parentNode;
        this.operator = operator;
    }

    public abstract ArrayList<Node> expandNode();
    public abstract boolean depthSearch();
    public abstract boolean iterativeDepthSearch();
    public abstract boolean depthLimitedSearch(int depth);
    public abstract boolean testGoal();
    public abstract void traceSolution();
    public abstract void traceSolutionUp();

    public Node getParentNode()
    {
        return parentNode;
    }

    public int getDepth()
    {
        return depth;
    }

    public int getPathCost()
    {
        return pathCost;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int compareTo(Node o) {
        return o.pathCost - this.pathCost;
    }

    public static ArrayList<String> getSolution()
    {
        return solution;
    }

}