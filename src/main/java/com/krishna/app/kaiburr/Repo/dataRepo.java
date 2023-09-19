package com.krishna.app.kaiburr.Repo;
import com.krishna.app.kaiburr.Model.Mdata;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface dataRepo extends MongoRepository<Mdata, Long> {
    List<Mdata> findAllByNameContaining(String name);
}
