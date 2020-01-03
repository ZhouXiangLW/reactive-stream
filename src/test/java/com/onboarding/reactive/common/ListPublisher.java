package com.onboarding.reactive.common;

import java.util.List;

public class ListPublisher<T> {


    private List<T> list;

    public ListPublisher(List<T> list) {
        this.list = list;
    }

    public void publish(ElementListener<T> listener) {
        list.forEach(listener::onNext);
        listener.onComplete();
    }

}
