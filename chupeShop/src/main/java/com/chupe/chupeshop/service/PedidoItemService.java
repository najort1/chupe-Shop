package com.chupe.chupeshop.service;

import com.chupe.chupeshop.model.PedidoItem;
import com.chupe.chupeshop.repository.PedidoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoItemService {

    private final PedidoItemRepository pedidoItemRepository;

    public List<PedidoItem> findAllPedidosItem(){
        return pedidoItemRepository.findAll();
    }

    public Optional<PedidoItem> findPedidoItemById(Long id){
        return pedidoItemRepository.findById(id);
    }

    public PedidoItem savePedidoItem(PedidoItem pedidoItem){
        return pedidoItemRepository.save(pedidoItem);
    }

    public void deletePedidoItem(Long id){
        pedidoItemRepository.deleteById(id);
    }



}
