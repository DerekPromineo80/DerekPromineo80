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

import lombok.extern.slf4j.Slf4j;
import tree.harvest.controller.model.ForresterData;
import tree.harvest.controller.model.TreeFieldData;
import tree.harvest.service.TreeFieldService;

@RestController
@RequestMapping("/tree_harvest")
@Slf4j
public class TreeFieldController {

	@Autowired
	private TreeFieldService treeFieldService;
	
	@PostMapping("/forrester")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ForresterData insertForester(
			@RequestBody ForresterData forresterData) {
		log.info("Creating Forrester {}", forresterData);
		return treeFieldService.saveForrester(forresterData);
	}
	
	@PutMapping("/forrester/{forresterId}")
	public ForresterData updateForrester(@PathVariable Long forresterId,
			@RequestBody ForresterData forresterData) {
		forresterData.setForresterId(forresterId);
		log.info("Updating Forrester {}", forresterData);
		return treeFieldService.saveForrester(forresterData);
	}
	
	@GetMapping("/forrester")
	public List<ForresterData> retrieveAllForresters() {
		log.info("Retrieve all Foresters called.");
		return treeFieldService.retrieveAllForresters();
	}
	
	@GetMapping("/forrester/{forresterId}")
	public ForresterData retrieveForresterById(@PathVariable Long forresterId) {
		log.info("Retrieving Forrester with ID={}", forresterId);
		return treeFieldService.retrieveForresterId(forresterId);
	}
	
	@DeleteMapping("/forrester")
	public void deleteAllForresters() {
		log.info("Attempting to delete all Forresters");
		throw new UnsupportedOperationException("Deleting all Forresters is prohibited");		
	}
	
	@DeleteMapping("/forrester/{forresterId}")
	public Map<String, String> deleteForresterById(
			@PathVariable Long forresterId) {
		
		log.info("Deleting Forrester with ID={}", forresterId);
		
		treeFieldService.deleteForresterById(forresterId);
		
		return Map.of("message", "Deletion of contributor with ID="
				+ forresterId + " was successful.");
				
	}
	
	@PostMapping("/forrester/{forresterId}/treeField")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TreeFieldData insertTreeField(@PathVariable Long forresterId,
			@RequestBody TreeFieldData treeFieldData) {
		log.info("Creating Tree Field {} for Forrester with ID={}", treeFieldData,
				forresterId);
		
		return treeFieldService.saveTreeField(forresterId, treeFieldData);
		
	}
	
	@PutMapping("/forrester/{forresterId}/treeField/{treeFieldId}")
	public TreeFieldData updateTreeField(@PathVariable Long forresterId,
			@PathVariable Long treeFieldId,
			@RequestBody TreeFieldData treeFieldData) {
		treeFieldData.setTreeFieldId(treeFieldId);
		
		log.info("Updating Tree Field {} for Forrester with ID={}", 
				treeFieldData, forresterId);
		
		return treeFieldService.saveTreeField(forresterId, treeFieldData);
	}
	
	@GetMapping("/forrester/{forresterId}/treeField/{treeFieldId}")
	public TreeFieldData retrievePetParkById(@PathVariable Long forresterId,
			@PathVariable Long treeFieldId) {
		log.info("Retrieving Tree Field with ID={} for Forrester with ID={}",
				treeFieldId, forresterId);
		
		return treeFieldService.retrieveTreeFieldById(forresterId, treeFieldId);
	}
	
}
