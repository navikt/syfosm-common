package no.nav.syfo.kafka.aiven

import no.nav.syfo.kafka.toConsumerConfig
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.SslConfigs
import java.util.*


class KafkaUtils {
    companion object {
        fun getAivenKafkaConfig() : Properties {
            return Properties().also {
                val kafkaEnv = KafkaEnvironment()
                it[CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG] = kafkaEnv.KAFKA_BROKERS
                it[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = "SASL_SSL"
                it[CommonClientConfigs.CLIENT_ID_CONFIG] = kafkaEnv.KAFKA_CLIENT_ID
                it[SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG] = "jks"
                it[SslConfigs.SSL_KEYSTORE_TYPE_CONFIG] = "PKCS12"
                it[SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG] = kafkaEnv.KAFKA_CA_PATH
                it[SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG] = kafkaEnv.KAFKA_CREDSTORE_PASSWORD
                it[SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG] = kafkaEnv.KAFKA_KEYSTORE_PATH
                it[SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG] = kafkaEnv.KAFKA_CREDSTORE_PASSWORD
                it[ProducerConfig.ACKS_CONFIG] = "all"
                it[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = "true"
            }
        }
    }
}
