package vng.toanhda.grpc;

import lombok.Getter;
import lombok.Setter;

/** @author ToanHDA created on Apr 25, 2019 */
@Setter
@Getter
public class GrpcConfig {
  private int port = -1;
  private int numInstances = Runtime.getRuntime().availableProcessors();
}
