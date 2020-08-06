package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Modle implements Serializable {
    @Id
    private Integer id;

    private String modlename;

    private Date createtime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return modlename
     */
    public String getModlename() {
        return modlename;
    }

    /**
     * @param modlename
     */
    public void setModlename(String modlename) {
        this.modlename = modlename == null ? null : modlename.trim();
    }

    /**
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", modlename=").append(modlename);
        sb.append(", createtime=").append(createtime);
        sb.append("]");
        return sb.toString();
    }
}