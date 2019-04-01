package no.nav.syfo.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties
import kotlin.reflect.KClass

interface KafkaConfig {
    val kafkaBootstrapServers: String
}

interface KafkaCredentials {
    val kafkaUsername: String
    val kafkaPassword: String
}

fun loadBaseConfig(env: KafkaConfig, credentials: KafkaCredentials): Properties = Properties().also {
    it.load(KafkaConfig::class.java.getResourceAsStream("/kafka_base.properties"))
    it["sasl.jaas.config"] = "org.apache.kafka.common.security.plain.PlainLoginModule required " +
            "username=\"${credentials.kafkaUsername}\" password=\"${credentials.kafkaPassword}\";"
    it["bootstrap.servers"] = env.kafkaBootstrapServers
    it["specific.avro.reader"] = true
}

fun Properties.toConsumerConfig(
    groupId: String,
    valueDeserializer: KClass<out Deserializer<out Any>>,
    keyDeserializer: KClass<out Deserializer<out Any>> = StringDeserializer::class
): Properties = Properties().also {
    it.putAll(this)
    it[ConsumerConfig.GROUP_ID_CONFIG] = groupId
    it[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = keyDeserializer.java
    it[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = valueDeserializer.java
}

fun Properties.toProducerConfig(
    groupId: String,
    valueSerializer: KClass<out Serializer<out Any>>,
    keySerializer: KClass<out Serializer<out Any>> = StringSerializer::class
): Properties = Properties().also {
    it.putAll(this)
    it[ConsumerConfig.GROUP_ID_CONFIG] = groupId
    it[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = valueSerializer.java
    it[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = keySerializer.java
}
