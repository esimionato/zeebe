/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Zeebe Community License 1.0. You may not use this file
 * except in compliance with the Zeebe Community License 1.0.
 */
package io.zeebe.e2e.util.containers.configurators;

import io.zeebe.containers.ZeebeBrokerContainer;
import io.zeebe.e2e.util.containers.BrokerConfigurator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BrokerConfiguratorChain implements BrokerConfigurator {
  private final List<BrokerConfigurator> configurators;

  public BrokerConfiguratorChain(final BrokerConfigurator... configurators) {
    this(Arrays.asList(configurators));
  }

  public BrokerConfiguratorChain(final List<BrokerConfigurator> configurators) {
    this.configurators = new ArrayList<>();
    this.configurators.addAll(configurators);
  }

  public void add(final BrokerConfigurator configurator) {
    configurators.add(configurator);
  }

  @Override
  public ZeebeBrokerContainer configure(final ZeebeBrokerContainer brokerContainer) {
    var container = brokerContainer;
    for (final var configurator : configurators) {
      container = configurator.configure(container);
    }

    return container;
  }
}