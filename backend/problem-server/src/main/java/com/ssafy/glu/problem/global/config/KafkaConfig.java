package com.ssafy.glu.problem.global.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.ssafy.glu.problem.domain.problem.dto.event.ProblemSolvedEvent;

@Configuration
public class KafkaConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	String KAFKA_BOOTSTRAP_SERVERS;

	@Value("${kafka.consumer.group-id.user-problem-log}")
	String GROUP_ID_USER_PROBLEM_LOG;

	@Value("${kafka.consumer.group-id.user-problem-status}")
	String GROUP_ID_USER_PROBLEM_STATUS;

	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ConsumerFactory<String, ProblemSolvedEvent> consumerFactory() {
		JsonDeserializer<ProblemSolvedEvent> jsonDeserializer = new JsonDeserializer<>(ProblemSolvedEvent.class);
		jsonDeserializer.setRemoveTypeHeaders(false);
		jsonDeserializer.addTrustedPackages("*");
		jsonDeserializer.setUseTypeMapperForKey(true);
		return new DefaultKafkaConsumerFactory<>(
			kafkaConsumerConfigs(),
			new StringDeserializer(),
			jsonDeserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ProblemSolvedEvent> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ProblemSolvedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	// Kafka 설정
	public Map<String, Object> kafkaConsumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return props;
	}
}