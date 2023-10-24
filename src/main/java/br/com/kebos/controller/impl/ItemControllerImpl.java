package br.com.kebos.controller.impl;
import br.com.kebos.controller.ItemController;
import br.com.kebos.model.Item;
import br.com.kebos.service.ItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/items")
@SecurityRequirement(name = "kebosapi")
public
class ItemControllerImpl implements ItemController {

    @Autowired
    private ItemService itemService;

    @PreAuthorize("hasRole('ADMIN') and hasRole('MODERATOR') and hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Item>> findAll(){
        return ResponseEntity.ok(itemService.findAll());
    }


    @PreAuthorize("hasRole('ADMIN') and hasRole('MODERATOR') and hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Item> findByIdItem(@PathVariable("id") Long id){
        return ResponseEntity.ok(itemService.findById(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Item> saveItem(@Valid @RequestBody Item item){
        return ResponseEntity.ok(itemService.save(item));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') and hasRole('MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable(name = "id") Long id, @Valid @RequestBody Item item){
        try {
            itemService.update(id, item);
        } catch (NotFoundException e){
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(itemService.save(item));
    }
}
