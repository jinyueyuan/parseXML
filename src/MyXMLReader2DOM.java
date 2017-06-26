import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MyXMLReader2DOM {
    /*    public static void main(String arg[]) {

            long lasting = System.currentTimeMillis();

            try {
                File f = new File("data_10k.xml");
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(f);

                NodeList nl = doc.getElementsByTagName("VALUE");
                for (int i = 0; i < nl.getLength(); i++) {
                    System.out.print("车牌号码:"+ doc.getElementsByTagName("NO").item(i).getFirstChild().getNodeValue());
                    System.out.println("车主地址:"+ doc.getElementsByTagName("ADDR").item(i).getFirstChild().getNodeValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    */
    public static void prin(Node element){
        NamedNodeMap attrs = element.getAttributes();
        if(attrs != null) {
            String nodename = element.getNodeName();
            for (int j = 0; j < attrs.getLength(); j++) {
                //通过item(index)方法获取book节点的某一个属性
                Node attr = attrs.item(j);
                //获取属性名
                System.out.print(nodename+ "的属性名：" + attr.getNodeName());
                //获取属性值
                System.out.println("--属性值" + attr.getNodeValue());
            }
        }
    }

    public static void dfs(NodeList elementList){

        for(int i = 0; i < elementList.getLength(); ++i){

            Node element = elementList.item(i);

            if(element.getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            prin(element);
            if(element.getChildNodes().getLength() != 0){
                dfs(element.getChildNodes());
            }

        }
    }
    public static void main(String[] args) {
        //创建一个DocumentBuilderFactory的对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        File dest = new File("test.xml");
        //创建一个DocumentBuilder的对象
        try {
            //创建DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            //通过DocumentBuilder对象的parser方法加载books.xml文件到当前项目下
            Document document = db.parse("books.xml");
            //获取所有book节点的集合
            NodeList bookList = document.getElementsByTagName("face:DataModel");

            //通过nodelist的getLength()方法可以获取bookList的长度
            //System.out.println("一共有" + bookList.getLength() + "本书");
            //遍历每一个book节点
            dfs(bookList);

            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.transform(new DOMSource(document), new StreamResult(dest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}