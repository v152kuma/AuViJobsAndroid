package com.auvi.sortutil;

import com.auvi.entity.base.CSObject;

import java.util.Comparator;

abstract public class CSSort implements Comparator<CSObject> {

    public int compare(CSObject csOne, CSObject csAnother){
        int returnVal = 0;
        int firstObjectCount = getCount(csOne);
        int secObjectCount = getCount(csAnother);

    if(firstObjectCount< secObjectCount){
        returnVal =  +1;
    }else if(firstObjectCount > secObjectCount){
        returnVal =  -1;
    }else if(firstObjectCount == secObjectCount){
        returnVal =  0;
    }
    return returnVal;
    }

    abstract public int getCount(CSObject csObject);
}