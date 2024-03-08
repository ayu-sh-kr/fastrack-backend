package dev.arhimedes.global.service.contract;

public interface Converter<S, T>{

    T convert(S s, T t);

    S reverseConvert(T t, S s);
}
