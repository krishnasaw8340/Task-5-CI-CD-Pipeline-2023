package com.krishna.app.kaiburr.Controller;
import com.krishna.app.kaiburr.Model.Mdata;
import com.krishna.app.kaiburr.Repo.dataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {
    @Autowired
    private  dataRepo dataRepo;
    @GetMapping(value="/")
    public List<Mdata> getData(){
        return dataRepo.findAll();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Mdata> getDataById(@PathVariable long id) {
        Optional<Mdata> data = dataRepo.findById(id);
        if (!data.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(data.get());
    }

    @GetMapping("/servers/{name}")
    public ResponseEntity<List<Mdata>> getServersByName(@PathVariable String name) {
        List<Mdata> servers = dataRepo.findAllByNameContaining(name);

        if (servers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servers);
    }
    @PostMapping(value ="/send")
    public String saveData(@RequestBody Mdata d)
    {
        dataRepo.save(d);
        return "Data saved to database...";
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<String> updateData(@PathVariable long id, @RequestBody Mdata d) {
        Optional<Mdata> optionalData = dataRepo.findById(id);

        if (optionalData.isPresent()) {
            Mdata updateData = optionalData.get();
            updateData.setName(d.getName());
            updateData.setLanguage(d.getLanguage());
            updateData.setFramework(d.getFramework());

            dataRepo.save(updateData); // Save the updated data

            return ResponseEntity.ok("Data is updated");
        } else {
            return ResponseEntity.notFound().build(); // Handle the case where data with the given ID is not found
        }
    }

    @DeleteMapping(value="/delete/{id}")
    public String deleteUser(@PathVariable long id)
    {
        Mdata deleteUser = dataRepo.findById(id).get();
        dataRepo.delete(deleteUser);
        return "Delete user with the id..."+id;
    }
}
