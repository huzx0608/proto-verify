package io.code47.mokito;

public class First {
    Second second ;

    public First(){
        second = new Second();
    }

    public String doSecond(){
        return second.doSecond();
    }

    public void setSecond(Second second) {
        this.second = second;
    }


}

class Second {
    public String doSecond(){
        return "Do Something";
    }
}
