package au.com.goed.starter.demo.stuff;

import javax.validation.constraints.NotNull;

public class Stuff {

    @NotNull
    private String val;

    public Stuff(String val) {
        this.val = val;
    }
    
    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
    
    
}
