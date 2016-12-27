package com.myself.myapplication;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by sw on 2016/12/27.
 */
public class SaxItem extends DefaultHandler {

    private ArrayList<NewsBean> billItems;
    private String preTag;
    private NewsBean billItem;

    @Override
    public void startDocument() throws SAXException {

        billItems= new ArrayList<NewsBean>();
    }
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
            String data = new String(ch, start, length);
if (billItem!=null){
    if ("title".equals(preTag)) {
        billItem.setTittle(data);
    }
    if ("link".equals(preTag)) {
        billItem.setLink(data);
    }
    if ("content".equals(preTag)) {
        billItem.setContent(data);
    }
}

    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attr) throws SAXException {
        if ("item".equals(name)) {
            //遇到BillItem节点，new一个对象
            billItem = new NewsBean();
        }
        preTag = name;

    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {

        if (billItem != null && "item".equals(name)) {
            //子节点下面的billItem单条数据加入billItems列表中
            billItems.add(billItem);
            billItem = null;
        }
        preTag = null;
    }


    public ArrayList<NewsBean> getBillItems() {
        return billItems;
    }

    public void setBillItems(ArrayList<NewsBean> billItems) {
        this.billItems = billItems;
    }

    public String getPreTag() {
        return preTag;
    }

    public void setPreTag(String preTag) {
        this.preTag = preTag;
    }

    public NewsBean getBillItem() {
        return billItem;
    }

    public void setBillItem(NewsBean billItem) {
        this.billItem = billItem;
    }

}