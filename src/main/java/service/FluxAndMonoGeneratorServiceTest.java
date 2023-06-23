package service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService fluxAndMonoGeneratorService =
            new FluxAndMonoGeneratorService();

    // return List.of("alex", "ben", "chloe", "adam", "adam");
    @Test
    void namesAndLength() {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
        StepVerifier.create(service.namesAndLength(3))
                .expectNext("4-ALEX").expectNext("5-CHLOE").expectNext("4-ADAM").expectNext("4-ADAM").verifyComplete();
    }

    @Test
    void namesFlux_immutability() {
    }

    @Test
    void namesMono_flatMap() {
    }

    @Test
    void namesFlux_flatMapMany() {
    }

    @Test
    void namesFlux_flatmap() {
    }

    @Test
    void names_flux_flatMap_async() {
    }

    @Test
    void mergeFlux() {
    }

    @Test
    void namesFlux_concatmap() {
    }

    @Test
    void namesFlux_transform() {
    }

    @Test
    void namesFlux_transform_switchifEmpty() {
    }

    @Test
    void explore_concat() {
    }

    @Test
    void explore_concatwith() {
    }

    @Test
    void explore_concatwith_mono() {
    }

    @Test
    void explore_merge() {
    }

    @Test
    void explore_mergeWith() {
    }

    @Test
    void explore_mergeWith_mono() {
    }

    @Test
    void explore_mergeSequential() {
    }

    @Test
    void explore_zip() {
    }

    @Test
    void explore_zip_1() {
    }

    @Test
    void explore_zipWith() {
    }

    @Test
    void explore_ZipWith_mono() {
    }

    @Test
    void exception_flux() {
    }

    @Test
    void explore_OnErrorReturn() {
    }

    @Test
    void explore_OnErrorResume() {
    }

    @Test
    void explore_OnErrorContinue() {
    }

    @Test
    void explore_OnErrorMap() {
    }

    @Test
    void explore_doOnError() {
    }

    @Test
    void explore_Mono_OnErrorReturn() {
    }

    @Test
    void explore_generate() {
    }

    @Test
    void names() {
    }

    @Test
    void explore_create() {
    }

    @Test
    void sendEvents() {
    }

    @Test
    void explore_handle() {
    }
}