package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item = new Item("ItemA",10000,10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem); // 실제 저장한값이랑 조회한값이랑 같다 Assertions.assertThat() 인데  Assertions에 알트+엔터해서 static으로 추가해놔서 줄어듬


    }

    @Test
    void findAll(){
        //given

        Item item1 = new Item("Item1",10000,10);
        Item item2 = new Item("Item2",20000,20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1,item2);//contains 는  item1 item2 가지고있느냐 묻는거

    }

    @Test
    void updateItem(){
        //given
        Item item = new Item("Item1",10000,10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        Item updateParam = new Item("Item2", 20000, 30);
        itemRepository.update(itemId,updateParam);

        //then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());

    }

}
