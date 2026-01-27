package com.sweet.n_plus_one_query.service;

public interface IsolationService {
    void testReadUncommitted(Long id) throws InterruptedException;
    void testReadCommitted(Long id, Long quantity) throws InterruptedException;
    void testRepeatableRead(Long id, Long quantity) throws InterruptedException;
}
