package com.onboarding.reactive.common;

public interface ElementListener<T> {

    void onNext(T data);

    void onComplete();

}
