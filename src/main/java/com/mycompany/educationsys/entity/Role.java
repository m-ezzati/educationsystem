package com.mycompany.educationsys.entity;

import com.mycompany.educationsys.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Role extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String roleName;

    public Role(){}

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
