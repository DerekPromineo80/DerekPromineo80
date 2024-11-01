package tree.harvest.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import tree.harvest.controller.model.TreeFieldData;
import tree.harvest.service.TreeFieldService;

@RestController
@Tag(name = "Forests", description= "Displays and interactions with the forests or national park areas.")
public class ForestController {
  //@Autowired
  private TreeFieldService service;
  
  public ForestController(TreeFieldService service) {
    this.service = service;
  }
  
  @GetMapping(value = "/forests")
  public List<TreeFieldData> all() {
    List<TreeFieldData> forests = service.getAllForests();
    return forests;
  }
  
  @PostMapping(value = "/forests")
  public ResponseEntity<TreeFieldData> create(@RequestBody TreeFieldData forest) {
    if ((forest != null) && (forest.isValid())) {
      TreeFieldData result = service.createForest(forest);
      if (result != null) {
        return ResponseEntity.ok(result);
      }
      return (ResponseEntity<TreeFieldData>) ResponseEntity.internalServerError();
    }
    
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .body(null);
  }
}
