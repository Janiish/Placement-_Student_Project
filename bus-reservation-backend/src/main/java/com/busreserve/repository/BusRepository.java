package com.busreserve.repository;

import com.busreserve.entity.Bus;
import com.busreserve.entity.Route;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BusRepository extends MongoRepository<Bus, String> {
    List<Bus> findByRoute(Route route);
}
