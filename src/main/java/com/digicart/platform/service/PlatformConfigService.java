package com.digicart.platform.service;

import com.digicart.platform.dto.PlatformConfigDto;
import com.digicart.platform.entity.PlatformConfig;
import com.digicart.platform.repository.PlatformConfigRepository;
import org.springframework.stereotype.Service;

/**
 * Application service implementing platform config use cases for <em>platform-service</em>.
 */
@Service
public class PlatformConfigService {

    private final PlatformConfigRepository repository;

    public PlatformConfigService(PlatformConfigRepository repository) {
        this.repository = repository;
    }

    public PlatformConfig get() {
        return repository.findById("singleton").orElseGet(() -> {
            PlatformConfig config = new PlatformConfig();
            config.setId("singleton");
            config.setData("{}");
            return repository.save(config);
        });
    }

    public PlatformConfig update(PlatformConfigDto.UpdateRequest req) {
        PlatformConfig config = get();
        if (req.getData() != null) config.setData(req.getData());
        return repository.save(config);
    }
}
