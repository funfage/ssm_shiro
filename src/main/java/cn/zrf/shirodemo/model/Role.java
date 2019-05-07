package cn.zrf.shirodemo.model;

import java.io.Serializable;
import java.util.Set;

public class Role implements Serializable {


    private static final long serialVersionUID = 3117537754848156203L;
    private Integer id;

    private String rname;

    private String rcode;

    private Set<Permission> permissions;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rname='" + rname + '\'' +
                ", rcode='" + rcode + '\'' +
                ", permissions=" + permissions +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRcode() {
        return rcode;
    }

    public void setRcode(String rcode) {
        this.rcode = rcode;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
