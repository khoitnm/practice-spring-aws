package org.tnmk.practicespringaws.pro04sqsbatchpubisher.aws.sqs.model;

public class Child {
    private Integer id;
    private String value;

    @Override
    public String toString() {
        return "Child{" +
            "id=" + id +
            ", value='" + value + '\'' +
            '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
