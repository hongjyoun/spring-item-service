package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

  private final ItemRepository itemRepository;

  // @RequiredArgsConstructor 이 어노테이션을 붙이면, 아래와 같은 생성자를 자동으로 생성해줌
  //  public BasicItemController(ItemRepository itemRepository) {
  //    this.itemRepository = itemRepository;
  //  }

  @GetMapping
  public String items(Model model) {
    List<Item> items = itemRepository.findAll();
    model.addAttribute("items", items);
    return "basic/items";
  }

  @GetMapping("/{itemId}")
  public String item(@PathVariable("itemId") Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/item";
  }

  @GetMapping("/add")
  public String addForm() {
    return "basic/addForm";
  }

//  @PostMapping("/add")
  public String addItemV1(@RequestParam("itemName") String itemName,
                     @RequestParam("price") int price,
                     @RequestParam("quantity") Integer quantity,
                     Model model) {
    Item item = new Item();
    item.setItemName(itemName);
    item.setPrice(price);
    item.setQuantity((quantity));

    itemRepository.save(item);
    model.addAttribute("item", item);

    return "basic/item";
  }

//  @PostMapping("/add")
  public String addItemV2(@ModelAttribute("item") Item item, Model model) {
    itemRepository.save(item);
//    model.addAttribute("item", item); // @ModelAttribute("item") 이 의미는, 이렇게 "item" 이라는 이름으로 addAttribute까지 하는 역할까지 다 수행해준다. 그래서 이 라인코드는 없어도 됨 (생략가능)
    return "basic/item";
  }

//  @PostMapping("/add")
  public String addItemV3(@ModelAttribute Item item, Model model) { // 괄호안에 ("item")이 생략되면, 클래스명(Item)의 첫글자만 소문자로 바꾼 item으로 자동으로 만들어준다.
    itemRepository.save(item);
    return "basic/item";
  }

//  @PostMapping("/add")
  public String addItemV4(Item item) {
    itemRepository.save(item);
    return "basic/item";
  }

//  @PostMapping("/add")
  public String addItemV5(Item item) {
    itemRepository.save(item);
    return "redirect:/basic/items/" + item.getId();
  }

  @PostMapping("/add")
  public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/basic/items/{itemId}";
  }

  @GetMapping("/{itemId}/edit")
  public String editForm(@PathVariable("itemId") Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/editForm";
  }

  @PostMapping("/{itemId}/edit")
  public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item) {
    itemRepository.update(itemId, item);
    return "redirect:/basic/items/{itemId}";
  }

  /**
   * 테스트용 데이터 추가
   */
  @PostConstruct
  public void init() {
    itemRepository.save(new Item("testA", 10000, 10));
    itemRepository.save(new Item("testB", 20000, 20));
  }

}
