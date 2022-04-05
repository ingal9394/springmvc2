package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
//@Getter @Setter
public class Item {
    private long id;
    private String itemName;
    private Integer price; //혹시 null 들어갈수있으니 int  안쓴거
    private Integer quantity;

    public Item(){

    }

    public Item( String itemName, Integer price, Integer quantity) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
