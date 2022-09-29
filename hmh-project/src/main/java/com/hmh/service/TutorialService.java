package com.hmh.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hmh.model.Tutorial;
import com.hmh.repository.TutorialRepository;

@Component
public class TutorialService {

	@Autowired
	TutorialRepository tutorialRepository;

	public List<Tutorial> getAllTutorials(String title) {
		List<Tutorial> tutorials = new ArrayList<Tutorial>();

		if (title == null)
			tutorialRepository.findAll().forEach(tutorials::add);
		else
			tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
		return tutorials;
	}
	
	public Optional<Tutorial> getTutorialById(long id) {
		Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
		return tutorialData;
	}
	
	public Tutorial createTutorial(Tutorial tutorial) {
		Tutorial _tutorial = tutorialRepository
				.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
		return _tutorial;
	}
	
	public void deleteTutorial(long id) {
		tutorialRepository.deleteById(id);
	}

	public void deleteAllTutorials() {
		tutorialRepository.deleteAll();
	}
	
	public List<Tutorial> getPublishedTutorials() {
		List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
		return tutorials;
	}
	
	public Tutorial updateTutorial(long id, Tutorial tutorial) {
		Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
		if (tutorialData.isPresent()) {
			Tutorial _tutorial = tutorialData.get();
			_tutorial.setTitle(tutorial.getTitle());
			_tutorial.setDescription(tutorial.getDescription());
			_tutorial.setPublished(tutorial.isPublished());
			return tutorialRepository.save(_tutorial);
		}else {
			return null;
		}
	}
}
