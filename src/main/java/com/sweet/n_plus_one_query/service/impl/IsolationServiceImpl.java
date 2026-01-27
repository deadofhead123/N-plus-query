package com.sweet.n_plus_one_query.service.impl;

import com.sweet.n_plus_one_query.service.IsolationService;
import com.sweet.n_plus_one_query.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsolationServiceImpl implements IsolationService {
    private final ProductService productService;

    @Override
    public void testReadUncommitted(Long id) throws InterruptedException {
        Thread threadA = new Thread(() -> {
           try{
               productService.updateStock(id, 5L);
           }
           catch (InterruptedException ie){
               ie.printStackTrace();
               throw new RuntimeException(ie.getMessage());
           }
        });

        Thread threadB = new Thread(() -> {
            try{
                Thread.sleep(2000);
                Long stock = productService.checkStock(id);
                System.out.println("Stock read by Transaction B: " + stock);
            }
            catch (InterruptedException ie){
                ie.printStackTrace();
                throw new RuntimeException(ie.getMessage());
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
    }

    @Override
    public void testReadCommitted(Long id, Long quantity) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            try{
                productService.updateStock(id, quantity);
            }
            catch (InterruptedException ie){
                ie.printStackTrace();
                throw new RuntimeException(ie.getMessage());
            }
        });

        Thread threadB = new Thread(() -> {
            try{
                Thread.sleep(2000);
                Long stock = productService.checkStock(id);
                System.out.println("Stock read by Transaction B: " + stock);
            }
            catch (InterruptedException ie){
                ie.printStackTrace();
                throw new RuntimeException(ie.getMessage());
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
    }

    @Override
    public void testRepeatableRead(Long id, Long quantity) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            try{
                productService.updateStock(id, quantity);
            }
            catch (InterruptedException ie){
                ie.printStackTrace();
                throw new RuntimeException(ie.getMessage());
            }
        });

        Thread threadB = new Thread(() -> {
            try{
                productService.fetchStock(id);
            }
            catch (Exception ie){       // Tại sao chỗ này ko ném được InterruptedException?
                ie.printStackTrace();
                throw new RuntimeException(ie.getMessage());
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
    }

    @Override
    public void testSerializable(Long id, Long quantity) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            try{
                productService.updateStock(id, quantity);
            }
            catch (InterruptedException ie){
                ie.printStackTrace();
                throw new RuntimeException(ie.getMessage());
            }
        });

        Thread threadB = new Thread(() -> {
            try{
//                Thread.sleep(2000);
//                Long stock = productService.checkStock(id);
                productService.fetchStock(id);
//                System.out.println("Stock read by Transaction B: " + stock);
            }
//            catch (InterruptedException ie){
//                ie.printStackTrace();
//                throw new RuntimeException(ie.getMessage());
//            }
            catch (Exception ie){
                ie.printStackTrace();
                throw new RuntimeException(ie.getMessage());
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
    }
}
