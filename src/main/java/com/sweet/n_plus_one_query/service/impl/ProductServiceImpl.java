package com.sweet.n_plus_one_query.service.impl;

import com.sweet.n_plus_one_query.dto.ProductDto;
import com.sweet.n_plus_one_query.dto.request.ProductRequest;
import com.sweet.n_plus_one_query.entity.ProductEntity;
import com.sweet.n_plus_one_query.exception.DataNotFoundException;
import com.sweet.n_plus_one_query.repository.ProductRepository;
import com.sweet.n_plus_one_query.service.ProductService;
import com.sweet.n_plus_one_query.util.ErrorCode;
import com.sweet.n_plus_one_query.util.LocalizationUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final LocalizationUtil localizationUtil;

    @Override
    @Transactional
    public ProductDto createProduct(ProductRequest productRequest) {
        ProductEntity productEntity = modelMapper.map(productRequest, ProductEntity.class);
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        entityManager.flush();
        return modelMapper.map(savedProductEntity, ProductDto.class);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createProductWithThread(ProductRequest productRequest) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            try{
//                Thread.sleep(2000);
                ProductEntity productEntity = modelMapper.map(productRequest, ProductEntity.class);
                ProductEntity savedProductEntity = productRepository.save(productEntity);
                System.out.println("savedProductEntity's id = " + savedProductEntity.getId());
                Thread.sleep(10000);
                System.out.println("Thread A ended");
            }
            catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        });

        Thread threadB = new Thread(() -> {
            try{
                Long numberOfRecord = productRepository.count();
                System.out.println("Number of records 1st: " + numberOfRecord);

                Thread.sleep(5000);
                Long numberOfRecord2 = productRepository.count();
                System.out.println("Number of records 2nd: " + numberOfRecord2);
            }
            catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
    }

    @Override
//    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
//    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateStock(Long productId, Long stock) throws InterruptedException {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(localizationUtil.getLocalMessage(ErrorCode.Product.PRODUCT_NOT_FOUND, productId)));

        productEntity.setQuantity(stock);
        productRepository.save(productEntity);
        entityManager.flush();
        System.out.println("Transaction A: Updated stock: " +  productEntity.getQuantity());
//        Thread.sleep(1000);

//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        System.out.println("Transaction A rollback successfully");
        System.out.println("Transaction A: Change committed");
    }

    @Override
//    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Long checkStock(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(localizationUtil.getLocalMessage(ErrorCode.Product.PRODUCT_NOT_FOUND, productId)));
        return productEntity.getQuantity();
    }

    // Transaction B: Read stock multiple times
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void fetchStock(Long productId) {

        // First read
        ProductEntity product1 = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("Transaction B: First read stock as " + product1.getQuantity());

        // Simulate a delay to allow Transaction A to update the stock
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Second read
        ProductEntity product2 = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("Transaction B: Second read stock as " + product2.getQuantity());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Third read
        ProductEntity product3 = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("Transaction B: Third read stock as " + product2.getQuantity());
    }

}
