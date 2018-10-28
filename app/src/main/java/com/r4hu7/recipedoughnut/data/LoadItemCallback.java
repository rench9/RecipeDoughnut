package com.r4hu7.recipedoughnut.data;

public interface LoadItemCallback<T> {
    void onLoading();

    void onItemLoaded(T t);

    void onDataNotAvailable(Throwable e);
}