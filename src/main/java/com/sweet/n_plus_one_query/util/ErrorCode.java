package com.sweet.n_plus_one_query.util;

public class ErrorCode {
    public class Order{
        public static final String TEST_PROPAGATION = "order.transactional.test.propagation";
    }

    public class Product{
        public static final String NAME_NOT_NULL_OR_BLANK = "product.name.notNullOrBlank";
        public static final String PRICE_NOT_NULL_OR_BLANK = "product.price.notNullOrBlank";
        public static final String PRICE_MUST_BE_POSITIVE = "product.price.mustBePositive";
        public static final String PRODUCT_NOT_FOUND = "product.createProduct.notFound";
        public static final String NAME_EXISTED = "product.createProduct.nameExists";
        public static final String PRODUCT_NOT_ENOUGH_QUANTITY = "product.createOrder.notEnoughQuantity";
    }
}
