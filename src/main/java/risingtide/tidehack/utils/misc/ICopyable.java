// fuck you rat go brrrrrrrr
package risingtide.tidehack.utils.misc;

public interface ICopyable<T extends ICopyable<T>> {
    T set(T value);

    T copy();
}
