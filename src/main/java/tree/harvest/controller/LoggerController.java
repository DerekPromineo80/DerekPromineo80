package tree.harvest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import tree.harvest.controller.model.ForesterData;
import tree.harvest.controller.model.TreeFieldData;
import tree.harvest.service.TreeFieldService;

@RestController
@RequestMapping("")
@Slf4j
@Tag(name = "Loggers", description = "Foresters that perform the tree harvesting")
public class LoggerController {

	@Autowired
	private TreeFieldService treeFieldService;
	
	@PostMapping("/loggers")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ForesterData insertForester(@RequestBody ForesterData foresterData) {
		log.info("Creating Forester {}", foresterData);
		return treeFieldService.saveForester(foresterData);
	}
	
	@PutMapping("/loggers/{foresterId}")
	public ForesterData updateForester(@PathVariable Long foresterId,
			@RequestBody ForesterData foresterData) {
		foresterData.setForesterId(foresterId);
		log.info("Updating Forrester {}", foresterData);
		return treeFieldService.saveForester(foresterData);
	}
	
	@GetMapping("/loggers")
	public List<ForesterData> retrieveAllForesters() {
		log.info("Retrieve all Foresters called.");
		return treeFieldService.retrieveAllForesters();
	}
	
	@GetMapping("/loggers/{foresterId}")
	public ForesterData retrieveForesterById(@PathVariable Long foresterId) {
		log.info("Retrieving Forrester with ID={}", foresterId);
		return treeFieldService.retrieveForesterId(foresterId);
	}
	
	@DeleteMapping("/loggers")
	public void deleteAllForesters() {
		log.info("Attempting to delete all Foresters");
		throw new UnsupportedOperationException("Deleting all Foresters is prohibited");		
	}
	
	@DeleteMapping("/loggers/{foresterId}")
	public Map<String, String> deleteForesterById(
			@PathVariable Long foresterId) {
		
		log.info("Deleting Forester with ID={}", foresterId);
		
		treeFieldService.deleteForesterById(foresterId);
		
		return Map.of("message", "Deletion of Forester with ID="
				+ foresterId + " was successful.");
				
	}
	
//	@PostMapping("/loggers/{foresterId}/treeField")
//	@ResponseStatus(code = HttpStatus.CREATED)
//	public TreeFieldData insertTreeField(@PathVariable Long foresterId,
//			@RequestBody TreeFieldData treeFieldData) {
//		log.info("Creating Tree Field {} for Forester with ID={}", treeFieldData,
//				foresterId);
//		
//		return treeFieldService.saveTreeField(foresterId, treeFieldData);
//		
//	}
	
//	@PutMapping("/loggers/{foresterId}/treeField/{treeFieldId}")
//	public TreeFieldData updateTreeField(@PathVariable Long foresterId,
//			@PathVariable Long treeFieldId,
//			@RequestBody TreeFieldData treeFieldData) {
//		treeFieldData.setTreeFieldId(treeFieldId);
//		
//		log.info("Updating Tree Field {} for Forester with ID={}", 
//				treeFieldData, foresterId);
//		
//		return treeFieldService.saveTreeField(foresterId, treeFieldData);
//	}
	
//	@GetMapping("/loggers/{foresterId}/treeField/{treeFieldId}")
//	public TreeFieldData retrievePetParkById(@PathVariable Long foresterId,
//			@PathVariable Long treeFieldId) {
//		log.info("Retrieving Tree Field with ID={} for Forester with ID={}",
//				treeFieldId, foresterId);
//		
//		return treeFieldService.retrieveTreeFieldById(foresterId, treeFieldId);
//	}
	
}
