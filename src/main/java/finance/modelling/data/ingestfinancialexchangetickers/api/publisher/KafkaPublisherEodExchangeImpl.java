package finance.modelling.data.ingestfinancialexchangetickers.api.publisher;

import finance.modelling.data.ingestfinancialexchangetickers.client.dto.EodExchangeDTO;
import finance.modelling.fmcommons.data.logging.LogPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static finance.modelling.fmcommons.data.helper.api.publisher.PublisherHelper.buildProducerRecordWithTraceIdHeader;

@Component
public class KafkaPublisherEodExchangeImpl implements KafkaPublisher<EodExchangeDTO> {

    private final KafkaTemplate<String, Object> template;

    public KafkaPublisherEodExchangeImpl(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void publishMessage(String topic, EodExchangeDTO payload) {
        UUID traceId = UUID.randomUUID();
        template.send(buildProducerRecordWithTraceIdHeader(topic, payload.getCode(),payload, traceId));
        LogPublisher.logInfoDataItemSent(EodExchangeDTO.class, topic, List.of(traceId));
    }
}
