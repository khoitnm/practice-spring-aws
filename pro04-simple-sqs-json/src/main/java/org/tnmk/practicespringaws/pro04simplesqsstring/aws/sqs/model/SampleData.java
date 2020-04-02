package org.tnmk.practicespringaws.pro04simplesqsstring.aws.sqs.model;

import java.util.List;
import java.util.Map;

public class SampleData {
    private String value;

    private List<Child> children;

    private Map<String, Child> childrenMap;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public Map<String, Child> getChildrenMap() {
        return childrenMap;
    }

    public void setChildrenMap(Map<String, Child> childrenMap) {
        this.childrenMap = childrenMap;
    }
}
