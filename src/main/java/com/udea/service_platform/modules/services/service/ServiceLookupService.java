package com.udea.service_platform.modules.services.service;

import com.udea.service_platform.modules.services.model.Service;

/**
 * Facade published by the services module.
 * Other modules resolve services through this contract
 * instead of injecting ServiceRepository directly.
 */
public interface ServiceLookupService {

    /**
     * Returns the service when it exists and is active,
     * fail-fast otherwise.
     */
    Service findActiveService(Long serviceId);
}
