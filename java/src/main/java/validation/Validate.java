package validation;

import java.util.LinkedList;
import java.util.List;

public class Validate{
    private final List<Integer> xRange = new LinkedList<>();
    private final List<Integer> rRange = new LinkedList<>();
    private String log = "all ok";
    public Validate(){
        xRange.add(-5);
        xRange.add(-4);
        xRange.add(-3);
        xRange.add(-2);
        xRange.add(-1);
        xRange.add(0);
        xRange.add(1);
        xRange.add(2);
        xRange.add(3);

        rRange.add(1);
        rRange.add(2);
        rRange.add(3);
        rRange.add(4);
        rRange.add(5);
    }
    public boolean check(Integer x, Float y, Integer r){
        return checkX(x) && checkY(y) && checkR(r);
    }

    public String getErr(){
        return log;
    }

    public boolean checkX(int x){
        if (xRange.contains(x)){
            return true;
        }
        log = "X must be selected";
        return false;
    }

    public boolean checkY(Float y){
        if (-3 <= y && y <= 5){
            return true;
        }
        log = "Y value must be -3<=y<=5";
        return false;
    }

    public boolean checkR(int r){
        if (rRange.contains(r)){
            return true;
        }
        log = "R must be selected";
        return false;
    }
}