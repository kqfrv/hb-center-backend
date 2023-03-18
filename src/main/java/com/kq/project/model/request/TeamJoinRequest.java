package com.kq.project.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamJoinRequest implements Serializable {


    private static final long serialVersionUID = -924859512879930972L;
    private Long teamId;

    private String password;
}
