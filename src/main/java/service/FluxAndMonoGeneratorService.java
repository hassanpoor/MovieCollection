package service;

import exception.ReactorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import util.CommonUtil;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Function;

@Slf4j
public class FluxAndMonoGeneratorService {

    public Flux<String> nameFLux() {
        return Flux.fromIterable(CommonUtil.createNamesList());
    }

    public Mono<List<String>> nameMono() {
        return Mono.just(CommonUtil.createNamesList());
    }

    public boolean strLengthMoreThan(String s, int length) {
        return s.length() > length;
    }

    public Flux<String> namesAndLength(int length) {
        return nameFLux().map(String::toUpperCase).filter(s -> strLengthMoreThan(s, length))
                .map(s -> s.length() + "-" + s)
                .doOnNext(name -> {
                    System.out.println(name);
                }).doOnSubscribe(s -> System.out.println("subscribt :" + s))
                .doOnComplete(() -> System.out.println("complete")).doFinally(singleType -> System.out.println("finally" + singleType));

    }

    public Flux<String> namesFlux_immutability() {
        var names = nameFLux();
        names.map(String::toUpperCase);
        return names;
    }


    public Mono<List<String>> namesMono_flatMap(int length) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> strLengthMoreThan(s, length))
                .flatMap(this::splitStringMono)
                .log();  //Mono<List of A, L, E  X>

    }

    private static Flux<String> splitString(String s) {
        return Flux.fromArray(s.split(""));
    }

    public Flux<String> namesFlux_flatMapMany(int length) {
        return Mono.just("Alex").map(String::toUpperCase).filter(s -> strLengthMoreThan(s, length))
                .flatMapMany(FluxAndMonoGeneratorService::splitString).log();
    }

    private Mono<List<String>> splitStringMono(String s) {
        return Mono.just(List.of(s.split(""))).log();//Mono<List of A, L, E  X>
    }

    public Flux<String> namesFlux_flatmap(int length) {
//filter the string whose length is greater than 3
        return nameFLux().map(String::toUpperCase).
                filter(s -> strLengthMoreThan(s, length)).flatMap(FluxAndMonoGeneratorService::splitString).log();
    }

    public Flux<String> names_flux_flatMap_async(int length) {
        return nameFLux().filter(s -> strLengthMoreThan(s, length)).flatMapSequential(FluxAndMonoGeneratorService::splitString_withDelay).log();
    }

    public Flux<String> mergeFlux() {
        Flux<String> test1 = Flux.fromArray(new String[]{"test"});
        Flux<String> test2 = Flux.fromArray(new String[]{"test"});
        test1.mergeWith(test2);
        return Flux.merge(test1, test2);
    }

    public Flux<String> namesFlux_concatmap(int stringLength) {
        //filter the string whose length is greater than 3
        return nameFLux()
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .concatMap(s -> splitString_withDelay(s)) // A,L,E,X,C,H,L,O,E
                .log(); // db or a remote service call
    }

    private static Flux<String> splitString_withDelay(String s) {
        Flux<String> stringFlux = Flux.fromArray(s.split(""));
        return stringFlux.delayElements(Duration.ofMillis(1000));
    }

    public Flux<String> namesFlux_transform(int stringLength) {
        //filter the string whose length is greater than 3

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);

        return nameFLux().transform(filterMap).flatMap(FluxAndMonoGeneratorService::splitString).defaultIfEmpty("default").log();

    }

    public Flux<String> namesFlux_transform_switchifEmpty(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap =
                name -> name.map(String::toUpperCase).filter(s -> s.length() > stringLength).flatMap(FluxAndMonoGeneratorService::splitString);
        var def = Flux.just("Default").transform(filterMap);
        return nameFLux().transform(filterMap).filter(x -> x.length() > 7).switchIfEmpty(def).log();
    }

    public Flux<String> explore_concat() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return Flux.concat(abcFlux, defFlux);

    }

    public Flux<String> explore_concatwith() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.concatWith(defFlux);

    }

    public Flux<String> explore_concatwith_mono() {

        var aMono = Mono.just("A");

        var bMono = Mono.just("B");

        return aMono.concatWith(bMono);

    }

    public Flux<String> explore_merge() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100)); //A,B

        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));//D,E

        return Flux.merge(abcFlux, defFlux).log();

    }

    public Flux<String> explore_mergeWith() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100)); //A,B

        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));//D,E

        //return Flux.merge(abcFlux,defFlux).log();
        return abcFlux.mergeWith(defFlux).log();

    }

    public Flux<String> explore_mergeWith_mono() {

        var aMono = Mono.just("A"); //A

        var bMono = Mono.just("B"); //B

        return aMono.mergeWith(bMono).log(); // A, B

    }

    public Flux<String> explore_mergeSequential() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100)); //A,B

        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));//D,E

        return Flux.mergeSequential(abcFlux, defFlux).log();
        // return abcFlux.mergeWith(defFlux).log();

    }

    public Flux<String> explore_zip() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return Flux.zip(abcFlux, defFlux, (first, second) -> first + second)
                .log(); //AD, BE, CF

    }

    public Flux<String> explore_zip_1() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        var _123Flux = Flux.just("1", "2", "3");
        var _456Flux = Flux.just("4", "5", "6");

        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux)
                .map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4())
                .log(); //AD14, BE25, CF36

    }

    public Flux<String> explore_zipWith() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.zipWith(defFlux, (first, second) -> first + second)
                .log(); //AD, BE, CF

    }

    public Mono<String> explore_ZipWith_mono() {

        var aMono = Mono.just("A"); //A

        var bMono = Mono.just("B"); //B

        return aMono.zipWith(bMono)
                .map(t2 -> t2.getT1() + t2.getT2()) //AB
                .log(); // A, B

    }

    public Flux<String> exception_flux() {
        return Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred"))).concatWith(Flux.just("D")).log();
    }

    public Flux<String> explore_OnErrorReturn() {
        return Flux.just("A", "B", "C")
                .concatWith(Flux.error(new IllegalStateException("Exception Occurred")))
                .onErrorReturn("D")
                .log();
    }

    public Flux<String> explore_OnErrorResume(Exception e) {
        var recoveryFlux = Flux.just("D", "E", "F");

        return Flux.just("A", "B", "C")
                .concatWith(Flux.error(e)).onErrorResume(ex -> {
                    log.error("Exception is ", ex);
                    if (ex instanceof IllegalStateException)
                        return recoveryFlux;
                    else
                        return Flux.error(ex);
                }).log();

    }

    public Flux<String> explore_OnErrorContinue() {

        return Flux.just("A", "B", "C")
                .map(name -> {
                    if (name.equals("B"))
                        throw new IllegalStateException("Exception Occurred");
                    return name;
                })
                .concatWith(Flux.just("D"))
                .onErrorContinue((ex, name) -> {
                    log.error("Exception is ", ex);
                    log.info("name is {}", name);
                })
                .log();
    }

    public Flux<String> explore_OnErrorMap(Exception e) {

        return Flux.just("A")
                .concatWith(Flux.error(e))
                .checkpoint("errorSpot")
                .onErrorMap((ex) -> {
                    log.error("Exception is ", ex);
                    return new ReactorException(ex);
                })
                .log();
    }

    public Flux<String> explore_doOnError() {
        return mergeFlux()
                .concatWith(Flux.error(new IllegalStateException("Exception Occurred")))
                .doOnError(ex -> {
                    log.error("Exception is ", ex);
                })
                .log();
    }

    public Mono<Object> explore_Mono_OnErrorReturn() {
        return Mono.just("A")
                .map(value -> {
                    throw new RuntimeException("Exception Occurred");
                })
                .onErrorReturn("abc")
                .log();
    }

    public Flux<Integer> explore_generate() {

        return Flux.generate(
                () -> 1, (state, sink) -> {
                    sink.next(state * 3);
                    // sink.next(state * 3);

                    if (state == 10) {
                        sink.complete();
                    }
                    return state + 1;
                }

        );
    }

    //List.of("alex", "ben", "chloe")
    public static List<String> names() {
        CommonUtil.delay(1000);
        return CommonUtil.createNamesList();
    }

    public Flux<String> explore_create() {
        return Flux.create(sink -> {
          /*  names()
                    .forEach(sink::next);*/
            CompletableFuture
                    .supplyAsync(() -> names())
                    .thenAccept(names -> {
                        names.forEach((name) -> {
                            sink.next(name);
                        });
                    })
                    .thenRun(() -> sendEvents(sink));
        });
    }

    public void sendEvents(FluxSink<String> sink) {

        CompletableFuture
                .supplyAsync(() -> names(), Executors.newSingleThreadExecutor())
                .thenAccept(names -> {
                    names.forEach(sink::next);
                })
                .thenRun(() -> {
                    sink.complete();
                });
    }

    public Flux<String> explore_handle() {

        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .handle((name, sink) -> {
                    if (name.length() > 3) {
                        sink.next(name.toUpperCase());
                    }
                });
    }


    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();


    }

}

