package net.loove.notifications.services;

import net.loove.notifications.dtos.ProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "profiles-service", url = "${services.gateway.url}", path = "/api/v1")
public interface ProfilesService {

    @GetMapping("/profiles/{userId}")
    ProfileDTO getProfile(@PathVariable Long userId);

}
