package com.onboarding.reactive.akkastream;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.pattern.Patterns;
import com.onboarding.reactive.common.Clazz;
import scala.concurrent.Future;

public class ClazzActor extends AbstractActor {

    static Props props() {
        return Props.create(ClazzActor.class, ClazzActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(ClazzMessage.GetStudent.class, getStudent -> {
                Clazz clazz = Clazz.builder().id(getStudent.getId()).build();
                Patterns.pipe(Future.successful(clazz), getContext().dispatcher()).to(sender());
            })
            .build();
    }
}
