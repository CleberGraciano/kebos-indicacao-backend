package br.com.kebos.service;


import br.com.kebos.model.Item;

import java.util.List;


public interface ItemService {

    Item findById(Long id);
    List<Item> findAll();
    Item save(Item item);

}
