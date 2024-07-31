package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

  private Long id;
  private String itemName;
  private Integer price;
  private Integer quantity; // int타입은 null이 들어갈 수 없으니, Integer 객체 타입은 null을 넣을 수 있어서, null값이 들어갈 수 있는 값에 대해서 Integer 타입을 쓰기도 한다.

  public Item() {}

  public Item(String itemName, Integer price, Integer quantity) {
    this.itemName = itemName;
    this.price = price;
    this.quantity = quantity;
  }
}
