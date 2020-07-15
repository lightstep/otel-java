package com.lightstep.opentelemetry.common;

public class VariablesConverter {
  public static final String DEFAULT_LS_SATELLITE_URL = "ingest.lightstep.com";
  public static final long DEFAULT_LS_DEADLINE_MILLIS = 30000;
  public static final boolean DEFAULT_LS_USE_TLS = true;
  public static final String DEFAULT_PROPAGATOR = "b3";

  private static final String LS_ACCESS_TOKEN = "LS_ACCESS_TOKEN";
  private static final String LS_SATELLITE_URL = "LS_SATELLITE_URL";
  private static final String OTEL_PROPAGATORS = "OTEL_PROPAGATORS";
  private static final String LS_USE_TLS = "LS_USE_TLS";
  private static final String LS_DEADLINE_MILLIS = "LS_DEADLINE_MILLIS";

  public static void convert(String satelliteUrl,
      boolean useTls,
      long deadlineMillis,
      String accessToken,
      String propagator) {
    System.setProperty("otel.otlp.endpoint", satelliteUrl);
    System.setProperty("otel.otlp.use.tls", String.valueOf(useTls));
    System.setProperty("otel.otlp.span.timeout", String.valueOf(deadlineMillis));
    System.setProperty("otel.otlp.metadata", "lightstep-access-token=" + accessToken);
    System.setProperty("ota.propagators", propagator);
  }

  public static void convertFromEnv() {
    convert(getSatelliteUrl(), useTransportSecurity(), getDeadlineMillis(), getAccessToken(),
        getPropagator());
  }

  public static String getAccessToken() {
    return getProperty(LS_ACCESS_TOKEN, "");
  }

  public static String getSatelliteUrl() {
    return getProperty(LS_SATELLITE_URL, DEFAULT_LS_SATELLITE_URL);
  }

  public static String getPropagator() {
    return getProperty(OTEL_PROPAGATORS, DEFAULT_PROPAGATOR);
  }

  public static boolean useTransportSecurity() {
    return Boolean.parseBoolean(getProperty(LS_USE_TLS, String.valueOf(DEFAULT_LS_USE_TLS)));
  }

  public static long getDeadlineMillis() {
    return Long.parseLong(
        getProperty(LS_DEADLINE_MILLIS, String.valueOf(DEFAULT_LS_DEADLINE_MILLIS)));
  }

  private static String getProperty(String name, String defaultValue) {
    String val = System.getProperty(name.toLowerCase().replaceAll("_", "."), System.getenv(name));
    if (val == null || val.isEmpty()) {
      return defaultValue;
    }
    return val;
  }
}
