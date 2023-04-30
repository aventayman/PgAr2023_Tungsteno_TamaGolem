package it.unibs.fp.tamaGolem;

public class Stone {
    private String elementType;

    public Stone(String elementType) {
        this.elementType = elementType;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "elementType='" + elementType + '\'' +
                '}';
    }
}
