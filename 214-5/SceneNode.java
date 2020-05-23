/**
 * This class represents a scene within a game, stored as a node within a SceneTree
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class SceneNode{
    private static int numScenes = 0;
    private String title;
    private String sceneDescription;
    private int sceneID;
    private SceneNode left;
    private SceneNode middle;
    private SceneNode right;
    private SceneNode parent;

    /**
     * Constructor for the SceneNode
     */
    public SceneNode(String title, String sceneDescription, SceneNode parent){
        this.sceneID = ++numScenes;
        this.title = title;
        this.sceneDescription = sceneDescription;
        this.left = null;
        this.middle = null;
        this.right = null;
        this.parent = parent;
    }

    /**
     * getter for the number of scenes
     * @return
     * returns the total number of scenes
     */
    public static int getNumScenes(){
        return numScenes;
    }

    /**
     * getter for the scene ID
     * @return
     * returns the scene ID of the SceneNode
     */
    public int getSceneID(){
        return this.sceneID;
    }
    /**
     * getter for the title
     * @return
     * returns the title of the SceneNode
     */
    public String getTitle(){
        return title;
    }
    /**
     * getter for parent of the node
     * @return
     * returns the parent of the node
     */
    public SceneNode getParent(){
        return parent;
    }
    /**
     * getter for the left child of the node
     * @return
     * returns the left child
     */
    public SceneNode getLeft(){
        return left;
    }
    /**
     * getter for the middle child of the node
     * @return
     * returns the middle child
     */
    public SceneNode getMiddle(){
        return middle;
    }
    /**
     * getter for the right child of the node
     * @return
     * returns the right child
     */
    public SceneNode getRight(){
        return right;
    }
    /**
     * Setter for the parent of the node
     */
    public void setParent(SceneNode dab){
        this.parent = dab;
    }

    /**
     * Setter for the left child of the node
     */
    public void setLeft(SceneNode dab){
        this.left = dab;
    }

    /**
     * Setter for the middle child of the node
     */
    public void setMiddle(SceneNode dab){
        this.middle = dab;
    }

    /**
     * Setter for the right child of the node
     */
    public void setRight(SceneNode dab){
        this.right = dab;
    }

    /**
     * Method to add a child to the node
     * @param scene
     * The new scene to add as a child
     * @throws FullSceneException
     * Thrown if the node is already full
     */
    public void addSceneNode(SceneNode scene) throws FullSceneException{
        if(this.isFull())
            throw new FullSceneException();
        else if(this.left == null)
            this.left = scene;
        else if(this.middle == null)
            this.middle = scene;
        else this.right = scene;
    }

    /**
     * Checks if the node is ending / has no children
     * @return
     * returns true if the node has no children, false otherwise
     */
    public boolean isEnding(){
        return this.left == null && this.middle == null && this.right == null;
    }

    /**
     * Checks if the node is full (3 children)
     * @return
     * returns true if the node has 3 non-null children, false otherwise
     */
    public boolean isFull(){
        return this.left != null && this.middle != null && this.right != null;
    }

    /**
     * Displays the scene as it would appear in the playGame() method in AdventureDesigner class
     */
    public void displayScene(){
        String first = this.title + "\n" + this.sceneDescription + "\n\n";
        if(this.left != null)
            first = first + "A) " + this.left.toString() + "\n";
        if(this.middle != null)
            first = first + "B) " + this.middle.toString() + "\n";
        if(this.right != null)
            first = first + "C) " + this.right.toString() + "\n";
        System.out.println(first);
    }

    /**
     * Displays the scene's information, including the title, scene id, scene description, and it's children
     */
    public void displayFullScene(){
        String first = "Scene ID #"+ sceneID +"\n" +
                "Title: " + title + "\n" +
                "Scene: " + sceneDescription + "\n" +
                "Leads to:";
        if(this.isEnding())
            first = first + " NONE";
        if(this.left != null)
            first = first + " " + this.left.toString();
        if(this.middle != null)
            first = first + ", " + this.middle.toString();
        if(this.right != null)
            first = first + ", " + this.right.toString();
        System.out.println(first);
    }

    /**
     * Creates a string representation of the sceneNode
     * @return
     * returns a textual representation of this sceneNode
     */
    public String toString(){
        return this.title + " (#" + this.sceneID + ")";
    }
}