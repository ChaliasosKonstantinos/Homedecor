package gr.homedeco.www.homedeco.helpers;

public class ValContainer<T> {

    private T val;

    public ValContainer(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }
}
