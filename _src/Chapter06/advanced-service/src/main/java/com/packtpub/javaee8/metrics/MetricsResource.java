package com.packtpub.javaee8.metrics;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
@Path("metrics")
public class MetricsResource {

    private Random random = new Random(4711L);

    private AtomicLong poolSize = new AtomicLong(10);

    @POST
    @Path("/timed")
    @Timed(displayName = "Timed invocation", unit = "milliseconds")
    public Response timed() throws InterruptedException {
        poolSize.incrementAndGet();
        TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
        poolSize.decrementAndGet();

        return Response.noContent().build();
    }

    @Gauge(displayName = "Gauge invocation", unit = "seconds")
    public long gauge() {
        return poolSize.get();
    }

    @POST
    @Path("/counted")
    @Counted(monotonic = true)
    public Response counted() throws InterruptedException {
        poolSize.incrementAndGet();
        TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
        poolSize.decrementAndGet();

        return Response.noContent().build();
    }

}
