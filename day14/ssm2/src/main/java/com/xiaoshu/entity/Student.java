package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

public class Student implements Serializable {
    @Id
    private Integer id;

    private String name;

    private Integer age;

    private String gender;

    private Integer choolid;

    private String code;

    private String grade;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date entrytime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private Integer teacherid;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * @return choolid
     */
    public Integer getChoolid() {
        return choolid;
    }

    /**
     * @param choolid
     */
    public void setChoolid(Integer choolid) {
        this.choolid = choolid;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @param grade
     */
    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    /**
     * @return entrytime
     */
    public Date getEntrytime() {
        return entrytime;
    }

    /**
     * @param entrytime
     */
    public void setEntrytime(Date entrytime) {
        this.entrytime = entrytime;
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

    /**
     * @return teacherid
     */
    public Integer getTeacherid() {
        return teacherid;
    }

    /**
     * @param teacherid
     */
    public void setTeacherid(Integer teacherid) {
        this.teacherid = teacherid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", age=").append(age);
        sb.append(", gender=").append(gender);
        sb.append(", choolid=").append(choolid);
        sb.append(", code=").append(code);
        sb.append(", grade=").append(grade);
        sb.append(", entrytime=").append(entrytime);
        sb.append(", createtime=").append(createtime);
        sb.append(", teacherid=").append(teacherid);
        sb.append("]");
        return sb.toString();
    }
}