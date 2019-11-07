import com.codahale.metrics.MetricRegistry;
import com.signalfx.codahale.SfxMetrics;
import com.signalfx.codahale.reporter.SignalFxReporter;
import com.signalfx.endpoint.SignalFxEndpoint;
import com.signalfx.endpoint.SignalFxReceiverEndpoint;
import com.signalfx.metrics.auth.StaticAuthToken;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SfxMetricsRegistry {
    static SfxMetrics wireMetrics() throws MalformedURLException {
        String ORG_TOKEN = System.getenv("SFX_ORG_TOKEN");
        String REALM = "us1";

        final String ingestStr = String.format("https://ingest.%s.signalfx.com", REALM);
        final URL ingestUrl = new URL(ingestStr);
        SignalFxReceiverEndpoint endpoint =
                new SignalFxEndpoint(ingestUrl.getProtocol(), ingestUrl.getHost(), ingestUrl.getPort());
        MetricRegistry metricRegistry = new MetricRegistry();
        SignalFxReporter reporter =
                new SignalFxReporter.Builder(metricRegistry, new StaticAuthToken(ORG_TOKEN), ingestStr)
                        .setEndpoint(endpoint)
                        .build();

        reporter.start(1, TimeUnit.SECONDS);

        return new SfxMetrics(metricRegistry, reporter.getMetricMetadata());
    }
}
