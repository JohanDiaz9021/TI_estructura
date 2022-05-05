package model.objects;

public class Country {
    String name;
    double init;
    double end;

    public Country(String name, double init, double end) {
        this.name = name;
        this.init = init;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public double getInit() {
        return init;
    }

    public double getEnd() {
        return end;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInit(double init) {
        this.init = init;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}
