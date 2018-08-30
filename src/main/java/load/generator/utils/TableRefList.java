package load.generator.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TableRefList {
    private List<TreeNode> listNodes = new LinkedList<>();
    private List<TreeNode> currentNodes = new LinkedList<>();
    private Random r = new Random();


    public int randomGetNode() {
        int result = -1;
        currentNodes.clear();
        for (TreeNode listNode : listNodes) {
            if (listNode.isCanRef()) {
                currentNodes.add(listNode);
            }
        }
        int len = currentNodes.size();
        if (len > 0) {
            int index = r.nextInt(len);
            result = currentNodes.get(index).getValue();
            makeNotRef(result);
        }
        return result;
    }

    public boolean isHasTable() {
        for (TreeNode treeNode : listNodes) {
            if (treeNode.isCanRef()) {
                return true;
            }
        }
        return false;
    }

    public int getLevel(int index) {
        ArrayList<Integer> parents = listNodes.get(index).getParent();
        int max = 0;
        for (Integer parent : parents) {
            int temp = listNodes.get(parent).getLevel();
            if (temp > max) {
                max = temp;
            }
        }
        listNodes.get(index).setLevel(max + 1);
        return max + 1;
    }


    private void makeNotRef(int index) {
        listNodes.get(index).setCanRef(false);
        for (Integer aParent : listNodes.get(index).getParent()) {
            makeNotRef(aParent);
        }
    }

    public void addNode(ArrayList<Integer> parent) {
        int index = listNodes.size();
        TreeNode node = new TreeNode(index);
        node.setParents(parent);
        for (Integer aParent : parent) {
            listNodes.get(aParent).setCanRef(true);
        }
        listNodes.add(node);
    }
}
