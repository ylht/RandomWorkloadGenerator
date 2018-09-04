package load.generator.utils;

import java.util.ArrayList;

class TreeNode {
    private int value;
    private ArrayList<Integer> parents;
    private ArrayList<Integer> children=new ArrayList<>();
    private boolean canRef = true;
    private int level;

    TreeNode(int value) {
        this.value = value;
    }

    int getLevel() {
        return level;
    }

    void setLevel(int level) {
        this.level = level;
    }

    void setParents(ArrayList<Integer> parents) {
        this.parents = parents;
    }

    int getValue() {
        return value;
    }

    ArrayList<Integer> getParent() {
        return parents;
    }

    boolean hasChildren()
    {
        if(children.size()>0)
        {
            return true;
        }
        else{
            return false;
        }
    }

    void addChild(int childIndex)
    {
        children.add(childIndex);
    }

    boolean isCanRef() {
        return canRef;
    }

    void setCanRef(boolean canRef) {
        this.canRef = canRef;
    }

}
