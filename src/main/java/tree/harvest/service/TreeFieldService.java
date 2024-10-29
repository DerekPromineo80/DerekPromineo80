package tree.harvest.service;

import java.util.LinkedList;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tree.harvest.controller.model.ForresterData;
import tree.harvest.controller.model.TreeFieldData;
import tree.harvest.dao.ForresterDao;
import tree.harvest.dao.TreeDao;
import tree.harvest.dao.TreeFieldDao;
import tree.harvest.entity.Forrester;
import tree.harvest.entity.Tree;
import tree.harvest.entity.TreeField;

@Service
public class TreeFieldService {

	@Autowired
	private ForresterDao forresterDao;
	
	@Autowired
	private TreeDao treeDao;
	
	@Autowired
	private TreeFieldDao treeFieldDao;
	
	@Transactional(readOnly = false)
	public ForresterData saveForrester(ForresterData forresterData) {
		Long forresterId = forresterData.getForresterId();
		Forrester forrester = findOrCreateForrester(forresterId,
				forresterData.getForresterEmail());
		
		setFieldsInForrester(forrester, forresterData);
		
		return new ForresterData(forresterDao.save(forrester));
	}
	
	private void setFieldsInForrester(Forrester forrester,
			ForresterData forresterData) {
		forrester.setForresterEmail(forresterData.getForresterEmail());
		forrester.setForresterFirstName(forresterData.getForresterFirstName());
		forrester.setForresterLastName(forresterData.getForresterLastName());
		
	}
	
	private Forrester findOrCreateForrester(Long forresterId,
			String forresterEmail) {
		Forrester forrester;
		
		if(Objects.isNull(forresterId)) {
			Optional<Forrester> opForrester = 
					forresterDao.findByForresterEmail(forresterEmail);
			
			if(opForrester.isPresent()) {
				throw new DuplicateKeyException (
					"Forrester with email " + forresterEmail 
					+ " already exists."); 
			}
			
			forrester = new Forrester();
		}
		else {
			forrester = findForresterById(forresterId);
			
		}
		
		return forrester;
		
	}
	
	private Forrester findForresterById(Long forresterId) {
		return forresterDao.findById(forresterId)
				.orElseThrow(() -> new NoSuchElementException(
						"Contributor with ID=" + forresterId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<ForresterData> retrieveAllForresters() {
		List<Forrester> forresters = forresterDao.findAll();
		List<ForresterData> response = new LinkedList<>();
		
		for (Forrester forrester : forresters) {
			response.add(new ForresterData(forrester));
		}
		
		return response;
		
	}
	
	@Transactional(readOnly = true)
	public ForresterData retrieveForresterId(Long forresterId) {
		Forrester forrester = findForresterById(forresterId);
		return new ForresterData(forrester);
	}
	
	@Transactional(readOnly = false)
	public void deleteForresterById(Long forresterId) {
		Forrester forrester = findForresterById(forresterId);
		forresterDao.delete(forrester);
	}

	
	@Transactional(readOnly = false)
	public TreeFieldData saveTreeField(Long forresterId,
			TreeFieldData treeFieldData) {
		Forrester forrester = findForresterById(forresterId);
		
		Set<Tree> trees = 
				treeDao.findAllByTreeIn(treeFieldData.getTrees());
		
		TreeField treeField = findOrCreateTreeField(treeFieldData.getTreeFieldId());
		setTreeFieldFields(treeField, treeFieldData);
		
		treeField.setForrester(forrester);
		forrester.getTreeFields().add(treeField);
		
		for(Tree tree : trees) {
			tree.getTreeFields().add(treeField);
			treeField.getTrees().add(tree);
		}
		
		TreeField dbTreeField = treeFieldDao.save(treeField);
		return new TreeFieldData(dbTreeField);
		
	}
	
	private void setTreeFieldFields(TreeField treeField, TreeFieldData treeFieldData) {
		treeField.setTreeFieldCountry(treeFieldData.getTreeFieldCountry());
		treeField.setTreeFieldDescription(treeFieldData.getTreeFieldDescription());
		treeField.setFieldGeoLocation(treeFieldData.getFieldGeoLocation());
		treeField.setTreeFieldName(treeFieldData.getTreeFieldName());
		treeField.setTreeFieldId(treeFieldData.getTreeFieldId());
		treeField.setTreeFieldStateOrProvince(treeFieldData.getTreeFieldStateOrProvince());
	}

	
	private TreeField findOrCreateTreeField(Long treeFieldId) {
		TreeField treeField;
		
		if(Objects.isNull(treeFieldId)) {
			treeField = new TreeField();
		}	
		else {
			treeField = findTreeFieldById(treeFieldId);
		}
		
		return treeField;
	}
	
	
	private TreeField findTreeFieldById(Long treeFieldId) {
		return treeFieldDao.findById(treeFieldId)
				.orElseThrow(() -> new NoSuchElementException(
						"Tree Field with ID=" 
						+ treeFieldId + 
						"does not exist."));
	}
	
	@Transactional(readOnly = true)
	public TreeFieldData retrieveTreeFieldById(Long forresterId, Long treeFieldId) {
		findForresterById(forresterId);
		TreeField treeField = findTreeFieldById(treeFieldId);
		
		if(treeField.getForrester().getForresterId() != forresterId) {
			throw new IllegalStateException("Tree Field with ID=" + treeFieldId 
					+ " is not owned by Forrester with ID=" + forresterId);
		}
		
		return new TreeFieldData(treeField);
		
	}
	
}


