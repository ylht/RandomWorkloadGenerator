package load.generator.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.util.List;

public class LoadConfig {
    private static SAXReader reader = new SAXReader();
    private static Document document;

    static {
        try {
            document = reader.read("config/config.tld");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static Document getConfig() {
        return document;
    }


    public static void main(String[] args) {
        Document dc = LoadConfig.getConfig();
        String xpath = "//generator/table/tableNum";
        List<Node> list = dc.selectNodes(xpath);
        for (Node l : list) {
            System.out.println(l.valueOf("max"));
        }
    }
}
