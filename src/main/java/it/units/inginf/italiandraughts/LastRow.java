package it.units.inginf.italiandraughts;

public enum LastRow {
    WHITE("8"),
    BLACK("1");

    private final String coordinate;

    LastRow(String coordinate){
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return coordinate;
    }

}
