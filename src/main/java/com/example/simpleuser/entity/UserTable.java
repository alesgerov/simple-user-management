package com.example.simpleuser.entity;


import com.example.simpleuser.entity.utils.RoleClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Transactional
public class UserTable {

    @Id
    @SequenceGenerator(
            name = "usertab_id_seq",
            sequenceName = "usertab_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usertab_id_seq"
    )
    private long id;
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String role = RoleClass.USER_ROLE;
}
