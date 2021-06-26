public enum Goods {

    SAMSUNG ("Смартфон Samsung"),
    XIAOMI ("Смартфон Xiaomi");

    private String title;

    Goods(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

}
