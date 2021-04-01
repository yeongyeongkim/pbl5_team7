package pt.up.fe.ssin.pexplorer.utils;

public class Pair<T, U> {

    private T first;
    private U second;

    public Pair() {
    }

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public U getSecond() {
        return second;
    }

    public void setSecond(U second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        Pair<?, ?> pair = (Pair<?, ?>) obj;
        return first.equals(pair.getFirst()) && second.equals(pair.getSecond());
    }

    @Override
    public String toString() {
        return "[" + first + ", " + second + "]";
    }
}
