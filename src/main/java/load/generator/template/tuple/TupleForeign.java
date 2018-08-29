package load.generator.template.tuple;

import java.util.ArrayList;

public class TupleForeign {
    private int tableLoc;
    private ArrayList<TupleType> tupleTypes;


    public TupleForeign(int tableLoc, ArrayList<TupleType> tupleTypes) {
        this.tableLoc = tableLoc;
        this.tupleTypes = tupleTypes;
    }

    public ArrayList<TupleType> getTupleTypes() {
        return tupleTypes;
    }


    public int getTableLoc() {
        return tableLoc;
    }


}
