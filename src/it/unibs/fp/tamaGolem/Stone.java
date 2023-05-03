package it.unibs.fp.tamaGolem;

public class Stone {
    private Element elementType;

    public Stone(Element elementType) {
        this.elementType = elementType;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "elementType='" + elementType + '\'' +
                '}';
    }
}
