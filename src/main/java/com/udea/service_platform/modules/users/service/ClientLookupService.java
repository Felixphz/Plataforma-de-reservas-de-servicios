package com.udea.service_platform.modules.users.service;

import java.util.Collection;
import java.util.Map;

/**
 * Facade published by the users module.
 * Other modules resolve client display data through this
 * contract instead of injecting UserRepository directly.
 */
public interface ClientLookupService {

    /**
     * Returns display info for a user, fail-fast when the user
     * does not exist or does not have the Cliente role.
     */
    ClientDisplayInfo findClientDisplayInfo(Long userId);

    /**
     * Batch variant to avoid N+1 lookups. Only includes users
     * that exist and have the Cliente role.
     */
    Map<Long, ClientDisplayInfo> findClientDisplayInfoByIds(Collection<Long> userIds);
}
