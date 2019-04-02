package no.nav.syfo.rules

import io.prometheus.client.Counter
import no.nav.syfo.model.Status
import no.nav.syfo.model.Sykmelding

val RULE_HIT_COUNTER: Counter = Counter.Builder()
    .namespace("syfosm")
    .name("rule_hit_counter")
    .labelNames("rule_name")
    .help("Counts the amount of times a rule is hit").register()

data class RuleData<T>(val healthInformation: Sykmelding, val metadata: T)

interface Rule<in T> {
    val name: String
    val ruleId: Int?
    val messageForSender: String?
    val messageForUser: String?
    val status: Status
    val predicate: (T) -> Boolean
    operator fun invoke(input: T) = predicate(input)
}

inline fun <reified T, reified R : Rule<RuleData<T>>> List<R>.executeFlow(healthInformation: Sykmelding, value: T): List<Rule<Any>> =
    filter { it.predicate(RuleData(healthInformation, value)) }
        .map { it as Rule<Any> }
        .onEach { RULE_HIT_COUNTER.labels(it.name).inc() }

inline fun <reified T, reified R : Rule<RuleData<T>>> Array<R>.executeFlow(healthInformation: Sykmelding, value: T): List<Rule<Any>> = toList().executeFlow(healthInformation, value)

@Retention(AnnotationRetention.RUNTIME)
annotation class Description(val description: String)
