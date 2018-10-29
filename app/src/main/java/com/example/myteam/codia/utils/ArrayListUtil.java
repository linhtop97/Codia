package com.example.myteam.codia.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayListUtil<T> {

    public List<ArrayList<T>> divideList(List<T> list, int itemsLoadMore) {
        int totalSize = list.size();
        int numMember = totalSize / itemsLoadMore;
        int x = totalSize % itemsLoadMore;
        int size = (x == 0 ? numMember : numMember + 1);
        List<ArrayList<T>> listContain = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            listContain.add(new ArrayList<T>());
        }

        int j = 0;
        int currentItem = itemsLoadMore;
        for (int i = 0; i < totalSize; i++) {
            listContain.get(j).add(list.get(i));
            if (i == currentItem - 1) {
                j++;
                currentItem += itemsLoadMore;
            }
        }
        return listContain;
    }
}
