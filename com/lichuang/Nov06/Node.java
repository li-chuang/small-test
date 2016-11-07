package com.lichuang.Nov06;

import java.util.List;

public class Node {
	private Long id;
    private String name;
    private String text;
    private List<Attribute> attributeList;
    private List<Node> nodeList;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public List<Attribute> getAttributeList() {
        return attributeList;
    }
    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
    public List<Node> getNodeList() {
        return nodeList;
    }
    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

}
