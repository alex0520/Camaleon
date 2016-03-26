/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.entities;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import javax.swing.AbstractListModel;

public class TreeSetListModel<T extends Comparable<T>> extends AbstractListModel<T> {

    private static final long serialVersionUID = 1L;
    private TreeSet<T> treeSet;

    public TreeSetListModel() {
        treeSet = new TreeSet<T>();
    }

    public TreeSetListModel(Comparator<? super T> comparator) {
        treeSet = new TreeSet<T>(comparator);
    }

    @Override
    public T getElementAt(int index) {
        if (index < 0 || index >= getSize()) {
            String s = "index, " + index + ", is out of bounds for getSize() = "
                    + getSize();
            throw new IllegalArgumentException(s);
        }
        Iterator<T> iterator = treeSet.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            T t = (T) iterator.next();
            if (index == count) {
                return t;
            }
            count++;
        }
        // out of index. return null. will probably never reach this
        return null;
    }

    @Override
    public int getSize() {
        return treeSet.size();
    }

    public int getIndexOf(T t) {
        int index = 0;
        for (T treeItem : treeSet) {
            if (treeItem.equals(treeItem)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public boolean add(T t) {
        boolean result = treeSet.add(t);
        if (result) {
            int index = getIndexOf(t);
            fireIntervalAdded(this, index, index + 1);
        }
        return result;
    }

    public boolean remove(T t) {
        int index = getIndexOf(t);
        if (index < 0) {
            return false;
        }
        boolean result = treeSet.remove(t);
        fireIntervalRemoved(this, index, index + 1);
        return result;
    }

    public void clear() {
        int size = treeSet.size();
        treeSet.clear();
        fireIntervalRemoved(this, 0, ((size-1)<0)?0:(size-1));
    }
    
    public HashSet<T> getAllElements(){
        HashSet<T> result = new HashSet<>(treeSet);
        return result;
    }

}
