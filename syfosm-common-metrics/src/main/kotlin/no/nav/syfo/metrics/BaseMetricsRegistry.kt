package no.nav.syfo.metrics

import io.prometheus.client.Summary

const val METRICS_NS = "syfosm"

val NETWORK_CALL_SUMMARY: Summary = Summary.Builder().namespace(METRICS_NS).name("network_call_summary")
    .labelNames("http_endpoint").help("Summary for networked call times").register()

