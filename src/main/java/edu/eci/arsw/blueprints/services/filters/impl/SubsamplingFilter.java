package edu.eci.arsw.blueprints.services.filters.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.filters.BlueprintFilter;

@Service
public class SubsamplingFilter implements BlueprintFilter{
    
    @Override
    public Blueprint filterBlueprint(Blueprint bp) {
        List<Point> points = bp.getPoints();
        ArrayList<Point> toAdd = new ArrayList<>();
        
        for(int i = 0; i < points.size(); i++) {
            if(i%2==0){
                toAdd.add(points.get(i));
            }
        }

        Point[] pointsToAdd = new Point[toAdd.size()];

        for(int i = 0; i < pointsToAdd.length; i++){
            pointsToAdd[i] = toAdd.get(i);
        }

        bp.setPoints(pointsToAdd);

        return bp;
    }
}
