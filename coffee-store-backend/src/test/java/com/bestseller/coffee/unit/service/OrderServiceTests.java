package com.bestseller.coffee.unit.service;

import com.bestseller.coffee.dto.request.order.CreateOrderDto;
import com.bestseller.coffee.dto.request.order.DrinkOrderDto;
import com.bestseller.coffee.dto.request.order.ToppingOrderDto;
import com.bestseller.coffee.dto.response.order.CreatedOrderDto;
import com.bestseller.coffee.entity.DrinkOrder;
import com.bestseller.coffee.entity.Order;
import com.bestseller.coffee.entity.ToppingOrder;
import com.bestseller.coffee.repository.DrinkOrderRepository;
import com.bestseller.coffee.repository.OrderRepository;
import com.bestseller.coffee.repository.ToppingOrderRepository;
import com.bestseller.coffee.service.Impl.OrderServiceImpl;
import com.bestseller.coffee.service.OrderService;
import com.bestseller.coffee.strategy.DiscountExecute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DrinkOrderRepository drinkOrderRepository;

    @Mock
    private ToppingOrderRepository toppingOrderRepository;

    @Mock
    private DiscountExecute discountExecute;

    @InjectMocks
    private OrderServiceImpl orderService;

    @DisplayName("junit test - create order successfully")
    @Test
    public void givenOrderObject_whenCreateOrder_thenReturnCreatedOrderObject(){
        // given - precondition or setup
        CreateOrderDto createOrderDto = new CreateOrderDto();
        DrinkOrderDto drinkOrderDto = DrinkOrderDto.builder()
                .drinkId(1l)
                .drinkAmount(new BigDecimal("18"))
                .drinkName("Black Coffee")
                .build();
        ToppingOrderDto toppingOrderDto = ToppingOrderDto.builder()
                .toppingId(1l)
                .toppingAmount(new BigDecimal("2"))
                .toppingName("Milk")
                .build();
        drinkOrderDto.setToppingOrderList(List.of(toppingOrderDto));
        createOrderDto.setCartList(List.of(drinkOrderDto));

        CreatedOrderDto createdOrderDto = CreatedOrderDto.builder()
                .cartList(createOrderDto.getCartList())
                .totalAmount(new BigDecimal("20"))
                .build();

        Order order = Order.builder()
                .drinksCount(1)
                .toppingsCount(0)
                .totalAmount(new BigDecimal("20"))
                .discountedAmount(new BigDecimal("15"))
                .discount(new BigDecimal("5")).build();

        DrinkOrder drinkOrder = DrinkOrder.builder()
                .drinkId(drinkOrderDto.getDrinkId())
                .orderId(order.getId())
                .drinkAmount(drinkOrderDto.getDrinkAmount())
                .drinkName(drinkOrderDto.getDrinkName()).build();

        ToppingOrder toppingOrder = ToppingOrder.builder()
                .orderId(order.getId())
                .toppingId(1l)
                .toppingAmount(new BigDecimal("2"))
                .toppingName("Milk")
                .drinkId(drinkOrder.getId())
                .build();

        willDoNothing().given(discountExecute).execute(createdOrderDto);

        given(orderRepository.save(any(Order.class))).willReturn(order);
        given(drinkOrderRepository.save(any(DrinkOrder.class))).willReturn(drinkOrder);
        given(toppingOrderRepository.save(any(ToppingOrder.class))).willReturn(toppingOrder);

        // when -  action or the behaviour that we are going test
        CreatedOrderDto response = orderService.createOrder(createOrderDto);

        //verify method
        assertThat(response.getTotalAmount()).isEqualTo(createdOrderDto.getTotalAmount());
        assertThat(response.getCartList()).hasSameSizeAs(createdOrderDto.getCartList());

    }

}
