package jpabook.jpashop.domain;


import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Getter @Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //== 비지니스 로직 ==//
    // 데이터를 가지고 있는 쪽에 비지니스 로직을 넣자 그래야 응집ㅕㄱ이 있다.

    /**
     * stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;

    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity){
        int restStock =this.stockQuantity - quantity;
        if(restStock <0){
            throw new NotEnoughStockException("need more stock");
        }
    }

}
