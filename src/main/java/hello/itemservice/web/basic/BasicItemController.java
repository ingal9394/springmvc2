package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // 자동으로 생성자 만들어지는 어노테이션
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";

    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addform";
    }

    //getmapping이랑 같은 url 로 하지만 넘어오는 방식 get,post에따라 분기되는것
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam  int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item",item);

        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){

        //@ModelAttribute 를 씀으로써 밑에 주석들이 자동실행된다고 보면됨  생성 및 set
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);

        itemRepository.save(item);
        //@ModelAttribute("item2")  "item" 의 의미는 밑에주석임  자동으로 객체생성 및 set 한  item 을  item2라고 addattribute 하는거
        //model.addAttribute("item2",item);

        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item,Model model){

        // @ModelAttribute 만 쓰게되면 클래스명이 Item 이면 >> 첫글자만 소문자가되서 item 으로 자동으로 추가됨
        //model.addAttribute("item",item);
        itemRepository.save(item);

        return "basic/item";
    }

 //   @PostMapping("/add")
    public String addItemV4( Item item){
        //인간의 욕식은 끝이없기에... @ModelAttribute 도 날릴수있음.. 단순형태 string int 등은 @requestparam 이 숨겨져있는거고  나머지 개인이 만든것?들은 @modelattribute가 숨긴상태
        //사실 @ModelAttribute 는   Model model도 안받아도됨
        itemRepository.save(item);

        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemV5( Item item){

        itemRepository.save(item);
        //사용자가 정보입력후 저장버튼클릭하면  form 이니까 post방식으로 진행되고
        // return "basic/item"; 이렇게 리턴이 되어있으면  주소창은 요청받은대로 ~~~/add 이면서 페이지만 item으로 새로보여줌
        // 새로고침하면 포스트형식으로 동일정보다 또 날가가게 되어있음 = 중복추가
        // 따라서 리다이렉트를 해주면 다시 get으로 새로 요청이들어가서  getmapping쪽을 타게되어 새로고침시 중복저장이안됨
        //이것을 prg라고 부름 post/redirect/get
        return "redirect:/basic/items/"+ item.getId(); // 사실 이렇게 바로getid를 불러와서 넣어버리면 공백이라던지 문자가 잘못오면 오류나기때문에 관련해서 쓰는 redirectAttrbutes가 있음

    }
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){

        Item saveItem = itemRepository.save(item);

        //리다이렉트 시 뒤에다가 값을넣어서 날리기위해
       redirectAttributes.addAttribute("itemId",saveItem.getId());
       //status는 쿼리파라미터형식으로 맨뒤에 붙어서 ?status=ture 로 날아감
        redirectAttributes.addAttribute("status",true);

        return "redirect:/basic/items/{itemId}";

    }

    @GetMapping("/{itemId}/edit")
    public  String editForm(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editform";
    }


    @PostMapping("/{itemId}/edit")
    public  String editItemV1(@PathVariable Long itemId,@ModelAttribute Item item){
        itemRepository.update(itemId,item);

        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }

}
