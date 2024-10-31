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

import tree.harvest.controller.model.ForesterData;
import tree.harvest.controller.model.TreeFieldData;
import tree.harvest.dao.ForesterDao;
import tree.harvest.dao.TreeDao;
import tree.harvest.dao.TreeFieldDao;
import tree.harvest.entity.Forester;
import tree.harvest.entity.Tree;
import tree.harvest.entity.TreeField;

@Service
public class TreeFieldService {

	@Autowired
	private ForesterDao foresterDao;
	
	@Autowired
	private TreeDao treeDao;
	
	@Autowired
	private TreeFieldDao treeFieldDao;
	
	@Transactional(readOnly = false)
	public ForesterData saveForester(ForesterData foresterData) {
		Long forresterId = foresterData.getForesterId();
		Forester forester = findOrCreateForester(forresterId,
				foresterData.getForesterEmail());
		
		setFieldsInForester(forester, foresterData);
		
		return new ForesterData(foresterDao.save(forester));
	}
	
	private void setFieldsInForester(Forester forester,
			ForesterData foresterData) {
		forester.setForesterEmail(foresterData.getForesterEmail());
		forester.setForesterFirstName(foresterData.getForesterFirstName());
		forester.setForesterLastName(foresterData.getForesterLastName());
		
	}
	
	private Forester findOrCreateForester(Long foresterId,
			String foresterEmail) {
		Forester forester;
		
		if(Objects.isNull(foresterId)) {
			Optional<Forester> opForrester = 
					foresterDao.findByForesterEmail(foresterEmail);
			
			if(opForrester.isPresent()) {
				throw new DuplicateKeyException (
					"Forester with email " + foresterEmail 
					+ " already exists."); 
			}
			
			forester = new Forester();
		}
		else {
			forester = findForesterById(foresterId);
			
		}
		
		return forester;
		
	}
	
	private Forester findForesterById(Long foresterId) {
		return foresterDao.findById(foresterId)
				.orElseThrow(() -> new NoSuchElementException(
						"Forester with ID=" + foresterId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<ForesterData> retrieveAllForesters() {
		List<Forester> foresters = foresterDao.findAll();
		List<ForesterData> response = new LinkedList<>();
		
		for (Forester forester : foresters) {
			response.add(new ForesterData(forester));
		}
		
		return response;
		
	}
	
	@Transactional(readOnly = true)
	public ForesterData retrieveForesterId(Long foresterId) {
		Forester forester = findForesterById(foresterId);
		return new ForesterData(forester);
	}
	
	@Transactional(readOnly = false)
	public void deleteForesterById(Long foresterId) {
		Forester forester = findForesterById(foresterId);
		foresterDao.delete(forester);
	}

	
	@Transactional(readOnly = false)
	public TreeFieldData saveTreeField(Long foresterId,
			TreeFieldData treeFieldData) {
		Forester forester = findForesterById(foresterId);
		
		Set<Tree> trees = 
				treeDao.findAllByTreeIn(treeFieldData.getTrees());
		
		TreeField treeField = findOrCreateTreeField(treeFieldData.getTreeFieldId());
		setTreeFieldFields(treeField, treeFieldData);
		
		treeField.setForester(forester);
		forester.getTreeFields().add(treeField);
		
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
	public TreeFieldData retrieveTreeFieldById(Long foresterId, Long treeFieldId) {
		findForesterById(foresterId);
		TreeField treeField = findTreeFieldById(treeFieldId);
		
		if(treeField.getForester().getForesterId() != foresterId) {
			throw new IllegalStateException("Tree Field with ID=" + treeFieldId 
					+ " is not owned by Forester with ID=" + foresterId);
		}
		
		return new TreeFieldData(treeField);
		
	}
	
}


