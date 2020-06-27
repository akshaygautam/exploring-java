package java8InAction.chapter11;

import static java.lang.System.nanoTime;
import static java.lang.System.out;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FutureDemo {
    private class Shop {
        String name;
        Double price;

        Shop(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Double getPrice(final String product) {
            return calculatePrice(product);
        }
    }

    private final Random rand = new Random();
    static Logger log = Logger.getLogger("log");
    final List<Shop> shops = Arrays.asList(new Shop("A"), new Shop("B"), new Shop("C"), new Shop("D"), new Shop("E"),
            new Shop("F"), new Shop("G"), new Shop("H"));

    public static void main(final String[] args) {
        final FutureDemo demo = new FutureDemo();
        final String LOOKED_FOR_PRICE = "looked for price: ";
        final String MOBILE = "MOBILE";
        final String LOOKIN_IN_FUTURE = "looking for price in future: ";
        final String RETURN_AFTER = "Returned after: ";
        final String SECS = " SECs";
        final int MAKE_NANO_TO_SECONDS = 1_000_000_000;
        out.println("Looking for price");
        out.println(LOOKED_FOR_PRICE + demo.getPrice(MOBILE));

        out.println("=========Future==========");
        out.println(LOOKIN_IN_FUTURE + 1);
        long start = nanoTime();
        Future<Double> future = demo.getPriceInFuture(MOBILE);
        long invocationTime = ((nanoTime() - start) / MAKE_NANO_TO_SECONDS);
        out.println("future is back after: " + invocationTime + SECS);
        try {
            out.println(LOOKED_FOR_PRICE + future.get(2000L, TimeUnit.SECONDS));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        long retreivalTime = ((nanoTime() - start) / MAKE_NANO_TO_SECONDS);
        out.println(RETURN_AFTER + retreivalTime + SECS);

        out.println("=========Future with supply Async==========");
        start = nanoTime();
        out.println(LOOKIN_IN_FUTURE + 2);
        future = demo.getPriceWithSupplyAsync(MOBILE);
        invocationTime = ((nanoTime() - start) / MAKE_NANO_TO_SECONDS);
        out.println("future is back after: " + invocationTime);
        try {
            out.println(LOOKED_FOR_PRICE + future.get(2000L, TimeUnit.SECONDS));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        retreivalTime = ((nanoTime() - start) / MAKE_NANO_TO_SECONDS);
        out.println(RETURN_AFTER + retreivalTime + SECS);

        out.println("=========Pricess with stream==========");
        start = nanoTime();
        out.println(LOOKED_FOR_PRICE + demo.findPrices(MOBILE));
        retreivalTime = ((nanoTime() - start) / MAKE_NANO_TO_SECONDS);
        out.println(RETURN_AFTER + retreivalTime + SECS);

        out.println("=========Pricess with parallel stream==========");
        start = nanoTime();
        out.println(LOOKED_FOR_PRICE + demo.findPricesPrallel(MOBILE));
        retreivalTime = ((nanoTime() - start) / MAKE_NANO_TO_SECONDS);
        out.println(RETURN_AFTER + retreivalTime + SECS);

        out.println("=========Pricess with stream future==========");
        start = nanoTime();
        out.println(LOOKED_FOR_PRICE + demo.findPricesFurture(MOBILE));
        retreivalTime = ((nanoTime() - start) / MAKE_NANO_TO_SECONDS);
        out.println(RETURN_AFTER + retreivalTime + SECS);

        out.println("=========Pricess with parallel stream future==========");
        start = nanoTime();
        out.println(LOOKED_FOR_PRICE + demo.findPricesParallelFurture(MOBILE));
        retreivalTime = ((nanoTime() - start) / MAKE_NANO_TO_SECONDS);
        out.println(RETURN_AFTER + retreivalTime + SECS);

    }

    public Double getPrice(final String product) {
        return calculatePrice(product);
    }

    public Future<Double> getPriceInFuture(final String product) {
        final CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                final Double price = calculatePrice(product);
                // out.println("price aagya hai: " + price);
                // if (true)
                // throw new RuntimeException("Mene roka hai");
                futurePrice.complete(price);
            } catch (final Exception e) {
                futurePrice.completeExceptionally(e);
            }
        }).start();
        return futurePrice;
    }

    public Future<Double> getPriceWithSupplyAsync(final String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private Double calculatePrice(final String product) {
        delay();
        return rand.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    private void delay() {
        try {
            Thread.sleep(1000L);
        } catch (final InterruptedException e) {
            log.log(Level.WARNING, "Error", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 
     */
    public List<String> findPrices(final String product) {
        return shops.stream().map(shop -> String.format("%s price is $ %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public List<String> findPricesPrallel(final String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is $ %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public List<String> findPricesFurture(final String product) {
        final List<CompletableFuture<String>> future = shops.stream()
                .map(shop -> CompletableFuture
                        .supplyAsync(() -> String.format("%s price is $ %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());

        return future.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public List<String> findPricesParallelFurture(final String product) {
        final List<CompletableFuture<String>> future = shops.parallelStream()
                .map(shop -> CompletableFuture
                        .supplyAsync(() -> String.format("%s price is $ %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());

        return future.parallelStream().map(CompletableFuture::join).collect(Collectors.toList());
    }

}