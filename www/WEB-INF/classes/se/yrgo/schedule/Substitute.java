package se.yrgo.schedule;

public class Substitute {
    private String name;

    public Substitute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Substitute{" +
                "name='" + name + '\'' +
                '}';
    }
}
