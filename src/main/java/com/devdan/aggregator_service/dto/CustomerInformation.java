package com.devdan.aggregator_service.dto;

import java.util.List;

public record CustomerInformation(Integer id, String name, String balance, List<Holding> holdings) {
}
