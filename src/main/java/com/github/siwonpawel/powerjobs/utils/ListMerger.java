package com.github.siwonpawel.powerjobs.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@UtilityClass
public class ListMerger {

    public static <T> List<T> merge(Collection<T> colA, Collection<T> colB) {
        List<T> merge = new ArrayList<>(colA);
        merge.addAll(colB);

        return merge;
    }
}
