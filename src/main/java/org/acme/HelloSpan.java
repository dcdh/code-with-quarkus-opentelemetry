package org.acme;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloSpan {

    private final Tracer tracer;

    // Instead of using @Inject on the tracer attribute
    public HelloSpan(Tracer tracer) {
        // The same as openTelemetry.getTracer("io.quarkus.opentelemetry");
        this.tracer = tracer;
    }

    public String helloManualSpan() {
        Log.info("helloManualSpan");
        // Create a new span
        Span span = tracer.spanBuilder("HelloBean.helloManualSpan").startSpan();
        // Make sure span scope is closed
        try (Scope scope = span.makeCurrent()) {
            // Add an attribute
            span.setAttribute("myAttributeName", "myValue");
            // Execute logic...
            return "Hello from Quarkus REST";
        } catch (Exception ignored) {
            // Store potential exceptions.
            span.recordException(ignored);
        } finally {
            // Whatever happens above, the span will be closed.
            span.end();
        }
        return "failover message";
    }

    @Scheduled(every = "1s")
    public void schedule() {
        helloManualSpan();
    }
}
