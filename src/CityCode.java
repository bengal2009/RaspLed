/**
 * Created by Lin on 2015/6/26.
 */

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Logger;


public class CityCode
{

    private Logger log = Logger.getLogger(CityCode.class.getName());

    public static void main(String[] args) {
        try {
            CityCode cc = new CityCode();
            Document doc = cc.getCityXml();
            System.out.println(doc.asXML());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private Document getCityXml() throws Exception {
        /** 新建一?doc 保存?果 */
        Document docResult = DocumentHelper.createDocument();
        Element addElementRoot = DocumentHelper.createElement("china");

        String sUrlChina = "http://flash.weather.com.cn/wmaps/xml/china.xml";
        String sChinaProvXml = getUrlString(sUrlChina);
        Document doc = DocumentHelper.parseText(sChinaProvXml);

        Element rootElt = doc.getRootElement(); // ?取根??
        log.info("根??：" + rootElt.getName()); // 拿到根??的名?
        /** 取得所有省份 */
        List<Element> listProv = rootElt.elements("city"); // ?取根??下的子??
        for (int i = 0; i < listProv.size(); i++) {
            Element elementProv = listProv.get(i);
            /** 省份名字 */
            String sProvName = elementProv.attributeValue("pyName");
            String sProvNameCN = elementProv.attributeValue("quName");
            /** 添加?? */
            Element addElementProv = DocumentHelper.createElement("prov");
            addElementProv.addAttribute("pyName", sProvName);
            addElementProv.addAttribute("quName", sProvNameCN);

            try {
                log.info("查?省份:" + sProvNameCN + " 下的城市!");
                String sUrlProv = "http://flash.weather.com.cn/wmaps/xml/" + sProvName + ".xml";
                String sProvXml = getUrlString(sUrlProv);
                Document docProv = DocumentHelper.parseText(sProvXml);

                Element rootEltProv = docProv.getRootElement(); // ?取省份下的跟??
                List<Element> listCity1 = rootEltProv.elements("city");
                for (int j = 0; j < listCity1.size(); j++) {
                    Element elementCity1 = listCity1.get(j);
                    String sCityName1 = elementCity1.attributeValue("pyName");
                    String sCityUrl1 = elementCity1.attributeValue("url");
                    String sCityNameCN1 = elementCity1.attributeValue("cityname");
                    System.out.println(sCityNameCN1 + ":" + sCityUrl1);

                    Element addElementMainCity = DocumentHelper.createElement("city");
                    addElementMainCity.addAttribute("pyName", sCityName1);
                    addElementMainCity.addAttribute("quName", sCityNameCN1);
                    addElementMainCity.addAttribute("url", sCityUrl1);

                    try {
                        String sUrlCity1 = "http://flash.weather.com.cn/wmaps/xml/" + sCityName1 + ".xml";
                        log.info("查?主要城市:" + sCityNameCN1 + " 下的城市!");
                        String sCityXml = getUrlString(sUrlCity1);
                        Document docCity1 = DocumentHelper.parseText(sCityXml);
                        Element rootCity1 = docCity1.getRootElement();
                        List<Element> listCity2 = rootCity1.elements("city");

                        for (int k = 0; k < listCity2.size(); k++) {
                            Element elementCity2 = listCity2.get(k);
                            String sCityName2 = elementCity2.attributeValue("pyName");
                            String sCityNameCN2 = elementCity2.attributeValue("cityname");
                            String sCityUrl2 = elementCity2.attributeValue("url");
                            System.out.println(sCityNameCN2 + ":" + sCityUrl2);

                            Element addElementCity2 = DocumentHelper.createElement("city2");
                            addElementCity2.addAttribute("pyName", sCityName2);
                            addElementCity2.addAttribute("quName", sCityNameCN2);
                            addElementCity2.addAttribute("url", sCityUrl2);
                            addElementMainCity.add(addElementCity2);
                        }

                    } catch (Exception e) {
                        log.info("查?主要城市:" + sCityNameCN1 + " 下的城市失?!");
                    }
                    addElementProv.add(addElementMainCity);
                }
            } catch (Exception e) {
                log.info("查?省份:" + sProvNameCN + "下的城市失?!");
            }
            addElementRoot.add(addElementProv);
        }
        docResult.setRootElement(addElementRoot);
        return docResult;
    }

    /**
     * ?url?取xml字符串
     *
     * @param sUrl
     * @return
     * @throws Exception
     */
    private String getUrlString(String sUrl) throws Exception {
        String sResult = "";
        StringBuffer sbResult = null;
        log.info("?始?接Url:" + sUrl);
        sbResult = new StringBuffer();
        URL url = new URL(sUrl);
        URLConnection con = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String sLine = null;
        while ((sLine = br.readLine()) != null) {
            sbResult.append(sLine);
        }
        sResult = sbResult.toString();
        log.info("?取到信息:" + sbResult.toString().substring(0, 500));
        if (sResult.contains("html")) {
            String sInt = "null";
            Integer.parseInt(sInt);
        }
        return sResult;
    }
}
