package cn.zrf.shirodemo.model;

import java.io.Serializable;

public class Permission implements Serializable {


    private static final long serialVersionUID = -7459027383997110132L;
    private Integer id;

    //权限代码
    private String pname;

    private String url;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", pname='" + pname + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
