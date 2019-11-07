package toanhda.disruptor.disruptor;

import lombok.Getter;
import lombok.Setter;

/** @author ToanHDA created on Apr 25, 2019 */
@Setter
@Getter
public class DisruptorConfig {
  private int bufferSize = -1;
  private int numWorkers = -1;
  private String producerType = "";
}
