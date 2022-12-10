package loader;

public interface Loader<T> {
    public T load(T model);
}
