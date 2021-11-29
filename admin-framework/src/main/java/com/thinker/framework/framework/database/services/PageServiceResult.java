package com.thinker.framework.framework.database.services;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PageServiceResult<T> {
    List<T> items;
    int total;
}
