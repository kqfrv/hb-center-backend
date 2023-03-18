package com.kq.project.once;

import com.alibaba.excel.EasyExcel;

import java.util.List;

public class ImportExcel {

    public static void main(String[] args) {

    }

    public  static  void readByListener(String fileName){
        EasyExcel.read(fileName, XingQiuUserInfo.class,new TableListener()).sheet().doRead();
    }
    public  static  void synchronousRead(String fileName){

        List<XingQiuUserInfo> totalDataList =
                EasyExcel.read(fileName).head(XingQiuUserInfo.class).sheet().doReadSync();
        for (XingQiuUserInfo xingQiuUserInfo : totalDataList) {
            System.out.println(xingQiuUserInfo);
        }
    }

}
