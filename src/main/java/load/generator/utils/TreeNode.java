package load.generator.utils;

import java.util.ArrayList;

public class TreeNode {
    private int value;
    private ArrayList<Integer> parents;
    private ArrayList<Integer> children = new ArrayList<>();
    private boolean canRef = true;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private int level;

    public TreeNode(int value) {
        this.value = value;
    }

    public ArrayList<Integer> getParents() {
        return parents;
    }

    public void setParents(ArrayList<Integer> parents) {
        this.parents = parents;
    }

    public ArrayList<Integer> getChildren() {
        return children;
    }

    public int getValue() {
        return value;
    }

    public ArrayList<Integer> getParent() {
        return parents;
    }

    public boolean isCanRef() {
        return canRef;
    }

    public void setCanRef(boolean canRef) {
        this.canRef = canRef;
    }

    public void addChildren(int child) {
        children.add(child);
    }


}
