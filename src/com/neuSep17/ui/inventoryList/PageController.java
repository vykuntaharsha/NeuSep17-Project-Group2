

import com.neuSep17.dto.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class PageController {
    private List<Vehicle> bigList=new ArrayList<Vehicle>(); //大集合，从外界获取
    private List<Vehicle> smallList=new ArrayList<Vehicle>(); //小集合，返回给调用它的类
    private static int curentPageIndex=1;        //当前页码
    private int countPerpage=10;        //每页显示条数
    private int pageCount;           //总页数
    private int recordCount;           //总记录条数

    public  PageController(List<Vehicle> list) {
        this.bigList = list;
        //计算总页数
        if (bigList.size()%countPerpage==0) {
            this.pageCount=bigList.size()/countPerpage;
        } else {
            this.pageCount=(bigList.size()/countPerpage)+1;
        }
        recordCount=bigList.size();
    }

    public List<Vehicle> getSmallList() {
        return smallList;
    }

    //    //传入指定页码的构造函数，参看第几页。
//    public PageController(int curentPageIndex){
//        this.curentPageIndex=curentPageIndex;
//    }

    //确切的获取当前页的记录，返回一个list列表
    public List<Vehicle> setFirstPage() {
        curentPageIndex = 1;
        return select();
    }

    public List<Vehicle> setLastPage() {
        curentPageIndex = pageCount;
        return select();
    }

    public List<Vehicle> shiftRightPage() {

        if (curentPageIndex < pageCount-9 ) {
            curentPageIndex += 10;
        }
        else curentPageIndex = pageCount;
        return select();
    }
    //下一页
    public List<Vehicle> nextPage() {

        if (curentPageIndex < pageCount ) {
            curentPageIndex++;
        }
        return select();
    }
    //上一页
    public List<Vehicle> previousPage() {
        if (curentPageIndex > 1) {
            curentPageIndex--;
        }

        return select();
    }
    public List<Vehicle> shiftLeftPage() {
        if (curentPageIndex > 10) {
            curentPageIndex-=10;
        }
        else{
            curentPageIndex = 1;
        }
        return select();
    }
    //此方法供以上方法调用，根据当前页，筛选记录
    public List<Vehicle> select(){
        smallList.clear();
        for(int i=(curentPageIndex-1)*countPerpage; i<curentPageIndex*countPerpage&&i<recordCount; i++){
            smallList.add(bigList.get(i));
        }

        return smallList;
    }

    public List<Vehicle> jumpPage(int targetPageIndex) {
        if (targetPageIndex<=pageCount&&targetPageIndex>=1) {
            curentPageIndex = targetPageIndex;
        }
        return select();
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getCurentPageIndex() {
        return curentPageIndex;
    }
}

