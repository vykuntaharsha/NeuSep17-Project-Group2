package com.neuSep17.ui.inventoryList.CenterSection;

import java.util.ArrayList;
import java.util.List;

public class PageController<T> {
    private List<T> bigList=new ArrayList<>(); //大集合，从外界获取
    private List<T> smallList=new ArrayList<>(); //小集合，返回给调用它的类
    private int curentPageIndex=1;        //当前页码
    private int countPerpage;        //每页显示条数
    private int pageCount;           //总页数
    private int recordCount;           //总记录条数

    public  PageController(List<T> list, int countPerpage) {
        this.bigList = list;
        this.countPerpage = countPerpage;
        //计算总页数
        if (bigList.size()%countPerpage==0) {
            this.pageCount=bigList.size()/countPerpage;
        } else {
            this.pageCount=(bigList.size()/countPerpage)+1;
        }
        recordCount=bigList.size();
        select();
    }

    public List<T> getBigList() {
        return bigList;
    }

    public List<T> getSmallList() {
        return smallList;
    }

    //确切的获取当前页的记录，返回一个list列表
    public List<T> setFirstPage() {
        curentPageIndex = 1;
        return select();
    }

    public void setCurentPageIndex(int curentPageIndex) {
        this.curentPageIndex = curentPageIndex;
    }

    public List<T> setLastPage() {
        curentPageIndex = pageCount;
        return select();
    }

    public List<T> shiftRightPage() {

        if (curentPageIndex < pageCount-9 ) {
            curentPageIndex += 10;
        }
        else curentPageIndex = pageCount;
        return select();
    }
    //下一页
    public List<T> nextPage() {

        if (curentPageIndex < pageCount ) {
            curentPageIndex++;
        }
        else{
            curentPageIndex = pageCount;
        }
        return select();
    }
    //上一页
    public List<T> previousPage() {
        if (curentPageIndex > 1) {
            curentPageIndex--;
        }
        else{
            curentPageIndex = 1;
        }
        return select();
    }
    public List<T> shiftLeftPage() {
        if (curentPageIndex > 10) {
            curentPageIndex-=10;
        }
        else{
            curentPageIndex = 1;
        }
        return select();
    }
    //此方法供以上方法调用，根据当前页，筛选记录
    public List<T> select(){
        smallList.clear();
        for(int i=(curentPageIndex-1)*countPerpage; i<curentPageIndex*countPerpage&&i<recordCount; i++){
            smallList.add(bigList.get(i));
        }

        return smallList;
    }

    public List<T> jumpPage(int targetPageIndex) {
        if (targetPageIndex<=pageCount&&targetPageIndex>=1) {
            curentPageIndex = targetPageIndex;
        }
        else if(targetPageIndex>pageCount) curentPageIndex = pageCount;
        else if(targetPageIndex<1) curentPageIndex = 1;
        return select();
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getCurentPageIndex() {
        return curentPageIndex;
    }

    public int getCountPerpage() {
        return countPerpage;
    }
}

