package load.generator.generator;

import load.generator.template.TableTemplate;

import java.util.List;

public class GeneratorData {
    private TableTemplate[] tables;
    public GeneratorData(TableTemplate[] tables)
    {
        this.tables=tables;
    }

    public void loadData()
    {
        for(TableTemplate table:tables)
        {

        }
    }
}
