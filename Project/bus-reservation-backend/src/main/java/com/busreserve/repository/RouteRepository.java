package com.busreserve.repository;

import com.busreserve.entity.Route;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RouteRepository extends MongoRepository<Route, String> {
    List<Route> findByOriginIgnoreCaseAndDestinationIgnoreCase(String origin, String destination);
}
