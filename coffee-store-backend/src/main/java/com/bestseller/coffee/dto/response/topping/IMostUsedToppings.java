package com.bestseller.coffee.dto.response.topping;


public interface IMostUsedToppings {

    Long getToppingId();
    String getToppingName();
    Integer getCount();

    void setToppingId(Long toppingId);
    void seToppingName(String toppingName);
    void setCount(Integer count);

}
