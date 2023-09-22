package edu.eci.arsw.blueprints.services.filters;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;

@Component
@Service
public interface BlueprintFilter {
    
    /**
     * 
     * @param bp the new blueprint
     */
    public Blueprint filterBlueprint(Blueprint bp);
}
