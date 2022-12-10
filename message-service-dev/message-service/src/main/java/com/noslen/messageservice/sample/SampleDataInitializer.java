//package com.noslen.messageservice.sample;
//
//import com.noslen.messageservice.model.Msg;
//import com.noslen.messageservice.repository.MsgRepo;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Flux;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.UUID;
//
//@Log4j2
//@Component
//@org.springframework.context.annotation.Profile("demo")
//class SampleDataInitializer implements ApplicationListener<ApplicationReadyEvent> {
//
//    private final MsgRepo repository;
//
//    public SampleDataInitializer(MsgRepo repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        final String time = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date());
//        repository.deleteAll().thenMany(
//                Flux.just("Jim", "George", "Jen", "Susan")
//                        .map(name -> new Msg(
//                                UUID.randomUUID().toString(),
//                                name,
//                                "Hello",
//                                 time))
//                        .flatMap(repository::save))
//                .thenMany(repository.findAll())
//                .subscribe(message -> log.info("saving " + message.toString()));
//    }
//}
