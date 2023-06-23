package java8;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class compatibleFutureTest {

    @Test
    @Ignore
    public void compatibleFutureTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.complete("hello");
        Assert.assertEquals(completableFuture.get(), "hello");
    }


    @Test
    @Ignore
    public void compatibleFutureTest2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> "Hello");
        Assert.assertEquals(future.get(), "Hello");
    }

    @Test
    @Ignore
    public void compatibleFutureTest3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future = completableFuture
                .thenApply(s -> s + " World");

        Assert.assertEquals("Hello World", future.get());
    }

    @Test
    @Ignore
    public void compatibleFutureTest4() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Void> future = completableFuture
                .thenAccept(s -> System.out.println("Computation returned: " + s));

        future.get();
    }

    @Test
    @Ignore
    public void compatibleFutureTest5() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Void> future = completableFuture
                .thenRun(() -> System.out.println("Computation finished."));
        future.get();
    }

    @Test
    @Ignore
    public void compatibleFutureTest6() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));

        Assert.assertEquals("Hello World", completableFuture.get());
    }

    @Test
    @Ignore
    public void compatibleFutureTest7() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (a, b) -> a + b);

        Assert.assertEquals("Hello World", completableFuture.get());
    }
}
