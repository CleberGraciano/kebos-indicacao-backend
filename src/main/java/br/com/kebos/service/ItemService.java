package br.com.kebos.service;


import br.com.kebos.model.Item;
import javassist.NotFoundException;

import java.util.List;


public interface ItemService {

    Item findById(Long id);
    List<Item> findAll();
    Item save(Item item);

    Item update(Long id, Item item) throws NotFoundException;

}
