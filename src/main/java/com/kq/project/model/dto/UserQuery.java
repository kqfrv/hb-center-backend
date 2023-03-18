package com.kq.project.model.dto;

import com.kq.project.common.PageRequest;
import lombok.Data;

import java.util.List;

@Data
public class UserQuery extends PageRequest {
    String searchText;
    List<Long> ids;
}
