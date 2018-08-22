package load.generator.template.tuple;

public class TupleForeign {
    private int tableLoc;
    private int[] selfTVLoc;
    private int[] refTVLoc;
    private int[] rangeMin;
    private int[] rangeMax;

    public TupleForeign(int tableLoc, int[] selfTVLoc, int[] refTVLoc, int[] rangeMin, int[] rangeMax) {
        this.tableLoc = tableLoc;
        this.selfTVLoc = selfTVLoc;
        this.refTVLoc = refTVLoc;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
    }

    public int getRangeMin(int index) {
        return rangeMin[index];
    }

    public int getRangeMax(int index) {
        return rangeMax[index];
    }

    public int getTableLoc() {
        return tableLoc;
    }

    public int[] getSelfTVLoc() {
        return selfTVLoc;
    }

    public int[] getRefTVLoc() {
        return refTVLoc;
    }
}
