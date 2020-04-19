package demosax3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
Задание: Написать класс DemoSAX, который на основе SAX разбирает файл sapmle.xml
 */
public class DemoSax3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fileName = "sample.xml";

        /*
public abstract class SAXParserFactory extends Object
 
        Определяет фабричный API, который позволяет приложениям настраивать 
 и получать синтаксический анализатор на основе SAX для анализа XML-документов. 

        static SAXParserFactory	newInstance() - получение нового экземпляра SAXParserFactory.
         */
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
        SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(DemoSax3.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        // создание переменных для всех встречающихся тегов
        DefaultHandler handler = new DefaultHandler() {
            boolean bUser = false;
            boolean bFirstName = false;
            boolean bFamily = false;
            boolean bDepartment = false;
            /*
           public interface Attributes
            Интерфейс для списка атрибутов XML.
*/
            Attributes attrList;

          
            // переопределение startElement класса DefaultHandler - определет какой мы тег встретили, 
            // определяет, когда встречается любой тег
            @Override
            public void startElement(String url, String lName, String qName, Attributes attr) throws SAXException {
                attrList = attr;
                // выяснение встречи конкретного тега:
                if (qName.equalsIgnoreCase("user")) {
                    bUser = true;
                }
                if (qName.equalsIgnoreCase("first-name")) {
                    bFirstName = true;
                }
                if (qName.equalsIgnoreCase("family")) {
                    bFamily = true;
                }
                if (qName.equalsIgnoreCase("department")) {
                    bDepartment = true;
                }
            }

          
            // переопределение метода класса DefaultHandler, позволяющего получить содержимое метода
            @Override
            public void characters(char ch[], int start, int length)
                    throws SAXException {
                if (bFirstName) {
                    System.out.print("First-name: " + new String(ch, start, length));
                    bFirstName = false;
                } else if (bFamily) {
                    System.out.print("Family: " + new String(ch, start, length));
                    bFamily = false;
                } else if (bDepartment) {
                    System.out.print("Department: " + new String(ch, start, length));
                    bDepartment = false;
                } else if (bUser) {
                    System.out.print("User: " + new String(ch, start, length));
                    bUser = false;
                } else {
                    return;
                }
                // вывод 
                String allAttr = listAttr();
                System.out.println(" Атрибуты: " + allAttr);

            }

          
            private String listAttr() {
                StringBuilder sb = new StringBuilder();

                int n = attrList.getLength();
                for (int i = 0; i < n; i++) {
                    String name = attrList.getQName(i);
                    String value = attrList.getValue(i);
                    sb.append(name + "= " + value + "; ");
                }
                return sb.toString();
            }
        }; // handler

        try {
            saxParser.parse(fileName, handler);
        } catch (SAXException | IOException ex) {
            Logger.getLogger(DemoSax3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
