package com.ftnisa.isa.task;

import com.ftnisa.isa.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RouteTask {
    private static final Logger log = LoggerFactory.getLogger(RouteTask.class);

    private final RouteService routeService;

    public RouteTask(RouteService routeService) {
        this.routeService = routeService;
    }

    @Scheduled(fixedRate = 300000)
    public void reportCurrentTime() {
        var removedItems = routeService.cleanOrphanRoutes();
        if (removedItems > 0){
            log.info(removedItems + " orphan routes removed!");
        } else {
            log.info("No orphan routes found!");
        }
    }
}
