package org.example;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class Counter implements Serializable {
    @NonNull
    private Integer id;
    @NonNull
    private Integer count ;
    @NonNull
    private Long startinterval;

}

