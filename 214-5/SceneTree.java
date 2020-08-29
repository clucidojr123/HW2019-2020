/**
 * This class represents a tree of SceneNodes
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class SceneTree {
    private SceneNode root;
    private SceneNode cursor;

    /**
     * The constructor for the SceneTree
     */
    public SceneTree(){
        root = null;
        cursor = null;
    }

    /**
     * Getter for the root node
     * @return
     * returns the root node
     */
    public SceneNode getRoot() {
        return root;
    }

    /**
     * getter for the cursor node
     * @return
     * returns the cursor node
     */
    public SceneNode getCursor(){
        return cursor;
    }
    /**
     * setter for the cursor node
     * @param cursor
     * the new cursor
     */
    public void setCursor(SceneNode cursor) {
        this.cursor = cursor;
    }

    /**
     * Moves the cursor to it's parent if possible
     * @throws NoSuchNodeException
     * Thrown if the cursor does not have a parent
     */
    public void moveCursorBackwards() throws NoSuchNodeException{
        if(cursor == null || cursor == root)
            throw new NoSuchNodeException();
        else cursor = cursor.getParent();
    }

    /**
     * Moves the cursor forward based on user input
     * @param option
     * The selected node to move towards
     * @throws NoSuchNodeException
     * Thrown if there is no node in within the option selected
     */
    public void moveCursorForward(String option) throws NoSuchNodeException{
        if(cursor == null)
            throw new NoSuchNodeException();
        switch(option){
            case "A":
                if(cursor.getLeft() == null)
                    throw new NoSuchNodeException();
                cursor = cursor.getLeft();
                break;
            case "B":
                if(cursor.getMiddle() == null)
                    throw new NoSuchNodeException();
                cursor = cursor.getMiddle();
                break;
            case "C":
                if(cursor.getRight() == null)
                    throw new NoSuchNodeException();
                cursor = cursor.getRight();
                break;
            default:
                throw new IllegalArgumentException();
        }

    }

    /**
     * Adds a new SceneNode to the tree
     * @param title
     * The title of the new scene
     * @param sceneDescription
     * The description of the new scene
     * @throws FullSceneException
     * Thrown if the cursor's children are all full
     */
    public void addNewNode(String title, String sceneDescription) throws FullSceneException{
        if(cursor == null) {
            SceneNode temp = new SceneNode(title, sceneDescription, null);
            cursor = temp;
            root = temp;
       } else cursor.addSceneNode(new SceneNode(title, sceneDescription, cursor));
    }

    /**
     * Removes a child of the cursor based on input
     * @param option
     * The selected node to remove
     * @param print
     * Whether or not to print status of removal
     * @throws NoSuchNodeException
     * Thrown if there is no node at the selected input
     */
    public void removeScene(String option, boolean print) throws NoSuchNodeException{
        switch(option){
            case "A":
                if(cursor.getLeft() == null)
                    throw new NoSuchNodeException();
                if(print)
                    System.out.println(cursor.getLeft().getTitle() + " has been removed.");
                cursor.setLeft(null);
                break;
            case "B":
                if(cursor.getMiddle() == null)
                    throw new NoSuchNodeException();
                if(print)
                    System.out.println(cursor.getMiddle().getTitle() + " has been removed.");
                cursor.setMiddle(null);
                break;
            case "C":
                if(cursor.getRight() == null)
                    throw new NoSuchNodeException();
                if(print)
                    System.out.println(cursor.getRight().getTitle() + " has been removed.");
                cursor.setRight(null);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Moves the cursor to become a child of the node with the specified id#
     * @param sceneIDToMoveTo
     * The scene number to move to
     * @throws NoSuchNodeException
     * Thrown if the node does not exist
     * @throws FullSceneException
     * Thrown if the scene to move towards is already full
     */
    public void moveScene(int sceneIDToMoveTo) throws NoSuchNodeException, FullSceneException {
        if(root == null)
            throw new NoSuchNodeException();
        if(childContains(root, sceneIDToMoveTo) || root.getSceneID() == sceneIDToMoveTo) {
            if(childContains(cursor,sceneIDToMoveTo))
                System.out.println("Unable to move scene as it would create a circular link! Please try again.");
            else {
                String option = cursor.getParent().getLeft() == cursor ? "A" :
                        cursor.getParent().getMiddle() == cursor ? "B" :  "C";
                moveHelper(root,option,sceneIDToMoveTo);
            }
        } else
            throw new NoSuchNodeException();
    }

    /**
     * Helper function for the moveScene method
     * @param temp
     * The SceneNode called
     * @param option
     * Specifies which node of the cursor's parent to remove
     * @param id
     * The scene id to move towards
     */
    public void moveHelper(SceneNode temp, String option, int id) throws FullSceneException, NoSuchNodeException {
        if(temp.isEnding())
            return;
        if(root.getSceneID() == id) {
            root.addSceneNode(cursor);
            SceneNode temp2 = cursor;
            moveCursorBackwards();
            removeScene(option, false);
            cursor = temp2;
            cursor.setParent(root);
            return;
        }
        if(temp.getLeft() != null) {
            if(temp.getLeft().getSceneID() == id) {
                temp.getLeft().addSceneNode(cursor);
                SceneNode temp2 = cursor;
                moveCursorBackwards();
                removeScene(option, false);
                cursor = temp2;
                cursor.setParent(temp);
                return;
            }
            moveHelper(temp.getLeft(),option,id);
        }
        if(temp.getMiddle() != null) {
            if(temp.getMiddle().getSceneID() == id) {
                temp.getMiddle().addSceneNode(cursor);
                SceneNode temp2 = cursor;
                moveCursorBackwards();
                removeScene(option, false);
                cursor = temp2;
                cursor.setParent(temp);
                return;
            }
            moveHelper(temp.getMiddle(),option,id);
        }
        if(temp.getRight() != null) {
            if(temp.getRight().getSceneID() == id) {
                temp.getRight().addSceneNode(cursor);
                SceneNode temp2 = cursor;
                moveCursorBackwards();
                removeScene(option,false);
                cursor = temp2;
                cursor.setParent(temp);
                return;
            }
            moveHelper(temp.getRight(),option,id);
        }
    }

    /**
     * Checks if a child of the SceneNode contains a scene with the given SceneNode.
     * This is used in the moveScene method to avoid circular links
     * @param temp
     * The SceneNode to check
     * @param id
     * The id to search for
     * @return
     * Returns true if the SceneNode contains a child with the given id
     */
    public boolean childContains(SceneNode temp, int id){
        if(temp.isEnding())
            return false;
        if(temp.getLeft() != null) {
            if(temp.getLeft().getSceneID() == id)
                return true;
            if(childContains(temp.getLeft(),id))
                return true;
        }
        if(temp.getMiddle() != null) {
            if(temp.getMiddle().getSceneID() == id)
                return true;
            if(childContains(temp.getMiddle(),id))
                return true;
        }
        if(temp.getRight() != null) {
            if(temp.getRight().getSceneID() == id)
                return true;
            if(childContains(temp.getRight(),id))
                return true;
        }
        return false;
    }

    /**
     * Creates a path from the root to the cursor
     * @return
     * Returns a string representing the path
     */
    public String getPathFromRoot(){
        String first = "";
        SceneNode temp = cursor;
        if(root == null)
            return first;
        else {
            while(temp != null) {
                if(temp == cursor)
                    first = temp.getTitle() + first;
                else
                    first = temp.getTitle() + ", " + first;
                temp = temp.getParent();
            }
        }
        return first;
    }

    /**
     * Creates a representation of the tree
     * @return
     * Returns a string representing the tree
     */
    public String toString(){
        return createString(0,"",root);
    }

    /**
     * Helper method for the toString method
     * @param depth
     * depth of the current node
     * @param s
     * the string to add upon
     * @param temp
     * the SceneNode to add
     * @return
     * Returns a textual representation of the tree;
     */
    public String createString(int depth, String s, SceneNode temp){
        if(temp == root) {
            s = temp.getTitle() + " #" + temp.getSceneID();
            if(temp == cursor)
                s = s + " * \n";
            else
                s = s + "\n";
        }
        if(temp.getLeft() != null) {
            s = s + "  ".repeat(depth + 1) + "A) " + temp.getLeft().getTitle() + " #" + temp.getLeft().getSceneID();
            if(temp.getLeft() == cursor)
                s = s + " * \n";
            else
                s = s + "\n";
            s = createString(depth + 1,s,temp.getLeft());
        }
        if(temp.getMiddle() != null) {
            s = s + "  ".repeat(depth + 1) + "B) " + temp.getMiddle().getTitle() + " #" + temp.getMiddle().getSceneID();
            if(temp.getMiddle() == cursor)
                s = s + " * \n";
            else
                s = s + "\n";
            s = createString(depth + 1,s,temp.getMiddle());
        }
        if(temp.getRight() != null) {
            s = s + "  ".repeat(depth + 1) + "C) " + temp.getRight().getTitle() + " #" + temp.getRight().getSceneID();
            if(temp.getRight() == cursor)
                s = s + " * \n";
            else
                s = s + "\n";
            s = createString(depth + 1,s,temp.getRight());
        }
        return s;
    }
}
